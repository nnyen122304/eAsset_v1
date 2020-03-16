import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { LicenseComponent } from '../license.component';
import { SessionInfo } from 'src/app/models/session-info';
import { LicenseSR } from 'src/app/models/api/license/license-sr.model';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';
import { LicenseResultListComponent } from 'src/app/parts/screen/license/license-result-list/license-result-list.component';
import { DownloadOptionPropComponent } from 'src/app/parts/option/download-option-prop/download-option-prop.component';
import { SessionService } from 'src/app/services/session.service';
import { LicenseService } from 'src/app/services/api/license.service';
import { DatePipe } from '@angular/common';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { MessageService } from 'src/app/services/message.service';
import { FormBuilder } from '@angular/forms';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { SystemMessage } from 'src/app/const/system-message';
import { License } from 'src/app/models/api/license/license.model';

@Component({
  selector: 'app-license-result',
  templateUrl: './license-result.component.html',
  styleUrls: ['./license-result.component.scss']
})
export class LicenseResultComponent extends AbstractChildComponent<LicenseComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * リスト
   */
  licenseSRList: LicenseSR[] = [];

  /**
   * 検索条件
   */
  licenseSCSearch: LicenseSC = {};

  /**
   * 検索
   */
  searchLicenseId = '';

  deleteObj = {
    deleteDate: new Date(),
    deleteReason: '',
  };

  @ViewChild(LicenseResultListComponent) resultList: LicenseResultListComponent;
  @ViewChild(DownloadOptionPropComponent, null) downloadOptionPropComponent: DownloadOptionPropComponent;

  constructor(
    private sessionService: SessionService,
    private licenseService: LicenseService,
    private datePipe: DatePipe,
    private fileDownloadService: FileDownloadService,
    private messageService: MessageService,
    private fb: FormBuilder
  ) {
    super();
  }

  /**
   * ダウンロードウィンドウ
   */
  openOptionDownload(event) {
    this.downloadOptionPropComponent.open(event);
  }

  /**
   * コンポネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.licenseSRList = param.params.dataList;
        this.resultList.deleteFlagInput = this.licenseSCSearch.deleteFlag;
        if (this.sessionInfo.currentMenuId === '02050') {
          this.resultList.checkBoxVisible = true;
        } else {
          this.resultList.checkBoxVisible = false;
        }
        if (this.licenseSCSearch.deleteFlag === '1') {
          this.resultList.deleteColumnVisible = true;
        } else {
          this.resultList.deleteColumnVisible = false;
        }
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
   * 検索画面に戻る
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexSearch, {
      action: 'backSearch',
      params: {
        searchParam: this
      },
    });
  }

  /**
   * アクセスレベル
   */
  getAccessLevel() {
    let accessLevel = 'category';
    switch (this.sessionInfo.currentAccessLevel) {
      case 'S':
        accessLevel = 'admin';
        break;
      case 'C':
        accessLevel = 'general';
        break;
      case 'B':
        accessLevel = 'asset_manager';
        break;
      default:
        accessLevel = 'category';
        break;
    }

    return accessLevel;
  }

  /**
   * ダウンロード
   */
  download(data) {
    const item = data.item ? data.item : [];
    this.licenseSCSearch.dowloadLineItem = data.lineItem ? data.lineItem : '';
    this.licenseService.createLicenseCsv(
      this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel,
      item,
      this.licenseSCSearch,
      true).subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          const fileName = 'ライセンス検索結果_ ' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          this.fileDownloadService.download(fileId, fileName, contentType);
        });
  }

  /**
   * 詳細に行く
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
   * 探している
   */
  search() {
    if (this.searchLicenseId === null || this.searchLicenseId === '') {
      this.messageService.info(SystemMessage.Err.msg30030);
      return;
    }
    this.licenseSCSearch.licenseId = this.searchLicenseId;
    this.licenseService.searchLicense(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, this.licenseSCSearch, false).subscribe(
      (resp: LicenseSR[]) => {
        if (resp.length > 0) {
          this.licenseSRList = resp;
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
          return;
        }
      });
  }

  /**
   * 削除する
   */
  delete() {
    const list: License[] = this.resultList.gridLicenseResultList.getCheckedRows();
    if (this.deleteObj.deleteDate === null || this.deleteObj.deleteReason === '' || this.resultList.gridLicenseResultList.getCheckedRows() === null) {
      this.messageService.warn(SystemMessage.Err.msg300002);
      return;
    }
    this.licenseService.deleteLicenseLogical(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, new Date(this.deleteObj.deleteDate), this.deleteObj.deleteReason, list).subscribe();
    this.resultList.gridLicenseResultList.refresh();
  }
}
