import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTableModule } from "angular2-datatable";
import { routing } from './loads.routing';
import { LoadsListComponent } from './components/list/loads.list.component';
import { LoadAddComponent } from './components/add/load.add.component';
import { LoadUpdateComponent } from './components/update/load.update.component';
import { LoadDocumentsComponent } from './components/damage-images/damge-images.component';
import { MapStyleService } from './components/map-style.service';

import { LoadDataFilterPipe } from '../shared/pipes/loaddata-filter.pipe';
import { MyDatePickerModule } from 'mydatepicker';
import { SelectModule } from 'angular2-select';

import { SmartadminModule } from "../shared/smartadmin.module";
import { SmartadminDatatableModule } from "../shared/ui/datatable/smartadmin-datatable.module";

import { Error500Module } from '../+auth/+error/+error500/error500.module';

@NgModule({
  imports: [
    SmartadminModule,
    SmartadminDatatableModule,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    routing,
    DataTableModule,
    MyDatePickerModule,
    SelectModule,
    Error500Module
  ],
  declarations: [
    LoadsListComponent,
    LoadAddComponent,
    LoadDocumentsComponent,
    LoadUpdateComponent,
    LoadDataFilterPipe,

  ],
  providers:[MapStyleService]
})
export class LoadsModule { }
