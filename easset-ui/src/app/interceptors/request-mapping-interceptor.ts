import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * リクエストマッピング用Interceptor
 */
@Injectable()
export class RequestMappingInterceptor implements HttpInterceptor {
  constructor() { }

  /**
   * マッピングが起きる時の処理
   * @param request HTTPリクエスト情報
   * @param next HTTPハンドラー
   */
  // tslint:disable-next-line: no-any
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // apiリクエストのHTTPヘッダー書き換え
    let req = request;

    if (request.url.match(/\/api\/v1\//)) {
      req = request.clone({
        setHeaders: {
          'Content-Type': 'application/json'
        },
      });
    }
    return next.handle(req);
  }
}
