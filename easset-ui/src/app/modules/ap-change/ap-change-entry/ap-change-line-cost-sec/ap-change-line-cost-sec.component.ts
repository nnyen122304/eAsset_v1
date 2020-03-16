import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SessionInfo} from 'src/app/models/session-info';
import {SessionService} from 'src/app/services/session.service';
import { ApChangeLineCostSec } from 'src/app/models/api/ap-change/ap-change-line-cost-sec.model';

@Component({
  selector: 'app-ap-change-line-cost-sec',
  templateUrl: './ap-change-line-cost-sec.component.html',
  styleUrls: ['./ap-change-line-cost-sec.component.scss']
})
export class ApChangeLineCostSecComponent implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 経費負担部署配
   */
  @Input() apChangeLineCostSec: ApChangeLineCostSec;

  /**
   *
   */
  @Input() totalCostDist: number;

  /**
   * 経費負担部署配
   */
  cpApChangeLineCostSec: ApChangeLineCostSec;
  /**
   * 行更新用イベント
   */
  @Output() save: EventEmitter<object> = new EventEmitter();

  /**
   * 行更新キャンセル用イベント
   */
  @Output() next: EventEmitter<object> = new EventEmitter();

  /**
   * 行更新用イベント
   */
  @Output() cancel: EventEmitter<object> = new EventEmitter();
  costSecBox: FormGroup;
  costSectionCodeLov = new Date();
  costSectionCodeOldLov = new Date();

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService) {
  }

  /**
   * 初期化
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.initForm();
    this.cpApChangeLineCostSec = Object.assign({}, this.cpApChangeLineCostSec, this.apChangeLineCostSec);
    this.cpApChangeLineCostSec.costDist = parseFloat((100 - this.totalCostDist).toString());
  }

  /**
   * 経費負担明細フォーム
   */
  initForm() {
    this.costSecBox = this.fb.group({
      costSectionCode: [this.apChangeLineCostSec.costSectionCode ? this.apChangeLineCostSec.costSectionCode : ''],
      costSectionName: [this.apChangeLineCostSec.costSectionName ? this.apChangeLineCostSec.costSectionName : ''],
      costSectionYear: [this.apChangeLineCostSec.costSectionYear ? this.apChangeLineCostSec.costSectionYear : ''],
    });

  }

  /**
   *
   */
  selectCostSection(data) {
    if (data) {
      this.cpApChangeLineCostSec.costSectionCode = data.sectionCode;
      this.cpApChangeLineCostSec.costCompanyCode = this.sessionInfo.loginCompanyCode;
      this.cpApChangeLineCostSec.costCompanyName = this.sessionInfo.loginCompanyName;
      this.cpApChangeLineCostSec.costSectionName = data.sectionName;
    }
  }

  /**
   * 経費負担部署をキャンセルする
   */
  cancelLineCostSec() {
    this.cancel.emit(this.cpApChangeLineCostSec);
  }

  /**
   * 経費負担部署を親コンポネントに送信する
   */
  saveLineCostSec() {
    this.save.emit(this.cpApChangeLineCostSec);
  }

  /**
   * 経費負担部署を親コンポネントに送信する
   */
  goToNextLineCostSec() {
    this.next.emit(this.cpApChangeLineCostSec);
  }
}
