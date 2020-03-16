import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import * as wjCore from 'wijmo/wijmo';
import * as wjGrid from 'wijmo/wijmo.grid';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { AdminComponent } from '../admin.component';
import { ProfileRegisterComponent } from './profile-register/profile-register.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';

import { SessionInfo } from 'src/app/models/session-info';
import { RoleSection } from 'src/app/models/api/role-section';
import { SystemMessage } from 'src/app/const/system-message';
import { User } from 'src/app/models/api/user';

/**
 * 資産管理担当者プロフィールコンポネント
 */

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent extends AbstractChildComponent<AdminComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * 社員検索フォーム
   */
  staffForm: FormGroup;

  /**
   * 部署検索フォーム
   */
  sectionForm: FormGroup;

  /**
   * 社員一覧情報
   */
  staffList: RoleSection[];

  /**
   * 選択中プロフィール
   */
  selectedProfile: RoleSection;

  /**
   * プロフィール登録表示か判定用
   */
  isProfileRegisterShown = false;

  /**
   * グリッド Ref
   */
  @ViewChild('gridAdminProfile', null) gridAdminProfile: EaFlexGrid;

  /**
   * プロフィール登録コンポネント Ref
   */
  @ViewChild(ProfileRegisterComponent, null) profileRegister: ProfileRegisterComponent;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService,
    private messageService: MessageService,
    private masterService: MasterService
  ) {
    super();
    this.fb = fb;
    this.searchForm = this.fb.group({
      companyCode: ['']
    });
    this.staffForm = this.fb.group({
      staffCode: [''],
      staffName: ['']
    });
    this.sectionForm = this.fb.group({
      sectionCode: [''],
      sectionName: [''],
      sectionYear: ['']
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
    this.searchForm.controls.companyCode.setValue(this.sessionInfo.loginCompanyName);
  }

  /**
   * 部署選択後処理
   * @param data 部署情報
   */
  selectSection(data: RoleSection) {
  }

  /**
   * 社員選択後処理
   * @param data 社員データ
   */
  selectStaff(data: User) {
    console.log(this.staffForm.controls.staffCode.value);
  }

  /**
   * 検索処理
   */
  search() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const sectionCode: string = this.sectionForm.controls.sectionCode.value ? this.sectionForm.controls.sectionCode.value : '';
    const staffCode: string = this.staffForm.controls.staffCode.value ? this.staffForm.controls.staffCode.value : '';
    this.masterService.searchSectionRoleProfile(loginStaffCode, accessLevel, companyCode, sectionCode, staffCode)
    .subscribe(
      (resp: RoleSection[]) => {
        if (!resp.length) {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
        this.staffList = resp;
        this.profileRegister.close();
        this.gridAdminProfile.resetSelection();
      }
    );
  }

  /**
   * プロフィール選択後処理
   * @param s Flexグリッド
   * @param e 情報
   */
  onProfileSelect(s: wjGrid.FlexGrid, e: object) {

    if (wjGrid.CellType[s.hitTest(e).cellType]  !== 'Cell') {
      return;
    }
    this.selectedProfile = this.gridAdminProfile.selectedRows[0].dataItem;
    this.showProfileRegister();

  }

  /**
   * プロフィール更新後処理
   * @param data プロフィール情報
   */
  onProfileUpdate(data: RoleSection) {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const roleSection: RoleSection = {
      companyCode: this.selectedProfile.companyCode,
      sectionYear: this.selectedProfile.sectionYear,
      sectionCode: this.selectedProfile.sectionCode,
      sectionName: this.selectedProfile.sectionName,
      staffCode: this.selectedProfile.staffCode,
      staffName: this.selectedProfile.staffName,
      publicFlag: data.publicFlag,
      publicComment: data.publicComment
    };
    this.masterService.updateSectionRoleProfile(loginStaffCode, accessLevel, roleSection)
    .subscribe(
      () => {
        this.messageService.info(SystemMessage.Info.msg100003);
        this.isProfileRegisterShown = false;
        this.profileRegister.close();
        this.search();
      }
    );
  }

  /**
   * プロフィール登録を表示する
   */
  showProfileRegister() {
    this.profileRegister.open();
    this.isProfileRegisterShown = true;
  }

  /**
   * プロフィール登録を非表示する
   */
  removeProfileRegister(data: boolean) {
    if (data) {
      this.isProfileRegisterShown = false;
      if (this.gridAdminProfile && this.gridAdminProfile.collectionView) {
        this.gridAdminProfile.collectionView.refresh();
      }
    }
  }

}
