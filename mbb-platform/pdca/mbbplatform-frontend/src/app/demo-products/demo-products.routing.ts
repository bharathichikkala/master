import { ModuleWithProviders } from "@angular/core"
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../+auth/guards/index';
import { UserRoleGuard } from '../+auth/guards/userRole.guard';
import { DemoProductsListComponent } from './products-list/products-list.component';
import { AddDemoProductComponent } from './add-product/add-product.component';
import { UpdateDemoProductComponent } from './update-product/update-product.component';
import { ViewDemoProductComponent } from './view-product/view-product.component';
import { DemoToOrderComponent } from './demo-to-order/demo-to-order.component';
export const routes: Routes = [
    {
        path: '',
        component: DemoProductsListComponent
    },
    {
        path: 'add-product',
        component: AddDemoProductComponent
    },
    {
        path: 'update-product',
        component: UpdateDemoProductComponent
    },
    {
        path: 'view-product',
        component: ViewDemoProductComponent
    },
    {
        path: 'demo-to-order',
        component: DemoToOrderComponent
    }
];

export const routing = RouterModule.forChild(routes);


export const routedComponent = [
    DemoProductsListComponent
]
