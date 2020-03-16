import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApGetIntRoutingModule } from './ap-get-int-routing.module';
import { ApGetIntComponent } from './ap-get-int.component';
import { ApGetIntSearchComponent } from './ap-get-int-search/ap-get-int-search.component';
import { ApGetIntResultComponent } from './ap-get-int-result/ap-get-int-result.component';

import { SharedModule } from '../../shared.module';
import { ApGetIntHistComponent } from './ap-get-int-hist/ap-get-int-hist.component';
import { ApGetIntApGetTanSearchComponent } from './ap-get-int-ap-get-tan-search/ap-get-int-ap-get-tan-search.component';
import { ApGetIntLineOtrCostComponent } from './ap-get-int-entry/ap-get-int-line-otr-cost/ap-get-int-line-otr-cost.component';
import { ApGetIntLineFldLineComponent } from './ap-get-int-entry/ap-get-int-line-fld-line/ap-get-int-line-fld-line.component';
import { ApGetIntEntryComponent } from './ap-get-int-entry/ap-get-int-entry.component';
import { ApGetIntApGetTanResultComponent } from './ap-get-int-ap-get-tan-result/ap-get-int-ap-get-tan-result.component';

@NgModule({
  declarations: [
    ApGetIntComponent,
    ApGetIntSearchComponent,
    ApGetIntResultComponent,
    ApGetIntHistComponent,
    ApGetIntEntryComponent,
    ApGetIntApGetTanSearchComponent,
    ApGetIntLineFldLineComponent,
    ApGetIntLineOtrCostComponent,
    ApGetIntApGetTanResultComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    ApGetIntRoutingModule
  ]
})
export class ApGetIntModule {
}
