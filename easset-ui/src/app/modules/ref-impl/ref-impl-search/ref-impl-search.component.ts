import { Component, OnInit } from '@angular/core';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { ApGetTanService } from 'src/app/services/api/ap-get-tan.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { RefImplComponent } from '../ref-impl.component';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { SessionService } from 'src/app/services/session.service';

/**
 * リファレンス実装-検索画面
 */
@Component({
  selector: 'app-ref-impl-search',
  templateUrl: './ref-impl-search.component.html',
  styleUrls: ['./ref-impl-search.component.scss']
})
export class RefImplSearchComponent extends AbstractChildComponent<RefImplComponent> implements OnInit {
  /**
   * 検索条件
   */
  apGetTanSC: ApGetTanSC = new ApGetTanSC();

  constructor(private sessionService: SessionService, private apGetTanService: ApGetTanService, private fileDownloadService: FileDownloadService) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    /**
     * 画面切替初期処理(param:画面オープンパラメータ)
     */
    this.changeChildSubject.subscribe((param) => {
      if (param) {
        switch (param.action) {
          case 'init': // 初期処理
            // 検索条件固定・初期値設定
            this.apGetTanSC.apCompanyCode = this.parent.sessionInfo.loginCompanyCode;
            this.apGetTanSC.apCompanyName = this.parent.sessionInfo.loginCompanyName;
            break;
        }
      }
    });
  }

  /**
   * 検索
   */
  search(): void {
    this.apGetTanService.searchApGetTan(this.parent.sessionInfo.loginUser.staffCode, this.parent.sessionInfo.currentAccessLevel,
      this.apGetTanSC).subscribe((response: ApGetTanSR[]) => {
      this.parent.changeChild(this.parent.viewIndexResult, {action: 'search', params: {resultData: response}});
    });
  }

  /**
   * ダウンロード
   */
  download(): void {
    this.apGetTanService.createApGetTanCsv(this.parent.sessionInfo.loginUser.staffCode, this.parent.sessionInfo.currentAccessLevel,
      this.apGetTanSC).subscribe((response: NonObjectResponse<string>) => {
      this.fileDownloadService.download(response.value, 'test.csv', 'text/csv');
    });
  }
}
