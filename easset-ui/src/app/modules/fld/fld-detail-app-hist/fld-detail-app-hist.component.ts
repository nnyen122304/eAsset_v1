import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { FldComponent } from '../fld.component';

@Component({
  selector: 'app-fld-detail-app-hist',
  templateUrl: './fld-detail-app-hist.component.html',
  styleUrls: ['./fld-detail-app-hist.component.scss']
})
export class FldDetailAppHistComponent extends AbstractChildComponent<FldComponent> implements OnInit {

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
