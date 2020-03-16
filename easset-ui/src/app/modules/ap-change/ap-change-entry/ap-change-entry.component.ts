import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver, OnChanges } from '@angular/core';
import * as wjGrid from 'wijmo/wijmo.grid';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApChangeComponent } from '../ap-change.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { StatusSelectionComponent } from 'src/app/parts/lov/status-selection/status-selection.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { User } from 'src/app/models/api/user';
import { ApChangeService } from 'src/app/services/api/ap-change.service';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { ApChange } from 'src/app/models/api/ap-change/ap-change.model';
import { ApChangeLineCostSec } from 'src/app/models/api/ap-change/ap-change-line-cost-sec.model';
import { ApChangeLineLic } from 'src/app/models/api/ap-change/ap-change-line-lic.model';
import { ApChangeLineAst } from 'src/app/models/api/ap-change/ap-change-line-ast.model';
import { ApChangeLineContract } from 'src/app/models/api/ap-change/ap-change-line-contract.model';
import { ApChangeLineFld } from 'src/app/models/api/ap-change/ap-change-line-fld.model';
import { ApChangeSC } from 'src/app/models/api/ap-change/ap-change-sc.model';
import { ApChangeSR } from 'src/app/models/api/ap-change/ap-change-sr.model';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { SystemConst } from 'src/app/const/system-const';
import { LineCostSecListComponent } from './line-cost-sec-list/line-cost-sec-list.component';
import { MasterService } from 'src/app/services/api/master.service';

@Component({
  selector: 'app-ap-change-entry',
  templateUrl: './ap-change-entry.component.html',
  styleUrls: ['./ap-change-entry.component.scss']
})
export class ApChangeEntryComponent extends AbstractChildComponent<ApChangeComponent> implements OnInit, OnChanges {
  /**
   * 
   */
  apChangeForm: FormGroup;
  apStaffForm: FormGroup;
  sectionBox: FormGroup;
  staffBox: FormGroup;
  officeBox: FormGroup;
  useStaffBox: FormGroup;
  intHolStaffBox: FormGroup;
  repOfficeBox: FormGroup;
  costBox: FormGroup;
  costSecBox: FormGroup;
  staffInfo: FormGroup;
  allowStatus = true;
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;
  /**
   * 
   */
  staffCode: string;
  /**
   * 
   */
  accessLevel: string;
  /**
   * 
   */
  currentMenuId: string;
  /**
   * 
   */
  isSectionBox = false;
  isStaffBox = false;
  isOfficeBox = false;
  isUseStaffBox = false;
  isRepOfficeBox = false;
  isCostBox = false;
  isCostSecBox = false;
  isIntHolStaffBox = false;
  date: Date;
  isCostSecOld = false;
  isCostSecNew = false;

  apChange: ApChange;
  apChangeLineLic: ApChangeLineLic[];
  apChangeLineAst: ApChangeLineAst[];
  apChangeLineContractLease: ApChangeLineContract[];
  apChangeLineContractRental: ApChangeLineContract[];
  apChangeLineFldTan: ApChangeLineFld[];
  apChangeLineFldInt: ApChangeLineFld[];
  apChangeLineCostSecOld: ApChangeLineCostSec[];
  apChangeLineCostSecNew: ApChangeLineCostSec[];

  // tslint:disable-next-line:no-any
  lineCostSecRef: any;
  valueChangeId: string;
  sourceApplicationId: string;
  mailToSetting = [];
  /**
   * (検索画面の更新判別に使用) 0:新規作成なし、1:更新なし、2:新規作成・更新、3:削除
   */
  modeUpdateFlag = {
    UPDATE_FLAG_NONE: 0,
    UPDATE_FLAG_UPDATE: 1,
    UPDATE_FLAG_DELETE: 2,
  };
  /**
   *
   */
  updateFlag: number;
  holSectionMsg: boolean;
  assetTypeMsgHolSec: boolean;
  holSectionGB: boolean;
  holStaffGB: boolean;
  holOfficeGB: boolean;
  useStaffGB: boolean;
  intHolStaffGB: boolean;
  holRepOfficeGB: boolean;
  assetTypeMsgHolRepOffice: boolean;
  costGB: boolean;
  costSecGB: boolean;
  costSectionMsgCost: boolean;
  assetTypeMsgCost: boolean;
  costSectionMsgCostSec: boolean;
  assetTypeMsgCostSec: boolean;
  assetSearchChange: boolean;
  licSearchChange: boolean;
  fldIntEaSearchChange: boolean;
  lldSearchChange: boolean;
  fldTanSearchChange: boolean;
  fldIntSearchChange: boolean;
  /**
   *
   */
  @ViewChild(StatusSelectionComponent, null) popupStatus: StatusSelectionComponent;
  @ViewChild('gridCostSecOldList', null) gridCostSecOldList: EaFlexGrid;
  @ViewChild('gridCostSecNewList', null) gridCostSecNewList: EaFlexGrid;
  @ViewChild('gridLineContractLeaseList', null) gridLineContractLeaseList: EaFlexGrid;
  @ViewChild('gridLineLicList', null) gridLineLicList: EaFlexGrid;
  @ViewChild('gridLineAstList', null) gridLineAstList: EaFlexGrid;
  @ViewChild('gridLineContractRentalList', null) gridLineContractRentalList: EaFlexGrid;
  @ViewChild('gridLineFldTanList', null) gridLineFldTanList: EaFlexGrid;
  @ViewChild('gridLineFldIntList', null) gridLineFldIntList: EaFlexGrid;
  @ViewChild(LineCostSecListComponent, null) lineCostSecListComponent: LineCostSecListComponent;
  constructor(
    private sessionService: SessionService,
    private apchangeService: ApChangeService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private masterService: MasterService,
  ) {
    super();
  }

  // VI Khởi tạo
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.staffCode = this.sessionInfo.loginUser.staffCode;
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.currentMenuId = this.sessionInfo.currentMenuId;
    this.initForm();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.apchangeService.getApChange(param.params.applicationId).subscribe((resp: ApChange) => {
          this.apChange = resp;
          this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_NONE;
          console.log('this.apChange: ', this.apChange);
          this.initParam(this.apChange);
        });
      }
      // else if (param.action === 'initP') {
      //   this.apchangeService.getApChange(param.params.applicationId).subscribe((resp: ApChange) => {
      //     this.apChange = resp;
      //     this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_UPDATE;
      //     console.log('this.apChange: ', this.apChange);
      //     this.initParam(this.apChange);
      //   });
      // } else if (param.action === 'initC') {
      //   this.apchangeService.getApChange(param.params.applicationId).subscribe((resp: ApChange) => {
      //     this.apChange = resp;
      //     this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_DELETE;
      //     console.log('this.apChange: ', this.apChange);
      //     this.initParam(this.apChange);
      //   });
      // }
    });

  }

  ngOnChanges() {
    // const element = document.getElementById('holSectionMsg');
    // const cStyle = window.getComputedStyle(element);
    // const visibility = cStyle.getPropertyValue('visibility');
    // this.holSectionMsg = visibility === 'visible' ? true : false;
    // this.holSectionMsg = getComputedStyle(document.getElementById('holSectionMsg')).getPropertyValue('visibility') === 'visible' ? true : false;
    // this.assetTypeMsgHolSec = getComputedStyle(document.getElementById('assetTypeMsgHolSec')).getPropertyValue('visibility') === 'visible' ? true : false;
    setTimeout(() => {
      if (this.apChange && (this.apChange.apChangeType === '1' || this.apChange.apChangeType === '2' || this.apChange.apChangeType === '3') && (this.apChange.apChangeLineAst.length > 0 ||
        this.apChange.apChangeLineLic.length > 0 || this.apChange.apChangeLineFldInt.length > 0)) {
        const req = this.getHolSectionMixFlag();
        this.holSectionMsg = req ? true : false;
      } else {
        this.holSectionMsg = false;
      }

      if (this.apChange && (this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0 || this.apChange.apChangeLineContractLease.length > 0
        || this.apChange.apChangeLineContractRental.length > 0)) {
        const req = this.getAstTypeMixFlag();
        this.assetTypeMsgHolSec = req ? true : false;
      } else {
        this.assetTypeMsgHolSec = false;
      }

      if (this.apChange && (this.apChange.apChangeLineAst.length > 0 || this.apChange.apChangeLineLic.length > 0
        || this.apChange.apChangeLineContractLease.length > 0 || this.apChange.apChangeLineContractRental.length > 0 || this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0)
        && (this.apChange.apChangeLineAst.length > 0 || this.apChange.apChangeLineLic.length > 0 || this.apChange.apChangeLineFldInt.length > 0)
        && (this.apChange.apChangeType === '1' || this.apChange.apChangeType === '2' || this.apChange.apChangeType === '3' || this.sectionBox.get('holSectionCode').value !== '')) {
        this.holSectionGB = true;
      } else {
        this.holSectionGB = false;
      }

      if (this.apChange && (this.apChange.apChangeType === '1' || this.apChange.apChangeType === '2') && !this.holSectionMsg && !this.assetTypeMsgHolSec) {
        this.holStaffGB = true;
      } else {
        this.holStaffGB = false;
      }

      if (this.apChange && this.apChange.apChangeType === '1') {
        this.holOfficeGB = true;
        this.useStaffGB = true;
      } else {
        this.holOfficeGB = false;
        this.useStaffGB = false;
      }
      this.intHolStaffGB = (this.apChange && this.apChange.apChangeType === '3') ? true : false;

      if (this.apChange && (this.apChange.apChangeType === 'A' || this.apChange.apChangeType === 'B' || this.apChange.apChangeType === 'C') && (this.apChange.apChangeLineContractLease.length > 0 ||
        this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0)) {
        this.holRepOfficeGB = true;
      } else {
        this.holRepOfficeGB = false;
      }

      if (this.apChange && (this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0 || this.apChange.apChangeLineContractLease.length > 0 ||
        this.apChange.apChangeLineContractRental.length > 0)) {
        this.assetTypeMsgHolRepOffice = this.getAstTypeMixFlag() ? true : false;
      } else {
        this.assetTypeMsgHolRepOffice = false;
      }

      if (this.apChange && (this.apChange.apChangeLineAst.length > 0 || this.apChange.apChangeLineLic.length > 0 || this.apChange.apChangeLineContractLease.length > 0
        || this.apChange.apChangeLineContractRental.length > 0 || this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0)
        && (this.apChange.apChangeLineContractLease.length > 0 || this.apChange.apChangeLineContractRental.length > 0 || this.apChange.apChangeLineFldTan.length > 0
          || this.apChange.apChangeLineFldInt.length > 0) && (this.apChange.apChangeType === 'A' || this.apChange.apChangeType === 'B' || this.apChange.apChangeType === 'C')
        && (this.costBox.get('costType').value !== null || this.costBox.get('costType').value !== '')) {
        this.costGB = true;
      } else {
        this.costGB = false;
      }

      if (this.apChange && (this.apChange.apChangeLineContractLease.length > 0 || this.apChange.apChangeLineContractRental.length > 0 || this.apChange.apChangeLineFldTan.length > 0
        || this.apChange.apChangeLineFldInt.length > 0)) {
        this.costSectionMsgCost = this.getCostSectionMixFlag() ? true : false;
      } else {
        this.costSectionMsgCost = false;
      }

      if (this.apChange && (this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0 || this.apChange.apChangeLineContractLease.length > 0
        || this.apChange.apChangeLineContractRental.length > 0)) {
        this.assetTypeMsgCost = this.getAstTypeMixFlag() ? true : false;
      } else {
        this.assetTypeMsgCost = false;
      }

      if (this.apChange && (this.apChange.apChangeLineAst.length > 0 || this.apChange.apChangeLineLic.length > 0 || this.apChange.apChangeLineContractLease.length > 0
        || this.apChange.apChangeLineContractRental.length > 0 || this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0)
        && (this.apChange.apChangeLineContractLease.length > 0 || this.apChange.apChangeLineContractRental.length > 0 || this.apChange.apChangeLineFldTan.length > 0
          || this.apChange.apChangeLineFldInt.length > 0) && (this.apChange.apChangeType === 'A' || this.apChange.apChangeType === 'B' || this.apChange.apChangeType === 'C' || this.costSecBox.get('costCompanyCode').value !== '')) {
        this.costSecGB = true;
      } else {
        this.costSecGB = false;
      }

      if (this.apChange && (this.apChange.apChangeLineContractLease.length > 0 || this.apChange.apChangeLineContractRental.length > 0 || this.apChange.apChangeLineFldTan.length > 0
        || this.apChange.apChangeLineFldInt.length > 0)) {
        this.costSectionMsgCostSec = this.getCostSectionMixFlag() ? true : false;
      } else {
        this.costSectionMsgCostSec = false;
      }

      if (this.apChange && (this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0 || this.apChange.apChangeLineContractLease.length > 0
        || this.apChange.apChangeLineContractRental.length > 0)) {
        this.assetTypeMsgCostSec = this.getAstTypeMixFlag() ? true : false;
      } else {
        this.assetTypeMsgCostSec = false;
      }

      if (((this.apChange.apChangeType === '' && this.currentMenuId === '07010') || this.apChange.apChangeType === '1') && !(this.apChangeForm.controls.apStatus.value === '3' ||
        (this.apChangeForm.controls.apStatus.value === '2' && (this.accessLevel === 'C' || this.accessLevel === 'B')) || this.apChangeForm.controls.apStatus.value === '5')) {
        this.assetSearchChange = true;
      } else {
        this.assetSearchChange = false;
      }

      if (((this.apChange.apChangeType === '' && this.currentMenuId === '07010') || this.apChange.apChangeType === '2') && !(this.apChangeForm.controls.apStatus.value === '3' ||
        (this.apChangeForm.controls.apStatus.value === '2' && (this.accessLevel === 'C' || this.accessLevel === 'B')) || this.apChangeForm.controls.apStatus.value === '5') &&
        (this.accessLevel === 'S' || this.accessLevel === 'B')) {
        this.licSearchChange = true;
      } else {
        this.licSearchChange = false;
      }

      if (((this.apChange.apChangeType === '' && this.currentMenuId === '07010') || this.apChange.apChangeType === '3') && !(this.apChangeForm.controls.apStatus.value === '3' ||
        (this.apChangeForm.controls.apStatus.value === '2' && (this.accessLevel === 'C' || this.accessLevel === 'B')) || this.apChangeForm.controls.apStatus.value === '5') &&
        (this.accessLevel === 'S' || this.accessLevel === 'B')) {
        this.fldIntEaSearchChange = true;
      } else {
        this.fldIntEaSearchChange = false;
      }

      if (((this.apChange.apChangeType === '' && this.currentMenuId === '07030') || this.apChange.apChangeType === 'A') && !(this.apChangeForm.controls.apStatus.value === '3' ||
        (this.apChangeForm.controls.apStatus.value === '2' && (this.accessLevel === 'C' || this.accessLevel === 'B')) || this.apChangeForm.controls.apStatus.value === '5')) {
        this.lldSearchChange = true;
      } else {
        this.lldSearchChange = false;
      }

      if (((this.apChange.apChangeType === '' && this.currentMenuId === '07030') || this.apChange.apChangeType === 'B') && !(this.apChangeForm.controls.apStatus.value === '3' ||
        (this.apChangeForm.controls.apStatus.value === '2' && (this.accessLevel === 'C' || this.accessLevel === 'B')) || this.apChangeForm.controls.apStatus.value === '5')) {
        this.fldTanSearchChange = true;
      } else {
        this.fldTanSearchChange = false;
      }

      if (((this.apChange.apChangeType === '' && this.currentMenuId === '07030') || this.apChange.apChangeType === 'C') && !(this.apChangeForm.controls.apStatus.value === '3' ||
        (this.apChangeForm.controls.apStatus.value === '2' && (this.accessLevel === 'C' || this.accessLevel === 'B')) || this.apChangeForm.controls.apStatus.value === '5')) {
        this.fldIntSearchChange = true;
      } else {
        this.fldIntSearchChange = false;
      }
    }, 300);
  }

  // VI Gán dữ liệu ban đầu cho form
  initParam(data) {
    if (data !== null) {
      this.apChangeForm = this.fb.group({
        applicationId: [data.applicationId ? data.applicationId : ''],
        apStatus: [data.apStatus ? data.apStatus : 1],
        apStatusName: [this.getApStatus(data.apStatus)],
        eappId: [data.eappId ? data.eappId : ''],
        apDate: [data.apDate ? data.apDate : new Date()],
        apChangeTypeName: [data.apChangeTypeName ? data.apChangeTypeName : ''],
      });

      if (data.holCompanyCode && data.holSectionCode) {
        this.isSectionBox = true;
      }
      this.sectionBox = this.fb.group({
        holCompanyName: [data.holCompanyName ? data.holCompanyName : ''],
        holCompanyCode: [data.holCompanyCode ? data.holCompanyCode : ''],
        holSectionCode: [data.holSectionCode ? data.holSectionCode : ''],
        holSectionName: [data.holSectionName ? data.holSectionName : ''],
        holSectionYear: [data.holSectionYear ? data.holSectionYear : ''],
        holSectionWithFlag: [data.holSectionWithFlag ? (data.holSectionWithFlag === '0' ? false : true) : false],
      });

      if (data.holStaffName && data.holStaffCode) {
        this.isStaffBox = true;
      }
      const holStaffCodeName = data.holStaffCode + ' ' + data.holStaffName;
      this.staffBox = this.fb.group({
        holStaffName: [data.holStaffName ? data.holStaffName : ''],
        holStaffCode: [data.holStaffCode ? holStaffCodeName : ''],
      });

      if (data.holOfficeCode && data.holOfficeName) {
        this.isOfficeBox = true;
      }
      this.officeBox = this.fb.group({
        holOfficeCode: [data.holOfficeCode ? data.holOfficeCode : ''],
        holOfficeName: [data.holOfficeName ? data.holOfficeName : ''],
        holOfficeFloor: [data.holOfficeFloor ? data.holOfficeFloor : 0],
        holOfficeRoomNum: [data.holOfficeRoomNum ? data.holOfficeRoomNum : ''],
        holOfficeRackNum: [data.holOfficeRackNum ? data.holOfficeRackNum : '']
      });

      if (data.useStaffCode && data.useStaffName) {
        this.isUseStaffBox = true;
      }
      this.useStaffBox = this.fb.group({
        useStaffCode: [data.useStaffCode ? data.useStaffCode : ''],
        useStaffName: [data.useStaffName ? data.useStaffName : ''],
        useStaffCompanyCode: [data.useStaffCompanyCode ? data.useStaffCompanyCode : ''],
        useStaffCompanyName: [data.useStaffCompanyName ? data.useStaffCompanyName : ''],
        useStaffSectionCode: [data.useStaffSectionCode ? data.useStaffSectionCode : ''],
        useStaffSectionName: [data.useStaffSectionName ? data.useStaffSectionName : ''],
        useStaffSectionYear: [data.useStaffSectionYear ? data.useStaffSectionYear : 2019],
      });

      if (data.intHolStaffCode && data.intHolStaffName) {
        this.isIntHolStaffBox = true;
      }
      this.intHolStaffBox = this.fb.group({
        intHolStaffCode: [data.intHolStaffCode ? data.intHolStaffCode : ''],
        intHolStaffName: [data.intHolStaffName ? data.intHolStaffName : ''],
        intHolStaffCompanyCode: [data.intHolStaffCompanyCode ? data.intHolStaffCompanyCode : ''],
        intHolStaffCompanyName: [data.intHolStaffCompanyName ? data.intHolStaffCompanyName : ''],
        intHolStaffSectionCode: [data.intHolStaffSectionCode ? data.intHolStaffSectionCode : ''],
        intHolStaffSectionName: [data.intHolStaffSectionName ? data.intHolStaffSectionName : ''],
        intHolStaffSectionYear: [data.intHolStaffSectionYear ? data.intHolStaffSectionYear : 2019],
      });

      if (data.holRepOfficeCode && data.holRepOfficeName) {
        this.isRepOfficeBox = true;
      }
      this.repOfficeBox = this.fb.group({
        holRepOfficeCode: [data.holRepOfficeCode ? data.holRepOfficeCode : ''],
        holRepOfficeName: [data.holRepOfficeName ? data.holRepOfficeName : ''],
      });

      if (data.costType) {
        this.isCostBox = true;
      }
      this.costBox = this.fb.group({
        costType: [data.costType ? data.costType : ''],
        costDepPrjCode: [data.costDepPrjCode ? data.costDepPrjCode : ''],
        costDepPrjName: [data.costDepPrjName ? data.costDepPrjName : ''],
        costDepPrjType: [data.costDepPrjType ? data.costDepPrjType : ''],
      });

      if (data.apChangeLineCostSecOld[0]) {
        this.isCostSecBox = true;
      }
      this.costSecBox = this.fb.group({
        apChangeLineCostSecLineSeq: [1],
        // costCompanyCode_LEF: [''],
        // costCompanyName_LEF: [''],
        costSectionCode_LEF: [''],
        // costSectionName_LEF: [data.costDepPrjType ? data.costDepPrjType : ''],
        costCompanyCode: [data.costCompanyCode ? data.costCompanyCode : ''],
        costCompanyName: [data.costCompanyName ? data.costCompanyName : ''],
        costSectionYear: [data.costSectionYear ? data.costSectionYear : 2019],
        costSectionCode: [data.costSectionCode ? data.costSectionCode : ''],
        costSectionName: [data.costSectionName ? data.costSectionName : ''],
        // costDist_LEF: [0],
      });
      this.staffInfo = this.fb.group({
        apprHolStaffCodeOld: [data.apprHolStaffCodeOld ? data.apprHolStaffCodeOld : ''],
        apprHolStaffNameOld: [data.apprHolStaffNameOld ? data.apprHolStaffNameOld : ''],
        apprHolStaffNameOldDisp: [data.apprHolStaffNameOldDisp ? data.apprHolStaffNameOldDisp : ''],
        apprCostStaffCodeOld: [data.apprCostStaffCodeOld ? data.apprCostStaffCodeOld : ''],
        apprCostStaffNameOld: [data.apprCostStaffNameOld ? data.apprCostStaffNameOld : ''],
        apprCostStaffSkipFlagOld: [data.apprCostStaffSkipFlagOld === '0' ? false : true],
        apprSuperiorStaffCodeOld: [data.apprSuperiorStaffCodeOld ? data.apprSuperiorStaffCodeOld : ''],
        apprSuperiorStaffNameOld: [data.apprSuperiorStaffNameOld ? data.apprSuperiorStaffNameOld : ''],

        apprHolStaffCodeNew: [data.apprHolStaffCodeNew ? data.apprHolStaffCodeNew : ''],
        apprHolStaffNameNew: [data.apprHolStaffNameNew ? data.apprHolStaffNameNew : ''],
        apprHolStaffNameNewDisp: [data.apprHolStaffNameNewDisp ? data.apprHolStaffNameNewDisp : ''],
        apprCostStaffCodeNew: [data.apprCostStaffCodeNew ? data.apprCostStaffCodeNew : ''],
        apprCostStaffNameNew: [data.apprCostStaffNameNew ? data.apprCostStaffNameNew : ''],
        apprSuperiorStaffCodeNew: [data.apprSuperiorStaffCodeNew ? data.apprSuperiorStaffCodeNew : ''],
        apprSuperiorStaffNameNew: [data.apprSuperiorStaffNameNew ? data.apprSuperiorStaffNameNew : ''],
        apprCostStaffSkipFlagNew: [data.apprCostStaffSkipFlagNew === '0' ? false : true],
      });
      this.apStaffForm = this.fb.group({
        // staffCode: [this.sessionInfo.loginUser.staffCode],
        // staffName: [this.sessionInfo.loginUser.name],

        apCreateStaffCode: [data.apCreateStaffCode ? data.apCreateStaffCode : ''],
        apCreateStaffName: [data.apCreateStaffName ? data.apCreateStaffName : ''],
        apCreateCompanyCode: [data.apCreateCompanyCode ? data.apCreateCompanyCode : ''],
        apCreateCompanyName: [data.apCreateCompanyName ? data.apCreateCompanyName : ''],
        apCreateSectionCode: [data.apCreateSectionCode ? data.apCreateSectionCode : ''],
        apCreateSectionName: [data.apCreateSectionName ? data.apCreateSectionName : ''],
        apCreateSectionYear: [data.apCreateSectionYear ? data.apCreateSectionYear : 2020],
        apCreateTel: [data.apCreateTel ? data.apCreateTel : ''],
        // ----------------------
        apStaffCode: [data.apStaffCode ? data.apStaffCode : ''],
        apStaffName: [data.apStaffName ? data.apStaffName : ''],
        apCompanyCode: [data.apCompanyCode ? data.apCompanyCode : ''],
        apCompanyName: [data.apCompanyName ? data.apCompanyName : ''],
        apSectionCode: [data.apSectionCode ? data.apSectionCode : ''],
        apSectionName: [data.apSectionName ? data.apSectionName : ''],
        apSectionYear: [data.apSectionYear ? data.apSectionYear : 2020],
        apTel: [data.apTel ? data.apTel : ''],
        // ----------------------
        chgScheduleDate: [data.chgScheduleDate ? data.chgScheduleDate : Date()],
        chgSchedulePeriodName: [data.chgSchedulePeriodName ? data.chgSchedulePeriodName : ''],
        chgSchedulePeriod: [data.chgSchedulePeriod ? data.chgSchedulePeriod : ''],
        chgDescription: [data.chgDescription ? data.chgDescription : ''],
      });
      this.apChangeLineLic = data.apChangeLineLic !== undefined ? data.apChangeLineLic : new Array();
      this.apChangeLineAst = data.apChangeLineAst !== undefined ? data.apChangeLineAst : new Array();
      this.apChangeLineContractLease = data.apChangeLineContractLease !== undefined ? data.apChangeLineContractLease : new Array();
      this.apChangeLineContractRental = data.apChangeLineContractRental !== undefined ? data.apChangeLineContractRental : new Array();
      this.apChangeLineFldTan = data.apChangeLineFldTan !== undefined ? data.apChangeLineFldTan : new Array();
      this.apChangeLineFldInt = data.apChangeLineFldInt !== undefined ? data.apChangeLineFldInt : new Array();
      this.lineCostSecListComponent.apChangeLineCostSecList = data.apChangeLineCostSecOld;
      this.setItemDef();
    } else {
      if (this.currentMenuId === SystemConst.Menu.Eapp.menuIdApChangeEapp) {
        this.messageService.info('申請内容を表示できません。以下の理由が考えられます。\n\n・申請書に対して「引戻し」もしくは「差戻し」\n　が行われた。\n\n・eAsset ⇔ e申請システム間連携に失敗した。\n　この場合当申請書で承認を進める事はできませんので、\n　申請上で「引戻し」もしくは「差戻し」を行い、\n　その後eAssetより再度申請を行ってください。');
      } else {
        this.messageService.err('移動申請データが存在しません。');
        this.goBack();
      }
    }
  }
  // VI Khai báo thuộc tính của form
  initForm() {
    const apCompanyCode = this.sessionInfo.loginCompanyCode;
    const apCompanyName = this.sessionInfo.loginCompanyName;
    let apSectionCode = '';
    let apSectionName = '';
    if (apCompanyCode === this.sessionInfo.loginUser.companyCode) {
      apSectionCode = this.sessionInfo.loginUser.sectionCode;
      apSectionName = this.sessionInfo.loginUser.sectionName;
    }
    this.apChangeForm = this.fb.group({
      applicationId: [''],
      apStatus: [1],
      apStatusName: ['未申請'],
      eappId: [''],
      apDate: [new Date()],
      apChangeTypeName: [''],
    });
    this.sectionBox = this.fb.group({
      holCompanyName: [''],
      holCompanyCode: [''],
      holSectionCode: [''],
      holSectionName: [''],
      holSectionYear: [0],
      holSectionWithFlag: [false],
    });
    this.staffBox = this.fb.group({
      holStaffName: [''],
      holStaffCode: [''],
    });
    this.officeBox = this.fb.group({
      holOfficeCode: [''],
      holOfficeName: [''],
      holOfficeFloor: [0],
      holOfficeRoomNum: [''],
      holOfficeRackNum: [''],
    });
    this.useStaffBox = this.fb.group({
      useStaffCode: [''],
      useStaffName: [''],
      useStaffCompanyCode: [''],
      useStaffCompanyName: [''],
      useStaffSectionCode: [''],
      useStaffSectionName: [''],
      useStaffSectionYear: [0],
    });
    this.intHolStaffBox = this.fb.group({
      intHolStaffCode: [''],
      intHolStaffName: [''],
      intHolStaffCompanyCode: [''],
      intHolStaffCompanyName: [''],
      intHolStaffSectionCode: [''],
      intHolStaffSectionName: [''],
      intHolStaffSectionYear: [0],
    });
    this.repOfficeBox = this.fb.group({
      holRepOfficeCode: [''],
      holRepOfficeName: [''],
    });
    this.costBox = this.fb.group({
      costType: [''],
      costDepPrjCode: [''],
      costDepPrjName: [''],
      costDepPrjType: [''],
    });
    this.costSecBox = this.fb.group({
      apChangeLineCostSecLineSeq: [1],
      costSectionCode_LEF: [''],
      costCompanyCode: [''],
      costCompanyName: [''],
      costSectionYear: [''],
      costSectionCode: [''],
      costSectionName: [''],
    });
    this.staffInfo = this.fb.group({
      apprHolStaffCodeOld: [''],
      apprHolStaffNameOld: [''],
      apprHolStaffNameOldDisp: [''],
      apprCostStaffCodeOld: [''],
      apprCostStaffNameOld: [''],
      apprCostStaffSkipFlagOld: [false],
      apprSuperiorStaffCodeOld: [''],
      apprSuperiorStaffNameOld: [''],

      apprHolStaffCodeNew: [''],
      apprHolStaffNameNew: [''],
      apprHolStaffNameNewDisp: [''],
      apprCostStaffCodeNew: [''],
      apprCostStaffNameNew: [''],
      apprSuperiorStaffCodeNew: [''],
      apprSuperiorStaffNameNew: [''],
      apprCostStaffSkipFlagNew: [false],
    });
    this.apStaffForm = this.fb.group({
      // staffCode: [this.sessionInfo.loginUser.staffCode],
      // staffName: [this.sessionInfo.loginUser.name],
      apCreateStaffCode: [this.sessionInfo.loginUser.staffCode],
      apCreateStaffName: [this.sessionInfo.loginUser.name],
      apCreateCompanyCode: [this.sessionInfo.loginUser.companyCode],
      apCreateCompanyName: [this.sessionInfo.loginUser.companyName],
      apCreateSectionCode: [this.sessionInfo.loginUser.sectionCode],
      apCreateSectionName: [this.sessionInfo.loginUser.sectionName],
      apCreateSectionYear: [this.sessionInfo.currentYear.toString()],
      apCreateTel: [this.sessionInfo.loginUser.tel1],
      // ----------------------
      apStaffCode: [this.sessionInfo.loginUser.staffCode],
      apStaffName: [this.sessionInfo.loginUser.name],
      apCompanyCode: [apCompanyCode],
      apCompanyName: [apCompanyName],
      apSectionCode: [apSectionCode],
      apSectionName: [apSectionName],
      apSectionYear: [this.sessionInfo.currentYear.toString()],
      apTel: [this.sessionInfo.loginUser.tel1],
      // ----------------------
      chgScheduleDate: [Date()],
      chgSchedulePeriodName: [''],
      chgSchedulePeriod: [''],
      chgDescription: [''],
    });
    this.apChangeLineFldTan = [];
    this.apChangeLineFldInt = [];
    this.apChangeLineContractLease = [];
    this.apChangeLineContractRental = [];
    this.apChangeLineAst = [];
    this.apChangeLineLic = [];
    this.apChangeLineCostSecNew = [];
    this.setItemDef();
  }

  dataChange(data) {
    console.log(data);
  }

  // TODO
  setItemDef() {

  }

  /**
   * 資産計上部課
   */
  changeLineCostSec(data: ApChangeLineCostSec[]) {
    if (data.length > 0) {
      if (data[0].costSectionCode && data[0].costSectionCode !== '') {
        this.costSecBox.controls.costSectionCode.setValue(data[0].costSectionCode);
        this.costSecBox.controls.costSectionName.setValue(data[0].costSectionName);
        this.costSecBox.controls.costSectionYear.setValue(this.sessionInfo.currentYear);
        this.costSecBox.controls.costCompanyCode.setValue(this.sessionInfo.loginCompanyCode);
        this.costSecBox.controls.costCompanyName.setValue(this.sessionInfo.loginCompanyName);
      }
    }
  }

  getApStatus(data) {
    data = data ? data : 1;
    switch (data) {
      case '1':
        return '未申請';
      case '2':
        return '申請中';
      case '3':
        return '承認済';
      case '4':
        return '未申請(再)';
      case '5':
        return '却下';
    }
  }

  selectStatus(status: LovDataEx) {
    this.apChangeForm.controls.apStatus.setValue(status.code);
    this.apChangeForm.controls.apStatusName.setValue(status.value1);
  }

  selectStaff(data: User) {
    this.staffCode = data.staffCode;
    this.apStaffForm.controls.apStaffCode.setValue(data.staffCode);
    this.apStaffForm.controls.apStaffName.setValue(data.name);
    this.apStaffForm.controls.apCompanyCode.setValue(data.companyCode);
    this.apStaffForm.controls.apCompanyName.setValue(data.companyName);
    this.apStaffForm.controls.apSectionCode.setValue(data.sectionCode);
    this.apStaffForm.controls.apSectionName.setValue(data.sectionName);

    this.sectionBox.controls.holCompanyName.setValue(data.companyName);
    this.sectionBox.controls.holSectionName.setValue(data.sectionName);
    this.sectionBox.controls.holSectionYear.setValue(this.date.getFullYear());
  }
  selectStaffBox(data) {
    this.staffBox.controls.holStaffName.setValue(data.value1);
    this.staffBox.controls.holStaffCode.setValue(data.code);
    this.staffInfo.controls.apprHolStaffNameNew.setValue(data.value1);
    this.staffInfo.controls.apprHolStaffCodeNew.setValue(data.code);
  }
  selectSectionBox(data) {
    this.sectionBox.controls.holCompanyName.setValue(data.companyName);
    this.sectionBox.controls.holCompanyCode.setValue(data.companyCode);
    this.staffBox.controls.holStaffName.setValue('');
    this.staffBox.controls.holStaffCode.setValue('');
  }
  // VI Xử lý hàm t
  selectUseStaffBox(data: User) {
    this.useStaffBox.controls.useStaffCompanyCode.setValue(data.companyCode);
    this.useStaffBox.controls.useStaffCompanyName.setValue(data.companyName);
    this.useStaffBox.controls.useStaffSectionCode.setValue(data.sectionCode);
    this.useStaffBox.controls.useStaffSectionName.setValue(data.sectionName);
  }

  /**
   * 減価償却プロジェクトコード
   */
  changeCostDepPrj(data) {
    this.costBox.controls.costDepPrjCode.setValue(data.code);
    this.costBox.controls.costDepPrjName.setValue(data.name);
    this.costBox.controls.costDepPrjType.setValue(data.value1);
  }

  /**
   *
   */
  showDetail(data) { }

  /**
   * 保有部署を比較し、フラグ取得
   */
  getHolSectionMixFlag() {
    let itemOld = new Object();
    let itemNew = new Object();
    let flag = false;
    const notContractAC = new Array();
    const inContractAC = new Array();
    let addItem = new Object();
    let accessLevelFlag = false;
    if (this.accessLevel !== 'C') {
      accessLevelFlag = true;
    }
    const itemKeyArray = ['holCompanyCode', 'holSectionCode', 'holSectionYear'];
    let lineAC = new Array();
    if (this.apChange.apChangeType === '1') {
      lineAC = this.apChange.apChangeLineAst;
    } else if (this.apChange.apChangeType === '2') {
      lineAC = this.apChange.apChangeLineLic;
    } else if (this.apChange.apChangeType === '3') {
      lineAC = this.apChange.apChangeLineFldInt;
    }
    lineAC.map((item, i) => {
      itemNew = item;
      if (i === 0) {
        itemOld = itemNew;
        inContractAC.push({ astNum: itemOld['astNum'] });
      } else {
        let valDiff = false;
        itemKeyArray.forEach(itemkey => {
          if (itemNew[itemkey] !== itemOld[itemkey]) {
            return valDiff = true;
          }
        });
        if (valDiff) {
          if (accessLevelFlag) {
            // 保有部署と一致しない資産番号を格納
            addItem = { astNum: itemNew['astNum'] };
            notContractAC.push(addItem);
          }
          flag = true;
        } else {
          if (accessLevelFlag) {
            // 保有部署と一致する資産番号を格納
            addItem = { astNum: itemNew['astNum'] };
            notContractAC.push(addItem);
          }
        }
      }
    });
    // 2次チェック(全社権限と部署権限で保有部署が混在した場合のみチェック)
    if (notContractAC.length > 0 && inContractAC.length > 0) {
      notContractAC.map(notContractNum => {
        inContractAC.forEach(inContractNum => {
          if ((notContractNum['astNum'] !== '' && notContractNum['astNum'] !== null) || (inContractNum['astNum'] !== '' && inContractNum['astNum'] !== null)) {
            if (notContractNum['astNum'] === inContractNum['astNum']) {
              return flag = false;
            } else {
              flag = true;
            }
          } else {
            flag = true;
          }
        });
      });
    }
    return flag;
  }

  /**
   * 資産区分が混在するかどうかのフラグ取得
   */
  getAstTypeMixFlag() {
    const leaseAC = this.apChange.apChangeLineContractLease;
    const rentalAC = this.apChange.apChangeLineContractRental;
    const fldTanAC = this.apChange.apChangeLineFldTan;
    const fldIntAC = this.apChange.apChangeLineFldInt;
    let ret = false;
    if (fldTanAC.length > 0 && fldIntAC.length > 0) { // 有形・無形混在
      ret = true;
    } else if ((leaseAC.length > 0 || rentalAC.length > 0) && fldTanAC.length > 0) { // リレ・有形混在
      ret = true;
    } else if ((leaseAC.length > 0 || rentalAC.length > 0) && fldIntAC.length > 0) { // リレ・無形混在
      ret = true;
    }

    return ret;
  }

  /**
   * 資産・契約明細で経費負担部課が混在するかどうかのフラグ取得
   */
  getCostSectionMixFlag() {
    const leaseAC = this.apChange.apChangeLineContractLease;
    const rentalAC = this.apChange.apChangeLineContractRental;
    const fldTanAC = this.apChange.apChangeLineFldTan;
    const fldIntAC = this.apChange.apChangeLineFldInt;
    let itemNew = new Object();
    let flag = false;
    let i: number;
    const itemKey = 'costSectionName';
    let lastKeyValue = '';

    // リース明細
    for (i = 0; i < leaseAC.length; i++) {
      itemNew = leaseAC[i];
      if (lastKeyValue !== '' && lastKeyValue !== itemNew[itemKey]) {
        flag = true;
        break;
      }
      lastKeyValue = itemNew[itemKey];
    }

    // レンタル明細
    if (!flag) {
      for (i = 0; i < rentalAC.length; i++) {
        itemNew = rentalAC[i];
        if (lastKeyValue !== '' && lastKeyValue !== itemNew[itemKey]) {
          flag = true;
          break;
        }
        lastKeyValue = itemNew[itemKey];
      }
    }

    // 有形固定資産明細
    if (!flag) {
      for (i = 0; i < fldTanAC.length; i++) {
        itemNew = fldTanAC[i];
        if (lastKeyValue !== '' && lastKeyValue !== itemNew[itemKey]) {
          flag = true;
          break;
        }
        lastKeyValue = itemNew[itemKey];
      }
    }

    // 無形固定資産明細
    if (!flag) {
      for (i = 0; i < fldIntAC.length; i++) {
        itemNew = fldIntAC[i];
        if (lastKeyValue !== '' && lastKeyValue !== itemNew[itemKey]) {
          flag = true;
          break;
        }
        lastKeyValue = itemNew[itemKey];
      }
    }

    return flag;
  }

  /**
   *
   */
  getApChange() {
    const apChange = Object.assign({}, this.apChangeForm.getRawValue(), this.apStaffForm.getRawValue(), this.sectionBox.getRawValue(),
      this.staffBox.getRawValue(), this.officeBox.getRawValue(), this.useStaffBox.getRawValue(), this.intHolStaffBox.getRawValue(), this.repOfficeBox.getRawValue(), this.costBox.getRawValue(),
      this.costSecBox.getRawValue(), this.staffInfo.getRawValue());
    apChange.apChangeLineFldTan = this.apChangeLineFldTan.length !== 0 ? this.apChangeLineFldTan : new ApChangeLineFld();
    apChange.apChangeLineFldInt = this.apChangeLineFldInt.length !== 0 ? this.apChangeLineFldInt : new ApChangeLineFld();
    apChange.apChangeLineContractLease = this.apChangeLineContractLease.length !== 0 ? this.apChangeLineContractLease : new ApChangeLineContract();
    apChange.apChangeLineContractRental = this.apChangeLineContractRental.length !== 0 ? this.apChangeLineContractRental : new ApChangeLineContract();
    apChange.apChangeLineAst = this.apChangeLineAst.length !== 0 ? this.apChangeLineAst : new ApChangeLineAst();
    apChange.apChangeLineLic = this.apChangeLineLic.length !== 0 ? this.apChangeLineLic : new ApChangeLineLic();
    apChange.apChangeLineCostSecNew = this.lineCostSecListComponent.apChangeLineCostSecList.length !== 0 ? this.lineCostSecListComponent.apChangeLineCostSecList : new ApChangeLineCostSec();
    console.log('apChange value', apChange);
    return apChange;
  }
  /**
   *
   */
  getInfoApChange(id) {
    this.apchangeService.getApChange(id).subscribe((resp: ApChange) => {
      this.apChange = resp;
      this.initParam(resp);
    });
  }

  searchApChange(sc: ApChangeSC) {
    this.apchangeService.searchApChange(this.staffCode, this.accessLevel, sc).subscribe((data: ApChangeSR[]) => {
      // TODO
    });
  }

  /**
   * ボタンクリックイベント
   */
  btnClickEvent(data) {
    switch (data) {
      // 保存・申請・削除
      case 'save':
      case 'apply':
      case 'del':
      case 'approve':
      case 'reject':
      case 'sendBack':
        let msg = '';
        if (this.isCostSecOld) {
          msg = '経費負担部課明細';
        }
        if (msg !== '') {
          this.messageService.warn(msg + 'の入力を「OK」ボタンをクリックして確定させるか、キャンセルしてください。');
        } else {
          // 変更項目の必須チェック(保存、申請のみ)
          if (data === 'save' || data === 'apply') {
            let showFlag = false;
            // 保有部署
            if (this.isSectionBox && this.sectionBox.controls.holSectionName.value === '') {
              msg = msg + '保有部署は必須入力です。' + ' ';
              showFlag = true;
            }
            // 資産管理担当者
            if (this.isStaffBox && this.staffBox.controls.holStaffName.value === '') {
              msg = msg + '資産管理担当者は必須入力です。' + ' ';
              showFlag = true;
            }
            // 資産管理担当者
            if (this.isOfficeBox && this.officeBox.controls.holOfficeName.value === '') {
              msg = msg + '個別設置(使用)場所は必須入力です。' + ' ';
              showFlag = true;
            }
            // 使用者
            if (this.isUseStaffBox && this.useStaffBox.controls.useStaffCode.value === '') {
              msg = msg + '使用者-社員番号は必須入力です。' + ' ';
              showFlag = true;
            }
            // 無形管理担当者
            if (this.isIntHolStaffBox && this.intHolStaffBox.controls.intHolStaffCode.value === '') {
              msg = msg + '無形管理担当者-社員番号は必須入力です。' + ' ';
              showFlag = true;
            }
            // 販売管理費/原価区分
            if (this.isCostBox && this.costBox.controls.costType.value === 2 && this.costBox.controls.costDepPrjCode.value === '') {
              msg = msg + '減価償却プロジェクトコードは必須入力です。' + ' ';
              showFlag = true;
            }
            // 経費負担部署
            if (this.isCostSecBox && this.apChangeLineCostSecNew.length === 0) {
              msg = msg + '経費負担部課は必須入力です。' + ' ';
              showFlag = true;
            }
            // 代表設置場所
            if (this.isRepOfficeBox && this.repOfficeBox.controls.holRepOfficeName.value === '') {
              msg = msg + '代表設置場所は必須入力です。' + ' ';
              showFlag = true;
            }
            // 担当部承認者
            // 現担当者(移動元) ---------------------------------------------------
            if (this.isRepOfficeBox) {
              // 資産管理担当者
              if (this.repOfficeBox.controls.apprHolStaffCodeOld.value === '') {
                msg = msg + '現担当者(移動元)-資産管理担当者は必須入力です。' + ' ';
                // 経費負担部課/GL
              } else if (this.repOfficeBox.controls.apprCostStaffCodeOld.value === '') {
                msg = msg + '現担当者(移動元)-経費負担部課長/GLは必須入力です。' + ' ';
                // 部長
              } else if (this.repOfficeBox.controls.apprSuperiorStaffCodeOld.value === '') {
                msg = msg + '現担当者(移動元)-部長は必須入力です。' + ' ';
              }
              showFlag = true;
            }
            // 現担当者(移動元) ---------------------------------------------------
            if (this.isRepOfficeBox) {
              // 資産管理担当者
              if (this.repOfficeBox.controls.apprHolStaffCodeOld.value === '') {
                msg = msg + '現担当者(移動元)-資産管理担当者は必須入力です。' + ' ';
                // 経費負担部課/GL
              } else if (this.repOfficeBox.controls.apprCostStaffCodeOld.value === '') {
                msg = msg + '現担当者(移動元)-経費負担部課長/GLは必須入力です。' + ' ';
                // 部長
              } else if (this.repOfficeBox.controls.apprSuperiorStaffCodeOld.value === '') {
                msg = msg + '現担当者(移動元)-部長は必須入力です。' + ' ';
              }
              showFlag = true;
            }
            console.log('showMsg: ', msg);
            if (showFlag) {
              this.messageService.info(msg);
              return;
            }
          }
          if (!this.isSectionBox) { this.apChange.holSectionWithFlag = null; }

          const apChange = this.getApChange();
          switch (data) {
            case 'save':
              if (this.apChangeForm.controls.applicationId.value !== '') {
                this.apchangeService.updateApChange(this.staffCode, this.accessLevel, apChange).subscribe((resp) => {
                  console.log('save: ', resp);
                  this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_UPDATE;
                  this.messageService.info('保存しました。');
                  // this.getInfoApChange(resp);
                  this.getInfoApChange(this.apChangeForm.controls.applicationId.value);
                });
              } else {
                this.apchangeService.createApChange(this.staffCode, this.accessLevel, apChange).subscribe((resp) => {
                  console.log('save: ', resp);
                  if (resp !== null) {
                    this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_UPDATE;
                    this.messageService.info('移動申請書番号「' + resp + '」で申請書が作成されました。');
                    if (this.currentMenuId === SystemConst.Menu.menuIdApChangeSearch) {
                      if (this.sourceApplicationId === '') {
                        this.valueChangeId = 'create';
                        const sc = new ApChangeSC();
                        sc.holCompanyCode = this.sessionInfo.loginCompanyCode;
                        // sc.applicationIdPlural = resp;
                        this.searchApChange(sc);
                      }
                    }
                    this.getInfoApChange(resp);
                  } else {
                    this.messageService.err('移動申請データの作成に失敗しました。');
                  }
                });
              }
              break;
            case 'apply':
              this.apchangeService.applyApChange(this.staffCode, this.accessLevel, this.apChange).subscribe((resp) => {
                console.log('save: ', resp);
                if (resp !== null) {
                  this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_UPDATE;
                  // 更新完了メッセージ表示
                  let confirmMsg = '';
                  if (this.apChangeForm.controls.applicationId.value !== '') {
                    confirmMsg = '保存して承認依頼を行いました。';
                  } else {
                    confirmMsg = '移動申請書番号「' + resp + '」で申請書が作成されて承認依頼を行いました。';
                    if (this.currentMenuId === SystemConst.Menu.menuIdApChangeSearch) {
                      if (this.sourceApplicationId !== '') {
                        this.sourceApplicationId = 'create';
                        const sc = new ApChangeSC();
                        sc.holCompanyCode = this.sessionInfo.loginCompanyCode;
                        // sc.applicationIdPlural = resp;
                        this.searchApChange(sc);
                      }
                    }
                  }
                  // 現物情報の申請で保有部署の変更有りの場合は、経費の申請作成も続けて行える
                  if ((this.apChange.apChangeType === '1' || this.apChange.apChangeType === '2' || this.apChange.apChangeType === '3') && this.holSectionGB
                    && (this.apChange.apChangeLineContractLease.length > 0 || this.apChange.apChangeLineContractRental.length > 0
                      || this.apChange.apChangeLineFldTan.length > 0 || this.apChange.apChangeLineFldInt.length > 0)) {
                    this.apChangeForm.controls.applicationId.setValue(resp);
                    // CommonFunction.AlertYesNo(confirmMsg + "\n" + "経費情報(経費負担部課)の移動申請も続けて作成しますか？", continueHandler);
                  } else {
                    this.messageService.info(confirmMsg);
                    this.getInfoApChange(resp);
                  }
                }
              });
              break;
            case 'del':
              this.apchangeService.applyApChange(this.staffCode, this.accessLevel, this.apChange).subscribe((resp) => {
                console.log('save: ', resp);
                this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_DELETE;
                this.messageService.info('削除しました。');
                this.goBack();
              });
              break;
            case 'approve':
              this.apchangeService.approveApChange(this.staffCode, this.apChange).subscribe((resp) => {
                console.log('save: ', resp);
                this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_UPDATE;
                this.messageService.info('承認しました。');
                this.getInfoApChange(this.apChangeForm.controls.applicationId.value);
              });
              break;
            case 'reject':
              this.apchangeService.rejectApChange(this.staffCode, this.apChange, '2', this.apStaffForm.controls.chgDescription.value).subscribe((resp) => {
                console.log('save: ', resp);
                this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_UPDATE;
                this.messageService.info('却下・差戻しを行いました。');
                this.getInfoApChange(this.apChangeForm.controls.applicationId.value);
              });
              break;
            case 'sendBack':
              this.apchangeService.rejectApChange(this.staffCode, this.apChange, '1', this.apStaffForm.controls.chgDescription.value).subscribe((resp) => {
                console.log('save: ', resp);
                this.updateFlag = this.modeUpdateFlag.UPDATE_FLAG_UPDATE;
                this.messageService.info('却下・差戻しを行いました。');
                this.getInfoApChange(this.apChangeForm.controls.applicationId.value);
              });
              break;
          }
        }
        break;
      case 'hist':
        // 履歴画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'init', params: { item: this.apChange } });
        break;
      case 'back':
        // 遷移元画面呼び出し
        this.goBack();
        break;
      // 情報機器等追加検索
      case 'addAstItem_BTN':
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initAddAst', params: {} });
        break;
      // ライセンス追加検索
      case 'addLicItem_BTN':
        // 検索画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initAddLic', params: {} });
        break;
      // 固定資産追加検索
      case 'addFldIntEaItem_BTN':
      case 'addFldTanItem_BTN':
      case 'addFldIntItem_BTN':
        // 検索画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initAddFld', params: {} });
        break;
      // リース・レンタル追加検索
      case 'addLldItem_BTN':
        // 検索画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initAddLld', params: {} });
        break;
    }
  }

  /**
   * リンクボタンクリック用ボタンハンドラー
   */
  linkBtnClickEvent(data) {
    switch (data) {
      // 回付状況詳細
      case 'eappId_LNK':
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initEapp', params: { eappId: this.apChange.eappId } });
        break;
      // 情報機器等遷移のリンク
      case 'assetSearchChange_LNK':
        this.apChange.apChangeType = '1';
        // 情報機器等画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initAsset', params: { eappId: this.apChange.eappId } });
        break;
      // ライセンス遷移のリンク
      case 'licSearchChange_LNK':
        this.apChange.apChangeType = '2';
        // 情報機器等画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initLic', params: { eappId: this.apChange.eappId } });
        break;
      // 無形固定資産(現物)遷移のリンク
      case 'fldIntEaSearchChange_LNK':
        this.apChange.apChangeType = '3';
        // 固定資産画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initFld', params: { eappId: this.apChange.eappId } });
        break;
      // リース・レンタル遷移のリンク
      case 'lldSearchChange_LNK':
        this.apChange.apChangeType = 'A';
        // 固定資産画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initLld', params: { eappId: this.apChange.eappId } });
        break;
      // 有形固定資産遷移のリンク
      case 'fldTanSearchChange_LNK':
        this.apChange.apChangeType = 'B';
        // 固定資産画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initFld', params: { eappId: this.apChange.eappId } });
        break;
      // 無形固定資産遷移のリンク
      case 'fldIntSearchChange_LNK':
        this.apChange.apChangeType = 'C';
        // 固定資産画面呼び出し
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'initFld', params: { eappId: this.apChange.eappId } });
        break;

      //////////////////// 変更・変更取消リンク
      // 保有部署GroupBox
      // 変更 , 変更取消
      case 'sectionBox':
        this.isSectionBox = !this.isSectionBox;
        if (this.isSectionBox) {
          if (this.apChange.apChangeType === '1' || this.apChange.apChangeType === '2') {
            this.isStaffBox = true;
          } else if (this.apChange.apChangeType === '3') {
            this.isUseStaffBox = true;
          }
        } else {
          this.resetHolCompanyGBChild();
          this.resetStaffInfoValue('section');
          if (this.apChange.apChangeType === '1' || this.apChange.apChangeType === '2') {
            this.resetHolCompanyGBChild();
            this.isStaffBox = false;
          } else if (this.apChange.apChangeType === '3') {
            this.resetIntHolStaffGBChild();
            this.isUseStaffBox = false;
          }
          this.deleteAutoAddAstLic();
        }
        break;
      // 資産管理担当者GroupBox
      case 'staffBox':
        this.isStaffBox = !this.isStaffBox;
        if (this.apChange.apChangeTypeThinClFlag === '1' || this.apChange.apChangeTypeTakePcFlag === '1') {
          if (this.isStaffBox) {
            this.setStaffInfoDefultValue('staff');
          } else {
            this.resetHolStaffGBChild();
            this.resetStaffInfoValue('staff');
          }
        }
        break;
      // 設置(使用)場所GroupBox
      case 'officeBox':
        this.isOfficeBox = !this.isOfficeBox;
        if (this.apChange.apChangeTypeThinClFlag === '1' || this.apChange.apChangeTypeTakePcFlag === '1') {
          if (this.isOfficeBox) {
            this.setStaffInfoDefultValue('office');
          } else {
            this.resetHolOfficeGBChild();
            this.resetStaffInfoValue('office');
          }
        }
        break;
      // 使用者GroupBox
      case 'useStaffBox':
        this.isUseStaffBox = !this.isUseStaffBox;
        if (!this.isUseStaffBox) {
          this.resetUseStaffGBChild();
        }
        break;
      // 無形管理担当者GroupBox
      case 'intHolStaffBox':
        this.isIntHolStaffBox = !this.isIntHolStaffBox;
        if (!this.isIntHolStaffBox) {
          this.resetIntHolStaffGBChild();
        }
        break;
      // 代表設置場所GroupBox
      case 'repOfficeBox':
        this.isRepOfficeBox = !this.isRepOfficeBox;
        if (!this.isRepOfficeBox) {
          this.resetHolRepOfficeGBChild();
        }
        break;
      // 販売管理費/原価区分GroupBox
      case 'costBox':
        this.isCostBox = !this.isCostBox;
        if (this.isCostBox) {
          this.setStaffInfoDefultValue('cost');
          // DONT this.searchCostApproveStaff('cost');		// 経費負担部課長検索
          // DONT this.searchHolApproveStaff('cost');		// 保有部署長検索
        } else {
          this.resetCostGBChild();
          this.resetStaffInfoValue('cost');
        }
        break;
      // 経費負担部署GroupBox
      case 'costSecBox':
        this.isCostSecBox = !this.isCostSecBox;
        if (this.isCostSecBox) {
          this.setStaffInfoDefultValue('costSec');
          // DONT this.searchCostApproveStaff('costSec');		// 経費負担部課長検索
          // DONT this.searchHolApproveStaff('costSec');		// 保有部署長検索
        } else {
          this.resetCostSecGBChild();
          this.resetStaffInfoValue('costSec');
        }
        break;
      case 'costSec':
        this.isCostSecOld = !this.isCostSecOld;
        break;
    }
  }

  /**
   * 遷移元画面へ戻る
   */
  goBack() {
    if (this.updateFlag === this.modeUpdateFlag.UPDATE_FLAG_UPDATE) {
      const sc = new ApChangeSC();
      sc.applicationIdPlural = this.apChangeForm.controls.applicationId.value;
      this.apchangeService.searchApChange(this.staffCode, this.accessLevel, sc).subscribe((resp: ApChangeSR[]) => {
        this.parent.changeChild(this.parent.viewIndexSearch, { action: 'backFromLine', params: resp });
      });
    } else if (this.updateFlag === this.modeUpdateFlag.UPDATE_FLAG_DELETE) { // -----------------------------------
      // this.parent.changeChild(this.parent.viewIndexSearch, { action: 'backFromLine', params: {} });
    } else {
      this.parent.changeChild(this.parent.viewIndexSearch, { action: 'backFromLine', params: {} });
    }
  }

  /**
   * 保有部署GroupBoxの子コンポーネントの値リセット
   */
  resetHolCompanyGBChild() {
    this.sectionBox.controls.holCompanyCode.setValue('');
    this.sectionBox.controls.holCompanyName.setValue('');
    this.sectionBox.controls.holSectionCode.setValue('');
    this.sectionBox.controls.holSectionName.setValue('');
    this.sectionBox.controls.holSectionYear.setValue('');
    this.sectionBox.controls.holSectionWithFlag.setValue(false);
  }

  /**
   * 資産管理担当者GroupBoxの子コンポーネントの値リセット
   */
  resetHolStaffGBChild() {
    this.staffBox.controls.holStaffCode.setValue('');
    this.staffBox.controls.holStaffName.setValue('');
  }

  /**
   * 設置(使用)場所GroupBoxの子コンポーネントの値リセット
   */
  resetHolOfficeGBChild() {
    this.officeBox.controls.holOfficeCode.setValue('');
    this.officeBox.controls.holOfficeName.setValue('');
    this.officeBox.controls.holOfficeFloor.setValue('');
    this.officeBox.controls.holOfficeRackNum.setValue('');
    this.officeBox.controls.holOfficeRoomNum.setValue('');
  }

  /**
   * 使用者GroupBoxの子コンポーネントの値リセット
   */
  resetUseStaffGBChild() {
    this.useStaffBox.controls.useStaffCode.setValue('');
    this.useStaffBox.controls.useStaffName.setValue('');
    this.useStaffBox.controls.useStaffCompanyCode.setValue('');
    this.useStaffBox.controls.useStaffCompanyName.setValue('');
    this.useStaffBox.controls.useStaffSectionCode.setValue('');
    this.useStaffBox.controls.useStaffSectionName.setValue('');
    this.useStaffBox.controls.useStaffSectionYear.setValue('');
  }

  /**
   * 無形管理担当者GroupBoxの子コンポーネントの値リセット
   */
  resetIntHolStaffGBChild() {
    this.intHolStaffBox.controls.intHolStaffCode.setValue('');
    this.intHolStaffBox.controls.intHolStaffName.setValue('');
    this.intHolStaffBox.controls.intHolStaffCompanyCode.setValue('');
    this.intHolStaffBox.controls.intHolStaffCompanyName.setValue('');
    this.intHolStaffBox.controls.intHolStaffSectionCode.setValue('');
    this.intHolStaffBox.controls.intHolStaffSectionName.setValue('');
    this.intHolStaffBox.controls.intHolStaffSectionYear.setValue('');
  }

  /**
   * 販売管理費/原価区分GroupBoxの子コンポーネントの値リセット
   */
  resetCostGBChild() {
    if (this.costBox.controls.costType.value != null) {
      this.costBox.controls.costType.setValue(1);
      this.costBox.controls.costDepPrjCode.setValue('');
      this.costBox.controls.costDepPrjName.setValue('');
      this.costBox.controls.costDepPrjType.setValue('');
    }
  }

  /**
   * 経費負担部署GroupBoxの子コンポーネントの値リセット
   */
  resetCostSecGBChild() {
    // 1.5次 Start
    this.costSecBox.controls.chgSchedulePeriod.setValue('');
    this.costSecBox.controls.chgSchedulePeriodName.setValue('');
    // 1.5次 End
    this.costSecBox.controls.costCompanyCode.setValue('');
    this.costSecBox.controls.costCompanyName.setValue('');
    this.costSecBox.controls.costSectionCode.setValue('');
    this.costSecBox.controls.costSectionYear.setValue(null);
    this.costSecBox.controls.costSectionName.setValue('');
    this.lineCostSecListComponent.apChangeLineCostSecList = new Array();
    // DONT apChangeLineCostSecLCB.changeDisplay(1);
  }

  /**
   * 代表設置場所GroupBoxの子コンポーネントの値リセット
   */
  resetHolRepOfficeGBChild() {
    this.repOfficeBox.controls.holRepOfficeCode.setValue('');
    this.repOfficeBox.controls.holRepOfficeName.setValue('');
  }

  /**
   * 担当部承認者-値リセット
   */
  resetStaffInfoValue(id) {
    switch (id) {
      case 'section':
      case 'staff':
      case 'office':
        if (this.isCostBox && !this.isCostSecBox) {
          this.staffInfo.controls.apprHolStaffCodeNew.setValue('');
          this.staffInfo.controls.apprHolStaffNameNew.setValue('');
          this.staffInfo.controls.apprCostStaffCodeNew.setValue('');
          this.staffInfo.controls.apprCostStaffNameNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffCodeNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffNameNew.setValue('');
        } else if (!this.isCostBox && !this.isCostSecBox) {
          // 担当部承認者-資産管理
          if (this.accessLevel === 'C' && this.apChange.apChangeType !== '3' && this.apChange.apChangeType !== 'C') {
            this.staffInfo.controls.apprHolStaffCodeOld.setValue('');
            this.staffInfo.controls.apprHolStaffNameOld.setValue('');
          }
          // 担当部承認者-資産管理
          this.staffInfo.controls.apprHolStaffCodeOld.setValue('');
          this.staffInfo.controls.apprHolStaffNameOld.setValue('');
          this.staffInfo.controls.apprHolStaffCodeNew.setValue('');
          this.staffInfo.controls.apprHolStaffNameNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffCodeOld.setValue('');
          this.staffInfo.controls.apprSuperiorStaffNameOld.setValue('');
          this.staffInfo.controls.apprSuperiorStaffCodeNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffNameNew.setValue('');
          this.staffInfo.controls.apprCostStaffCodeOld.setValue('');
          this.staffInfo.controls.apprCostStaffNameOld.setValue('');
          this.staffInfo.controls.apprCostStaffCodeNew.setValue('');
          this.staffInfo.controls.apprCostStaffNameNew.setValue('');
        }
        break;
      case 'cost':
        if (this.isSectionBox && !this.isCostSecBox) {
          // 経費負担部課長/GL(移動元)
          this.staffInfo.controls.apprCostStaffCodeOld.setValue('');
          this.staffInfo.controls.apprCostStaffNameOld.setValue('');
          // 経費負担部課長/GL(移動元)
          this.staffInfo.controls.apprCostStaffCodeNew.setValue('');
          this.staffInfo.controls.apprCostStaffNameNew.setValue('');
        } else if (!this.isSectionBox && !this.isCostSecBox) {
          // 担当部承認者-資産管理
          if (this.accessLevel === 'C' && this.apChange.apChangeType !== '3' && this.apChange.apChangeType !== 'C') {
            this.staffInfo.controls.apprHolStaffCodeOld.setValue('');
            this.staffInfo.controls.apprHolStaffNameOld.setValue('');
          }
          // 担当部承認者-資産管理
          this.staffInfo.controls.apprHolStaffCodeNew.setValue('');
          this.staffInfo.controls.apprHolStaffNameNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffCodeOld.setValue('');
          this.staffInfo.controls.apprSuperiorStaffNameOld.setValue('');
          this.staffInfo.controls.apprSuperiorStaffCodeNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffNameNew.setValue('');
          this.staffInfo.controls.apprCostStaffCodeOld.setValue('');
          this.staffInfo.controls.apprCostStaffNameOld.setValue('');
          this.staffInfo.controls.apprCostStaffCodeNew.setValue('');
          this.staffInfo.controls.apprCostStaffNameNew.setValue('');
        }
        break;
      case 'costSec':
        if (!this.isCostBox && this.isSectionBox) {
          // 経費負担部課長/GL(移動元)
          this.staffInfo.controls.apprCostStaffCodeOld.setValue('');
          this.staffInfo.controls.apprCostStaffNameOld.setValue('');
          // 経費負担部課長/GL(移動先)
          this.staffInfo.controls.apprCostStaffCodeNew.setValue('');
          this.staffInfo.controls.apprCostStaffNameNew.setValue('');
        } else if (this.isCostBox && !this.isSectionBox) {
          this.staffInfo.controls.apprHolStaffCodeNew.setValue('');
          this.staffInfo.controls.apprHolStaffNameNew.setValue('');
          this.staffInfo.controls.apprCostStaffCodeNew.setValue('');
          this.staffInfo.controls.apprCostStaffNameNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffCodeNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffNameNew.setValue('');
        } else if (!this.isCostBox && !this.isSectionBox) {
          // 担当部承認者-資産管理
          if (this.accessLevel === 'C' && this.apChange.apChangeType !== '3' && this.apChange.apChangeType !== 'C') {
            this.staffInfo.controls.apprHolStaffCodeOld.setValue('');
            this.staffInfo.controls.apprHolStaffNameOld.setValue('');
          }
          // 担当部承認者-資産管理
          this.staffInfo.controls.apprHolStaffCodeOld.setValue('');
          this.staffInfo.controls.apprHolStaffNameOld.setValue('');
          this.staffInfo.controls.apprHolStaffCodeNew.setValue('');
          this.staffInfo.controls.apprHolStaffNameNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffCodeOld.setValue('');
          this.staffInfo.controls.apprSuperiorStaffNameOld.setValue('');
          this.staffInfo.controls.apprSuperiorStaffCodeNew.setValue('');
          this.staffInfo.controls.apprSuperiorStaffNameNew.setValue('');
          this.staffInfo.controls.apprCostStaffCodeOld.setValue('');
          this.staffInfo.controls.apprCostStaffNameOld.setValue('');
          this.staffInfo.controls.apprCostStaffCodeNew.setValue('');
          this.staffInfo.controls.apprCostStaffNameNew.setValue('');
        }
        break;
    }
  }

  /**
   * 担当部承認者-デフォルト値セット
   */
  setStaffInfoDefultValue(id) {
    switch (id) {
      case 'section':
      case 'staff':
      case 'office':
        // 担当部承認者-資産管理(移動元デフォルト値設定)
        if (this.accessLevel === 'C' && this.apChange.apChangeType !== '3' && this.apChange.apChangeType !== 'C') {
          this.getApprHolStaff('OLD');
        }
        break;

      case 'cost':
        // 担当部承認者-資産管理(移動元デフォルト値設定)
        if (this.accessLevel === 'C' && this.apChange.apChangeType !== '3' && this.apChange.apChangeType !== 'C') {
          this.getApprHolStaff('OLD');
        }
        break;

      case 'costSec':
        // 担当部承認者-資産管理(移動元デフォルト値設定)
        if (this.accessLevel === 'C' && this.apChange.apChangeType !== '3' && this.apChange.apChangeType !== 'C') {
          this.getApprHolStaff('OLD');
        }

        // 資産管理担当者変更なしの場合、担当部承認者(資産管理担当者デフォルトセット)
        /*
                if(!holStaff_Box.visible){
                  this.getApprHolStaff('NEW');
                }
        */
        break;

    }
  }

  /**
   * 担当部承認者情報の資産管理担当者名取得
   */
  getApprHolStaff(type) {
    if (type === 'OLD' && this.apChange.apprHolStaffNameOld !== '') {
      return;		// 既にセットありの場合セットする必要なし
    } else if (type === 'NEW' && this.apChange.apprHolStaffNameNew !== '') {
      return;		// 既にセットありの場合セットする必要なし
    }

    const astAC = this.apChangeLineAst;
    const licAC = this.apChangeLineLic;
    const i = 0;
    let newItem = new Object();
    let oldItem = new Object();
    let apprHolStaffCode = '';
    let apprHolStaffName = '';

    // 情報機器/リース・レンタル/有形
    if (this.apChange.apChangeType === '1' || this.apChange.apChangeType === 'A' || this.apChange.apChangeType === 'B') {
      astAC.map((item, i) => {
        if (i === 0) {
          oldItem = item;
          newItem = item;
          if (oldItem['holStaffCode'] !== null && oldItem['holStaffCode'] !== ''
            && newItem['holStaffCode'] !== null && newItem['holStaffCode'] !== '') {
            apprHolStaffName = newItem['holStaffName'];
            apprHolStaffCode = newItem['holStaffCode'];
          } else {
            newItem = item;
            if (oldItem['holStaffCode'] !== null && oldItem['holStaffCode'] !== ''
              && newItem['holStaffCode'] !== null && newItem['holStaffCode'] !== ''
              && oldItem['holStaffCode'] !== newItem['holStaffCode']) {
              apprHolStaffName = '';
              apprHolStaffCode = '';
              return;
            } else if (apprHolStaffName === ''
              && oldItem['holStaffCode'] !== null && oldItem['holStaffCode'] !== ''
              && newItem['holStaffCode'] !== null && newItem['holStaffCode'] !== '') {
              apprHolStaffName = newItem['holStaffName'];
              apprHolStaffCode = newItem['holStaffCode'];
            }
            oldItem = newItem;
          }
        }
      });
    }

    // ライセンス/リース・レンタル
    if (apprHolStaffCode === '' && (this.apChange.apChangeType === '2' || this.apChange.apChangeType === 'A')) {
      licAC.map((item, i) => {
        if (i === 0) {
          oldItem = item;
          newItem = item;
          if (oldItem['holStaffCode'] !== null && oldItem['holStaffCode'] !== ''
            && newItem['holStaffCode'] !== null && newItem['holStaffCode'] !== '') {
            apprHolStaffName = newItem['holStaffName'];
            apprHolStaffCode = newItem['holStaffCode'];
          }
        } else {
          newItem = item;
          if (oldItem['holStaffCode'] !== null && oldItem['holStaffCode'] !== ''
            && newItem['holStaffCode'] !== null && newItem['holStaffCode'] !== ''
            && oldItem['holStaffCode'] !== newItem['holStaffCode']) {
            apprHolStaffName = '';
            apprHolStaffCode = '';
            return;
          } else if (apprHolStaffName === ''
            && oldItem['holStaffCode'] !== null && oldItem['holStaffCode'] !== ''
            && newItem['holStaffCode'] !== null && newItem['holStaffCode'] !== '') {
            apprHolStaffName = newItem['holStaffName'];
            apprHolStaffCode = newItem['holStaffCode'];
          }
          oldItem = newItem;
        }
      });
    }

    if (type === 'OLD') {
      this.staffInfo.controls.apprHolStaffCodeOld.setValue(apprHolStaffCode);
      this.staffInfo.controls.apprHolStaffNameOld.setValue(apprHolStaffName);
    } else {
      this.staffInfo.controls.apprHolStaffCodeNew.setValue(apprHolStaffCode);
      this.staffInfo.controls.apprHolStaffNameNew.setValue(apprHolStaffName);
    }
  }

  /**
   * 部長検索実行
   */
  searchHolApproveStaff(id: string, searchHolSectionCode: string = '') {
    if (searchHolSectionCode === null) {
      searchHolSectionCode = '';
    }
    if (searchHolSectionCode === '') {
      switch (id) {
        // 保有部署変更指定
        case 'holSectionChange_LNK':
          let item = new Object();
          if (this.apChange.apChangeType === '3' && this.apChangeLineFldInt.length > 0) {
            item = this.apChangeLineFldInt[0];
            searchHolSectionCode = item['holSectionCode'];
          } else if (this.apChangeLineAst.length > 0) {
            item = this.apChangeLineAst[0];
            searchHolSectionCode = item['holSectionCode'];
          } else if (this.apChangeLineLic.length > 0) {
            item = this.apChangeLineLic[0];
            searchHolSectionCode = item['holSectionCode'];
          }
          break;

        // 保有部署選択
        case 'holSectionName_LOV':
          searchHolSectionCode = this.sectionBox.controls.holSectionCode.value;
          break;

        // 経費情報(販管原価・経費部課)変更指定
        case 'costChange_LNK':
        case 'costSecChange_LNK':
          searchHolSectionCode = this.apStaffForm.controls.apSectionCode.value; // 申請部署
          break;
      }
    }
    this.valueChangeId = id;
    if (searchHolSectionCode !== '') {
      this.masterService.searchHolApproveStaff(this.sessionInfo.loginCompanyCode, searchHolSectionCode);
    }
  }

  /**
   * 自動追加された機器/ライセンス明細を削除する
   */
  deleteAutoAddAstLic() {
    // 保有部署・販管原価区分・経費負担部課の変更項目がすべて閉じられた場合
    // 自動追加機器・ライセンスを削除する。
    if (!this.isSectionBox) {
      let astAc = this.apChangeLineAst;
      astAc = astAc.filter(item => {
        if (item.autoAddFlag !== '1') {
          return item;
        }
      });
      let licAc = this.apChangeLineLic;
      licAc = licAc.filter(item => {
        if (item.autoAddFlag !== '1') {
          return item;
        }
      });
    }
  }
}
