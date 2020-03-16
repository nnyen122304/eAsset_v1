import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { HistoryComponent } from 'src/app/parts/screen/history/history.component';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { ApGetIntComponent } from '../ap-get-int.component';
import { ApGetIntService } from 'src/app/services/api/ap-get-int.service';

@Component({
  selector: 'app-ap-get-int-hist',
  templateUrl: './ap-get-int-hist.component.html',
  styleUrls: ['./ap-get-int-hist.component.scss']
})
export class ApGetIntHistComponent extends AbstractChildComponent<ApGetIntComponent> implements OnInit {

  /**
   * エンティティデータ
   */
  data;

  /**
   * VI LicenseSearchConditionComponent
   */
  @ViewChild(HistoryComponent) historyComponent: HistoryComponent;
  constructor(
    private apGetIntService: ApGetIntService,
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
      const index = data.key.indexOf(' ');
      if (0 < index) {
        this.apGetIntService.createApGetIntHistPdf(data.key.substring(0, index), data.key.substring(index + 1), data.version)
          .subscribe(
            (resp: NonObjectResponse<string>) => {
              const fileID = resp.value;
              this.fileDownloadService.preview(fileID, 'application/pdf');
            }
          );
      }
    }
  }
}
