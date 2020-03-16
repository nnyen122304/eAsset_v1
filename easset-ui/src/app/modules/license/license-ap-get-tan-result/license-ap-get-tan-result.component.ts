import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { LicenseComponent } from '../license.component';
import { ApGetTanResultListComponent } from 'src/app/parts/screen/ap-get-tan/ap-get-tan-result-list/ap-get-tan-result-list.component';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-license-ap-get-tan-result',
  templateUrl: './license-ap-get-tan-result.component.html',
  styleUrls: ['./license-ap-get-tan-result.component.scss']
})
export class LicenseApGetTanResultComponent extends AbstractChildComponent<LicenseComponent> implements OnInit {

  /**
   * 検索条件
   */
  searchParamSC: ApGetTanSC = {};

  /**
   * 入力データ
   */
  listApGetTanSR: ApGetTanSR[] = [];

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild(ApGetTanResultListComponent) resultList: ApGetTanResultListComponent;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridApGetTanResultList', null) gridApGetTanResultList: EaFlexGrid;

  constructor(private sessionService: SessionService, ) {
    super();
  }

  /**
   * コンポネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initResult') {
        this.listApGetTanSR = param.params.datalist;
        this.searchParamSC = param.params.searchParam;
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
    * Go Back
    */
   goBack() {
    this.parent.changeChild(this.parent.viewIndexLicenseApGetTanSearch, {
      action: 'backSearch',
      params: {
        searchParam: this.searchParamSC
      },
    });
  }
}
