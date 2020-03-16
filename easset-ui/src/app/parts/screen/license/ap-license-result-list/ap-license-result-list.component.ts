import { Component, EventEmitter, Input, ViewChild, OnChanges, Output } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';
import { LicenseSR } from 'src/app/models/api/license/license-sr.model';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';

@Component({
  selector: 'app-ap-license-result-list',
  templateUrl: './ap-license-result-list.component.html',
  styleUrls: ['./ap-license-result-list.component.scss']
})
export class ApLicenseResultListComponent implements OnChanges {

  /**
   * 検索画面からデータを受信する
   */
  @Input() dataList;

  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  licenseSCSearch: LicenseSC;

  licenseSRResult: LicenseSR = {};

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridApLicenseResultList', null) gridApLicenseResultList: EaFlexGrid;

  constructor() { }

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

  updateRow(row) {
    Object.assign(row, { sel: false });
    const rowIndex = this.gridApLicenseResultList.selectedRows[0].index;
    this.dataList[rowIndex] = row;
    this.gridApLicenseResultList.collectionView.refresh();
  }
}
