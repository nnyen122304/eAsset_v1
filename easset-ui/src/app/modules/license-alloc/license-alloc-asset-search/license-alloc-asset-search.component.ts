import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { LicenseAllocComponent } from '../license-alloc.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { AssetSearchConditionComponent } from 'src/app/parts/screen/asset/asset-search-condition/asset-search-condition.component';

@Component({
  selector: 'app-license-alloc-asset-search',
  templateUrl: './license-alloc-asset-search.component.html',
  styleUrls: ['./license-alloc-asset-search.component.scss']
})
export class LicenseAllocAssetSearchComponent extends AbstractChildComponent<LicenseAllocComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * Component Ref
   */
  @ViewChild(AssetSearchConditionComponent, null) assetSearchConditionComponent: AssetSearchConditionComponent;

  constructor(
    private sessionService: SessionService,
    private apAssetService: ApAssetService,
    private messageService: MessageService) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe(param => {
      this.assetSearchConditionComponent.changeCondition();
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
          this.parent.changeChild(this.parent.viewIndexAllocAssetResult, {
            action: 'initResult',
            params: {
              datalist: data,
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
}
