import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApLicenseRoutingModule } from './ap-license-routing.module';
import { ApLicenseComponent } from './ap-license.component';
import { SharedModule } from 'src/app/shared.module';
import { ApLicenseSearchComponent } from './ap-license-search/ap-license-search.component';
import { ApLicenseApGetTanSearchComponent } from './ap-license-ap-get-tan-search/ap-license-ap-get-tan-search.component';
import { ApLicenseHistComponent } from './ap-license-hist/ap-license-hist.component';
import { ApLicenseApGetIntResultComponent } from './ap-license-ap-get-int-result/ap-license-ap-get-int-result.component';
import { ApLicenseApGetIntSearchComponent } from './ap-license-ap-get-int-search/ap-license-ap-get-int-search.component';
import { ApLicenseApGetTanResultComponent } from './ap-license-ap-get-tan-result/ap-license-ap-get-tan-result.component';
import { ApLicenseResultComponent } from './ap-license-result/ap-license-result.component';

@NgModule({
  declarations: [
    ApLicenseComponent,
    ApLicenseSearchComponent,
    ApLicenseApGetTanSearchComponent,
    ApLicenseHistComponent,
    ApLicenseApGetIntSearchComponent,
    ApLicenseApGetIntResultComponent,
    ApLicenseApGetTanResultComponent,
    ApLicenseResultComponent
  ],
  exports: [
    ApLicenseApGetIntResultComponent
  ],
  imports: [
    CommonModule,
    ApLicenseRoutingModule,
    SharedModule
  ]
})
export class ApLicenseModule {
}
