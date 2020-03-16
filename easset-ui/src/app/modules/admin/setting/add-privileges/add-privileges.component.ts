import { Component, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { StaffSelectionComponent } from 'src/app/parts/lov-input/staff-selection/staff-selection.component';
import { SectionSelectionComponent } from 'src/app/parts/lov-input/section-selection/section-selection.component';

import { RoleSection } from 'src/app/models/api/role-section';
import { User } from 'src/app/models/api/user';

/**
 * 権限追加コンポネント
 */

@Component({
  selector: 'app-add-privileges',
  templateUrl: './add-privileges.component.html',
  styleUrls: ['./add-privileges.component.scss']
})
export class AddPrivilegesComponent {

  /**
   * 権限追加フォーム
   */
  addPrivilegesForm: FormGroup;


  /**
   * 部署フォーム
   */
  sectionForm: FormGroup;

  /**
   * フォームを表示するか判定用値
   */
  isFormShown: boolean;

  /**
   * 社員フォーム
   */
  @Input() staffForm: FormGroup;

  /**
   * 社員コードコントロール名
   */
  @Input() staffCodeControlName: string;

  /**
   * 社員名コントロール名
   */
  @Input() staffNameControlName: string;

  /**
   * 追加する権限
   */
  @Input() role: string;

  /**
   * 権限追加イベント
   */
  @Output() add: EventEmitter<object> = new EventEmitter();

  /**
   * キャンセルイベント
   */
  @Output() cancel: EventEmitter<boolean> = new EventEmitter();

  /**
   * 社員選択ポップアップ Ref
   */
  @ViewChild(StaffSelectionComponent, null) staffSelectionPopup: StaffSelectionComponent;

  /**
   * 部署選択ポップアップ Ref
   */
  @ViewChild(SectionSelectionComponent, null) sectionSelectionPopup: SectionSelectionComponent;

  constructor(
    private fb: FormBuilder
  ) {
    this.fb = fb;
    this.staffForm = this.fb.group({
      staffCode: [''],
      staffName: ['']
    });
    this.sectionForm = this.fb.group({
      sectionCode: [''],
      sectionName: [''],
      sectionYear: ['']
    });
    this.addPrivilegesForm = this.fb.group({
      roleCode: [''],
      companyCode: [''],
      publicFlag: ['1'],
      publicComment: ['']
    });
  }

  /**
   * ポップアップを開く
   */
  open() {
    this.isFormShown = true;
  }

  /**
   * 部署フォーム更新
   * @param data 部署情報
   * @param sectionName 部署名
   * @param sectionYear 部署年度
   */
  updateSectionForm(data: {sectionCode: string, sectionName: string, sectionYear: string}) {
    this.sectionForm.controls.sectionCode.setValue(data.sectionCode);
    this.sectionForm.controls.sectionName.setValue(data.sectionName);
    this.sectionForm.controls.sectionYear.setValue(data.sectionYear);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    this.reset();
    this.isFormShown = false;
    this.cancel.emit(true);
  }

  /**
   * 全ての値をクリアする
   */
  reset() {
    this.staffForm = this.fb.group({
      staffCode: [''],
      staffName: ['']
    });
    this.sectionForm = this.fb.group({
      sectionCode: [''],
      sectionName: [''],
      sectionYear: ['']
    });
    this.addPrivilegesForm = this.fb.group({
      roleCode: [''],
      companyCode: [''],
      publicFlag: ['1'],
      publicComment: ['']
    });
  }

  /**
   * 権限追加後処理
   */
  submit() {
    const result = {
      form: this.addPrivilegesForm.value,
      staff: this.staffForm.value,
      section: this.sectionForm.value
    };
    this.add.emit({data: result});
  }

  /**
   * 部署選択後処理
   * @param data 部署情報
   */
  selectSection(data: RoleSection) {
    this.open();
  }

  /**
   * 社員選択後処理
   * @param data ユーザー情報
   */
  selectStaff(data: User) {
    this.open();
  }

}
