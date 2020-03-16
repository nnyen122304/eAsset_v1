import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from 'src/app/shared.module';
import { ApAssetRoutingModule } from './ap-asset-routing.module';
import { ApAssetComponent } from './ap-asset.component';
import { ApAssetApGetTanSearchComponent } from './ap-asset-ap-get-tan-search/ap-asset-ap-get-tan-search.component';
import { ApAssetHistComponent } from './ap-asset-hist/ap-asset-hist.component';
import { ApAssetVmSearchComponent } from '../ap-asset/ap-asset-vm-search/ap-asset-vm-search.component';
import { ApAssetApGetTanResultComponent } from './ap-asset-ap-get-tan-result/ap-asset-ap-get-tan-result.component';
import { ApAssetResultComponent } from './ap-asset-result/ap-asset-result.component';
import { ApAssetVmResultComponent } from './ap-asset-vm-result/ap-asset-vm-result.component';
import { ApAssetEntryComponent } from './ap-asset-entry/ap-asset-entry.component';
import { ApAssetApGetTanResultLineComponent } from './ap-asset-ap-get-tan-result-line/ap-asset-ap-get-tan-result-line.component';
import { ApAssetSearchComponent } from './ap-asset-search/ap-asset-search.component';

@NgModule({
  declarations: [
    ApAssetComponent,
    ApAssetApGetTanSearchComponent,
    ApAssetHistComponent,
    ApAssetVmSearchComponent,
    ApAssetApGetTanResultComponent,
    ApAssetApGetTanResultLineComponent,
    ApAssetResultComponent,
    ApAssetVmResultComponent,
    ApAssetEntryComponent,
    ApAssetSearchComponent
  ],
  imports: [
    CommonModule,
    ApAssetRoutingModule,
    SharedModule,
  ]
})
export class ApAssetModule {
}
