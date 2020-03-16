import { Component, OnInit, ViewChild } from '@angular/core';
import { ApLicenseComponent } from '../ap-license.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { FormGroup } from '@angular/forms';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { ApGetTanResultListComponent } from 'src/app/parts/screen/ap-get-tan/ap-get-tan-result-list/ap-get-tan-result-list.component';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-ap-license-ap-get-tan-result',
  templateUrl: './ap-license-ap-get-tan-result.component.html',
  styleUrls: ['./ap-license-ap-get-tan-result.component.scss']
})
export class ApLicenseApGetTanResultComponent extends AbstractChildComponent<ApLicenseComponent> implements OnInit {

  /**
   * 検索条件
   */
  searchParamSC: ApGetTanSC = {};

  /**
   * 入力データ
   */
  listApGetTanSR: ApGetTanSR[] = [];

  /**
   * オプションを作成
   */
  createOption = '1';

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
    private messageService: MessageService
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
    this.parent.changeChild(this.parent.viewIndexApLicenseApGetIntSearch, {
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
      action: 'initCreate',
      params: {
        loadAssetId: row.dataItem.applicationId,
        loadAssetVersion: row.dataItem.applicationVersion,
        index: row.index
      },
    });
  }

  /**
   *  取得申請選択(個別申請)
   */
  change(val = 1) {
    document.getElementById('wp-grid-ap-get-tan-result-list').querySelectorAll('input[type=checkbox]').forEach((e) => {
      if (val === 2) {
        e.setAttribute('style', 'display:block');
      } else {
        e.setAttribute('style', 'display:none');
      }
    });
  }

  /**
   * アプリケーションターゲット
   */
  applicationTarget() {
    const list: ApGetTanSR[] = this.resultList.gridApGetTanResultList.getCheckedRows();
    if (list.length === 0 || list === null || list === undefined) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    } else {
      this.parent.changeChild(this.parent.viewAPGetTanResultLine, {
        action: 'initLine',
        params: {
          datalist: this.resultList.gridApGetTanResultList.getCheckedRows()
        }
      });
    }
  }
}

