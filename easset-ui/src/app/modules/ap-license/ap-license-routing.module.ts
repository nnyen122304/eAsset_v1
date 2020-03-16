import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ApLicenseComponent } from './ap-license.component';

const routes: Routes = [{path: '', component: ApLicenseComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApLicenseRoutingModule { }
