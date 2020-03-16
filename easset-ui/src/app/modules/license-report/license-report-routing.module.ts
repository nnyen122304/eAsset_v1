import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LicenseReportComponent } from './license-report.component';

const routes: Routes = [{path: '', component: LicenseReportComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LicenseReportRoutingModule { }
