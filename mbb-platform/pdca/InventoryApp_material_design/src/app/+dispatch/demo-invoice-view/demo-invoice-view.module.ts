import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DemoInvoiceViewComponent, DialogComponent } from './demo-invoice-view.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { SharedModule } from '../../shared.module'

import {
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatRippleModule,
    MatFormFieldModule,
    MatTooltipModule,
    MatSelectModule,
    MatDialogModule,
    MatSliderModule,
    MatSlideToggleModule,
} from '@angular/material';

const routes: Routes = [
    {
        path: '',
        component: DemoInvoiceViewComponent
    }
]


@NgModule({
    imports: [
        MatIconModule,
        MatCheckboxModule,
        MatButtonModule,
        MatRippleModule,
        MatFormFieldModule,
        MatInputModule,
        MatTooltipModule,
        MatSliderModule,
        MatSlideToggleModule,
        MatSelectModule,
        CommonModule,
        MatDialogModule,
        SharedModule,
        [RouterModule.forChild(routes)],
        FormsModule,
        ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
    ],
    declarations: [DemoInvoiceViewComponent, DialogComponent],
    providers: [

    ],
    entryComponents: [DialogComponent]
})
export class DemoInvoiceViewModule { }
