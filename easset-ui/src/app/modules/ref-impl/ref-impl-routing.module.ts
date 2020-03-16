import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RefImplComponent } from './ref-impl.component';

const routes: Routes = [{ path: '', component: RefImplComponent}];

/**
 * リファレンス実装-ルーティング設定
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RefImplRoutingModule { }
