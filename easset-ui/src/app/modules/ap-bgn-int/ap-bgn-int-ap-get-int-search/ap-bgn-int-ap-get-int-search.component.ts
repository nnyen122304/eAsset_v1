import { Component, OnInit, ViewChild } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApGetIntService } from 'src/app/services/api/ap-get-int.service';
import { MessageService } from 'src/app/services/message.service';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { SystemMessage } from 'src/app/const/system-message';
import { ApGetIntSR } from 'src/app/models/api/ap-get-int/ap-get-int-sr.model';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApBgnIntComponent } from '../ap-bgn-int.component';
import { ApGetIntSearchConditionComponent } from 'src/app/parts/screen/ap-get-int/ap-get-int-search-condition/ap-get-int-search-condition.component';


@Component({
  selector: 'app-ap-bgn-int-ap-get-int-search',
  templateUrl: './ap-bgn-int-ap-get-int-search.component.html',
  styleUrls: ['./ap-bgn-int-ap-get-int-search.component.scss']
})
export class ApBgnIntApGetIntSearchComponent extends AbstractChildComponent<ApBgnIntComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索条件
   */
  searchParams: ApGetIntSC;

  /**
   * 検索条件グリッド
   */
  @ViewChild(ApGetIntSearchConditionComponent) searchCondition: ApGetIntSearchConditionComponent;

  constructor(
    private sessionService: SessionService,
    private apGetIntService: ApGetIntService,
    private messageService: MessageService
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
          this.parent.changeChild(this.parent.viewIndexApGetIntResult, {
            action: 'initResult',
            params: {
              dataList: resp,
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
}
