import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApGetTanRoutingModule } from './ap-get-tan-routing.module';
import { ApGetTanComponent } from './ap-get-tan.component';
import { ApGetTanSearchComponent } from './ap-get-tan-search/ap-get-tan-search.component';
import { SharedModule } from '../../shared.module';
import { ApGetTanHistComponent } from './ap-get-tan-hist/ap-get-tan-hist.component';
import { ApGetTanAssetSearchComponent } from './ap-get-tan-asset-search/ap-get-tan-asset-search.component';
import { ApGetTanEntryComponent } from './ap-get-tan-entry/ap-get-tan-entry.component';
import { ApGetTanResultComponent } from './ap-get-tan-result/ap-get-tan-result.component';
import { ApGetTanAssetResultComponent } from './ap-get-tan-asset-result/ap-get-tan-asset-result.component';
@NgModule({
  declarations: [ApGetTanComponent, ApGetTanSearchComponent, ApGetTanHistComponent, ApGetTanAssetSearchComponent, ApGetTanEntryComponent, ApGetTanResultComponent, ApGetTanAssetResultComponent],
  imports: [
    CommonModule,
    ApGetTanRoutingModule,
    SharedModule
  ]
})
export class ApGetTanModule {
}
