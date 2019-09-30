

import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';



const routes: Routes = [


    { path: '', redirectTo: 'home' },
    {
        path: 'view',
        loadChildren: './+dispatch-view/dispatch-view.module#DispatchViewModule'

    },
    {
        path: 'details',
        loadChildren: './+dispatch-details/dispatch-details.module#DispatchDetailsModule'
    },
    {
        path: 'home',
        loadChildren: './+dispatch-home/dispatch-home.module#DispatchHomeModule'
    },
    {
        path: 'manifest',
        loadChildren: './+dispatch-manifest/dispatch-manifest.module#DispatchManifestModule'
    },
    {
        path: 'invoice',
        loadChildren: './+invoice/invoice.module#InvoiceModule'
    },
    {
        path: 'invoice-view',
        loadChildren: './+invoiceview/invoice-view.module#InvoiceViewModule'
    },
    {
        path: 'rental',
        loadChildren: './+rental/rental.module#RentalModule'
    },
    {
        path: 'demo-dispatch',
        loadChildren: './+demo-invoice/demo-invoice.module#DemoInvoiceModule'
    },
    {
        path: 'demo-invoice-view',
        loadChildren: './demo-invoice-view/demo-invoice-view.module#DemoInvoiceViewModule'
    }
]


@NgModule({
    imports: [RouterModule.forChild(routes)],
    declarations: [],
    exports: [],
    providers: [

    ],
})
export class DispatchManagerModule { }
