import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

/**
 * レスポンスマッピング用Interceptor
 */
@Injectable()
export class ResponseMappingInterceptor implements HttpInterceptor {
  /**
   * 日付文字列正規表現
   */
  static readonly iso8601 = /^\d{4,5}-\d\d-\d\dT\d\d:\d\d:\d\d(\.\d+)?(([+-]\d\d:\d\d)|Z)?$/i;
  /**
   * JSONレスポンスの日付文字列→日付オブジェクト変換
   * @param input JSONレスポンス
   */
  // tslint:disable-next-line: no-any
  static converStringToDate(input: any): void {
    if (typeof input !== 'object') {
      return input;
    }
    for (const key in input) {
      if (!input.hasOwnProperty(key)) {
        continue;
      }
      const value = input[key];
      if (typeof value === 'string') {
        const match = value.match(ResponseMappingInterceptor.iso8601);
        if (match) {
          const milliseconds = Date.parse(match[0]);
          if (!isNaN(milliseconds)) {
            input[key] = new Date(milliseconds);
          }
        }
      } else if (typeof value === 'object') {
        this.converStringToDate(value);
      }
    }
  }

  constructor() { }

  /**
   * JSONレスポンスの内部に含まれる日付文字列項目のデータを日付型のオブジェクトに変換する
   * @param request HttpRequest
   * @param next HttpHandler
   */
  // tslint:disable-next-line: no-any
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const req = request.clone();
    return next.handle(req).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {
          if (event.headers.get('Content-Type') === 'application/json') {
            ResponseMappingInterceptor.converStringToDate(event.body);
          }
        }
      })
    );
  }
}
