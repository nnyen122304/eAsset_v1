import { Component, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-download-option-fld-int',
  templateUrl: './download-option-fld-int.component.html',
  styleUrls: ['./download-option-fld-int.component.scss']
})
export class DownloadOptionFldIntComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 更新用フォーム
   */
  fldDownloadForm: FormGroup;

  /**
   * データ選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 公開設定ポップアップ Ref
   */
  @ViewChild('fldDownloadOptionPopup', null) fldDownloadOptionPopup: EaPopup;

  constructor(private fb: FormBuilder) {
    this.fldDownloadForm = this.fb.group({
      downloadOption: [1]
    });
  }

  /**
   * ポップアップを表示
   */
  open(opener: Element) {
    this.opener = opener;
    this.fldDownloadForm.controls.downloadOption.setValue(1);
    this.fldDownloadOptionPopup.show(true);
  }

  /**
   * ポップアップを表示
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.fldDownloadOptionPopup.hide();
  }

  /**
   * 変更された内容を親コンポネントに送信
   */
  submit() {
    const result = {itemDef: SystemConst.CategoryCodeItemDefPpfsFld.categoryCodeItemDefPpfsFldSrIntApp};
    if (this.fldDownloadForm.controls.downloadOption.value === 1) {
      result.itemDef = SystemConst.CategoryCodeItemDefPpfsFld.categoryCodeItemDefPpfsFldSrInt;
    }

    this.select.emit(result);
    this.close();
  }
}
