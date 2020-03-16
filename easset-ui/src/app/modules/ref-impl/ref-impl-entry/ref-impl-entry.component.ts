import { Component, OnInit, ViewChild } from '@angular/core';
import { RefImplComponent } from '../ref-impl.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApGetTan } from 'src/app/models/api/ap-get-tan/ap-get-tan.model';
import { ApGetTanService } from 'src/app/services/api/ap-get-tan.service';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { FileUploadService } from 'src/app/services/api/file-upload.service';
import { SessionService } from 'src/app/services/session.service';
import { sprintf } from 'sprintf-js';
import { DatePipe } from '@angular/common';

/**
 * RefImpl入力コンポネント
 */

@Component({
  selector: 'app-ref-impl-entry',
  templateUrl: './ref-impl-entry.component.html',
  styleUrls: ['./ref-impl-entry.component.scss']
})
export class RefImplEntryComponent extends AbstractChildComponent<RefImplComponent> implements OnInit {

  /**
   * 取得申請データ
   */
  apGetTan: ApGetTan = new ApGetTan();

  /**
   * 画面オープン時のアクション
   */
  openAction: string;

  /**
   * 保存処理が実行されたかどうか
   */
  isSaved: boolean;

  constructor(private apGetTanService: ApGetTanService, private messageService: MessageService,
              private sessionService: SessionService,
              private fileDownloadService: FileDownloadService, private fileUploadService: FileUploadService,
              private datePipe: DatePipe) { super(); }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    /**
     * 画面切替初期処理(param:画面オープンパラメータ)
     */
    this.changeChildSubject.subscribe((param) => {
      // 変数初期化
      this.isSaved = false;

      // オープンアクションごとの初期処理
      this.openAction = param.action;
      switch (this.openAction) {
        case 'detail': // 検索結果の詳細表示
        case 'ref': // 別Window参照画面用
          this.initById(param.params.id); // 申請書IDで画面表示
          break;
        case 'eapp': // e申請用画面
          this.apGetTanService.getApGetTanEapp(param.params.eappId).subscribe(apGetTan => {
            if (apGetTan) {
              this.apGetTan =  apGetTan;
            } else {
              this.messageService.err(SystemMessage.Err.msg300001);
            }
          });
          break;
      }
    });
  }

  /**
   * 申請書IDで画面表示
   * @param id ID
   */
  private initById(id: string): void {
    this.apGetTanService.getApGetTan(id).subscribe(apGetTan => {
      if (apGetTan) {
        this.apGetTan =  apGetTan;
      } else {
        this.messageService.err(SystemMessage.Err.msg300001);
      }
    });
  }

  /**
   * 保存
   */
  save(): void {
    this.apGetTanService.updateApGetTan(this.parent.sessionInfo.loginUser.staffCode,
      this.parent.sessionInfo.currentAccessLevel, this.apGetTan).subscribe(() => {
        this.isSaved = true;
        this.initById(this.apGetTan.applicationId);
        this.messageService.info(SystemMessage.Info.msg100001);
    });
  }

  /**
   * 印刷
   */
  print(): void {
    this.apGetTanService.createApGetTanPdf([this.apGetTan.applicationId], true).subscribe((response) => {
      this.fileDownloadService.preview(response.value, 'application/pdf');
    });
  }

  /**
   * アップロード
   */
  upload(fileList: FileList): void {
    this.fileUploadService.upload(fileList).subscribe(response => {
      // tslint:disable-next-line: no-any
      const fileItem: any = fileList.item(0); // IE11では File型のフィールドlastModifiedに値が設定されておらず、lastModifiedDateにしか値が無いためanyとして取得
      this.messageService.err(sprintf(SystemMessage.Info.msg100000, response.value, fileItem.name, fileItem.size,
        this.datePipe.transform(fileItem.lastModifiedDate, 'yyyy/MM/dd HH:mm:ss')));
    });
  }

  /**
   * 戻る
   */
  goBack(): void {
    if (this.isSaved) { // 保存が実行されていたら一覧の行データを最新情報に
      this.apGetTanService.searchApGetTan(this.parent.sessionInfo.loginUser.staffCode, this.parent.sessionInfo.currentAccessLevel,
        {applicationId: this.apGetTan.applicationId}).subscribe(result => {
        this.parent.changeChild(this.parent.viewIndexResult, {action: 'detail', params: {rowData: result[0]}});
      });
    } else {
      this.parent.changeChild(this.parent.viewIndexResult);
    }
  }
}
