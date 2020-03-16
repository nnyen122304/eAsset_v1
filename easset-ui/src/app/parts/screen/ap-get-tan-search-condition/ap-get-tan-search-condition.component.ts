import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { SessionInfo } from 'src/app/models/session-info';

@Component({
  selector: 'app-ap-get-tan-search-condition',
  templateUrl: './ap-get-tan-search-condition.component.html',
  styleUrls: ['./ap-get-tan-search-condition.component.scss']
})
export class ApGetTanSearchConditionComponent implements OnInit {

  /**
   * vi phân quyền
   */
  @Input() currentState: string;

  /**
   * form 探す
   */
  searchForm: FormGroup;

  /**
   * form 取得
   */
  getForm: FormGroup;

  /**
   * form 経費負担
   */
  expenseForm: FormGroup;

  /**
   * form 備考
   */
  remarkForm: FormGroup;

  /**
   * form 保有・設置
   */
  possessionForm: FormGroup;

  /**
   * form ライセンス明細
   */
  licenseForm: FormGroup;

  /**
   * form 資産(機器)明細
   */
  assetStatementForm: FormGroup;

  /**
   * form 故障交換対象の資産(機器)明細
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
   * vi change input type
   */
  checkApplicationId = true;
  checkEappId = true;
  checkPurEstimateNumber = true;
  checkLeReEstimateNumber = true;
  checkFailureAssetId = true;

  /**
   * vi isShow
   */
  isShow = true;

  /**
   * vi readOnly staff code
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
  ) {}

  ngOnInit() {
    this.init();
  }

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

    if (this.accessLevel === 'C') {
      this.searchForm.controls.apStaffCode.setValue(this.sessionInfo.loginUser.staffCode);
      this.searchForm.controls.apStaffName.setValue(this.sessionInfo.loginUser.name);
      this.readOnlyStaffCode = true;
    }

    switch (this.currentState) {
      case 'AP_GET_TAN':
        break;
      case 'AP_GET_INT':
        this.searchForm.controls.apGetType.disable();
        this.searchForm.controls.apStatus.disable();
        this.searchForm.controls.excludeCompleteFlag.disable();
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
          if (i === 2) {
            return true;
          }
          return false;
        })
        );
        this.searchForm.controls.apGetType.setValue(this.apGetType.map((x, i) => {
          if (i === 1 || i === 2) {
            return true;
          }
          return false;
        })
        );
        this.searchForm.controls.excludeCompleteFlag.setValue(true);
        this.searchForm.controls.lineExistsType.setValue('1');
        break;
      case 'AP_ASSET':
          this.searchForm.controls.apStatus.disable();
          this.searchForm.controls.excludeCompleteFlag.disable();
          this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
            if (i === 2) {
              return true;
            }
            return false;
          })
          );
          this.searchForm.controls.excludeCompleteFlag.setValue(true);
          this.searchForm.controls.lineExistsType.setValue('1');
          break;
      case 'AP_LICENSE':
      case 'LICENSE':
        this.searchForm.controls.apStatus.disable();
        this.searchForm.controls.excludeCompleteFlag.disable();
        this.searchForm.controls.apStatus.setValue(this.apStatus.map((x, i) => {
          if (i === 2) {
            return true;
          }
          return false;
        })
        );
        this.searchForm.controls.excludeCompleteFlag.setValue(true);
        this.searchForm.controls.lineExistsType.setValue('2');
        break;
    }

  }

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

  selectSoftwareData(data) {
    if (data !== null) {
      this.licenseForm.controls.softwareMakerId.setValue(data.value1);
      this.licenseForm.controls.softwareMakerName.setValue(data.value2);
    } else {
      this.licenseForm.controls.softwareMakerId.setValue('');
      this.licenseForm.controls.softwareMakerName.setValue('');
    }
  }

  selectSoftwareMakerData(data) {
    this.licenseForm.controls.softwareId.setValue('');
    this.licenseForm.controls.softwareName.setValue('');
  }

  selectCodeName(data) {
    if (data !== null) {
      this.isShow = data.isShow;
    }
  }

  selectPurCompanyName(data) {
    if (data !== null) {
      this.getForm.controls.getPurCompanyName.setValue(data._data.name);
    }
  }

  setSearch() {
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
    this.searchParam.reoDisuseFlag = searchForm.reoDisuseFlag ? '1' : '0';
    this.searchParam.costDepPrjUndecidedFlag = expenseForm.costDepPrjUndecidedFlag ? '1' : '0';
    this.searchParam.includeSectionHierarchyFlag = possessionForm.includeSectionHierarchyFlag ? '1' : '0';
    this.searchParam.holOfficeFloor = possessionForm.holOfficeFloor ? possessionForm.holOfficeFloor : null;
    delete this.searchParam.getPurCompanyCode;
    delete this.searchParam.holSectionYear;
    return this.searchParam;
  }
}
