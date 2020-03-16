import { Component, OnInit, ViewChild } from '@angular/core';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

@Component({
  selector: 'app-asset-result-list',
  templateUrl: './asset-result-list.component.html',
  styleUrls: ['./asset-result-list.component.scss']
})
export class AssetResultListComponent implements OnInit {

  searchParamSR: AssetSR[];

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridAssetResultList', null) gridAssetResultList: EaFlexGrid;

  constructor() { }

  ngOnInit() {
  }

  /**
   * グリッド初期処理
   * @param grid Flexグリッド
   */
  onGridInitialized(grid: EaFlexGrid) {
    grid.frozenColumns = 1;
  }
}
