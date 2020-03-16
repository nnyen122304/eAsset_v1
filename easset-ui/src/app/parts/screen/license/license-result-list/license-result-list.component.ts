import { Component, OnInit, Input, EventEmitter, Output, ViewChild } from '@angular/core';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';
import { SessionInfo } from 'src/app/models/session-info';
import { LicenseSR } from 'src/app/models/api/license/license-sr.model';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';

@Component({
  selector: 'app-license-result-list',
  templateUrl: './license-result-list.component.html',
  styleUrls: ['./license-result-list.component.scss']
})
export class LicenseResultListComponent implements OnInit {

  @Input() dataList;

  @Input() checkBoxVisible = false;

  @Input() deleteColumnVisible = false;

  @Input() currentState: string;

  @Output() select: EventEmitter<object> = new EventEmitter();

  @ViewChild('gridLicenseResultList', null) gridLicenseResultList: EaFlexGrid;

  sessionInfo: SessionInfo;
  licenseSRList: LicenseSR[] = [];
  licenseSCSearch: LicenseSC = {};
  deleteFlagInput = '';

  constructor() { }

  ngOnInit() {
  }

  ngOnchanges(): void {
    if (this.dataList) {
      this.dataList = this.dataList.map((item) => {
        item.sel = false;
        return item;
      });
    }
  }

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

  updateRow(row) {
    Object.assign(row, { sel: false });
    const rowIndex = this.gridLicenseResultList.selectedRows[0].index;
    this.dataList[rowIndex] = row;
    this.gridLicenseResultList.collectionView.refresh();
  }
}
