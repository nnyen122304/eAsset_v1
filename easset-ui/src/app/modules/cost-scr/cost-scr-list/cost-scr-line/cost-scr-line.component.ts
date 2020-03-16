import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { SessionService } from 'src/app/services/session.service';
import { CostScrComponent } from '../../cost-scr.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';
import * as wjGridFilter from 'wijmo/wijmo.grid.filter';
import { SessionInfo } from 'src/app/models/session-info';
import { CostScrSR } from 'src/app/models/api/cost-scr/cost-scr-sr.model';
import { DeepCopy } from 'src/app/utils/deep-copy';
import { CodeName } from 'src/app/models/api/code-name';
import { FormGroup, FormBuilder } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';
import { CostScrService } from 'src/app/services/api/cost-scr.service';
import { CostScrLine } from 'src/app/models/api/cost-scr/cost-scr-line.model';
import { CollectionView } from 'wijmo/wijmo';
import { SystemConst } from 'src/app/const/system-const';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { FileUploadService } from 'src/app/services/api/file-upload.service';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';

@Component({
  selector: 'app-cost-scr-line',
  templateUrl: './cost-scr-line.component.html',
  styleUrls: ['./cost-scr-line.component.scss'],
})
export class CostScrLineComponent extends AbstractChildComponent<CostScrComponent>
  implements OnInit {

  /**
   * Form filter
   */
  filterForm: FormGroup;

  /**
   * 検索条件フォーム
   */
  itemForm: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索する資産データ
   */
  item: CostScrSR;

  /**
   * 検索リクエスト用条件パラメータ
   */
  searchParam: CostScrSR;

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
   * 表示切替用値
   */
  view: string;

  /**
   * セル編集を許可するか判定用値
   */
  allowEdit = false;

  /**
   * 保存ボタン表示判定用値
   */
  allowSave = false;

  /**
   * 全「有」チェックボックス選択用
   */
  checkboxSelectAll1 = false;

  /**
   * 全「無」チェックボックス選択用
   */
  checkboxSelectAll2 = false;

  /**
   * label changeStatus
   */
  apChangeStatus = ['未', '申請中', '承認済', '未申請(再)', '却下'];

  searchCostScrLineList: CostScrLine[] = [];
  scrType: string;

  /**
   * 棚卸一覧グリッド Ref
   */
  @ViewChild('gridCostScrLine', null) gridCostScrLine: EaFlexGrid;

  /**
   * Popup Ref
   */
  @ViewChild('confirmPopupExport', null) confirmPopupExport: ConfirmPopupComponent;

  /**
   * label Status
   */
  scrStatus = ['未実施', 'OK', '要対応'];

  constructor(
    private sessionService: SessionService,
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private costScrService: CostScrService,
    private fileDownloadService: FileDownloadService,
    private messageService: MessageService,
  ) {
    super();
  }

  ngOnInit() {
    this.initForms();
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe(param => {
      if (param.action === 'initLine') {
        this.item = DeepCopy(param.params.item);
        this.searchParam = param.params.searchParam;
        this.codeNameList = param.params.codeNameList;
        this.scrType = param.params.scrType;
        this.rowID = param.params.rowID;
        this.view = param.params.view;
        this.init();
      }
    });
  }

  /**
   * 初期処理
   */
  init() {
    this.itemForm.controls.costSectionCodeAndName.setValue(
      `${this.item.costSectionCode} ${this.item.costSectionName}`,
    );
    this.itemForm.controls.costSectionCodeOldAndNameOld.setValue(
      `${this.item.costSectionCodeOld} ${this.item.costSectionNameOld}`,
    );
    this.itemForm.controls.scrTypeName.setValue(this.item.scrTypeName);

    const loginStaffCode = this.sessionInfo.loginUser.staffCode;
    const companyCode = this.sessionInfo.loginCompanyCode;
    const accessLevel = this.sessionInfo.currentAccessLevel;
    const search = this.item;
    delete search.sel;
    this.costScrService
      .searchCostScrLine(loginStaffCode, accessLevel, companyCode, search)
      .subscribe((data: CostScrLine[]) => {
        this.searchCostScrLineList = data.map((obj: CostScrLine) => {
          obj.selScrStatus1 = obj.scrStatus1 === '1' ? true : false;
          obj.selScrStatus2 = obj.scrStatus2 === '1' ? true : false;
          return obj;
        });
        this.gridCostScrLine.resetSelection();
      });
  }

  /**
   * フォーム初期設定
   */
  initForms() {
    this.sessionService.setDataChange(false);
    this.itemForm = this.fb.group({
      costSectionCodeAndName: [''],
      costSectionCodeOldAndNameOld: [''],
      scrTypeName: [''],
    });

    this.filterForm = this.fb.group({
      scrStatus: this.fb.array(this.scrStatus.map(x => false)),
      apChangeStatus: this.fb.array(this.apChangeStatus.map(x => false)),
      contractNum: [''],
      astNum: [''],
      assetId: [''],
      applicationId: [''],
    });
  }

  /**
   * 棚卸ステータス切り替え
   */
  toggleCostScrStatus(e: MouseEvent, i: number) {
    const target = e.target as HTMLInputElement;
    if (target.id === 'item-scrStatus-2') {
      if (target.checked) {
        this.filterForm.controls['apChangeStatus'].setValue([true, true, true, true, true]);
      } else {
        this.filterForm.controls['apChangeStatus'].setValue([false, false, false, false, false]);
      }
    }
    if (
      target.id === 'item-apChangeStatus-0' ||
      target.id === 'item-apChangeStatus-1' ||
      target.id === 'item-apChangeStatus-2' ||
      target.id === 'item-apChangeStatus-3' ||
      target.id === 'item-apChangeStatus-4'
    ) {
      if (target.checked) {
        this.filterForm.controls['scrStatus'].setValue([null, null, true]);
        this.filterForm.controls['apChangeStatus'].setValue(
          this.filterForm.controls['apChangeStatus'].value,
        );
      }
    }
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
    let mergableCols = [];

    this.gridCostScrLine.cellEditEnded.addHandler(
      (s: EaFlexGrid, e: wjGrid.CellEditEndingEventArgs) => {
        if (e.col === 0) {
          if (s.collectionView.items[e.row].scrStatus1) {
            s.selectedRows[0].dataItem.selScrStatus2 = false;
            s.selectedRows[0].dataItem.scrStatus1 = '1';
            s.selectedRows[0].dataItem.scrStatus2 = '0';

            s.itemsSource.forEach((val, i) => {
              if (
                s.collectionView.items[e.row].selScrStatus1 &&
                s.collectionView.items[e.row].astNum === val.astNum
              ) {
                val.selScrStatus1 = true;
                val.selScrStatus2 = false;
                val.scrStatus1 = '1';
                val.scrStatus2 = '0';
              } else if (
                !s.collectionView.items[e.row].selScrStatus1 &&
                s.collectionView.items[e.row].astNum === val.astNum
              ) {
                val.selScrStatus1 = false;
                val.selScrStatus2 = false;
                val.scrStatus1 = '0';
                val.scrStatus2 = '0';
              }
            });

            s.invalidate();
          } else {
            s.selectedRows[0].dataItem.scrStatus1 = '0';
            s.invalidate();
          }
        }

        if (e.col === 1) {
          if (s.collectionView.items[e.row].scrStatus2) {
            s.selectedRows[0].dataItem.selScrStatus1 = false;
            s.selectedRows[0].dataItem.scrStatus1 = '0';
            s.selectedRows[0].dataItem.scrStatus2 = '1';

            s.itemsSource.forEach((val, i) => {
              if (
                s.collectionView.items[e.row].selScrStatus2 &&
                s.collectionView.items[e.row].astNum === val.astNum
              ) {
                val.selScrStatus1 = false;
                val.selScrStatus2 = true;
                val.scrStatus1 = '0';
                val.scrStatus2 = '1';
              } else if (
                !s.collectionView.items[e.row].selScrStatus2 &&
                s.collectionView.items[e.row].astNum === val.astNum
              ) {
                val.selScrStatus1 = false;
                val.selScrStatus2 = false;
                val.scrStatus1 = '0';
                val.scrStatus2 = '0';
              }
            });

            s.invalidate();
          } else {
            s.selectedRows[0].dataItem.scrStatus2 = '0';
            s.invalidate();
          }
        }
        this.sessionService.setDataChange(true);
      },
    );

    if (this.scrType === '1') {
      for (let colIndex = 6; colIndex <= 45; colIndex++) {
        if (colIndex >= 6 && colIndex <= 11) {
          panel.setCellData(0, colIndex, '経費情報：当年度');
        }
        if (colIndex >= 12 && colIndex <= 15) {
          panel.setCellData(0, colIndex, '経費情報：前年度');
        }

        if (colIndex >= 16 && colIndex <= 25) {
          panel.setCellData(0, colIndex, '資産情報');
        }

        if (colIndex >= 26 && colIndex <= 31) {
          panel.setCellData(0, colIndex, '情報機器等');
        }
        if (colIndex >= 32 && colIndex <= 34) {
          panel.setCellData(0, colIndex, '情報機器等使用者：当年度');
        }

        if (colIndex >= 35 && colIndex <= 37) {
          panel.setCellData(0, colIndex, '情報機器等使用者：前年度');
        }
        if (colIndex >= 38 && colIndex <= 45) {
          panel.setCellData(0, colIndex, '移動申請：要対応分');
        }
      }
      mergableCols = [0, 1, 2, 3, 4, 5];
    }

    if (this.scrType === '2') {
      for (let colIndex = 5; colIndex <= 38; colIndex++) {
        if (colIndex >= 5 && colIndex <= 10) {
          panel.setCellData(0, colIndex, '経費情報：当年度');
        }
        if (colIndex >= 11 && colIndex <= 14) {
          panel.setCellData(0, colIndex, '経費情報：前年度');
        }
        if (colIndex >= 15 && colIndex <= 24) {
          panel.setCellData(0, colIndex, '資産情報');
        }
        if (colIndex >= 25 && colIndex <= 27) {
          panel.setCellData(0, colIndex, '無形管理担当者：当年度');
        }
        if (colIndex >= 28 && colIndex <= 30) {
          panel.setCellData(0, colIndex, '無形管理担当者：前年度');
        }
        if (colIndex >= 31 && colIndex <= 38) {
          panel.setCellData(0, colIndex, '移動申請：要対応分');
        }
      }
      mergableCols = [0, 1, 2, 3, 4];
    }

    if (this.scrType === '3' || this.scrType === '4') {
      for (let colIndex = 8; colIndex <= 47; colIndex++) {
        if (colIndex >= 8 && colIndex <= 13) {
          panel.setCellData(0, colIndex, '経費情報：当年度');
        }
        if (colIndex >= 14 && colIndex <= 17) {
          panel.setCellData(0, colIndex, '経費情報：前年度');
        }
        if (colIndex >= 18 && colIndex <= 27) {
          panel.setCellData(0, colIndex, '契約情報');
        }
        if (colIndex >= 28 && colIndex <= 33) {
          panel.setCellData(0, colIndex, '情報機器等');
        }
        if (colIndex >= 34 && colIndex <= 36) {
          panel.setCellData(0, colIndex, '情報機器等使用者：当年度');
        }
        if (colIndex >= 37 && colIndex <= 39) {
          panel.setCellData(0, colIndex, '情報機器等使用者：前年度');
        }
        if (colIndex >= 40 && colIndex <= 47) {
          panel.setCellData(0, colIndex, '移動申請：要対応分');
        }
      }
      mergableCols = [0, 1, 2, 3, 4, 5, 6, 7];
    }

    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });

    grid.frozenColumns = 4;
    grid.hostElement.addEventListener('click', (e: MouseEvent) => {
      if (!grid.hitTest(e).panel) {
        return;
      }
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
        this.gridCostScrLine.rows.forEach(obj => {
          obj.dataItem['selScrStatus1'] = false;
          obj.dataItem['scrStatus1'] = '0';
        });
        this.gridCostScrLine.invalidate();
      } else {
        this.gridCostScrLine.rows.forEach(obj => {
          obj.dataItem['selScrStatus2'] = false;
          obj.dataItem['scrStatus2'] = '0';
        });
        this.gridCostScrLine.invalidate();
      }
    } else {
      if (column === '1') {
        this.checkboxSelectAll2 = false;
        this.gridCostScrLine.rows.forEach(obj => {
          obj.dataItem['selScrStatus1'] = true;
          obj.dataItem['selScrStatus2'] = false;
          obj.dataItem['scrStatus1'] = '1';
          obj.dataItem['scrStatus2'] = '0';
        });
        this.gridCostScrLine.invalidate();
      } else {
        this.checkboxSelectAll1 = false;
        this.gridCostScrLine.rows.forEach(obj => {
          obj.dataItem['selScrStatus1'] = false;
          obj.dataItem['selScrStatus2'] = true;
          obj.dataItem['scrStatus1'] = '0';
          obj.dataItem['scrStatus2'] = '1';
        });
        this.gridCostScrLine.invalidate();
      }
    }

    this.gridCostScrLine.refresh();
    this.sessionService.setDataChange(true);
  }

  /**
   * 選択切り替え
   * @param e マウスイベント情報
   */
  changeInput(item: CostScrLine) {
    this.gridCostScrLine.rows.forEach(obj => {
      if (obj.dataItem['astNum'] === item.astNum) {
        obj.dataItem['scrComment'] = item.scrComment;
      }
    });
    this.gridCostScrLine.invalidate();
    this.gridCostScrLine.refresh();
    this.sessionService.setDataChange(true);
  }

  /**
   * 保有部署一覧に戻る
   */
  close() {
    this.parent.changeChild(1, {action: 'backFromLine', params: {}});
  }

  /**
   * 資産絞込
   */
  filterAssets() {
    const checkStatus = '0';
    const checkStatus1 = '1';
    const checkStatus2 = '2';
    const checkStatus3 = '3';
    const checkStatus4 = '4';
    const checkStatus5 = '5';

    const filterData = {
      statusNone: this.filterForm.controls['scrStatus'].value[0],
      statusOk: this.filterForm.controls['scrStatus'].value[1],
      statusNo: this.filterForm.controls['scrStatus'].value[2],
      contractNum: this.filterForm.controls['contractNum'].value,
      astNum: this.filterForm.controls['astNum'].value,
      assetId: this.filterForm.controls['assetId'].value,
      apChangeStatus1: this.filterForm.controls['apChangeStatus'].value[0],
      apChangeStatus2: this.filterForm.controls['apChangeStatus'].value[1],
      apChangeStatus3: this.filterForm.controls['apChangeStatus'].value[2],
      apChangeStatus4: this.filterForm.controls['apChangeStatus'].value[3],
      apChangeStatus5: this.filterForm.controls['apChangeStatus'].value[4],
      applicationId: this.filterForm.controls['applicationId'].value,
    };

    const filter = new wjGridFilter.FlexGridFilter(this.gridCostScrLine);
    filter.clear();
    filter.showFilterIcons = false;

    const noResultObject = {};
    noResultObject[SystemConst.Identifiers.filterString] = true;
    const cv = new CollectionView(this.searchCostScrLineList);

    cv.filter = (row: CostScrLine) => {

      if (filterData.statusNone && filterData.statusOk && !filterData.statusNo) {
        if ((row.scrStatus1 === checkStatus && row.scrStatus2 === checkStatus) || row.scrStatus1 === checkStatus1) {
          return true;
        }
      }
      if (filterData.statusNone && !filterData.statusOk && filterData.statusNo) {
        if ((row.scrStatus1 === checkStatus && row.scrStatus2 === checkStatus) || row.scrStatus2 === checkStatus) {
          return true;
        }
      }
      if (
        !filterData.statusNone &&
        !filterData.statusOk &&
        !filterData.statusNo && (filterData.contractNum === '' && filterData.astNum === '' && filterData.assetId === '' && filterData.applicationId === '')
      ) {
        return true;
      } else {
        if (
          (filterData.statusNone && row.scrStatus === checkStatus1) ||
          (filterData.statusOk && row.scrStatus === checkStatus2) ||
          (filterData.statusNo && row.scrStatus === checkStatus3)
        ) {
          if (filterData.statusNo && row.scrStatus === checkStatus3) {
            if (
              filterData.apChangeStatus1 &&
              ((filterData.apChangeStatus1 && row.apChangeApStatus === checkStatus4) ||
                (filterData.apChangeStatus4 && row.apChangeApStatus === checkStatus4) ||
                (filterData.apChangeStatus5 && row.apChangeApStatus === checkStatus5) ||
                row.apChangeApStatus === null ||
                row.apChangeApStatus === '')
            ) {
              return true;
            } else if (
              filterData.apChangeStatus2 &&
              row.apChangeApStatus === checkStatus2
            ) {
              return true;
            } else if (
              filterData.apChangeStatus3 &&
              row.apChangeApStatus === checkStatus3
            ) {
              return true;
            } else if (
              !filterData.apChangeStatus1 &&
              !filterData.apChangeStatus2 &&
              !filterData.apChangeStatus3
            ) {
              return true;
            } else {
              return false;
            }
          } else {
            return true;
          }
        } else if (
          (filterData.statusNone && row.scrStatus !== checkStatus1) ||
          (filterData.statusOk && row.scrStatus !== checkStatus2) ||
          (filterData.statusNo && row.scrStatus !== checkStatus3)
        ) {
          if (!filterData.statusNone) {
            if (filterData.statusOk && filterData.statusNo) {
              if (row.scrStatus1 === checkStatus1 || row.scrStatus2 === checkStatus1) {
                return true;
              } else {
                return false;
              }
            } else {
              if (filterData.statusOk) {
                return row.scrStatus1 === checkStatus1;
              } else if (filterData.statusNo) {
                return row.scrStatus2 === checkStatus1;
              } else {
                return true;
              }
            }
          } else {
            if (filterData.statusOk || filterData.statusNo) {
              return false;
            } else {
              return row.scrStatus1 === checkStatus1 && row.scrStatus2 === checkStatus1;
            }
          }
        }

      }
    };
    cv.refresh();
    this.gridCostScrLine.itemsSource = cv.items;
    filter.apply();
    this.gridCostScrLine.resetSelection();
  }

  /**
   * CSV出力ダウンロード
   */
  exportAssets(confirm?: boolean) {
    if (!confirm && this.sessionService.getDataChange()) {
      this.confirmPopupExport.prompt(SystemMessage.Warn.msg200004, document.activeElement);
      return;
    } else {
      const loginStaffCode = this.sessionInfo.loginUser.staffCode;
      const accessLevel = this.sessionInfo.currentAccessLevel;
      const companyCode = this.sessionInfo.loginCompanyCode;

      this.costScrService
        .createCostScrLineCsv(
          loginStaffCode,
          accessLevel,
          companyCode,
          this.item,
        )
        .subscribe((resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          const fileName =
            '経費負担部課精査明細_' +
            '_' +
            this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') +
            '.csv';
          this.fileDownloadService.download(fileId, fileName, contentType);
        });
    }
  }

  /**
   * CSVアップロード
   */
  importAssets(file) {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;

    this.costScrService
      .getScrLineByCsv(loginStaffCode, accessLevel, file.fileId, this.item)
      .subscribe((data: CostScrLine[]) => {
        const validData = data.filter(
          (obj: CostScrLine) =>
            obj.scrStatus1 !== '0' || obj.scrStatus2 !== '0',
        );
        if (!validData.length) {
          this.messageService.warn(SystemMessage.Warn.msg200006);
        } else {
          data.forEach((row, index) => {
            this.searchCostScrLineList[index].scrStatus1 = row.scrStatus1;
            this.searchCostScrLineList[index].scrStatus2 = row.scrStatus2;
            this.searchCostScrLineList[index].scrStatusName =
              row.scrStatusName;

            if (row.scrStatus1 === '1') {
              this.searchCostScrLineList[index].selScrStatus1 = true;
            } else {
              this.searchCostScrLineList[index].selScrStatus1 = false;
            }

            if (row.scrStatus2 === '1') {
              this.searchCostScrLineList[index].selScrStatus2 = true;
            } else {
              this.searchCostScrLineList[index].selScrStatus2 = false;
            }

            this.searchCostScrLineList[index].scrComment = row.scrComment;
          });
          this.gridCostScrLine.collectionView.refresh();
          this.messageService.warn(SystemMessage.Warn.msg200005);
        }
        this.sessionService.setDataChange(true);
      });


  }

  /**
   * Save list CostScrLine
   */
  saveCostScrLine() {
    const loginStaffCode = this.sessionInfo.loginUser.staffCode;
    const accessLevel = this.sessionInfo.currentAccessLevel;
    const companyCode = this.sessionInfo.loginCompanyCode;
    const costScrSR: CostScrSR = this.item;
    delete costScrSR.sel;

    const list: CostScrLine[] = DeepCopy(this.searchCostScrLineList);
    list.forEach(row => {
      if (row.selScrStatus1 === true) {
        row.scrStatus = '2';
        row.scrStatus1 = '1';
        row.scrStatus2 = '0';
      } else if (row.selScrStatus2 === true) {
        row.scrStatus = '3';
        row.scrStatus1 = '0';
        row.scrStatus2 = '1';
      } else {
        row.scrStatus = '1';
        row.scrStatus1 = '0';
        row.scrStatus2 = '0';
      }

      delete row.selScrStatus1;
      delete row.selScrStatus2;
    });

    this.costScrService
      .updateCostScrLine(
        loginStaffCode,
        accessLevel,
        companyCode,
        costScrSR,
        list,
      )
      .subscribe(() => {
        this.sessionService.setDataChange(false);
        this.messageService.info(SystemMessage.Info.msg100001);
      });
  }
}
