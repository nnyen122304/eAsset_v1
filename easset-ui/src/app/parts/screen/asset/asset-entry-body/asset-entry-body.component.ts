import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Asset } from '../../../../models/api/asset/asset.model';
import { LovDataEx } from '../../../../models/api/lov-data-ex';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from '../../../../services/session.service';

@Component({
  selector: 'app-asset-entry-body',
  templateUrl: './asset-entry-body.component.html',
  styleUrls: ['./asset-entry-body.component.scss']
})
export class AssetEntryBodyComponent implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  apAssetMode: true;

  @Input() assetForm: FormGroup;

  assetObj: Asset = new Asset();


  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
  ) {
  }

  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
  }

  /**
   * ステータス選択後処理
   *
   * @param status 選択ステータス情報
   *
   * @return void
   */
  changeApStatus(status: LovDataEx) {
    this.assetObj.apStatus = status.code;
    this.assetObj.apStatusName = status.value1;
  }

}
