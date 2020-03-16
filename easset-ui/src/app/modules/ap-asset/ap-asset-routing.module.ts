import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ApAssetComponent } from './ap-asset.component';

const routes: Routes = [{path: '', component: ApAssetComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApAssetRoutingModule { }
