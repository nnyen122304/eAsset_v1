import { Component, OnInit, ViewChild } from '@angular/core';
import { LicenseAllocComponent } from '../license-alloc.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { AssetResultListComponent } from 'src/app/parts/screen/asset/asset-result-list/asset-result-list.component';
import { SessionInfo } from 'src/app/models/session-info';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-license-alloc-asset-result',
  templateUrl: './license-alloc-asset-result.component.html',
  styleUrls: ['./license-alloc-asset-result.component.scss']
})
export class LicenseAllocAssetResultComponent extends AbstractChildComponent<LicenseAllocComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索条件
   */
  assetSRList: AssetSR[] = [];

  /**
   * 検索条件
   */
  assetSCSearch: AssetSC;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild(AssetResultListComponent) resultList: AssetResultListComponent;

  constructor(
    private sessionService: SessionService,
  ) {
    super();
  }

  /**
   * 画面の読み込み
   */
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
    this.parent.changeChild(this.parent.viewIndexAllocAssetSearch, {
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

    this.parent.changeChild(this.parent.viewIndexAllocAssetSearch, {
      action: 'initCreate',
      params: {
        loadAssetId: row.dataItem.applicationId,
        loadAssetVersion: row.dataItem.applicationVersion,
        index: row.index
      },
    });
  }
}
