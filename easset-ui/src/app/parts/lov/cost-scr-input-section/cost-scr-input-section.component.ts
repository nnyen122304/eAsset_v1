import { Component, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SessionInfo } from 'src/app/models/session-info';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { CostScrService } from 'src/app/services/api/cost-scr.service';
import { Section } from 'src/app/models/api/section';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';
import { SessionService } from '../../../services/session.service';

@Component({
  selector: 'app-cost-scr-input-section',
  templateUrl: './cost-scr-input-section.component.html',
  styleUrls: ['./cost-scr-input-section.component.scss'],
})

export class CostScrInputSectionComponent {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * Form Group Section
   */
  sectionForm: FormGroup;

  /**
   * modal title
   */
  @Input() modalTitle = '精査担当部署';

  /**
   * Show button clear
   */
  @Input() showButtonClear = true;

  /**
   * データ選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('confirmPopup', null) confirmPopup: ConfirmPopupComponent;

  /**
   * Ref
   */
  @ViewChild('openInputSectionPopup') openInputSectionPopup: EaPopup;

  constructor(
    private fb: FormBuilder,
    private costScrService: CostScrService,
    private messageService: MessageService,
    private sessionService: SessionService
  ) {
    this.fb = fb;
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.sectionForm = this.fb.group({
      ipCostScrSectionName: [''],
      ipCostScrSectionCode: [''],
      ipCostScrSectionYear: [''],
    });
  }

  /**
   * ポップアップを表示
   */
  open(opener: Element) {
    this.opener = opener;
    this.openInputSectionPopup.show(true);
    this.sectionForm.reset();
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.openInputSectionPopup.hide();
  }

  /**
   * OKボタンハンドラー
   */
  ok() {
    const sectionName = this.sectionForm.controls['ipCostScrSectionName'].value;
    const loginStaffCode = this.sessionInfo.loginUser.staffCode;
    const accessLevel = this.sessionInfo.currentAccessLevel;
    const companyCode = this.sessionInfo.loginCompanyCode;

    if (sectionName !== null && sectionName !== '') {
      this.costScrService.getSectionByName(loginStaffCode, accessLevel, companyCode, sectionName).subscribe((data: Section) => {
        this.select.emit(data);
        this.messageService.info(SystemMessage.Info.msg100018);
      });
      this.close();
    } else {
      this.confirmPopup.prompt(SystemMessage.Info.msg100019, document.activeElement);
      this.confirmPopup.confirm.subscribe(() => {
        this.close();
      });
    }
  }
}
