import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SiteMapRoutingModule } from './site-map-routing.module';
import { SiteMapComponent } from './site-map.component';
import { SiteMapResultComponent } from './site-map-result/site-map-result.component';
import { SharedModule } from 'src/app/shared.module';

@NgModule({
  declarations: [SiteMapComponent, SiteMapResultComponent],
  imports: [
    CommonModule,
    SiteMapRoutingModule,
    SharedModule
  ]
})
export class SiteMapModule { }
