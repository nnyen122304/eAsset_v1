import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { ApEndLeComponent } from '../ap-end-le.component';

@Component({
  selector: 'app-ap-end-le-hist',
  templateUrl: './ap-end-le-hist.component.html',
  styleUrls: ['./ap-end-le-hist.component.scss']
})
export class ApEndLeHistComponent extends AbstractChildComponent<ApEndLeComponent> implements OnInit {

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
