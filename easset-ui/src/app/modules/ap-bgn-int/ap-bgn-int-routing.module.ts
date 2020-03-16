import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ApBgnIntComponent} from './ap-bgn-int.component';

const routes: Routes = [{path: '', component: ApBgnIntComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApBgnIntRoutingModule {
}
