import { NgModule } from '@angular/core';
import { SmartadminModule } from '../../shared/smartadmin.module'
import { routing, routedComponent } from './rental-inventory.routing';
import { DataTableModule } from "angular2-datatable";
import { SmartadminDatatableModule } from "../../shared/ui/datatable/smartadmin-datatable.module";
import { LoaderModule } from '../../loader/loader.module';
import { MyDatePickerModule } from 'mydatepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
    imports: [
        SmartadminModule,
        routing,
        SmartadminDatatableModule,
        LoaderModule,
        DataTableModule,
        MyDatePickerModule,
        FormsModule, ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),

    ],
    declarations: [
        routedComponent
    ],
    providers: [],
})
export class RentalInventoryModule {

}
