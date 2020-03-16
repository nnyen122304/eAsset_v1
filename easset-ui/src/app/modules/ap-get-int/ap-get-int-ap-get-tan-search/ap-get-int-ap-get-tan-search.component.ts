import { Component, OnInit, ViewChild } from '@angular/core';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetTanSearchConditionComponent } from 'src/app/parts/screen/ap-get-tan/ap-get-tan-search-condition/ap-get-tan-search-condition.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApGetIntComponent } from '../ap-get-int.component';
import { SessionService } from 'src/app/services/session.service';
import { ApGetTanService } from 'src/app/services/api/ap-get-tan.service';
import { MessageService } from 'src/app/services/message.service';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-ap-get-int-ap-get-tan-search',
  templateUrl: './ap-get-int-ap-get-tan-search.component.html',
  styleUrls: ['./ap-get-int-ap-get-tan-search.component.scss']
})
export class ApGetIntApGetTanSearchComponent extends AbstractChildComponent<ApGetIntComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * ログイン社員番号
   */
  loginStaffCode: string;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * 検索条件
   */
  searchParam: ApGetTanSC;

  @ViewChild(ApGetTanSearchConditionComponent) searchCondition: ApGetTanSearchConditionComponent;

  constructor(
    private sessionService: SessionService,
    private apGetTanService: ApGetTanService,
    private messageService: MessageService,
  ) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.loginStaffCode = this.sessionInfo.loginUser.staffCode;
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.searchCondition.init();
      }
    });
  }

  /**
   * 検索
   */
  search() {
    this.searchParam = this.searchCondition.getSearchCondition();
    this.apGetTanService.searchApGetTan(this.loginStaffCode, this.accessLevel, this.searchParam).subscribe(
      (resp: ApGetTanSR[]) => {
        if (resp.length > 0) {
          this.parent.changeChild(this.parent.viewIndexResult, {
            action: 'initResult',
            params: {
              datalist: resp,
              searchParam: this.searchParam
            }
          });
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
      }
    );
  }

  /**
   * クリア
   */
  resetSearch() {
    this.searchCondition.init();
  }

  /**
   * 戻る
   */
  back() {
    this.parent.changeChild(this.parent.viewIndexApGetIntMain, {action: 'backFromLine', params: {}});
  }
}
