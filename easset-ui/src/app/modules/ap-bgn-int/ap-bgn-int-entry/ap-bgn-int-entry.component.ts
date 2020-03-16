import { Component, ComponentFactoryResolver, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { ApBgnIntComponent } from '../ap-bgn-int.component';
import { StatusSelectionComponent } from 'src/app/parts/lov/status-selection/status-selection.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { ApBgnIntService } from 'src/app/services/api/ap-bgn-int.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { ApBgnInt } from 'src/app/models/api/ap-bgn-int/ap-bgn-int.model';
import { User } from 'src/app/models/api/user';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { ApBgnIntLineAtt } from 'src/app/models/api/ap-bgn-int/ap-bgn-int-line-att.model';
import { ApBgnIntLineFld } from 'src/app/models/api/ap-bgn-int/ap-bgn-int-line-fld.model';
import { LineProfEstListComponent } from './line-prof-est-list/line-prof-est-list.component';
import { LineCostSecListComponent } from './line-cost-sec-list/line-cost-sec-list.component';
import { ApGetInt } from 'src/app/models/api/ap-get-int/ap-get-int.model';
import { ApGetIntService } from 'src/app/services/api/ap-get-int.service';
import { MessageService } from 'src/app/services/message.service';
import { SystemConst } from 'src/app/const/system-const';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';
import { SystemMessage } from 'src/app/const/system-message';
import { CodeName } from 'src/app/models/api/code-name';
import { MasterService } from 'src/app/services/api/master.service';
import { ApBgnIntLineCostSec } from 'src/app/models/api/ap-bgn-int/ap-bgn-int-line-cost-sec.model';

@Component({
  selector: 'app-ap-bgn-int-entry',
  templateUrl: './ap-bgn-int-entry.component.html',
  styleUrls: ['./ap-bgn-int-entry.component.scss']
})
export class ApBgnIntEntryComponent extends AbstractChildComponent<ApBgnIntComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * エンティティデータ
   */
  apBgnInt: ApBgnInt;

  /**
   * リモートオブジェクトの初期化
   */
  apGetInt: ApGetInt;

  /**
   * フォーム
   */
  apBgnIntForm: FormGroup;
  /**
   * 申請者-社員番号フォーム
   */
  staffForm: FormGroup;

  /**
   * 申請者-申請部署フォーム
   */
  apSectionForm: FormGroup;

  /**
   * 案件グループ
   */
  astGroupForm: FormGroup;

  /**
   * 資産ﾌﾟﾛｼﾞｪｸﾄｺｰﾄフォーム
   */
  astPrjCodeForm: FormGroup;

  /**
   * 保有部署フォーム
   */
  holSectionForm: FormGroup;

  /**
   * 代表設置場所選択フォーム
   */
  holRepOfficeForm: FormGroup;

  /**
   * 無形管理担当者フォーム
   */
  holSectionStaffForm: FormGroup;

  /**
   * 減価償却プロジェクトコードフォーム
   */
  depPrjCodeForm: FormGroup;

  /**
   * エンティティデータ
   */
  costSectionForm: FormGroup;

  /**
   * アクション
   */
  action = '';

  index = 0;

  accessLevel = '';

  confirmFlag = '';
  visibleStatus = true;

  modeConfirmFlag = {
    delete: '1',
    file: '2'
  };

  mailSetting = '';

  /**
   * サービス提供開始報告タイプ
   */
  apBgnType = '';

  /**
   * Default status
   */
  apStatus = SystemConst.ApStatBgnInt.apStatBgnIntNoApply;
  /**
   * (検索画面の更新判別に使用) 0:新規作成なし、1:更新なし、2:新規作成・更新、3:削除
   */
  modeUpdateFlag = {
    noneCreate: '0',
    noneUpdate: '1',
    update: '2',
    delete: '3'
  };

  /**
   * 更新を行ったかどうか
   */
  updateFlag: string;

  // tslint:disable-next-line:no-any
  lineCostSecRef: any;

  mailToSetting = [];

  /**
   * 利益予測明細
   */
  apBgnIntLineProfEstList = [];
  /**
   * 経費負担部署明細
   */
  apBgnIntLineCostSecList = [];
  /**
   * 資産明細(申請時)
   */
  apBgnIntLineFld1List = [];
  /**
   * 資産明細(承認時)
   */
  apBgnIntLineFld2List = [];
  /**
   * 添付明細
   */
  apBgnIntLineAttList = [];
  /**
   * 添付明細
   */
  apBgnIntLineAttListAC = [];

  readonly ApGetTypeInt = SystemConst.ApGetTypeInt;
  readonly ProjectCategory = SystemConst.ProjectCategory;
  readonly ApBgnType = SystemConst.ApBgnType;
  readonly ApStatBgnInt = SystemConst.ApStatBgnInt;
  /**
   * ステータス選択ポップアップ Ref
   */
  @ViewChild(StatusSelectionComponent, null) popupStatus: StatusSelectionComponent;
  @ViewChild('gridApBgnIntLineFld1', null) gridApBgnIntLineFld1: EaFlexGrid;
  @ViewChild('gridApBgnIntLineFld2', null) gridApBgnIntLineFld2: EaFlexGrid;
  @ViewChild('gridApBgnIntLineAtt', null) gridApBgnIntLineAtt: EaFlexGrid;
  @ViewChild(LineProfEstListComponent, null) lineProfEstListComponent: LineProfEstListComponent;
  @ViewChild(LineCostSecListComponent, null) lineCostSecListComponent: LineCostSecListComponent;
  @ViewChild(ConfirmPopupComponent, null) confirmPopup: ConfirmPopupComponent;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private appBgnIntService: ApBgnIntService,
    private componentFactoryResolver: ComponentFactoryResolver,
    private apGetIntService: ApGetIntService,
    private fileDownloadService: FileDownloadService,
    private messageService: MessageService) {
    super();
  }

  /**
   * Init
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    // Init form
    this.initForm();
    this.changeChildSubject.subscribe(param => {
      const loadApGetIntId = param.params.loadApGetIntId;
      const loadApGetIntVersion = param.params.loadApGetIntVersion;
      this.index = param.params.index;
      this.action = param.action;
      if (this.action === 'initCreate') { // 取得申請書(無形)データ取得
        this.appBgnIntService.getApBgnIntByApGetInt(loadApGetIntId, loadApGetIntVersion)
          .subscribe((resp: ApBgnInt) => {
            this.updateFlag = this.modeUpdateFlag.noneCreate;
            this.apBgnInt = resp;
            this.init();
          });
      } else if (this.action === 'initDetail') { // サービス提供開始報告データ取得
        this.appBgnIntService.getApBgnInt(loadApGetIntId)
          .subscribe((resp: ApBgnInt) => {
            this.updateFlag = this.modeUpdateFlag.noneUpdate;
            this.apBgnInt = resp;
            this.init();
          });
      } else if (this.action === 'initEapp') { // サービス提供開始報告データ取得
        this.appBgnIntService.getApBgnIntEapp(loadApGetIntId)
          .subscribe((resp: ApBgnInt) => {
            this.apBgnInt = resp;
            this.init();
          });
      }
    });
  }

  /**
   * Init form
   */
  initForm() {
    // 申請者 - 社員番号フォーム
    this.staffForm = this.fb.group({
      staffCode: [this.sessionInfo.loginUser.staffCode],
      staffName: [this.sessionInfo.loginUser.name]
    });

    let apSectionCode = '';
    let apSectionName = '';
    if (this.sessionInfo.loginCompanyCode === this.sessionInfo.loginUser.companyCode) {
      // ログインユーザの所属がログイン会社と同じ場合のみ部署セット
      apSectionCode = this.sessionInfo.loginUser.sectionCode;
      apSectionName = this.sessionInfo.loginUser.sectionName;
    }
    // 申請者-申請部署フォーム
    this.apSectionForm = this.fb.group({
      apSectionCode: [apSectionCode],
      apSectionName: [apSectionName]
    });

    // 案件グループフォーム
    this.astGroupForm = this.fb.group({
      astGroupCode: [''],
      astGroupName: [''],
    });

    // 資産ﾌﾟﾛｼﾞｪｸﾄｺｰﾄﾞフォーム
    this.astPrjCodeForm = this.fb.group({
      astPrjName: [''],
      astPrjCode: [''],
      astPrjType: ['']
    });

    // 代表設置場所フォーム
    this.holRepOfficeForm = this.fb.group({
      holRepOfficeCode: [''],
      holRepOfficeName: [''],
    });

    // 保有部署
    this.holSectionForm = this.fb.group({
      holSectionCode: [apSectionCode],
      holSectionName: [apSectionName],
      holSectionYear: [this.sessionInfo.currentYear]
    });

    // 無形管理担当者 - 社員番号
    this.holSectionStaffForm = this.fb.group({
      holStaffCode: [''],
      holStaffName: ['']
    });

    // 減価償却プロジェクトコード
    this.depPrjCodeForm = this.fb.group({
      costDepPrjCode: [''],
      costDepPrjName: [''],
      costDepPrjType: ['']
    });

    // 資産計上部課
    this.costSectionForm = this.fb.group({
      costSectionCode: [''],
      costSectionName: [''],
      costSectionYear: [this.sessionInfo.currentYear]
    });

    this.apBgnIntForm = this.fb.group({
      applicationId: [''],
      applicationVersion: [''],
      createDate: [null],
      createStaffCode: [null],
      updateDate: [null],
      updateStaffCode: [null],
      version: [null],
      displayVersion: [null],
      updateComment: [null],
      eappId: [null],
      apStatus: ['1'],
      apStatusName: ['未申請'],
      apDate: [new Date()],
      apCreateStaffCode: [this.sessionInfo.loginUser.staffCode],
      apCreateStaffName: [this.sessionInfo.loginUser.name],
      apCreateCompanyCode: [this.sessionInfo.loginUser.companyCode],
      apCreateCompanyName: [this.sessionInfo.loginUser.companyName],
      apCreateSectionCode: [this.sessionInfo.loginUser.sectionCode],
      apCreateSectionName: [this.sessionInfo.loginUser.sectionName],
      apCreateSectionYear: [this.sessionInfo.currentYear],
      apCreateTel: [this.sessionInfo.loginUser.tel1],
      apStaffCode: [this.sessionInfo.loginUser.staffCode],
      apStaffName: [this.sessionInfo.loginUser.name],
      apCompanyCode: [this.sessionInfo.loginCompanyCode],
      apCompanyName: [this.sessionInfo.loginCompanyName],
      apSectionCode: [apSectionCode],
      apSectionName: [apSectionName],
      apSectionYear: [this.sessionInfo.currentYear],
      apTel: [this.sessionInfo.loginUser.tel1],
      apSkipApproveFlag: ['0'],
      apGetType: [''],
      apGetTypeName: [''],
      astName: [''],
      astPrjCode: [''],
      astPrjName: [''],
      astPrjType: [''],
      astCloudType: [''],
      astPrjLife: [''],
      astGroupCode: [''],
      astGroupName: [''],
      astMachineCode: [''],
      astProductCode: [''],
      astRealAmount: [0],
      astAppAmount: [0],
      getRealAmount: [0],
      getAppAmount: [0],
      astCompleteDate: [''],
      astReleaseDate: [''],
      mktCatCategoryCode: [''],
      mktCatCategoryName: [''],
      astDescription: [''],
      attDescription: [''],
      holCompanyCode: [''],
      holCompanyName: [''],
      holSectionCode: [''],
      holSectionName: [''],
      holSectionYear: [0],
      holStaffCode: [''],
      holStaffName: [''],
      holStaffCompanyCode: [''],
      holStaffCompanyName: [''],
      holStaffSectionCode: [''],
      holStaffSectionName: [''],
      holStaffSectionYear: [''],
      holRepOfficeCode: [''],
      holRepOfficeName: [''],
      costDepPrjCode: [''],
      costDepPrjName: [''],
      costDepPrjType: [''],
      costCompanyCode: [''],
      costCompanyName: [''],
      costSectionCode: [''],
      costSectionName: [''],
      costSectionYear: [0],
      costType: [''],
      descriptionAp: [''],
      descriptionAdmin: [''],

    });
  }

  /**
   * Init
   */
  init() {
    // Init access level
    this.setAccessLevel();

    if (this.action === 'initCreate') {
      this.initNewApBgnInt();

    } else {
      this.apBgnIntLineAttList = this.apBgnInt.apBgnIntLineAttList;
      // 添付明細
      this.gridApBgnIntLineAtt.collectionView.refresh();
      this.gridApBgnIntLineAtt.resetSelection();
    }

    this.lineProfEstListComponent.apBgnIntLineProfEstList = this.apBgnInt.apBgnIntLineProfEstList;
    this.lineCostSecListComponent.apBgnIntLineCostSecList = this.apBgnInt.apBgnIntLineCostSecList;
    // サービス提供開始報告タイプ
    if (this.apBgnInt.apGetType === SystemConst.ApGetTypeInt.apGetTypeInt1) {
      // 社内使用ソフトウェア
      this.apBgnType = SystemConst.ApBgnType.apBgnTypeInt1;
    } else if (this.apBgnInt.apGetType === SystemConst.ApGetTypeInt.apGetTypeInt2) {
      if (this.apBgnInt.mktCatCategoryCode === '6') {
        // 研究開発
        this.apBgnType = SystemConst.ApBgnType.apBgnTypeInt3;
      } else {
        // 市販目的ソフトウェア
        this.apBgnType = SystemConst.ApBgnType.apBgnTypeInt2;
      }
    }

    this.getMailSetting();
    // 明細データのバインド
    let astTotalAmount = 0; // 取得価額合計
    let getTotalAmount = 0; // 資産計上額合計
    if (this.apBgnInt.apBgnIntLineFldList.length > 0) {
      this.apBgnInt.apBgnIntLineFldList.forEach((item: ApBgnIntLineFld) => {
        if (item.lineType === '1') {
          this.apBgnIntLineFld1List.push(item);
        } else if (item.lineType === '2') {
          this.apBgnIntLineFld2List.push(item);
        }
        astTotalAmount = astTotalAmount + item.astAmount;
        getTotalAmount = getTotalAmount + item.astGetAmount;
      });
    }

    if (this.action === 'initCreate') {
      this.apBgnInt.astRealAmount = astTotalAmount;
      this.apBgnInt.getRealAmount = astTotalAmount;
    }

    this.gridApBgnIntLineAtt.collectionView.refresh();
    this.gridApBgnIntLineAtt.resetSelection();

    this.apBgnIntForm.patchValue(this.apBgnInt);
    this.holSectionForm.patchValue({
      holSectionCode: this.apBgnInt.holSectionCode,
      holSectionName: this.apBgnInt.holSectionName,
      holSectionYear: this.apBgnInt.holSectionYear,
    });

    this.holSectionStaffForm.patchValue({
      holStaffCode: this.apBgnInt.holStaffCode,
      holStaffName: this.apBgnInt.holStaffName,
    });

    this.holRepOfficeForm.patchValue(
      {
        holRepOfficeCode: this.apBgnInt.holRepOfficeCode,
        holRepOfficeName: this.apBgnInt.holRepOfficeName,
      }
    );

    this.astPrjCodeForm.patchValue({
      astPrjCode: this.apBgnInt.astPrjCode,
      astPrjName: this.apBgnInt.astPrjName,
      astPrjType: this.apBgnInt.astPrjType
    });

    this.depPrjCodeForm.patchValue({
      costDepPrjCode: this.apBgnInt.costDepPrjCode,
      costDepPrjName: this.apBgnInt.costDepPrjName,
      costDepPrjType: this.apBgnInt.costDepPrjType
    });

    this.costSectionForm.patchValue({
      costSectionCode: this.apBgnInt.costSectionCode,
      costSectionName: this.apBgnInt.costSectionName,
    });

    this.astGroupForm.patchValue({
      astGroupCode: this.apBgnInt.astGroupCode,
      astGroupName: this.apBgnInt.astGroupName,
    });
  }

  /**
   * Init
   */
  initNewApBgnInt() {
    // ステータス
    this.apBgnInt.apStatus = '1';
    this.apBgnInt.apStatusName = '未申請';
    this.apBgnInt.apDate = new Date();
    // 起票者
    this.apBgnInt.apCreateStaffCode = this.sessionInfo.loginUser.staffCode;
    this.apBgnInt.apCreateStaffName = this.sessionInfo.loginUser.name;
    this.apBgnInt.apCreateCompanyCode = this.sessionInfo.loginUser.companyCode;
    this.apBgnInt.apCreateCompanyName = this.sessionInfo.loginUser.companyName;
    this.apBgnInt.apCreateSectionCode = this.sessionInfo.loginUser.sectionCode;
    this.apBgnInt.apCreateSectionName = this.sessionInfo.loginUser.sectionName;
    this.apBgnInt.apCreateSectionYear = this.sessionInfo.currentYear;
    this.apBgnInt.apCreateTel = this.sessionInfo.loginUser.tel1;
    // 申請者
    this.apBgnInt.apStaffCode = this.sessionInfo.loginUser.staffCode;
    this.apBgnInt.apStaffName = this.sessionInfo.loginUser.name;
    this.apBgnInt.apCompanyCode = this.sessionInfo.loginCompanyCode;
    this.apBgnInt.apCompanyName = this.sessionInfo.loginCompanyName;
    if (this.apBgnInt.apCompanyCode === this.sessionInfo.loginUser.companyCode) { // ログインユーザの所属がログイン会社と同じ場合のみ部署セット
      this.apBgnInt.apSectionCode = this.sessionInfo.loginUser.sectionCode;
      this.apBgnInt.apSectionName = this.sessionInfo.loginUser.sectionName;
    }
    this.apBgnInt.apSectionYear = this.sessionInfo.currentYear;
    this.apBgnInt.apTel = this.sessionInfo.loginUser.tel1;

    this.apBgnInt.eappId = null; // e申請IDクリア

    // 項目クリア
    this.apBgnInt.descriptionAdmin = null;

    if (this.apBgnInt.holSectionYear !== this.sessionInfo.currentYear) { // カレント年度ではない場合部署情報クリア
      // 保有部署クリア
      this.apBgnInt.holSectionCode = null;
      this.apBgnInt.holSectionName = null;
      this.apBgnInt.holSectionYear = null;
      // 経費負担部署クリア
      this.apBgnInt.costCompanyCode = null;
      this.apBgnInt.costSectionCode = null;
      this.apBgnInt.costSectionYear = null;
      this.apBgnInt.costSectionName = null;
      this.apBgnInt.apBgnIntLineCostSecList = [];
    }
    this.apGetInt = new ApGetInt();
    this.apGetInt.applicationId = this.apBgnInt.applicationId;
    this.apGetInt.applicationVersion = this.apBgnInt.applicationVersion;
    this.apGetIntService.createApGetIntPdf([this.apGetInt], true).subscribe(
      (res: NonObjectResponse<string>) => {
        const apBgnIntLineAtt = new ApBgnIntLineAtt();		// 添付明細クリア
        apBgnIntLineAtt.lineSeq = 1;
        apBgnIntLineAtt.createDate = new Date();
        apBgnIntLineAtt.createStaffCode = 'eAsset';
        apBgnIntLineAtt.createStaffName = null;
        apBgnIntLineAtt.attFileIdTmp = res.value;
        apBgnIntLineAtt.attFileName = '取得申請' + this.apBgnInt.applicationId + '.pdf';
        apBgnIntLineAtt.attComment = '自動追加';
        this.apBgnIntLineAttList.push(apBgnIntLineAtt);
        this.apBgnInt.apBgnIntLineAttList = this.apBgnIntLineAttList;
        // 添付明細
        this.gridApBgnIntLineAtt.collectionView.refresh();
        this.gridApBgnIntLineAtt.resetSelection();
      });
  }

  /**
   * Get access level
   */
  setAccessLevel() {
    switch (this.sessionInfo.currentAccessLevel) {
      case 'S':
        this.accessLevel = 'admin';
        break;
      case 'C':
        this.accessLevel = 'general';
        break;
      case 'B':
        this.accessLevel = 'asset_manager';
        break;
      default:
        this.accessLevel = 'category';
        break;
    }
  }

  /**
   * 添付明細クリア
   */
  initGridApBgnIntLineAtt(grid) {
    // Add event listener
    grid.addEventListener(grid.hostElement, 'click', (e) => {
      // If click delete button
      if (e.target.id === 'btnDel') {
        this.confirmFlag = this.modeConfirmFlag.file;
        this.confirmPopup.prompt('添付ファイルを削除してもよろしいでしょうか。', document.activeElement);
      } else if (e.target.name === 'iptComment') {
        e.target.addEventListener('focusout', () => {
          // Get selected row
          const selectRow = this.gridApBgnIntLineAtt.selectedRows[0];
          this.apBgnIntLineAttList = this.apBgnIntLineAttList.map(item => {
            if (item.lineSeq === selectRow.dataItem.lineSeq) {
              item = selectRow.dataItem;
            }
            return item;
          });
          this.gridApBgnIntLineAtt.resetSelection();
        });
      }
    });
  }

  /**
   * ステータス選択後処理
   *
   * @param status 選択ステータス情報
   *
   * @return void
   */
  changeApStatus(status: LovDataEx) {
    this.apStatus = status.code;
    this.apBgnIntForm.controls.apStatus.setValue(status.code);
    this.apBgnIntForm.controls.apStatusName.setValue(status.value1);
    this.visibleStatus = !this.visibleStatus;
  }

  /**
   * 社員選択後処理
   * @param data ユーザー情報
   */
  changeStaffSelection(data: User) {
    if (data) {
      this.apBgnIntForm.controls.apStaffCode.setValue(data.staffCode);
      this.apBgnIntForm.controls.apStaffName.setValue(data.name);
      this.apBgnIntForm.controls.apSectionCode.setValue(data.sectionCode);
      this.apBgnIntForm.controls.apSectionName.setValue(data.sectionName);
      this.apBgnIntForm.controls.apTel.setValue(data.tel1);
      this.apSectionForm.patchValue({
        apSectionCode: data.sectionCode,
        apSectionName: data.sectionName
      });
    } else {
      this.apBgnIntForm.controls.apStaffCode.setValue('');
      this.apBgnIntForm.controls.apStaffName.setValue('');
      this.apBgnIntForm.controls.apSectionCode.setValue('');
      this.apBgnIntForm.controls.apSectionName.setValue('');
      this.apBgnIntForm.controls.apTel.setValue('');
      this.apSectionForm.patchValue({
        apSectionCode: '',
        apSectionName: ''
      });
    }

  }

  /**
   * 申請部署
   */
  changeApSection(data) {
    this.apBgnIntForm.controls.apSectionCode.setValue(data.sectionCode);
    this.apBgnIntForm.controls.apSectionName.setValue(data.sectionName);
  }

  /**
   * 保有部署
   * Set data when selected change
   */
  changeHolSection(data) {
    this.apBgnIntForm.controls.holSectionCode.setValue(data.sectionCode);
    this.apBgnIntForm.controls.holSectionName.setValue(data.sectionName);
    this.apBgnIntForm.controls.holSectionYear.setValue(data.sectionYear);
  }

  /**
   * 社員番号
   * Set data when selected change
   */
  changeHolSectionStaff(data) {
    this.apBgnIntForm.controls.holStaffCode.setValue(data.staffCode);
    this.apBgnIntForm.controls.holStaffName.setValue(data.name);
    this.apBgnIntForm.controls.holStaffCompanyCode.setValue(data.companyCode);
    this.apBgnIntForm.controls.holStaffCompanyName.setValue(data.companyName);
    this.apBgnIntForm.controls.holStaffSectionCode.setValue(data.sectionCode);
    this.apBgnIntForm.controls.holStaffSectionName.setValue(data.sectionName);
    this.apBgnIntForm.controls.holStaffSectionYear.setValue(this.sessionInfo.currentYear);
  }

  /**
   * 資産計上部課
   * Set data when selected change
   */
  selectCostSection(data) {
    if (!data) {
      return;
    }

    this.apBgnIntForm.controls.costSectionCode.setValue(data.sectionCode);
    this.apBgnIntForm.controls.costSectionName.setValue(data.sectionName);
    this.apBgnIntForm.controls.costSectionYear.setValue(this.sessionInfo.currentYear);
    this.apBgnIntForm.controls.costCompanyCode.setValue(this.sessionInfo.loginCompanyCode);
    this.apBgnIntForm.controls.costCompanyName.setValue(this.sessionInfo.loginCompanyName);
  }

  /**
   * 減価償却プロジェクトコード
   */
  changeCostDepPrj(data) {
    this.apBgnIntForm.controls.costDepPrjCode.setValue(data.code);
    this.apBgnIntForm.controls.costDepPrjName.setValue(data.name);
    this.apBgnIntForm.controls.costDepPrjType.setValue(data.value1);
  }

  /**
   * 代表設置場所
   */
  updateHolRepOffice(data) {
    this.apBgnIntForm.controls.holRepOfficeCode.setValue(data.code);
    this.apBgnIntForm.controls.holRepOfficeName.setValue(data.name);
  }

  /**
   * 案件グループ
   */
  updateAstGroup(data) {
    this.apBgnIntForm.controls.astGroupCode.setValue(data.code);
    this.apBgnIntForm.controls.astGroupName.setValue(data.name);
  }

  /**
   * ファイル選択(精査部署設定更新)
   *
   * @param files ファイル
   *
   * @return なし
   */
  uploadFile(file) {
    if (!file) {
      return;
    }

    const apBgnIntLineAtt = new ApBgnIntLineAtt();
    apBgnIntLineAtt.lineSeq = (this.apBgnIntLineAttList.length + 1);
    apBgnIntLineAtt.attFileIdTmp = file.fileId;
    apBgnIntLineAtt.createStaffCode = this.sessionInfo.loginUser.staffCode;
    apBgnIntLineAtt.createStaffName = this.sessionInfo.loginUser.name;
    apBgnIntLineAtt.attFileName = file.fileName;
    apBgnIntLineAtt.createDate = new Date();
    this.apBgnIntLineAttList.push(apBgnIntLineAtt);
    this.apBgnInt.apBgnIntLineAttList = this.apBgnIntLineAttList;
    this.gridApBgnIntLineAtt.collectionView.refresh();
    this.gridApBgnIntLineAtt.resetSelection();
  }

  /**
   * Download file
   */
  downloadFile(file) {
    this.fileDownloadService.download(file.attFileIdTmp, file.attFileName, 'application/pdf');
  }

  /**
   * 最新の資産情報を読込
   */
  reloadFld() {
    this.appBgnIntService.getPpfsFldList(this.sessionInfo.loginCompanyCode, this.apBgnInt.applicationId, '2').subscribe(
      (resp: ApBgnIntLineFld[]) => {
        if (resp.length > 0) {
          // 既存データ存在チェック用
          const keyMapFld1 = [];
          const keyMapFld2 = [];
          this.apBgnIntLineFld1List.forEach((item: ApBgnIntLineFld) => {
            keyMapFld1[item.ppId] = '1';
          });

          this.apBgnIntLineFld2List.forEach((item: ApBgnIntLineFld) => {
            keyMapFld2[item.ppId] = '1';
          });
          // 明細データのバインド
          let astTotalAmount = 0; // 取得価額合計
          let getTotalAmount = 0; // 資産計上額合計
          resp.forEach((item: ApBgnIntLineFld) => {
            let addFlag = true;
            // データのセット
            if (keyMapFld1.hasOwnProperty(item.ppId)) { // 既存データ有り？
              addFlag = false;
            }
            if (addFlag) {
              if (this.apStatus === SystemConst.ApStatBgnInt.apStatBgnIntNoApply
                || this.apStatus === SystemConst.ApStatBgnInt.apStatBgnIntSendBack) {
                item.lineType = '1';
                this.apBgnIntLineFld1List.push(item);
              } else {
                item.lineType = '2';
                this.apBgnIntLineFld2List.push(item);
              }
            }

            astTotalAmount = astTotalAmount + item.astAmount;
            getTotalAmount = getTotalAmount + item.astGetAmount;
          });

          this.apBgnInt.astRealAmount = astTotalAmount;
          this.apBgnInt.getRealAmount = astTotalAmount;
        }
      }
    );
  }

  /**
   * 資産計上部課
   */
  changeLineCostSec(data: ApBgnIntLineCostSec[]) {
    if (data.length > 0) {
      if (data[0].costSectionCode && data[0].costSectionCode !== '') {
        this.costSectionForm.patchValue({
          costSectionCode: data[0].costSectionCode,
          costSectionName: data[0].costSectionName
        });
        this.apBgnIntForm.controls.costSectionCode.setValue(data[0].costSectionCode);
        this.apBgnIntForm.controls.costSectionName.setValue(data[0].costSectionName);
        this.apBgnIntForm.controls.costSectionYear.setValue(this.sessionInfo.currentYear);
        this.apBgnIntForm.controls.costCompanyCode.setValue(this.sessionInfo.loginCompanyCode);
        this.apBgnIntForm.controls.costCompanyName.setValue(this.sessionInfo.loginCompanyName);
      }
    }
  }

  /**
   * Get Value
   */
  getApBgnIntValue() {
    let apBgnIntVal = this.apBgnIntForm.getRawValue();
    let apBgnIntLineFldList: ApBgnIntLineFld[] = [];

    if (this.apBgnIntLineFld1List.length > 0) {
      apBgnIntLineFldList = apBgnIntLineFldList.concat(this.apBgnIntLineFld1List);
    }

    if (this.apBgnIntLineFld2List.length > 0) {
      apBgnIntLineFldList = apBgnIntLineFldList.concat(this.apBgnIntLineFld2List);
    }

    apBgnIntVal = {
      ...apBgnIntVal, ...{
        apBgnIntLineProfEstList: this.lineProfEstListComponent.apBgnIntLineProfEstList,
        apBgnIntLineFldList: apBgnIntLineFldList,
        apBgnIntLineAttList: this.apBgnIntLineAttList,
        apBgnIntLineCostSecList: this.lineCostSecListComponent.apBgnIntLineCostSecList
      }
    };

    return this.apBgnInt = apBgnIntVal;
  }

  /**
   * データ作成
   */
  save() {
    this.getApBgnIntValue();
    if (this.updateFlag === this.modeUpdateFlag.noneCreate) {
      this.appBgnIntService.createApBgnInt(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, this.apBgnInt).subscribe(
        (res: NonObjectResponse<string>) => {
          this.updateFlag = this.modeUpdateFlag.update;
          this.messageService.info(`取得申請書番号「${this.apBgnInt.applicationId}」で申請書が作成されました。`);
          this.getApBgnInt(this.apBgnInt.applicationId);
          this.init();
        }
      );
    } else {
      this.appBgnIntService.updateApBgnInt(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, this.apBgnInt).subscribe(
        (res: NonObjectResponse<string>) => {
          this.updateFlag = this.modeUpdateFlag.update;
          this.messageService.info(`保存しました。`);
          this.getApBgnInt(this.apBgnInt.applicationId);
          this.init();
        }
      );
    }
  }

  /**
   * 再読み込み
   */
  getApBgnInt(applicationId) {
    this.appBgnIntService.getApBgnInt(applicationId)
      .subscribe((resp: ApBgnInt) => {
        this.apBgnInt = resp;
      });
  }

  /**
   * データ更新
   */
  apply() {
    this.getApBgnIntValue();
    this.appBgnIntService.applyApBgnInt(
      this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel,
      this.apBgnInt,
      this.updateFlag === this.modeUpdateFlag.noneCreate ? true : false
    ).subscribe(
      (res: NonObjectResponse<string>) => {
        this.updateFlag = this.modeUpdateFlag.update;
        if (this.updateFlag === this.modeUpdateFlag.noneCreate) {
          this.messageService.info(`取得申請書番号「${this.apBgnInt.applicationId}」で申請書が作成されて承認依頼を行いました。`);
        } else {
          this.messageService.info(`保存して承認依頼を行いました。`);
        }
        this.getApBgnInt(this.apBgnInt.applicationId);
        this.init();
      }
    );
  }

  /**
   * 遷移元画面へ戻る
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexApGetIntResult, {
      action: 'back',
      params: {
        index: this.index
      }
    });
  }

  /**
   * データ変更あり時クローズ確認
   */
  confirm(confirm: boolean = false) {
    if (!confirm) {
      this.confirmFlag = this.modeConfirmFlag.delete;
      this.confirmPopup.prompt(SystemMessage.Info.msg100021, document.activeElement);
      return;
    }

    // 削除
    if (this.confirmFlag === this.modeConfirmFlag.delete) {
      // 削除ボタン選択時確認ハンドラ
      this.appBgnIntService.deleteApBgnInt(
        this.sessionInfo.loginUser.staffCode,
        this.sessionInfo.currentAccessLevel,
        this.apBgnInt).subscribe(
        (res: NonObjectResponse<string>) => {
          this.updateFlag = this.modeUpdateFlag.delete;
          this.messageService.info(`削除しました。`);
          this.goBack();
        }
      );
    } else if (this.confirmFlag === this.modeConfirmFlag.file) {
      // Get selected row
      const selectRow = this.gridApBgnIntLineAtt.selectedRows[0];
      // Remove row
      this.apBgnIntLineAttList = this.apBgnIntLineAttList.filter((item) => {
        return item.lineSeq !== selectRow.dataItem.lineSeq;
      });
      // Reset grid
      this.gridApBgnIntLineAtt.resetSelection();
    }
  }

  /**
   *  メール
   */
  mailTo(isMailTo = false) {
    let mailTo = `mailto:${this.sessionInfo.loginUser.mailAddress}`;
    let subject = '';
    let body = '';

    if (isMailTo) {
      // 予約語の置換（申請者名、申請書番号、申請ステータス）
      const mailSetting = this.mailToSetting[this.apBgnType + '_' + this.apStatus];
      if (mailSetting != null) {
        subject = mailSetting.value4;
        body = mailSetting.value5;

        // 予約語の置換（申請者名、申請書番号、申請ステータス）
        subject = subject.replace('${申請者名}', this.sessionInfo.loginUser.name);
        subject = subject.replace('${申請書番号}', this.apBgnIntForm.controls.applicationId.value);
        subject = subject.replace('${申請ステータス}', this.apBgnIntForm.controls.apStatusName.value);
        body = body.replace('${申請者名}', this.sessionInfo.loginUser.name);
        body = body.replace('${申請書番号}', this.apBgnIntForm.controls.applicationId.value);
        body = body.replace('${申請ステータス}', this.apBgnIntForm.controls.apStatusName.value);
      }
    }

    if (subject !== '') {
      mailTo = mailTo + '?' + `subject= ${encodeURIComponent(subject)}`;
    }

    if (body !== '') {
      mailTo = mailTo + (subject !== '' ? '&' : '?') + `body= ${encodeURIComponent(body)}`;
    }

    window.location.href = mailTo;
  }

  /**
   * 印刷(e申請画面から)
   */
  print() {
    // Set print param
    const printParam = [this.apBgnInt.applicationId.toString()];

    // Call service create pdf file
    this.appBgnIntService.createApBgnIntPdf(printParam).subscribe((resp: NonObjectResponse<string>) => {
      const fileID = resp.value;
      // Call service preview file
      this.fileDownloadService.preview(fileID, 'application/pdf');
    });
  }

  /**
   * 履歴画面呼び出し
   */
  goToHist() {
    this.parent.changeChild(this.parent.viewIndexHist, {
      action: 'init',
      params: {
        item: this.apBgnInt
      }
    });
  }

  /**
   * メール送信設定あり
   */
  getMailSetting() {
    // 社内使用ソフトウェア
    let categoryCode = SystemConst.CategoryCodeApAttention.apAttentionBgnInt1;
    // 社内使用ソフトウェア
    if (this.apBgnType === SystemConst.ApBgnType.apBgnTypeInt1) {
      categoryCode = SystemConst.CategoryCodeApAttention.apAttentionBgnInt1;
    } else if (this.apBgnType === SystemConst.ApBgnType.apBgnTypeInt2) { // 市販目的ソフトウェア
      categoryCode = SystemConst.CategoryCodeApAttention.apAttentionBgnInt2;
    } else if (this.apBgnType === SystemConst.ApBgnType.apBgnTypeInt3) { // 研究開発
      categoryCode = SystemConst.CategoryCodeApAttention.apAttentionBgnInt2;
    }

    const companyCode = this.sessionInfo.loginCompanyCode;
    this.masterService.getCodeNameList(categoryCode, null, companyCode)
      .subscribe(
        (resp: CodeName[]) => {
          resp.map((item: CodeName) => {
            this.mailToSetting[this.apBgnType + '_' + item.value2] = null;
            if (item.value3 === '1') {
              this.mailToSetting[this.apBgnType + '_' + item.value2] = item; // メール送信設定あり
            }
          });
        }
      );
  }
}
