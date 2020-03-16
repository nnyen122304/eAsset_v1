import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssetRoutingModule } from './asset-routing.module';
import { AssetComponent } from './asset.component';
import { AssetHistComponent } from './asset-hist/asset-hist.component';
import { SharedModule } from 'src/app/shared.module';
import { AssetParentAssetSearchComponent} from './asset-parent-asset-search/asset-parent-asset-search.component';
import { AssetSearchComponent} from './asset-search/asset-search.component';
import { AssetResultComponent } from './asset-result/asset-result.component';
import { AssetParentAssetResultComponent } from './asset-parent-asset-result/asset-parent-asset-result.component';

@NgModule({
  declarations: [AssetComponent, AssetHistComponent, AssetParentAssetSearchComponent, AssetSearchComponent, AssetResultComponent, AssetParentAssetResultComponent],
  imports: [
    CommonModule,
    AssetRoutingModule,
    SharedModule
  ]
})
export class AssetModule { }
