import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { ApLicenseComponent } from '../ap-license.component';

@Component({
  selector: 'app-ap-license-hist',
  templateUrl: './ap-license-hist.component.html',
  styleUrls: ['./ap-license-hist.component.scss']
})
export class ApLicenseHistComponent extends AbstractChildComponent<ApLicenseComponent> implements OnInit {

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
