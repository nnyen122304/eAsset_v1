import { Component, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { InvStatEx } from 'src/app/models/api/inv/inv-stat-ex';

/**
 * 公開設定ポップアップコンポネント
 */
@Component({
  selector: 'app-release-settings-popup',
  templateUrl: './release-settings-popup.component.html',
  styleUrls: ['./release-settings-popup.component.scss']
})
export class ReleaseSettingsPopupComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 更新用フォーム
   */
  updateForm: FormGroup;

  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 公開設定ポップアップ Ref
   */
  @ViewChild('releaseSettingsPopup', null) releaseSettingsPopup: EaPopup;

  constructor(
    private fb: FormBuilder
  ) {
    this.fb = fb;
    this.updateForm = this.fb.group({
      period: null,
      periodFormated: null,
      equipPublicType: null,
      equipPublicTypeName: null,
      equipSendEndDate: null,
      equipSendEndDateFormated: null,
      equipSendStartDate: null,
      equipSendStartDateFormated: null,
      equipSendStatus: null,
      equipSendStatusName: null,
      fldIntPublicType: null,
      fldIntPublicTypeName: null,
      fldIntSendEndDate: null,
      fldIntSendEndDateFormated: null,
      fldIntSendStartDate: null,
      fldIntSendStartDateFormated: null,
      fldIntSendStatus: null,
      fldIntSendStatusName: null,
      fldRoPublicType: null,
      fldRoPublicTypeName: null,
      fldRoSendEndDate: null,
      fldRoSendEndDateFormated: null,
      fldRoSendStartDate: null,
      fldRoSendStartDateFormated: null,
      fldRoSendStatus: null,
      fldRoSendStatusName: null,
      fldTanPublicType: null,
      fldTanPublicTypeName: null,
      fldTanSendEndDate: null,
      fldTanSendEndDateFormated: null,
      fldTanSendStartDate: null,
      fldTanSendStartDateFormated: null,
      fldTanSendStatus: null,
      fldTanSendStatusName: null,
      lastSuccessCreateEndDate: null,
      lastSuccessCreateEndDateFormated: null,
      lastSuccessCreateStartDate: null,
      lastSuccessCreateStartDateFormated: null,
      lastSuccessExecStaffCode: null,
      lePublicType: null,
      lePublicTypeName: null,
      leSendEndDate: null,
      leSendEndDateFormated: null,
      leSendStartDate: null,
      leSendStartDateFormated: null,
      leSendStatus: null,
      leSendStatusName: null,
      leSendStatusNameFormated: null,
      rePublicType: null,
      rePublicTypeName: null,
      reSendEndDate: null,
      reSendEndDateFormated: null,
      reSendStartDate: null,
      reSendStartDateFormated: null,
      reSendStatus: null,
      reSendStatusName: null,
      fldTanMail: false,
      fldRoMail: false,
      fldIntMail: false,
      leMail: false,
      reMail: false,
      equipMail: false
    });
  }

  /**
   * ポップアップを表示
   */
  open(opener: Element) {
    this.opener = opener;
    this.releaseSettingsPopup.show(true);
  }

  /**
   * ポップアップを非表示
   */
  close() {
    this.releaseSettingsPopup.hide();
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
  }

  /**
   * ポップアップにデータを設定する
   */
  setData(data: InvStatEx) {
    this.updateForm.reset(); // Reset form before setting new data
    this.updateForm.controls.period.setValue(data.period);
    this.updateForm.controls.periodFormated.setValue(data.periodFormated);
    this.updateForm.controls.equipPublicType.setValue(data.equipPublicType);
    this.updateForm.controls.equipPublicTypeName.setValue(data.equipPublicTypeName);
    this.updateForm.controls.equipSendEndDate.setValue(data.equipSendEndDate);
    this.updateForm.controls.equipSendStartDate.setValue(data.equipSendStartDate);
    this.updateForm.controls.equipSendStatus.setValue(data.equipSendStatus);
    this.updateForm.controls.equipSendStatusName.setValue(data.equipSendStatusName);
    this.updateForm.controls.fldIntPublicType.setValue(data.fldIntPublicType);
    this.updateForm.controls.fldIntPublicTypeName.setValue(data.fldIntPublicTypeName);
    this.updateForm.controls.fldIntSendEndDate.setValue(data.fldIntSendEndDate);
    this.updateForm.controls.fldIntSendStartDate.setValue(data.fldIntSendStartDate);
    this.updateForm.controls.fldIntSendStatus.setValue(data.fldIntSendStatus);
    this.updateForm.controls.fldIntSendStatusName.setValue(data.fldIntSendStatusName);
    this.updateForm.controls.fldRoPublicType.setValue(data.fldRoPublicType);
    this.updateForm.controls.fldRoPublicTypeName.setValue(data.fldRoPublicTypeName);
    this.updateForm.controls.fldRoSendEndDate.setValue(data.fldRoSendEndDate);
    this.updateForm.controls.fldRoSendStartDate.setValue(data.fldRoSendStartDate);
    this.updateForm.controls.fldRoSendStatus.setValue(data.fldRoSendStatus);
    this.updateForm.controls.fldRoSendStatusName.setValue(data.fldRoSendStatusName);
    this.updateForm.controls.fldTanPublicType.setValue(data.fldTanPublicType);
    this.updateForm.controls.fldTanPublicTypeName.setValue(data.fldTanPublicTypeName);
    this.updateForm.controls.fldTanSendEndDate.setValue(data.fldTanSendEndDate);
    this.updateForm.controls.fldTanSendStartDate.setValue(data.fldTanSendStartDate);
    this.updateForm.controls.fldTanSendStatus.setValue(data.fldTanSendStatus);
    this.updateForm.controls.fldTanSendStatusName.setValue(data.fldTanSendStatusName);
    this.updateForm.controls.lastSuccessCreateEndDate.setValue(data.lastSuccessCreateEndDate);
    this.updateForm.controls.lastSuccessCreateStartDate.setValue(data.lastSuccessCreateStartDate);
    this.updateForm.controls.lastSuccessExecStaffCode.setValue(data.lastSuccessExecStaffCode);
    this.updateForm.controls.lePublicType.setValue(data.lePublicType);
    this.updateForm.controls.lePublicTypeName.setValue(data.lePublicTypeName);
    this.updateForm.controls.leSendEndDate.setValue(data.leSendEndDate);
    this.updateForm.controls.leSendStartDate.setValue(data.leSendStartDate);
    this.updateForm.controls.leSendStatus.setValue(data.leSendStatus);
    this.updateForm.controls.leSendStatusName.setValue(data.leSendStatusName);
    this.updateForm.controls.rePublicType.setValue(data.rePublicType);
    this.updateForm.controls.rePublicTypeName.setValue(data.rePublicTypeName);
    this.updateForm.controls.reSendEndDate.setValue(data.reSendEndDate);
    this.updateForm.controls.reSendStartDate.setValue(data.reSendStartDate);
    this.updateForm.controls.reSendStatus.setValue(data.reSendStatus);
    this.updateForm.controls.reSendStatusName.setValue(data.reSendStatusName);
    this.updateForm.controls.fldTanMail.setValue(false);
    this.updateForm.controls.fldRoMail.setValue(false);
    this.updateForm.controls.fldIntMail.setValue(false);
    this.updateForm.controls.leMail.setValue(false);
    this.updateForm.controls.reMail.setValue(false);
    this.updateForm.controls.equipMail.setValue(false);
  }

  /**
   * 変更された内容を親コンポネントに送信
   */
  sendChanges() {
    this.select.emit({data: this.updateForm.value});
  }
}
