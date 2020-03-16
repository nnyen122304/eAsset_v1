import { Component, Input, OnChanges, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { CodeNameSelectionComponent } from 'src/app/parts/lov-input/code-name-selection/code-name-selection.component';
import { MasterService } from 'src/app/services/api/master.service';
import { CodeNameEx } from 'src/app/models/api/code-name-ex';
import { ComAssetSearchConditionComponent } from '../com-asset-search-condition/com-asset-search-condition.component';
import { SessionInfo } from 'src/app/models/session-info';
import { CodeName } from 'src/app/models/api/code-name';

@Component({
  selector: 'app-asset-search-condition',
  templateUrl: './asset-search-condition.component.html',
  styleUrls: ['./asset-search-condition.component.scss'],
})
export class AssetSearchConditionComponent implements OnChanges {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * FormSearchCondition
   */
  formSearchScopeTypeLayout: FormGroup;

  /**
   * FormSearchCondition
   */
  formSearchCondition: FormGroup;

  /**
   * Label searchScopeTypeLayout
   */
  arrLabel = [
    {title: '自分が使用者となっている資産(機器)', value: true},
    {title: '全ての資産(機器)', value: false},
  ];

  /**
   * Status menu
   */
  @Input() currentState: string;

  /**
   * リモートオブジェクト定義
   */
  masterRmtObj: CodeNameEx;

  /**
   * 利用可能会社一覧
   */
  usableCompanyList: CodeNameEx[] = [];

  /**
   * Show/hidden layout
   */
  compVisible = false;

  /**
   * Hidden input
   */
  isAssetId = true;
  isContractNum = true;
  isContractEda = true;
  isShisanNum = true;
  isGetApplicationId = true;
  isRegistApplicationId = true;

  /**
   * Label search
   */
  searchScopeType1Label = '';

  /**
   * Hidden form search scope type
   */
  isSearchScopeTypeShow = true;

  /**
   * CodeNameSelectionComponent Ref
   */
  @ViewChild(CodeNameSelectionComponent, null)
  popupStatus: CodeNameSelectionComponent;

  /**
   * ComAssetSearchConditionComponent Ref
   */
  @ViewChild(ComAssetSearchConditionComponent, null) comAssetSearchConditionComponent: ComAssetSearchConditionComponent;

  constructor(
    private fb: FormBuilder,
    private masterService: MasterService,
    private sessionService: SessionService,
  ) {
  }

  /**
   * コンポーネント初期処理
   */
  ngOnChanges() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.initChild();
    this.fsStateStatus(this.currentState);
  }

  /**
   * 子画面初期化処理
   */
  initChild() {
    this.initFormSearchScopeTypeLayout();
    this.initFormSearchCondition();
    this.comAssetSearchConditionComponent.initFormComSearchCondition();
    this.getCodeNamelist('ASSET_MANAGE_TYPE', null, null, null);
    this.clearSearchCondition();
  }

  /**
   * 子画面切替
   */
  changeCondition() {
    if (
      this.sessionInfo.currentAccessLevel === 'C' ||
      this.sessionInfo.currentAccessLevel === 'B'
    ) {
      if (this.sessionInfo.currentAccessLevel === 'C') {
        this.searchScopeType1Label = '自分が使用者となっている資産(機器)';
      } else if (this.sessionInfo.currentAccessLevel === 'B') {
        this.searchScopeType1Label =
          '自分の管轄部署が保有（使用）部署に設定されている資産(機器)';
      }
      if (
        this.formSearchScopeTypeLayout.controls.searchScopeType.value === null
      ) {
        this.formSearchScopeTypeLayout.controls.searchScopeType.setValue('1');
        this.comAssetSearchConditionComponent.comAllCompanyFlagSelected = false;
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = true;
      }
      this.isSearchScopeTypeShow = true;
    } else {
      if (
        this.formSearchScopeTypeLayout.controls.searchScopeType.value === null
      ) {
        this.formSearchScopeTypeLayout.controls.searchScopeType.setValue('2');
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = false;
      }
      this.isSearchScopeTypeShow = false;
    }
  }

  /**
   * Create form search scope layout
   */
  initFormSearchScopeTypeLayout() {
    this.formSearchScopeTypeLayout = this.fb.group({
      searchScopeType: [''],
    });
  }

  /**
   * Create Form Search Condition
   */
  initFormSearchCondition() {
    this.formSearchCondition = this.fb.group({
      assetId: [''],
      assetIdPlural: [''],
      levelAssetFlag: [''],
      contractNum: [''],
      contractNumPlural: [''],
      contractEda: [''],
      contractEdaPlural: [''],
      shisanNum: [null],
      shisanNumPlural: [null],
      relationShisanNumFlag: [false],
      getApplicationId: [''],
      getApplicationIdPlural: [''],
      registApplicationId: [''],
      registApplicationIdPlural: [''],
      registDateFrom: [''],
      registDateTo: [''],
      deleteFlag: [false],
    });
  }

  /**
   * 編集による値変更ハンドラー（詳細項目共通
   */
  dataEditChanged($event) {
    const target = $event.target as HTMLInputElement;
    switch (target.id) {
      case 'searchScopeType1':
        this.comAssetSearchConditionComponent.comAllCompanyFlagSelected = false;
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = true;
        break;
      case 'searchScopeType2':
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = false;
        break;
    }
  }

  /**
   * リンクボタンクリック用ボタンハンドラー
   */
  linkBtnClick($event: MouseEvent) {
    const target = $event.target as HTMLInputElement;
    switch (target.id) {
      case 'assetId_LNK':
        this.isAssetId = !this.isAssetId;
        this.formSearchCondition.controls.assetId.setValue('');
        break;
      case 'assetIdPlural_LNK':
        this.isAssetId = !this.isAssetId;
        this.formSearchCondition.controls.assetIdPlural.setValue('');
        break;
      case 'contractNum_LNK':
        this.isContractNum = !this.isContractNum;
        this.formSearchCondition.controls.contractNum.setValue('');
        break;
      case 'contractNumPlural_LNK':
        this.isContractNum = !this.isContractNum;
        this.formSearchCondition.controls.contractNumPlural.setValue('');
        break;
      case 'contractEda_LNK':
        this.isContractEda = !this.isContractEda;
        this.formSearchCondition.controls.contractEda.setValue('');
        break;
      case 'contractEdaPlural_LNK':
        this.isContractEda = !this.isContractEda;
        this.formSearchCondition.controls.contractEdaPlural.setValue('');
        break;

      case 'shisanNum_LNK':
        this.isShisanNum = !this.isShisanNum;
        this.formSearchCondition.controls.shisanNum.setValue('');
        break;
      case 'shisanNumPlural_LNK':
        this.isShisanNum = !this.isShisanNum;
        this.formSearchCondition.controls.shisanNumPlural.setValue('');
        break;

      case 'getApplicationId_LNK':
        this.isGetApplicationId = !this.isGetApplicationId;
        this.formSearchCondition.controls.getApplicationId.setValue('');
        break;
      case 'getApplicationIdPlural_LNK':
        this.isGetApplicationId = !this.isGetApplicationId;
        this.formSearchCondition.controls.getApplicationIdPlural.setValue('');
        break;

      case 'registApplicationId_LNK':
        this.isRegistApplicationId = !this.isRegistApplicationId;
        this.formSearchCondition.controls.registApplicationId.setValue('');
        break;
      case 'registApplicationIdPlural_LNK':
        this.isRegistApplicationId = !this.isRegistApplicationId;
        this.formSearchCondition.controls.registApplicationIdPlural.setValue(
          '',
        );
        break;
    }
  }

  /**
   * Check status
   */
  private fsStateStatus(status: string) {
    switch (status) {
      case 'ASSET_SEARCH':
        this.isSearchScopeTypeShow = false;
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = false;
        break;
      case 'PARENT_ASSET_SEARCH':
        this.isSearchScopeTypeShow = false;
        this.formSearchCondition.controls.deleteFlag.disable();
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = false;
        break;
      case 'ASSET_DELETE':
        this.formSearchScopeTypeLayout.disable();
        this.formSearchScopeTypeLayout.controls.searchScopeType.setValue('1');
        this.formSearchCondition.controls.deleteFlag.disable();
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = true;
        this.comAssetSearchConditionComponent.comAllCompanyFlagSelected = false;
        break;
      case 'AP_GET_TAN':
        this.formSearchScopeTypeLayout.disable();
        this.formSearchCondition.controls.deleteFlag.disable();
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = true;
        this.comAssetSearchConditionComponent.comAllCompanyFlagSelected = false;
        break;
      case 'AP_ASSET_VM':
        this.comAssetSearchConditionComponent.titleCategory = 'ASSET_CATEGORY2';
        this.formSearchScopeTypeLayout.disable();
        this.formSearchCondition.controls.deleteFlag.disable();
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = true;
        this.comAssetSearchConditionComponent.comAllCompanyFlagSelected = false;
        break;
      case 'AP_CHANGE':
        this.formSearchScopeTypeLayout.disable();
        this.formSearchCondition.controls.deleteFlag.disable();
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = true;
        this.comAssetSearchConditionComponent.comAllCompanyFlagSelected = false;
        break;
      case 'LICENSE_ALLOC':
        this.comAssetSearchConditionComponent.titleCategory = 'ASSET_CATEGORY2';
        this.formSearchScopeTypeLayout.disable();
        this.formSearchCondition.controls.deleteFlag.disable();
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = false;
        break;
      case 'LICENSE_REPORT_ALLOC':
        this.isSearchScopeTypeShow = false;
        if (
          this.sessionInfo.currentAccessLevel === 'C' ||
          this.sessionInfo.currentAccessLevel === 'B'
        ) {
          this.isSearchScopeTypeShow = true;
        }
        this.comAssetSearchConditionComponent.titleCategory = 'ASSET_CATEGORY2';
        this.formSearchCondition.controls.deleteFlag.disable();
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = false;
        break;
      case 'AP_END_RE':
        this.formSearchScopeTypeLayout.disable();
        this.formSearchCondition.controls.deleteFlag.disable();
        this.comAssetSearchConditionComponent.comAllCompanyFlagEditLock = true;
        break;
    }
  }

  /**
   * 検索条件クリア
   */
  clearSearchCondition() {
    this.isAssetId = true;
    this.isContractNum = true;
    this.isContractEda = true;
    this.isShisanNum = true;
    this.isGetApplicationId = true;
    this.isRegistApplicationId = true;

    if (
      this.sessionInfo.currentAccessLevel === 'C' ||
      this.sessionInfo.currentAccessLevel === 'B'
    ) {
      this.formSearchScopeTypeLayout.controls.searchScopeType.setValue('1');
    } else {
      this.formSearchScopeTypeLayout.controls.searchScopeType.setValue('2');
    }
    switch (this.currentState) {
      case 'ASSET_SEARCH':
        break;
      default:
        this.formSearchCondition.controls.deleteFlag.setValue(false);
        break;
    }
    switch (this.currentState) {
      case 'AP_CHANGE':
      case 'AP_END_RE':
        this.formSearchCondition.controls.levelAssetFlag.setValue(true);
        this.formSearchCondition.controls.relationShisanNumFlag.setValue(true);
        break;
      default:
        this.formSearchCondition.controls.levelAssetFlag.setValue(false);
        this.formSearchCondition.controls.relationShisanNumFlag.setValue(false);
        break;
    }
    switch (this.currentState) {
      case 'ASSET_SEARCH':
      case 'PARENT_ASSET_SEARCH':
      case 'LICENSE_ALLOC':
      case 'LICENSE_REPORT_ALLOC':
        if (this.sessionInfo.currentAccessLevel === 'C') {
          this.comAssetSearchConditionComponent.comAllCompanyFlagSelected = true;
          break;
        }
        break;
      default:
        this.comAssetSearchConditionComponent.comAllCompanyFlagSelected = false;
        break;
    }
    switch (this.currentState) {
      case 'AP_GET_TAN':
        this.comAssetSearchConditionComponent.isAstCategoryEqual = false;
        this.comAssetSearchConditionComponent.formAssetsEquipment.controls.astAssetTypePlural.setValue(
          '',
        );
        break;

      case 'AP_END_RE':
        this.comAssetSearchConditionComponent.isAstAssetypeFP = false;
        this.comAssetSearchConditionComponent.formAssetsEquipment.controls.astAssetTypePlural.setValue(
          '',
        );
        break;
      default:
        this.comAssetSearchConditionComponent.isAstAssetypeFP = true;
        this.comAssetSearchConditionComponent.formAssetsEquipment.controls.astAssetTypePlural.setValue(
          '',
        );
        break;
    }
    switch (this.currentState) {
      case 'AP_ASSET_VM':
      case 'LICENSE_ALLOC':
      case 'LICENSE_REPORT_ALLOC':
        this.comAssetSearchConditionComponent.isAstCategoryEqual = true;
        this.comAssetSearchConditionComponent.formAssetsEquipment.controls.astCategory2Plural.setValue(
          '',
        );

        break;
      default:
        this.comAssetSearchConditionComponent.isAstCategoryEqual = true;
        this.comAssetSearchConditionComponent.formAssetsEquipment.controls.astAssetTypePlural.setValue('');
        break;
    }
    switch (this.currentState) {
      case 'AP_CHANGE':
        if (this.getApChangeUseType() === '2') {
          this.comAssetSearchConditionComponent.isAstManageTypeFP = false;
          this.comAssetSearchConditionComponent.isAstSystemSectionDeployFlagFi = true;
          this.comAssetSearchConditionComponent.formAssetsEquipment.controls.astSystemSectionDeployFlag.setValue(
            true,
          );
          this.comAssetSearchConditionComponent.formAssetsEquipment.controls.astManageTypePlural.setValue('');
        }
        break;
      default:
        this.comAssetSearchConditionComponent.isAstManageTypeFP = true;
        this.comAssetSearchConditionComponent.formAssetsEquipment.controls.astManageTypePlural.setValue('');
        this.comAssetSearchConditionComponent.isAstSystemSectionDeployFlagFi = false;
        break;
    }
  }

  /**
   * 検索条件項目一覧の値取得
   */
  getSearchCondition() {
    const resultSearchCondition = {
      searchScopeType: this.formSearchScopeTypeLayout.get('searchScopeType')
        .value,
      assetId: this.formSearchCondition.get('assetId').value,
      assetIdPlural: this.formSearchCondition.get('assetIdPlural').value,
      levelAssetFlag: this.formSearchCondition.get('levelAssetFlag').value
        ? '1'
        : '0',
      contractNum: this.formSearchCondition.get('contractNum').value,
      contractNumPlural: this.formSearchCondition.get('contractNumPlural')
        .value,
      contractEda: this.formSearchCondition.get('contractEda').value,
      contractEdaPlural: this.formSearchCondition.get('contractEdaPlural')
        .value,
      shisanNum: this.formSearchCondition.get('shisanNum').value,
      shisanNumPlural: this.formSearchCondition.get('shisanNumPlural').value,
      relationShisanNumFlag: this.formSearchCondition.get(
        'relationShisanNumFlag',
      ).value
        ? '1'
        : '0',
      getApplicationId: this.formSearchCondition.get('getApplicationId').value,
      getApplicationIdPlural: this.formSearchCondition.get(
        'registApplicationId',
      ).value,
      registApplicationId: this.formSearchCondition.get('registApplicationId')
        .value,
      registApplicationIdPlural: this.formSearchCondition.get(
        'registApplicationIdPlural',
      ).value,
      registDateFrom: this.formSearchCondition.get('registDateFrom').value,
      registDateTo: this.formSearchCondition.get('registDateTo').value,
      deleteFlag: this.formSearchCondition.get('deleteFlag').value ? '1' : '0',
    };

    const resultComSearchCondition = this.comAssetSearchConditionComponent.getSearchCondition();

    const searchParams = {
      ...resultSearchCondition,
      ...resultComSearchCondition
    };
    return searchParams;
  }

  /**
   * コードネーム一覧データを取得
   */
  private getCodeNamelist(
    categoryCode: string,
    internalCode?: string,
    companyCode?: string,
    values?,
  ) {
    this.masterService
      .getCodeNameList(categoryCode, internalCode, companyCode, values)
      .subscribe((resp: CodeName[]) => {
        this.usableCompanyList = resp;
      });
  }

  /**
   * 移動申請を使用区分
   * @return    0:未使用、1:使用、2:情シス配備のみ使用
   */
  private getApChangeUseType(): string {
    let ret = '0';
    for (const iterator of this.usableCompanyList) {
      const row: CodeName = iterator as CodeName;
      if (
        (row.internalCode === this.sessionInfo.loginUser.companyCode &&
          row.value4 === '1') ||
        row.value4 === '2'
      ) {
        ret = row.value4;
        if (ret === null || ret === '') {
          ret = '0';
        }
      }
    }
    return ret;
  }
}
