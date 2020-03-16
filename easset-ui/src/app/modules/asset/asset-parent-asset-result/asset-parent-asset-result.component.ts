import { Component, OnInit, ViewChild } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { AssetResultListComponent } from 'src/app/parts/screen/asset/asset-result-list/asset-result-list.component';
import { SessionService } from 'src/app/services/session.service';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { AssetComponent } from '../asset.component';

@Component({
  selector: 'app-asset-parent-asset-result',
  templateUrl: './asset-parent-asset-result.component.html',
  styleUrls: ['./asset-parent-asset-result.component.scss']
})
export class AssetParentAssetResultComponent extends AbstractChildComponent<AssetComponent> implements OnInit {

  sessionInfo: SessionInfo;
  assetSRList: AssetSR[] = [];
  assetSCSearch: AssetSC;

  @ViewChild(AssetResultListComponent) resultList: AssetResultListComponent;

  constructor(
    private sessionService: SessionService,
  ) {
    super();
  }

  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initResult') {
        this.assetSRList = param.params.datalist;
        this.assetSCSearch = param.params.searchParams;
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
        searchParams: this.assetSCSearch
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
}
