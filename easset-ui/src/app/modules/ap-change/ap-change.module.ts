import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ApChangeRoutingModule} from './ap-change-routing.module';
import {ApChangeComponent} from './ap-change.component';
import { SharedModule } from 'src/app/shared.module';
import { ApChangeLicenseSearchComponent } from './ap-change-license-search/ap-change-license-search.component';
import { ApChangeHistComponent } from './ap-change-hist/ap-change-hist.component';
import { ApChangeAssetSearchComponent} from './ap-change-asset-search/ap-change-asset-search.component';
import { ApChangeEntryComponent } from './ap-change-entry/ap-change-entry.component';
import { LineCostSecListComponent } from './ap-change-entry/line-cost-sec-list/line-cost-sec-list.component';
import { ApChangeAssetResultComponent } from './ap-change-asset-result/ap-change-asset-result.component';
import { ApChangeLicenseResultComponent } from './ap-change-license-result/ap-change-license-result.component';
@NgModule({
  declarations: [
    ApChangeComponent,
    ApChangeLicenseSearchComponent,
    ApChangeHistComponent,
    ApChangeAssetSearchComponent,
    ApChangeEntryComponent,
    LineCostSecListComponent,
    ApChangeAssetResultComponent,
    ApChangeLicenseResultComponent
  ],
  imports: [
    CommonModule,
    ApChangeRoutingModule,
    SharedModule
  ],
  entryComponents: [LineCostSecListComponent]
})

export class ApChangeModule {
}
