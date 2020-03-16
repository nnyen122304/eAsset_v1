import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApAssetComponent } from '../ap-asset.component';
import { AssetResultListComponent } from 'src/app/parts/screen/asset/asset-result-list/asset-result-list.component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-ap-asset-vm-result',
  templateUrl: './ap-asset-vm-result.component.html',
  styleUrls: ['./ap-asset-vm-result.component.scss']
})
export class ApAssetVmResultComponent extends AbstractChildComponent<ApAssetComponent> implements OnInit {

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
   *  取得申請選択(個別申請)
   */
  radioApAssetVmForm: FormGroup;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild(AssetResultListComponent) resultList: AssetResultListComponent;

  constructor(
    private sessionService: SessionService,
    private fb: FormBuilder,
    private messageService: MessageService,
  ) {
    super();
  }

  /**
   * 画面の読み込み
   */
  ngOnInit() {
    this.radioApAssetVmForm = this.fb.group({
      createOption: [1],
    });
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

  /**
   *  取得申請選択(個別申請)
   */
  testchange(val = 1) {
    document.getElementById('wp-grid-asset-result-list').querySelectorAll('input[type=checkbox]').forEach((e) => {
      if (val === 2) {
        e.setAttribute('style', 'display:block');
      } else {
        e.setAttribute('style', 'display:none');
      }
    });
  }

  /**
   * 選択された資産項目
   */
  selectedeAssetItem() {
    const list: ApGetTanSR[] = this.resultList.gridAssetResultList.getCheckedRows();
    if (list.length === 0 || list === null || list === undefined) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    } else {
      this.parent.changeChild(this.parent.viewIndexApGetTanResultLine, {
        action: 'initLine',
        params: {
          datalist: this.resultList.gridAssetResultList.getCheckedRows()
        }
      });
    }
  }
}
