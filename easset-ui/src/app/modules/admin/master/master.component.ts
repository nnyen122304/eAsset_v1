import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import * as wjGrid from 'wijmo/wijmo.grid';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { AdminComponent } from '../admin.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';

import { SessionInfo } from 'src/app/models/session-info';
import { CodeNameSet } from 'src/app/models/api/code-name-set';
import { CodeNameSetItem } from 'src/app/models/api/code-name-set-item';
import { CodeName } from 'src/app/models/api/code-name';
import { SystemMessage } from 'src/app/const/system-message';

/**
 * 汎用マスタ管理コンポネント
 */

@Component({
  selector: 'app-master',
  templateUrl: './master.component.html',
  styleUrls: ['./master.component.scss']
})
export class MasterComponent extends AbstractChildComponent<AdminComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * 表示切り替え用変数
   */
  view: string;

  /**
   * カテゴリ一覧情報
   */
  categoriesList: CodeNameSet[];

  /**
   * 選択済みか判定用
   */
  isSearched = false;

  @ViewChild('gridAdminMaster', null) gridAdminMaster: EaFlexGrid;

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
      companyCode: [''],
      categoryName: ['']
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
   * 検索処理
   */
  search() {
    this.isSearched = true;
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const categoryName: string = this.searchForm.controls.categoryName.value;
    const roleCodeList: string[] = this.sessionInfo.loginRoleCodeList;
    this.masterService.searchCodeNameSet(loginStaffCode, companyCode, categoryName, roleCodeList)
    .subscribe(
      (resp: CodeNameSet[]) => {
        if (!resp.length) {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
        this.categoriesList = resp;
        this.gridAdminMaster.resetSelection();
      }
    );
  }

  /**
   * カテゴリ選択後処理
   * @param s Flexグリッド
   * @param e 選択アイテム
   */
  onCategorySelect(s: wjGrid.FlexGrid, e: object) {

    if (wjGrid.CellType[s.hitTest(e).cellType]  === 'ColumnHeader') {
      return;
    }

    const data = this.gridAdminMaster.selectedRows[0].dataItem;

    this.parent.changeChild(this.parent.viewIndexMasterSetting, {
      action: 'initMasterSetting',
      params: {
        item: data
      }
    });

  }

}
