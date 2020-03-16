import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LicenseAllocRoutingModule } from './license-alloc-routing.module';
import { LicenseAllocComponent } from './license-alloc.component';
import { LicenseAllocLicenseSearchComponent } from './license-alloc-license-search/license-alloc-license-search.component';
import { SharedModule } from 'src/app/shared.module';
import { LicenseAllocAssetSearchComponent } from './license-alloc-asset-search/license-alloc-asset-search.component';
import { LicenseAllocAssetResultComponent } from './license-alloc-asset-result/license-alloc-asset-result.component';
import { LicenseAllocLicenseResultComponent } from './license-alloc-license-result/license-alloc-license-result.component';

@NgModule({
  declarations: [LicenseAllocComponent, LicenseAllocLicenseSearchComponent, LicenseAllocAssetSearchComponent, LicenseAllocAssetResultComponent, LicenseAllocLicenseResultComponent],
  imports: [
    CommonModule,
    SharedModule,
    LicenseAllocRoutingModule,
  ]
})
export class LicenseAllocModule { }
