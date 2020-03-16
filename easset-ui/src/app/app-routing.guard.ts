import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionService } from './services/session.service';

@Injectable({
  providedIn: 'root'
})
export class AppRoutingGuard implements CanActivate {
  constructor(private sessionService: SessionService) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    // 直リンク出来ないように制御
    const sessionInfo = this.sessionService.getSessionInfo();
    let b = false;
    if (sessionInfo && sessionInfo.currentMenuId) {
      b = true;
    } else {
      console.log('ルーティングガード');
    }
    return b;
  }
}
