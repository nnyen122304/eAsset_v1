import { Component, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import * as wjGrid from 'wijmo/wijmo.grid';
import { AssetLineOs } from '../../../../../models/api/asset/asset-line-os.model';

@Component({
  selector: 'app-asset-line-os',
  templateUrl: './asset-line-os.component.html',
  styleUrls: ['./asset-line-os.component.scss']
})
export class AssetLineOsComponent {
  /**
   * 行追加
   */
  @Input() lineOsList = [];

  btnAddOs = true;

  lineOsForm: FormGroup;
  isNewLineOs = false;

  @ViewChild('gridLineOs', null) gridLineOs: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
  ) {
    this.lineOsForm = this.fb.group({
      lineSeq: [0],
      assetId: [''],
      osName: [''],
      osDescription: [''],
      assetLineOsId: [0],
      createDate: [''],
      createStaffCode: [''],
      updateDate: [''],
      updateStaffCode: [''],
    });
  }

  /**
   * 利益予測明細入力初期表示
   */
  addLineOs(isNextLine = false) {
    const lineOs = new AssetLineOs();
    lineOs.lineSeq = (this.lineOsList.length + 1);
    lineOs.osName = '';
    lineOs.osDescription = '';
    lineOs.assetId = '';
    lineOs.assetLineOsId = 0;
    lineOs.createDate = null;
    lineOs.createStaffCode = null;
    lineOs.updateDate = null;
    lineOs.updateStaffCode = null;
    this.lineOsList.push(lineOs);
    this.lineOsForm.patchValue(lineOs);
    this.gridLineOs.collectionView.refresh();
    this.btnAddOs = false;
    this.isNewLineOs = true;
    setTimeout(() => {
      this.gridLineOs.collectionView.moveCurrentToLast();
    }, 500);
  }

  /**
   * 見込販売計画
   * Init grid
   */
  initGridLineOs() {
    // Add footer
    this.gridLineOs.columnFooters.rows.push(new wjGrid.GroupRow());
    // Set selected is row -1
    this.gridLineOs.resetSelection();
    const that = this;
    // Add event listener
    this.gridLineOs.addEventListener(this.gridLineOs.hostElement, 'click', (e) => {
      // Reset selected
      that.gridLineOs.rows.forEach(item => {
        return item.isSelected = false;
      });
      // Get selected row
      const selectRow = this.gridLineOs.selectedRows[0];
      // If click delete button
      if (e.target.id === 'btnDel') {
        // Remove row and sort lineSeq
        that.lineOsList = that.lineOsList.filter((item) => {
          if (item.lineSeq !== selectRow.dataItem.lineSeq) {
            if (item.lineSeq > selectRow.dataItem.lineSeq) {
              item.lineSeq -= 1;
            }
            return true;
          }
          return false;
        });
        this.gridLineOs.resetSelection();
      } else { // Click row
        // Set data for form input
        that.lineOsForm.patchValue(selectRow.dataItem);
        that.btnAddOs = false;
      }
    });
  }

  /**
   * 見込販売計画
   * Update
   *
   * @param data 見込販売計画
   *
   * @return void
   */
  updateLineOs() {
    const lineOsData = this.lineOsForm.getRawValue();
    // Update data into list
    this.lineOsList = this.lineOsList.map(item => {
      if (item.lineSeq === lineOsData.lineSeq) {
        item = lineOsData;
      }
      return item;
    });
  }

  /**
   * 見込販売計画
   * Cancel
   *
   * @param data 見込販売計画
   *
   * @return void
   */
  cancelLineOs() {
    if (this.isNewLineOs === true) {
      const lineOsData = this.lineOsForm.getRawValue();
      this.lineOsList = this.lineOsList.filter((item) => {
        return item.lineSeq !== lineOsData.lineSeq;
      });
      this.gridLineOs.resetSelection();
      this.isNewLineOs = false;
    }
    this.btnAddOs = !this.btnAddOs;
  }

  /**
   * 見込販売計画
   * Go to next record
   *
   * @param data 見込販売計画
   *
   * @return void
   */
  goToNextLineOs() {
    const lineOsData = this.lineOsForm.getRawValue();
    const row = this.gridLineOs.rows.find(r => r.dataItem.lineSeq === lineOsData.lineSeq);
    if (row) {
      this.updateLineOs();
      const nextRow = this.gridLineOs.rows[(row.index + 1)];
      if (nextRow !== undefined) {
        this.gridLineOs.select((nextRow.index), 1);
        setTimeout(() => {
          this.gridLineOs.rows[(nextRow.index)].isSelected = true;
        });
        this.gridLineOs.collectionView.refresh();
        this.lineOsForm.patchValue(nextRow.dataItem);
        this.btnAddOs = false;
      } else {
        this.addLineOs();
      }
    }
    // Reset focus
    document.getElementById('profSalesQuantity').focus();
  }

  saveLineOs() {
    console.log(this.lineOsForm.getRawValue());
    this.updateLineOs();
    // Reset grid
    this.gridLineOs.resetSelection();
    // Show or hide button add
    this.btnAddOs = !this.btnAddOs;
    this.isNewLineOs = false;
  }
}
