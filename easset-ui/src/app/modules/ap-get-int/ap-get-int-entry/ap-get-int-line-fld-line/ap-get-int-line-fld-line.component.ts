import { Component, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApGetIntLineFld } from 'src/app/models/api/ap-get-int/ap-get-int-line-fld.model';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import * as wjGrid from 'wijmo/wijmo.grid';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
@Component({
  selector: 'app-ap-get-int-line-fld-line',
  templateUrl: './ap-get-int-line-fld-line.component.html',
  styleUrls: ['./ap-get-int-line-fld-line.component.scss']
})
export class ApGetIntLineFldLineComponent {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 見込販売計画
   */
  @Input() apGetIntLineFldList = [];

  /**
   * 行追加
   */
  @Input() btnAddLineFldLine = true;

  /**
   * Emit data
   */
  @Output() public change = new EventEmitter();

  /**
   * Flag for check status new row
   */
  isNewLineFldLine = false;

  /**
   * Flag for check status update row
   */
  isUpdateLineFldLine1 = false;

  /**
   * Flag for check status update row
   */
  isUpdateLineFldLine = [];

  @ViewChild('gridApGetIntLineFldLine', null) gridApGetIntLineFldLine: EaFlexGrid;

  /**
   *  value3CodeName
   */
  value3CodeName = 'value3 = "1"';

  FldLineForm: FormGroup;

  formAssetsEquipment: FormGroup;

  astPrjCodeForm: FormGroup;

  // tslint:disable-next-line: no-any
  lineFldLine: any = new ApGetIntLineFld();

  /**
   * 仕入先Form
   */
  formAstPurCompanyName: FormGroup;

  astAddUpType1Flag = false;

  astAccountNameNumber = [];

  astAccountName = {};

  /**
   * 分類(明細)
   */
  astCategoryTypeLef = '';

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,

  ) {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.formAssetsEquipment = this.fb.group({
      astCategoryType: [''],
      astCategoryName: ['']
    });
    this.formAstPurCompanyName = this.fb.group({
      astPurCompanyCode: [''],
      astPurCompanyName: ['']
    });
    this.FldLineForm = this.fb.group({
      lineSeq: [0],
      apGetIntLineFldLineSeq: [0],
      astTermFlag: [''],
      astUnitAmount: [''],
      astQuantity: [''],
      astGetAmount: [''],
      astCategoryName: [''],
      astTermYear: [''],
      astEstimateNumber: [''],
      astName: [''],
      astPurCompanyName: [''],
      astPrjCode: [''],
      astPrjName: [''],
      astPrjType: [''],
      astComment: [''],
      astAddUpType: ['1'],
      astAddUpType1Flag: [true],
      astAddUpType2Flag: [true],
      type1FlagChecked: [true],
      type2FlagChecked: [true]
    });
    this.astPrjCodeForm = this.fb.group({
      astPrjCode: [''],
      astPrjName: [''],
      astPrjType: ['']
    });
  }

  addFldLine() {
    this.btnAddLineFldLine = !this.btnAddLineFldLine;
    this.resetForm();
    // tslint:disable-next-line: no-any
    this.lineFldLine.lineSeq = (this.apGetIntLineFldList.length + 1);
    this.lineFldLine.type1FlagChecked = false;
    this.lineFldLine.type2FlagChecked = false;
    this.apGetIntLineFldList.push(this.lineFldLine);
    this.FldLineForm.patchValue(this.lineFldLine);
    this.gridApGetIntLineFldLine.collectionView.refresh();
    this.btnAddLineFldLine = false;
    this.isNewLineFldLine = true;

    this.astAccountName = { ...this.astAccountName, ...{ [this.lineFldLine.lineSeq]: [{ data: '', label: '' }] } };

    // Move to last row
    setTimeout(() => {
      this.gridApGetIntLineFldLine.collectionView.moveCurrentToLast();
    }, 500);
  }

  changeValueLine() {
    const totalAstUnitAmount = this.FldLineForm.controls.astUnitAmount.value;
    const totalAstQuantity = this.FldLineForm.controls.astQuantity.value;
    if (totalAstUnitAmount === '' || totalAstUnitAmount === null || totalAstQuantity === '' || totalAstQuantity === null) {
      this.FldLineForm.controls.astGetAmount.setValue('');
    } else {
      this.FldLineForm.controls.astGetAmount.setValue(totalAstUnitAmount * totalAstQuantity);
    }
  }

  cancelFldLine() {
    if (this.isNewLineFldLine === true) {
      const lineLineFldLine = this.FldLineForm.getRawValue();
      this.apGetIntLineFldList = this.apGetIntLineFldList.filter((item) => {
        return item.lineSeq !== lineLineFldLine.lineSeq;
      });
    }
    this.isNewLineFldLine = false;
    this.btnAddLineFldLine = !this.btnAddLineFldLine;
    this.gridApGetIntLineFldLine.resetSelection();
    this.resetForm();
    // Return emit data
    this.change.emit(this.apGetIntLineFldList);
  }

  astCategoryType(data) {
    this.astCategoryTypeLef = data.value2;
  }

  selectProject(data: LovDataEx) {
    this.FldLineForm.controls.astName.setValue(data.name);
  }

  /**
   * Update data to list
   */
  updateLineFldLine() {
    this.FldLineForm.controls.astCategoryName.setValue(this.formAssetsEquipment.controls.astCategoryName.value);
    this.FldLineForm.controls.astPurCompanyName.setValue(this.formAstPurCompanyName.controls.astPurCompanyName.value);
    this.FldLineForm.controls.astPrjCode.setValue(this.astPrjCodeForm.controls.astPrjCode.value);
    this.FldLineForm.controls.astPrjName.setValue(this.astPrjCodeForm.controls.astPrjName.value);
    this.FldLineForm.controls.astPrjType.setValue(this.astPrjCodeForm.controls.astPrjType.value);

    if (this.FldLineForm.controls.astAddUpType.value === '1') {
      this.FldLineForm.controls.astAddUpType1Flag.setValue(true);
      this.FldLineForm.controls.astAddUpType2Flag.setValue(false);
      this.FldLineForm.controls.type1FlagChecked.setValue(true);
      this.FldLineForm.controls.type2FlagChecked.setValue(false);
      this.astAccountName = { ...this.astAccountName, ...{ [this.lineFldLine.lineSeq]: [{ data: '', label: '' }, { data: '1', label: '10' }] } };
    } else {
      this.FldLineForm.controls.astAddUpType1Flag.setValue(false);
      this.FldLineForm.controls.astAddUpType2Flag.setValue(true);
      this.FldLineForm.controls.type1FlagChecked.setValue(false);
      this.FldLineForm.controls.type2FlagChecked.setValue(true);
      this.astAccountName = { ...this.astAccountName, ...{ [this.lineFldLine.lineSeq]: [{ data: '', label: '' }, { data: '1', label: '30' }] } };
    }

    // Get form value
    const lineLineCostSec = this.FldLineForm.getRawValue();
    // Update data into list
    this.apGetIntLineFldList = this.apGetIntLineFldList.map(item => {
      if (item.lineSeq === lineLineCostSec.lineSeq) {
        item = lineLineCostSec;
      }
      return item;
    });
  }

  /**
   * Event click button 次行入力
   */
  goToNextLineFldLine() {
    const lineLineFldLine = this.FldLineForm.getRawValue();
    const row = this.gridApGetIntLineFldLine.rows.find(r => r.dataItem.lineSeq === lineLineFldLine.lineSeq);
    if (row) {
      this.updateLineFldLine();
      const nextRow = this.gridApGetIntLineFldLine.rows[(row.index + 1)];
      if (nextRow !== undefined) {
        this.gridApGetIntLineFldLine.select((nextRow.index), 1);
        setTimeout(() => {
          this.gridApGetIntLineFldLine.rows[(nextRow.index)].isSelected = true;
        });
        this.gridApGetIntLineFldLine.collectionView.refresh();
        this.FldLineForm.patchValue(nextRow.dataItem);
        this.btnAddLineFldLine = false;
      } else {
        this.addFldLine();
      }
    }
  }

  /**
   * Event click button OK
   */
  saveLineFldLine() {
    this.updateLineFldLine();
    // Reset grid
    this.gridApGetIntLineFldLine.resetSelection();
    // Show or hide button add
    this.btnAddLineFldLine = !this.btnAddLineFldLine;
    this.isNewLineFldLine = false;
  }

  resetForm() {
    this.astCategoryTypeLef = '';
    this.FldLineForm.controls.astUnitAmount.setValue('');
    this.FldLineForm.controls.astQuantity.setValue('');
    this.FldLineForm.controls.astGetAmount.setValue('');
    this.FldLineForm.controls.astCategoryName.setValue('');
    this.FldLineForm.controls.astPurCompanyName.setValue('');
    this.FldLineForm.controls.astTermFlag.setValue('');
    this.FldLineForm.controls.astName.setValue('');
    this.FldLineForm.controls.astTermYear.setValue('');
    this.FldLineForm.controls.astEstimateNumber.setValue('');
    this.FldLineForm.controls.astPrjCode.setValue('');
    this.FldLineForm.controls.astPrjName.setValue('');
    this.FldLineForm.controls.astPrjType.setValue('');
    this.FldLineForm.controls.astAddUpType.setValue('1');
    this.astPrjCodeForm.reset();
    this.formAssetsEquipment.reset();
    this.formAstPurCompanyName.reset();
  }

  onGridInitialized() {
    this.gridApGetIntLineFldLine.rows.forEach((obj) => {
      obj.dataItem['astAddUpType1Flag'] = false;
      obj.dataItem['astAddUpType2Flag'] = false;
    });

    // Add footer
    this.gridApGetIntLineFldLine.columnFooters.rows.push(new wjGrid.GroupRow());
    // Set selected is row -1
    this.gridApGetIntLineFldLine.resetSelection();
    const that = this;
    // Add event listener
    this.gridApGetIntLineFldLine.addEventListener(this.gridApGetIntLineFldLine.hostElement, 'click', (e) => {
      // Reset selected
      that.gridApGetIntLineFldLine.rows.forEach(item => {
        return item.isSelected = false;
      });
      // Get selected row
      const selectRow = this.gridApGetIntLineFldLine.selectedRows[0];
      // If click delete button
      if (e.target.id === 'btnDel') {
        // Remove row and sort lineSeq
        that.apGetIntLineFldList = that.apGetIntLineFldList.filter((item) => {
          if (item.lineSeq !== selectRow.dataItem.lineSeq) {
            if (item.lineSeq > selectRow.dataItem.lineSeq) {
              item.lineSeq -= 1;
            }
            return true;
          }
          return false;
        });
        this.gridApGetIntLineFldLine.resetSelection();
        // Return emit data
        this.change.emit(this.apGetIntLineFldList);
        this.btnAddLineFldLine = true;
      } else {
        // Set data for form input
        that.FldLineForm.patchValue(selectRow.dataItem);
        this.formAssetsEquipment.patchValue({
          astCategoryName: selectRow.dataItem.astCategoryName ? selectRow.dataItem.astCategoryName : '',
        });
        this.formAstPurCompanyName.patchValue({
          astPurCompanyName: selectRow.dataItem.astPurCompanyName ? selectRow.dataItem.astPurCompanyName : '',
        });
        if (selectRow.dataItem.astCategoryName === 'ライセンス') {
          this.astCategoryTypeLef = '1';
        }
        switch (selectRow.dataItem.astCategoryName) {
          case 'ライセンス':
            this.astCategoryTypeLef = '1';
            break;
          case '自社製作':
            this.astCategoryTypeLef = '2';
            break;
          case '外部委託':
            this.astCategoryTypeLef = '3';
            break;
          case 'その他直下経費':
            this.astCategoryTypeLef = '4';
            break;
          case 'その他':
            this.astCategoryTypeLef = '5';
            break;
          default:
            this.astCategoryTypeLef = '';
        }

        this.FldLineForm.patchValue ({
          astName: selectRow.dataItem.astName ? selectRow.dataItem.astName : '',
          astTermFlag: selectRow.dataItem.astTermFlag ? true : false,
          astEstimateNumber: selectRow.dataItem.astEstimateNumber ? selectRow.dataItem.astEstimateNumber : '',
          astComment: selectRow.dataItem.astComment ? selectRow.dataItem.astComment : ''
        });
        this.astPrjCodeForm.patchValue({
          astPrjCode: selectRow.dataItem.astPrjCode ? selectRow.dataItem.astPrjCode : '',
          astPrjName: selectRow.dataItem.astPrjName ? selectRow.dataItem.astPrjName : '',
          astPrjType: selectRow.dataItem.astPrjType ? selectRow.dataItem.astPrjType : ''
        });
        that.btnAddLineFldLine = false;
      }

      if (e.target.id === 'type1Flag_' + selectRow.dataItem.lineSeq) {
        that.apGetIntLineFldList = that.apGetIntLineFldList.filter((item) => {
          if (item.lineSeq === selectRow.dataItem.lineSeq) {
            item.type1FlagChecked = true;
            item.type2FlagChecked = false;
            this.astAccountName[selectRow.dataItem.lineSeq] = [{ data: '', label: '' }, { data: 1, label: 10 }];
            this.astAccountNameNumber[selectRow.dataItem.lineSeq] = 1;
          }
          return item;
        });
      }
      if (e.target.id === 'type2Flag_' + selectRow.dataItem.lineSeq) {
        that.apGetIntLineFldList = that.apGetIntLineFldList.filter((item) => {
          if (item.lineSeq === selectRow.dataItem.lineSeq) {
            item.type1FlagChecked = false;
            item.type2FlagChecked = true;
            this.astAccountName[selectRow.dataItem.lineSeq] = [{ data: '', label: '' }, { data: 1, label: 30 }];
            this.astAccountNameNumber[selectRow.dataItem.lineSeq] = 0;
          }
          return item;
        });
      }
    });
  }
}
