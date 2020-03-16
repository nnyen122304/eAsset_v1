import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { ApBgnIntLineProfEst } from 'src/app/models/api/ap-bgn-int/ap-bgn-int-line-prof-est.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';


@Component({
  selector: 'app-line-prof-est-list',
  templateUrl: './line-prof-est-list.component.html',
  styleUrls: ['./line-prof-est-list.component.scss']
})
export class LineProfEstListComponent {

  /**
   * 見込販売計画
   */
  @Input() apBgnIntLineProfEstList: ApBgnIntLineProfEst[] = [];

  /**
   * 行追加
   */
  @Input() btnAddProfProfEst = true;

  lineProfEstForm: FormGroup;
  isNewLineProfEst = false;

  @ViewChild('gridApBgnIntLineProfEst', null) gridApBgnIntLineProfEst: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
  ) {
    this.lineProfEstForm = this.fb.group({
      lineSeq: [0],
      applicationId: [''],
      profGrossAmount: [0],
      profCostAmount: [0],
      profSalesAmount: [0],
      profSalesQuantity: [0],
      createDate: [''],
      createStaffCode: [''],
      updateDate: [''],
      updateStaffCode: [''],
    });
  }

  /**
   * 利益予測明細入力初期表示
   */
  addLineProfEst(isNextLine = false) {
    const lineProfEst = new ApBgnIntLineProfEst();
    lineProfEst.lineSeq = (this.apBgnIntLineProfEstList.length + 1);
    lineProfEst.applicationId = '';
    lineProfEst.profGrossAmount = 0;
    lineProfEst.profCostAmount = 0;
    lineProfEst.profSalesAmount = 0;
    lineProfEst.profSalesQuantity = 0;
    lineProfEst.createDate = null;
    lineProfEst.createStaffCode = null;
    lineProfEst.updateDate = null;
    lineProfEst.updateStaffCode = null;
    this.apBgnIntLineProfEstList.push(lineProfEst);
    this.lineProfEstForm.patchValue(lineProfEst);
    this.gridApBgnIntLineProfEst.collectionView.refresh();
    this.btnAddProfProfEst = false;
    this.isNewLineProfEst = true;
    setTimeout(() => {
      this.gridApBgnIntLineProfEst.collectionView.moveCurrentToLast();
    }, 500);
  }

  /**
   * 見込販売計画
   * Init grid
   */
  initGridLineProfEst() {
    // Add footer
    this.gridApBgnIntLineProfEst.columnFooters.rows.push(new wjGrid.GroupRow());
    // Set selected is row -1
    this.gridApBgnIntLineProfEst.resetSelection();
    const that = this;
    // Add event listener
    this.gridApBgnIntLineProfEst.addEventListener(this.gridApBgnIntLineProfEst.hostElement, 'click', (e) => {
      // Reset selected
      that.gridApBgnIntLineProfEst.rows.forEach(item => {
        return item.isSelected = false;
      });
      // Get selected row
      const selectRow = this.gridApBgnIntLineProfEst.selectedRows[0];
      // If click delete button
      if (e.target.id === 'btnDel') {
        // Remove row and sort lineSeq
        that.apBgnIntLineProfEstList = that.apBgnIntLineProfEstList.filter((item) => {
          if (item.lineSeq !== selectRow.dataItem.lineSeq) {
            if (item.lineSeq > selectRow.dataItem.lineSeq) {
              item.lineSeq -= 1;
            }
            return true;
          }
          return false;
        });
        this.gridApBgnIntLineProfEst.resetSelection();
      } else { // Click row
        // Set data for form input
        that.lineProfEstForm.patchValue(selectRow.dataItem);
        that.btnAddProfProfEst = false;
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
  updateLineProfEst() {
    const lineProfEstData = this.lineProfEstForm.getRawValue();
    // Update data into list
    this.apBgnIntLineProfEstList = this.apBgnIntLineProfEstList.map(item => {
      if (item.lineSeq === lineProfEstData.lineSeq) {
        item = lineProfEstData;
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
  cancelLineProfEst() {
    if (this.isNewLineProfEst === true) {
      const lineProfEstData = this.lineProfEstForm.getRawValue();
      this.apBgnIntLineProfEstList = this.apBgnIntLineProfEstList.filter((item) => {
        return item.lineSeq !== lineProfEstData.lineSeq;
      });
      this.gridApBgnIntLineProfEst.resetSelection();
      this.isNewLineProfEst = false;
    }
    this.btnAddProfProfEst = !this.btnAddProfProfEst;
  }

  /**
   * 見込販売計画
   * Go to next record
   *
   * @param data 見込販売計画
   *
   * @return void
   */
  goToNextLineProfEst() {
    const lineProfEstData = this.lineProfEstForm.getRawValue();
    const row = this.gridApBgnIntLineProfEst.rows.find(r => r.dataItem.lineSeq === lineProfEstData.lineSeq);
    if (row) {
      this.updateLineProfEst();
      const nextRow = this.gridApBgnIntLineProfEst.rows[(row.index + 1)];
      if (nextRow !== undefined) {
        this.gridApBgnIntLineProfEst.select((nextRow.index), 1);
        setTimeout(() => {
          this.gridApBgnIntLineProfEst.rows[(nextRow.index)].isSelected = true;
        });
        this.gridApBgnIntLineProfEst.collectionView.refresh();
        this.lineProfEstForm.patchValue(nextRow.dataItem);
        this.btnAddProfProfEst = false;
      } else {
        this.addLineProfEst();
      }
    }
    // Reset focus
    document.getElementById('profSalesQuantity').focus();
  }

  saveLineProfEst() {
    this.updateLineProfEst();
    // Reset grid
    this.gridApBgnIntLineProfEst.resetSelection();
    // Show or hide button add
    this.btnAddProfProfEst = !this.btnAddProfProfEst;
    this.isNewLineProfEst = false;
  }

  /**
   * 見込販売計画明細：粗利益 ＝ 売上 - 売上原価
   */
  setProfGrossAmount() {
    this.lineProfEstForm.controls.profGrossAmount.setValue(
      this.lineProfEstForm.controls.profSalesAmount.value - this.lineProfEstForm.controls.profCostAmount.value);
  }
}
