import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { SessionInfo } from 'src/app/models/session-info';
import { MasterService } from 'src/app/services/api/master.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-project-section',
  templateUrl: './project-section.component.html',
  styleUrls: ['./project-section.component.scss']
})
export class ProjectSectionComponent implements OnInit {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

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
  @Input() paramMap: object;

  /**
   * 会社内のみ
   */
  @Input() companyOnly = false;

  /**
   * 社員コードコントロール名
   */
  @Input() projectCodeControlName: string;

  /**
   * 社員コードコントロール名
   */
  @Input() projectNameControlName: string;


  /**
   * 社員検索用フォーム
   */
  @Input() projectForm: FormGroup;

  /**
   * ポップアップ Ref
   */
  @ViewChild('projectSelectionPopup', null) projectSelectionPopup: EaPopup;

  /**
   * ステータス選択一覧グリッド Ref
   */
  @ViewChild('gridProjectSelectionList', null) gridProjectSelectionList: EaFlexGrid;

  /**
   * 選択時イベント
   */
  @Output() selectData: EventEmitter<object> = new EventEmitter();

  /**
   * ステータス一覧
   */
  statusList: LovDataEx[];


  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private messageService: MessageService,
    private masterService: MasterService ) {
      this.fb = fb;
      this.sessionInfo = this.sessionService.getSessionInfo();
      this.searchForm = this.fb.group({
        projectName: [''],
        projectValue1: ['']
      });
  }

  ngOnInit() {
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.resetForm();
    this.projectSelectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.projectSelectionPopup.hide();
  }

  search() {
    const nameSearch = (this.searchForm.controls.projectName.value) ? this.searchForm.controls.projectName.value : '';
    const projectValue1 = (this.searchForm.controls.projectValue1.value) ? this.searchForm.controls.projectValue1.value : '';
    this.paramMap = Object.assign(this.paramMap, {name: nameSearch, value1: projectValue1});
    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
      this.statusList = resp;
      if (this.gridProjectSelectionList) {
        this.gridProjectSelectionList.resetSelection();
      }
    });
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridProjectSelectionList && this.gridProjectSelectionList.selectedRows.length) {
      this.projectForm.controls[this.projectCodeControlName].setValue(this.gridProjectSelectionList.selectedRows[0].dataItem.code);
      this.projectForm.controls[this.projectNameControlName].setValue(this.gridProjectSelectionList.selectedRows[0].dataItem.name);
      this.selectData.emit(this.gridProjectSelectionList.selectedRows[0].dataItem);
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
    if (event.keyCode === 13 || this.projectForm.controls[this.projectCodeControlName].value.length === 0) {
      this.getMasterNameData();
    }
  }

  /**
   * フォーカス時に社員情報取得
   */
  getProjectDataOnFocus() {
    setTimeout(() => {
      const value = this.projectForm.controls[this.projectNameControlName].value;
      if (!value) {
        this.getMasterNameData();
      }
    }, 100);
  }

  /**
   * 社員情報取得
   */
  getMasterNameData() {
    setTimeout(() => {
      const code = this.projectForm.controls[this.projectCodeControlName].value;
      if (code.length >= 1) {
        this.paramMap = Object.assign(this.paramMap, {gruoupName: this.searchForm.controls.projectName.value});
        this.masterService.getLovData(this.sqlName, this.paramMap, code).subscribe((resp: LovDataEx) => {
              if (resp) {
                this.projectForm.controls[this.projectNameControlName].setValue(resp.name);
                this.projectForm.controls[this.projectCodeControlName].setValue(code);
                this.selectData.emit(resp);
              } else {
                this.projectForm.controls[this.projectNameControlName].setValue(null);
                this.selectData.emit(null);
              }
            }
        );
      } else {
        this.projectForm.controls[this.projectNameControlName].setValue(null);
        this.selectData.emit(null);
      }
    });
  }

  /**
   * 絞込フォームリセット
   */
  resetForm() {
    this.statusList = [];
    this.searchForm.reset();
  }
}
