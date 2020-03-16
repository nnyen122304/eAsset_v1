import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ApEndReRoutingModule} from './ap-end-re-routing.module';
import {ApEndReComponent} from './ap-end-re.component';
import { ApEndReHistComponent } from './ap-end-re-hist/ap-end-re-hist.component';
import { SharedModule } from 'src/app/shared.module';
import { ApEndReAstSearchComponent} from './ap-end-re-ast-search/ap-end-re-ast-search.component';

@NgModule({
  declarations: [ApEndReComponent, ApEndReHistComponent, ApEndReAstSearchComponent],
  imports: [
    CommonModule,
    ApEndReRoutingModule,
    SharedModule
  ]
})
export class ApEndReModule {
}
