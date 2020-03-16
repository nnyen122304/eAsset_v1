import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {ApGetTanComponent} from './ap-get-tan.component';

const routes: Routes = [{path: '', component: ApGetTanComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApGetTanRoutingModule {
}
