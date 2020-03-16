import {Component, ViewChild, Input, Output, EventEmitter} from '@angular/core';
import {EaPopup} from 'src/app/components/ea-popup/ea-popup.component';
import {SessionInfo} from 'src/app/models/session-info';
import {MasterService} from 'src/app/services/api/master.service';
import {LovDataEx} from 'src/app/models/api/lov-data-ex';
import {EaFlexGrid} from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MessageService} from 'src/app/services/message.service';
import {SystemMessage} from 'src/app/const/system-message';
import {SessionService} from 'src/app/services/session.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ppfs-group-selection',
  templateUrl: './ppfs-group-selection.component.html',
  styleUrls: ['./ppfs-group-selection.component.scss']
})
export class PpfsGroupSelectionComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * パラメータ
   */
  @Input() paramMap: object = {};

  /**
   * 会社内のみ
   */
  @Input() companyOnly = false;

  /**
   * 題名
   */
  @Input() title: string;

  /**
   * 題名スタイル
   */
  @Input() styleTitle: string;

  /**
   * 案件グループコードスタイル
   */
  @Input() ppfsGroupCodeStyle: string;

  /**
   * 案件グループ名スタイル
   */
  @Input() ppfsGroupNameStyle: string;

  /**
   * 案件グループ名スタイル
   */
  @Input() searchDateFrom: Date;

  /**
   * 案件グループ名スタイル
   */
  @Input() searchDateTo: Date;

  /**
   * 案件グループコードコントロール名
   */
  @Input() ppfsGroupCodeControlName: string;

  /**
   * 案件グループコントロール名
   */
  @Input() ppfsGroupNameControlName: string;

  /**
   * 案件グループフォーム
   */
  @Input() ppfsGroupForm: FormGroup;

  /**
   * 状態 -1:編集ロック、0:状態設定なし、1～：各エンティティの項目定義に従いセット
   */
  @Input() visibleStatus = true;

  /**
   * ポップアップ Ref
   */
  @ViewChild('ppfsGroupSelectionPopup', null) ppfsGroupSelectionPopup: EaPopup;

  /**
   * ステータス選択一覧グリッド Ref
   */
  @ViewChild('gridPpfsGroupSelectionList', null) gridPpfsGroupSelectionList: EaFlexGrid;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ステータス一覧
   */
  ppfsGroupSelectionList: LovDataEx[];

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private masterService: MasterService) {
    this.fb = fb;
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.searchForm = this.fb.group({
      ppfsGroupName: ['']
    });
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.resetForm();
    this.ppfsGroupSelectionPopup.show(true);
    this.init();
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.ppfsGroupSelectionPopup.hide();
  }

  /**
   * 初期処理
   */
  init() {
    const searchDateFrom = !this.searchDateFrom ? new Date() : this.searchDateFrom;
    const searchDateTo = !this.searchDateFrom ? new Date() : this.searchDateTo;
    this.paramMap = Object.assign(this.paramMap, {
                    gruoupName: (this.searchForm.controls.ppfsGroupName.value) ? this.searchForm.controls.ppfsGroupName.value : '',
                    searchDateFrom: this.datePipe.transform(searchDateFrom, 'yyyy-MM-dd'),
                    searchDateTo: this.datePipe.transform(searchDateTo, 'yyyy-MM-dd')});
    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
      this.ppfsGroupSelectionList = resp;
      this.gridPpfsGroupSelectionList.resetSelection();
    });
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridPpfsGroupSelectionList && this.gridPpfsGroupSelectionList.selectedRows.length) {
      this.ppfsGroupForm.controls[this.ppfsGroupCodeControlName].setValue(this.gridPpfsGroupSelectionList.selectedRows[0].dataItem.code);
      this.ppfsGroupForm.controls[this.ppfsGroupNameControlName].setValue(this.gridPpfsGroupSelectionList.selectedRows[0].dataItem.name);
      this.select.emit(this.gridPpfsGroupSelectionList.selectedRows[0].dataItem);
      this.close();
    } else {
      this.messageService.info(SystemMessage.Info.msg100008);
    }

  }

  /**
   * 文字入力後処理
   * @param event キーボードイベント
   */
  onInput(event: KeyboardEvent) {
    if (event.keyCode === 13 || this.ppfsGroupForm.controls[this.ppfsGroupCodeControlName].value.length === 0) {
      this.getMasterNameData();
    }
  }

  /**
   * 社員情報取得
   */
  getMasterNameData() {
    setTimeout(() => {
      const code = this.ppfsGroupForm.controls[this.ppfsGroupCodeControlName].value;
      const searchDateFrom = !this.searchDateFrom ? new Date() : this.searchDateFrom;
      const searchDateTo = !this.searchDateFrom ? new Date() : this.searchDateTo;
      if (code.length >= 1) {
        this.paramMap = Object.assign(this.paramMap, {
                        gruoupName: (this.searchForm.controls.ppfsGroupName.value) ? this.searchForm.controls.ppfsGroupName.value : '',
                        searchDateFrom: this.datePipe.transform(searchDateFrom, 'yyyy-MM-dd'),
                        searchDateTo: this.datePipe.transform(searchDateTo, 'yyyy-MM-dd')});
        this.masterService.getLovData(this.sqlName, this.paramMap, code).subscribe((resp: LovDataEx) => {
            if (resp) {
              this.ppfsGroupForm.controls[this.ppfsGroupNameControlName].setValue(resp.name);
              this.ppfsGroupForm.controls[this.ppfsGroupCodeControlName].setValue(code);
              this.select.emit(resp);
            } else {
              this.ppfsGroupForm.controls[this.ppfsGroupNameControlName].setValue(null);
              this.select.emit(null);
            }
          }
        );
      } else {
        this.ppfsGroupForm.controls[this.ppfsGroupNameControlName].setValue(null);
        this.select.emit(null);
      }
    });
  }

  /**
   * フォーカス時に社員情報取得
   */
  getMasterDataOnFocus() {
    setTimeout(() => {
      const value = this.ppfsGroupForm.controls[this.ppfsGroupNameControlName].value;
      if (!value) {
        this.getMasterNameData();
      }
    }, 100);
  }

  /**
   * 絞込フォームリセット
   */
  resetForm() {
    this.ppfsGroupSelectionList = [];
    this.searchForm.reset();
  }
}
