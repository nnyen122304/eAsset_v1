import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { ApEndTanComponent } from '../ap-end-tan.component';

@Component({
  selector: 'app-ap-end-tan-hist',
  templateUrl: './ap-end-tan-hist.component.html',
  styleUrls: ['./ap-end-tan-hist.component.scss']
})
export class ApEndTanHistComponent extends AbstractChildComponent<ApEndTanComponent> implements OnInit {

  /**
   * エンティティデータ
   */
  data;
  constructor() {
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
