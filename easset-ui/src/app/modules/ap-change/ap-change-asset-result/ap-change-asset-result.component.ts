import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApChangeComponent } from '../ap-change.component';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { AssetResultListComponent } from 'src/app/parts/screen/asset/asset-result-list/asset-result-list.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-ap-change-asset-result',
  templateUrl: './ap-change-asset-result.component.html',
  styleUrls: ['./ap-change-asset-result.component.scss']
})
export class ApChangeAssetResultComponent extends AbstractChildComponent<ApChangeComponent> implements OnInit {

  sessionInfo: SessionInfo;
  assetSRList: AssetSR[] = [];
  assetSCSearch: AssetSC = {};

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild(AssetResultListComponent) resultList: AssetResultListComponent;


  constructor(
    private sessionService: SessionService,
    private messageService: MessageService
  ) {
    super();
  }

  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initResult') {
        this.assetSRList = param.params.datalist;
        this.assetSCSearch = param.params.searchParam;
        this.resultList.deleteFlagInput = this.assetSCSearch.deleteFlag;
      } else if (param.action === 'back') {
        if (param.params.updateRow) {
          this.resultList.updateRow(param.params.updateRow);
        }
      } else {
        this.goBack();
      }
    });
  }

  /**
   * Go Back
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexSearch, {
      action: 'backSearch',
      params: {
        searchParam: this.assetSCSearch
      },
    });
  }

  /**
   * Go to detail
   */
  clickRow(row) {
    if (!row || !row.dataItem) {
      return;
    }

    this.parent.changeChild(this.parent.viewIndexEntry, {
      action: 'initCreate',
      params: {
        loadAssetId: row.dataItem.applicationId,
        loadAssetVersion: row.dataItem.applicationVersion,
        index: row.index
      },
    });
  }

  /**
   * Select item
   */
  selectedAssetItem() {
    const list: ApGetTanSR[] = this.resultList.gridAssetResultList.getCheckedRows();
    if (list.length === 0 || list === null || list === undefined) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    } else {
      this.parent.changeChild(this.parent.viewIndexLicenseSearchResult, {
        action: 'initLine',
        params: {
          datalist: this.resultList.gridAssetResultList.getCheckedRows()
        }
      });
    }
  }

}
