import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';

/**
 * ファイルアップロードAPIコール
 */
@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private servicePath = environment.uploadUrl; // アップロードルートパス
  constructor(private http: HttpClient) { }

  /**
   * ファイルアップロード
   * @param fileList fileインプットのfiles属性
   * @return ファイルID(一時領域にアップロードされたファイル)
   */
  upload(fileList: FileList): Observable<NonObjectResponse<string>> {
    const formData = new FormData();
    for (let i = 0; i < fileList.length; i++) {
      formData.append('upfile' + i, fileList.item(i), fileList.item(i).name);
    }
    return this.http.post<NonObjectResponse<string>>(this.servicePath, formData);
  }
}
