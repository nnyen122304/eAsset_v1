import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { ApBgnIntComponent } from '../ap-bgn-int.component';
import { ApBgnInt } from '../../../models/api/ap-bgn-int/ap-bgn-int.model';
import { HistoryComponent } from 'src/app/parts/screen/history/history.component';
import { ApBgnIntService } from 'src/app/services/api/ap-bgn-int.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';

@Component({
  selector: 'app-ap-bgn-int-hist',
  templateUrl: './ap-bgn-int-hist.component.html',
  styleUrls: ['./ap-bgn-int-hist.component.scss']
})
export class ApBgnIntHistComponent extends AbstractChildComponent<ApBgnIntComponent> implements OnInit {

  /**
   * エンティティデータ
   */
  item: ApBgnInt;

  /**
   * VI LicenseSearchConditionComponent
   */
  @ViewChild(HistoryComponent) historyComponent: HistoryComponent;
  constructor(
    private apBgnIntService: ApBgnIntService,
    private fileDownloadService: FileDownloadService,
  ) {
    super();
  }

  /**
   * Init
   */
  ngOnInit() {
    this.changeChildSubject.subscribe(param => {
      // TODO
      // this.item = param.params.data;
    });
  }

  /**
   * 戻る
   */
  back() {
    // TODO
    // this.parent.changeChild(this.parent.viewIndexEntry, { action: 'backFromHistory', params: {} });
  }

  /**
   * 印刷
   */
  print() {
    // TODO
    const data = this.historyComponent.selectedData();
    if (data !== null) {
      this.apBgnIntService.createApBgnIntHistPdf(data.key, data.version)
        .subscribe(
          (resp: NonObjectResponse<string>) => {
            const fileID = resp.value;
            this.fileDownloadService.preview(fileID, 'application/pdf');
          }
        );
    }
  }
}
