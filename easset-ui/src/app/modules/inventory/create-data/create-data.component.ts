import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';

import * as wjGrid from 'wijmo/wijmo.grid';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { InventoryComponent } from '../inventory.component';
import { ReleaseSettingsPopupComponent } from './release-settings-popup/release-settings-popup.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { PpfsImportService } from 'src/app/services/api/ppfs-import.service';
import { InvService } from 'src/app/services/api/inv.service';

import { PpfsStat } from 'src/app/models/api/ppfs-import/ppfs-stat';
import { InvStat } from 'src/app/models/api/inv/inv-stat';
import { InvStatEx } from 'src/app/models/api/inv/inv-stat-ex';
import { SessionInfo } from 'src/app/models/session-info';
import { InvCreationCriteria } from 'src/app/models/inventory/inv-creation-criteria';

import { SystemMessage } from 'src/app/const/system-message';
import { DeepCopy } from 'src/app/utils/deep-copy';

/**
 * 棚卸データ作成画面コンポネント
 */

@Component({
  selector: 'app-create-data',
  templateUrl: './create-data.component.html',
  styleUrls: ['./create-data.component.scss']
})
export class CreateDataComponent extends AbstractChildComponent<InventoryComponent> implements OnInit {

  /**
   * 公開設定ポップアップ要素Reference
   */
  @ViewChild(ReleaseSettingsPopupComponent, null) popup: ReleaseSettingsPopupComponent;

  /**
   * グリッド要素Reference
   */
  @ViewChild('gridDataReleaseList', null) gridDataReleaseList: EaFlexGrid;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 作成条件フォーム
   */
  creatorForm: FormGroup;

  /**
   * 月年データ
   */
  accountingDates: PpfsStat[];

  /**
   * 編集グリッドデータ
   */
  dataReleaseList: InvStatEx[];

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private sessionService: SessionService,
    private messageService: MessageService,
    private ppfsImportService: PpfsImportService,
    private invService: InvService
  ) {
    super();
    this.fb = fb;
    this.creatorForm = this.fb.group({
      period: [''],
      overwriteFlag: false
    });
  }

  /**
   * 画面の読み込み
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.init();
      }
    });

  }

  /**
   * 初期処理
   */
  init() {
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const dataType = '1';

    this.ppfsImportService.getPpfsStatList(companyCode, dataType).subscribe(
      (data: PpfsStat[]) => {
        if (data.length) {
          this.accountingDates = [];
          data.forEach((date) => {
            if (date.lastSuccessCreateDate) {
              const formatedDate = DeepCopy(date);
              formatedDate.periodFormated = date.period.substring(0, 4) + '-' + date.period.substring(4, 6);
              this.accountingDates.push(formatedDate);
            }
          });
          this.creatorForm.controls.period.setValue(this.accountingDates[0].period);
        } else {
          this.accountingDates = [];
          this.accountingDates.push({
            period: this.datePipe.transform(new Date(), 'yyyyMM'),
            periodName: this.datePipe.transform(new Date(), 'yyyy-MM')
          });
        }
      }
    );

    this.updateData();

  }

  /**
   * ポップアップの再表示
   */
  toggleOverwritePopup() {
    if (this.creatorForm.controls.overwriteFlag.value === true) {
      this.messageService.warn(SystemMessage.Warn.msg200003);
    }
  }

  /**
   * グリッドの初期勝利
   * @param grid Flexグリッド要素
   */
  onGridInitialized(grid: EaFlexGrid) {

    const extraRow = new wjGrid.Row();
    extraRow.allowMerging = true;
    extraRow.allowResizing = true;
    const panel = grid.columnHeaders;
    panel.rows.splice(0, 0, extraRow);

    for (let colIndex = 5; colIndex <= 10; colIndex++) {
      panel.setCellData(0, colIndex, '各部公開');
    }

    [
      'periodFormated',
      'createStatusName',
      'createStartDate',
      'createEndDate',
      'createExecStaffName'
    ].forEach((binding) => {
      const col = grid.getColumn(binding);
      col.allowMerging = true;
      col.allowResizing = true;
      panel.setCellData(0, col.index, col.header);
    });

  }

  /**
   * 棚卸データ作成機能
   * @param data 作成条件情報
   */
  create(data: InvCreationCriteria) {

    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const execStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const period: string = data.period;
    const overwriteFlag: boolean = data.overwriteFlag;

    this.invService.callCreateInvData(companyCode, period, execStaffCode, overwriteFlag)
      .subscribe(
        () => {
          this.messageService.info(SystemMessage.Info.msg100002);
          this.updateData();
        }
      );

  }

  /**
   * グリッドのデータを更新
   */
  updateData() {
    const companyCode: string = this.sessionInfo.loginCompanyCode;

    this.invService.searchInvStat(companyCode).subscribe(
      (data: InvStat[]) => {
        this.dataReleaseList = data;
        this.dataReleaseList.forEach((row, index) => {
          row.periodFormated = row.period.substring(0, 4) + '-' + row.period.substring(4, 6);
          if (index === this.dataReleaseList.length - 1) {
          }
        });

        this.gridDataReleaseList.resetSelection();
      }
    );
  }

  /**
   * 公開設定ポップアップの初期処理、表示
   * @param data　選択中行データ
   */
  showReleaseSettingsPopup(data: EaFlexGrid, eventTarget) {

    if (!data.selectedRows.length || !data.selectedRows[0].dataItem.period) {
      return;
    }

    const selectedPeriod: string = data.selectedRows[0].dataItem.period;
    this.dataReleaseList.map((obj, i: number) => {
      if (obj.period === selectedPeriod) {
        this.popup.setData(this.dataReleaseList[i]);
        this.popup.open(eventTarget);
      }
    });
  }

  /**
   * 公開設定ポップアップで行データを選択された場合
   * @param data　行データ
   */
  onRowUpdate(data: InvStatEx) {
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const execStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const period: string = data.period;
    const publicTypeArray: string[] = [
      data.fldTanPublicType,
      data.fldRoPublicType,
      data.fldIntPublicType,
      data.lePublicType,
      data.rePublicType,
      data.equipPublicType
    ];
    const sendExecArray: boolean[] = [
      data.fldTanMail,
      data.fldRoMail,
      data.fldIntMail,
      data.leMail,
      data.reMail,
      data.equipMail
    ];

    this.invService.publicInvData(companyCode, period, publicTypeArray, sendExecArray, execStaffCode)
      .subscribe(
        () => {
          this.messageService.info(SystemMessage.Info.msg100004);
          this.popup.close();
          this.init();
        }
      );
  }
}
