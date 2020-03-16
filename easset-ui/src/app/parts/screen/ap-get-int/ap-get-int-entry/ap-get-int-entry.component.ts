import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver, Input } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { StatusSelectionComponent } from 'src/app/parts/lov/status-selection/status-selection.component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { MessageService } from 'src/app/services/message.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { MasterService } from 'src/app/services/api/master.service';
import { User } from 'src/app/models/api/user';
import { SystemMessage } from 'src/app/const/system-message';
import { SystemConst } from 'src/app/const/system-const';
import { ApGetIntLineFldLineComponent } from './ap-get-int-line-fld-line/ap-get-int-line-fld-line.component';

@Component({
  selector: 'app-ap-get-int-entry',
  templateUrl: './ap-get-int-entry.component.html',
  styleUrls: ['./ap-get-int-entry.component.scss']
})
export class ApGetIntEntryComponent implements OnInit {

  // tslint:disable-next-line: no-any
  apGetIntLineFldLineRef: any;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

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
   * ルクスレベル
   */
  @Input() accessLevel: string;

  /**
   * アクセスメニュー
   */
  @Input() accessMenu: string;

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
  @ViewChild('viewApGetIntLineFldLineComponent', { read: ViewContainerRef }) viewApGetIntLineFldLineComponent: ViewContainerRef;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private componentFactoryResolver: ComponentFactoryResolver,
    private messageService: MessageService,
    private masterService: MasterService
  ) {
  }

  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    // Init form
    this.initForm();
    // Create the ApGetIntLineFldLine component factory
    const apGetIntLineFldLine = this.componentFactoryResolver.resolveComponentFactory(ApGetIntLineFldLineComponent);
    // // Add the component to the view
    this.apGetIntLineFldLineRef = this.viewApGetIntLineFldLineComponent.createComponent(apGetIntLineFldLine);
    this.init();

    console.log(this.accessMenu);
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
      apCompanyCode: [this.sessionInfo.loginUser.companyCode],
      apCompanyName: [this.sessionInfo.loginUser.companyName],
      apSectionCode: [this.sessionInfo.loginUser.sectionCode],
      apSectionName: [this.sessionInfo.loginUser.sectionName],
      apSectionYear: [this.sessionInfo.currentYear],
      holCompanyCode: [this.sessionInfo.loginUser.companyCode],
      holCompanyName: [this.sessionInfo.loginUser.companyName],
      holSectionYear: [this.sessionInfo.currentYear],
      holStaffName: [this.sessionInfo.loginUser.name],
      holStaffCompanyName: [this.sessionInfo.loginUser.companyName],
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
}
