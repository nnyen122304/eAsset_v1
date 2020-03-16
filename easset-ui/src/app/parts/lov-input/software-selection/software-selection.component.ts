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
  selector: 'app-software-selection',
  templateUrl: './software-selection.component.html',
  styleUrls: ['./software-selection.component.scss']
})
export class SoftwareSelectionComponent {
  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;
  /**
   * 非表示/表示
   */
  useSearch1Flag = false;
  /**
   * コメント
   */
  comment: string;
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
      filterMakerName: [''],
      filterName: [''],
    });
  }

  /**
   * 初期処理
   */
  init() {
    if (this.paramMap.makerId !== '') {
      this.useSearch1Flag = true;
      this.comment = '※　メーカー：「' + this.paramMap.makerId + '」のソフトウェアを検索します。';
    } else {
      this.useSearch1Flag = false;
    }
    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe(
      (resp: LovDataEx[]) => {
        this.dataList = resp;
        if (this.gridSelectionList) {
          this.gridSelectionList.resetSelection();
        }
      });
  }
  /**
   * 検索処理
   */
  search() {
    const param = {
      makerId: this.paramMap.makerId, value2: this.searchForm.controls.filterMakerName.value,
      name: this.searchForm.controls.filterName.value
    };
    this.masterService.searchLovList(this.sqlName, param).subscribe(
      (resp: LovDataEx[]) => {
        this.dataList = resp;
        if (this.gridSelectionList) {
          this.gridSelectionList.resetSelection();
        }
      });
  }
  /**
   * ツリーをクリア
   */
  clear() {
    this.selectedItem = {};
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
    this.softwareForm.controls[this.softwareNameControlName].setValue(this.selectedItem.name);
    this.softwareForm.controls[this.softwareCodeControlName].setValue(this.selectedItem.code);
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
    this.searchForm.patchValue({
      filterMakerName: '',
      filterName: ''
    });
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
  }
}
