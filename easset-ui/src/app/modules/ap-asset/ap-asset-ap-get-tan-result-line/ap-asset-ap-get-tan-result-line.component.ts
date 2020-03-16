import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { ApAssetComponent } from '../ap-asset.component';
import { SessionInfo } from '../../../models/session-info';
import { SessionService } from '../../../services/session.service';
import { MessageService } from '../../../services/message.service';
import { ApGetTanService } from '../../../services/api/ap-get-tan.service';
import { EaFlexGrid } from '../../../components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';
import { EaFlexGridColumn } from '../../../components/ea-flex-grid/ea-flex-column.component';
import { FlexGrid } from 'wijmo/wijmo.grid';

@Component({
  selector: 'app-ap-asset-ap-get-tan-result-line',
  templateUrl: './ap-asset-ap-get-tan-result-line.component.html',
  styleUrls: ['./ap-asset-ap-get-tan-result-line.component.scss']
})
export class ApAssetApGetTanResultLineComponent extends AbstractChildComponent<ApAssetComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  apGetTanLineAstList = [];

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridApGetTanLineAstList', null) gridApGetTanLineAstList: EaFlexGrid;

  constructor(
    private sessionService: SessionService,
    private apGetTanService: ApGetTanService,
    private messageService: MessageService,
  ) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initLine') {
        if (param.params.loadApGetTanId && param.params.loadApGetTanId !== '') {
          this.apGetTanService.getApGetTan(param.params.loadApGetTanId).subscribe(resp => {
            if (resp) {
              this.apGetTanLineAstList = resp.apGetTanLineAstList;
            }
          });
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
      if (!flexGrid.hitTest(e).panel) {
        return;
      }

      const item = flexGrid.hitTest(e).grid.selectedRows[0].dataItem;
      if (item) {
        this.parent.changeChild(this.parent.viewIndexEntry, {
          action: 'intCreate',
          params: {loadApGetTanLineAstId: item.apGetTanLineAstId}
        });
      }
    });
  }
}
