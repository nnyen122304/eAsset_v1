import { Component, OnInit, OnChanges, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { SessionInfo } from 'src/app/models/session-info';
import { Asset } from 'src/app/models/api/asset/asset.model';

@Component({
  selector: 'app-ap-asset-result-list',
  templateUrl: './ap-asset-result-list.component.html',
  styleUrls: ['./ap-asset-result-list.component.scss']
})
export class ApAssetResultListComponent implements OnChanges {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索条件
   */
  searchParamSC: AssetSC = {};

  /**
   * 入力データ
   */
  @Input() dataList = [];

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * データグリッド
   */
  @ViewChild('gridDataResultList', null) gridDataResultList: EaFlexGrid;

  /**
   *  画面の読み込み
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
   */
  onGridInitialized(grid: EaFlexGrid) {
    grid.frozenColumns = 3;
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
   * 行を更新
   */
  updateRow(row) {
    Object.assign(row, { sel: false });
    const rowIndex = this.gridDataResultList.selectedRows[0].index;
    this.dataList[rowIndex] = row;
    this.gridDataResultList.collectionView.refresh();
  }

  /**
   * データを選択する
   */
  getDataSelected() {
    const items: Asset[] = [];
    const rows = this.gridDataResultList.getCheckedRows();
    // tslint:disable-next-line:no-any
    rows.map((item: any) => {
      delete item.sel;
      items.push(item);
    });
    return rows;
  }
}
