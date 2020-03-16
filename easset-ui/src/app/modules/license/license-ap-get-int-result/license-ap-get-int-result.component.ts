import { Component, OnInit, ViewChild } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetIntSR } from 'src/app/models/api/ap-get-int/ap-get-int-sr.model';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { SessionService } from 'src/app/services/session.service';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { LicenseComponent } from '../license.component';
import { ApGetIntResultListComponent } from 'src/app/parts/screen/ap-get-int/ap-get-int-result-list/ap-get-int-result-list.component';

@Component({
  selector: 'app-license-ap-get-int-result',
  templateUrl: './license-ap-get-int-result.component.html',
  styleUrls: ['./license-ap-get-int-result.component.scss']
})
export class LicenseApGetIntResultComponent extends AbstractChildComponent<LicenseComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;
  /**
   * 取得申請検索結果一覧
   */
  apGetIntSRList: ApGetIntSR[] = [];

  /**
   * 取得申請検索結果
   */
  searchApGetIntSC: ApGetIntSC;

  /**
   * 取得申請検索結果(無形固定資産)
   */
  @ViewChild(ApGetIntResultListComponent) resultList: ApGetIntResultListComponent;

  constructor(private sessionService: SessionService
  ) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initResult') {
        this.apGetIntSRList = param.params.dataList;
        this.searchApGetIntSC = param.params.searchParams;
      } else if (param.action === 'back') {
        if (param.params.updatedRow) {
          this.resultList.updateRow(param.params.updatedRow);
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
    this.parent.changeChild(this.parent.viewIndexApGetIntSearch, {
      action: 'backSearch',
      params: {
        searchParams: this.searchApGetIntSC
      },
    });
  }

  /**
   * 子画面切替処理
   */
  clickRow(row) {
    if (!row || !row.dataItem) {
      return;
    }

    this.parent.changeChild(this.parent.viewIndexSearch, {
      action: 'initCreate',
      params: {
        loadApGetIntId: row.dataItem.applicationId,
        loadApGetIntVersion: row.dataItem.applicationVersion,
        index: row.index
      },
    });
  }
}
