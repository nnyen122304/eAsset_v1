import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { RoleSection } from 'src/app/models/api/role-section';

/**
 * プロフィール登録コンポネント
 */

@Component({
  selector: 'app-profile-register',
  templateUrl: './profile-register.component.html',
  styleUrls: ['./profile-register.component.scss']
})
export class ProfileRegisterComponent implements OnChanges {

  /**
   * 権限追加フォーム
   */
  registerForm: FormGroup;

  /**
   * フォームが表示されているか判定用
   */
  isFormShown: boolean;

  /**
   * プロフィール情報
   */
  @Input() data: RoleSection;

  /**
   * 更新用イベント
   */
  @Output() update: EventEmitter<object> = new EventEmitter();

  /**
   * キャンセル用イベント
   */
  @Output() cancel: EventEmitter<boolean> = new EventEmitter();

  constructor(
    private fb: FormBuilder
  ) {
    this.fb = fb;
    this.registerForm = this.fb.group({
      sectionName: [''],
      staffCode: [''],
      staffName: [''],
      publicFlag: [''],
      publicComment: ['']
    });
  }

  /**
   * 値変更イベント
   * @param changes 変更内容
   */
  ngOnChanges(changes: SimpleChanges) {
    if (changes.data && this.data) {
      this.registerForm.controls.sectionName.setValue(this.data.sectionName);
      this.registerForm.controls.staffCode.setValue(this.data.staffCode);
      this.registerForm.controls.staffName.setValue(this.data.staffName);
      this.registerForm.controls.publicFlag.setValue(this.data.publicFlag);
      this.registerForm.controls.publicComment.setValue(this.data.publicComment);
    }
  }

  /**
   * ポップアップを開く
   */
  open() {
    this.isFormShown = true;
    setTimeout(() => {
      document.getElementById('focusChildInit').focus();
    });
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    this.isFormShown = false;
    this.cancel.emit(true);
  }

  /**
   * 権限追加後処理
   */
  submit() {
    this.update.emit({data: this.registerForm.value});
  }


}
