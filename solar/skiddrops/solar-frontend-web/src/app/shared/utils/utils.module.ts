import {NgModule} from "@angular/core";
import {MomentPipe,MomentPipeFromNow,MomentDateGroupedPipe} from "./moment.pipe";
import {TimeDirective} from "./time.directive";
import { FieldFilterPipe } from './field-filter.pipe';
import {BodyService} from "./body.service";
import {AlertNotificationService} from "./notification.service";
import {ToggleActiveDirective} from "./toggle-active.directive";

@NgModule({
  declarations: [ToggleActiveDirective, MomentPipe, MomentPipeFromNow,MomentDateGroupedPipe,TimeDirective, FieldFilterPipe],
  exports: [ToggleActiveDirective, MomentPipe,MomentPipeFromNow, MomentDateGroupedPipe,TimeDirective, FieldFilterPipe],
  providers: [BodyService, AlertNotificationService]
})
export class UtilsModule{
  static forRoot(){
    return {
      ngModule: UtilsModule,
      providers: [BodyService, AlertNotificationService]
    }
  }
}
