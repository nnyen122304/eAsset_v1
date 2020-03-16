import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { CodeNameEx } from 'src/app/models/api/code-name-ex';

@Component({
  selector: 'app-open-close-switching',
  templateUrl: './open-close-switching.component.html',
  styleUrls: ['./open-close-switching.component.scss']
})
export class OpenCloseSwitchingComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 更新用フォーム
   */
  openCloseForm: FormGroup;

  constructor(
    private fb: FormBuilder
  ) {
    this.openCloseForm = this.fb.group({
      value2: null,
      isSendMail: false
    });
  }

  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 公開設定ポップアップ Ref
   */
  @ViewChild('openCloseSwitchingPopup', null) openCloseSwitchingPopup: EaPopup;

  /**
   * ポップアップを表示
   */
  open(opener: Element) {
    this.opener = opener;
    this.openCloseSwitchingPopup.show(true);
  }

  /**
   * ポップアップを表示
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.openCloseSwitchingPopup.hide();

  }

  /**
   * Remove checked
   */
  onCheckboxChange() {
    this.openCloseForm.controls.isSendMail.setValue(false);
  }

  /**
   * ポップアップにデータを設定する
   */
  setData(data: CodeNameEx) {
    this.openCloseForm.reset();
    this.openCloseForm.controls.value2.setValue(data.value2);
    this.openCloseForm.controls.isSendMail.setValue(false);
  }

  /**
   * 変更された内容を親コンポネントに送信
   */
  sendChanges() {
    this.select.emit({ data: this.openCloseForm.value });
  }
}
