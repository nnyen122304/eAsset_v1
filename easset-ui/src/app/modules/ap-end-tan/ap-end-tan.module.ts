import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ApEndTanRoutingModule} from './ap-end-tan-routing.module';
import {ApEndTanComponent} from './ap-end-tan.component';
import { SharedModule } from 'src/app/shared.module';
import { ApEndTanHistComponent } from './ap-end-tan-hist/ap-end-tan-hist.component';

@NgModule({
  declarations: [ApEndTanComponent, ApEndTanHistComponent],
  imports: [
    CommonModule,
    ApEndTanRoutingModule,
    SharedModule
  ]
})
export class ApEndTanModule {
}
