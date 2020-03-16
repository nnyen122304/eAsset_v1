import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ApEndLeComponent} from './ap-end-le.component';

const routes: Routes = [{path: '', component: ApEndLeComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApEndLeRoutingModule {
}
