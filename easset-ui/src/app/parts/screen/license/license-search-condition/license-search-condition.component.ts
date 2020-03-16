import { Component, Input, ViewChild, OnChanges} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SessionInfo } from 'src/app/models/session-info';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';
import { MasterService } from 'src/app/services/api/master.service';
import { DownloadOptionPropComponent } from 'src/app/parts/option/download-option-prop/download-option-prop.component';
import { SessionService } from 'src/app/services/session.service';
import { ComLicenseSearchConditionComponent } from '../com-license-search-condition/com-license-search-condition.component';

@Component({
  selector: 'app-license-search-condition',
  templateUrl: './license-search-condition.component.html',
  styleUrls: ['./license-search-condition.component.scss']
})
export class LicenseSearchConditionComponent implements  OnChanges {

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
    isShowAllCompanyFlag: true,
  };

  /**
   * 要素の隠し変数
   */
  isLicense = true;
  isContractNum = true;
  isContractEda = true;
  isShisanNum = true;
  isAssetId = true;
  isGetApplicationId = true;
  isRegistApplicationId = true;
  isSearchScopeType: boolean;
  labelSearchScopeType = '自分が申請者となっているライセンス';

  /**
   * アクセスレベル
   */
  statusAction = {
    LICENSE_SEARCH: 'LICENSE_SEARCH',
    LICENSE_DELETE: 'LICENSE_DELETE',
    AP_CHANGE: 'AP_CHANGE',
    TERM_LICENSE: 'TERM_LICENSE',
    LICENSE_UPGRADE_FROM_ALLOC: 'LICENSE_UPGRADE_FROM_ALLOC',
    LICENSE_UPGRADE_TO_ALLOC: 'LICENSE_UPGRADE_TO_ALLOC',
    LICENSE_ALLOC: 'LICENSE_ALLOC',
    LICENSE_REPORT_ALLOC: 'LICENSE_REPORT_ALLOC',
    LICENSE_REPORT_UPGRADE: 'LICENSE_REPORT_UPGRADE',
  };

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
   * 現在の状態
   */
  @Input() currentState: string;

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
    private masterService: MasterService,
    private sessionService: SessionService,
  ) {
  }

  /**
   * 親マスタ標準値を設定する
   */
  ngOnChanges() {
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
      searchScopeType: [null],
      licenseId: [''],
      licenseIdPlural: [''],
      contractNum: [''],
      contractNumPlural: [''],
      contractEda: [''],
      contractEdaPlural: [''],
      shisanNum: [''],
      shisanNumPlural: [''],
      relationShisanNumFlag: [false],
      apStaffCode: [''],
      apStaffName: [''],
      assetId: [''],
      assetIdPlural: [''],
      getApplicationId: [''],
      getApplicationIdPlural: [''],
      registApplicationId: [''],
      registApplicationIdPlural: [''],
      apDateFrom: [''],
      apDateTo: [''],
      deleteFlag: [false],
    });

    if (this.accessLevel === this.accessLevelSection.ACCESS_LEVEL_SECTION_GENERAL) {
      this.labelSearchScopeType = '自分が申請者となっているライセンス';
    } else if (this.accessLevel === this.accessLevelSection.ACCESS_LEVEL_SECTION_MANAGER) {
      this.labelSearchScopeType = '自分の管轄部署が保有（使用許諾）部署に設定されているライセンス';
    }

    if ((this.accessLevel === this.accessLevelSection.ACCESS_LEVEL_SECTION_GENERAL || this.accessLevel === this.accessLevelSection.ACCESS_LEVEL_SECTION_MANAGER) && this.currentState !== 'LICENSE_ALLOC') {
      if (this.searchForm.controls.searchScopeType.value === null) {
        this.searchForm.controls.searchScopeType.setValue('1');
        // 他会社を検索不可にする
        this.statesStatus.allCompanyFlagSelected = false;
        this.statesStatus.allCompanyFlagEditLock = true;
      }
      this.isSearchScopeType = false;
    } else {
      if (this.searchForm.controls.searchScopeType.value === null) {
        this.searchForm.controls.searchScopeType.setValue('2');
        // 他会社を検索可にする
        this.statesStatus.allCompanyFlagEditLock = false;
      }
      this.isSearchScopeType = true;
    }
    // ステートに応じた固定値セット
    this.changeStateStatus(this.currentState);
  }

  /**
   * ステータスによる分権
   */
  changeStateStatus(status) {
    switch (status) {
      // ライセンス照会/修正
      case this.statusAction.LICENSE_SEARCH:
        break;
      // ライセンス抹消
      case this.statusAction.LICENSE_DELETE:
        this.searchForm.controls.deleteFlag.disable();
        this.searchForm.controls.searchScopeType.disable();
        this.searchForm.controls.searchScopeType.setValue('1');
        this.statesStatus.allCompanyFlagEditLock = true;
        break;
      // 移動申請(移動対象選択)
      case this.statusAction.AP_CHANGE:
        this.searchForm.controls.deleteFlag.disable();
        this.searchForm.controls.searchScopeType.disable();
        this.statesStatus.allCompanyFlagEditLock = true;
        this.searchForm.controls.relationShisanNumFlag.setValue(true);  // 関連資産も同時に検索する
        break;
      // タームライセンス情報
      case this.statusAction.TERM_LICENSE:
        this.searchForm.controls.searchScopeType.disable();
        this.statesStatus.licTermFlag = '1';
        this.statesStatus.isLicTermFlagEditLock = true;
        this.statesStatus.allCompanyFlagEditLock = true;
        break;
      // アップグレード割当：割当元
      case this.statusAction.LICENSE_UPGRADE_FROM_ALLOC:
        this.searchForm.controls.deleteFlag.disable();
        this.searchForm.controls.searchScopeType.disable();
        this.statesStatus.allCompanyFlagEditLock = true;
        break;
      // アップグレード割当:割当先
      case this.statusAction.LICENSE_UPGRADE_TO_ALLOC:
        this.searchForm.controls.deleteFlag.disable();
        this.searchForm.controls.searchScopeType.disable();
        this.statesStatus.licUpgradeFlag = '1';  // ステートに応じた固定値セット
        this.statesStatus.isLicUpgradeFlagEditLock = true;
        this.statesStatus.allCompanyFlagEditLock = true;
        break;
      // ライセンス割当
      case this.statusAction.LICENSE_ALLOC:
      // 割当情報
      case this.statusAction.LICENSE_REPORT_ALLOC:
        if (status === this.statusAction.LICENSE_ALLOC) {
          this.searchForm.controls.searchScopeType.disable();
          this.searchForm.controls.searchScopeType.setValue('2');
        }
        this.searchForm.controls.deleteFlag.disable();
        this.statesStatus.isLicLicenseTypePluralFI = true;
        // 割当可能ライセンス形態取得  // value1   // value2 = 割当可能
        const param = [null, '1'];
        this.masterService.getCodeNameList('LICENSE_TYPE', null, null, param).subscribe(data => {
          if (data.length > 0) {
            if (data[0].categoryCode === 'LICENSE_TYPE') {
              data.map(item => {
                this.statesStatus.licLicenseType = item.value2;
                this.statesStatus.licLicenseTypePlural = item.value1;
              });
            }
          }
        });
        break;
      // アップグレード情報
      case this.statusAction.LICENSE_REPORT_UPGRADE:
        this.searchForm.controls.deleteFlag.disable();
        break;
    }
  }

  /**
   * 編集による値変更ハンドラー（詳細項目共通）
   */
  selectScopeType() {
    const select = this.searchForm.controls.searchScopeType.value;
    if (select === '1') {
      this.statesStatus.allCompanyFlagSelected = false;
      this.statesStatus.allCompanyFlagEditLock = true;
    } else {
      this.statesStatus.allCompanyFlagEditLock = false;
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
    this.searchParamSC.relationShisanNumFlag = searchForm.relationShisanNumFlag === true ? '1' : '0';
    this.searchParamSC.registDateFrom = searchForm.apDateFrom ? dataSearch.apDateFrom : null;
    this.searchParamSC.registDateTo = searchForm.apDateTo ? dataSearch.apDateTo : null;
    this.searchParamSC.deleteFlag = searchForm.deleteFlag === true ? '1' : '0';
    this.searchParamSC.licAmountFrom = dataSearch.licAmountFrom !== 0 ? dataSearch.licAmountFrom : null;
    this.searchParamSC.licAmountTo = dataSearch.licAmountTo !== 0 ? dataSearch.licAmountTo : null;
    this.searchParamSC.trmEndDateFrom = dataSearch.trmEndDateFrom ? dataSearch.trmEndDateFrom : null;
    this.searchParamSC.trmEndDateTo = dataSearch.trmEndDateTo ? dataSearch.trmEndDateTo : null;
    this.searchParamSC.allCompanyFlag = dataSearch.allCompanyFlag === true ? '1' : '0';
    this.searchParamSC.includeSectionHierarchyFlag = dataSearch.includeSectionHierarchyFlag === true ? '1' : '0';
    delete this.searchParamSC.apStaffName;
    delete this.searchParamSC.apDateFrom;
    delete this.searchParamSC.apDateTo;
    delete this.searchParamSC.licVolumeTypeName;
    delete this.searchParamSC.holSectionYear;
    delete this.searchParamSC.licLicenseTypePlural;
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
      case 'contractNum':
        this.isContractNum = !this.isContractNum;
        this.searchForm.controls.contractNum.setValue('');
        break;
      case 'contractNumPlural':
        this.isContractNum = !this.isContractNum;
        this.searchForm.controls.contractNumPlural.setValue('');
        break;
      case 'contractEda':
        this.isContractEda = !this.isContractEda;
        this.searchForm.controls.contractEda.setValue('');
        break;
      case 'contractEdaPlural':
        this.isContractEda = !this.isContractEda;
        this.searchForm.controls.contractEdaPlural.setValue('');
        break;
      case 'shisanNum':
        this.isShisanNum = !this.isShisanNum;
        this.searchForm.controls.shisanNum.setValue('');
        break;
      case 'shisanNumPlural':
        this.isShisanNum = !this.isShisanNum;
        this.searchForm.controls.shisanNumPlural.setValue('');
        break;
      case 'assetId':
        this.isAssetId = !this.isAssetId;
        this.searchForm.controls.assetId.setValue('');
        break;
      case 'assetIdPlural':
        this.isAssetId = !this.isAssetId;
        this.searchForm.controls.assetIdPlural.setValue('');
        break;
      case 'getApplicationId':
        this.isGetApplicationId = !this.isGetApplicationId;
        this.searchForm.controls.getApplicationId.setValue('');
        break;
      case 'getApplicationIdPlural':
        this.isGetApplicationId = !this.isGetApplicationId;
        this.searchForm.controls.getApplicationIdPlural.setValue('');
        break;
      case 'registApplicationId':
        this.isRegistApplicationId = !this.isRegistApplicationId;
        this.searchForm.controls.registApplicationId.setValue('');
        break;
      case 'registApplicationIdPlural':
        this.isRegistApplicationId = !this.isRegistApplicationId;
        this.searchForm.controls.registApplicationIdPlural.setValue('');
        break;
    }
  }
}
