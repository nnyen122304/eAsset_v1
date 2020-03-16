import { Component, EventEmitter, Input, OnChanges, Output, ViewChild } from '@angular/core';
import * as wjGrid from 'wijmo/wijmo.grid';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';

import { ApGetIntSR } from 'src/app/models/api/ap-get-int/ap-get-int-sr.model';


@Component({
  selector: 'app-ap-get-int-result-list',
  templateUrl: './ap-get-int-result-list.component.html',
  styleUrls: ['./ap-get-int-result-list.component.scss']
})
export class ApGetIntResultListComponent implements OnChanges {

  readonly stateMenu = {
    apGetInt: 'AP_GET_INT',
    apGetIntCopy: 'AP_GET_INT_COPY',
    apAsset: 'AP_ASSET',
    apAssetMass: 'AP_ASSET_MASS',
    apLicense: 'AP_LICENSE',
    apLicenseMass: 'AP_LICENSE_MASS',
    license: 'LICENSE'
  };

  /**
   * 取得申請(無形)一覧
   */
  @Input() dataList;

  /**
   * 変数定義
   */
  @Input() currentState;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 取得申請(無形)一覧グリッド Ref
   */
  @ViewChild('gridDataList', null) gridDataList: EaFlexGrid;

  constructor() {
  }

  /**
   * コンポーネント初期処理
   */
  ngOnChanges(): void {
    if (this.dataList) {
      this.dataList = this.dataList.map((item) => {
        item.sel = false;
        return item;
      });
    }
  }

  /**
   * データを確認する
   */
  getDataSelected() {
    const items: ApGetIntSR[] = [];
    // チェックアイテム取得
    const rows = this.gridDataList.getCheckedRows();
    // tslint:disable-next-line:no-any
    rows.map((item: any) => {
      delete item.sel;
      items.push(item);
    });

    return rows;
  }

  /**
   * グリッド初期処理
   */
  onGridInitialized(grid: EaFlexGrid) {
    const extraRow = new wjGrid.Row();
    extraRow.allowMerging = true;
    extraRow.allowResizing = true;
    const panel = grid.columnHeaders;
    panel.rows.splice(0, 0, extraRow);

    for (let colIndex = 9; colIndex <= 11; colIndex++) {
      panel.setCellData(0, colIndex, '申請者');
    }

    for (let colIndex = 18; colIndex <= 19; colIndex++) {
      panel.setCellData(0, colIndex, '保有部署');
    }

    for (let colIndex = 20; colIndex <= 21; colIndex++) {
      panel.setCellData(0, colIndex, '無形管理担当者');
    }

    for (let colIndex = 23; colIndex <= 26; colIndex++) {
      panel.setCellData(0, colIndex, '経費負担');
    }

    for (let colIndex = 27; colIndex <= 28; colIndex++) {
      panel.setCellData(0, colIndex, '備考');
    }
    // fixed columns
    const mergableCols = [0, 1, 2, 3, 4, 5, 6, 7, 8, 12, 13, 14, 15, 16, 17, 22, 29, 30, 31];
    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });
    grid.frozenColumns = 4;

    grid.formatItem.addHandler((s: EaFlexGrid, e: wjGrid.FormatItemEventArgs) => {
      s.columns.forEach((col) => {
        if (s.columns[e.col].header === 'Sel') {
          if (this.currentState === this.stateMenu.apGetIntCopy || this.currentState === this.stateMenu.apLicense
            || this.currentState === this.stateMenu.license) {
            e.cell.innerHTML = '';
          }
        }
      });
    });
    grid.hostElement.addEventListener('dblclick', (e: MouseEvent) => {
      if (!grid.hitTest(e).panel) {
        return;
      }

      if (wjGrid.CellType[grid.hitTest(e).cellType] === 'Cell') {
        this.select.emit(grid.hitTest(e).grid.selectedRows[0]);
      }
    });


  }

  /**
   * データを更新
   */
  updateRow(row) {
    Object.assign(row, {sel: false});
    const rowIndex = this.gridDataList.selectedRows[0].index;
    this.dataList[rowIndex] = row;
    this.gridDataList.collectionView.refresh();
  }
}
