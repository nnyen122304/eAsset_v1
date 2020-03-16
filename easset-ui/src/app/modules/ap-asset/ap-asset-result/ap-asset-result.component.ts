import { Component, OnInit, ViewChild } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { DatePipe } from '@angular/common';
import { ApAssetResultListComponent } from 'src/app/parts/screen/ap-asset-result-list/ap-asset-result-list.component';
import { DownloadOptionPropComponent } from 'src/app/parts/option/download-option-prop/download-option-prop.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApAssetComponent } from '../ap-asset.component';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { SystemMessage } from 'src/app/const/system-message';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { Asset } from 'src/app/models/api/asset/asset.model';

@Component({
  selector: 'app-ap-asset-result',
  templateUrl: './ap-asset-result.component.html',
  styleUrls: ['./ap-asset-result.component.scss']
})
export class ApAssetResultComponent extends AbstractChildComponent<ApAssetComponent> implements OnInit {

  constructor(
    private sessionService: SessionService,
    private messageService: MessageService,
    private apAssetService: ApAssetService,
    private datePipe: DatePipe,
    private fileDownloadService: FileDownloadService,
  ) {
    super();
  }

  /**
   * 検索
   */
  searchModel = {
    getApplicationId: '',
    assetId: ''
  };

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索条件
   */
  searchParamSC: AssetSC = new AssetSC();

  /**
   * リスト
   */
  listAssetSR: AssetSR[] = [];

  @ViewChild('gridDataResultList', null) gridDataResultList: EaFlexGrid;
  @ViewChild(ApAssetResultListComponent) dataResultList: ApAssetResultListComponent;
  @ViewChild(DownloadOptionPropComponent, null) downloadOptionPropComponent: DownloadOptionPropComponent;

  /**
   * 初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initResult') {
        this.listAssetSR = param.params.dataList;
        this.searchParamSC = param.params.searchParam;
      } else if (param.action === 'back') {
        if (param.params.updateRow) {
          this.dataResultList.updateRow(param.params.updateRow);
        }
      } else {
        this.goBack();
      }
    });
  }

  /**
   * 返す
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexSearch, {
      action: 'backSearch',
      params: {
        searchParam: this.searchParamSC
      },
    });
  }

  /**
   * アクセスレベルを取得する
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
   * に移動
   */
  clickRow(row) {
    if (!row || !row.dataItem) {
      return;
    }
    this.parent.changeChild(this.parent.viewIndexSearch, {
      action: 'ApAssetSearch',
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
    this.apAssetService.searchAsset(
      this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel,
      this.searchParamSC, true).subscribe(
      (resp: AssetSR[]) => {
        if (resp.length > 0) {
          this.listAssetSR = resp;
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
          return;
        }
      }
    );
  }

  /**
   * 検索
   */
  search() {
    if (this.searchModel.getApplicationId === '' && this.searchModel.assetId === '') {
      this.messageService.err(SystemMessage.Err.msg30030);
      return;
    }

    this.searchParamSC = {...this.searchParamSC, ...this.searchModel};
    this.searchAsset();
  }

  /**
   * 申請
   */
  bulkApply() {
    const assetList = this.dataResultList.getDataSelected();

    if (assetList === null && assetList.length === 0) {
      this.messageService.err(SystemMessage.Err.msg30038('処理対象'));
      return;
    }

    const applyAsset: Asset[] = [];
    assetList.map((item) => {
      applyAsset.push(this.parseToAsset(item));
    });

    this.apAssetService.bulkApplyApAsset(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, applyAsset).subscribe(
      () => {
        this.messageService.info(SystemMessage.Info.msg100024);
        this.searchAsset();
      });
  }

  /**
   * 削除
   */
  bulkDelete() {
    const assetList = this.dataResultList.getDataSelected();

    if (assetList === null && assetList.length === 0) {
      this.messageService.err(SystemMessage.Err.msg30038('処理対象'));
      return;
    }

    const delAsset: Asset[] = [];
    assetList.map((item) => {
      delAsset.push(this.parseToAsset(item));
    });

    this.apAssetService.bulkDeleteApAsset(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, delAsset).subscribe(
      () => {
        this.messageService.info(SystemMessage.Info.msg100022);
        this.searchAsset();
      });
  }

  /**
   * 情報機器等新規登録
   */
  bulkAssetCreate() {
    const assetList = this.dataResultList.getDataSelected();

    if (assetList === null && assetList.length === 0) {
      this.messageService.err(SystemMessage.Err.msg30038('処理対象'));
      return;
    }

    const creAsset: Asset[] = [];
    assetList.map((item) => {
      creAsset.push(this.parseToAsset(item));
    });

    this.apAssetService.bulkCreateAsset(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, creAsset).subscribe(
      () => {
        this.messageService.info(SystemMessage.Info.msg100023);
        this.searchAsset();
      });
  }

  /**
   * ﾀﾞｳﾝﾛｰﾄﾞ(全件)
   */
  download(data) {
    const item = data.item ? data.item : [];
    this.searchParamSC.dowloadLineItem = data.lineItem ? data.lineItem : '';
    this.apAssetService.createAssetCsv(
      this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel,
      item,
      this.searchParamSC,
      true).subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileId = resp.value;
        const contentType = 'text/csv';
        const fileName = '情報機器登録申請検索結果_ ' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        this.fileDownloadService.download(fileId, fileName, contentType);
      });
  }

  /**
   * AssetSRからAssetに変換する
   */
  parseToAsset(assetSR) {
    const asset = new Asset();

    asset.updateStaffCode = assetSR.updateStaffCode;
    asset.createDate = assetSR.createDate;
    asset.updateDate = assetSR.updateDate;
    asset.version = assetSR.version;
    asset.eappId = assetSR.eappId;
    asset.astName = assetSR.astName;
    asset.updateComment = assetSR.updateComment;
    asset.astSerialManageType = assetSR.astSerialManageType;
    asset.apCompanyCode = assetSR.apCompanyCode;
    asset.apCompanyName = assetSR.apCompanyName;
    asset.astShapeCode = assetSR.astShapeCode;
    asset.astSerialCode = assetSR.astSerialCode;
    asset.astQuantityManageType = assetSR.astQuantityManageType;
    asset.apCreateCompanyCode = assetSR.apCreateCompanyCode;
    asset.apCreateCompanyName = assetSR.apCreateCompanyName;
    asset.apCreateSectionCode = assetSR.apCreateSectionCode;
    asset.apCreateSectionName = assetSR.apCreateSectionName;
    asset.apCreateSectionYear = assetSR.apCreateSectionYear;
    asset.apCreateStaffCode = assetSR.apCreateStaffCode;
    asset.apCreateStaffName = assetSR.apCreateStaffName;
    asset.apCreateTel = assetSR.apCreateTel;
    asset.apDate = assetSR.apDate;
    asset.astShapeName = assetSR.astShapeName;
    asset.apGetTanLineAstId = assetSR.apGetTanLineAstId;
    asset.apGetTanLineAstLineSeq = assetSR.apGetTanLineAstLineSeq;
    asset.apOfficeName = assetSR.apOfficeName;
    asset.apSectionName = assetSR.apSectionName;
    asset.apSectionCode = assetSR.apSectionCode;
    asset.apSectionYear = assetSR.apSectionYear;
    asset.apStaffCode = assetSR.apStaffCode;
    asset.apStaffName = assetSR.apStaffName;
    asset.apStatus = assetSR.apStatus;
    asset.apStatusName = assetSR.apStatusName;
    asset.apTel = assetSR.apTel;
    asset.assetId = assetSR.assetId;
    asset.assetLineComUsr = assetSR.assetLineComUsr;
    asset.assetLineInv = assetSR.assetLineInv;
    asset.assetLineNetwork = assetSR.assetLineNetwork;
    asset.assetLineOs = assetSR.assetLineOs;
    asset.astShopName = assetSR.astShopName;
    asset.astAssetType = assetSR.astAssetType;
    asset.astAssetTypeName = assetSR.astAssetTypeName;
    asset.astCategory1Code = assetSR.astCategory1Code;
    asset.astCategory1Name = assetSR.astCategory1Name;
    asset.astCategory2Code = assetSR.astCategory2Code;
    asset.astCategory2Name = assetSR.astCategory2Name;
    asset.astGetType = assetSR.astGetType;
    asset.astGetTypeName = assetSR.astGetTypeName;
    asset.astGuaranteeTermDate = assetSR.astGuaranteeTermDate;
    asset.astHolderCode = assetSR.astHolderCode;
    asset.astHolderName = assetSR.astHolderName;
    asset.astMakerCode = assetSR.astMakerCode;
    asset.astMakerModel = assetSR.astMakerModel;
    asset.astMakerName = assetSR.astMakerName;
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
    asset.astSystemSectionDeployFlag = assetSR.astSystemSectionDeployFlag;
    asset.astSystemSectionDeployFlagName = assetSR.astSystemSectionDeployFlagName;
    asset.mntContractServiceLevel = assetSR.mntContractServiceLevel;
    asset.mntContractDescription = assetSR.mntContractDescription;
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
    asset.assetLineComUsr = assetSR.assetLineComUsr;
    asset.assetLineOs = assetSR.assetLineOs;
    asset.assetLineNetwork = assetSR.assetLineNetwork;
    asset.assetLineInv = assetSR.assetLineInv;

    return asset;
  }
}
