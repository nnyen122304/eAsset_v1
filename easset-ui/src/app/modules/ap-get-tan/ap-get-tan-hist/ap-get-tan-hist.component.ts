import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { HistoryComponent } from 'src/app/parts/screen/history/history.component';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { ApGetTanComponent } from '../ap-get-tan.component';
import { ApGetTanService } from 'src/app/services/api/ap-get-tan.service';

@Component({
  selector: 'app-ap-get-tan-hist',
  templateUrl: './ap-get-tan-hist.component.html',
  styleUrls: ['./ap-get-tan-hist.component.scss']
})
export class ApGetTanHistComponent extends AbstractChildComponent<ApGetTanComponent> implements OnInit {

  /**
   * エンティティデータ
   */
  data;

  /**
   * VI LicenseSearchConditionComponent
   */
  @ViewChild(HistoryComponent) historyComponent: HistoryComponent;
  constructor(
    private apGetTanService: ApGetTanService,
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
      // this.data = param.params.data;
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
      this.apGetTanService.createApGetTanHistPdf(data.key, data.version)
        .subscribe(
          (resp: NonObjectResponse<string>) => {
            const fileID = resp.value;
            this.fileDownloadService.preview(fileID, 'application/pdf');
          }
        );
    }
  }
}
