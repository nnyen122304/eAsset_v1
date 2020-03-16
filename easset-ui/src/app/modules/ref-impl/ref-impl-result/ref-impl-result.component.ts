import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { RefImplComponent } from '../ref-impl.component';
import { FlexGrid } from 'wijmo/wijmo.grid';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { SessionService } from 'src/app/services/session.service';

/**
 * リファレンス実装-検索結果画面
 */
@Component({
  selector: 'app-ref-impl-result',
  templateUrl: './ref-impl-result.component.html',
  styleUrls: ['./ref-impl-result.component.scss']
})
export class RefImplResultComponent extends AbstractChildComponent<RefImplComponent> implements OnInit {
  /**
   * 検索結果
   */
  apGetTanSRList: ApGetTanSR[];

  /**
   * 検索結果グリッド
   */
  @ViewChild('apGetTanSRGrid') public apGetTanSRGrid: FlexGrid;

  constructor(private sessionService: SessionService) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    /**
     * 画面切替初期処理(param:画面オープンパラメータ)
     */
    this.changeChildSubject.subscribe((param) => {
      if (param) {
        switch (param.action) {
          case 'search': // 検索
            this.apGetTanSRList = param.params.resultData;
            break;
          case 'detail': // 行データ更新
            const row = this.apGetTanSRGrid.selection.row;
            Object.assign(this.apGetTanSRList[row], param.params.rowData);
            break;
        }
      }
    });
  }

  /**
   * グリッド初期処理
   * @param flexGrid グリッド
   */
  initializeGrid(flexGrid: FlexGrid) {
    // イベント：ダブルクリック
    flexGrid.addEventListener(flexGrid.hostElement, 'dblclick', (e: MouseEvent) => {
      const ht = flexGrid.hitTest(e);
      const id: string = flexGrid.cells.getCellData(ht.row, 0, true);
      this.parent.changeChild(this.parent.viewIndexEntry, {action: 'detail', params: {id}});
    });
  }

  /**
   * 戻る
   */
  goBack(): void {
    this.parent.changeChild(this.parent.viewIndexSearch);
  }

}
