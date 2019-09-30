import { NgModule } from '@angular/core';
import { SmartadminModule } from '../shared/smartadmin.module';
import { SmartadminDatatableModule } from '../shared/ui/datatable/smartadmin-datatable.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { routing } from './demo-products.routing';
import { MyDatePickerModule } from 'mydatepicker';
// import { ProductsService } from './service-products.service'
import { LoaderModule } from '../loader/loader.module';
// import { AddServiceProductComponent } from './add-product/add-product.component';
import { TreeViewModule } from "../shared/ui/tree-view/tree-view.module";
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { DemoProductsListComponent } from './products-list/products-list.component';
import { DemoProductsService } from './demo-products.service';
import { AddDemoProductComponent } from './add-product/add-product.component';
import { UpdateDemoProductComponent } from './update-product/update-product.component'
import { ViewDemoProductComponent } from './view-product/view-product.component';
import { DemoToOrderComponent } from './demo-to-order/demo-to-order.component';

@NgModule({
    declarations: [
        DemoProductsListComponent,
        AddDemoProductComponent,
        UpdateDemoProductComponent,
        ViewDemoProductComponent,
        DemoToOrderComponent
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
    providers: [DemoProductsService]
})
export class DemoProductsModule { }
