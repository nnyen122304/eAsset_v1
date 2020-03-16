import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MessageService } from '../services/message.service';

/**
 * HTTPレスポンスエラー捕捉用Interceptor
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private messageService: MessageService) {}

  /**
   * エラーが発生した際の処理
   * @param request HTTPリクエスト情報
   * @param next HTTPハンドラー
   */
  // tslint:disable-next-line: no-any
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const req = request.clone();
    return next.handle(req).pipe(
      catchError((res: HttpErrorResponse) => {
        if (res.status === 500 || res.status === 400) {
          if (res.error) {
            if (res.error.message || res.error.value) {
              const messages = (res.error.message) ? res.error.message.split('$$$$$') :  res.error.value;
              for (let i = 0; i < messages.length; i++) {
                if (messages[i].length === 0) {
                  messages.splice(i, 1);
                }
              }
              this.messageService.err(messages);
            } else {
              this.messageService.err(res.message);
            }
          }
        }
        return throwError(res);
      })
    );
  }
}
