import { Routes, RouterModule } from '@angular/router';

import { Fuel } from './food.component';
import { FuelListComponent } from './components/list/fuel.list.component';
import { FuelAddComponent } from './components/add/fuel.add.component';
import { FuelUpdateComponent } from './components/update/fuel.update.component';

// noinspection TypeScriptValidateTypes
const routes: Routes = [
  {
    path: '', component: FuelListComponent, data: { pageTitle: 'Fuel Station Details' }
  },
  {
    path: 'addFuel', component: FuelAddComponent, data: { pageTitle: 'Add Fuel Station' }
  },
  {
    path: 'updateFuelStation/:Id', component: FuelUpdateComponent, data: { pageTitle: 'Update Fule Station' }
  }
];

export const routing = RouterModule.forChild(routes);
