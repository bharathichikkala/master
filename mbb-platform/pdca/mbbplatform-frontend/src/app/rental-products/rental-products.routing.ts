import { ModuleWithProviders } from "@angular/core"
import { Routes, RouterModule } from '@angular/router';
import { RentalComponent } from "./rental-products-list/rental-products.component";
import { AddRentalComponent } from "./add-rental/add-rental.component";
import { UpdateRentalComponent } from "./update-rental/update-rental.component";
import { AddRentalExtensionComponent } from "./rental-extension/rental-extension.component";
import { ConvertedRentalComponent } from "./convert-details/convert-details.component";
import { InventoryComponent } from './rental-inventory.component';
import { ShowPaymentImageComponent } from "./rentalpay-image/rentalpay-image.component";

export const routes: Routes = [
    {
        path: 'rentals',
        component: RentalComponent,
    },
    {
        path: 'add',
        component: AddRentalComponent,
    },
    {
        path: 'update/:id',
        component: UpdateRentalComponent,
    },
    {
        path: 'extend/:id',
        component: AddRentalExtensionComponent,
    },
    {
        path: 'convert/:id',
        component: ConvertedRentalComponent,
    },
    {
        path: 'payimage/:id',
        component: ShowPaymentImageComponent,
    },
    {
        path: 'rental-inventory',
        loadChildren: './rental-inventory/rental-inventory.module#RentalInventoryModule',
    },
];

export const routing = RouterModule.forChild(routes);


export const routedComponent = [
    RentalComponent,
    AddRentalComponent,
    UpdateRentalComponent,
    AddRentalExtensionComponent,
    ConvertedRentalComponent,
    ShowPaymentImageComponent
]
