import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { AdminComponent } from '../admin.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { PpfsImportService } from 'src/app/services/api/ppfs-import.service';

import { DeepCopy } from 'src/app/utils/deep-copy';

import { SessionInfo } from 'src/app/models/session-info';
import { PpfsStat } from 'src/app/models/api/ppfs-import/ppfs-stat';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { SystemMessage } from 'src/app/const/system-message';


/**
 * ProPlus取込コンポネント
 */

@Component({
  selector: 'app-proplus',
  templateUrl: './proplus.component.html',
  styleUrls: ['./proplus.component.scss']
})
export class ProplusComponent extends AbstractChildComponent<AdminComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * 取込年月
   */
  operationPeriod: string;

  /**
   * 取込一覧情報
   */
  operationsList: PpfsStat[];

  /**
   * データ種類一覧情報
   */
  dataType = ['固定資産', 'リース・レンタル'];

  /**
   * 作成用フラグ一覧情報
   */
  schCreateFlag = ['行う'];

  @ViewChild('gridOperationsList', null) gridOperationsList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private ppfsImportService: PpfsImportService,
    private messageService: MessageService
  ) {
    super();
    this.fb = fb;
    this.searchForm = this.fb.group({
      companyName: [''],
      period: [''],
      dataType: this.fb.array(this.dataType.map((x) => true)),
      schCreateFlag: this.fb.array(this.schCreateFlag.map((x) => true))
    });
  }

  /**
   * 画面読み込み
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
    const companyCode = this.sessionInfo.loginCompanyCode;
    this.ppfsImportService.getPpfsCurrentPeriodRT(companyCode).subscribe(
      (resp: NonObjectResponse<string>) => {
        this.searchForm.controls.companyName.setValue(this.sessionInfo.loginCompanyName);
        this.searchForm.controls.period.setValue(resp.value);
      }
    );
    this.update();
  }

  toggleDataType(event: MouseEvent) {
    const target = event.target as HTMLInputElement;
    if (target.id === 'item-data-type-0') {
      if (!target.checked && this.searchForm.controls.dataType.value[1]) {
        this.searchForm.controls.dataType.setValue([true, true]);
        this.messageService.info(SystemMessage.Info.msg100006);
      }
    }
    if (target.id === 'item-data-type-1') {
      if (target.checked) {
        this.searchForm.controls.dataType.setValue([true, true]);
      }
    }
  }

  /**
   * 更新処理
   */
  update() {
    const companyCode = this.sessionInfo.loginCompanyCode;
    this.ppfsImportService.getPpfsStatList(companyCode).subscribe(
      (resp: PpfsStat[]) => {
        this.operationsList = [];
        resp.forEach((obj) => {
          const formatedDate = DeepCopy(obj);
          formatedDate.periodFormated = obj.period.substring(0, 4) + '-' + obj.period.substring(4, 6);
          this.operationsList.push(formatedDate);
        });
        this.gridOperationsList.resetSelection();
      }
    );
  }

  /**
   * 取込実行処理
   */
  execute() {
    const companyCode = this.sessionInfo.loginCompanyCode;
    const execStaffCode = this.sessionInfo.loginUser.staffCode;
    let dataType: string = null;
    if (this.searchForm.controls.dataType.value[0] && this.searchForm.controls.dataType.value[1]) {
      dataType = null;
    } else {
      if (this.searchForm.controls.dataType.value[0]) {
        dataType = '1';
      }
      if (this.searchForm.controls.dataType.value[1]) {
        dataType = '2';
      }
    }
    const schCreateFlag = this.searchForm.controls.schCreateFlag.value[0] ? '1' : '0';

    if (!this.searchForm.controls.dataType.value[0] && !this.searchForm.controls.dataType.value[1]) {
      this.messageService.err(SystemMessage.Err.msg300005);
      return;
    }

    this.ppfsImportService.callPpfsImport(companyCode, dataType, execStaffCode, schCreateFlag).subscribe(
      () => {
        this.messageService.info(SystemMessage.Info.msg100007);
        this.update();
      }
    );
  }

}
