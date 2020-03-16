import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import * as wjGrid from 'wijmo/wijmo.grid';
import { CostScrComponent } from '../cost-scr.component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { Section } from 'src/app/models/api/section';
import { FormGroup, FormBuilder } from '@angular/forms';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';
import { CostScrService } from 'src/app/services/api/cost-scr.service';
import { CostScrSC } from 'src/app/models/api/cost-scr/cost-scr-sc.model';
import { CostScrSR } from 'src/app/models/api/cost-scr/cost-scr-sr.model';
import { CostScrStat } from 'src/app/models/api/cost-scr/cost-scr-stat.model';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { DatePipe } from '@angular/common';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';
import { MasterService } from 'src/app/services/api/master.service';
import { CodeName } from 'src/app/models/api/code-name';
import { CostScrSrChange } from 'src/app/models/api/cost-scr-sr-change.model';
import { DeepCopy } from 'src/app/utils/deep-copy';
import { CostScrInputSectionComponent } from 'src/app/parts/lov/cost-scr-input-section/cost-scr-input-section.component';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-cost-scr-list',
  templateUrl: './cost-scr-list.component.html',
  styleUrls: ['./cost-scr-list.component.scss'],
})
export class CostScrListComponent extends AbstractChildComponent<CostScrComponent>
  implements OnInit {
  /**
   * 検索条件フォーム
   */
  searchForm: FormGroup;

  /**
   * 部署フォーム
   */
  sectionForm: FormGroup;

  /**
   * 部署フォーム
   */
  costSectionForm: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 表示するビューの判定用値
   */
  view: string;

  /**
   * 会計年月一覧データ
   */
  accountingDates: CostScrStat[];

  /**
   * 検索する部署コード
   */
  searchSectionCode: string;

  /**
   * 検索する会計年月
   */
  searchPeriod: string;

  /**
   * 検索する資産名
   */
  searchAsset: string;

  /**
   * 検索リクエスト用条件パラメータ
   */
  searchParam: CostScrSC;

  /**
   * 検索条件
   */
  selectedSearchScopeType: string;

  /**
   * 実施ステータス一覧
   */
  // CostScrImplementation: string[] = ['未実施', '実施中', '実施済'];

  /**
   * 精査担当部署編集フラグ（true：編集中）
   */
  sectionEditFlag = false;

  /**
   * 精査担当部署変更フラグ（true：変更あり）
   */
  sectionChangeFlag = false;

  /**
   * 資産カテゴリ一覧
   */
  costScrType: string[] = [
    '有形固定資産',
    '無形固定資産',
    'リース',
    'レンタル',
  ];

  /**
   * label costScrImplementation
   */
  costScrImplementation: string[] = ['未実施', '実施中', '実施済', '精査完了'];

  /**
   * label costScrApChangeStatus
   */
  costScrApChangeStatus: string[] = ['申請残有り', '承認済・申請不要'];

  /**
   * CostScrList Grid
   */
  costScrList: CostScrSrChange[] = [];

  /**
   * check show button
   */
  isShow = false;

  itemLastSuccessCreateEndDate: Date;

  costSectionCodeLov: Date;

  costSectionCodeOldLov: Date;

  /**
   * obj emit app-input-section-popup
   */
  objCostScrInputSection: Section;

  /**
   * 公開設定ポップアップ要素Reference
   */
  @ViewChild(CostScrInputSectionComponent, null) popupCostScrInput: CostScrInputSectionComponent;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('confirmPopup', null) confirmPopup: ConfirmPopupComponent;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridCostScrList', null) gridCostScrList: EaFlexGrid;

  constructor(
    private sessionService: SessionService,
    private fb: FormBuilder,
    private costScrService: CostScrService,
    private datePipe: DatePipe,
    private fileDownloadService: FileDownloadService,
    private messageService: MessageService,
    private masterService: MasterService,
  ) {
    super();
    this.fb = fb;

    this.sectionForm = this.fb.group({
      sectionCode: [''],
      sectionName: [''],
      sectionYear: [''],
    });

    this.costSectionForm = this.fb.group({
      costSectionCode: [''],
      costSectionName: [''],
      costSectionCodeOld: [''],
      costSectionNameOld: [''],
    });

    this.searchForm = this.fb.group({
      period: [''],
      includeSectionHierarchyFlag: false,
      scrType: this.fb.array(this.costScrType.map(x => false)),
      scrImplementation: this.fb.array(
        this.costScrImplementation.map(x => false),
      ),
      apChangeStatus: this.fb.array(this.costScrApChangeStatus.map(x => false)),
    });
  }

  /**
   * 画面の読み込み
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe(param => {
      if (param.action === 'init') {
        this.init();
      } else if (param.action === 'backFromLine') {
        if (param.params.updatedRows) {
          const updatedRow = param.params.updatedRows[0];
          Object.assign(updatedRow, { sel: false });
          const rowIndex = this.gridCostScrList.selectedRows[0].index;
          this.costScrList[rowIndex] = updatedRow;
          this.gridCostScrList.collectionView.refresh();
        }
      }
    });
  }

  /**
   * 初期処理
   */
  init() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;

    this.costScrService
      .searchCostScrStatList(loginStaffCode, accessLevel, companyCode)
      .subscribe((resp: CostScrStat[]) => {
        this.itemLastSuccessCreateEndDate = resp[0].lastSuccessCreateEndDate;
        this.searchPeriod = resp[0].period;
        this.searchForm.controls.period.setValue(
          resp[0].period.substring(0, 4) + resp[0].period.substring(4, 6),
        );
        this.costSectionCodeLov = new Date(`${this.searchPeriod.substring(0, 4)}-${this.searchPeriod.substring(4, 6)}-30`);
        this.costSectionCodeOldLov = new Date(`${this.searchPeriod.substring(0, 4)}-03-31`);
        return;
      });
  }

  /**
   * 検索リクエスト、処理
   */
  search() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    this.searchParam = {
      period: this.searchPeriod,
      scrSectionCode: this.sectionForm.controls.sectionCode.value,
      includeSectionHierarchyFlag: this.searchForm.controls
        .includeSectionHierarchyFlag.value
        ? '1'
        : '',
      costSectionCode: this.costSectionForm.controls.costSectionCode.value
        ? this.costSectionForm.controls.costSectionCode.value
        : null,
      costSectionCodeOld: this.costSectionForm.controls.costSectionCodeOld.value
        ? this.costSectionForm.controls.costSectionCodeOld.value
        : null,
      scrType: this.searchForm.controls.scrType.value
        .map((x: boolean, i: number) => (x ? ++i : null))
        .filter(x => x !== null)
        .join(' '),
      scrImplementation: this.searchForm.controls.scrImplementation.value
        .map((x: boolean, i: number) => (x ? ++i : null))
        .filter(x => x !== null)
        .join(' '),
      apChangeStatus: this.searchForm.controls.apChangeStatus.value
        .map((x: boolean, i: number) => (x ? ++i : null))
        .filter(x => x !== null)
        .join(' '),
    };

    this.costScrService
      .searchCostScr(loginStaffCode, accessLevel, companyCode, this.searchParam)
      .subscribe((data: CostScrSR[]) => {
        if (!data.length) {
          this.isShow = false;
          this.messageService.warn(SystemMessage.Warn.msg200002);
          return;
        } else {
          this.isShow = true;
          this.costScrList = data.map((obj: CostScrSR) => {
            obj.sel = false;
            return obj;
          });
          this.gridCostScrList.resetSelection();
        }
      });
  }

  /**
   * 検索処理
   */
  research() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;

    this.costScrService
      .searchCostScr(loginStaffCode, accessLevel, companyCode, this.searchParam)
      .subscribe((data: CostScrSR[]) => {
        if (!data.length) {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
        this.costScrList = data.map((obj: CostScrSR) => {
          return obj;
        });

        this.gridCostScrList.resetSelection();
      });
  }

  /**
   * グリッド初期処理
   * @param grid Flexグリッド
   */
  onGridInitialized(grid: EaFlexGrid) {
    const extraRow = new wjGrid.Row();
    extraRow.allowMerging = true;
    const panel = grid.columnHeaders;
    panel.rows.splice(0, 0, extraRow);

    for (let colIndex = 2; colIndex <= 16; colIndex++) {
      if (colIndex >= 2 && colIndex <= 3) {
        panel.setCellData(0, colIndex, '経費負担部課');
      }
      if (colIndex >= 7 && colIndex <= 10) {
        panel.setCellData(0, colIndex, '精査状況(資産件数)');
      }
      if (colIndex >= 11 && colIndex <= 14) {
        panel.setCellData(0, colIndex, '移動申請状況：要対応分');
      }
      if (colIndex >= 15 && colIndex <= 16) {
        panel.setCellData(0, colIndex, '精査完了(取消)処理');
      }
    }

    const mergableCols = [0, 1, 4, 5, 6, 17];
    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });

    this.gridCostScrList.columnFooters.rows.push(new wjGrid.GroupRow());
    this.gridCostScrList.bottomLeftCells.setCellData(0, 0, '');

    grid.frozenColumns = 6;

    grid.hostElement.addEventListener('click', (e: MouseEvent) => {
      if (!grid.hitTest(e).panel) {
        return;
      }

      if (
        grid.hitTest(e).panel.columns[grid.hitTest(e).col].header === '操作' &&
        wjGrid.CellType[grid.hitTest(e).cellType] === 'Cell'
      ) {
        this.showDetails(grid.hitTest(e).row);
      }
    });
  }

  /**
   * CSVダウンロード
   */
  download() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const params = {
      period: this.searchPeriod,
      scrSectionCode: this.sectionForm.controls.sectionCode.value,
      includeSectionHierarchyFlag: this.searchForm.controls
        .includeSectionHierarchyFlag.value
        ? '1'
        : '',
      costSectionCode: this.costSectionForm.controls.costSectionCode.value
        ? this.costSectionForm.controls.costSectionCode.value
        : null,
      costSectionCodeOld: this.costSectionForm.controls.costSectionCodeOld.value
        ? this.costSectionForm.controls.costSectionCodeOld.value
        : null,
      scrType: this.searchForm.controls.scrType.value
        .map((x: boolean, i: number) => (x ? ++i : null))
        .filter(x => x !== null)
        .join(' '),
      scrImplementation: this.searchForm.controls.scrImplementation.value
        .map((x: boolean, i: number) => (x ? ++i : null))
        .filter(x => x !== null)
        .join(' '),
      apChangeStatus: this.searchForm.controls.apChangeStatus.value
        .map((x: boolean, i: number) => (x ? ++i : null))
        .filter(x => x !== null)
        .join(' '),
    };

    if (params.period !== null && params.period !== '') {
      this.costScrService
        .createCostScrCsv(loginStaffCode, accessLevel, companyCode, params)
        .subscribe(
          (resp: NonObjectResponse<string>) => {
            const fileId = resp.value;
            const fileName = `経費負担部課精査集計_${this.datePipe.transform(
              new Date(),
              'yyyyMMdd_HHmmss',
            )}.csv`;
            this.fileDownloadService.download(fileId, fileName, 'text/csv');
          },
        );
    } else {
      this.messageService.err(SystemMessage.Err.msg300025);
    }
  }

  /**
   * 詳細CSVダウンロード
   */
  lineDownload() {
    const list: CostScrSR[] = this.gridCostScrList.getCheckedRows();
    let check = false;
    let chkScrType = '';
    if (list.length === 0 || list === null || list === undefined) {
      this.messageService.err(SystemMessage.Err.msg300025);
      return;
    }

    for (const item of list) {
      if (chkScrType === '') {
        chkScrType = item.scrType;
      } else {
        if (chkScrType !== item.scrType) {
          this.messageService.err(SystemMessage.Err.msg300026);
          check = true;
          break;
        }
      }
    }

    if (!check) {
      const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
      const accessLevel: string = this.sessionInfo.currentAccessLevel;

      this.costScrService
        .createCostScrLineListCsv(loginStaffCode, accessLevel, list)
        .subscribe((resp: NonObjectResponse<string>) => {
          const fileID = resp.value;
          const fileName = `経費負担部課精査集計_${this.datePipe.transform(
            new Date(),
            'yyyyMMdd_HHmmss',
          )}.csv`;
          this.fileDownloadService.download(fileID, fileName, 'text/csv');
        });
    } else {
      this.messageService.err(SystemMessage.Err.msg300025);
    }
  }

  /**
   * 督促メール送信
   */
  sendReminderMail() {
    const list: CostScrSR[] = this.gridCostScrList.getCheckedRows();
    let check = false;
    if (list.length === 0 || list === null) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    }

    for (const item of list) {
      if (item.compFlag === '1') {
        check = true;
        this.messageService.err(SystemMessage.Err.msg300021);
        break;
      }
    }

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;

    if (!check) {
      this.costScrService
        .remindCostScr(loginStaffCode, accessLevel, list)
        .subscribe(() => {
          this.messageService.info(SystemMessage.Info.msg100015);
        });
    } else {
      this.messageService.err(SystemMessage.Err.msg300025);
    }
  }

  /**
   * 精査完了
   */
  report() {
    const list: CostScrSR[] = this.gridCostScrList.getCheckedRows();
    let check = false;
    const compFlag = '1';

    if (list.length === 0 || list === null || list === undefined) {
      return this.messageService.err(SystemMessage.Err.msg300002);
    }

    for (const item of list) {
      if (item.compFlag === '1') {
        check = true;
        this.messageService.err(SystemMessage.Err.msg300021);
        break;
      }

      if (item.costScrCountUndecided > 0) {
        check = true;
        this.messageService.err(SystemMessage.Err.msg300022);
        break;
      }
    }

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;

    if (!check) {
      this.costScrService
        .updateCostScr(loginStaffCode, accessLevel, list, compFlag)
        .subscribe(() => {
          this.research();
          this.messageService.info(SystemMessage.Info.msg100015);
        });
    }
  }

  /**
   * 精査完了取消
   */
  cancel() {
    const list: CostScrSR[] = this.gridCostScrList.getCheckedRows();
    let check = false;
    const compFlag = '0';

    if (list.length === 0 || list === null || list === undefined) {
      return this.messageService.err(SystemMessage.Err.msg300002);
    }

    for (const item of list) {
      if (item.compFlag === '0') {
        check = true;
        this.messageService.err(SystemMessage.Err.msg300023);
        break;
      }
    }

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    if (!check) {
      this.costScrService
        .updateCostScr(loginStaffCode, accessLevel, list, compFlag)
        .subscribe(() => {
          this.research();
          this.messageService.info(SystemMessage.Info.msg100015);
        });
    }
  }

  /**
   * 精査担当部署編集
   */
  editSection() {
    this.isShow = false;
    this.sectionEditFlag = true;
    this.sectionChangeFlag = false;
  }

  /**
   * 入力
   */
  inputSection(event) {
    let selectSectionCode = '選択なし';
    let multiSectionFlag = false;
    const list: CostScrSR[] = this.gridCostScrList.getCheckedRows();
    const listCheckCostScrSR: CostScrSR[] = [];
    if (list !== null) {
      for (const item of list) {
        if (
          selectSectionCode !== item.scrSectionCode &&
          item.scrSectionCode !== null &&
          item.scrSectionCode !== ''
        ) {
          if (selectSectionCode !== '選択なし') {
            multiSectionFlag = true;
          }
          selectSectionCode = item.scrSectionCode;
        }
        listCheckCostScrSR.push(item);
      }
    }

    if (multiSectionFlag) {
      this.messageService.info(SystemMessage.Info.msg100018);
    } else if (listCheckCostScrSR !== null && listCheckCostScrSR.length > 0) {
      this.popupCostScrInput.open(event);
    } else {
      this.messageService.err(SystemMessage.Err.msg30028);
    }
  }

  /**
   * 依頼メール送信確認ハンドラ
   */
  private alertRequestSendHandler(list: CostScrSR[]) {
    const loginStaffCode = this.sessionInfo.loginUser.staffCode;
    const accessLevel = this.sessionInfo.currentAccessLevel;
    this.confirmPopup.confirm.subscribe(() => {
      this.costScrService.requestCostScr(loginStaffCode, accessLevel, list).subscribe(() => {
        this.messageService.info(SystemMessage.Info.msg100008);
      });
    });
  }

  /**
   * 依頼メール
   */
  requestSend() {
    let blankFlag = false;
    let checkFlag = false;

    const list: CostScrSR[] = this.gridCostScrList.getCheckedRows();
    if (list !== null) {
      for (const item of list) {
        checkFlag = true;
        if (item.scrSectionCode === null || item.scrSectionCode === '') {
          blankFlag = true;
          break;
        }
      }
    }

    if (blankFlag) {
      this.messageService.err(SystemMessage.Err.msg30029);
    } else if (checkFlag) {
      this.confirmPopup.prompt(SystemMessage.Info.msg100018, document.activeElement);
      this.alertRequestSendHandler(list);
    } else {
      this.messageService.err(SystemMessage.Err.msg300027);
    }
  }

  /**
   * seleled data from input-section-popup
   */
  selectedListInputSection(objInputSection: Section) {
    this.costScrList.forEach(obj => {
      if (obj.sel === true) {
        obj.changeFlag = true;
        obj.scrSectionName = objInputSection.sectionName;
        obj.scrSectionCode = objInputSection.sectionCode;
        obj.scrSectionYear = objInputSection.sectionYear;
      }
    });
    this.sectionChangeFlag = true;
    this.gridCostScrList.refresh();
    this.sessionService.setDataChange(true);
  }

  /**
   * update costScrList change
   */
  updateSection() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const listChange: CostScrSR[] = [];

    const listSearchCheck: CostScrSrChange[] = DeepCopy(this.costScrList);
    if (listSearchCheck !== null) {
      for (const item of listSearchCheck) {
        if (item.changeFlag) {
          delete item.changeFlag;
          delete item.sel;
          listChange.push(item);
        }
      }
      if (listChange !== null && listChange.length > 0) {
        this.costScrService.updateScrSection(loginStaffCode, listChange).subscribe(() => {
          this.confirmPopup.prompt(SystemMessage.Info.msg100020, document.activeElement);
          this.confirmPopup.confirm.subscribe(() => {
            this.search();
            this.sectionEditFlag = false;
          });
        });

      }
    }
  }

  /**
   * 編集終了
   */
  endEdit() {
    this.sectionEditFlag = false;
    if (this.sectionChangeFlag) {
      this.confirmPopup.prompt(SystemMessage.Info.msg100020, document.activeElement);
      this.confirmPopup.confirm.subscribe(() => {
        this.search();
        this.sectionEditFlag = false;
      });
      this.isShow = true;
    } else {
      this.isShow = true;
    }
  }

  /**
   * 詳細を表示
   * @param rowID Selected row ID
   */
  showDetails(rowID: number) {
    const data = this.costScrList[rowID];
    let categoryCode: string;
    switch (data.scrType) {
      case '1':
        categoryCode = SystemConst.CategoryCodeItemCostScr.itemDefCostScrLineFldTan;
        break;
      case '2':
        categoryCode = SystemConst.CategoryCodeItemCostScr.itemDefCostScrLineFldInt;
        break;
      case '3' || '4':
        categoryCode = SystemConst.CategoryCodeItemCostScr.itemDefCostScrLineLeRe;
        break;
    }

    const companyCode = this.sessionInfo.loginCompanyCode;
    this.masterService
      .getCodeNameList(categoryCode, null, companyCode)
      .subscribe((resp: CodeName[]) => {
        this.parent.changeChild(this.parent.viewIndexLine, {
          action: 'initLine',
          params: {
            item: data,
            CodeName,
            codeNameList: resp,
            categoryCode,
            scrType: data.scrType,
            rowID,
            view: this.view,
            searchParam: this.searchParam,
          },
        });
      });
  }
}
