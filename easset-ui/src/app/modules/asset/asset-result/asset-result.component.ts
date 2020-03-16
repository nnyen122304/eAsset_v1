import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { AssetComponent } from '../asset.component';
import { AssetResultListComponent } from 'src/app/parts/screen/asset/asset-result-list/asset-result-list.component';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { DownloadOptionPropComponent } from 'src/app/parts/option/download-option-prop/download-option-prop.component';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { DatePipe } from '@angular/common';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { Asset } from 'src/app/models/api/asset/asset.model';


@Component({
  selector: 'app-asset-result',
  templateUrl: './asset-result.component.html',
  styleUrls: ['./asset-result.component.scss']
})
export class AssetResultComponent extends AbstractChildComponent<AssetComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索結果
   */
  assetSRList: AssetSR[] = [];

  /**
   * 検索条件
   */
  assetSCSearch: AssetSC = new AssetSC();

  /**
   * 資産コードで検索
   */
  searchModel = {
    assetId: '',
  };

  /**
   * オブジェクトを削除
   */
  deleteObj = {
    deleteDate: new Date(),
    deleteReason: '',
  };

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild(AssetResultListComponent) resultList: AssetResultListComponent;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild(DownloadOptionPropComponent, null) downloadOptionPropComponent: DownloadOptionPropComponent;

  constructor(
    private sessionService: SessionService,
    private apAssetService: ApAssetService,
    private datePipe: DatePipe,
    private fileDownloadService: FileDownloadService,
    private messageService: MessageService,
  ) {
    super();
  }

  /**
   * コンポネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initResult') {
        this.assetSRList = param.params.datalist;
        this.assetSCSearch = param.params.searchParam;
        this.resultList.deleteFlagInput = this.assetSCSearch.deleteFlag;
        if (this.sessionInfo.currentMenuId !== '01010') {
          this.resultList.checkBoxVisible = true;
        } else {
          this.resultList.checkBoxVisible = false;
        }
        if (this.assetSCSearch.deleteFlag === '1') {
          this.resultList.deleteColumnVisible = true;
        } else {
          this.resultList.deleteColumnVisible = false;
        }
      } else if (param.action === 'back') {
        if (param.params.updateRow) {
          this.resultList.updateRow(param.params.updateRow);
        }
      } else {
        this.goBack();
      }
    });
  }

  /**
   * Get access level
   */
  getAccessLevel() {
    let accessLevel = 'category';
    switch (this.sessionInfo.currentAccessLevel) {
      case 'S':
        accessLevel = 'admin';
        break;
      case 'C':
        accessLevel = 'general';
        break;
      case 'B':
        accessLevel = 'asset_manager';
        break;
      default:
        accessLevel = 'category';
        break;
    }

    return accessLevel;
  }

  /**
   * ダウンロード
   */
  download(data) {
    const item = data.item ? data.item : [];
    this.assetSCSearch.dowloadLineItem = data.lineItem ? data.lineItem : '';
    this.apAssetService.createAssetCsv(
      this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel,
      item,
      this.assetSCSearch,
      true).subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          const fileName = '情報機器登録申請検索結果_ ' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          this.fileDownloadService.download(fileId, fileName, contentType);
        });
  }

  /**
   * Go Back
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexSearch, {
      action: 'backSearch',
      params: {
        searchParam: this.assetSCSearch
      },
    });
  }

  /**
   * Go to detail
   */
  clickRow(row) {
    if (!row || !row.dataItem) {
      return;
    }

    this.parent.changeChild(this.parent.viewIndexEntry, {
      action: 'initCreate',
      params: {
        loadAssetId: row.dataItem.applicationId,
        loadAssetVersion: row.dataItem.applicationVersion,
        index: row.index
      },
    });
  }

  /**
   * 情報機器検索結果一覧
   */
  searchAsset() {
    this.apAssetService.searchAsset(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, this.assetSCSearch, false).subscribe(
      (resp: AssetSR[]) => {
        if (resp.length > 0) {
          this.assetSRList = resp;
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
          return;
        }
      });
  }

  /**
   * 検索
   */
  search() {
    if (this.searchModel.assetId === null || this.searchModel.assetId === '') {
      this.messageService.info(SystemMessage.Err.msg30030);
      return;
    }

    this.assetSCSearch = { ...this.assetSCSearch, ... this.searchModel };
    this.searchAsset();
  }

  /**
   * 削除アクション
   */
  delete() {
    const list = this.resultList.getDataSelected();

    if (this.deleteObj.deleteDate === null || this.deleteObj.deleteReason === '' || (list === null && list.length === 0)) {
      this.messageService.err(SystemMessage.Err.msg30038('処理対象'));
      return;
    }

    const delAsset: Asset[] = [];
    list.map((item) => {
      delAsset.push(this.parseToAsset(item));
    });

    this.apAssetService.deleteAssetLogical(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, new Date(this.deleteObj.deleteDate), this.deleteObj.deleteReason, delAsset).subscribe(
      () => {
        this.messageService.info(SystemMessage.Info.msg100022);
        this.searchAsset();
      });
    this.resultList.gridAssetResultList.resetSelection();
  }

  /**
   * AssetSRからAssetに変換する
   */
  parseToAsset(assetSR) {
    const asset = new Asset();
    asset.assetId = assetSR.assetId;
    asset.createDate = assetSR.createDate;
    asset.apCreateStaffCode = assetSR.apCreateStaffCode;
    asset.updateDate = assetSR.updateDate;
    asset.updateStaffCode = assetSR.updateStaffCode;
    asset.version = assetSR.version;
    asset.apGetTanLineAstId = assetSR.apGetTanLineAstId;
    asset.apGetTanLineAstLineSeq = assetSR.apGetTanLineAstLineSeq;
    asset.updateComment = assetSR.updateComment;
    asset.eappId = assetSR.eappId;
    asset.apStatus = assetSR.apStatus;
    asset.apStatusName = assetSR.apStatusName;
    asset.apDate = assetSR.apDate;
    asset.apCreateStaffCode = assetSR.apCreateStaffCode;
    asset.apCreateStaffName = assetSR.apCreateStaffName;
    asset.apCreateCompanyCode = assetSR.apCreateCompanyCode;
    asset.apCreateCompanyName = assetSR.apCreateCompanyName;
    asset.apCreateSectionCode = assetSR.apCreateSectionCode;
    asset.apCreateSectionYear = assetSR.apCreateSectionYear;
    asset.apCreateSectionName = assetSR.apCreateSectionName;
    asset.apStaffCode = assetSR.apStaffCode;
    asset.apStaffName = assetSR.apStaffName;
    asset.apCompanyCode = assetSR.apCompanyCode;
    asset.apCompanyName = assetSR.apCompanyName;
    asset.apSectionCode = assetSR.apSectionCode;
    asset.apSectionYear = assetSR.apSectionYear;
    asset.apSectionName = assetSR.apSectionName;
    asset.apTel = assetSR.apTel;
    asset.apOfficeName = assetSR.apOfficeName;
    asset.astName = assetSR.astName;
    asset.astCategory1Code = assetSR.astCategory1Code;
    asset.astCategory1Name = assetSR.astCategory1Name;
    asset.astCategory2Code = assetSR.astCategory2Code;
    asset.astCategory2Name = assetSR.astCategory2Name;
    asset.astQuantityManageType = assetSR.astQuantityManageType;
    asset.astSerialManageType = assetSR.astSerialManageType;
    asset.astShapeCode = assetSR.astShapeCode;
    asset.astShapeName = assetSR.astShapeName;
    asset.astMakerCode = assetSR.astMakerCode;
    asset.astMakerName = assetSR.astMakerName;
    asset.astShopName = assetSR.astShopName;
    asset.astMakerModel = assetSR.astMakerModel;
    asset.astSerialCode = assetSR.astSerialCode;
    asset.astGuaranteeTermDate = assetSR.astGuaranteeTermDate;
    asset.astGetType = assetSR.astGetType;
    asset.astGetTypeName = assetSR.astGetTypeName;
    asset.astSystemSectionDeployFlag = assetSR.astSystemSectionDeployFlag;
    asset.astSystemSectionDeployFlagName = assetSR.astSystemSectionDeployFlagName;
    asset.astAssetType = assetSR.astAssetType;
    asset.astAssetTypeName = assetSR.astAssetTypeName;
    asset.astHolderCode = assetSR.astHolderCode;
    asset.astHolderName = assetSR.astHolderName;
    asset.astManageType = assetSR.astManageType;
    asset.astManageTypeName = assetSR.astManageTypeName;
    asset.astOirEnable = assetSR.astOirEnable;
    asset.astOir = assetSR.astOir;
    asset.astManageEndDate = assetSR.astManageEndDate;
    asset.holCompanyCode = assetSR.holCompanyCode;
    asset.holCompanyName = assetSR.holCompanyName;
    asset.holSectionCode = assetSR.holSectionCode;
    asset.holSectionYear = assetSR.holSectionYear;
    asset.holSectionName = assetSR.holSectionName;
    asset.holStaffCode = assetSR.holStaffCode;
    asset.holStaffName = assetSR.holStaffName;
    asset.holOfficeCode = assetSR.holOfficeCode;
    asset.holOfficeName = assetSR.holOfficeName;
    asset.holOfficeFloor = assetSR.holOfficeFloor;
    asset.holOfficeRoomNum = assetSR.holOfficeRoomNum;
    asset.holOfficeRackNum = assetSR.holOfficeRackNum;
    asset.holPurposeCode = assetSR.holPurposeCode;
    asset.holPurposeName = assetSR.holPurposeName;
    asset.holOfficeDescription = assetSR.holOfficeDescription;
    asset.holGetStaffCode = assetSR.holGetStaffCode;
    asset.holGetStaffName = assetSR.holGetStaffName;
    asset.holGetCompanyCode = assetSR.holGetCompanyCode;
    asset.holGetCompanyName = assetSR.holGetCompanyName;
    asset.holGetSectionCode = assetSR.holGetSectionCode;
    asset.holGetSectionYear = assetSR.holGetSectionYear;
    asset.holGetSectionName = assetSR.holGetSectionName;
    asset.holGetDate = assetSR.holGetDate;
    asset.holQuantity = assetSR.holQuantity;
    asset.useCompanyCode = assetSR.useCompanyCode;
    asset.useCompanyName = assetSR.useCompanyName;
    asset.useSectionCode = assetSR.useSectionCode;
    asset.useSectionYear = assetSR.useSectionYear;
    asset.useSectionName = assetSR.useSectionName;
    asset.useStaffCode = assetSR.useStaffCode;
    asset.useStaffName = assetSR.useStaffName;
    asset.useStaffCompanyCode = assetSR.useStaffCompanyCode;
    asset.useStaffCompanyName = assetSR.useStaffCompanyName;
    asset.useStaffSectionCode = assetSR.useStaffSectionCode;
    asset.useStaffSectionYear = assetSR.useStaffSectionYear;
    asset.useStaffSectionName = assetSR.useStaffSectionName;
    asset.useStartDate = assetSR.useStartDate;
    asset.useCommonFlag = assetSR.useCommonFlag;
    asset.useCommonFlagName = assetSR.useCommonFlagName;
    asset.netHostName = assetSR.netHostName;
    asset.netEguardPermitType = assetSR.netEguardPermitType;
    asset.mntContractCode = assetSR.mntContractCode;
    asset.mntContractCompanyName = assetSR.mntContractCompanyName;
    asset.mntContractStartDate = assetSR.mntContractStartDate;
    asset.mntContractEndDate = assetSR.mntContractEndDate;
    asset.mntContractAmount = assetSR.mntContractAmount;
    asset.mntContractRegistCode = assetSR.mntContractRegistCode;
    asset.mntContractRegistDate = assetSR.mntContractRegistDate;
    asset.mntContractStaffCode = assetSR.mntContractStaffCode;
    asset.mntContractStaffName = assetSR.mntContractStaffName;
    asset.mntContractServiceLevel = assetSR.mntContractServiceLevel;
    asset.mntContractDescription2 = assetSR.mntContractDescription2;
    asset.mntContractCode2 = assetSR.mntContractCode2;
    asset.mntContractCompanyName2 = assetSR.mntContractCompanyName2;
    asset.mntContractStartDate2 = assetSR.mntContractStartDate2;
    asset.mntContractEndDate2 = assetSR.mntContractEndDate2;
    asset.mntContractAmount2 = assetSR.mntContractAmount2;
    asset.mntContractRegistCode2 = assetSR.mntContractRegistCode2;
    asset.mntContractRegistDate2 = assetSR.mntContractRegistDate2;
    asset.mntContractStaffCode2 = assetSR.mntContractStaffCode2;
    asset.mntContractStaffName2 = assetSR.mntContractStaffName2;
    asset.mntContractServiceLevel2 = assetSR.mntContractServiceLevel2;
    asset.mntContractDescription2 = assetSR.mntContractDescription2;
    asset.mntContractCode3 = assetSR.mntContractCode3;
    asset.mntContractCompanyName3 = assetSR.mntContractCompanyName3;
    asset.mntContractStartDate3 = assetSR.mntContractStartDate3;
    asset.mntContractEndDate3 = assetSR.mntContractEndDate3;
    asset.mntContractAmount3 = assetSR.mntContractAmount3;
    asset.mntContractRegistCode3 = assetSR.mntContractRegistCode3;
    asset.mntContractRegistDate3 = assetSR.mntContractRegistDate3;
    asset.mntContractStaffCode3 = assetSR.mntContractStaffCode3;
    asset.mntContractStaffName3 = assetSR.mntContractStaffName3;
    asset.mntContractServiceLevel3 = assetSR.mntContractServiceLevel3;
    asset.mntContractDescription3 = assetSR.mntContractDescription3;
    asset.dscDescription = assetSR.dscDescription;
    asset.dscAttribute1 = assetSR.dscAttribute1;
    asset.dscAttribute2 = assetSR.dscAttribute2;
    asset.dscAttribute3 = assetSR.dscAttribute3;
    asset.dscAttribute4 = assetSR.dscAttribute4;
    asset.dscAttribute5 = assetSR.dscAttribute5;
    asset.dscAttribute6 = assetSR.dscAttribute6;
    asset.dscAttribute7 = assetSR.dscAttribute7;
    asset.dscAttribute8 = assetSR.dscAttribute8;
    asset.dscAttribute9 = assetSR.dscAttribute9;
    asset.dscAttribute10 = assetSR.dscAttribute10;
    asset.dscAttribute11 = assetSR.dscAttribute11;
    asset.dscAttribute12 = assetSR.dscAttribute12;
    asset.dscAttribute13 = assetSR.dscAttribute13;
    asset.dscAttribute14 = assetSR.dscAttribute14;
    asset.dscAttribute15 = assetSR.dscAttribute15;
    asset.dscAttribute16 = assetSR.dscAttribute16;
    asset.dscAttribute17 = assetSR.dscAttribute17;
    asset.dscAttribute18 = assetSR.dscAttribute18;
    asset.dscAttribute19 = assetSR.dscAttribute19;
    asset.dscAttribute20 = assetSR.dscAttribute20;
    asset.dscFailureAssetId = assetSR.dscFailureAssetId;
    asset.invInCompanyActualFlag = assetSR.invInCompanyActualFlag;
    asset.invInCompanyActualFlagName = assetSR.invInCompanyActualFlagName;
    asset.invBarcode = assetSR.invBarcode;
    asset.invSealIssueFlag = assetSR.invSealIssueFlag;
    asset.invSealIssueFlagName = assetSR.invSealIssueFlagName;
    asset.invSealIssueDate = assetSR.invSealIssueDate;
    asset.invSealIssueDescription = assetSR.invSealIssueDescription;
    asset.deleteFlag = assetSR.deleteFlag;
    asset.deleteDate = assetSR.deleteDate;
    asset.deleteReason = assetSR.deleteReason;
    asset.getApplicationId = assetSR.getApplicationId;
    asset.registApplicationId = assetSR.registApplicationId;
    asset.contractNum = assetSR.contractNum;
    asset.dreamsNum = assetSR.dreamsNum;
    asset.parentAssetId = assetSR.parentAssetId;
    asset.apCreateTel = assetSR.apCreateTel;
    asset.contractEda = assetSR.contractEda;
    asset.shisanNum = assetSR.shisanNum;
    asset.koyunoL = assetSR.koyunoL;
    asset.koyunoF = assetSR.koyunoF;
    asset.assetLineOs = assetSR.assetLineOs;
    asset.assetLineNetwork = assetSR.assetLineNetwork;
    asset.assetLineInv = assetSR.assetLineInv;

    return asset;
  }
}
