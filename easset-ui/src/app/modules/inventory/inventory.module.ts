import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { WjInputModule } from 'wijmo/wijmo.angular2.input';
import { SharedModule } from 'src/app/shared.module';
import { InventoryRoutingModule } from './inventory-routing.module';
import { InventoryComponent } from './inventory.component';
import { CreateDataComponent } from './create-data/create-data.component';
import { ReleaseSettingsPopupComponent } from './create-data/release-settings-popup/release-settings-popup.component';
import { ListComponent } from './list/list.component';
import { EApplicationComponent } from './list/e-application/e-application.component';
import { AssetListComponent } from './list/asset-list/asset-list.component';
import { UpdateDataComponent } from './update-data/update-data.component';

@NgModule({
    declarations: [
        InventoryComponent,
        CreateDataComponent,
        ReleaseSettingsPopupComponent,
        ListComponent,
        EApplicationComponent,
        AssetListComponent,
        UpdateDataComponent
    ],
    exports: [],
    imports: [
        ReactiveFormsModule,
        WjInputModule,
        InventoryRoutingModule,
        CommonModule,
        SharedModule,
    ]
})
export class InventoryModule { }
