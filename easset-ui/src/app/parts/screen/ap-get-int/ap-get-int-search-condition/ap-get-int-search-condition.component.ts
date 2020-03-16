import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { FormGroup, FormBuilder, Form } from '@angular/forms';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';

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
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * フォーム検索
   */
  searchForm: FormGroup;

  /**
   * アクセスレベル
   */
  accessLevelSection = {
    ACCESS_LEVEL_SECTION_GENERAL: 'C',
    ACCESS_LEVEL_SECTION_MANAGER: 'B',
    ACCESS_LEVEL_ADMIN_COMPANY: 'A',
    ACCESS_LEVEL_ADMIN_SYSTEM: 'S',
  };

  /**
   * 取得申請番号あいまい⇒複数
   */
  applicationIdLike = true;

  /**
   * e申請書類IDあいまい⇒複数
   */
  eappIdLike = true;

  /**
   * からの検索日
   */
  searchDateFrom: Date;

  /**
   * 日付を検索
   */
  searchDateTo: Date;

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
    {data: '', label: ''},
    {data: '1', label: 'クラウド以外'},
    {data: '2', label: 'クラウド'}
  ]);

  /**
   * 販売管理費/原価区分
   */
  costTypeAC = ([
    {data: '', label: ''},
    {data: '1', label: '販売管理費'},
    {data: '2', label: '原価'}
  ]);

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
  ) {
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    // フォーム初期処理
    this.initForm();
    // ステートに応じた固定値設定
    this.setItemPermission();
  }

  /**
   * フォーム初期処理
   */
  initForm() {
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
      costType: [''],
      costDepPrjCode: [''],
      costDepPrjName: [''],
      searchDateFrom: Date,
      searchDateTo: Date,
      costSectionCode: [''],
      costSectionName: [''],
      holCompanyCode: this.sessionInfo.loginCompanyCode,
      holCompanyName: this.sessionInfo.loginCompanyName,
      holSectionCode: [''],
      holSectionName: [''],
      holSectionYear: [''],
      includeSectionHierarchyFlag: [false],
      holRepOfficeCode: [''],
      holRepOfficeName: [''],
      astCategoryCodeFld: [''],
      astCategoryNameFld: null,
      astNameFld: [''],
      astPrjCodeFld: [''],
      astPrjNameFld: [''],
      astCommentFld: [''],
      description: [''],
    });
  }

  /**
   * ステートに応じた固定値設定
   */
  setItemPermission() {
    if (this.sessionInfo.currentAccessLevel === this.accessLevelSection.ACCESS_LEVEL_SECTION_GENERAL) {
      this.searchForm.controls.apStaffCode.setValue(this.sessionInfo.loginUser.staffCode);
      this.searchForm.controls.apStaffName.setValue(this.sessionInfo.loginUser.name);
    }

    if (this.sessionInfo.currentAccessLevel === this.accessLevelSection.ACCESS_LEVEL_SECTION_GENERAL) {
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
            return i === 0 ? true : false;
          })
        );
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
            return i === 2 ? true : false;
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
            return i === 2 ? true : false;
          })
        );
        this.searchForm.controls.reportCompleteFlag.setValue(this.reportCompleteFlag.map((x, i) => {
            return i === 0 ? true : false;
          })
        );
        this.searchForm.controls.apGetType.setValue(this.apGetType.map((x, i) => {
            return i !== 2 ? true : false;
          })
        );
        this.searchForm.controls.addUpType.setValue(this.addUpType.map((x, i) => {
            return i !== 1 ? true : false;
          })
        );
        break;
      case 'AP_LICENSE':
        this.searchForm.controls.apStatus.disable();
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
            return i === 2 ? true : false;
          })
        );
    }
  }

  /**
   * 起票/申請者:社員番号
   */
  updateApStaff(data) {
    if (data) {
      this.searchForm.controls.apStaffCode.setValue(data.staffCode);
      this.searchForm.controls.apStaffName.setValue(data.name);
    } else {
      this.searchForm.controls.apStaffCode.setValue('');
      this.searchForm.controls.apStaffName.setValue('');
    }
  }

  /**
   * 案件グループ
   */
  updateAstGroup(data) {
    if (data) {
      this.searchForm.controls.apStaffCode.setValue(data.code);
      this.searchForm.controls.apStaffName.setValue(data.name);
    } else {
      this.searchForm.controls.apStaffCode.setValue('');
      this.searchForm.controls.apStaffName.setValue('');
    }
  }

  /**
   * 減価償却プロジェクト
   */
  updateProject(data) {
    if (data) {
      this.searchForm.controls.costDepPrjCode.setValue(data.code);
      this.searchForm.controls.costDepPrjName.setValue(data.name);
    } else {
      this.searchForm.controls.costDepPrjCode.setValue('');
      this.searchForm.controls.costDepPrjName.setValue('');
    }
  }

  /**
   * 資産計上部課
   */
  updateCostSection(data) {
    if (data) {
      this.searchForm.controls.costSectionCode.setValue(data.sectionCode);
      this.searchForm.controls.costSectionName.setValue(data.sectionName);
    } else {
      this.searchForm.controls.costSectionCode.setValue('');
      this.searchForm.controls.costSectionName.setValue('');
    }
    this.searchForm.controls.costCompanyCode.setValue(this.sessionInfo.loginCompanyCode);
    this.searchForm.controls.costSectionYear.setValue(this.sessionInfo.currentYear);
  }

  /**
   * 保有部署
   */
  updateHolSection(data) {
    if (data) {
      this.searchForm.controls.holSectionCode.setValue(data.sectionCode);
      this.searchForm.controls.holSectionName.setValue(data.sectionName);
    } else {
      this.searchForm.controls.holSectionCode.setValue('');
      this.searchForm.controls.holSectionName.setValue('');
    }
  }

  /**
   * 代表設置場所
   */
  updateHolRepOffice(data) {
    if (data) {
      this.searchForm.controls.holRepOfficeCode.setValue(data.code);
      this.searchForm.controls.holRepOfficeName.setValue(data.name);
    } else {
      this.searchForm.controls.holRepOfficeCode.setValue('');
      this.searchForm.controls.holRepOfficeName.setValue('');
    }
  }

  /**
   * 分類
   */
  updateAstCategoryCode(data) {
    if (data) {
      this.searchForm.controls.astCategoryCodeFld.setValue(data.code);
      this.searchForm.controls.astCategoryNameFld.setValue(data.name);
    } else {
      this.searchForm.controls.astCategoryCodeFld.setValue('');
      this.searchForm.controls.astCategoryNameFld.setValue('');
    }
  }

  /**
   * 資産プロジェクト
   */
  updateAstPrj(data) {
    if (data) {
      this.searchForm.controls.astPrjCodeFld.setValue(data.code);
      this.searchForm.controls.astPrjNameFld.setValue(data.name);
    } else {
      this.searchForm.controls.astPrjCodeFld.setValue('');
      this.searchForm.controls.astPrjNameFld.setValue('');
    }
  }

  /**
   * 隠しボタン
   */
  changeReminderFlag() {
    return this.searchForm.controls.reminderFlag.value[1];
  }

  /**
   * 検索条件クリア
   *
   * @return なし
   */
  resetSearch() {
    const searchForm = this.searchForm.getRawValue();
    Object.entries(searchForm).forEach(([key, value]) => {
      if (typeof value === 'number') {
        searchForm[key] = 0;
      } else if (Array.isArray(value)) {
        searchForm[key] = value.map((i) => i = false);
      } else {
        searchForm[key] = null;
        if (key === 'searchDateFrom' || key === 'searchDateTo') {
          searchForm[key] = Date;
        }
      }
    });

    this.searchForm.patchValue(searchForm);
    this.setItemPermission();
  }

  /**
   * 検索条件項目一覧の値取得
   *
   * @return なし
   */
  getSearchCondition() {
    const searchData = this.searchForm.getRawValue();
    searchData.apStatus = searchData.apStatus.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    searchData.apGetType = searchData.apGetType.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    searchData.addUpType = searchData.addUpType.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    searchData.reportCompleteFlag = searchData.reportCompleteFlag.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    searchData.reminderFlag = searchData.reminderFlag.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    searchData.apDateFrom = searchData.apDateFrom ? searchData.apDateFrom : null;
    searchData.apDateTo = searchData.apDateTo ? searchData.apDateTo : null;
    searchData.astCompletePlanDateFrom = searchData.astCompletePlanDateFrom ? searchData.astCompletePlanDateFrom : null;
    searchData.astCompletePlanDateTo = searchData.astCompletePlanDateTo ? searchData.astCompletePlanDateTo : null;
    searchData.getTotalAmountFrom = searchData.getTotalAmountFrom ? searchData.getTotalAmountFrom : null;
    searchData.getTotalAmountTo = searchData.getTotalAmountTo ? searchData.getTotalAmountTo : null;
    searchData.reminderDateFrom = searchData.reminderDateFrom ? searchData.reminderDateFrom : null;
    searchData.reminderDateTo = searchData.reminderDateTo ? searchData.reminderDateTo : null;
    searchData.oldVerFlag = searchData.oldVerFlag ? '1' : '0';
    searchData.specialApproveFlag = searchData.specialApproveFlag ? '1' : '0';
    searchData.includeSectionHierarchyFlag = searchData.includeSectionHierarchyFlag ? '1' : '0';
    (!this.applicationIdLike) ? searchData.applicationId = '' : searchData.applicationIdPlural = '';
    (!this.eappIdLike) ? searchData.eappId = '' : searchData.eappIdPlural = '';
    delete searchData.astCategoryNameFld;
    delete searchData.astPrjNameFld;

    return searchData;
  }
}
