import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {EaFlexGrid} from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';
import {SessionInfo} from 'src/app/models/session-info';
import {SessionService} from 'src/app/services/session.service';
import {ApBgnIntLineCostSec} from 'src/app/models/api/ap-bgn-int/ap-bgn-int-line-cost-sec.model';

@Component({
  selector: 'app-line-cost-sec-list',
  templateUrl: './line-cost-sec-list.component.html',
  styleUrls: ['./line-cost-sec-list.component.scss']
})
export class LineCostSecListComponent {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 見込販売計画
   */
  @Input() apBgnIntLineCostSecList: ApBgnIntLineCostSec[] = [];

  /**
   * 行追加
   */
  @Input() btnAddLineCostSec = true;

  /**
   * Emit data
   */
  @Output() public change = new EventEmitter();

  /**
   * 経費負担部課フォーム
   */
  lineCostSecForm: FormGroup;

  /**
   * 経費負担部課フォーム
   */
  costSectionForm: FormGroup;

  /**
   * 検索日付From
   */
  costSectionCodeLov = new Date();

  /**
   * 検索日付To
   */
  costSectionCodeOldLov = new Date();

  /**
   * Flag for check status new row
   */
  isNewLineCostSec = false;

  @ViewChild('gridApBgnIntLineCostSec', null) gridApBgnIntLineCostSec: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService
  ) {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.lineCostSecForm = this.fb.group({
      lineSeq: [0],
      applicationId: [''],
      costSectionCode: [''],
      costSectionName: [''],
      costCompanyCode: [''],
      costCompanyName: [''],
      costDist: [0],
      costSectionYear: [0],
      createStaffCode: [''],
      updateStaffCode: [''],
      createDate: [''],
      updateDate: ['']
    });

    this.costSectionForm = this.fb.group({
      costSectionCode: [''],
      costSectionName: [''],
      costSectionYear: [''],
    });
  }

  /**
   * Add new row
   */
  addLineCostSec() {
    const lineCostSec = new ApBgnIntLineCostSec();
    const totalCostDist = this.apBgnIntLineCostSecList.reduce((a, b) => a + (b.costDist), 0);
    lineCostSec.lineSeq = (this.apBgnIntLineCostSecList.length + 1);
    lineCostSec.applicationId = null;
    lineCostSec.costSectionCode = '';
    lineCostSec.costSectionName = '';
    lineCostSec.costCompanyCode = '';
    lineCostSec.costCompanyName = '';
    lineCostSec.costSectionYear = this.sessionInfo.currentYear;
    lineCostSec.costDist = 0;
    lineCostSec.createStaffCode = null;
    lineCostSec.updateStaffCode = null;
    lineCostSec.createDate = null;
    lineCostSec.updateDate = null;
    this.apBgnIntLineCostSecList.push(lineCostSec);
    this.lineCostSecForm.patchValue(lineCostSec);
    this.lineCostSecForm.controls.costDist.setValue((100 - totalCostDist));
    this.gridApBgnIntLineCostSec.collectionView.refresh();
    this.btnAddLineCostSec = false;
    this.isNewLineCostSec = true;
    // Move to last row
    setTimeout(() => {
      this.gridApBgnIntLineCostSec.collectionView.moveCurrentToLast();
    }, 500);
  }

  /**
   * 見込販売計画
   * Init grid
   */
  initGridLineCostSec() {
    // Add footer
    this.gridApBgnIntLineCostSec.columnFooters.rows.push(new wjGrid.GroupRow());
    // Set selected is row -1
    this.gridApBgnIntLineCostSec.resetSelection();
    const that = this;
    // Add event listener
    this.gridApBgnIntLineCostSec.addEventListener(this.gridApBgnIntLineCostSec.hostElement, 'click', (e) => {
      // Reset selected
      that.gridApBgnIntLineCostSec.rows.forEach(item => {
        return item.isSelected = false;
      });
      // Get selected row
      const selectRow = this.gridApBgnIntLineCostSec.selectedRows[0];
      // If click delete button
      if (e.target.id === 'btnDel') {
        // Remove row and sort lineSeq
        that.apBgnIntLineCostSecList = that.apBgnIntLineCostSecList.filter((item) => {
          if (item.lineSeq !== selectRow.dataItem.lineSeq) {
            if (item.lineSeq > selectRow.dataItem.lineSeq) {
              item.lineSeq -= 1;
            }
            return true;
          }
          return false;
        });
        this.gridApBgnIntLineCostSec.resetSelection();
        // Return emit data
        this.change.emit(this.apBgnIntLineCostSecList);
      } else { // Click row
        // Set data for form input
        that.lineCostSecForm.patchValue(selectRow.dataItem);
        this.costSectionForm.patchValue({
          costSectionCode: selectRow.dataItem.costSectionCode,
          costSectionName: selectRow.dataItem.costSectionName
        });
        that.btnAddLineCostSec = false;
      }
    });
  }

  /**
   * Update data to list
   */
  updateLineCostSec() {
    // Get form value
    const lineLineCostSec = this.lineCostSecForm.getRawValue();
    // Update data into list
    this.apBgnIntLineCostSecList = this.apBgnIntLineCostSecList.map(item => {
      if (item.lineSeq === lineLineCostSec.lineSeq) {
        item = lineLineCostSec;
      }
      return item;
    });
    // Reset 経費負担部課
    this.resetSectionForm();
    // Return emit data
    this.change.emit(this.apBgnIntLineCostSecList);
  }

  /**
   * Event click button cancel
   */
  cancelLineCostSec() {
    if (this.isNewLineCostSec === true) {
      const lineLineCostSec = this.lineCostSecForm.getRawValue();
      this.apBgnIntLineCostSecList = this.apBgnIntLineCostSecList.filter((item) => {
        return item.lineSeq !== lineLineCostSec.lineSeq;
      });
      this.gridApBgnIntLineCostSec.resetSelection();
      this.isNewLineCostSec = false;
    }
    this.btnAddLineCostSec = !this.btnAddLineCostSec;
    this.resetSectionForm();
    // Return emit data
    this.change.emit(this.apBgnIntLineCostSecList);
  }

  /**
   * Event click button 次行入力
   */
  goToNextLineCostSec() {
    const lineLineCostSec = this.lineCostSecForm.getRawValue();
    const row = this.gridApBgnIntLineCostSec.rows.find(r => r.dataItem.lineSeq === lineLineCostSec.lineSeq);
    if (row) {
      this.updateLineCostSec();
      const nextRow = this.gridApBgnIntLineCostSec.rows[(row.index + 1)];
      if (nextRow !== undefined) {
        this.gridApBgnIntLineCostSec.select((nextRow.index), 1);
        setTimeout(() => {
          this.gridApBgnIntLineCostSec.rows[(nextRow.index)].isSelected = true;
        });
        this.gridApBgnIntLineCostSec.collectionView.refresh();
        this.lineCostSecForm.patchValue(nextRow.dataItem);
        this.btnAddLineCostSec = false;
      } else {
        this.addLineCostSec();
      }
    }
  }

  /**
   * Event click button OK
   */
  saveLineCostSec() {
    this.updateLineCostSec();
    // Reset grid
    this.gridApBgnIntLineCostSec.resetSelection();
    // Show or hide button add
    this.btnAddLineCostSec = !this.btnAddLineCostSec;
    this.isNewLineCostSec = false;
  }

  /**
   * Even change value form 経費負担部課
   */
  changeCostSection(data) {
    if (data) {
      this.lineCostSecForm.controls.costSectionCode.setValue(data.sectionCode);
      this.lineCostSecForm.controls.costSectionName.setValue(data.sectionName);
      this.lineCostSecForm.controls.costCompanyCode.setValue(this.sessionInfo.loginCompanyCode);
      this.lineCostSecForm.controls.costCompanyName.setValue(this.sessionInfo.loginCompanyName);
    }
  }

  /**
   * Update value 経費負担部課
   */
  resetSectionForm() {
    this.costSectionForm.patchValue({
      costSectionCode: '',
      costSectionName: ''
    });
  }
}
