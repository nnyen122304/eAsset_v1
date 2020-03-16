import { Component, OnInit, ViewChild } from '@angular/core';
import { SystemMessage } from 'src/app/const/system-message';
import { ApGetIntSR } from 'src/app/models/api/ap-get-int/ap-get-int-sr.model';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { ApGetIntService } from 'src/app/services/api/ap-get-int.service';
import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { SessionInfo } from 'src/app/models/session-info';
import { LicenseComponent } from '../license.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApGetIntSearchConditionComponent } from 'src/app/parts/screen/ap-get-int/ap-get-int-search-condition/ap-get-int-search-condition.component';

@Component({
  selector: 'app-license-ap-get-int-search',
  templateUrl: './license-ap-get-int-search.component.html',
  styleUrls: ['./license-ap-get-int-search.component.scss']
})
export class LicenseApGetIntSearchComponent extends AbstractChildComponent<LicenseComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索条件
   */
  searchParams: ApGetIntSC;

  @ViewChild(ApGetIntSearchConditionComponent) searchCondition: ApGetIntSearchConditionComponent;

  constructor(
    private sessionService: SessionService,
    private apGetIntService: ApGetIntService,
    private messageService: MessageService,
  ) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
  }

  /**
   * 検索
   */
  search() {
    this.searchParams = this.searchCondition.getSearchCondition();
    this.apGetIntService.searchApGetInt(
      this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel,
      this.searchParams).subscribe(
      (resp: ApGetIntSR[]) => {
        if (resp.length > 0) {
          this.parent.changeChild(this.parent.viewIndexResult, {
            action: 'initResult',
            params: {
              datalist: resp,
              searchParams: this.searchParams
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
  clear() {
    this.searchCondition.resetSearch();
  }

  /**
   * 戻る
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexSearch, {action: 'backFromInt', params: {}});
  }
}
