import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LicenseReportRoutingModule } from './license-report-routing.module';
import { LicenseReportComponent } from './license-report.component';
import { LicenseReportAllocLicenseComponent } from './license-report-alloc-license/license-report-alloc-license.component';
import { SharedModule } from '../../shared.module';
import { LicenseReportUpgradeComponent } from './license-report-upgrade/license-report-upgrade.component';
import { LicenseReportAssetComponent } from './license-report-asset/license-report-asset.component';
@NgModule({
  declarations: [LicenseReportComponent, LicenseReportAllocLicenseComponent, LicenseReportUpgradeComponent, LicenseReportAssetComponent],
  imports: [
    CommonModule,
    LicenseReportRoutingModule,
    SharedModule,
  ]
})
export class LicenseReportModule { }
