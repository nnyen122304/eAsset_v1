import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApAssetComponent } from '../ap-asset.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { SystemMessage } from 'src/app/const/system-message';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-ap-asset-search',
  templateUrl: './ap-asset-search.component.html',
  styleUrls: ['./ap-asset-search.component.scss']
})
export class ApAssetSearchComponent extends AbstractChildComponent<ApAssetComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  constructor(
    private sessionService: SessionService,
    private apAssetService: ApAssetService,
    private messageService: MessageService) {
    super();
  }

  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        // ...
      }
    });
  }

  search() {
    const searchParam: any = {
      assetId: '',
      eappId: null,
      apStatus: '1',
      apDateFrom: '',
      apDateTo: '',
      apStaffCode: '',
      apStaffName: '',
      getApplicationId: 'AH1401651',
      registDateFrom: '',
      registDateTo: '',
      notCompleteFlag: '',
      astName: '',
      astCategory1Code: '',
      astCategory1Name: '',
      astCategory2Code: '',
      astCategory2Name: '',
      astCategory2Plural: '',
      astMakerCode: '',
      astMakerName: '',
      astMakerModel: '',
      astSerialCode: '',
      astSerialCodePlural: '',
      astGetType: '',
      astGetTypeName: '',
      astAssetType: '',
      astAssetTypeName: '',
      astAssetTypePlural: '',
      astHolderCode: '',
      astHolderName: '',
      astManageType: '',
      astManageTypeName: '',
      astManageTypePlural: '',
      astOir: '',
      astOirPlural: '',
      netMacAddress1: '',
      netMacAddress2: '',
      netMacAddress3: '',
      netMacAddress4: '',
      netMacAddress5: '',
      netMacAddress6: '',
      netIpAddress1: '',
      netIpAddress2: '',
      netIpAddress3: '',
      netIpAddress4: '',
      netHostName: '',
      netHostNamePlural: '',
      holCompanyCode: '',
      holCompanyName: '',
      holSectionCode: '',
      holSectionName: '',
      includeSectionHierarchyFlag: '0',
      holStaffCode: '',
      holStaffName: '',
      holOfficeCode: '',
      holOfficeName: '',
      holOfficeRoomNum: '',
      holGetStaffCode: '',
      holGetStaffName: '',
      useStaffCode: '',
      useStaffName: '',
      dscDescription: '',
      dscAttribute: '',
      dscAttribute1: '',
      dscAttribute2: '',
      dscAttribute3: '',
      dscAttribute4: '',
      dscAttribute5: '',
      dscAttribute6: '',
      dscAttribute7: '',
      dscAttribute8: '',
      dscAttribute9: '',
      dscAttribute10: '',
      dscAttribute11: '',
      dscAttribute12: '',
      dscAttribute13: '',
      dscAttribute14: '',
      dscAttribute15: '',
      dscAttribute16: '',
      dscAttribute17: '',
      dscAttribute18: '',
      dscAttribute19: '',
      dscAttribute20: '',
      invInCompanyActualFlag: '',
      invSealIssueFlag: '',
      invSealIssueStatus: '',
      invSealIssueDateFrom: '',
      invSealIssueDateTo: '',
      invFlag: '',
      invSearchDateFrom: '',
      invSearchDateTo: ''
    };

    this.apAssetService.searchAsset(
      this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel,
      searchParam, true).subscribe(
      (resp: AssetSR[]) => {
        if (resp.length > 0) {
          this.parent.changeChild(this.parent.viewIndexResult, {
            action: 'initResult',
            params: {
              dataList: resp,
              searchParam: searchParam
            }
          });
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
      }
    );
  }

}
