import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { ApEndLeComponent } from '../../ap-end-le/ap-end-le.component';

@Component({
  selector: 'app-ap-end-re-hist',
  templateUrl: './ap-end-re-hist.component.html',
  styleUrls: ['./ap-end-re-hist.component.scss']
})
export class ApEndReHistComponent extends AbstractChildComponent<ApEndLeComponent> implements OnInit {

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
