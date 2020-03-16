import { Component, Output, ViewChild, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';
import { MessageService } from 'src/app/services/message.service';

import { SessionInfo } from 'src/app/models/session-info';

import { SystemMessage } from 'src/app/const/system-message';
import { CodeName } from 'src/app/models/api/code-name';

@Component({
  selector: 'app-download-option-prop',
  templateUrl: './download-option-prop.component.html',
  styleUrls: ['./download-option-prop.component.scss']
})
export class DownloadOptionPropComponent {
  /**
   * 保存フォームの表示・非表示の変数
   */
  isSave = false;
  /**
   * 復元フォームの表示・非表示の変数
   */
  isRestore = false;
  /**
   * 履歴を保存する機能の表示・非表示の変数
   */
  isFormSave = true;
  /**
   * 復元する機能の表示・非表示の変数
   */
  isFormRestore = true;
  /**
   * ダウンロードオプションの表示・非表示の変数
   */
  isLineItemFI = true;
  /**
   * 明細選択
   */
    // tslint:disable-next-line: no-any
  lineItemAC: any[];
  /**
   * 情報機器等
   */
    // tslint:disable-next-line: no-any
  assetLineAC: any[];
  /**
   * 情報機器登録申請
   */
    // tslint:disable-next-line: no-any
  apAssetLineAC: any[];
  /**
   * 明細項目無し
   */
    // tslint:disable-next-line: no-any
  dummyLineAC: any[];
  /**
   * 帳票名
   */
  itemDefName: string;
  /**
   * 帳票名2
   */
  itemDefName2: string;
  /**
   * 帳票名3
   */
  itemDefName3: string;
  /**
   * 帳票名2カテゴリプレフィックス
   */
  itemDefName2CategoryPrefix: string;
  /**
   * 帳票名3カテゴリプレフィック
   */
  itemDefName3CategoryPrefix: string;
  /**
   * 除外プロパティマップ
   */
  excludePropertyMap: object;
  /**
   * 除外カテゴリマップ
   */
  excludeCategoryMap: object;
  /**
   * キー項目数（画面上は非表示）
   */
  keyItemCount = 1;
  /**
   * キー項目オブジェクト(画面上は非表示)
   */
  keyItemList: CodeName[];
  /**
   * キー項目プロパティマップ
   */
  keyItemPropMap: object;
  /**
   * DLチェック情報履歴保存
   */
  histDlChek;
  /**
   * ダウンロード履歴のリスト名
   */
  labelHistDlChek: string;
  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * ダウンロードリスト
   */
  downloadItemList;
  /**
   * 復元リスト
   */
  restoreList;
  /**
   * ダウンロードオプションのフォーム
   */
  lineItemFIForm: FormGroup;
  /**
   * 確認履歴を保存するフォーム
   */
  saveForm: FormGroup;

  /**
   * パラメータ
   */
  @Input() paramObj: any = {};

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();
  /**
   * ポップアップ Ref
   */
  @ViewChild('downloadSelectionPopup', null) downloadSelectionPopup: EaPopup;
  /**
   * グリッド Ref
   */
  @ViewChild('gridDownloadSelectionList', null) gridDownloadSelectionList: EaFlexGrid;
  /**
   * グリッド Ref
   */
  @ViewChild('gridRestoreList', null) gridRestoreList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private messageService: MessageService,
  ) {
    this.fb = fb;
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.lineItemFIForm = this.fb.group({
      selectType: [''],
    });
    this.saveForm = this.fb.group({
      saveName: ['', Validators.required],
    });
  }

  /**
   * 初期処理
   */
  init() {
    // 情報機器等
    this.assetLineAC = ([
      {data: '0', label: ''},
      {data: 'NEA_ASSET_LINE_COM_USR', label: '共有ユーザー明細'},
      {data: 'NEA_ASSET_LINE_OS', label: 'OS明細'},
      {data: 'NEA_ASSET_LINE_NETWORK', label: 'ネットワーク明細'},
      {data: 'NEA_ASSET_LINE_INV', label: '棚卸明細'}
    ]);

    // 情報機器登録申請
    this.apAssetLineAC = ([
      {data: '0', label: ''},
      {data: 'NEA_AP_ASSET_LINE_COM_USR', label: '共有ユーザー明細'},
      {data: 'NEA_AP_ASSET_LINE_OS', label: 'OS明細'},
      {data: 'NEA_AP_ASSET_LINE_NETWORK', label: 'ネットワーク明細'},
    ]);

    // 明細項目無し
    this.dummyLineAC = ([
      {data: '0', label: ''}
    ]);

    // ポップアップパラメータ取得
    this.itemDefName = this.paramObj.itemDefName ? this.paramObj.itemDefName : '';
    if (this.itemDefName === 'ITEM_DEF_PPFS_FLD' || this.itemDefName === 'ITEM_DEF_PPFS_LLD') {
      this.itemDefName3CategoryPrefix = this.paramObj.itemDefName3CategoryPrefix ? this.paramObj.itemDefName3CategoryPrefix : '';
      this.itemDefName2CategoryPrefix = this.paramObj.itemDefName2CategoryPrefix ? this.paramObj.itemDefName2CategoryPrefix : '';
      this.excludePropertyMap = this.paramObj.excludePropertyMap ? this.paramObj.excludePropertyMap : '';
      this.excludeCategoryMap = this.paramObj.excludeCategoryMap ? this.paramObj.excludeCategoryMap : '';
      this.keyItemCount = this.paramObj.keyItemCount !== 0 ? this.paramObj.keyItemCount : 1;
    }

    if (this.itemDefName.indexOf(' ') !== -1) {
      const itemDefs = this.itemDefName.split(' ');
      this.itemDefName = itemDefs[0];
      if (itemDefs.length === 2) {
        this.itemDefName2 = itemDefs[1];
        this.itemDefName3 = '';
      } else if (itemDefs.length === 3) {
        this.itemDefName2 = itemDefs[1];
        this.itemDefName3 = itemDefs[2];
      }
    } else {
      this.itemDefName2 = '';
      this.itemDefName3 = '';
    }

    let className = '';
    switch (this.itemDefName) {
      case 'ITEM_DEF_ASSET':
        this.lineItemAC = this.assetLineAC;
        this.isLineItemFI = true;
        className = 'AssetSearch';
        break;
      case 'ITEM_DEF_AP_ASSET':
        this.lineItemAC = this.apAssetLineAC;
        this.isLineItemFI = true;
        className = 'ApAssetSearch';
        break;
      case 'ITEM_DEF_LICENSE':
        this.lineItemAC = this.dummyLineAC;
        this.isLineItemFI = false;
        className = 'LicenseSearch';
        break;
      case 'ITEM_DEF_AP_LICENSE':
        this.lineItemAC = this.dummyLineAC;
        this.isLineItemFI = false;
        className = 'ApLicenseSearch';
        break;
      case 'ITEM_DEF_PPFS_FLD':
        this.lineItemAC = this.dummyLineAC;
        this.isLineItemFI = false;
        className = 'FldSearchReport';
        break;
      default:
        this.lineItemAC = this.dummyLineAC;
        this.isLineItemFI = false;
        className = 'LldSearchReport';
        break;
    }

    this.labelHistDlChek = 'DLO_' + this.sessionInfo.loginUser.staffCode + className;
    this.getDownloadItemList(this.itemDefName);
  }

  /**
   * Apiを呼び出してダウンロード項目のリストを取得する
   *
   * @param itemDefName:string  識別コード
   *
   * @return なし
   */
  getDownloadItemList(itemDefName) {
    this.masterService.getDownloadItemList(itemDefName, null).subscribe((req: CodeName[]) => {
      // 検索結果格納
      const responseList = req.map((item) => {
        item.sel = false;
        return item;
      });

      if (this.itemDefName === 'ITEM_DEF_PPFS_FLD' || this.itemDefName === 'ITEM_DEF_PPFS_LLD') {
        let item: CodeName;
        let i: number;

        if (responseList !== null && responseList.length > 0) {
          const resCategory = responseList[0].categoryCode;
          if (resCategory === this.itemDefName) {
            for (i = 0; i < this.keyItemCount; i++) {
              item = responseList[i];
              this.keyItemList.push(item);	          // キー項目抜き出し
              this.keyItemPropMap[item.value3] = '1'; // キーマップ保存
            }
          }

          // 表示除外
          for (i = 0; i < responseList.length; i++) {
            item = responseList[i];
            // 除外項目
            if (this.excludePropertyMap !== null) {
              if (this.excludePropertyMap.hasOwnProperty(item.value3)) {
                responseList.splice(i, 1);
                continue;
              }
            }

            // 除外カテゴリ
            if (this.excludeCategoryMap !== null) {
              if (this.excludeCategoryMap.hasOwnProperty(item.description)) {
                responseList.splice(i, 1);
                continue;
              }
            }
          }

          // 項目カテゴリを会社帳簿 ⇒ 帳簿に置き換え（どの帳簿かは検索画面で選択するため）
          if (resCategory === 'ITEM_DEF_PPFS_FLD') {
            for (i = 0; i < responseList.length; i++) {
              item = responseList[i];
              if (item.description !== null) {
                if (item.description.search('：会社帳簿' + '$') !== -1) {
                  item.description = item.description.substring(0, item.description.search('：会社帳簿' + '$'));
                  item.description += '：帳簿';
                }
              }
            }
          }

          // 項目カテゴリ付加
          if (resCategory === this.itemDefName2 && this.itemDefName2CategoryPrefix !== null) { // 3つめの項目定義の場合カテゴリに情報付加
            for (i = 0; i < responseList.length; i++) {
              item = responseList[i];
              if (item.description === null || item.description === '') {
                item.description = this.itemDefName2CategoryPrefix + '';
              } else {
                item.description = this.itemDefName2CategoryPrefix + '-' + item.description;
              }
            }
          }
          if (resCategory === this.itemDefName3 && this.itemDefName3CategoryPrefix !== null) { // 3つめの項目定義の場合カテゴリに情報付加
            for (i = 0; i < responseList.length; i++) {
              item = responseList[i];
              if (item.description == null || item.description === '') {
                item.description = this.itemDefName3CategoryPrefix + '';
              } else {
                item.description = this.itemDefName3CategoryPrefix + '-' + item.description;
              }
            }
          }

          // 固定資産帳簿フィルタ（会社帳簿のみ表示）
          if (resCategory === 'ITEM_DEF_PPFS_FLD') {
            // tslint:disable-next-line: no-shadowed-variable
            responseList.map((item) => {
              item.sel = true;
              if (item.description !== null && item.description !== '') { // 会社帳簿以外は表示しない
                if (item.description.search('：税法帳簿' + '$') !== -1
                  || item.description.search('：IFRS帳簿' + '$') !== -1
                  || item.description.search('：会社帳簿2' + '$') !== -1
                  || item.description.search('：税法帳簿2' + '$') !== -1
                  || item.description.search('：戻入帳簿' + '$') !== -1) {
                  item.sel = false;
                }
              }
              return item;
            });
          }

          // 項目定義が２つの場合は２つめを取得
          if (resCategory === this.itemDefName && this.itemDefName2 !== '') {
            this.getDownloadItemList(this.itemDefName2);
          }

          // 項目定義が３つの場合は３つめを取得
          if (resCategory === this.itemDefName2 && this.itemDefName3 !== '') {
            this.getDownloadItemList(this.itemDefName3);
          }
        }
      }
      this.downloadItemList = responseList;
      this.gridDownloadSelectionList.resetSelection();
    });
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.init();
    this.downloadSelectionPopup.show(true);
  }

  /**
   * クリックイベントを処理する
   * @param data:string
   * @return なし
   */
  clickEvent(data) {
    switch (data) {
      // 保存
      case 'openSaveBox':
        this.isSave = !this.isSave;
        this.isFormRestore = false;
        break;
      // 選択状態キャンセル
      case 'saveCancel':
        this.isSave = !this.isSave;
        this.isFormRestore = true;
        break;
      // 選択状態保存
      case 'saveOk':
        const saveName = this.saveForm.controls.saveName.value ? this.saveForm.controls.saveName.value : '';
        const checkList = this.gridDownloadSelectionList.getCheckedRows();
        let selectedIndex = '';
        if (this.isLineItemFI) {
          selectedIndex = this.lineItemFIForm.controls.selectType.value;
        }

        if (checkList.length === 0) {
          this.messageService.err(SystemMessage.Err.msg300001);
        } else {
          let histDLItem: object;
          const local = localStorage.getItem(this.labelHistDlChek);

          if (local !== null) {
            this.histDlChek = JSON.parse(local);
            histDLItem = Object.assign({
              id: this.histDlChek.length + 1,
              name: saveName,
              data: checkList,
              lineIndex: selectedIndex
            });
            this.histDlChek.push(histDLItem);
            localStorage.setItem(this.labelHistDlChek, JSON.stringify(this.histDlChek));
          } else {
            histDLItem = Object.assign({id: 1, name: saveName, data: checkList, lineIndex: selectedIndex});
            const arr = [];
            arr.push(histDLItem);
            localStorage.setItem(this.labelHistDlChek, JSON.stringify(arr));
          }
          this.messageService.info(SystemMessage.Info.msg100001);
          this.isSave = false;
          this.isFormRestore = true;
          this.saveForm.controls.saveName.setValue('');
        }
        break;
      // 復元
      case 'openRestoreBox':
        this.isRestore = !this.isRestore;
        this.isFormSave = false;
        const local1 = localStorage.getItem(this.labelHistDlChek);

        if (local1) {
          const item1 = JSON.parse(local1);
          this.restoreList = item1;
        }
        break;
      // 選択状態の復元キャンセル
      case 'restoreCancel':
        this.isRestore = !this.isRestore;
        this.isFormSave = true;
        break;
      case 'removeRestore':
        const selectItem = this.gridRestoreList.selectedItems;
        const local2 = localStorage.getItem(this.labelHistDlChek);
        this.histDlChek = JSON.parse(local2);

        if (selectItem) {
          this.histDlChek.forEach((current, index) => {
            if (selectItem[0].id === current.id) {
              this.histDlChek.splice(index, 1);
            }
          });
          localStorage.setItem(this.labelHistDlChek, JSON.stringify(this.histDlChek));
          this.restoreList = this.histDlChek;
          this.gridRestoreList.resetSelection();
          this.gridRestoreList.collectionView.refresh();
          if (this.histDlChek.length === 0) {
            localStorage.removeItem(this.labelHistDlChek);
          }
        }
        break;
      // 選択状態の復元選択
      case 'restoreSelect':
        const selectedItem = this.gridRestoreList.selectedItems[0];

        if (selectedItem) {
          let i: number;
          // tslint:disable-next-line: no-shadowed-variable
          const data = selectedItem.data;
          this.downloadItemList = this.downloadItemList.map(item => {
            for (i = 0; i < data.length; i++) {
              if (data[i].internalCode === item.internalCode) {
                item.sel = true;
                return item;
              }
            }
            item.sel = false;
            return item;
          });
          this.gridDownloadSelectionList.resetSelection();
          this.gridDownloadSelectionList.collectionView.refresh();
          this.lineItemFIForm.controls.selectType.setValue(selectedItem.lineIndex);
          if (selectedItem.lineIndex === '') {
            this.lineItemFIForm.reset();
          }
          this.isRestore = !this.isRestore;
          this.isFormSave = true;
        }
        break;
      // ダウンロード
      case 'download':
        const itemAC = [];
        const itemObjAC = [];
        const dataAC = this.downloadItemList;
        let j: number;

        // 選択項目セット
        if (dataAC !== []) {
          const addCheckPropMap = new Object(); // 追加選択項目(後続項目のみサポー)
          dataAC.map((item, i) => {
            if (item.sel === true || i < this.keyItemCount) { // チェックされている or キー項目
              // ダウンロード項目に追加
              itemAC.push(item.value3);
              itemObjAC.push(item);

              // 追加選択項目有り
              if (item.value17 !== null && item.value17 !== '') {
                const addProps = item.value17.split(' ');
                for (j = 0; j < addProps.length; j++) {
                  addCheckPropMap[addProps[j]] = '1';
                }
              }
            } else {
              if (addCheckPropMap.hasOwnProperty(item.value3)) {
                // 追加選択項目のためダウンロード項目に追加
                itemAC.push(item.value3);
                itemObjAC.push(item);
              }
            }
          });
        }
        const retValue = new Object();
        retValue['item'] = itemAC;
        retValue['itemObjAC'] = itemObjAC;
        retValue['lineItem'] = this.lineItemFIForm.controls.selectType.value !== '0' ? this.lineItemFIForm.controls.selectType.value : '';
        this.select.emit(retValue);
        this.close();
        break;
    }
  }

  /**
   * ポップアップを表示
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.downloadSelectionPopup.hide();
  }
}
