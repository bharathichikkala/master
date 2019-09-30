import { NgModule } from '@angular/core';
import { SmartadminModule } from '../shared/smartadmin.module';
import { SmartadminDatatableModule } from '../shared/ui/datatable/smartadmin-datatable.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { routing } from './user.routing';

import { UsersListComponent } from './+users-list/users-list.component';
import { AddUserComponent } from './+add-user/add-user.component';
import { EditUserComponent } from './+edit-user/edit-user.component';
import { Select2Module } from './select2/select2.module'


import { UserService } from './user.service'
import { LoaderModule } from '../loader/loader.module';


@NgModule({
  declarations: [
    EditUserComponent, UsersListComponent, AddUserComponent
  ],
  imports: [
    SmartadminModule,
    SmartadminDatatableModule,
    routing,
    LoaderModule,
    FormsModule, ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),Select2Module
  ],
  providers: [UserService,
  ]
})
export class UserModule { }
