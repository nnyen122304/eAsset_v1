import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { DatePipe } from '@angular/common';
import * as wjcCore from 'wijmo/wijmo';

@Component({
  selector: 'app-com-asset-search-condition',
  templateUrl: './com-asset-search-condition.component.html',
  styleUrls: ['./com-asset-search-condition.component.scss']
})
export class ComAssetSearchConditionComponent implements OnInit {

  /**
   * Form 資産・機器
   */
  formAssetsEquipment: FormGroup;

  /**
   * Form ネットワーク
   */
  formNetWork: FormGroup;

  /**
   * Form 保守契約
   */
  formMaintenanceContract: FormGroup;

  /**
   * Form 保有・設置・使用
   */
  formProssessionInstallationUse: FormGroup;

  /**
   * Form 備考・付加情報
   */
  formRemaksAdditionalInfomation: FormGroup;

  /**
   * Form 棚卸
   */
  formInventory: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 社内実地棚卸対象
   */
  invInCompanyActualFlagAC = [
    { data: '', label: '' },
    { data: '1', label: '対象' },
    { data: '0', label: '対象外' }
  ];

  /**
   * シール発行対象
   */
  invSealIssueFlagAC = [
    { data: '', label: '' },
    { data: '1', label: '対象' },
    { data: '0', label: '対象外' }
  ];

  /**
   * シール発行
   */
  invSealIssueStatusAC = [
    { data: '', label: '' },
    { data: '0', label: '未発行' },
    { data: '1', label: '発行済' }
  ];

  /**
   * 棚卸実施状況
   */
  invFlagAC = [
    { data: '', label: '' },
    { data: '0', label: '未実施' },
    { data: '1', label: '実施済' }
  ];

  /**
   * 表示モード（true:情報機器登録申請、false:情報機器）
   */
  apAssetMode = false;

  /**
   * formcontrol hidden
   */
  isAstCategoryEqual = true;
  isAstAssetypeFP = true;
  isAstManageTypeFP = true;
  isAstSerialCode = true;
  isAstOir = true;
  isNetHostName = true;
  isHolOfficeCodeName = true;
  isDscAttribute = true;
  isInvSearchDateFi = true;
  isAstMakerCodeNameBtn = true;
  isAstHolderCodeNameBtn = true;
  isHolSectionSearch = true;
  isAstSystemSectionDeployFlagFi = true;

  /**
   * group hiden
   */
  isMntContGBox = true;
  isInvGBox = true;
  isInvSealIssueFI = true;
  isInvSealIssueDateFI = true;
  isInvFlagBOX = true;
  isInvSeal = true;

  /**
   * Text category app-asset-category
   */
  titleCategory = 'ASSET_CATEGORY1';

  /**
   * set disable vs selected comAllCompanyFlag
   */
  comAllCompanyFlagEditLock: boolean;
  comAllCompanyFlagSelected: boolean;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private apAssetService: ApAssetService,
    private fileDownloadService: FileDownloadService,
    private datePipe: DatePipe) { }

  ngOnInit() {
    this.initChild();
  }

  /**
   * 子画面初期化処理
   */
  initChild() {
    this.formProssessionInstallationUse.controls.holCompanyName.setValue(this.sessionInfo.loginCompanyName);
    this.formProssessionInstallationUse.controls.holCompanyCode.setValue(this.sessionInfo.loginCompanyCode);
    this.formProssessionInstallationUse.controls.holCompanyCodeALSection.setValue(this.sessionInfo.loginCompanyCode);

    this.apAssetMode = this.sessionInfo.currentMenuId === '05020' ? true : '05040' ? true : false;
    if (this.apAssetMode) {
      this.formProssessionInstallationUse.controls.allCompanyFlag.setValue(this.comAllCompanyFlagSelected);
      this.isMntContGBox = false;

      if (this.sessionInfo.currentAccessLevel === 'C' || this.sessionInfo.currentAccessLevel === 'B') {
        this.isInvGBox = true;
        this.formProssessionInstallationUse.controls.allCompanyFlag.setValue(this.comAllCompanyFlagSelected);
      } else {
        this.isInvSealIssueFI = false;
        this.isInvSealIssueDateFI = false;
        this.isInvFlagBOX = false;
      }
    } else {
      if (this.sessionInfo.currentAccessLevel === 'C' ||
        this.sessionInfo.currentAccessLevel === 'B') {
        this.isInvSeal = false;
      } else {
        this.isInvSeal = true;
      }
      this.formProssessionInstallationUse.controls.allCompanyFlag.setValue(this.comAllCompanyFlagSelected);

      this.isMntContGBox = true;
    }

    if (this.comAllCompanyFlagSelected) {
      this.formProssessionInstallationUse.controls.holCompanyName.setValue('');
      this.formProssessionInstallationUse.controls.holCompanyCode.setValue('');
      this.formProssessionInstallationUse.controls.holCompanyCodeALSection.setValue('');
    }

    if (this.comAllCompanyFlagEditLock) {
      this.formProssessionInstallationUse.controls.allCompanyFlag.disable();
      this.formProssessionInstallationUse.controls.allCompanyFlag.setValue(this.comAllCompanyFlagSelected);
    }
  }

  /**
   *  init form
   */
  initFormComSearchCondition() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.formAssetsEquipment = this.fb.group({
      astName: [null],
      astCategory1Code: [null],
      astCategory1Name: [null],
      astCategory2Code: [null],
      astCategory2Name: [null],
      astCategory2Plural: [null],
      astMakerCode: [null],
      astMakerName: [null],
      astMakerModel: [null],
      astSerialCode: [null],
      astSerialCodePlural: [null],
      astGetType: [null],
      astGetTypeName: [null],
      astSystemSectionDeployFlag: [false],
      astAssetType: [null],
      astAssetTypeName: [null],
      astAssetTypePlural: [null],
      astHolderCode: [null],
      astHolderName: [null],
      astManageType: [null],
      astManageTypeName: [null],
      astManageTypePlural: [null],
      astOir: [null],
      astOirPlural: [null],
    });

    this.formNetWork = this.fb.group({
      netMacAddress1: [''],
      netMacAddress2: [''],
      netMacAddress3: [''],
      netMacAddress4: [''],
      netMacAddress5: [''],
      netMacAddress6: [''],
      netIpAddress1: [''],
      netIpAddress2: [''],
      netIpAddress3: [''],
      netIpAddress4: [''],
      netHostName: [null],
      netHostNamePlural: [null],
    });

    this.formMaintenanceContract = this.fb.group({
      mntContractCompanyName: [null],
      mntContractCode: [null],
      mntContractStartDateFrom: [''],
      mntContractStartDateTo: [''],
      mntContractEndDateFrom: [''],
      mntContractEndDateTo: [''],
    });

    this.formProssessionInstallationUse = this.fb.group({
      holCompanyCode: [null],
      holCompanyCodeALSection: [null],
      holCompanyName: [null],
      allCompanyFlag: [false],
      holSectionCode: [null],
      holSectionName: [null],
      holSectionYear: [null],
      includeSectionHierarchyFlag: [false],
      holStaffCode: [null],
      holStaffName: [null],
      holOfficeCode: [null],
      holOfficeName: [null],
      holOfficeFloor: [''],
      holOfficePlural: [null],
      holGetStaffCode: [null],
      holGetStaffName: [null],
      useStaffCode: [null],
      useStaffName: [null],
    });

    this.formRemaksAdditionalInfomation = this.fb.group({
      dscDescription: [null],
      dscAttribute: [null],
      dscAttribute1: [null],
      dscAttribute2: [null],
      dscAttribute3: [null],
      dscAttribute4: [null],
      dscAttribute5: [null],
      dscAttribute6: [null],
      dscAttribute7: [null],
      dscAttribute8: [null],
      dscAttribute9: [null],
      dscAttribute10: [null],
      dscAttribute11: [null],
      dscAttribute12: [null],
      dscAttribute13: [null],
      dscAttribute14: [null],
      dscAttribute15: [null],
      dscAttribute16: [null],
      dscAttribute17: [null],
      dscAttribute18: [null],
      dscAttribute19: [null],
      dscAttribute20: [null],
    });

    this.formInventory = this.fb.group({
      invInCompanyActualFlag: [null],
      invSealIssueFlag: [null],
      invSealIssueStatus: [null],
      invSealIssueDateFrom: [''],
      invSealIssueDateTo: [''],
      invFlag: [null],
      invSearchDateFrom: [''],
      invSearchDateTo: [''],
    });

  }

  /**
   * hiden button clear vs 手入力
   */
  onClickLinkHidenBtnClear($event: MouseEvent) {
    const target = $event.target as HTMLInputElement;
    switch (target.id) {
      case 'astMakerCodeNameBtn':
        this.isAstMakerCodeNameBtn = false;
        break;
      case 'isAstHolderCodeName':
        this.isAstHolderCodeNameBtn = false;
        break;
    }
  }

  /**
   * 棚卸実施状況
   */
  changeInventoryStatus(valueStatus) {
    if (valueStatus === null || valueStatus.data === '') {
      this.isInvSearchDateFi = false;
    } else {
      this.isInvSearchDateFi = true;
    }
  }

  /**
   * 編集による値変更ハンドラー（確定前で入力中でも発生）
   */
  dataChange($event: KeyboardEvent) {
    const target = $event.target as HTMLInputElement;
    const elem = document.activeElement;
    const inpElem = wjcCore.closest(elem, '.wj-inputnumber');
    const maxLength = parseInt(inpElem.attributes['maxlength'].value, 10);
    const myLength = target.value.length;
    const tabName = !inpElem.nextElementSibling
      ? '' : inpElem.nextElementSibling.nextElementSibling
        ? inpElem.nextElementSibling.nextElementSibling.tagName.toLocaleLowerCase() : '';

    switch (inpElem.id) {
      case 'netMacAddress1':
        if (myLength >= maxLength) {
          if (tabName === 'ea-input-number') {
            document.getElementById('netMacAddress2').focus();
          }
        }
        break;
      case 'netMacAddress2':
        if (myLength >= maxLength) {
          if (tabName === 'ea-input-number') {
            document.getElementById('netMacAddress3').focus();
          }
        }
        break;
      case 'netMacAddress3':
        if (myLength >= maxLength) {
          if (tabName === 'ea-input-number') {
            document.getElementById('netMacAddress4').focus();
          }
        }
        break;
      case 'netMacAddress4':
        if (myLength >= maxLength) {
          if (tabName === 'ea-input-number') {
            document.getElementById('netMacAddress5').focus();
          }
        }
        break;
      case 'netMacAddress5':
        if (myLength >= maxLength) {
          if (tabName === 'ea-input-number') {
            document.getElementById('netMacAddress6').focus();
          }
        }
        break;
      case 'netIpAddress1':
        if (myLength >= maxLength) {
          if (tabName === 'ea-input-number') {
            document.getElementById('netIpAddress2').focus();
          }
        }
        break;
      case 'netIpAddress2':
        if (myLength >= maxLength) {
          if (tabName === 'ea-input-number') {
            document.getElementById('netIpAddress3').focus();
          }
        }
        break;
      case 'netIpAddress3':
        if (myLength >= maxLength) {
          if (tabName === 'ea-input-number') {
            document.getElementById('netIpAddress4').focus();
          }
        }
        break;
      default:
        break;
    }
  }

  /**
   * 値変更ハンドラー
   */
  dataValueChanged($event: MouseEvent) {
    const target = $event.target as HTMLInputElement;
    switch (target.id) {
      case 'allCompanyFlag':
        if (target.checked) {
          this.isHolSectionSearch = false;
          this.formProssessionInstallationUse.controls.holCompanyCode.setValue('');
          this.formProssessionInstallationUse.controls.holCompanyName.setValue('');
          this.formProssessionInstallationUse.controls.holSectionCode.setValue('');
          this.formProssessionInstallationUse.controls.holSectionName.setValue('');
          this.formProssessionInstallationUse.controls.includeSectionHierarchyFlag.setValue(false);
          this.formProssessionInstallationUse.controls.holStaffCode.setValue('');
          this.formProssessionInstallationUse.controls.holStaffName.setValue('');
        } else {
          this.isHolSectionSearch = true;
          this.formProssessionInstallationUse.controls.holCompanyCode.setValue(this.sessionInfo.loginCompanyCode);
          this.formProssessionInstallationUse.controls.holCompanyName.setValue(this.sessionInfo.loginCompanyName);
        }
        break;
    }
  }

  /**
   * 検索条件項目一覧の値取得
   */
  getSearchCondition() {
    const searchParam = {
      astName: this.formAssetsEquipment.get('astName').value,
      astCategory1Code: this.formAssetsEquipment.get('astCategory1Code').value,
      astCategory1Name: this.formAssetsEquipment.get('astCategory1Name').value,
      astCategory2Code: this.formAssetsEquipment.get('astCategory2Code').value,
      astCategory2Name: this.formAssetsEquipment.get('astCategory2Name').value,
      astCategory2Plural: this.formAssetsEquipment.get('astCategory2Plural').value,
      astMakerCode: this.formAssetsEquipment.get('astMakerCode').value,
      astMakerName: this.formAssetsEquipment.get('astMakerName').value,
      astMakerModel: this.formAssetsEquipment.get('astMakerModel').value,
      astSerialCode: this.formAssetsEquipment.get('astSerialCode').value,
      astSerialCodePlural: this.formAssetsEquipment.get('astSerialCodePlural').value,
      astGetType: this.formAssetsEquipment.get('astGetType').value,
      astGetTypeName: this.formAssetsEquipment.get('astGetTypeName').value,
      astSystemSectionDeployFlag: this.formAssetsEquipment.get('astSystemSectionDeployFlag').value ? '1' : '0',
      astAssetType: this.formAssetsEquipment.get('astAssetType').value,
      astAssetTypeName: this.formAssetsEquipment.get('astAssetTypeName').value,
      astAssetTypePlural: this.formAssetsEquipment.get('astAssetTypePlural').value,

      astHolderCode: this.formAssetsEquipment.get('astHolderCode').value,
      astHolderName: this.formAssetsEquipment.get('astHolderName').value,
      astManageType: this.formAssetsEquipment.get('astManageType').value,
      astManageTypeName: this.formAssetsEquipment.get('astManageTypeName').value,
      astManageTypePlural: this.formAssetsEquipment.get('astManageTypePlural').value,
      astOir: this.formAssetsEquipment.get('astOir').value,
      astOirPlural: this.formAssetsEquipment.get('astOirPlural').value,

      netMacAddress1: this.formNetWork.get('netMacAddress1').value,
      netMacAddress2: this.formNetWork.get('netMacAddress2').value,
      netMacAddress3: this.formNetWork.get('netMacAddress3').value,
      netMacAddress4: this.formNetWork.get('netMacAddress4').value,
      netMacAddress5: this.formNetWork.get('netMacAddress5').value,
      netMacAddress6: this.formNetWork.get('netMacAddress6').value,
      netIpAddress1: this.formNetWork.get('netIpAddress1').value,
      netIpAddress2: this.formNetWork.get('netIpAddress2').value,
      netIpAddress3: this.formNetWork.get('netIpAddress3').value,
      netIpAddress4: this.formNetWork.get('netIpAddress4').value,
      netHostName: this.formNetWork.get('netHostName').value,
      netHostNamePlural: this.formNetWork.get('netHostNamePlural').value,
      mntContractCompanyName: this.formMaintenanceContract.get('mntContractCompanyName').value,
      mntContractCode: this.formMaintenanceContract.get('mntContractCode').value,
      mntContractStartDateFrom: this.formMaintenanceContract.get('mntContractStartDateFrom').value,
      mntContractStartDateTo: this.formMaintenanceContract.get('mntContractStartDateTo').value,
      mntContractEndDateFrom: this.formMaintenanceContract.get('mntContractEndDateFrom').value,
      mntContractEndDateTo: this.formMaintenanceContract.get('mntContractEndDateTo').value,

      holCompanyCode: this.formProssessionInstallationUse.get('holCompanyCode').value,
      holCompanyCodeALSection: this.formProssessionInstallationUse.get('holCompanyCodeALSection').value,
      holCompanyName: this.formProssessionInstallationUse.get('holCompanyName').value,
      allCompanyFlag: this.formProssessionInstallationUse.get('allCompanyFlag').value ? '1' : '0',
      holSectionCode: this.formProssessionInstallationUse.get('holSectionCode').value,
      holSectionName: this.formProssessionInstallationUse.get('holSectionName').value,
      holSectionYear: this.formProssessionInstallationUse.get('holSectionYear').value,
      includeSectionHierarchyFlag: this.formProssessionInstallationUse.get('includeSectionHierarchyFlag').value ? '1' : '0',
      holStaffCode: this.formProssessionInstallationUse.get('holStaffCode').value,
      holStaffName: this.formProssessionInstallationUse.get('holStaffName').value,
      holOfficeCode: this.formProssessionInstallationUse.get('holOfficeCode').value,
      holOfficeName: this.formProssessionInstallationUse.get('holOfficeName').value,
      holOfficeFloor: this.formProssessionInstallationUse.get('holOfficeFloor').value,
      holOfficePlural: this.formProssessionInstallationUse.get('holOfficePlural').value,
      holGetStaffCode: this.formProssessionInstallationUse.get('holGetStaffCode').value,
      holGetStaffName: this.formProssessionInstallationUse.get('holGetStaffName').value,
      useStaffCode: this.formProssessionInstallationUse.get('useStaffCode').value,
      useStaffName: this.formProssessionInstallationUse.get('useStaffName').value,

      dscDescription: this.formRemaksAdditionalInfomation.get('dscDescription').value,
      dscAttribute: this.formRemaksAdditionalInfomation.get('dscAttribute').value,
      dscAttribute1: this.formRemaksAdditionalInfomation.get('dscAttribute1').value,
      dscAttribute2: this.formRemaksAdditionalInfomation.get('dscAttribute2').value,
      dscAttribute3: this.formRemaksAdditionalInfomation.get('dscAttribute3').value,
      dscAttribute4: this.formRemaksAdditionalInfomation.get('dscAttribute4').value,
      dscAttribute5: this.formRemaksAdditionalInfomation.get('dscAttribute5').value,
      dscAttribute6: this.formRemaksAdditionalInfomation.get('dscAttribute6').value,
      dscAttribute7: this.formRemaksAdditionalInfomation.get('dscAttribute7').value,
      dscAttribute8: this.formRemaksAdditionalInfomation.get('dscAttribute8').value,
      dscAttribute9: this.formRemaksAdditionalInfomation.get('dscAttribute9').value,
      dscAttribute10: this.formRemaksAdditionalInfomation.get('dscAttribute10').value,
      dscAttribute11: this.formRemaksAdditionalInfomation.get('dscAttribute11').value,
      dscAttribute12: this.formRemaksAdditionalInfomation.get('dscAttribute12').value,

      dscAttribute13: this.formRemaksAdditionalInfomation.get('dscAttribute13').value,
      dscAttribute14: this.formRemaksAdditionalInfomation.get('dscAttribute14').value,
      dscAttribute15: this.formRemaksAdditionalInfomation.get('dscAttribute15').value,
      dscAttribute16: this.formRemaksAdditionalInfomation.get('dscAttribute16').value,
      dscAttribute17: this.formRemaksAdditionalInfomation.get('dscAttribute17').value,
      dscAttribute18: this.formRemaksAdditionalInfomation.get('dscAttribute18').value,
      dscAttribute19: this.formRemaksAdditionalInfomation.get('dscAttribute19').value,
      dscAttribute20: this.formRemaksAdditionalInfomation.get('dscAttribute20').value,

      invInCompanyActualFlag: this.formInventory.get('invInCompanyActualFlag').value,
      invSealIssueFlag: this.formInventory.get('invSealIssueFlag').value,
      invSealIssueStatus: this.formInventory.get('invSealIssueStatus').value,
      invSealIssueDateFrom: this.formInventory.get('invSealIssueDateFrom').value,
      invSealIssueDateTo: this.formInventory.get('invSealIssueDateTo').value,
      invFlag: this.formInventory.get('invFlag').value,
      invSearchDateFrom: this.formInventory.get('invSearchDateFrom').value,
      invSearchDateTo: this.formInventory.get('invSearchDateTo').value,

    };
    return searchParam;
  }

  /**
   * リンクボタンクリック用ボタンハンドラー
   */
  linkBtnClick($event: MouseEvent) {
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const target = $event.target as HTMLInputElement;
    switch (target.id) {
      case 'astCategory2Name_LNK':
        this.isAstCategoryEqual = !this.isAstCategoryEqual;
        this.formAssetsEquipment.controls.astCategory1Code.setValue('');
        this.formAssetsEquipment.controls.astCategory1Name.setValue('');
        this.formAssetsEquipment.controls.astCategory2Code.setValue('');
        this.formAssetsEquipment.controls.astCategory2Name.setValue('');
        break;
      case 'astCategory_DEF':
        this.isAstCategoryEqual = !this.isAstCategoryEqual;
        this.formAssetsEquipment.controls.astCategory2Plural.setValue('');
        break;
      case 'astMakerName_LNK':
        this.isAstMakerCodeNameBtn = false;
        this.formAssetsEquipment.controls.astMakerCode.setValue('');
        break;

      case 'astSerialCode_LNK':
        this.isAstSerialCode = !this.isAstSerialCode;
        this.formAssetsEquipment.controls.astSerialCode.setValue('');
        break;

      case 'astSerialCodePlural_LNK':
        this.isAstSerialCode = !this.isAstSerialCode;
        this.formAssetsEquipment.controls.astSerialCodePlural.setValue('');
        break;
      case 'astHolderName_LNK':
        this.isAstHolderCodeNameBtn = false;
        this.formAssetsEquipment.controls.astHolderCode.setValue('');
        break;
      case 'astOir_LNK':
        this.isAstOir = !this.isAstOir;
        this.formAssetsEquipment.controls.astOir.setValue('');
        break;
      case 'astOirPlural_LNK':
        this.isAstOir = !this.isAstOir;
        this.formAssetsEquipment.controls.astOirPlural.setValue('');
        break;
      case 'holOfficeName_LNK':
        this.isHolOfficeCodeName = !this.isHolOfficeCodeName;
        this.formProssessionInstallationUse.controls.holOfficeCode.setValue('');
        this.formProssessionInstallationUse.controls.holOfficeName.setValue('');
        break;
      case 'holOffice_DEF':
        this.isHolOfficeCodeName = !this.isHolOfficeCodeName;
        this.formProssessionInstallationUse.controls.holOfficePlural.setValue('');
        break;
      case 'netHostName_LNK':
        this.isNetHostName = !this.isNetHostName;
        this.formNetWork.controls.netHostName.setValue('');
        break;
      case 'netHostNamePlural_LNK':
        this.isNetHostName = !this.isNetHostName;
        this.formNetWork.controls.netHostNamePlural.setValue('');
        break;

      case 'dscAttribute_LNK':
        this.isDscAttribute = !this.isDscAttribute;
        this.formRemaksAdditionalInfomation.controls.dscAttribute.setValue('');
        break;
      case 'dscAttributePlural_LNK':
        this.isDscAttribute = !this.isDscAttribute;
        if (!this.isDscAttribute) {
          this.formRemaksAdditionalInfomation.controls.dscAttribute.setValue('');
        } else {
          this.formRemaksAdditionalInfomation.controls.dscAttribute1.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute2.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute3.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute4.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute5.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute6.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute7.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute8.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute9.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute10.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute11.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute12.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute13.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute14.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute15.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute16.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute17.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute18.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute19.setValue('');
          this.formRemaksAdditionalInfomation.controls.dscAttribute20.setValue('');
        }

        break;
      case 'astCategory2Plural_LNK':
        const propListAstCategory: string[] = [];
        propListAstCategory.push('astCategory2Name');
        this.createAssetPossibleInputMasterCsv(companyCode, propListAstCategory, '入力可能値一覧_');
        break;
      case 'holOfficePlural_LNK':
        let companyCodeText = companyCode;
        const propListHol: string[] = [];
        propListHol.push('holOfficeName');
        const holCompanyCode = this.formProssessionInstallationUse.controls.holCompanyCode.value;
        if (holCompanyCode === '' || holCompanyCode === null) {
          companyCodeText = '';
        }
        this.createAssetPossibleInputMasterCsv(companyCode, propListHol, '入力可能値一覧_');
        break;
      default:
        break;
    }
  }

  /**
   * Download csv 入力可能値一覧_
   */
  private createAssetPossibleInputMasterCsv(companyCode: string, propList: string[], nameCsv: string) {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;

    this.apAssetService.createAssetPossibleInputMasterCsv(loginStaffCode, accessLevel, companyCode, propList).subscribe((resp: NonObjectResponse<string>) => {
      const fileId = resp.value;
      const fileName = `${nameCsv}${this.datePipe.transform(
        new Date(),
        'yyyyMMdd_HHmmss',
      )}.csv`;
      this.fileDownloadService.download(fileId, fileName, 'text/csv');
    });
  }
}
