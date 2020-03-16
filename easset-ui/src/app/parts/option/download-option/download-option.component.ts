import { Component, Output, ViewChild, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';

@Component({
  selector: 'app-download-option',
  templateUrl: './download-option.component.html',
  styleUrls: ['./download-option.component.scss']
})
export class DownloadOptionComponent {
  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 更新用フォーム
   */
  optionForm: FormGroup;

  /**
   * 現物明細単位
   */
  @Input() optText1 = '申請書単位';

  /**
   * 現物明細単位
   */
  @Input() optText2 = '現物明細単位';

  /**
   * データ選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 公開設定ポップアップ Ref
   */
  @ViewChild('downloadOptionPopup', null) downloadOptionPopup: EaPopup;

  constructor(private fb: FormBuilder) {
    this.optionForm = this.fb.group({
      optValue: [1]
    });
  }

  /**
   * ポップアップを表示
   */
  open(opener: Element) {
    this.opener = opener;
    this.optionForm.controls.optValue.setValue(1);
    this.downloadOptionPopup.show(true);
  }

  /**
   * ポップアップを表示
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.downloadOptionPopup.hide();
  }

  /**
   * 変更された内容を親コンポネントに送信
   */
  submit() {
    const result = {lineOutputFlag: false};
    if (this.optionForm.controls.optValue.value === 2) {
      result.lineOutputFlag = true;
    }

    this.select.emit(result);
    this.close();
  }
}
