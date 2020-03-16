import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {ApChangeComponent} from './ap-change.component';

const routes: Routes = [{path: '', component: ApChangeComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ApChangeRoutingModule {
}
