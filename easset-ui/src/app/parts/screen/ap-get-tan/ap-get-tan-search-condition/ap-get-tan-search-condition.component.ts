import { Component, Input } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { SessionInfo } from 'src/app/models/session-info';

@Component({
  selector: 'app-ap-get-tan-search-condition',
  templateUrl: './ap-get-tan-search-condition.component.html',
  styleUrls: ['./ap-get-tan-search-condition.component.scss']
})
export class ApGetTanSearchConditionComponent {

  /**
   * ステート
   */
  statusAction = {
    AP_GET_TAN: 'AP_GET_TAN',
    AP_GET_INT: 'AP_GET_INT',
    AP_ASSET: 'AP_ASSET',
    AP_LICENSE: 'AP_LICENSE',
    LICENSE: 'LICENSE',
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
   * 探すフォーム
   */
  searchForm: FormGroup;

  /**
   * 取得フォーム
   */
  getForm: FormGroup;

  /**
   * 経費負担フォーム
   */
  expenseForm: FormGroup;

  /**
   * 備考フォーム
   */
  remarkForm: FormGroup;

  /**
   * 保有・設置フォーム
   */
  possessionForm: FormGroup;

  /**
   * ライセンス明細フォーム
   */
  licenseForm: FormGroup;

  /**
   * 資産(機器)明細フォーム
   */
  assetStatementForm: FormGroup;

  /**
   * 故障交換対象の資産(機器)明細フォーム
   */
  assetReplacedForm: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * 要素の隠し変数
   */
  checkApplicationId = true;
  checkEappId = true;
  checkPurEstimateNumber = true;
  checkLeReEstimateNumber = true;
  checkFailureAssetId = true;
  isShow = true;

  /**
   * 読取専用
   */
  readOnlyStaffCode = false;

  /**
   * 検索条件
   */
  searchParam: ApGetTanSC;

  /**
   * ステータス
   */
  apStatus: string[] = ['未申請', '申請中', '承認済', '未申請(再)', '却下', '取下げ'];

  /**
   * 取得形態
   */
  apGetType: string[] = ['購入(有形固定資産/備品)', 'リース', 'レンタル', '故障交換', '借受', '譲受', '貸出商品', '納入前商品'];

  /**
   * eAssetレンタル発注
   */
  reoOrderFlag: string[] = ['未発注', '発注済'];

  /**
   * クラウド区分
   */
  getItemCloudTypeAC = ([
    { data: '', label: '' },
    { data: '1', label: 'クラウド以外' },
    { data: '2', label: 'クラウド' }
  ]);

  /**
   * 情報システム部配備
   */
  getSystemSectionDeployFlagAC = ([
    { data: '', label: '' },
    { data: '0', label: '情報システム部配備以外' },
    { data: '1', label: '情報システム部配備' }
  ]);

  /**
   * 販売管理費原価区分
   */
  costTypeAC = ([
    { data: '', label: '' },
    { data: '1', label: '販売管理費' },
    { data: '2', label: '原価' }
  ]);

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
  ) {
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
      excludeCompleteFlag: [''],
      reoOrderFlag: this.fb.array(this.reoOrderFlag.map((x) => false)),
      reoDisuseFlag: [false],
      lineExistsType: [''],
    });
    this.getForm = this.fb.group({
      getItemName: [''],
      getItemCloudType: [''],
      getItemGroupCode: [''],
      getItemGroupName: [''],
      getDeliveryDateFrom: [''],
      getDeliveryDateTo: [''],
      getSystemSectionDeployFlag: [''],
      getPurCompanyCode: [''],
      getPurCompanyName: [''],
      getPurEstimateNumber: [''],
      getPurEstimateNumberPlural: [''],
      getLeReCompanyCode: [''],
      getLeReCompanyName: [''],
      getLeReEstimateNumber: [''],
      getLeReEstimateNumberPlural: [''],
      getLeEappId: [''],
      getTotalAmountFrom: [0],
      getTotalAmountTo: [0],
    });
    this.expenseForm = this.fb.group({
      costType: [''],
      costDepPrjCode: [''],
      costDepPrjName: [''],
      costDepPrjUndecidedFlag: [false],
    });
    this.remarkForm = this.fb.group({
      description: [''],
    });
    this.possessionForm = this.fb.group({
      holCompanyCode: this.sessionInfo.loginCompanyCode,
      holCompanyName: this.sessionInfo.loginCompanyName,
      holSectionCode: [''],
      holSectionName: [''],
      holSectionYear: [''],
      includeSectionHierarchyFlag: [false],
      holStaffCode: [''],
      holStaffName: [''],
      holOfficeCode: [''],
      holOfficeName: [''],
      holOfficeFloor: [''],
    });
    this.assetStatementForm = this.fb.group({
      astName: [''],
      astMakerCode: [''],
      astMakerName: [''],
      astMakerModel: [''],
      astPrjCode: [''],
      astPrjName: [''],
    });
    this.licenseForm = this.fb.group({
      softwareMakerId: [''],
      softwareMakerName: [''],
      softwareId: [''],
      softwareName: [''],
    });
    this.assetReplacedForm = this.fb.group({
      failureAssetId: [''],
      failureAssetIdPlural: [''],
    });
  }

  /**
   * 初期処理
   */
  init() {
    if (this.accessLevel === this.accessLevelSection.ACCESS_LEVEL_SECTION_GENERAL) {
      this.searchForm.controls.apStaffCode.setValue(this.sessionInfo.loginUser.staffCode);
      this.searchForm.controls.apStaffName.setValue(this.sessionInfo.loginUser.name);
      this.readOnlyStaffCode = true;
    }

    /**
     * ステータスによる分権
     */
    switch (this.currentState) {
      case this.statusAction.AP_GET_TAN:
        break;
      case this.statusAction.AP_GET_INT:
        this.searchForm.controls.apGetType.disable();
        this.searchForm.controls.apStatus.disable();
        this.searchForm.controls.excludeCompleteFlag.disable();
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
          return i === 2 ? true : false;
        }));
        this.searchForm.controls.apGetType.setValue(this.apGetType.map((x, i) => {
          return i === 1 || i === 2 ? true : false;
        }));
        this.searchForm.controls.excludeCompleteFlag.setValue(true);
        this.searchForm.controls.lineExistsType.setValue('1');
        break;
      case this.statusAction.AP_ASSET:
          this.searchForm.controls.apStatus.disable();
          this.searchForm.controls.excludeCompleteFlag.disable();
          this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
            return i === 2 ? true : false;
          }));
          this.searchForm.controls.excludeCompleteFlag.setValue(true);
          this.searchForm.controls.lineExistsType.setValue('1');
          break;
      case this.statusAction.AP_LICENSE:
      case this.statusAction.LICENSE:
        this.searchForm.controls.apStatus.disable();
        this.searchForm.controls.excludeCompleteFlag.disable();
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
          return i === 2 ? true : false;
        }));
        this.searchForm.controls.excludeCompleteFlag.setValue(true);
        this.searchForm.controls.lineExistsType.setValue('2');
        break;
    }
  }

  /**
   * リンクボタンクリック用ボタンハンドラー
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
      case 'getPurEstimateNumber':
        this.checkPurEstimateNumber = !this.checkPurEstimateNumber;
        this.getForm.controls.getPurEstimateNumber.setValue('');
        this.getForm.controls.getPurEstimateNumberPlural.setValue('');
        break;
      case 'getLeReEstimateNumber':
        this.checkLeReEstimateNumber = !this.checkLeReEstimateNumber;
        this.getForm.controls.getLeReEstimateNumber.setValue('');
        this.getForm.controls.getLeReEstimateNumberPlural.setValue('');
        break;
      case 'failureAssetId':
        this.checkFailureAssetId = !this.checkFailureAssetId;
        this.assetReplacedForm.controls.failureAssetId.setValue('');
        this.assetReplacedForm.controls.failureAssetIdPlural.setValue('');
    }
  }

  /**
   * 部署選択後処理
   */
  selectSoftwareData(data) {
    if (data !== null) {
      this.licenseForm.controls.softwareMakerId.setValue(data.value1);
      this.licenseForm.controls.softwareMakerName.setValue(data.value2);
    } else {
      this.licenseForm.controls.softwareMakerId.setValue('');
      this.licenseForm.controls.softwareMakerName.setValue('');
    }
  }

  /**
   * 部署選択後処理
   */
  selectSoftwareMakerData() {
    this.licenseForm.controls.softwareId.setValue('');
    this.licenseForm.controls.softwareName.setValue('');
  }

  /**
   * 部署選択後処理
   */
  selectCodeName(data) {
    if (data !== null) {
      this.isShow = data.isShow;
    }
  }

  /**
   * 部署選択後処理
   */
  selectPurCompanyName(data) {
    this.getForm.controls.getPurCompanyName.setValue(data.name);
  }

  /**
   * 部署選択後処理
   */
  selectHolStaffData(data) {
    if (data !== null) {
      this.possessionForm.controls.holSectionCode.setValue(data.value1);
      this.possessionForm.controls.holSectionName.setValue(data.value2);
    } else {
      this.possessionForm.controls.holSectionCode.setValue('');
      this.possessionForm.controls.holSectionName.setValue('');
    }
  }

  /**
   * 部署選択後処理
   */
  selectSectionData() {
    this.possessionForm.controls.holStaffCode.setValue('');
    this.possessionForm.controls.holStaffName.setValue('');
  }

  /**
   * データ取得
   */
  getSearchCondition() {
    const searchForm = this.searchForm.getRawValue();
    const getForm = this.getForm.getRawValue();
    const expenseForm = this.expenseForm.getRawValue();
    const remarkForm = this.remarkForm.getRawValue();
    const possessionForm = this.possessionForm.getRawValue();
    const assetStatementForm = this.assetStatementForm.getRawValue();
    const licenseForm = this.licenseForm.getRawValue();
    const assetReplacedForm = this.assetReplacedForm.getRawValue();
    this.searchParam = Object.assign({}, searchForm, getForm, expenseForm, remarkForm, possessionForm, assetStatementForm, licenseForm, assetReplacedForm);
    this.searchParam.apStatus = searchForm.apStatus.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    this.searchParam.apGetType = searchForm.apGetType.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    this.searchParam.reoOrderFlag = searchForm.reoOrderFlag.map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ');
    this.searchParam.apDateFrom = searchForm.apDateFrom ? searchForm.apDateFrom : null;
    this.searchParam.apDateTo = searchForm.apDateTo ? searchForm.apDateTo : null;
    this.searchParam.getTotalAmountFrom = getForm.getTotalAmountFrom ? getForm.getTotalAmountFrom : null;
    this.searchParam.getTotalAmountTo = getForm.getTotalAmountTo ? getForm.getTotalAmountTo : null;
    this.searchParam.getDeliveryDateFrom = getForm.getDeliveryDateFrom ? getForm.getDeliveryDateFrom : null;
    this.searchParam.getDeliveryDateTo = getForm.getDeliveryDateTo ? getForm.getDeliveryDateTo : null;
    this.searchParam.excludeCompleteFlag = searchForm.excludeCompleteFlag ? '1' : '0';
    this.searchParam.reoDisuseFlag = searchForm.reoDisuseFlag ? '1' : '';
    this.searchParam.costDepPrjUndecidedFlag = expenseForm.costDepPrjUndecidedFlag ? '1' : '0';
    this.searchParam.includeSectionHierarchyFlag = possessionForm.includeSectionHierarchyFlag ? '1' : '0';
    this.searchParam.holOfficeFloor = possessionForm.holOfficeFloor ? possessionForm.holOfficeFloor : null;
    delete this.searchParam.getPurCompanyCode;
    delete this.searchParam.holSectionYear;
    return this.searchParam;
  }
}
