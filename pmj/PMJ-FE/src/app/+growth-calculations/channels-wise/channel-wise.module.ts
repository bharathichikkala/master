import { NgModule } from '@angular/core';
import { SmartadminModule } from '../../shared/smartadmin.module'
import { Routes, RouterModule } from '@angular/router';
import { ChannelWiseComponent } from './channel-wise.component';
import { GrowthKPIModule } from '../shared/kpi-block/kpi-block.module';
import { GrowthCalculationService } from '../growth.service';
import { GrowthDateFilterModule } from '../shared/date-filters/date-filter.module';
import { LoaderModule } from '../../shared-modules/loader/loader.module';
const routes: Routes = [
    {
        path: '',
        component: ChannelWiseComponent
    }
]


@NgModule({
    imports: [
        SmartadminModule,
        LoaderModule,
        RouterModule.forChild(routes),
        GrowthDateFilterModule,
        GrowthKPIModule
    ],
    declarations: [
        ChannelWiseComponent
    ],
    providers: [GrowthCalculationService],
})
export class GrowthChannelWiseModule {

}