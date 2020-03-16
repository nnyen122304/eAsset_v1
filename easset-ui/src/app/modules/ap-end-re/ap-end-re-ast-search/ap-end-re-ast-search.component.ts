import { Component, OnInit, ViewChild } from '@angular/core';
import { ApEndReComponent } from '../ap-end-re.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { SystemMessage } from 'src/app/const/system-message';
import { MessageService } from 'src/app/services/message.service';
import { AssetSearchConditionComponent } from 'src/app/parts/screen/asset/asset-search-condition/asset-search-condition.component';

@Component({
  selector: 'app-ap-end-re-ast-search',
  templateUrl: './ap-end-re-ast-search.component.html',
  styleUrls: ['./ap-end-re-ast-search.component.scss']
})
export class ApEndReAstSearchComponent extends AbstractChildComponent<ApEndReComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  @ViewChild(AssetSearchConditionComponent, null) assetSearchConditionComponent: AssetSearchConditionComponent;
  constructor(private sessionService: SessionService, private apAssetService: ApAssetService,  private messageService: MessageService) {
    super();
  }

  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe(param => {
      if (param.action === 'init') {
        this.assetSearchConditionComponent.changeCondition();
      }
    });
  }

  /**
   * 検索
   */
  search() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const assetSCObj = this.assetSearchConditionComponent.getSearchCondition();

    if (assetSCObj !== null) {
      if (this.sessionInfo.currentAccessLevel === 'C' || this.sessionInfo.currentAccessLevel === 'B') {
        assetSCObj.searchScopeType = '1';
      } else {
        assetSCObj.searchScopeType = '2';
      }
      this.apAssetService.searchAsset(loginStaffCode, accessLevel, assetSCObj, false).subscribe((data) => {
        if (data === null || data.length === 0) {
          this.messageService.warn(SystemMessage.Warn.msg200006);
        } else {
          // TODO
        }
      });
    }
  }

  /**
   * クリア
   */
  clear() {
    this.assetSearchConditionComponent.clearSearchCondition();
  }

}
