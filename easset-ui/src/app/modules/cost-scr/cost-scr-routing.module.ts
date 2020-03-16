import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CostScrComponent } from './cost-scr.component';

const routes: Routes = [
  { path: '', component: CostScrComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CostScrRoutingModule { }
