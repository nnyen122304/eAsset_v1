import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { finalize  } from 'rxjs/operators';
import { SessionService } from './../services/session.service';

/**
 * Loading表示用Interceptor
 */
@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  constructor(private sessionService: SessionService) { }

  /**
   * HTTPリクエスト中にLoading表示を行う。
   * @param request HttpRequest
   * @param next HttpHandler
   */
  // tslint:disable-next-line: no-any
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.sessionService.showLoading();
    const req = request.clone();
    return next.handle(req).pipe(
      finalize(() => {
        this.sessionService.hideLoading();
      })
    );
  }
}
