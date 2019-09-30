import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { DispatchDetailsComponent, FilterPipe } from './dispatch-details.component';
import { DemoDispatchComponent, DialogComponent } from './demo-invoice.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { SharedModule } from '../../shared.module'
import { MatSelectModule, MatDialogModule, MatIconModule } from '@angular/material';

import {
    MatInputModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,

} from '@angular/material';


const routes: Routes = [
    {
        path: '',
        component: DemoDispatchComponent
    }
]


@NgModule({
    imports: [
        MatExpansionModule,
        MatFormFieldModule,
        MatInputModule,
        SharedModule,
        MatDialogModule,
        MatIconModule,
        MatSelectModule,
        CommonModule,
        MatDatepickerModule,
        MatNativeDateModule,
        [RouterModule.forChild(routes)],
        FormsModule, ReactiveFormsModule,
    ],
    declarations: [DemoDispatchComponent, DialogComponent],
    providers: [

    ],
    entryComponents: [DialogComponent]
})
export class DemoInvoiceModule { }
