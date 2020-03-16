import { Component, ViewChild, EventEmitter, Output, OnChanges, Input } from '@angular/core';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import * as wjGrid from 'wijmo/wijmo.grid';

import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';


@Component({
  selector: 'app-ap-get-tan-result-list',
  templateUrl: './ap-get-tan-result-list.component.html',
  styleUrls: ['./ap-get-tan-result-list.component.scss']
})
export class ApGetTanResultListComponent implements OnChanges {

  /**
   * 入力データ
   */
  @Input() dataList: ApGetTanSR[] = [];

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索条件
   */
  searchParamSC: ApGetTanSC = {};

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridApGetTanResultList', null) gridApGetTanResultList: EaFlexGrid;

  constructor() { }

  /**
   * 画面の読み込み
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
   * グリッド初期処理
   * @param grid Flexグリッド
   */
  onGridInitialized(grid: EaFlexGrid) {
    const extraRow = new wjGrid.Row();
    extraRow.allowMerging = true;
    const panel = grid.columnHeaders;
    panel.rows.splice(0, 0, extraRow);

    for (let colIndex = 7; colIndex <= 35; colIndex++) {
      if (colIndex >= 7 && colIndex <= 9) {
        panel.setCellData(0, colIndex, '申請者');
      }
      if (colIndex >= 10 && colIndex <= 11) {
        panel.setCellData(0, colIndex, '申請保有部署者');
      }
      if (colIndex >= 12 && colIndex <= 13) {
        panel.setCellData(0, colIndex, '資産管理担当者');
      }
      if (colIndex >= 14 && colIndex <= 15) {
        panel.setCellData(0, colIndex, '設置(使用)場所');
      }
      if (colIndex >= 16 && colIndex <= 19) {
        panel.setCellData(0, colIndex, '経費負担');
      }
      if (colIndex >= 20 && colIndex <= 23) {
        panel.setCellData(0, colIndex, '資産(機器)情報[代表]');
      }
      if (colIndex >= 24 && colIndex <= 27) {
        panel.setCellData(0, colIndex, '資産(機器)数量[合計]');
      }
      if (colIndex >= 28 && colIndex <= 29) {
        panel.setCellData(0, colIndex, 'ライセンス情報[代表]');
      }
      if (colIndex >= 30 && colIndex <= 32) {
        panel.setCellData(0, colIndex, 'ライセンス数量[合計]');
      }
      if (colIndex >= 34 && colIndex <= 35) {
        panel.setCellData(0, colIndex, '備考');
      }
    }

    const mergableCols = [0, 1, 2, 3, 4, 5, 6, 33, 36, 37];
    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });


    grid.frozenColumns = 3;
    grid.hostElement.addEventListener('dblclick', (e: MouseEvent) => {
      if (!grid.hitTest(e).panel) {
        return;
      }
      if (wjGrid.CellType[grid.hitTest(e).cellType] === 'Cell') {
        this.select.emit(grid.hitTest(e).grid.selectedRows[0]);
      }
    });
    document.getElementById('wp-grid-ap-get-tan-result-list').querySelectorAll('input[type=checkbox]').forEach((e) => {
      e.setAttribute('style', 'display:none');
    });
  }

  /**
   * 行を更新
   */
  updateRow(row) {
    Object.assign(row, { sel: false });
    const rowIndex = this.gridApGetTanResultList.selectedRows[0].index;
    this.dataList[rowIndex] = row;
    this.gridApGetTanResultList.collectionView.refresh();
  }
}
