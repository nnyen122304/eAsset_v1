import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CostScrRoutingModule } from './cost-scr-routing.module';
import { SharedModule } from 'src/app/shared.module';
import { CostScrComponent } from './cost-scr.component';
import { CostScrCreateDataComponent } from './cost-scr-create-data/cost-scr-create-data.component';
import { CostScrListComponent } from './cost-scr-list/cost-scr-list.component';
import { CostScrLineComponent } from './cost-scr-list/cost-scr-line/cost-scr-line.component';
import { OpenCloseSwitchingComponent } from './cost-scr-create-data/open-close-switching/open-close-switching.component';

@NgModule({
  declarations: [
    CostScrComponent,
    CostScrCreateDataComponent,
    OpenCloseSwitchingComponent,
    CostScrListComponent,
    CostScrLineComponent
  ],
  imports: [
    CommonModule,
    CostScrRoutingModule,
    SharedModule
  ]
})
export class CostScrModule { }
