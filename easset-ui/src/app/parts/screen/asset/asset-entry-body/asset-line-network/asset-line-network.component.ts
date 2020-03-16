import { Component, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EaFlexGrid } from '../../../../../components/ea-flex-grid/ea-flex-grid.component';
import { AssetLineNetwork } from '../../../../../models/api/asset/asset-line-Network.model';
import * as wjGrid from 'wijmo/wijmo.grid';
import * as wjcCore from 'wijmo/wijmo';

@Component({
  selector: 'app-asset-line-network',
  templateUrl: './asset-line-network.component.html',
  styleUrls: ['./asset-line-network.component.scss']
})
export class AssetLineNetworkComponent {
  /**
   * 行追加
   */
  @Input() lineNetworkList = [];

  btnAddNetwork = true;

  lineNetworkForm: FormGroup;
  isNewLineNetwork = false;

  @ViewChild('gridLineNetwork', null) gridLineNetwork: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
  ) {
    this.lineNetworkForm = this.fb.group({
      lineSeq: [0],
      assetId: [''],
      netMacAddress1: [''],
      netMacAddress2: [''],
      netMacAddress3: [''],
      netMacAddress4: [''],
      netMacAddress5: [''],
      netMacAddress6: [''],
      netIpAddress1: [''],
      netIpAddress2: [''],
      netIpAddress3: [''],
      netIpAddress4: [''],
      netDescription: [''],
      createDate: [''],
      createStaffCode: [''],
      updateDate: [''],
      updateStaffCode: [''],
    });
  }

  /**
   * 利益予測明細入力初期表示
   */
  addLineNetwork() {
    const lineNetwork = new AssetLineNetwork();
    lineNetwork.lineSeq = (this.lineNetworkList.length + 1);
    lineNetwork.assetId = '';
    lineNetwork.netMacAddress1 = '';
    lineNetwork.netMacAddress2 = '';
    lineNetwork.netMacAddress3 = '';
    lineNetwork.netMacAddress4 = '';
    lineNetwork.netMacAddress5 = '';
    lineNetwork.netMacAddress6 = '';
    lineNetwork.netIpAddress1 = '';
    lineNetwork.netIpAddress2 = '';
    lineNetwork.netIpAddress3 = '';
    lineNetwork.netIpAddress4 = '';
    lineNetwork.netDescription = '';
    lineNetwork.createDate = null;
    lineNetwork.createStaffCode = null;
    lineNetwork.updateDate = null;
    lineNetwork.updateStaffCode = null;
    this.lineNetworkList.push(lineNetwork);
    this.lineNetworkForm.patchValue(lineNetwork);
    this.gridLineNetwork.collectionView.refresh();
    this.btnAddNetwork = false;
    this.isNewLineNetwork = true;
    setTimeout(() => {
      this.gridLineNetwork.collectionView.moveCurrentToLast();
    }, 500);
  }

  /**
   * 見込販売計画
   * Init grid
   */
  initGridLineNetwork() {
    // Add footer
    this.gridLineNetwork.columnFooters.rows.push(new wjGrid.GroupRow());
    // Set selected is row -1
    this.gridLineNetwork.resetSelection();
    const that = this;
    // Add event listener
    this.gridLineNetwork.addEventListener(this.gridLineNetwork.hostElement, 'click', (e) => {
      // Reset selected
      that.gridLineNetwork.rows.forEach(item => {
        return item.isSelected = false;
      });
      // Get selected row
      const selectRow = this.gridLineNetwork.selectedRows[0];
      // If click delete button
      if (e.target.id === 'btnDel') {
        // Remove row and sort lineSeq
        that.lineNetworkList = that.lineNetworkList.filter((item) => {
          if (item.lineSeq !== selectRow.dataItem.lineSeq) {
            if (item.lineSeq > selectRow.dataItem.lineSeq) {
              item.lineSeq -= 1;
            }
            return true;
          }
          return false;
        });
        this.gridLineNetwork.resetSelection();
      } else { // Click row
        // Set data for form input
        that.lineNetworkForm.patchValue(selectRow.dataItem);
        that.btnAddNetwork = false;
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
  updateLineNetwork() {
    const lineNetworkData = this.lineNetworkForm.getRawValue();
    // Update data into list
    this.lineNetworkList = this.lineNetworkList.map(item => {
      if (item.lineSeq === lineNetworkData.lineSeq) {
        item = lineNetworkData;
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
  cancelLineNetwork() {
    if (this.isNewLineNetwork === true) {
      const lineNetworkData = this.lineNetworkForm.getRawValue();
      this.lineNetworkList = this.lineNetworkList.filter((item) => {
        return item.lineSeq !== lineNetworkData.lineSeq;
      });
      this.gridLineNetwork.resetSelection();
      this.isNewLineNetwork = false;
    }
    this.btnAddNetwork = !this.btnAddNetwork;
  }

  /**
   * 見込販売計画
   * Go to next record
   *
   * @param data 見込販売計画
   *
   * @return void
   */
  goToNextLineNetwork() {
    const lineNetworkData = this.lineNetworkForm.getRawValue();
    const row = this.gridLineNetwork.rows.find(r => r.dataItem.lineSeq === lineNetworkData.lineSeq);
    if (row) {
      this.updateLineNetwork();
      const nextRow = this.gridLineNetwork.rows[(row.index + 1)];
      if (nextRow !== undefined) {
        this.gridLineNetwork.select((nextRow.index), 1);
        setTimeout(() => {
          this.gridLineNetwork.rows[(nextRow.index)].isSelected = true;
        });
        this.gridLineNetwork.collectionView.refresh();
        this.lineNetworkForm.patchValue(nextRow.dataItem);
        this.btnAddNetwork = false;
      } else {
        this.addLineNetwork();
      }
    }
    // Reset focus
    document.getElementById('profSalesQuantity').focus();
  }

  saveLineNetwork() {
    console.log(this.lineNetworkForm.getRawValue());
    this.updateLineNetwork();
    // Reset grid
    this.gridLineNetwork.resetSelection();
    // Show or hide button add
    this.btnAddNetwork = !this.btnAddNetwork;
    this.isNewLineNetwork = false;
  }

  /**
   * 編集による値変更ハンドラー（確定前で入力中でも発生）
   */
  changeNetworkIpt(event: KeyboardEvent, attrId = '') {
    const target = event.target as HTMLInputElement;
    if (target.value.length < parseInt(target.getAttribute('maxlength'), 10)) {
      return;
    }

    switch (attrId) {
      case 'netMacAddress1':
        document.getElementById('netMacAddress2').focus();
        break;
      case 'netMacAddress2':
        document.getElementById('netMacAddress3').focus();
        break;
      case 'netMacAddress3':
        document.getElementById('netMacAddress4').focus();
        break;
      case 'netMacAddress4':
        document.getElementById('netMacAddress5').focus();
        break;
      case 'netMacAddress5':
        document.getElementById('netMacAddress6').focus();
        break;
      case 'netIpAddress1':
        document.getElementById('netIpAddress2').focus();
        break;
      case 'netIpAddress2':
        document.getElementById('netIpAddress3').focus();
        break;
      case 'netIpAddress3':
        document.getElementById('netIpAddress4').focus();
        break;
      default:
        break;
    }
  }
}
