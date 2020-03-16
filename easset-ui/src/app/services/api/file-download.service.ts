import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

/**
 * ファイルダウンロードAPIコール
 */
@Injectable({
  providedIn: 'root'
})
export class FileDownloadService {
  private servicePath = environment.downloadUrl; // ダウンロードルートパス

  /**
   * ファイルダウンロード
   * @param fileId ファイルID
   * @param fileName 保存する際のファイル名
   * @param contentType contentType 省略した場合は、application/octet-stream
   * @param filePath filePath 省略した場合は、一時保存領域のファイル
   */
  download(fileId: string, fileName: string, contentType?: string, filePath?: string): void {
    console.log(fileId);
    // フォームコントロールを生成してリクエスト送信
    const form = document.createElement('form');
    document.body.appendChild(form);
    form.setAttribute('style', 'display: none');
    let input: HTMLInputElement;
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'fileId'; input.value = fileId;
    form.appendChild(input);
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'fileName'; input.value = fileName;
    form.appendChild(input);
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'contentDisposition'; input.value = 'attachment';
    form.appendChild(input);
    if (contentType) {
      input = document.createElement('input'); input.type = 'hidden'; input.name = 'contentType'; input.value = contentType;
      form.appendChild(input);
    }
    if (filePath) {
      input = document.createElement('input'); input.type = 'hidden'; input.name = 'filePath'; input.value = filePath;
      form.appendChild(input);
    }
    form.action = this.servicePath;
    form.method = 'POST';
    form.target = '_top';
    form.submit();
    form.parentNode.removeChild(form);
  }

  /**
   * ファイルプレビュー
   * @param fileId ファイルID
   * @param contentType contentType 省略した場合は、application/octet-stream
   * @param filePath filePath 省略した場合は、一時保存領域のファイル
   */
  preview(fileId: string, contentType?: string, filePath?: string): void {
    // 別Windowオープン
    const windowName = String(new Date().getTime());
    window.open('about:blank', windowName, '');

    // フォームコントロールを生成してリクエスト送信
    const form = document.createElement('form');
    document.body.appendChild(form);
    form.setAttribute('style', 'display: none');
    let input: HTMLInputElement;
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'fileId'; input.value = fileId;
    form.appendChild(input);
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'contentDisposition'; input.value = 'inline';
    form.appendChild(input);
    if (contentType) {
      input = document.createElement('input'); input.type = 'hidden'; input.name = 'contentType'; input.value = contentType;
      form.appendChild(input);
    }
    if (filePath) {
      input = document.createElement('input'); input.type = 'hidden'; input.name = 'filePath'; input.value = filePath;
      form.appendChild(input);
    }
    form.action = this.servicePath;
    form.target = windowName;
    form.submit();
    form.parentNode.removeChild(form);
  }
}
