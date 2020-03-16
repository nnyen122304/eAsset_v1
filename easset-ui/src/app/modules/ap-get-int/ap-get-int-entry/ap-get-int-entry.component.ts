import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApGetIntComponent } from '../ap-get-int.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import * as wjGrid from 'wijmo/wijmo.grid';
import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetIntLineAtt } from 'src/app/models/api/ap-get-int/ap-get-int-line-att.model';
import { SystemMessage } from 'src/app/const/system-message';
import { User } from 'src/app/models/api/user';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { SystemConst } from 'src/app/const/system-const';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { StatusSelectionComponent } from 'src/app/parts/lov/status-selection/status-selection.component';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { CodeNameEx } from 'src/app/models/api/code-name-ex';
import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';
import { ApGetIntLineDevSchEx } from 'src/app/models/api/ap-get-int/ap-get-int-line-dev-sch-ex.model';
import * as wjcInput from 'wijmo/wijmo.input';
import { CustomGridEditor } from './custom-editor..component';

@Component({
  selector: 'app-ap-get-int-entry',
  templateUrl: './ap-get-int-entry.component.html',
  styleUrls: ['./ap-get-int-entry.component.scss']
})
export class ApGetIntEntryComponent extends AbstractChildComponent<ApGetIntComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * accessMenu
   */
  accessMenu = '';

  /**
   * accessLevel
   */
  accessLevel = '';

  /**
   * 戻る遷移先
   */
  backToIndex = '-1';


  /**
   * 開発スケジュール明細
   */
  apGetIntLineDevSchAC: ApGetIntLineDevSchEx[];

  /**
   * コードネーム一覧データ
   */
  apGetIntMktDevSchList: CodeNameEx[];

  // tslint:disable-next-line: no-any
  apGetIntLineFldLineRef: any;

  /**
   * フォーム
   */
  apGetIntForm: FormGroup;

  /**
   * 申請者-社員番号フォーム
   */
  staffForm: FormGroup;

  /**
   * 取得金額範囲
   */
  apGetAmountRange: FormGroup;

  /**
   * 申請者-申請部署フォーム
   */
  sectionForm: FormGroup;

  holSectionForm: FormGroup;

  holSectionStaffForm: FormGroup;

  holRepOfficeForm: FormGroup;

  /**
   * 案件グループ
   */
  astGroup: FormGroup;

  /**
   * 取得金額範囲count
   */
  countApGetAmountRange = 0;

  /**
   * 添付明細
   */
  apGetIntLineAttList = [];


  /**
   * astCompletePlanDateLabel
   */
  astCompletePlanDateLabel = '';
  /**
   * astRentFlag
   */
  astRentFlag = false;

  /**
   * 資産
   */
  apGetAmountRangeUseCostType = '1';


  modeConfirmFlag = {
    delete: '1',
    file: '2'
  };

  confirmFlag = '';

  /**
   * Default status
   */
  apStatus = SystemConst.ApStatBgnInt.apStatBgnIntNoApply;

  readonly ApStatGetInt = SystemConst.ApStatBgnInt;
  /**
   * 検索条件SC
   */
  searchParamSC: ApGetIntSC = new ApGetIntSC();

  /**
   * ステータス選択ポップアップ Ref
   */
  @ViewChild(StatusSelectionComponent, null) popupStatus: StatusSelectionComponent;
  @ViewChild(ConfirmPopupComponent, null) confirmPopup: ConfirmPopupComponent;
  @ViewChild(ApGetIntEntryComponent, null) apGetIntEntryComponent: ApGetIntEntryComponent;
  @ViewChild('viewApGetIntLineFldLineComponent', { read: ViewContainerRef }) viewApGetIntLineFldLineComponent: ViewContainerRef;
  @ViewChild('gridApGetIntLineAtt', null) gridApGetIntLineAtt: EaFlexGrid;
  @ViewChild('gridApGetIntLineDevSchAC', null) gridApGetIntLineDevSchAC: EaFlexGrid;


  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private messageService: MessageService,
    private masterService: MasterService,
    private fileDownloadService: FileDownloadService,
  ) {
    super();
  }

  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.setAccessLevel();
    this.setAccessMenu();
    this.apGetIntMktDevSch();
    this.initForm();
  }

  /**
   * Get access level
   */
  setAccessLevel() {
    switch (this.sessionInfo.currentAccessLevel) {
      case 'S':
        this.accessLevel = 'admin';
        break;
      case 'C':
        this.accessLevel = 'general';
        break;
      case 'B':
        this.accessLevel = 'asset_manager';
        break;
      default:
        this.accessLevel = 'category';
        break;
    }
  }

  /**
   * Get access menu
   */
  setAccessMenu() {
    switch (this.sessionInfo.currentMenuId) {
      case '08010':
        this.accessMenu = 'menuIdApGetIntCreate1';
        break;
      case '08020':
        this.accessMenu = 'menuIdApGetIntCreate2';
        break;
      case '08030':
        this.accessMenu = 'menuIdApGetIntCreate3';
        break;
      case '08098':
        this.accessMenu = 'menuIdApGetIntEapp';
        break;
      case '08099':
        this.accessMenu = 'menuIdApGetIntRef';
        break;
      default:
        this.accessMenu = 'menuIdApGetIntCreate1';
        break;
    }
  }


  /**
   * 起票者メールボタンクリック
   */
  mailApCreateMailTo() {
    window.location.href = 'mailto:' + this.sessionInfo.loginUser.mailAddress + ';';
  }

  /**
   * 申請者メールボタンクリック
   */
  mailApMailTo() {
    const apCompanyCode = this.apGetIntForm.controls.apCompanyCode.value;
    const apStaffCode = this.apGetIntForm.controls.apStaffCode.value;
    this.masterService.getStaff(apCompanyCode, apStaffCode).subscribe((resp: User) => {
      console.log(resp);
      if (resp) {
        window.location.href = 'mailto:' + resp.mailAddress + ';';
      } else {
        window.location.href = 'mailto:' + '';
      }
    });
  }

  /**
   * Init form
   */
  initForm() {
    // 申請者 - 社員番号フォーム
    this.staffForm = this.fb.group({
      staffCode: [this.sessionInfo.loginUser.staffCode],
      staffName: [this.sessionInfo.loginUser.name]
    });

    let apSectionCode = '';
    let apSectionName = '';
    if (this.sessionInfo.loginCompanyCode === this.sessionInfo.loginUser.companyCode) {
      // ログインユーザの所属がログイン会社と同じ場合のみ部署セット
      apSectionCode = this.sessionInfo.loginUser.sectionCode;
      apSectionName = this.sessionInfo.loginUser.sectionName;
    }
    // 申請者-申請部署フォーム
    this.sectionForm = this.fb.group({
      apSectionCode: [apSectionCode],
      apSectionName: [apSectionName]
    });
    // 案件グループフォーム
    this.astGroup = this.fb.group({
      astGroupCode: [''],
      astGroupName: ['']
    });

    this.apGetAmountRange = this.fb.group({
      apGetAmountRangeName: [''],
      value1: [''],
      code: [''],
      value7: [''],
      value10: [''],
      value11: [''],
      value12: ['']
    });

    this.holSectionForm = this.fb.group({
      holSectionCode: [apSectionCode],
      holSectionName: [apSectionName],
      holSectionYear: [this.sessionInfo.currentYear]
    });

    this.holSectionStaffForm = this.fb.group({
      holStaffCode: [this.sessionInfo.loginUser.staffCode],
      holStaffName: [this.sessionInfo.loginUser.name]
    });

    this.holRepOfficeForm = this.fb.group({
      holRepOfficeCode: [''],
      holRepOfficeName: [''],
    });

    this.apGetIntForm = this.fb.group({
      eappId: [null],
      apStatus: ['1'],
      apStatusName: ['未申請'],
      apGetTypeName: [''],
      apNeedCioJudgeFlag: [null],
      apCreateStaffCode: [this.sessionInfo.loginUser.staffCode],
      apCreateStaffName: [this.sessionInfo.loginUser.name],
      apCreateCompanyCode: [this.sessionInfo.loginUser.companyCode],
      apCreateCompanyName: [this.sessionInfo.loginUser.companyName],
      apCreateSectionCode: [this.sessionInfo.loginUser.sectionCode],
      apCreateSectionName: [this.sessionInfo.loginUser.sectionName],
      apCreateSectionYear: [this.sessionInfo.currentYear],
      apCreateTel: [this.sessionInfo.loginUser.tel1],
      apStaffCode: [this.sessionInfo.loginUser.staffCode],
      apStaffName: [this.sessionInfo.loginUser.name],
      apCompanyCode: [this.sessionInfo.loginCompanyCode],
      apCompanyName: [this.sessionInfo.loginCompanyName],
      apSectionCode: [this.sessionInfo.loginUser.sectionCode],
      apSectionName: [this.sessionInfo.loginUser.sectionName],
      apSectionYear: [this.sessionInfo.currentYear],
      holCompanyCode: [this.sessionInfo.loginUser.sectionCode],
      holCompanyName: [this.sessionInfo.loginUser.sectionName],
      holSectionYear: [this.sessionInfo.currentYear],
      holStaffName: [this.sessionInfo.loginUser.name],
      holStaffCompanyName: [this.sessionInfo.loginCompanyName],
      holStaffSectionName: [this.sessionInfo.loginUser.sectionName],
      apTel: [this.sessionInfo.loginUser.tel1],
      astCloudType: ['1'],
      astEffectType: ['1'],
      astRentFlag: []
    });
  }

  init() {
    this.searchParamSC = Object.assign(this.searchParamSC, { apStaffName: this.staffForm.controls.staffName.value });
    if (this.accessMenu === 'menuIdApGetIntCreate1') { // 社内使用ソフトウェア
      this.apGetIntForm.controls.apGetTypeName.setValue(SystemConst.CategoryApGetInt.apGetTypeInt1);
      this.astCompletePlanDateLabel = 'リリース予定日:';
    } else if (this.accessMenu === 'menuIdApGetIntCreate2') { // 市販目的ソフトウェア
      this.apGetIntForm.controls.apGetTypeName.setValue(SystemConst.CategoryApGetInt.apGetTypeInt2);
      this.astCompletePlanDateLabel = '開発完了予定日:';
    } else if (this.accessMenu === 'menuIdApGetIntCreate3') { // 長期前払費用/その他無形固定資産
      this.apGetIntForm.controls.apGetTypeName.setValue(SystemConst.CategoryApGetInt.apGetTypeInt3);
      this.astCompletePlanDateLabel = '検収(納品)予定日:';
    }
  }

  /**
   * ポップアップの社内システム（基幹システム）関連資産取得の場合のみCIO承認
   */
  toggleApNeedCioJudgeFlag() {
    if (this.apGetIntForm.controls.apNeedCioJudgeFlag.value === true) {
      this.messageService.warn(SystemMessage.Warn.msg200008);
    }
  }

  /**
   * ステータス選択後処理
   *
   * @param status 選択ステータス情報
   *
   * @return void
   */
  changeApStatus(status: LovDataEx) {
    this.apGetIntForm.controls.apStatus.setValue(status.code);
    this.apGetIntForm.controls.apStatusName.setValue(status.value1);
  }

  /**
   * 取得金額範囲選択後処理
   *
   * @param data 選択取得金額範囲情報
   *
   * @return void
   */
  selectApGetAmountRange(data: LovDataEx) {
    this.apGetAmountRange.controls.value1.setValue(data.value1);
    this.apGetAmountRange.controls.code.setValue(data.code);
    this.apGetAmountRange.controls.value7.setValue(data.value7);
    this.apGetAmountRange.controls.value10.setValue(data.value10);
    this.apGetAmountRange.controls.value11.setValue(data.value11);
    this.apGetAmountRange.controls.value12.setValue(data.value12);

    if (this.apGetAmountRange.controls.value11.value === '1') {
      this.countApGetAmountRange++;
      if (this.countApGetAmountRange >= 2 && this.searchParamSC.specialApproveFlag.toString() === 'false') {
        this.searchParamSC = Object.assign(this.searchParamSC, { specialApproveFlag: false });
        return;
      }
      this.searchParamSC = Object.assign(this.searchParamSC, { specialApproveFlag: true });
    } else {
      this.countApGetAmountRange = 0;
      this.searchParamSC = Object.assign(this.searchParamSC, { specialApproveFlag: false });
      this.searchParamSC = Object.assign(this.searchParamSC, { specialApproveDate: null });
    }

    // 資産区分(1:資産、2:費用)
    this.apGetAmountRangeUseCostType = data.value12;

    console.log(this.apGetAmountRangeUseCostType);
  }

  /**
   * changeStaffSelection
   */
  changeStaffSelection(data: User) {
    this.apGetIntForm.controls.apStaffCode.setValue(data.staffCode);
    this.apGetIntForm.controls.apStaffName.setValue(data.name);
    this.apGetIntForm.controls.apSectionCode.setValue(data.sectionCode);
    this.apGetIntForm.controls.apSectionName.setValue(data.sectionName);
    this.apGetIntForm.controls.apTel.setValue(data.tel1);
    this.sectionForm.patchValue({
      apSectionCode: data.sectionCode,
      apSectionName: data.sectionName
    });
  }

  /**
   * changeHolSection
   */
  changeHolSection(data) {

  }

  /**
   * changeHolSectionStaff
   */
  changeHolSectionStaff(data: User) {
    this.apGetIntForm.controls.holStaffName.setValue(data.name ? data.name : null);
    this.apGetIntForm.controls.holStaffCompanyName.setValue(data.companyName ? data.companyName : null);
    this.apGetIntForm.controls.holStaffSectionName.setValue(data.sectionName ? data.sectionName : null);
  }

  /**
   * データ変更あり時クローズ確認
   */
  confirm(confirm: boolean = false) {
    if (!confirm) {
      this.confirmFlag = this.modeConfirmFlag.delete;
      this.confirmPopup.prompt(SystemMessage.Info.msg100021, document.activeElement);
      return;
    }

    if (this.confirmFlag === this.modeConfirmFlag.file) {
      // Get selected row
      const selectRow = this.gridApGetIntLineAtt.selectedRows[0];
      // Remove row
      this.apGetIntLineAttList = this.apGetIntLineAttList.filter((item) => {
        if (item.lineSeq !== selectRow.dataItem.lineSeq) {
          if (item.lineSeq > selectRow.dataItem.lineSeq) {
            item.lineSeq -= 1;
          }
          return true;
        }
        return item.lineSeq !== selectRow.dataItem.lineSeq;
      });
      // // Reset grid
      // this.gridApGetIntLineAtt.resetSelection();
    }
  }

  /**
   * 添付明細クリア
   */
  initGridApGetIntLineAttList(grid) {
    // Add event listener
    grid.addEventListener(grid.hostElement, 'click', (e) => {
      console.log(e.target.id);
      // If click delete button
      if (e.target.id === 'btnDel') {
        this.confirmFlag = this.modeConfirmFlag.file;
        this.confirmPopup.prompt('添付ファイルを削除してもよろしいでしょうか。', document.activeElement);
      } else if (e.target.name === 'iptComment') {
        e.target.addEventListener('focusout', () => {
          // Get selected row
          const selectRow = this.gridApGetIntLineAtt.selectedRows[0];
          this.apGetIntLineAttList = this.apGetIntLineAttList.map(item => {
            if (item.lineSeq === selectRow.dataItem.lineSeq) {
              item = selectRow.dataItem;
            }
            return item;
          });
          this.gridApGetIntLineAtt.resetSelection();
        });
      }
    });
  }

  uploadFile(file) {
    if (!file) {
      return;
    }

    const apGetIntLineAtt = new ApGetIntLineAtt();
    apGetIntLineAtt.lineSeq = (this.apGetIntLineAttList.length + 1);
    apGetIntLineAtt.attFileIdTmp = file.fileId;
    apGetIntLineAtt.createStaffCode = this.sessionInfo.loginUser.staffCode;
    apGetIntLineAtt.createStaffName = this.sessionInfo.loginUser.name;
    apGetIntLineAtt.attFileName = file.fileName;
    apGetIntLineAtt.createDate = new Date();
    this.apGetIntLineAttList.push(apGetIntLineAtt);
    this.gridApGetIntLineAtt.collectionView.refresh();
    this.gridApGetIntLineAtt.resetSelection();
  }

  /**
   * Download file
   */
  downloadFile(file) {
    this.fileDownloadService.download(file.attFileIdTmp, file.attFileName, 'application/pdf');
  }

  apGetIntMktDevSch() {
    let i = 1;
    this.masterService.getCodeNameList('AP_GET_INT_MKT_DEV_SCH', null, null, null)
      .subscribe(
        (resp: CodeNameEx[]) => {
          // this.apGetIntLineDevSchAC = Object.assign(this.apGetIntLineDevSchAC, resp);
          for (const item of resp) {
            if (this.apGetIntLineDevSchAC === undefined) {
              this.apGetIntLineDevSchAC = [{
                index: 0,
                levelName: item.value1,
                devPeriodFrom: null,
                devPeriodTo: null,
                devProperCount: null,
                devEntrustCount: null,
                totalPeopleCount: null,
                devAmount: null
              }];
            } else {
              this.apGetIntLineDevSchAC.push({
                index: i++,
                levelName: item.value1,
                devPeriodFrom: null,
                devPeriodTo: null,
                devProperCount: null,
                devEntrustCount: null,
                totalPeopleCount: null,
                devAmount: null
              });
            }
          }
        });
  }

  /**
   * グリッドの初期勝利
   * @param grid Flexグリッド要素
   */
  onGridInitApGetIntLineDevSchADG(grid: EaFlexGrid) {

    // Add footer
    this.gridApGetIntLineDevSchAC.columnFooters.rows.push(new wjGrid.GroupRow());

    // add custom editors to the grid
    // tslint:disable-next-line: no-unused-expression
    new CustomGridEditor(grid, 'devPeriodFrom', wjcInput.InputDate, {
      format: 'd',
      isRequired: false
    });

    // tslint:disable-next-line: no-unused-expression
    new CustomGridEditor(grid, 'devPeriodTo', wjcInput.InputDate, {
      format: 'd',
      isRequired: false
    });

    const extraRow = new wjGrid.Row();
    extraRow.allowMerging = true;
    extraRow.allowResizing = true;
    const panel = grid.columnHeaders;
    panel.rows.splice(0, 0, extraRow);

    for (let colIndex = 1; colIndex <= 2; colIndex++) {
      panel.setCellData(0, colIndex, '実施期間');
    }

    for (let colIndex = 3; colIndex <= 5; colIndex++) {
      panel.setCellData(0, colIndex, '要員数(人月)');
    }

    const mergableCols = [0, 6];
    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });

    grid.frozenColumns = 1;

    // // Add footer
    // this.gridApGetIntLineDevSchAC.columnFooters.rows.push(new wjGrid.GroupRow());
    // // Set selected is row -1
    // this.gridApGetIntLineDevSchAC.resetSelection();
    // const that = this;
    // // Add event listener
    // this.gridApGetIntLineDevSchAC.addEventListener(this.gridApGetIntLineDevSchAC.hostElement, 'click', (e) => {
    //   that.apGetIntLineDevSchAC = that.apGetIntLineDevSchAC.filter((item) => {
    //     item.devPeriodFrom = null;
    //   });
    // });
  }

  fnFocusOut(row, $event, name = 'devProperCount') {
    this.apGetIntLineDevSchAC = this.apGetIntLineDevSchAC.map(item => {
      if (item.index === row.index) {
        if (name === 'devProperCount') {
          // tslint:disable-next-line: radix
          item.devProperCount = parseInt($event.target.value);
        } else {
          // tslint:disable-next-line: radix
          item.devEntrustCount = parseInt($event.target.value);
        }

        item.totalPeopleCount = item.devProperCount + item.devEntrustCount;
      }

      return item;
    });
    this.gridApGetIntLineDevSchAC.collectionView.refresh();
    this.gridApGetIntLineDevSchAC.resetSelection();
  }
}
