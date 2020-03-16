import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LicenseRoutingModule } from './license-routing.module';
import { LicenseComponent } from './license.component';
import { SharedModule } from 'src/app/shared.module';
import { LicenseSearchComponent } from './license-search/license-search.component';
import { TermLicenseSearchComponent } from './term-license-search/term-license-search.component';
import { UpgradeLicenseSearchComponent } from './upgrade-license-search/upgrade-license-search.component';
import { LicenseHistComponent } from './license-hist/license-hist.component';
import { LicenseApGetTanSearchComponent } from './license-ap-get-tan-search/license-ap-get-tan-search.component';
import { LicenseApGetTanResultComponent } from './license-ap-get-tan-result/license-ap-get-tan-result.component';
import { LicenseApGetIntSearchComponent } from './license-ap-get-int-search/license-ap-get-int-search.component';
import { LicenseApGetIntResultComponent } from './license-ap-get-int-result/license-ap-get-int-result.component';
import { LicenseResultComponent } from './license-result/license-result.component';
import { TermLicenseResultComponent } from './term-license-result/term-license-result.component';
import { UpgradeLicenseResultComponent } from './upgrade-license-result/upgrade-license-result.component';

@NgModule({
  declarations: [LicenseComponent, LicenseSearchComponent, TermLicenseSearchComponent, UpgradeLicenseSearchComponent, LicenseHistComponent, LicenseApGetTanSearchComponent, LicenseApGetTanResultComponent, LicenseApGetIntSearchComponent, LicenseApGetIntResultComponent, LicenseResultComponent, TermLicenseResultComponent, UpgradeLicenseResultComponent ],
  imports: [
    CommonModule,
    LicenseRoutingModule,
    SharedModule
  ]
})
export class LicenseModule { }
