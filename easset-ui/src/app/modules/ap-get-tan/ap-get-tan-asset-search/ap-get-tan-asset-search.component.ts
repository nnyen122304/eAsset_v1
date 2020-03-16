import { Component, OnInit, ViewChild } from '@angular/core';
import { ApGetTanComponent } from '../ap-get-tan.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { AssetSearchConditionComponent } from 'src/app/parts/screen/asset/asset-search-condition/asset-search-condition.component';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-ap-get-tan-asset-search',
  templateUrl: './ap-get-tan-asset-search.component.html',
  styleUrls: ['./ap-get-tan-asset-search.component.scss']
})
export class ApGetTanAssetSearchComponent extends AbstractChildComponent<ApGetTanComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * Component Ref
   */
  @ViewChild(AssetSearchConditionComponent, null) assetSearchConditionComponent: AssetSearchConditionComponent;

  constructor(private sessionService: SessionService, private apAssetService: ApAssetService, private messageService: MessageService) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe(param => {
      if (param.action === 'init-asset-search') {
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
      this.apAssetService.searchAsset(loginStaffCode, accessLevel, assetSCObj, false).subscribe((data) => {
        if (data === null || data.length === 0) {
          this.messageService.warn(SystemMessage.Warn.msg200006);
        } else {
          this.parent.changeChild(this.parent.viewIndexResult, {
            action: 'initResult',
            params: {
              datalist: data,
              searchParam: assetSCObj
            }
          });
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

  /**
   * 遷移元画面へ戻る
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexEntry, {
      action: 'backSearch',
    });
  }
}
