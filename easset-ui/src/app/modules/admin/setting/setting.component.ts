import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { AdminComponent } from '../admin.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { PrivilegeSelectionComponent } from 'src/app/parts/lov/privilege-selection/privilege-selection.component';
import { AddPrivilegesComponent } from 'src/app/modules/admin/setting/add-privileges/add-privileges.component';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';

import { SessionInfo } from 'src/app/models/session-info';
import { User } from 'src/app/models/api/user';
import { RoleAdmin } from 'src/app/models/api/role-admin';
import { RoleSection } from 'src/app/models/api/role-section';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { SystemMessage } from 'src/app/const/system-message';


/**
 * 管理者設定コンポネント
 */

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.scss']
})
export class SettingComponent extends AbstractChildComponent<AdminComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 権限フォーム
   */
  privilegeForm: FormGroup;

  /**
   * 社員検索フォーム
   */
  staffForm: FormGroup;

  /**
   * 社員検索フォーム
   */
  sectionForm: FormGroup;

  /**
   * 管理者一覧情報
   */
  adminList: RoleAdmin[];

  /**
   * 一般ユーザー一覧情報
   */
  userList: RoleSection[];

  /**
   * 権限追加ボタン表示判定用
   */
  isAddPrivilegesButtonShown = true;

  /**
   * 選択中権限
   */
  selectedRole: string;

  /**
   * 選択中コード
   */
  selectedRoleCode: string;

  /**
   * 選択中部署年度
   */
  selectedSectionYear: string;

  /**
   * 選択中部署コード
   */
  selectedSectionCode: string;

  /**
   * 選択中部署名
   */
  selectedSectionName: string;

  /**
   * 選択中社員コード
   */
  selectedStaffCode: string;

  /**
   * 権限選択ポップアップ Ref
   */
  @ViewChild(PrivilegeSelectionComponent, null) selectPrivilegePopup: PrivilegeSelectionComponent;

  /**
   * 確認ポップアップ Ref
   */
  @ViewChild('confirmPopupDelete', null) confirmPopupDelete: ConfirmPopupComponent;

  /**
   * 権限追加ポップアップ Ref
   */
  @ViewChild(AddPrivilegesComponent, null) addPrivileges: AddPrivilegesComponent;

  /**
   * ADMIN Flexグリッド Ref
   */
  @ViewChild('gridAdminList', null) gridAdminList: EaFlexGrid;

  /**
   * その他 Flexグリッド Ref
   */
  @ViewChild('gridUserList', null) gridUserList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private sessionService: SessionService,
    private messageService: MessageService,
    private masterService: MasterService,
    private fileDownloadService: FileDownloadService
  ) {
    super();
    this.fb = fb;
    this.staffForm = this.fb.group({
      staffCode: [''],
      staffName: ['']
    });
    this.sectionForm = this.fb.group({
      sectionYear: [''],
      sectionCode: [''],
      sectionName: [''],
    });
    this.privilegeForm = this.fb.group({
      company: [''],
      staffCode: [''],
      staffName: [''],
      privilegeName: [''],
      privilegeCode: ['']
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
    this.privilegeForm.controls.company.setValue(this.sessionInfo.loginCompanyName);
  }

  /**
   * 権限追加ポップアップを開く
   */
  showAddPrivileges() {
    this.isAddPrivilegesButtonShown = false;
    this.addPrivileges.updateSectionForm(this.sectionForm.value);
    this.addPrivileges.open();
  }

  /**
   * 権限追加ポップアップを開く閉じる
   * @param showButton 権限追加ボタンを表示するか判定用値
   */
  removeAddPrivileges(showButton: boolean) {
    if (showButton) {
      this.isAddPrivilegesButtonShown = true;
    }
  }

  /**
   * 権限選択後処理
   * @param data 選択された権限情報
   */
  onPrivilegeSelected(data: LovDataEx) {
    if (data) {
      this.clearSearch();
      this.privilegeForm.controls.privilegeName.setValue(data.name);
      this.privilegeForm.controls.privilegeCode.setValue(data.code);
      if (data.code === 'B01') {
        this.selectedRole = 'user';
      } else {
        this.selectedRole = 'admin';
        this.search();
      }
    }
  }

  /**
   * 部署選択後処理
   * @param data 部署情報
   */
  selectSection(data: RoleSection) {
  }

  /**
   * 社員選択後処理
   * @param data ユーザー情報
   */
  selectStaff(data: User) {
  }

  /**
   * 検索処理
   */
  search() {
    if (this.selectedRole === 'admin') {

      const companyCode: string = this.sessionInfo.loginCompanyCode;
      const roleCode: string = this.privilegeForm.controls.privilegeCode.value;
      const staffCode: string = this.staffForm.controls.staffCode.value;
      this.selectedRoleCode = roleCode;

      this.masterService.searchRoleAdmin(companyCode, roleCode, staffCode)
        .subscribe(
          (resp: RoleAdmin[]) => {

            if (!resp.length) {
              this.messageService.warn(SystemMessage.Warn.msg200002);
            }

            this.adminList = resp;
            if (this.gridAdminList) {
              this.gridAdminList.resetSelection();
            }
          }
        );

    } else {

      const companyCode: string = this.sessionInfo.loginCompanyCode;
      const sectionCode: string = this.sectionForm.controls.sectionCode.value;
      const staffCode: string = this.staffForm.controls.staffCode.value;

      this.masterService.searchRoleSection(companyCode, sectionCode, staffCode)
        .subscribe(
          (resp: RoleSection[]) => {

            if (!resp.length) {
              this.messageService.warn(SystemMessage.Warn.msg200002);
            }

            this.userList = resp;
            if (this.gridUserList) {
              this.gridUserList.resetSelection();
            }
          }
        );

    }
  }

  /**
   * 検索条件をクリアする
   */
  clearSearch() {
    this.adminList = [];
    this.userList = [];
    this.selectedRole = null;
    this.selectedRoleCode = null;
    this.selectedSectionYear = null;
    this.selectedSectionCode = null;
    this.selectedSectionName = null;
    this.selectedStaffCode = null;
    this.staffForm = this.fb.group({
      staffCode: [''],
      staffName: ['']
    });
    this.sectionForm = this.fb.group({
      sectionYear: [''],
      sectionCode: [''],
      sectionName: [''],
    });
    this.addPrivileges.reset();
    this.addPrivileges.close();
  }

  /**
   * CSVダウンロード
   */
  getCSV() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const sectionCode: string = this.sectionForm.controls.sectionCode.value;
    const staffCode: string = this.staffForm.controls.staffCode.value;
    this.masterService.createRoleSectionCsv(loginStaffCode, companyCode, sectionCode, staffCode)
      .subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          const fileName = '資産管理担当者検索結果_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          this.fileDownloadService.download(fileId, fileName, contentType);
        }
      );
  }

  /**
   * 権限追加後処理
   * @param data　追加された権限
   */
  onPrivilegeAdded(data) {

    if (this.selectedRole === 'admin') {
      const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
      const companyCode: string = this.sessionInfo.loginCompanyCode;
      const roleAdmin: RoleAdmin = {
        companyCode,
        staffCode: data.staff.staffCode,
        staffName: data.staff.staffName,
        roleCode: this.privilegeForm.controls.privilegeCode.value,
        createStaffCode: loginStaffCode,
        createDate: new Date(),
        updateStaffCode: loginStaffCode,
        updateDate: new Date()
      };
      this.masterService.createRoleAdmin(loginStaffCode, companyCode, roleAdmin)
        .subscribe(
          () => {
            this.messageService.info(SystemMessage.Info.msg100012);
            this.isAddPrivilegesButtonShown = true;
            this.addPrivileges.close();
          }
        );
    } else {
      const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
      const companyCode: string = this.sessionInfo.loginCompanyCode;
      const roleSection: RoleSection = {
        companyCode,
        sectionYear: data.section.sectionYear,
        sectionCode: data.section.sectionCode,
        sectionName: data.section.sectionName,
        staffCode: data.staff.staffCode,
        staffName: data.staff.staffName,
        publicFlag: data.form.publicFlag,
        publicComment: data.form.publicComment,
        createStaffCode: loginStaffCode,
        createDate: new Date(),
        updateStaffCode: loginStaffCode,
        updateDate: new Date()
      };
      this.masterService.createRoleSection(loginStaffCode, companyCode, roleSection)
        .subscribe(
          () => {
            this.messageService.info(SystemMessage.Info.msg100012);
            this.isAddPrivilegesButtonShown = true;
            this.addPrivileges.close();
          }
        );
    }

  }

  /**
   * 権限削除後処理
   * @param confirm 確認ポップアップ表示か判定用
   */
  removePrivileges(confirm?: boolean) {

    if (!confirm) {
      this.confirmPopupDelete.prompt(SystemMessage.Info.msg100011, document.activeElement);
    } else {
      if (this.selectedRole === 'admin') {
        const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
        const companyCode: string = this.sessionInfo.loginCompanyCode;
        const roleCode: string = this.selectedRoleCode;
        const staffCode: string = this.gridAdminList.selectedRows[0].dataItem.staffCode;
        this.masterService.deleteRoleAdmin(loginStaffCode, companyCode, roleCode, staffCode)
          .subscribe(
            () => {
              this.messageService.info(SystemMessage.Info.msg100010);
              this.gridAdminList.collectionView.items.splice(this.gridAdminList.selectedRows[0].index, 1);
              this.gridAdminList.collectionView.refresh();
              this.gridAdminList.resetSelection();
            }
          );
      } else {
        const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
        const companyCode: string = this.sessionInfo.loginCompanyCode;
        const sectionCode: string = this.gridUserList.selectedRows[0].dataItem.sectionCode;
        const staffCode: string = this.gridUserList.selectedRows[0].dataItem.staffCode;
        this.masterService.deleteRoleSection(loginStaffCode, companyCode, sectionCode, staffCode)
          .subscribe(
            () => {
              this.messageService.info(SystemMessage.Info.msg100010);
              this.gridUserList.collectionView.items.splice(this.gridUserList.selectedRows[0].index, 1);
              this.gridUserList.collectionView.refresh();
              this.gridUserList.resetSelection();
            }
          );
      }
    }
  }

}
