import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LicenseAllocComponent } from './license-alloc.component';

const routes: Routes = [{path: '', component: LicenseAllocComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LicenseAllocRoutingModule { }
