import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './+auth/login/login.component'
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';

const routes: Routes = [
  {
    path: 'loginUrl',
    loadChildren: './+auth/auth.module#AuthModule',
    // component:LoginComponent
  },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      { path: 'inventory', loadChildren: './+inventory/inventory-manager.module#InventoryManagerModule' },
      { path: 'sku', loadChildren: './+sku-management/sku-management.module#SKUModule' },
      { path: 'product', loadChildren: './+product-management/product-management.module#ProductManagementModule' },
      { path: 'qr', loadChildren: './+qr-management/qr-management.module#QrManagementModule' },
      { path: 'dispatch', loadChildren: './+dispatch/dispatch-manager.module#DispatchManagerModule' },
      { path: 'version', loadChildren: './+version-details/version-details.module#VersionModule' },
      { path: 'facilities', loadChildren: './facilities/facilities.module#FacilityModule' }
    ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes, { useHash: true })
  ],
  exports: [
  ],
})
export class AppRoutingModule { }
