import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ApGetIntComponent} from './ap-get-int.component';

const routes: Routes = [{path: '', component: ApGetIntComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ApGetIntRoutingModule {
}
