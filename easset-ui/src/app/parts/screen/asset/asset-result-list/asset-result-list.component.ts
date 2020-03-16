import { Component, OnInit, ViewChild, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { SessionInfo } from 'src/app/models/session-info';
import { Asset } from 'src/app/models/api/asset/asset.model';

@Component({
  selector: 'app-asset-result-list',
  templateUrl: './asset-result-list.component.html',
  styleUrls: ['./asset-result-list.component.scss']
})
export class AssetResultListComponent implements OnChanges {

  /**
   * 入力データ
   */
  @Input() dataList: AssetSR[] = [];

  /**
   * チェックボックスが見える
   */
  @Input() checkBoxVisible = false;

  /**
   * 表示列を削除
   */
  @Input() deleteColumnVisible = false;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索条件
   */
  assetSCSearch: AssetSC = {};

  /**
   * フラグ入力を削除
   */
  deleteFlagInput = '';
  /**
   * action Menu
   */
  @Input() currentState: string;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridAssetResultList', null) gridAssetResultList: EaFlexGrid;

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
    grid.frozenColumns = 1;
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
    const rowIndex = this.gridAssetResultList.selectedRows[0].index;
    this.dataList[rowIndex] = row;
    this.gridAssetResultList.collectionView.refresh();
  }

  /**
   * 選択したデータを取得
   */
  getDataSelected() {
    const items: Asset[] = [];
    const rows = this.gridAssetResultList.getCheckedRows();
    // tslint:disable-next-line:no-any
    rows.map((item: any) => {
      delete item.sel;
      items.push(item);
    });
    return rows;
  }
}
