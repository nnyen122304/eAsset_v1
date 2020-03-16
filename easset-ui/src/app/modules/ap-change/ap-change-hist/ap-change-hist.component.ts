import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { ApChangeComponent } from '../ap-change.component';

@Component({
  selector: 'app-ap-change-hist',
  templateUrl: './ap-change-hist.component.html',
  styleUrls: ['./ap-change-hist.component.scss']
})
export class ApChangeHistComponent extends AbstractChildComponent<ApChangeComponent> implements OnInit {

  /**
   * エンティティデータ
   */
  data;
  constructor(
  ) {
    super();
  }

  /**
   * Init
   */
  ngOnInit() {
    this.changeChildSubject.subscribe(param => {
      // TODO
      // this.data = param.params.data;
    });
  }

  /**
   * 戻る
   */
  back() {
    // TODO
    // this.parent.changeChild(this.parent.viewIndexEntry, { action: 'backFromHistory', params: {} });
  }
}
