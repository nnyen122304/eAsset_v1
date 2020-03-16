import { Component, OnInit, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SessionInfo } from 'src/app/models/session-info';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';
import { DownloadOptionPropComponent } from 'src/app/parts/option/download-option-prop/download-option-prop.component';
import { SessionService } from 'src/app/services/session.service';
import { ComLicenseSearchConditionComponent } from '../com-license-search-condition/com-license-search-condition.component';

@Component({
  selector: 'app-ap-license-search-condition',
  templateUrl: './ap-license-search-condition.component.html',
  styleUrls: ['./ap-license-search-condition.component.scss']
})
export class ApLicenseSearchConditionComponent implements OnInit {

  /**
   * 検索条件フォーム
   */
  searchForm: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * ログイン社員番号
   */
  loginStaffCode: string;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * 選択されているメニュー
   */
  currentMenuId: string;

  /**
   * 検索条件
   */
  searchParamSC: LicenseSC = new LicenseSC();

  /**
   * 申請書ステータス
   */
  apStatus: string[] = ['未申請', '申請中', '登録完了', '未申請(再)'];

  /**
   * 権限設定オブジェクト
   */
  statesStatus = {
    searchScopeTypeEditLock: false,
    allCompanyFlagSelected: false,
    allCompanyFlagEditLock: false,
    licTermFlag: '',
    isLicTermFlagEditLock: false,
    licUpgradeFlag: '',
    isLicUpgradeFlagEditLock: false,
    isLicLicenseTypePluralFI: false,
    licLicenseType: '',
    licLicenseTypePlural: '',
    isShowAllCompanyFlag: false,
  };

  /**
   * 要素の隠し変数
   */
  isLicense = true;
  isEappId = true;
  isGetApplicationId = true;
  isShow = true;
  isReadOnly = false;

  /**
   * アクセス権限
   */
  accessLevelSection = {
    ACCESS_LEVEL_SECTION_GENERAL: 'C',
    ACCESS_LEVEL_SECTION_MANAGER: 'B',
    ACCESS_LEVEL_ADMIN_COMPANY: 'A',
    ACCESS_LEVEL_ADMIN_SYSTEM: 'S',
  };

  /**
   * コンポーネントの参照
   */
  @ViewChild(DownloadOptionPropComponent, null) popupDownload: DownloadOptionPropComponent;

  /**
   * コンポーネントの参照
   */
  @ViewChild(ComLicenseSearchConditionComponent) comLicSearchCondition: ComLicenseSearchConditionComponent;
  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
  ) {
  }

  /**
   * コンポネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.currentMenuId = this.sessionInfo.currentMenuId;
    this.initChangeCondition();
  }

  /**
   * 初期処理
   */
  initChangeCondition() {
    this.searchForm = this.fb.group({
      licenseId: [''],
      licenseIdPlural: [''],
      eappId: [''],
      eappIdPlural: [''],
      apStatus: this.fb.array(this.apStatus.map((x) => false)),
      apDateFrom: [''],
      apDateTo: [''],
      apStaffCode: [''],
      apStaffName: [''],
      getApplicationId: [''],
      getApplicationIdPlural: [''],
      registDateFrom: [''],
      registDateTo: [''],
      notCompleteFlag: [false],
    });

    if (this.accessLevel === this.accessLevelSection.ACCESS_LEVEL_SECTION_GENERAL) {
      this.isShow = false;
      this.isReadOnly = true;
      this.searchForm.controls.apStaffCode.setValue(this.sessionInfo.loginUser.staffCode);
      this.searchForm.controls.apStaffName.setValue(this.sessionInfo.loginUser.name);
    }
  }

  /**
   * 検索をリセット
   */
  resetSearch() {
    this.initChangeCondition();
    this.comLicSearchCondition.init();
  }

  /**
   * 検索処理
   */
  getSearch() {
    const dataSearch = this.comLicSearchCondition.getForm();
    const searchForm = this.searchForm.getRawValue();
    this.searchParamSC = Object.assign({}, searchForm, dataSearch);
    this.searchParamSC.apDateFrom = searchForm.apDateFrom ? dataSearch.apDateFrom : null;
    this.searchParamSC.apDateTo = searchForm.apDateTo ? dataSearch.apDateTo : null;
    this.searchParamSC.registDateFrom = searchForm.registDateFrom ? dataSearch.registDateFrom : null;
    this.searchParamSC.registDateTo = searchForm.registDateTo ? dataSearch.registDateTo : null;
    this.searchParamSC.notCompleteFlag = searchForm.notCompleteFlag === true ? '1' : '0';
    this.searchParamSC.licAmountFrom = dataSearch.licAmountFrom !== 0 ? dataSearch.licAmountFrom : null;
    this.searchParamSC.licAmountTo = dataSearch.licAmountTo !== 0 ? dataSearch.licAmountTo : null;
    this.searchParamSC.trmEndDateFrom = dataSearch.trmEndDateFrom ? dataSearch.trmEndDateFrom : null;
    this.searchParamSC.trmEndDateTo = dataSearch.trmEndDateTo ? dataSearch.trmEndDateTo : null;
    this.searchParamSC.allCompanyFlag = dataSearch.allCompanyFlag === true ? '1' : '0';
    this.searchParamSC.includeSectionHierarchyFlag = dataSearch.includeSectionHierarchyFlag === true ? '1' : '0';
    this.searchParamSC.apStatus = searchForm.apStatus.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    delete this.searchParamSC.licenseIdPlural;
    delete this.searchParamSC.eappIdPlural;
    delete this.searchParamSC.licVolumeTypeName;
    delete this.searchParamSC.holSectionYear;
    delete this.searchParamSC.licLicenseTypePlural;
    this.searchParamSC.searchScopeType = '1';
    if (this.accessLevel === this.accessLevelSection.ACCESS_LEVEL_ADMIN_SYSTEM || this.accessLevel === this.accessLevelSection.ACCESS_LEVEL_ADMIN_COMPANY) {
      this.searchParamSC.searchScopeType = '2';
    }
    return this.searchParamSC;
  }

  /**
   * リンクボタンクリック用ボタンハンドラー
   */
  linkBtnClickEvent(data) {
    switch (data) {
      case 'licenseId':
        this.isLicense = !this.isLicense;
        this.searchForm.controls.licenseId.setValue('');
        break;
      case 'licenseIdPlural':
        this.isLicense = !this.isLicense;
        this.searchForm.controls.licenseIdPlural.setValue('');
        break;
      case 'eappId':
        this.isEappId = !this.isEappId;
        this.searchForm.controls.eappId.setValue('');
        break;
      case 'eappIdPlural':
        this.isEappId = !this.isEappId;
        this.searchForm.controls.eappIdPlural.setValue('');
        break;
      case 'getApplicationId':
        this.isGetApplicationId = !this.isGetApplicationId;
        this.searchForm.controls.getApplicationId.setValue('');
        break;
      case 'getApplicationIdPlural':
        this.isGetApplicationId = !this.isGetApplicationId;
        this.searchForm.controls.getApplicationIdPlural.setValue('');
        break;
    }
  }
}
