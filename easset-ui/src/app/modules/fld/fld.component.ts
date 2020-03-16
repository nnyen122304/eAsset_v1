import { Component, OnInit } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ActivatedRoute } from '@angular/router';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';

@Component({
  selector: 'app-fld',
  templateUrl: './fld.component.html',
  styleUrls: ['./fld.component.scss']
})
export class FldComponent extends AbstractMainComponent implements OnInit {
  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 検索画面Index
   */
  readonly viewIndexSearch = 0;

  /**
   * 登録画面Index
   */
  readonly viewIndexResult = 1;

  /**
   * 登録画面Index
   */
  readonly viewIndexEntry = 2;

  /**
   * 履歴画面Index: 固定資産照会_履歴
   */
  readonly viewIndexHist = 3;

  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
    super(sessionService);
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    // メニューに応じた初期処理、子画面表示
    this.updateView();
  }

  /**
   * メニューに応じた初期処理、子画面表示
   */
  updateView() {
    const paramMap = this.route.snapshot.paramMap; // オープンパラメータ
  }
}
