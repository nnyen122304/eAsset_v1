import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { AssetComponent } from '../asset.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { DownloadOptionPropComponent } from 'src/app/parts/option/download-option-prop/download-option-prop.component';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { DatePipe } from '@angular/common';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { AssetSearchConditionComponent } from 'src/app/parts/screen/asset/asset-search-condition/asset-search-condition.component';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-asset-search',
  templateUrl: './asset-search.component.html',
  styleUrls: ['./asset-search.component.scss']
})
export class AssetSearchComponent extends AbstractChildComponent<AssetComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * Get menu id
   */
  menuId = SystemConst.Menu;

  /**
   * Component Ref
   */
  @ViewChild(AssetSearchConditionComponent, null) assetSearchConditionComponent: AssetSearchConditionComponent;
  @ViewChild(DownloadOptionPropComponent, null) downloadOptionPropComponent: DownloadOptionPropComponent;

  constructor(
    private sessionService: SessionService,
    private apAssetService: ApAssetService,
    private fileDownloadService: FileDownloadService,
    private datePipe: DatePipe,
    private messageService: MessageService) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
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
    const assetSCObj: AssetSC = this.assetSearchConditionComponent.getSearchCondition();

    if (assetSCObj != null) {
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
   * ダウンロード
   */
  download(event) {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const assetSCObj: AssetSC = this.assetSearchConditionComponent.getSearchCondition();

    this.apAssetService.createAssetCsv(loginStaffCode, accessLevel, event, assetSCObj, false).subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileId = resp.value;
        const contentType = 'text/csv';
        const fileName = '情報機器検索結果_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        this.fileDownloadService.download(fileId, fileName, contentType);
      });
  }

  /**
   * クリア
   */
  clear() {
    this.assetSearchConditionComponent.clearSearchCondition();
  }
}
