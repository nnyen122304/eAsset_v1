import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApBgnIntRoutingModule } from './ap-bgn-int-routing.module';
import { SharedModule } from 'src/app/shared.module';
import { ApBgnIntComponent } from './ap-bgn-int.component';
import { ApBgnIntEntryComponent } from './ap-bgn-int-entry/ap-bgn-int-entry.component';
import { LineProfEstListComponent } from './ap-bgn-int-entry/line-prof-est-list/line-prof-est-list.component';
import { LineCostSecListComponent } from './ap-bgn-int-entry/line-cost-sec-list/line-cost-sec-list.component';
import { ApBgnIntHistComponent } from './ap-bgn-int-hist/ap-bgn-int-hist.component';
import { ApBgnIntApGetIntSearchComponent } from './ap-bgn-int-ap-get-int-search/ap-bgn-int-ap-get-int-search.component';
import { ApBgnIntResultComponent } from './ap-bgn-int-result/ap-bgn-int-result.component';
import { ApBgnIntApGetIntResultComponent } from './ap-bgn-int-ap-get-int-result/ap-bgn-int-ap-get-int-result.component';

@NgModule({
  declarations: [
    ApBgnIntComponent,
    ApBgnIntEntryComponent,
    LineProfEstListComponent,
    LineCostSecListComponent,
    ApBgnIntHistComponent,
    ApBgnIntApGetIntSearchComponent,
    ApBgnIntResultComponent,
    ApBgnIntApGetIntResultComponent
  ],
  imports: [
    CommonModule,
    ApBgnIntRoutingModule,
    SharedModule
  ],
  entryComponents: [LineProfEstListComponent, LineCostSecListComponent]
})
export class ApBgnIntModule {
}
