import { Component, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { WjTreeView } from 'wijmo/wijmo.angular2.nav';
import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';
import { SessionInfo } from 'src/app/models/session-info';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-software-maker-selection',
  templateUrl: './software-maker-selection.component.html',
  styleUrls: ['./software-maker-selection.component.scss']
})
export class SoftwareMakerSelectionComponent {
  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * ソフトウェアリスト
   */
  dataList: LovDataEx[];

  /**
   * 選択中部署
   */
  selectedItem: WjTreeView['selectedItem'];

  /**
   * クラスを拡張する
   */
  @Input() nameClass: string;

  /**
   * 非表示/表示
   */
  @Input() isShowStatus = true;

  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * パラメータ
   */
  @Input() paramMap;

  /**
   * ソフトウェア形式
   */
  @Input() softwareForm: FormGroup;

  /**
   * ソフトウェア名
   */
  @Input() softwareCodeControlName: string;

  /**
   * ソフトウェアコード
   */
  @Input() softwareNameControlName: string;

  /**
   * 必須か判定用
   */
  @Input() required: boolean;

  /**
   * 選択のみ
   */
  @Input() selectOnly: boolean;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('apPopup', null) apPopup: EaPopup;

  /**
   * グリッド Ref
   */
  @ViewChild('gridSelectionList', null) gridSelectionList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private messageService: MessageService
  ) {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.fb = fb;
    this.searchForm = this.fb.group({
      filterText: ['']
    });
  }

  /**
   * 初期処理
   */
  init() {
    let param = this.paramMap;
    if (param) {
      if (this.searchForm.controls.filterText.value !== '') {
        param.name = this.searchForm.controls.filterText.value;
      }
    } else {
      param = {name: this.searchForm.controls.filterText.value};
    }
    this.masterService.searchLovList(this.sqlName, param).subscribe(
      (resp: LovDataEx[]) => {
        this.dataList = resp;
        if (this.gridSelectionList) {
          this.gridSelectionList.resetSelection();
        }
      });
  }

  /**
   * Flexグリッド初期処理
   */
  onGridInitialized() {
    this.gridSelectionList.resetSelection();
  }

  /**
   * ツリーをクリア
   */
  clear() {
    this.softwareForm.controls[this.softwareCodeControlName].setValue('');
    this.softwareForm.controls[this.softwareNameControlName].setValue('');
    this.select.emit(null);
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (!this.gridSelectionList.selectedRows[0]) {
      this.messageService.info(SystemMessage.Info.msg100008);
      return;
    }
    this.selectOnly = false;
    this.selectedItem = this.gridSelectionList.selectedRows[0].dataItem;
    this.softwareForm.controls[this.softwareCodeControlName].setValue(this.selectedItem.code);
    this.softwareForm.controls[this.softwareNameControlName].setValue(this.selectedItem.name);
    this.select.emit(this.selectedItem);
    this.close();
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.init();
    this.apPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    this.apPopup.hide();
    this.dataList = [];
    // フォームをリセット
    this.searchForm = this.fb.group({
      filterText: ['']
    });
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
  }
}
