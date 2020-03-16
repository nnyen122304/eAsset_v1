import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApAssetComponent } from '../ap-asset.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApGetTanSearchConditionComponent } from 'src/app/parts/screen/ap-get-tan/ap-get-tan-search-condition/ap-get-tan-search-condition.component';
import { ApGetTanService } from 'src/app/services/api/ap-get-tan.service';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';

@Component({
  selector: 'app-ap-asset-ap-get-tan-search',
  templateUrl: './ap-asset-ap-get-tan-search.component.html',
  styleUrls: ['./ap-asset-ap-get-tan-search.component.scss']
})
export class ApAssetApGetTanSearchComponent extends AbstractChildComponent<ApAssetComponent> implements OnInit {

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
          this.parent.changeChild(this.parent.viewIndexApGetTanResult, {
            action: 'initResult',
            params: {
              dataList: resp,
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
}
