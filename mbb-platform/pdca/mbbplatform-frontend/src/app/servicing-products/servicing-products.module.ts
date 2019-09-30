import { NgModule } from '@angular/core';
import { SmartadminModule } from '../shared/smartadmin.module';
import { SmartadminDatatableModule } from '../shared/ui/datatable/smartadmin-datatable.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { routing, routedComponent } from './service-products.routing';
import { MyDatePickerModule } from 'mydatepicker';
import { ProductsService } from './service-products.service'
import { LoaderModule } from '../loader/loader.module';
import { AddServiceProductComponent } from './add-product/add-product.component';
import { TreeViewModule } from "../shared/ui/tree-view/tree-view.module";
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { UpdateServicingProductComponent } from './update-product/update-product.component';
import { ViewProductComponent } from './view-product/view-product.component';
import { DispatchProductComponent } from './dispatch-product/dispatch-product.component'
import { PaymentDetailsComponent } from './payment-details/payment-details.component';
@NgModule({
    declarations: [
        routedComponent,
        AddServiceProductComponent,
        UpdateServicingProductComponent,
        ViewProductComponent,
        DispatchProductComponent,
        PaymentDetailsComponent 
    ],
    imports: [
        SmartadminModule,
        MyDatePickerModule,
        SmartadminDatatableModule,
        LoaderModule,
        routing,
        TreeViewModule,
        NgMultiSelectDropDownModule.forRoot(),
        FormsModule, ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
    ],
    providers: [ProductsService]
})
export class ServicingProductsModule { }
