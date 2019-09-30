import { ModuleWithProviders } from "@angular/core"
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../+auth/guards/index';
import { UserRoleGuard } from '../+auth/guards/userRole.guard';
import { ServiceProductsComponent } from "./products-list/products-list.component";
import { AddServiceProductComponent } from "./add-product/add-product.component";
import { UpdateServicingProductComponent } from './update-product/update-product.component';
import { ViewProductComponent } from "./view-product/view-product.component";
import { DispatchProductComponent } from "./dispatch-product/dispatch-product.component";
import { PaymentDetailsComponent } from "./payment-details/payment-details.component";
export const routes: Routes = [
    {
        path: '',
        component: ServiceProductsComponent,
    },
    {
        path: 'add-product',
        component: AddServiceProductComponent
    },
    {
        path: 'update-product',
        component: UpdateServicingProductComponent
    },
    {
        path: 'view-product',
        component: ViewProductComponent,
    },
    {
        path: 'dispatch-product',
        component: DispatchProductComponent,
    },
    {
        path: 'payment-details',
        component: PaymentDetailsComponent
    }

];

export const routing = RouterModule.forChild(routes);


export const routedComponent = [
    ServiceProductsComponent
]
