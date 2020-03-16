import { Component, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { WjTreeView } from 'wijmo/wijmo.angular2.nav';

import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';

import { SessionInfo } from 'src/app/models/session-info';
import { SectionNode } from 'src/app/models/api/section-node';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';

/**
 * 部署選択コンポネント
 */
@Component({
  selector: 'app-section-selection',
  templateUrl: './section-selection.component.html',
  styleUrls: ['./section-selection.component.scss']
})
export class SectionSelectionComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * ツリーフォーム
   */
  treeForm: FormGroup;

  /**
   * ツリーデータ
   */
  dataList: SectionNode[];

  /**
   * 選択中部署
   */
  selectedItem: WjTreeView['selectedItem'];

  /**
   * 部署フォーム
   */
  @Input() sectionForm: FormGroup;

  /**
   * 部署コードコントロール名
   */
  @Input() sectionCodeControlName: string;

  /**
   * 部署名コントロール名
   */
  @Input() sectionNameControlName: string;

  /**
   * 部署年度コントロール名
   */
  @Input() sectionYearControlName: string;

  /**
   * クラスを拡張する
   */
  @Input() title = '';

  /**
   * 題名スタイル
   */
  @Input() styleTitle = '';


  /**
   * 必須か判定用
   */
  @Input() required: boolean;

  /**
   * 選択のみ
   */
  @Input() selectOnly: boolean;

  /**
   * 年度
   */
  @Input() year: number;

  /**
   * クラスを拡張する
   */
  @Input() extClass = '';

  /**
   * 状態 -1:編集ロック、0:状態設定なし、1～：各エンティティの項目定義に従いセット
   */
  @Input() visibleStatus = true;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('treePopup', null) treePopup: EaPopup;

  /**
   * ツリー Ref
   */
  @ViewChild('treeView', null) treeView: WjTreeView;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
  ) {
    this.fb = fb;
    this.treeForm = this.fb.group({
      filterText: ['']
    });
    this.sessionInfo = this.sessionService.getSessionInfo();
  }

  /**
   * 初期処理
   */
  init() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const sectionName: string = this.treeForm.controls.filterText.value;

    const listToTree = (list: SectionNode[]): SectionNode[] => {
      const map = {};
      let node: SectionNode;
      const roots: SectionNode[] = [];
      let i: number;

      for (i = 0; i < list.length; i += 1) {
        map[list[i].sectionCode] = i;
        list[i].items = [];
      }

      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (node.parentSectionCode !== '0') {
          if (list[map[node.parentSectionCode]]) {
            list[map[node.parentSectionCode]].items.push(node);
          } else {
            roots.push(node);
          }
        } else {
          roots.push(node);
        }
      }

      return roots;
    };

    this.masterService.searchSection(loginStaffCode, accessLevel, companyCode, sectionName, this.year)
      .subscribe(
        (data: SectionNode[]) => {
          this.dataList = listToTree(data);
          setTimeout(() => {
            if (sectionName) {
              this.treeView.collapseToLevel(999);
            } else {
              this.treeView.collapseToLevel(1);
            }

            const divs = this.treeView.hostElement.getElementsByTagName('div');
            for (let i = 0; i < divs.length; i++) {
              const div = divs.item(i);
              if (div.getAttribute('role') === 'treeitem') {
                div.tabIndex = 0;
                break;
              }
            }

          }, 100);
        }
      );
  }

  /**
   * ツリー初期処理
   */
  onTreeInitialized() {
    this.treeView.collapseToLevel(1);
  }

  /**
   * ツリーをクリア
   */
  clear() {
    this.selectedItem = {};
    this.sectionForm.controls[this.sectionCodeControlName].setValue(null);
    this.sectionForm.controls[this.sectionNameControlName].setValue(null);
    this.sectionForm.controls[this.sectionYearControlName].setValue(null);
    this.select.emit(null);
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.treeView.selectedItem) {
      this.selectedItem = this.treeView.selectedItem;
      this.sectionForm.controls[this.sectionCodeControlName].setValue(this.treeView.selectedItem.sectionCode);
      this.sectionForm.controls[this.sectionNameControlName].setValue(this.treeView.selectedItem.sectionShortName);
      this.sectionForm.controls[this.sectionYearControlName].setValue(this.treeView.selectedItem.sectionYear);
      this.select.emit(this.selectedItem);
      this.close();
    }
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.init();
    this.treePopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    this.treePopup.hide();
    this.dataList = [];
    // Reset treeForm
    this.treeForm = this.fb.group({
      filterText: ['']
    });
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
  }
}
