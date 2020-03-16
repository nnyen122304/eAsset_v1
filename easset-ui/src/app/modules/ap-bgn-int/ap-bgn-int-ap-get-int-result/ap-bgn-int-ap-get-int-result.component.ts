import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApBgnIntComponent } from '../ap-bgn-int.component';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetIntSR } from 'src/app/models/api/ap-get-int/ap-get-int-sr.model';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { SessionService } from 'src/app/services/session.service';
import { ApGetIntResultListComponent } from 'src/app/parts/screen/ap-get-int/ap-get-int-result-list/ap-get-int-result-list.component';

@Component({
  selector: 'app-ap-bgn-int-ap-get-int-result',
  templateUrl: './ap-bgn-int-ap-get-int-result.component.html',
  styleUrls: ['./ap-bgn-int-ap-get-int-result.component.scss']
})
export class ApBgnIntApGetIntResultComponent extends AbstractChildComponent<ApBgnIntComponent> implements OnInit {
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
      if (param.action === 'initResult') { // 取得申請検索結果一覧
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
   * 取得申請選択
   */
  clickRow(row) {
    if (!row || !row.dataItem) {
      return;
    }
    // サービス提供開始報告登録
    this.parent.changeChild(this.parent.viewIndexEntry, {
      action: 'initCreate',
      params: {
        loadApGetIntId: row.dataItem.applicationId,
        loadApGetIntVersion: row.dataItem.applicationVersion,
        index: row.index
      },
    });
  }
}
