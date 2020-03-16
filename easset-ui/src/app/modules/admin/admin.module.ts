import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { WjInputModule } from 'wijmo/wijmo.angular2.input';
import { SharedModule } from 'src/app/shared.module';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { MasterComponent } from '../../modules/admin/master/master.component';
import { MasterSettingComponent } from '../../modules/admin/master/master-setting/master-setting.component';
import { ProfileComponent } from '../../modules/admin/profile/profile.component';
import { SettingComponent } from '../../modules/admin/setting/setting.component';
import { ProplusComponent } from '../../modules/admin/proplus/proplus.component';
import { AddPrivilegesComponent } from 'src/app/modules/admin/setting/add-privileges/add-privileges.component';
import { ParentMasterSelectionComponent } from 'src/app/parts/lov-input/parent-master-selection/parent-master-selection.component';
import { InputDetailComponent } from './master/master-setting/input-detail/input-detail.component';
import { ProfileRegisterComponent } from './profile/profile-register/profile-register.component';

@NgModule({
  declarations: [
    AdminComponent,
    MasterComponent,
    MasterSettingComponent,
    ProfileComponent,
    SettingComponent,
    ProplusComponent,
    AddPrivilegesComponent,
    ParentMasterSelectionComponent,
    InputDetailComponent,
    ProfileRegisterComponent
  ],
  imports: [
    ReactiveFormsModule,
    WjInputModule,
    CommonModule,
    AdminRoutingModule,
    SharedModule,
  ]
})
export class AdminModule { }
