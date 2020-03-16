import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FldRoutingModule } from './fld-routing.module';
import { FldComponent } from './fld.component';
import { SharedModule } from 'src/app/shared.module';
import { FldDetailAppHistComponent } from './fld-detail-app-hist/fld-detail-app-hist.component';

@NgModule({
  declarations: [FldComponent, FldDetailAppHistComponent],
  imports: [
    CommonModule,
    FldRoutingModule,
    SharedModule
  ]
})
export class FldModule { }
