import { Component, forwardRef } from '@angular/core';
import { WjFlexGrid, wjFlexGridMeta } from 'wijmo/wijmo.angular2.grid';
import { Menu } from 'wijmo/wijmo.input';
import { CellRange, CellType } from 'wijmo/wijmo.grid';
import { Clipboard } from 'wijmo/wijmo';
import * as wjGrid from 'wijmo/wijmo.grid';
import * as wjCore from 'wijmo/wijmo';

import { DeepCopy } from 'src/app/utils/deep-copy';

/**
 * グリッド拡張
 * コンテキストメニューからセル値をコピーできるように拡張
 */
@Component({
  selector: 'ea-flex-grid',
  template: wjFlexGridMeta.template,
  // tslint:disable-next-line: use-input-property-decorator
  inputs: wjFlexGridMeta.inputs,
  // tslint:disable-next-line: use-output-property-decorator
  outputs: wjFlexGridMeta.outputs,
  providers: [
    { provide: 'WjComponent', useExisting: forwardRef(() => EaFlexGrid) },
    wjFlexGridMeta.providers
  ]
})
// tslint:disable-next-line: component-class-suffix
export class EaFlexGrid extends WjFlexGrid {

  /**
   * 初期処理
   */
  created(): void {

    const userAgent = window.navigator.userAgent.toLowerCase();
    if (userAgent.indexOf('msie') !== -1 || userAgent.indexOf('trident') !== -1 || userAgent.indexOf('edge') !== -1) {
      this.cloneFrozenCells = false; // 固定列のカスタムセルイベントが発生しない問題の対応
    }

    this.isReadOnly = true;
    this.selectionMode = wjGrid.SelectionMode.Row;
    this.allowMerging = wjGrid.AllowMerging.ColumnHeaders;
    this.allowDragging = wjGrid.AllowDragging.Columns;
    this.deferResizing = true;
    this.autoGenerateColumns = false;
    this.alternatingRowStep = 0;
    this.headersVisibility = wjGrid.HeadersVisibility.Column;
    this.hostElement.tabIndex = 0;

    const divs = this.hostElement.getElementsByTagName('div');
    for (let i = 0; i < divs.length; i++) {
      const div = divs.item(i);
      if (div.getAttribute('wj-part') === 'focus') {
        div.tabIndex = 0; // ポップアップ画面に表示するグリッドでもタブでフォーカス遷移させるための指定
        break;
      }
    }

    // コンテキストメニュー生成
    let clipValue: string;
    const contextMenu = new Menu(document.createElement('div'), {
      displayMemberPath: 'header',
      selectedValuePath: 'cmd',
      itemsSource: [
          { header: 'セルの値をコピー', cmd: 'copyCellToClipbord' },
      ],
      itemClicked: (menu: Menu) => {
        Clipboard.copy(clipValue);
      }
    });

    // コンテキストメニューイベント登録
    this.addEventListener(this.hostElement, 'contextmenu', (e: MouseEvent) => {
      const ht = this.hitTest(e);
      if (ht.cellType === CellType.Cell) {
        this.select(new CellRange(ht.row, ht.col));
        clipValue = this.cells.getCellElement(ht.row, ht.col).innerText;
        contextMenu.owner = this.hostElement;
        if (contextMenu.owner) {
          e.preventDefault();
          contextMenu.show(e);
        }
      }
    });

   // 単一チェックボックス対応
    this.formatItem.addHandler((s: EaFlexGrid, e: wjGrid.FormatItemEventArgs) => {
      if (this.collectionView) {
        if (e.panel === s.columnHeaders) {
          if (s.columns[e.col].header === 'Sel') {
            e.cell.innerHTML = '<input class="select-all" tabindex="-1" type="checkbox">';
            this.updateSelectAllBox();
          }
        } else if (e.panel === s.cells) {
          wjCore.setAttribute(
            e.cell.parentElement,
            'aria-selected',
            s.rows[e.row].dataItem.sel
          );
        }
      }
    });

    this.hostElement.addEventListener('click', (e: MouseEvent) => {
      if (wjCore.hasClass(e.target as HTMLInputElement, 'select-all') &&
        e.target instanceof HTMLInputElement) {
        this.deferUpdate(() => {
          // tslint:disable-next-line: no-any
          this.collectionView.items.forEach((item: any) => {
            item.sel = (e.target as HTMLInputElement).checked;
          });
        });
      }
    });

    // 初期状態ではグリッドの行選択をリセットしているため、十字キー・Enterキーでの行移動ができない。
    // そのため行が選択されていない状態で↓キー・Enterキーが押されたときは１行目が選択されるようにする。

    this.hostElement.addEventListener('keydown', (e: KeyboardEvent) => {
      if ((e.keyCode === 40 || e.keyCode === 13) && this.selectedRows.length === 0) {
        this.select(0, 0);
      }
    });

  }

  /**
   * 全てのチェックボックスを更新する
   */
  updateSelectAllBox() {

    if (!this.collectionView) {
      return;
    }

    const cb = this.hostElement.querySelector('.select-all');
    // tslint:disable-next-line: no-any
    const items: any = this.collectionView.items;

    if (cb instanceof HTMLInputElement) {
      let last: boolean = null;
      let indeterminate = false;
      for (const item of items) {
        if (last != null && item.sel !== last) {
          indeterminate = true;
          break;
        }
        last = item.sel;
      }
      cb.checked = last;
      if (indeterminate) {
        cb.checked = false;
      }
    }

  }

  /**
   * 選択行データを取得する
   * @param <T> リスト行のデータ型
   * @return 選択行データの配列
   */
  getCheckedRows<T>(): T[] {
    const list: T[] = [];
    for (const row of this.rows) {
      if (row._data.sel) {
        const rowData = DeepCopy(row._data);
        delete rowData.sel;
        list.push(rowData);
      }
    }
    return list;
  }


  /**
   * グリッドの選択行を外す
   */
  resetSelection(): void {
    setTimeout(() => {
      this.select(-1, -1);
    }, 100);
  }

}
