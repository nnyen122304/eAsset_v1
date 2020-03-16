import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';
import { InventoryComponent } from '../../inventory.component';
import { StatusSelectionComponent } from 'src/app/parts/lov/status-selection/status-selection.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import * as wjGrid from 'wijmo/wijmo.grid';
import * as wjGridFilter from 'wijmo/wijmo.grid.filter';

import { SessionService } from 'src/app/services/session.service';
import { SiteAccessService } from 'src/app/services/site-access.service';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';
import { InvService } from 'src/app/services/api/inv.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { FileUploadService } from 'src/app/services/api/file-upload.service';

import { SessionInfo } from 'src/app/models/session-info';
import { InvLine } from 'src/app/models/api/inv/inv-line';
import { InvLineEx } from 'src/app/models/api/inv/inv-line-ex';
import { InvSumSR } from 'src/app/models/api/inv/inv-sum-sr';
import { InvSumSC } from 'src/app/models/api/inv/inv-sum-sc';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { CodeName } from 'src/app/models/api/code-name';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { DeepCopy } from 'src/app/utils/deep-copy';

import { SystemMessage } from 'src/app/const/system-message';
import { SystemConst } from 'src/app/const/system-const';
import { CollectionView } from 'wijmo/wijmo';

/**
 * 棚卸資産別一覧コンポネント
 */

@Component({
  selector: 'app-asset-list',
  templateUrl: './asset-list.component.html',
  styleUrls: ['./asset-list.component.scss']
})
export class AssetListComponent extends AbstractChildComponent<InventoryComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索条件フォーム
   */
  itemForm: FormGroup;

  /**
   * 絞込フォーム
   */
  filterForm: FormGroup;

  /**
   * 資産データ（本来）
   */
  searchInvLineOriginal: InvLineEx[];

  /**
   * 資産データ（編集済み）
   */
  searchInvLineList: InvLineEx[];

  /**
   * 検索する資産データ
   */
  item: InvSumSR;

  /**
   * 検索条件
   */
  searchScopeType: string;

  /**
   * 検索リクエスト用条件パラメータ
   */
  searchParam: InvSumSC;

  /**
   * 行番号
   */
  rowID: number;

  /**
   * コードネーム一覧データ
   */
  codeNameList: CodeName[];

  /**
   * カテゴリコード
   */
  categoryCode: string;

  /**
   * 説明文章
   */
  descriptionText: string;

  /**
   * 検索リクエスト用履歴フラグ
   */
  histPeriodFlag: boolean;

  /**
   * 表示切替用値
   */
  view: string;

  /**
   * 選択ファイル
   */
  fileToUpload: File = null;

  /**
   * 絞込ステータス一覧データ
   */
  filterStatus: string[] = ['未実施', '有', '無'];

  /**
   * セル編集を許可するか判定用値
   */
  allowEdit = false;

  /**
   * ステータス変更可能か判定用値
   */
  allowStatus = false;

  /**
   * 保存ボタン表示判定用値
   */
  allowSave = false;

  /**
   * インポートボタン表示判定用値
   */
  allowImport = false;


  /**
   * 戻るボタン表示判定用値
   */
  allowBack = true;

  /**
   * データが保存されたか判定
   */
  hasBeenSaved = false;

  /**
   * 読み込み中認識用値
   */
  isLoading = false;

  /**
   * 全「有」チェックボックス選択用
   */
  checkboxSelectAll1 = false;

  /**
   * 全「無」チェックボックス選択用
   */
  checkboxSelectAll2 = false;

  /**
   * グリッド Ref
   */
  @ViewChild('gridAssetList', null) gridAssetList: EaFlexGrid;

  /**
   * 確認ポップアップ Ref
   */
  @ViewChild('confirmPopupExport', null) confirmPopupExport: ConfirmPopupComponent;

  /**
   * ステータス選択ポップアップ Ref
   */
  @ViewChild(StatusSelectionComponent, null) popupStatus: StatusSelectionComponent;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private sessionService: SessionService,
    private masterService: MasterService,
    private siteAccessService: SiteAccessService,
    private messageService: MessageService,
    private invService: InvService,
    private fileDownloadService: FileDownloadService,
    private fileUploadService: FileUploadService,
  ) {
    super();
    this.fb = fb;
  }

  /**
   * 画面読み込み
   */
  ngOnInit() {
    this.initForms();
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initAsset') {
        this.item = DeepCopy(param.params.item);
        this.searchScopeType = param.params.searchScopeType;
        this.searchParam = param.params.searchParam;
        this.codeNameList = param.params.codeNameList;
        this.categoryCode = param.params.categoryCode;
        this.histPeriodFlag = param.params.histPeriodFlag;
        this.rowID = param.params.rowID;
        this.view = param.params.view;
        this.toggleDisplay();
        this.init();
      } else if (param.action === 'initRef') {
        const sr = new InvSumSR();
        Object.assign(sr, JSON.parse(param.params.params.params));
        sr.apDate = new Date(sr.apDate);
        this.item = sr;
        this.initNewWindow();
      }
    });
  }

  /**
   * 初期処理
   */
  init() {

    // ワーニング文章崇徳
    this.getDescription(this.item.apStatus);

    // フォーム値設定
    this.itemForm.controls.period.setValue(this.item.period);
    this.itemForm.controls.holSectionName.setValue(this.item.holSectionName);
    this.itemForm.controls.invAssetTypeName.setValue(this.item.invAssetTypeName);
    this.itemForm.controls.apStatus.setValue(this.item.apStatus);
    this.itemForm.controls.apStatusName.setValue(this.item.apStatusName);
    this.itemForm.controls.eappId.setValue(this.item.eappId);

    // 一覧取得
    const loginStaffCode = this.sessionInfo.loginUser.staffCode;
    const accessLevel = this.sessionInfo.currentAccessLevel;
    const searchParam = DeepCopy(this.item);
    delete searchParam.sel;
    const searchScopeType = this.searchScopeType;

    this.invService.searchInvLine(loginStaffCode, accessLevel, searchParam, searchScopeType)
    .subscribe(
      (data: InvLine[]) => {
        this.searchInvLineList = data.map((obj: InvLine) => {
          obj.selInvStatus1 = obj.invStatus1 === '1' ? true : false;
          obj.selInvStatus2 = obj.invStatus2 === '1' ? true : false;
          return obj;
        });

        // this.searchInvLineList = DeepCopy(this.searchInvLineOriginal);
        this.gridAssetList.resetSelection();
      }
    );

  }

  /**
   * 表示切り替え
   */
  toggleDisplay() {
    switch (this.view) {
      case 'admin': // S: ADMIN
        if (!this.histPeriodFlag) {
          if (this.item.apStatusName === '未申請' || this.item.apStatusName === '未申請(再)') {
            this.allowSave = true;
            this.allowImport = true;
            this.allowStatus = true;
            this.allowEdit = true;
          } else {
            this.allowSave = true;
            this.allowImport = false;
            this.allowStatus = true;
            this.allowEdit = false;
          }
        } else {
          this.allowSave = false;
          this.allowImport = false;
          this.allowStatus = false;
          this.allowEdit = false;
        }
        break;
      case 'general': // C: 一般
        this.allowSave = false;
        this.allowImport = false;
        this.allowStatus = false;
        this.allowEdit = false;
        if (!this.histPeriodFlag) {
          if (this.searchScopeType === '1') {
            if (this.item.apStatusName === '未申請' || this.item.apStatusName === '未申請(再)') {
              this.allowSave = true;
              this.allowImport = true;
              this.allowStatus = false;
              this.allowEdit = true;
            }
          }
        }
        break;
      case 'asset_manager': // B: 資産管理担当者
        this.allowSave = false;
        this.allowImport = false;
        this.allowStatus = false;
        this.allowEdit = false;
        if (!this.histPeriodFlag) {
          if (this.searchScopeType === '1') {
            if (this.item.apStatusName === '未申請' || this.item.apStatusName === '未申請(再)') {
              this.allowSave = true;
              this.allowImport = true;
              this.allowStatus = false;
              this.allowEdit = true;
            }
          }
        }
        break;
      default: // general - A: 資産管理者
        if (!this.histPeriodFlag) {
          if (this.item.apStatusName === '未申請' || this.item.apStatusName === '未申請(再)') {
            this.allowSave = true;
            this.allowImport = true;
            this.allowStatus = false;
            this.allowEdit = true;
          } else if (this.item.apStatusName === '申請中') {
            this.allowSave = false;
            this.allowImport = false;
            this.allowStatus = true;
            this.allowEdit = false;
          } else { // Status = 承認済
            this.allowSave = false;
            this.allowImport = false;
            this.allowStatus = false;
            this.allowEdit = false;
          }
        } else {
          this.allowSave = false;
          this.allowImport = false;
          this.allowStatus = false;
          this.allowEdit = false;
        }
        break;
    }
  }

  /**
   * 別ウインドウで開いた時の初期処理
   */
  initNewWindow() {
    this.searchScopeType = '1';
    const companyCode = this.sessionInfo.loginCompanyCode;
    const typeName = this.item.invAssetTypeName;
    const period = this.item.period;

    switch (typeName) {
      case '有形固定資産':
        this.categoryCode = 'AP_ATTENTION_INV_FLD_TAN';
        break;
      case '資産除去費用':
        this.categoryCode = 'AP_ATTENTION_INV_FLD_RO';
        break;
      case '無形固定資産(本勘定)':
        this.categoryCode = 'AP_ATTENTION_INV_FLD_INT';
        break;
      case '無形固定資産(仮勘定)':
        this.categoryCode = 'AP_ATTENTION_INV_FLD_INT_S';
        break;
      case 'リース資産':
        this.categoryCode = 'AP_ATTENTION_INV_LE';
        break;
      case 'レンタル資産':
        this.categoryCode = 'AP_ATTENTION_INV_RE';
        break;
      case '備品等':
        this.categoryCode = 'AP_ATTENTION_INV_EQUIP';
        break;
    }

    switch (this.sessionInfo.currentAccessLevel) {
      case 'S':
        this.view = 'admin';
        break;
      case 'C':
        this.view = 'general';
        break;
      case 'B':
        this.view = 'asset_manager';
        break;
      default:
        this.view = 'category';
        break;
    }

    this.invService.getInvHistPeriodFlag(companyCode, period)
    .subscribe(
      (resp: NonObjectResponse<string>) => {
        this.histPeriodFlag = JSON.parse(resp.value);
        this.masterService.getCodeNameList(this.categoryCode, null, companyCode)
        .subscribe((codeNameList: CodeName[]) => {
          this.codeNameList = codeNameList;
          this.allowEdit = false;
          this.allowSave = false;
          this.allowStatus = false;
          this.allowImport = false;
          this.allowBack = false;
          this.init();
        });
      });
  }

  /**
   * フォーム初期設定
   */
  initForms() {
    this.sessionService.setDataChange(false);
    this.hasBeenSaved = false;
    this.searchInvLineOriginal = [];
    this.searchInvLineList = [];
    this.itemForm = this.fb.group({
      period: [''],
      holSectionName: [''],
      invAssetTypeName: [''],
      apStatus: [''],
      apStatusName: [''],
      eappId: [''],
    });
    this.filterForm = this.fb.group({
      filterStatus: this.fb.array(this.filterStatus.map((x) => false)),
      filterAstNum: [''],
      filterContractNum: [''],
      filterAstLicId: [''],
      filterApplicationId: ['']
    });
  }

  /**
   * リセット処理
   */
  reset() {
    this.sessionService.setDataChange(false);
    this.hasBeenSaved = false;
    this.item = {};
    this.searchInvLineOriginal = [];
    this.searchInvLineList = [];
    this.filterForm = this.fb.group({
      filterStatus: this.fb.array(this.filterStatus.map((x) => false)),
      filterAstNum: [''],
      filterContractNum: [''],
      filterAstLicId: [''],
      filterApplicationId: ['']
    });
  }

  /**
   * 説明文章取得
   * @param statusNo 検索する文章のステータス
   */
  getDescription(statusNo: string) {
    for (const codeName of this.codeNameList) {
      if (codeName.value2 === statusNo) {
        this.descriptionText = codeName.value1;
      }
    }
  }

  /**
   * ステータス選択後処理
   * @param status 選択ステータス情報
   */
  selectStatus(status: LovDataEx) {
    this.itemForm.controls.apStatus.setValue(status.code);
    this.itemForm.controls.apStatusName.setValue(status.value1);
    this.item.apStatus = status.code;
    this.item.apStatusName = status.value1;
    this.getDescription(status.code);
    this.toggleDisplay();
    this.sessionService.setDataChange(true);
  }

  /**
   * グリッド初期処理
   */
  onGridInitialized() {
    this.gridAssetList.columnHeaders.rows.defaultSize = 52;
    this.gridAssetList.cellEditEnded.addHandler((s: EaFlexGrid, e: wjGrid.CellRangeEventArgs) => {
      if (e.col === 0) {
        if (s.collectionView.items[e.row].selInvStatus1) {
          s.selectedRows[0].dataItem.selInvStatus2 = false;
          s.selectedRows[0].dataItem.invStatus1 = '1';
          s.selectedRows[0].dataItem.invStatus2 = '0';
          s.invalidate();
        } else {
          s.selectedRows[0].dataItem.invStatus1 = '0';
          s.invalidate();
        }
      }
      if (e.col === 1) {
        if (s.collectionView.items[e.row].selInvStatus2) {
          s.selectedRows[0].dataItem.selInvStatus1 = false;
          s.selectedRows[0].dataItem.invStatus1 = '0';
          s.selectedRows[0].dataItem.invStatus2 = '1';
          s.invalidate();
        } else {
          s.selectedRows[0].dataItem.invStatus2 = '0';
          s.invalidate();
        }
      }
      this.sessionService.setDataChange(true);
    });
  }

  /**
   * 選択切り替え
   * @param e マウスイベント情報
   */
  toggleSelect(e: MouseEvent) {
    const target = e.target as HTMLInputElement;
    const column = target.id.replace('toggle-', '');
    if (!target.checked) {
      if (column === '1') {
        this.gridAssetList.rows.forEach((obj) => {
          obj.dataItem['selInvStatus1'] = false;
          obj.dataItem['invStatus1'] = '0';
        });
        /*this.searchInvLineList.forEach((obj) => {
          obj['selInvStatus1'] = false;
          obj['invStatus1'] = '0';
        });*/
        this.gridAssetList.invalidate();
      } else {
        this.gridAssetList.rows.forEach((obj) => {
          obj.dataItem['selInvStatus2'] = false;
          obj.dataItem['invStatus2'] = '0';
        });
        /*this.searchInvLineList.forEach((obj) => {
          obj['selInvStatus2'] = false;
          obj['invStatus2'] = '0';
        });*/
        this.gridAssetList.invalidate();
      }
    } else {
      if (column === '1') {
        this.checkboxSelectAll2 = false;
        this.gridAssetList.rows.forEach((obj) => {
          obj.dataItem['selInvStatus1'] = true;
          obj.dataItem['selInvStatus2'] = false;
          obj.dataItem['invStatus1'] = '1';
          obj.dataItem['invStatus2'] = '0';
        });
        /*this.searchInvLineList.forEach((obj) => {
          obj['selInvStatus1'] = true;
          obj['selInvStatus2'] = false;
          obj['invStatus1'] = '1';
          obj['invStatus2'] = '0';
        });*/
        this.gridAssetList.invalidate();
      } else {
        this.checkboxSelectAll1 = false;
        this.gridAssetList.rows.forEach((obj) => {
          obj.dataItem['selInvStatus1'] = false;
          obj.dataItem['selInvStatus2'] = true;
          obj.dataItem['invStatus1'] = '0';
          obj.dataItem['invStatus2'] = '1';
        });
        /*this.searchInvLineList.forEach((obj) => {
          obj['selInvStatus1'] = false;
          obj['selInvStatus2'] = true;
          obj['invStatus1'] = '0';
          obj['invStatus2'] = '1';
        });*/
        this.gridAssetList.invalidate();
      }
    }
    this.gridAssetList.collectionView.refresh();
    this.sessionService.setDataChange(true);
  }

  /**
   * 資産絞込
   */
  filterAssets() {
    const filterData = {
      statusNone: this.filterForm.controls.filterStatus.value[0],
      statusYes: this.filterForm.controls.filterStatus.value[1],
      statusNo: this.filterForm.controls.filterStatus.value[2],
      astNum: this.filterForm.controls.filterAstNum.value,
      contractNum: this.filterForm.controls.filterContractNum.value,
      astLicId: this.filterForm.controls.filterAstLicId.value,
      applicationId: this.filterForm.controls.filterApplicationId.value,
    };

    const filter = new wjGridFilter.FlexGridFilter(this.gridAssetList);
    filter.clear();
    filter.showFilterIcons = false;

    const columns = {
      astNum: -1,
      contractNum: -1,
      astLicId: -1,
      applicationId: -1
    };

    switch (this.categoryCode) {
      case 'AP_ATTENTION_INV_FLD_TAN':
      case 'AP_ATTENTION_INV_FLD_RO':
        columns.astNum = 3;
        columns.astLicId = 4;
        break;
      case 'AP_ATTENTION_INV_FLD_INT':
        columns.applicationId = 3;
        break;
      case 'AP_ATTENTION_INV_FLD_INT_S':
        columns.applicationId = 3;
        break;
      case 'AP_ATTENTION_INV_LE':
      case 'AP_ATTENTION_INV_RE':
        columns.contractNum = 3;
        columns.astLicId = 5;
        break;
      case 'AP_ATTENTION_INV_EQUIP':
        columns.astLicId = 3;
        break;
    }
    const noResultObject = {};
    noResultObject[SystemConst.Identifiers.filterString] = true;

    // 有無チェックフィルタ

    const cv = new CollectionView(this.searchInvLineList);
    cv.filter = (row: InvLineEx) => {
      if (filterData.statusNone && filterData.statusYes && filterData.statusNo) {
        return true;
      }
      if (filterData.statusNone && filterData.statusYes && !filterData.statusNo) {
        if ((row.invStatus1 === '0' && row.invStatus2 === '0') || row.invStatus1 === '1') {
          return true;
        }
      }
      if (filterData.statusNone && !filterData.statusYes && filterData.statusNo) {
        if ((row.invStatus1 === '0' && row.invStatus2 === '0') || row.invStatus2 === '1') {
          return true;
        }
      }
      if (!filterData.statusNone) {
        if (filterData.statusYes && filterData.statusNo) {
          if (row.invStatus1 === '1' || row.invStatus2 === '1') {
            return true;
          } else {
            return false;
          }
        } else {
          if (filterData.statusYes) {
            return row.invStatus1 === '1';
          } else if (filterData.statusNo) {
            return row.invStatus2 === '1';
          } else {
            return true;
          }
        }
      } else {
        if (filterData.statusYes || filterData.statusNo) {
          return false;
        } else {
          return (row.invStatus1 === '0' && row.invStatus2 === '0');
        }
      }
    };
    cv.refresh();
    this.gridAssetList.itemsSource = cv.items;

    // 契約番号フィルタ
    if (columns.contractNum >= 0) {
      const contractNumFilter = filter.getColumnFilter(this.gridAssetList.columns[columns.contractNum]);
      contractNumFilter.valueFilter.showValues = this.searchInvLineList
      .filter((obj: InvLineEx) => {
        if (filterData.contractNum.length > 0) {
          if (obj.contractNum && obj.contractNum.includes(filterData.contractNum)) {
            return true;
          }
        } else {
          return true;
        }
      })
      .map(v => {
        const ret = new Object();
        const key = (v.contractNum) ? v.contractNum : '';
        ret[key] = true;
        return ret;
      })
      .reduce((v1, v2) => {
        return Object.assign(v1, v2);
      }, noResultObject);
    }

    // 資産番号フィルタ
    if (columns.astNum >= 0) {
      const astNumFilter = filter.getColumnFilter(this.gridAssetList.columns[columns.astNum]);
      astNumFilter.valueFilter.showValues = this.searchInvLineList
      .filter((obj: InvLineEx) => {
        if (filterData.astNum.length > 0) {
          if (obj.astNum && obj.astNum.includes(filterData.astNum)) {
            return true;
          }
        } else {
          return true;
        }
      })
      .map(v => {
        const ret = new Object();
        const key = (v.astNum) ? v.astNum : '';
        ret[key] = true;
        return ret;
      })
      .reduce((v1, v2) => {
        return Object.assign(v1, v2);
      }, noResultObject);
    }

    // 管理番号フィルタ
    if (columns.astLicId >= 0) {
      const astLicIdFilter = filter.getColumnFilter(this.gridAssetList.columns[columns.astLicId]);
      astLicIdFilter.valueFilter.showValues = this.searchInvLineList
      .filter((obj: InvLineEx) => {
        if (filterData.astLicId.length > 0) {
          if (obj.astLicId && obj.astLicId.includes(filterData.astLicId)) {
            return true;
          }
        } else {
          return true;
        }
      })
      .map(v => {
        const ret = new Object();
        const key = (v.astLicId) ? v.astLicId : '';
        ret[key] = true;
        return ret;
      })
      .reduce((v1, v2) => {
        return Object.assign(v1, v2);
      }, noResultObject);
    }

    // 取得申請書番号
    if (columns.applicationId >= 0) {
      const applicationIdFilter = filter.getColumnFilter(this.gridAssetList.columns[columns.applicationId]);
      applicationIdFilter.valueFilter.showValues = this.searchInvLineList
      .filter((obj: InvLineEx) => {
        if (filterData.applicationId.length > 0) {
          if (obj.applicationId && obj.applicationId.includes(filterData.applicationId)) {
            return true;
          }
        } else {
          return true;
        }
      })
      .map(v => {
        const ret = new Object();
        const key = (v.applicationId) ? v.applicationId : '';
        ret[key] = true;
        return ret;
      })
      .reduce((v1, v2) => {
        return Object.assign(v1, v2);
      }, noResultObject);
    }

    // フィルタ適用
    filter.apply();
    this.gridAssetList.resetSelection();

  }

  /**
   * 行データ更新
   */
  updateItem() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    this.invService.searchInvSum(loginStaffCode, accessLevel, companyCode, this.searchParam)
    .subscribe(
      (resp: InvSumSR[]) => {
        if (resp.length) {
          this.item = resp[this.rowID];
          this.isLoading = false;
          this.init();
        }
      }
    );
  }

  /**
   * 資産セーブリクエスト
   */
  saveAssets() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const invSumSR: InvSumSR = this.item;
    delete invSumSR.sel;
    const list: InvLineEx[] = DeepCopy(this.searchInvLineList);
    list.forEach((row) => {
      if (row.selInvStatus1 === true) {
        row.invStatus = '2';
        row.invStatus1 = '1';
        row.invStatus2 = '0';
      } else if (row.selInvStatus2 === true) {
        row.invStatus = '3';
        row.invStatus1 = '0';
        row.invStatus2 = '1';
      } else {
        row.invStatus = '1';
        row.invStatus1 = '0';
        row.invStatus2 = '0';
      }
      delete row.selInvStatus1;
      delete row.selInvStatus2;
    });

    this.invService.updateInvLine(loginStaffCode, accessLevel, invSumSR, list)
    .subscribe(
      () => {
        this.hasBeenSaved = true;
        this.sessionService.setDataChange(false);
        this.messageService.info(SystemMessage.Info.msg100001);
        this.isLoading = true;
        this.updateItem();
      }
    );
  }

  /**
   * CSV出力ダウンロード
   */
  exportAssets(confirm?: boolean) {

    if (!confirm && this.sessionService.getDataChange()) {
      this.confirmPopupExport.prompt(SystemMessage.Warn.msg200004, document.activeElement);
      return;
    } else {
      const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
      const accessLevel: string = this.sessionInfo.currentAccessLevel;
      const invSumSR: InvSumSR = this.item;
      delete invSumSR.sel;
      const searchScopeType: string = this.searchScopeType;

      this.invService.createInvLineCsv(loginStaffCode, accessLevel, invSumSR, searchScopeType)
      .subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          const fileName = '棚卸明細_' + (this.item.invAssetTypeName ? this.item.invAssetTypeName + '_' : '') + (this.item.holSectionName ? this.item.holSectionName + '_' : '') + this.item.period + '_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          this.fileDownloadService.download(fileId, fileName, contentType);
        }
      );
    }
  }

  /**
   * CSVアップロード
   * @param files ファイル一覧情報
   */
  importAssets(files: FileList) {

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const fileList = files;
    const invSumSR: InvSumSR = this.item;
    delete invSumSR.sel;

    this.fileUploadService.upload(fileList)
    .subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileID = resp.value;
        this.invService.getInvLineByCsv(loginStaffCode, accessLevel, fileID, invSumSR)
        .subscribe(
          (data: InvLine[]) => {
            const validData = data.filter((obj: InvLine) => obj.invStatus1 !== '0' || obj.invStatus2 !== '0');
            if (!validData.length) {
              this.messageService.warn(SystemMessage.Warn.msg200006);
            } else {
              data.forEach((row, index) => {
                this.searchInvLineList[index].invStatus1 = row.invStatus1;
                this.searchInvLineList[index].invStatus2 = row.invStatus2;
                this.searchInvLineList[index].invStatusName = row.invStatusName;
                if (row.invStatus1 === '1') {
                  this.searchInvLineList[index].selInvStatus1 = true;
                } else {
                  this.searchInvLineList[index].selInvStatus1 = false;
                }
                if (row.invStatus2 === '1') {
                  this.searchInvLineList[index].selInvStatus2 = true;
                } else {
                  this.searchInvLineList[index].selInvStatus2 = false;
                }
                this.searchInvLineList[index].invComment = row.invComment;
              });
              this.gridAssetList.collectionView.refresh();
              this.messageService.warn(SystemMessage.Warn.msg200005);
            }
            this.sessionService.setDataChange(true);
          }
        );
      }
    );

  }

  /**
   * e申請画面を表示
   */
  openEApplication() {
    this.masterService.getCodeName('OUTER_SITE_URL').subscribe((resp: CodeName) => {
      const url = resp.value2 + this.itemForm.controls.eappId.value;
      this.siteAccessService.openUrl(url);
    });
  }


  /**
   * 詳細情報を表示
   */
  showCirculationDetails() {
    console.log('showCirculationDetails');
  }

  /**
   * 詳細情報を表示
   */
  showControlDetails() {
    console.log('showControlDetails');
  }

  /**
   * 契約詳細情報を表示
   */
  showContractDetails() {
    console.log('showContractDetails');
  }

  /**
   * 保有部署一覧に戻る
   */
  close() {
    if (this.hasBeenSaved) {
      const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
      const accessLevel: string = this.sessionInfo.currentAccessLevel;
      const companyCode: string = this.sessionInfo.loginCompanyCode;
      const searchScopeType: string = (this.searchScopeType) ? this.searchScopeType : '1';

      const searchParam = {
        companyCode,
        searchScopeType,
        period: this.item.period,
        holSectionYear: this.item.holSectionYear,
        holSectionCode: this.item.holSectionCode,
        invAssetType: this.item.invAssetType
      };

      this.invService.searchInvSum(loginStaffCode, accessLevel, companyCode, searchParam).subscribe((rows: InvSumSR[]) => {
        this.parent.changeChild(1, {action: 'backFromAssetList', params: {updatedRows: rows}});
      });
    } else {
      this.parent.changeChild(1, {action: 'backFromAssetList', params: {}});
    }
  }

}
