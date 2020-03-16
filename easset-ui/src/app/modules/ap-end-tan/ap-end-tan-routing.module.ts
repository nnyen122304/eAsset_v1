import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ApEndTanComponent} from './ap-end-tan.component';

const routes: Routes = [{path: '', component: ApEndTanComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApEndTanRoutingModule {
}
