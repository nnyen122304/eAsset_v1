import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { LicenseComponent } from '../license.component';

@Component({
  selector: 'app-license-hist',
  templateUrl: './license-hist.component.html',
  styleUrls: ['./license-hist.component.scss']
})
export class LicenseHistComponent extends AbstractChildComponent<LicenseComponent> implements OnInit {

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
