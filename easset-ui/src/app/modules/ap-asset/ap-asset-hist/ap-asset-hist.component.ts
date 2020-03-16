import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from '../../../components/view-stack/abstract-child-component';
import { ApAssetComponent } from '../ap-asset.component';

@Component({
  selector: 'app-ap-asset-hist',
  templateUrl: './ap-asset-hist.component.html',
  styleUrls: ['./ap-asset-hist.component.scss']
})
export class ApAssetHistComponent extends AbstractChildComponent<ApAssetComponent> implements OnInit {

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
