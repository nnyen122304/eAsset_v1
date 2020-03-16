import { Component, ViewChild, ElementRef, Output, EventEmitter, Input } from '@angular/core';
import { FileUploadService } from 'src/app/services/api/file-upload.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';

@Component({
  selector: 'app-file-upload-selection',
  templateUrl: './file-upload-selection.component.html',
  styleUrls: ['./file-upload-selection.component.scss']
})

export class FileUploadSelectionComponent {

  /**
   * ファイル
   */
  file = {
    fileId: '',
    fileName: ''
  }

  /**
   * ボタン名
   */
  @Input() btnText = 'ファイル選択';

  @Input() isIcon = false;

  /**
   * ラベル名
   */
  @Input() labelText = '一括入力CSVファイル：';

  /**
   * 目に見える入力
   */
  @Input() visibleInput = false;

  /**
   * データ選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ファイルアップロードコントロール Ref
   */
  @ViewChild('fileSelectCSV') fileSelectCSV: ElementRef;

  constructor(
    private fileUploadService: FileUploadService,
  ) {
  }

  /**
   * ポップアップを開く
   */
  open() {
    this.fileSelectCSV.nativeElement.value = '';
    this.fileSelectCSV.nativeElement.click();
  }

  /**
   * ファイル選択後処理
   * @param files ファイル情報
   */
  fileSelected(files: FileList) {
    if (!files.length || files.length <= 0) {
      return;
    }

    const selectedFile = files[0];
    // Call service for upload file
    this.fileUploadService.upload(files)
      .subscribe(
        (fileId: NonObjectResponse<string>) => {
          this.fileSelectCSV.nativeElement.value = '';
          this.file = {
            ...this.file, ...{
              fileId: fileId.value,
              fileName: selectedFile.name
            }
          };
          // Emit data
          this.select.emit(this.file);
        }
      );
  }
}
