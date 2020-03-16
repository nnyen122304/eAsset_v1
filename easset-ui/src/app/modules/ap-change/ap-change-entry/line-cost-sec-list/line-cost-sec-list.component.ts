import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {EaFlexGrid} from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';
import {SessionInfo} from 'src/app/models/session-info';
import {SessionService} from 'src/app/services/session.service';
import { ApChangeLineCostSec } from 'src/app/models/api/ap-change/ap-change-line-cost-sec.model';

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
  @Input() apChangeLineCostSecList: ApChangeLineCostSec[] = [];

  /**
   * 行追加
   */
  @Input() visibleStatus = true;

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

  @ViewChild('gridApChangeLineCostSec', null) gridApChangeLineCostSec: EaFlexGrid;

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
    const lineCostSec = new ApChangeLineCostSec();
    const totalCostDist = this.apChangeLineCostSecList.reduce((a, b) => a + (b.costDist), 0);
    lineCostSec.lineSeq = (this.apChangeLineCostSecList.length + 1);
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
    this.apChangeLineCostSecList.push(lineCostSec);
    this.lineCostSecForm.patchValue(lineCostSec);
    this.lineCostSecForm.controls.costDist.setValue((100 - totalCostDist));
    this.gridApChangeLineCostSec.collectionView.refresh();
    this.btnAddLineCostSec = false;
    this.isNewLineCostSec = true;
    // Move to last row
    setTimeout(() => {
      this.gridApChangeLineCostSec.collectionView.moveCurrentToLast();
    }, 500);
  }

  /**
   * 見込販売計画
   * Init grid
   */
  initGridLineCostSec() {
    // Add footer
    this.gridApChangeLineCostSec.columnFooters.rows.push(new wjGrid.GroupRow());
    // Set selected is row -1
    this.gridApChangeLineCostSec.resetSelection();
    const that = this;
    // Add event listener
    this.gridApChangeLineCostSec.addEventListener(this.gridApChangeLineCostSec.hostElement, 'click', (e) => {
      // Reset selected
      that.gridApChangeLineCostSec.rows.forEach(item => {
        return item.isSelected = false;
      });
      // Get selected row
      const selectRow = this.gridApChangeLineCostSec.selectedRows[0];
      // If click delete button
      if (e.target.id === 'btnDel') {
        // Remove row and sort lineSeq
        that.apChangeLineCostSecList = that.apChangeLineCostSecList.filter((item) => {
          if (item.lineSeq !== selectRow.dataItem.lineSeq) {
            if (item.lineSeq > selectRow.dataItem.lineSeq) {
              item.lineSeq -= 1;
            }
            return true;
          }
          return false;
        });
        this.gridApChangeLineCostSec.resetSelection();
        // Return emit data
        this.change.emit(this.apChangeLineCostSecList);
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
    this.apChangeLineCostSecList = this.apChangeLineCostSecList.map(item => {
      if (item.lineSeq === lineLineCostSec.lineSeq) {
        item = lineLineCostSec;
      }
      return item;
    });
    // Reset 経費負担部課
    this.resetSectionForm();
    // Return emit data
    this.change.emit(this.apChangeLineCostSecList);
  }

  /**
   * Event click button cancel
   */
  cancelLineCostSec() {
    if (this.isNewLineCostSec === true) {
      const lineLineCostSec = this.lineCostSecForm.getRawValue();
      this.apChangeLineCostSecList = this.apChangeLineCostSecList.filter((item) => {
        return item.lineSeq !== lineLineCostSec.lineSeq;
      });
      this.gridApChangeLineCostSec.resetSelection();
      this.isNewLineCostSec = false;
    }
    this.btnAddLineCostSec = !this.btnAddLineCostSec;
    this.resetSectionForm();
    // Return emit data
    this.change.emit(this.apChangeLineCostSecList);
  }

  /**
   * Event click button 次行入力
   */
  goToNextLineCostSec() {
    const lineLineCostSec = this.lineCostSecForm.getRawValue();
    const row = this.gridApChangeLineCostSec.rows.find(r => r.dataItem.lineSeq === lineLineCostSec.lineSeq);
    if (row) {
      this.updateLineCostSec();
      const nextRow = this.gridApChangeLineCostSec.rows[(row.index + 1)];
      if (nextRow !== undefined) {
        this.gridApChangeLineCostSec.select((nextRow.index), 1);
        setTimeout(() => {
          this.gridApChangeLineCostSec.rows[(nextRow.index)].isSelected = true;
        });
        this.gridApChangeLineCostSec.collectionView.refresh();
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
    this.gridApChangeLineCostSec.resetSelection();
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
