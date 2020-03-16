import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ApEndLeRoutingModule} from './ap-end-le-routing.module';
import {ApEndLeComponent} from './ap-end-le.component';
import { ApEndLeHistComponent } from './ap-end-le-hist/ap-end-le-hist.component';
import { SharedModule } from 'src/app/shared.module';

@NgModule({
  declarations: [ApEndLeComponent, ApEndLeHistComponent],
  imports: [
    CommonModule,
    ApEndLeRoutingModule,
    SharedModule
  ]
})
export class ApEndLeModule {
}
