import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ApEndReComponent} from './ap-end-re.component';

const routes: Routes = [{path: '', component: ApEndReComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApEndReRoutingModule {
}
