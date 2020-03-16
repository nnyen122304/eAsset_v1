import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { LicenseComponent } from '../license.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { DownloadOptionPropComponent } from 'src/app/parts/option/download-option-prop/download-option-prop.component';
import { LicenseService } from 'src/app/services/api/license.service';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';
import { LicenseSearchConditionComponent } from 'src/app/parts/screen/license/license-search-condition/license-search-condition.component';
import { LicenseSR } from 'src/app/models/api/license/license-sr.model';
import { SystemMessage } from 'src/app/const/system-message';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-term-license-search',
  templateUrl: './term-license-search.component.html',
  styleUrls: ['./term-license-search.component.scss']
})
export class TermLicenseSearchComponent extends AbstractChildComponent<LicenseComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * 選択されているメニュー
   */
  currentMenuId: string;

  /**
   * ログイン社員番号
   */
  loginStaffCode: string;

  /**
   * アクション
   */
  action = '';

  /**
   * 検索条件
   */
  searchParamSC: LicenseSC = new LicenseSC();

  /**
   * コンポーネントの参照
   */
  @ViewChild(DownloadOptionPropComponent, null) popupDownload: DownloadOptionPropComponent;

  /**
   * コンポーネントの参照
   */
  @ViewChild(LicenseSearchConditionComponent) licSearchCondition: LicenseSearchConditionComponent;

  constructor(
    private sessionService: SessionService,
    private licenseService: LicenseService,
    private messageService: MessageService
  ) {
    super();
  }

  /**
   * コンポネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.loginStaffCode = this.sessionInfo.loginUser.staffCode;
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initApLicense') {
        this.action = 'initApLicense';
      } else if (param.action === 'initLicense') {
        this.action = 'initLicense';
      }
    });
  }

  /**
   * 戻る
   */
  back() {
    if (this.action === 'initApLicense') {
      this.parent.changeChild(this.parent.viewIndexEntry, {action: 'backFromLine', params: {}});
    } else {
      this.parent.changeChild(this.parent.viewIndexEntry, {action: 'backFromLine', params: {}});
    }
  }

  /**
   * 検索処理
   */
  search() {
    this.searchParamSC = this.licSearchCondition.getSearch();
    this.licenseService.searchLicense(this.loginStaffCode, this.accessLevel, this.searchParamSC, false).subscribe(
      (resp: LicenseSR[]) => {
        if (resp.length > 0) {
          if (this.action === 'initApLicense') {
            this.parent.changeChild(this.parent.viewIndexTermSearch, {
              action: 'initResult',
              params: {
                dataList: resp
              },
            });
          } else {
            this.parent.changeChild(this.parent.viewIndexTermSearch, {
              action: 'initResult',
              params: {
                dataList: resp
              },
            });
          }
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
          return;
        }
      }
    );
  }

  /**
   * 検索をリセット
   */
  resetSearch() {
    this.licSearchCondition.resetSearch();
  }
}
