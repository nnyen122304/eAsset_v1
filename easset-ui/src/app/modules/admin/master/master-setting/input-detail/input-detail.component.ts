import { Component, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';

import { MessageService } from 'src/app/services/message.service';

import { SessionInfo } from 'src/app/models/session-info';
import { CodeName } from 'src/app/models/api/code-name';
import { CodeNameEx } from 'src/app/models/api/code-name-ex';
import { CodeNameSet } from 'src/app/models/api/code-name-set';
import { CodeNameSetItemEx } from 'src/app/models/api/code-name-set-item-ex';
import { AdmCompanyItem } from 'src/app/models/admin/adm-company-item';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';

import { DeepCopy } from 'src/app/utils/deep-copy';
import { DeleteHighlightedText } from 'src/app/utils/highlited-text';

import { SystemMessage } from 'src/app/const/system-message';


/**
 * マスタ詳細入力コンポネント
 */

@Component({
  selector: 'app-input-detail',
  templateUrl: './input-detail.component.html',
  styleUrls: ['./input-detail.component.scss']
})
export class InputDetailComponent {

  /**
   * マスタ詳細フォーム
   */
  detailForm: FormGroup;

  /**
   * フォームを表示するか判定値
   */
  isFormShown: boolean;

  /**
   * マスタ定義一覧情報
   */
  defListData: CodeNameSetItemEx[];

  /**
   * マスタID
   */
  masterID: number;

  /**
   * マスタデータ情報
   */
  masterData: CodeNameEx;

  /**
   * チェックボックス用一覧
   */
  checkboxCompanies: AdmCompanyItem[];

  /**
   * テキストエリア編集前値
   */
  textareaPrevValues: {} = {};

  /**
   * 会社コード編集可能判定用
   */
  codeEditable = false;

  /**
   * 会社コードプレフィックスマスク
   */
  codePremask = null;

  /**
   * フォーム送信許可用
   */
  isFormEnabled = true;

  /**
   * 追加する権限
   */
  @Input() defList: CodeNameSetItemEx[];

  /**
   * マスタデータ一覧
   */
  @Input() masterList: CodeName[];

  /**
   * マスタID
   */
  @Input() categoryCode: string;

  /**
   * 選択マスタ情報
   */
  @Input() paramItem: CodeNameSet;

  /**
   * 行選択可能フラグ
   */
  @Input() lineDelEnableFlag: string;

  /**
   * 行追加可能フラグ
   */
  @Input() lineAddEnableFlag: string;

  /**
   * 親値があるか判定
   */
  @Input() hasParent: boolean;

  /**
   * 会社一覧
   */
  @Input() companyList: CodeName[];

  /**
   * セッション情報
   */
  @Input() sessionInfo: SessionInfo;

  /**
   * 行更新用イベント
   */
  @Output() update: EventEmitter<object> = new EventEmitter();

  /**
   * 行更新キャンセル用イベント
   */
  @Output() cancel: EventEmitter<boolean> = new EventEmitter();

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {
    this.fb = fb;
    this.checkboxCompanies = [];
    this.initForm();
  }

  /**
   * フォーム初期処理
   */
  initForm() {
    this.detailForm = this.fb.group({
      checkboxCompanies: [],
      sortNumber: [''],
      parentInternalName: [''],
      internalCode: [''],
      internalCodeNoPrefix: [''],
      deleteFlagName: [''],
      companyName: [''],
      createStaffCode: [''],
      createDate: [''],
      updateDate: [''],
      deleteFlag: [''],
      deleteFlagFormat: [''],
      categoryCode: [''],
      companyCode: [''],
      description: [''],
      parentSortNumber: [''],
      parentCategoryCode: [''],
      parentInternalCode: [''],
      value1: [''],
      value2: [''],
      value3: [''],
      value4: [''],
      value5: [''],
      value6: [''],
      value7: [''],
      value8: [''],
      value9: [''],
      value10: [''],
      value11: [''],
      value12: [''],
      value13: [''],
      value14: [''],
      value15: [''],
      value16: [''],
      value17: [''],
      value18: [''],
      value19: [''],
      value20: [''],
      value21: [''],
      value22: [''],
      value23: [''],
      value24: [''],
      value25: [''],
      value26: [''],
      value27: [''],
      value28: [''],
      value29: [''],
      value30: [''],
      value31: [''],
      value32: [''],
      value33: [''],
      value34: [''],
      value35: [''],
      value36: [''],
      value37: [''],
      value38: [''],
      value39: [''],
      value40: [''],
      value41: [''],
      value42: [''],
      value43: [''],
      value44: [''],
      value45: [''],
      value46: [''],
      value47: [''],
      value48: [''],
      value49: [''],
      value50: [''],
      value51: [''],
      value52: [''],
      value53: [''],
      value54: [''],
      value55: [''],
      value56: [''],
      value57: [''],
      value58: [''],
      value59: [''],
      value60: ['']
    });
    this.detailForm.controls.sortNumber.setValue(0);
    this.detailForm.controls.deleteFlagFormat.setValue(false);
    this.detailForm.controls.createDate.setValue(new Date());
    this.detailForm.controls.updateDate.setValue(new Date());
  }

  /**
   * マスタ定義取得
   */
  getDefListData() {
    this.defListData = this.defList.map((obj: CodeNameSetItemEx) => {
      if (obj.cmbData && obj.cmbLabel) {
        const cmbLabel = obj.cmbLabel.split(' ');
        obj.cmbDataFormated = [];
        obj.cmbData.split(' ').forEach((value, index) => {
          obj.cmbDataFormated.push({data: value, label: cmbLabel[index] ? cmbLabel[index] : '　'});
        });
      }
      return obj;
    });
  }


  /**
   * フォームデータ取得
   */
  getFormData() {
    this.defList.forEach((obj: CodeNameSetItemEx) => {
      if (obj.itemInputType === 'NUM') {
        this.masterData['value' + obj.itemSeq] = parseInt(this.masterData['value' + obj.itemSeq], 10);
      }
    });
    setTimeout(() => {
      for (const key of Object.keys(this.masterData)) {
        if (this.detailForm.controls[key]) {
          if (this.masterData[key] || this.masterData[key] === 0) {
            this.detailForm.controls[key].setValue(this.masterData[key]);
          } else {
            this.detailForm.controls[key].setValue('');
          }
        }
      }
    });
  }

  /**
   * ポップアップを開く
   * @param id ID
   * @param data マスタデータ
   */
  open(lastOrder: number, id?: number, data?: CodeName) {
    this.getDefListData();
    const nextOrder = parseInt(((lastOrder + 10) / 10).toString(), 10) * 10;
    this.detailForm.controls.sortNumber.setValue(!id ? nextOrder : 0);
    if (id !== undefined && data) {
      this.masterID = id;
      this.masterData = data;
      if (this.masterData.deleteFlag === '1') {
        this.masterData.deleteFlagFormat = true;
      } else {
        this.masterData.deleteFlagFormat = false;
      }
      if (this.masterData.internalCode) {
        let noPrefix = this.masterData.internalCode;
        if (this.paramItem.setCompanyCode === '000') {
          this.codePremask = this.masterData.internalCode.substring(0, 4);
          noPrefix = this.masterData.internalCode.substring(4, this.masterData.internalCode.length);
        }
        this.detailForm.controls.internalCodeNoPrefix.setValue(noPrefix);
      }
      this.codeEditable = false;
      this.getFormData();
    } else {
      this.getCmbData();
      this.masterID = undefined;
      this.masterData = undefined;
      this.codeEditable = true;
      if (this.paramItem.setCompanyCode === '000') {
        this.codePremask = this.sessionInfo.loginCompanyCode + '_';
      }
    }
    this.createCompanyList();
    this.enableSubmit();
    this.isFormShown = true;
    setTimeout(() => {
      this.textareaValidation();
      document.getElementById('focusChildInit').focus();
    });
  }

  /**
   * 会社選択状況一覧作成
   */
  createCompanyList() {
    this.checkboxCompanies = []; // 表示する予定の会社チェックボックス一覧
    const checkedCompanies =  (this.masterData && this.masterData.companyName) ? this.masterData.companyName.split(',') : []; // チェック済み会社名一覧
    const allCompanies = []; // 全ての会社コード一覧
    for (const company of this.companyList) {
      allCompanies.push(company.internalCode);
    }
    const useCompanies = (this.paramItem.useCompanyCode && this.paramItem.useCompanyCode !== '000') ? this.paramItem.useCompanyCode.split(' ') : allCompanies; // 表示される予定会社コード一覧
    for (const company of this.companyList) {
      if (useCompanies.indexOf(company.internalCode) >= 0) { // 会社が表示される予定の場合
        const item = {
          id: company.internalCode,
          name: company.value1,
          checked: checkedCompanies.indexOf(company.value1) >= 0 // チェック状態を指定する
        };
        this.checkboxCompanies.push(item);
      }
    }
    this.detailForm.controls.checkboxCompanies = this.buildCheckboxes();
  }

  /**
   * チェックボックス用フォームコントロール作成
   */
  buildCheckboxes() {
    const arr = this.checkboxCompanies.map(comp => {
      return new FormControl(comp.checked);
    });
    return new FormArray(arr);
  }

  /**
   * コンボボックスデータ取得
   */
  getCmbData() {
    this.defListData.forEach((obj: CodeNameSetItemEx) => {
      if (obj.itemInputType === 'CMB') {
        this.detailForm.controls['value' + obj.itemSeq].setValue(obj.cmbDataFormated[0].data);
      }
    });
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    this.isFormShown = false;
    this.cancel.emit(true);
    this.initForm();
  }

  /**
   * フォーム送信を有効化する
   */
  enableSubmit() {
    this.isFormEnabled = true;
  }

  /**
   * フォーム送信を無効化する
   */
  disableSubmit() {
    this.isFormEnabled = false;
  }

  /**
   * 権限追加後処理
   */
  submit() {

    this.updateCompaniesField();

    const data = DeepCopy(this.detailForm.value);

    if (data.deleteFlagFormat === true) {
      data.deleteFlag = '1';
      data.deleteFlagName = 'Yes';
    } else {
      data.deleteFlag = '0';
      data.deleteFlagName = 'No';
    }

    if (!data.categoryCode) {
      data.categoryCode = this.categoryCode;
    }
    if (this.codePremask) {
      data.internalCode = this.codePremask + data.internalCodeNoPrefix;
    } else {
      data.internalCode = data.internalCodeNoPrefix;
    }

    const invalidRequiredFields = [];

    if (this.paramItem.useCompanyCode !== '000' && this.paramItem.setCompanyCode !== '000') {
      if (!this.detailForm.controls.checkboxCompanies.value.includes(true)) {
        invalidRequiredFields.push(SystemMessage.Err.msg300017);
      }
    }
    if (this.detailForm.controls.internalCodeNoPrefix.errors && this.detailForm.controls.internalCodeNoPrefix.errors.required) {
      invalidRequiredFields.push(SystemMessage.Err.msg300018);
    }
    if (this.detailForm.controls.sortNumber.errors && this.detailForm.controls.sortNumber.errors.required) {
      invalidRequiredFields.push(SystemMessage.Err.msg300019);
    }
    if (!this.masterData && this.codeAlreadyExists(data.internalCode)) {
      invalidRequiredFields.push(SystemMessage.Err.msg300020);
    }

    if (!this.masterData) {
      data.createStaffCode = 'easset';
    }

    delete data.checkboxCompanies;
    delete data.deleteFlagFormat;
    delete data.internalCodeNoPrefix;

    if (invalidRequiredFields.length) {
      this.messageService.err(invalidRequiredFields);
    } else {
      this.update.emit({data, id: this.masterID});
      this.isFormShown = false;
      this.initForm();
    }
  }

  /**
   * 既存コードが入力されたか確認用
   * @param code コード
   * @return boolean
   */

  codeAlreadyExists(code: string): boolean {
    let i = 0;
    let found = false;
    while (!found && i < this.masterList.length) {
      if (this.masterList[i].internalCode === code) {
        found = true;
      } else {
        i++;
      }
    }
    return found;
  }

  /**
   * フォームオブジェクト会社値更新
   */
  updateCompaniesField() {
    if (this.paramItem.setCompanyCode !== '000') {
      if (this.paramItem.useCompanyCode === '000') {
        this.detailForm.controls.companyCode.setValue(this.companyList[0].companyCode);
        this.detailForm.controls.companyName.setValue(this.companyList[0].companyName);
      } else {
        const companyNames: string[] = [];
        const companyCodes: string[] = [];
        this.detailForm.controls.checkboxCompanies.value.forEach((key: boolean, i: number) => {
          if (key === true) {
            companyNames.push(this.checkboxCompanies[i].name);
            companyCodes.push(this.checkboxCompanies[i].id);
          }
        });
        this.detailForm.controls.companyName.setValue(companyNames.join(','));
        this.detailForm.controls.companyCode.setValue(companyCodes.join(' '));
      }
    } else {
      this.detailForm.controls.companyName.setValue(this.sessionInfo.loginCompanyName);
      this.detailForm.controls.companyCode.setValue(this.sessionInfo.loginCompanyCode);
    }
  }

  /**
   * 親選択後処理
   * @param data 親マスタ情報
   */
  onParentSelected(data: LovDataEx) {
  }

  /**
   * テキストエリアバリデーション
   */
  textareaValidation() {
    for (const master of this.defList) {
      if (master.itemInputType === 'TXTA') {
        const id = 'txta-' + master.itemSeq.toString();
        const ref = document.getElementById(id);
        const el = (ref as HTMLInputElement);
        this.textareaPrevValues[id] = el.value;
        if (master.maxChars) { el.maxLength = master.maxChars; }
        if (master.restrict) {
          el.onkeyup = (e: KeyboardEvent) => {
            if (el.value) {
              el.value = this.regexFormat(el.value, id, master.restrict);
            }
            this.textareaPrevValues[master.itemSeq] = el.value;
          };
          el.onpaste = (e) => {
            DeleteHighlightedText(el);
            setTimeout(() => {
              const selectionIndex = el.selectionStart;
              const leftPart = el.value.substring(0, selectionIndex);
              const rightPart = el.value.substring(selectionIndex, el.value.length);
              const testVal = leftPart + e.clipboardData.getData('text/plain') + rightPart;
              el.value = this.regexFormat(testVal, id, master.restrict, master.maxChars);
              this.textareaPrevValues[master.itemSeq] = el.value;
            }, 100);
          };
        }
      }
    }
  }

  /**
   * Regexパターン通りに値をフォーマットする
   * @param value 項目値
   * @param id テキストエリアID
   * @param pattern Regex
   */
  regexFormat(value: string, id: string, pattern: string, maxlength?: number): string {
    const regex = new RegExp('[' + pattern + ']+', 'g');
    const resultValue = (value.match(regex)) ? value.match(regex).join('') : this.textareaPrevValues[id];
    if (maxlength && value.length > maxlength) {
      value = value.slice(0, maxlength);
    }
    return resultValue;
  }

}
