import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppRoutingGuard } from './app-routing.guard';

const routes: Routes = [
  { path: 'site-map', loadChildren: './modules/site-map/site-map.module#SiteMapModule', canActivate: [AppRoutingGuard] },
  { path: 'ref-impl', loadChildren: './modules/ref-impl/ref-impl.module#RefImplModule', canActivate: [AppRoutingGuard] },
  { path: 'inv', loadChildren: './modules/inventory/inventory.module#InventoryModule', canActivate: [AppRoutingGuard] },
  { path: 'role-setting', loadChildren: './modules/admin/admin.module#AdminModule', canActivate: [AppRoutingGuard] },
  { path: 'section-role-profile', loadChildren: './modules/admin/admin.module#AdminModule', canActivate: [AppRoutingGuard] },
  { path: 'code-name-setting', loadChildren: './modules/admin/admin.module#AdminModule', canActivate: [AppRoutingGuard] },
  { path: 'ppfs-import', loadChildren: './modules/admin/admin.module#AdminModule', canActivate: [AppRoutingGuard] },
  { path: 'ap-bgn-int', loadChildren: './modules/ap-bgn-int/ap-bgn-int.module#ApBgnIntModule', canActivate: [AppRoutingGuard] },
  { path: 'ap-asset', loadChildren: './modules/ap-asset/ap-asset.module#ApAssetModule', canActivate: [AppRoutingGuard] },
  { path: 'ap-change', loadChildren: './modules/ap-change/ap-change.module#ApChangeModule', canActivate: [AppRoutingGuard] },
  { path: 'ap-get-int', loadChildren: './modules/ap-get-int/ap-get-int.module#ApGetIntModule', canActivate: [AppRoutingGuard] },
  { path: 'ap-get-tan', loadChildren: './modules/ap-get-tan/ap-get-tan.module#ApGetTanModule', canActivate: [AppRoutingGuard] },
  { path: 'ap-end-tan', loadChildren: './modules/ap-end-tan/ap-end-tan.module#ApEndTanModule', canActivate: [AppRoutingGuard] },
  { path: 'ap-end-le', loadChildren: './modules/ap-end-le/ap-end-le.module#ApEndLeModule', canActivate: [AppRoutingGuard] },
  { path: 'ap-end-re', loadChildren: './modules/ap-end-re/ap-end-re.module#ApEndReModule', canActivate: [AppRoutingGuard] },
  {
    path: 'ap-license',
    loadChildren: './modules/ap-license/ap-license.module#ApLicenseModule',
    canActivate: [AppRoutingGuard]
  },
  {
    path: 'cost-scr',
    loadChildren: './modules/cost-scr/cost-scr.module#CostScrModule',
    canActivate: [AppRoutingGuard]
  },
  {
    path: 'fld',
    loadChildren: './modules/fld/fld.module#FldModule',
    canActivate: [AppRoutingGuard]
  },
  {
    path: 'asset',
    loadChildren: './modules/asset/asset.module#AssetModule',
    canActivate: [AppRoutingGuard]
  },
  {
    path: 'license',
    loadChildren: './modules/license/license.module#LicenseModule',
    canActivate: [AppRoutingGuard]
  },
  {
    path: 'license-alloc',
    loadChildren: './modules/license-alloc/license-alloc.module#LicenseAllocModule', canActivate: [AppRoutingGuard] },
  {
    path: 'license-report',
    loadChildren: './modules/license-report/license-report.module#LicenseReportModule',
    canActivate: [AppRoutingGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
