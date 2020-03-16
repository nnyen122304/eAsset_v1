import { Input } from '@angular/core';
import { AbstractMainComponent } from './abstract-main-component';
import { Subject } from 'rxjs';

/**
 * 機能画面のスーパークラス
 * @param <T> 機能メイン(親)画面クラスを指定
 */
export abstract class AbstractChildComponent<T extends AbstractMainComponent> {
  /**
   * 親コンポーネント参照
   */
  @Input() parent: T;

  /**
   * 画面表示時イベント補足
   * ngOnInit内で以下のようにsubscribeを設定する。
   * changeChildSubject.subscribe(param => {
   *   // 画面初期処理(paramはchangeChildで指定されたパラメータオブジェクト)
   * });
   */
  // tslint:disable-next-line: no-any
  changeChildSubject: Subject<{action?: string, params?: {[param: string]: any}}>
    // tslint:disable-next-line: no-any
    = new Subject<{action?: string, params?: {[param: string]: any}}>();
}
