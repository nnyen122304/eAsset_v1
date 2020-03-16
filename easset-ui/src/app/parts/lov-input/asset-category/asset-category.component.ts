import { Component, Input, Output, ViewChild, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { WjTreeView } from 'wijmo/wijmo.angular2.nav';

import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';

import { SessionInfo } from 'src/app/models/session-info';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { AssetCategoryNode } from 'src/app/models/api/asset-category-node';

@Component({
  selector: 'app-asset-category',
  templateUrl: './asset-category.component.html',
  styleUrls: ['./asset-category.component.scss']
})
export class AssetCategoryComponent implements OnInit {

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
  dataList: AssetCategoryNode[];

  categoryName2: string;
  /**
   * 選択中部署
   */
  selectedItem: WjTreeView['selectedItem'];

  /**
   * 部署フォーム
   */
  @Input() categoryForm: FormGroup;

  /**
   * 分類コードコントロール名
   */
  @Input() categoryCodeControlName: string;

  /**
   * 分類名コントロール名
   */
  @Input() categoryNameControlName: string;

  /**
   * 親分類コードコントロール名
   */
  @Input() categoryCodeControlParent: string;

  /**
   * 親分類名コントロール名
   */
  @Input() categoryNameControlParent: string;

  /**
   * 分類名検索キーワード
   */
  @Input() categoryName: string;

  /**
   * 変更前資産大分類
   */
  @Input() selectedAssetCategory1: string;

  /**
   * 必須か判定用
   */
  @Input() required: boolean;

  /**
   * 選択のみ
   */
  @Input() selectOnly: boolean;

  @Input() category2 = false;

  @Input() parentInputClass: string;

  @Input() inputClass: string;

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
      searchParamCategoryName: ['']
    });
    this.sessionInfo = this.sessionService.getSessionInfo();
  }

  /**
   * Init
   */
  ngOnInit(): void {
    if (this.categoryName === 'ASSET_CATEGORY2' && this.category2 === false) {
      const paramCategoryName = '';
      let text = '';
      this.masterService.searchAssetCategory(paramCategoryName, this.selectedAssetCategory1)
        .subscribe(
          (data: AssetCategoryNode[]) => {
            data.map(item => {
              if (item.categorySegment === 'ASSET_CATEGORY2') {
                text += item.categoryName + ' ';
                this.categoryName2 = text;
              }
            });
          });
    }
  }

  /**
   * 初期処理
   */
  init() {
    const paramCategoryName: string = this.treeForm.controls.searchParamCategoryName.value;

    const listToTree = (list: AssetCategoryNode[]): AssetCategoryNode[] => {
      const map = {};
      let node: AssetCategoryNode;
      const roots: AssetCategoryNode[] = [];
      let i: number;

      for (i = 0; i < list.length; i += 1) {
        map[list[i].categoryCode] = i;
        list[i].items = [];
      }

      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (node.parentCategoryCode !== '0') {
          if (list[map[node.parentCategoryCode]]) {
            list[map[node.parentCategoryCode]].items.push(node);
          } else {
            roots.push(node);
          }
        } else {
          roots.push(node);
        }
      }
      return roots;
    };

    this.masterService.searchAssetCategory(paramCategoryName, this.selectedAssetCategory1)
      .subscribe(
        (data: AssetCategoryNode[]) => {
          this.dataList = listToTree(data);
        }
      );
  }

  /**
   * ツリー初期処理
   */
  onTreeInitialized(treeView) {
    treeView.collapseToLevel(0);
  }

  /**
   * ツリーをクリア
   */
  clear() {
    this.selectedItem = {};
    this.categoryForm.controls[this.categoryNameControlParent].setValue(null);
    this.categoryForm.controls[this.categoryNameControlName].setValue(null);
    this.select.emit(null);
  }

  /**
   * 選択ボタンハンドラー
   */
  submit() {
    this.selectedItem = this.treeView.selectedItem;
    if (this.selectedItem != null) {
      if (this.categoryName !== 'ASSET_CATEGORY2' || this.categoryName === this.selectedItem.categorySegment) {
        if (this.selectedItem.categorySegment === 'ASSET_CATEGORY1') {
          this.categoryForm.controls[this.categoryCodeControlParent].setValue('');
          this.categoryForm.controls[this.categoryNameControlParent].setValue('');
          this.categoryForm.controls[this.categoryCodeControlParent].setValue(this.selectedItem.categoryCode);
          this.categoryForm.controls[this.categoryNameControlParent].setValue(this.selectedItem.categoryName);
        } else {
          this.categoryForm.controls[this.categoryCodeControlName].setValue(this.selectedItem.categoryCode);
          this.categoryForm.controls[this.categoryNameControlName].setValue(this.selectedItem.categoryName);
          this.categoryForm.controls[this.categoryCodeControlParent].setValue(this.selectedItem.parentCategoryCode);
          this.categoryForm.controls[this.categoryNameControlParent].setValue(this.selectedItem.parentCategoryName);
        }
        this.select.emit(this.selectedItem);
        this.close();
      }
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
   * 閉じるボタンハンドラー
   */
  close() {
    this.treePopup.hide();
    this.dataList = [];
    // vi Reset treeForm
    this.treeForm = this.fb.group({
      searchParamCategoryName: ['']
    });
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
  }
}
