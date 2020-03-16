import { Component, OnInit, ViewChild } from '@angular/core';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetTanResultListComponent } from 'src/app/parts/screen/ap-get-tan/ap-get-tan-result-list/ap-get-tan-result-list.component';
import { SessionService } from 'src/app/services/session.service';
import { ApGetIntComponent } from '../ap-get-int.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';

@Component({
  selector: 'app-ap-get-int-ap-get-tan-result',
  templateUrl: './ap-get-int-ap-get-tan-result.component.html',
  styleUrls: ['./ap-get-int-ap-get-tan-result.component.scss']
})
export class ApGetIntApGetTanResultComponent extends AbstractChildComponent<ApGetIntComponent> implements OnInit {

  /**
   * 検索条件
   */
  searchParamSC: ApGetTanSC;

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

  constructor(
    private sessionService: SessionService,
  ) {
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
   * Go Back
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexApGetIntApGetTanSearch, {
      action: 'backSearch',
      params: {
        searchParam: this.searchParamSC
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
      action: 'initLine',
      params: {
        loadApGetTanId: row.dataItem.applicationId,
        loadApGetTanAC: row.dataItem,
        index: row.index,
        loadCreateCsvFileId: '',
        loadApGetTanLineAstId: -1
      },
    });
  }
}
