import { Component, Output, EventEmitter, ViewChild, Input } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';

@Component({
  selector: 'app-print-option',
  templateUrl: './print-option.component.html',
  styleUrls: ['./print-option.component.scss']
})
export class PrintOptionComponent {
  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 更新用フォーム
   */
  printForm: FormGroup;

  /**
   * ラベル
   */
  @Input() label = '現物明細出力：';

  /**
   * 更新用イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 公開設定ポップアップ Ref
   */
  @ViewChild('printOptionsPopup', null) printOptionsPopup: EaPopup;

  constructor(private fb: FormBuilder) {
    this.printForm = this.fb.group({
      printOption: [1]
    });
  }

  /**
   * ポップアップを表示
   */
  open(opener: Element) {
    this.opener = opener;
    this.printForm.controls.printOption.setValue(1);
    this.printOptionsPopup.show(true);
  }

  /**
   * ポップアップを表示
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.printOptionsPopup.hide();
  }

  /**
   * ポップアップにデータを設定する
   */
  okBtnClickHandler() {
    const result = {lineOutputFlag: false};
    if (this.printForm.controls.printOption.value === 2) {
      result.lineOutputFlag = true;
    }

    this.select.emit(result);
    this.close();
  }
}
