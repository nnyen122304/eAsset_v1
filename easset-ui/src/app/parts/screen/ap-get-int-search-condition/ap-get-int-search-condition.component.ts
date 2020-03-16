import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { User } from 'src/app/models/api/user';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { Section } from 'src/app/models/api/section';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { ApGetIntService } from 'src/app/services/api/ap-get-int.service';

const accessLevelSection = {
  ACCESS_LEVEL_SECTION_GENERAL: 'C',
  ACCESS_LEVEL_SECTION_MANAGER: 'B',
  ACCESS_LEVEL_ADMIN_COMPANY: 'A',
  ACCESS_LEVEL_ADMIN_SYSTEM: 'S',
};

@Component({
  selector: 'app-ap-get-int-search-condition',
  templateUrl: './ap-get-int-search-condition.component.html',
  styleUrls: ['./ap-get-int-search-condition.component.scss']
})
export class ApGetIntSearchConditionComponent implements OnInit {

  /**
   * 分権化
   */
  @Input() currentState: string;

  /**
   * フォーム 探す
   */
  searchForm: FormGroup;

  /**
   * フォーム 取得
   */
  form1: FormGroup;

  /**
   * フォーム 経費負担
   */
  form2: FormGroup;

  /**
   * フォーム 保有・設置
   */
  form3: FormGroup;

  /**
   * フォーム 資産明細
   */
  form4: FormGroup;

  /**
   * フォーム 備考
   */
  form5: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * トグル入力
   */
  checkApplicationId = true;

  /**
   * トグル入力
   */
  checkEappId = true;

  /**
   * からの検索日
   */
  searchDateFrom: Date;

  /**
   * 日付を検索
   */
  searchDateTo: Date;

  /**
   * isShow
   */
  isShow = true;

  /**
   * readOnly staff code
   */
  readOnlyStaffCode = false;

  /**
   * 検索条件
   */
  searchParam: ApGetIntSC;

  /**
   * 実施ステータス一覧
   */
  apStatus: string[] = ['未申請', '申請中', '承認済', '未申請(再)', '却下', '取下げ'];

  /**
   * 実施ステータス一覧
   */
  apGetType: string[] = ['社内使用ソフトウェア', '市販目的ソフトウェア', '長期前払費用/その他無形固定資産'];

  /**
   * 計上区分
   */
  addUpType: string[] = ['資産のみ', '費用のみ', '混在'];

  /**
   * リリース督促メール
   */
  reminderFlag: string[] = ['未送信', '送信済'];

  /**
   * ｻｰﾋﾞｽ提供開始報告
   */
  reportCompleteFlag: string[] = ['未報告', '報告済'];

  /**
   * クラウド区分
   */
  astCloudTypeAC = ([
    { data: '', label: '' },
    { data: '1', label: 'クラウド以外' },
    { data: '2', label: 'クラウド' }
  ]);

  /**
   * 販売管理費/原価区分
   */
  costTypeAC = ([
    { data: '', label: '' },
    { data: '1', label: '販売管理費' },
    { data: '2', label: '原価' }
  ]);

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
  ) { }

  ngOnInit() {
    this.init();
  }

  /**
   * 初期処理
   */
  init() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.searchForm = this.fb.group({
      applicationId: [''],
      applicationIdPlural: [''],
      eappId: [''],
      eappIdPlural: [''],
      apStatus: this.fb.array(this.apStatus.map((x) => false)),
      apDateFrom: [''],
      apDateTo: [''],
      apStaffCode: [''],
      apStaffName: [''],
      apGetType: this.fb.array(this.apGetType.map((x) => false)),
      addUpType: this.fb.array(this.addUpType.map((x) => false)),
      oldVerFlag: [''],
      reportCompleteFlag: this.fb.array(this.reportCompleteFlag.map((x) => false)),
    });

    this.form1 = this.fb.group({
      astCloudType: [''],
      astName: [''],
      astGroupCode: [''],
      astGroupName: [''],
      astCompletePlanDateFrom: [''],
      astCompletePlanDateTo: [''],
      reminderFlag: this.fb.array(this.reminderFlag.map((x) => false)),
      getTotalAmountFrom: [0],
      getTotalAmountTo: [0],
      reminderDateFrom: [''],
      reminderDateTo: [''],
      specialApproveFlag: [''],
    });

    this.form2 = this.fb.group({
      costType: [''],
      costDepPrjCode: [''],
      costDepPrjName: [''],
      searchDateFrom: Date,
      searchDateTo: Date,
      costSectionCode: [''],
      costSectionName: [''],
    });

    this.form3 = this.fb.group({
      holCompanyCode: this.sessionInfo.loginCompanyCode,
      holCompanyName: this.sessionInfo.loginCompanyName,
      holSectionCode: [''],
      holSectionName: [''],
      holSectionYear: [''],
      includeSectionHierarchyFlag: [false],
      holRepOfficeCode: [''],
      holRepOfficeName: [''],
    });

    this.form4 = this.fb.group({
      astCategoryCode: [''],
      astCategoryName: [''],
      astNameFld: [''],
      astPrjCodeFld: [''],
      astPrjNameFld: [''],
      astCommentFld: [''],
    });

    this.form5 = this.fb.group({
      description: [''],
    });

    if (this.accessLevel === accessLevelSection.ACCESS_LEVEL_SECTION_GENERAL) {
      this.searchForm.controls.apStaffCode.setValue(this.sessionInfo.loginUser.staffCode);
      this.searchForm.controls.apStaffName.setValue(this.sessionInfo.loginUser.name);
    }
    if (this.accessLevel === accessLevelSection.ACCESS_LEVEL_SECTION_GENERAL) {
      this.searchForm.controls.reminderFlag.disable();
    }
    switch (this.currentState) {
      case 'AP_GET_INT':
        break;
      case 'AP_GET_INT_UPDATE1':
      case 'AP_GET_INT_UPDATE2':
        this.searchForm.controls.apStatus.disable();
        this.searchForm.controls.oldVerFlag.disable();
        this.searchForm.controls.reportCompleteFlag.setValue(this.reportCompleteFlag.map((x, i) => {
          if (i === 0) {
            return true;
          }
          return false;
        })
        );
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
          if (i === 2) {
            return true;
          }
          return false;
        })
        );
        break;
      case 'AP_BGN_INT':
        this.searchForm.controls.apStatus.disable();
        this.searchForm.controls.apGetType.disable();
        this.searchForm.controls.reportCompleteFlag.disable();
        this.searchForm.controls.addUpType.disable();
        this.searchForm.controls.oldVerFlag.disable();
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
          if (i === 2) {
            return true;
          }
          return false;
        })
        );
        this.searchForm.controls.reportCompleteFlag.setValue(this.reportCompleteFlag.map((x, i) => {
          if (i === 0) {
            return true;
          }
          return false;
        })
        );
        this.searchForm.controls.apGetType.setValue(this.apGetType.map((x, i) => {
          if (i !== 2) {
            return true;
          }
          return false;
        })
        );
        this.searchForm.controls.addUpType.setValue(this.addUpType.map((x, i) => {
          if (i !== 1) {
            return true;
          }
          return false;
        })
        );
        break;
      case 'AP_LICENSE':
        this.searchForm.controls.apStatus.disable();
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
          if (i === 2) {
            return true;
          }
          return false;
        })
        );
    }
  }

  /**
   * 隠しボタン
   */
  linkBtnClickEvent(data) {
    switch (data) {
      case 'applicationId':
        this.checkApplicationId = !this.checkApplicationId;
        this.searchForm.controls.applicationId.setValue('');
        this.searchForm.controls.applicationIdPlural.setValue('');
        break;
      case 'eappId':
        this.checkEappId = !this.checkEappId;
        this.searchForm.controls.eappId.setValue('');
        this.searchForm.controls.eappIdPlural.setValue('');
        break;
    }
  }

  /**
   * 隠しボタン
   */
  changeReminderFlag() {
    return this.form1.controls.reminderFlag.value[1];
  }

  /**
   * 親選択後処理
   * @param data 親マスタ情報
   */
  onParentSelected(data: LovDataEx) {
  }

  setSearch() {
    const searchForm = this.searchForm.getRawValue();
    const form1 = this.form1.getRawValue();
    const form2 = this.form2.getRawValue();
    const form3 = this.form3.getRawValue();
    const form4 = this.form4.getRawValue();
    const form5 = this.form5.getRawValue();
    this.searchParam = Object.assign({}, searchForm, form1, form2, form3, form4, form5);
    this.searchParam.apStatus = searchForm.apStatus.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    this.searchParam.apGetType = searchForm.apGetType.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    this.searchParam.addUpType = searchForm.addUpType.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    this.searchParam.reportCompleteFlag = searchForm.reportCompleteFlag.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    this.searchParam.reminderFlag = form1.reminderFlag.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    this.searchParam.apDateFrom = searchForm.apDateFrom ? searchForm.apDateFrom : null;
    this.searchParam.apDateTo = searchForm.apDateTo ? searchForm.apDateTo : null;
    this.searchParam.astCompletePlanDateFrom = form1.astCompletePlanDateFrom ? form1.astCompletePlanDateFrom : null;
    this.searchParam.astCompletePlanDateTo = form1.astCompletePlanDateTo ? form1.astCompletePlanDateTo : null;
    this.searchParam.getTotalAmountFrom = searchForm.getTotalAmountFrom ? searchForm.getTotalAmountFrom : null;
    this.searchParam.getTotalAmountTo = searchForm.getTotalAmountTo ? searchForm.getTotalAmountTo : null;
    this.searchParam.reminderDateFrom = searchForm.reminderDateFrom ? searchForm.reminderDateFrom : null;
    this.searchParam.reminderDateTo = searchForm.reminderDateTo ? searchForm.reminderDateTo : null;
    this.searchParam.oldVerFlag = searchForm.oldVerFlag ? '1' : '0';
    this.searchParam.specialApproveFlag = searchForm.specialApproveFlag ? '1' : '0';
    this.searchParam.includeSectionHierarchyFlag = searchForm.includeSectionHierarchyFlag ? '1' : '0';
    return this.searchParam;
  }
}
