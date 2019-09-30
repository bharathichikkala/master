import { Component, EventEmitter, Input, OnInit, Output, ChangeDetectorRef } from '@angular/core';
import { FadeInTop } from '../../shared/animations/fade-in-top.decorator';

import { ActivatedRoute, Params, Router } from '@angular/router';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, Validators, FormControl, FormsModule, AbstractControl, FormArray } from '@angular/forms';
import { UserService } from '../user.service';
import { endponitConfig } from '../../environments/endpoints';
import { Global } from '../../shared/global';


declare var $: any;
declare var SockJS;
declare var Stomp;
@FadeInTop()
@Component({
  selector: 'datatables-users-add',
  templateUrl: './add-user.component.html',
  providers: [],
  styles: [`
  .select2-selection__choice__remove {   
    margin: 0!important;  
}`]
})
export class AddUserComponent implements OnInit {
  public complexForm: FormGroup;
  public username: AbstractControl;
  public email: AbstractControl;
  public phone: AbstractControl;
  public roles: AbstractControl;
  public facility: AbstractControl;
  public notification: AbstractControl;

  submitted = false;
  public userTypeRoles;
  userError;
  userSuccess;
  public serviceErrorResponse;
  public serviceErrorData;

  selectedUserRoleName: any;
  userRoles = new Array<{ name: string }>();
  userDetails: any = { roles: [], facilities: [] };

  datauserfacility: any;
  stompClient;
  loading = true;
  facilityList = []

  constructor(
    private readonly userService: UserService,
    private readonly route: ActivatedRoute,
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly cdr: ChangeDetectorRef) {

    this.selectedUserRoleName = '';
    this.selectedFacilityId = '';

    this.complexForm = fb.group({
      username: [null, Validators.compose([Validators.required,
      Validators.minLength(3), Validators.maxLength(32), Validators.pattern('[a-zA-Z*" "]+')])],
      email: [null, Validators.compose([Validators.required,
      Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')])],
      phone: [null, Validators.compose([Validators.required, Validators.pattern('^[0-9]{10,16}$')])],
      roles: [null, Validators.compose([Validators.required])],
      //facility: [null, Validators.compose([Validators.required])],
      notification: [false, Validators.compose([Validators.requiredTrue])],
    })
    this.username = this.complexForm.controls['username'];
    this.email = this.complexForm.controls['email'];
    this.phone = this.complexForm.controls['phone'];
    this.roles = this.complexForm.controls['roles'];
    //  this.facility = this.complexForm.controls['facility'];
    this.notification = this.complexForm.controls['notification'];
    this.userError = '';
    this.userService.getAllFacilityTypes().subscribe((data: any) => {
      if (data.error == null) {         
          this.facilityList= data.data;
      }
  })
  }
  no = false; yes = false;
  notificationChecked(e) {
    if (e.target.checked) {
      this.no = false;
      this.yes = true;
    } else {
      this.yes = false;
    }
  }
  notificationChecked1(e) {
    if (e.target.checked) {
      this.yes = false;
      this.no = true;
    } else {
      this.no = false;
    }
  }
  ngOnInit() {
    this.getUserTypeRoles();
  }

  facArray: any = []; facilityLength: any = 0;
  ngAfterViewInit() {
    $('#f').on('change', (event) => {
      this.facArray = [];
      this.facArray.push($("#f").val())
      this.facilityLength = $("#f").val().length
    });
  }
  getUserTypeRoles() {
    this.userService.getAllUserRoleTypes().subscribe(
      data => {
        this.userTypeRoles = data.data;
      },
      error => {
        console.log('Unable to get user roles types')
      });
  }
  selectedFacilityId;
  facilities: any = []
  addUser() {
    for (let i = 0; i < this.facArray.length; i++) {
      for (let j = 0; j < this.facArray[i].length; j++) {
        this.facilities.push({ id: parseInt(this.facArray[i][j]) })
      }
    }
    if (this.complexForm.valid && this.facilityLength != 0) {
      const data = {
        'name': this.selectedUserRoleName
      }
      this.userDetails.roles[0] = data

      this.userDetails.facilities = this.facilities
      if (this.yes == true) {
        this.userDetails.notificationStatus = true
      }
      else if (this.no == true) {
        this.userDetails.notificationStatus = false
      }
      this.loading = false;
      this.userService.addUser(this.userDetails).subscribe(
        (jsondata: any) => {
          if (jsondata.data) {
            this.userSuccess = "User Registration Successfully";
            this.userError = '';
            setTimeout(() => {
              this.router.navigate(['/users'])
            }, 2000)
          } else if (jsondata.error) {
            this.userError = jsondata.error.message;
            setTimeout(() => {
              this.loading = true;
              this.userError = '';
            }, 2000);
          }
        },
        error => {
          this.loading = true;
          this.serviceErrorResponse = error.exception;
          this.serviceErrorData = true;
          this.userError = 'Failed to Create User';
        });
    } else {
      this.submitted = true;
    }

  }

  // cancel user button
  cancelUser() {
    this.userError = '';
    this.router.navigate(['/users'])
  }
}


export class UserModel {
  constructor(
    public name: string,
    public email: string,
    public phone: string,
    public active: string,
    public roles: UserRolesModel[]) { }
}
export class UserRolesModel {
  constructor(
    public name: string
  ) {
  }
}

