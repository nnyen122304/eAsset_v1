import { NgModule } from '@angular/core';

import { RefImplRoutingModule } from './ref-impl-routing.module';
import { RefImplSearchComponent } from './ref-impl-search/ref-impl-search.component';
import { SharedModule } from '../../shared.module';
import { CommonModule } from '@angular/common';
import { RefImplResultComponent } from './ref-impl-result/ref-impl-result.component';
import { RefImplComponent } from './ref-impl.component';
import { RefImplEntryComponent } from './ref-impl-entry/ref-impl-entry.component';

/**
 * リファレンス実装機能モジュール
 */
@NgModule({
  declarations: [
    RefImplSearchComponent,
    RefImplResultComponent,
    RefImplComponent,
    RefImplEntryComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RefImplRoutingModule,
  ]
})
export class RefImplModule { }
