import { Component, AfterViewInit, OnInit } from '@angular/core';
import { QRCodeScanner } from '../../+qrcode-scanner/qrcode-scanner.service';
import { AppService } from '../../app.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { FormGroup, FormControl, AbstractControl, FormArray, FormBuilder, Validators } from '@angular/forms';


@Component({
    selector: 'product-return',
    templateUrl: 'return.component.html',
    styleUrls: ['return.component.css']
})

export class ReturnProductComponent implements OnInit {


    productArray
    conditionsList;
    statusList;
    statusList1
    inventoryId;
    addProductObj: any;
    facilitiesList: any;

    constructor(
        public _qrScanner: QRCodeScanner,
        public _appService: AppService,
        public _router: Router,
        public dialog: MatDialog,
        private readonly fb: FormBuilder
    ) {
        this.facility = JSON.parse(localStorage.getItem('facility'));
        this._appService.currentMessage.subscribe(message => {
            for (const i of message) {
                this.productArray = i;
                for (let itm of this.productArray) {
                    itm.inventoryItem.facilityId.facility = this.facility.facility
                }
                this.getAllProductConditionList();
                this.getAllProductStatusList();
                this.getFacilitiesList()
            }
        })
    }
    returnFacility: any; facility: any;
    reason;
    ngOnInit() {
        this.returnFacility = '';
        this.statusObj.itemStatus = ''
        /**
    * Back Button event trigger
    */
        history.pushState(null, null, location.href);
        window.onpopstate = (event) => {
            history.go(1);
        };
        this.reason = localStorage.getItem("reason")
        if (this.reason == null || this.reason == '') {
            this.reason = 20
        } else {
            this.reason = localStorage.getItem("reason")
        }
    }

    onChange(i, j) {
        if (this.productArray[i].checklist[j].accessoryCondition) {
            this.productArray[i].checklist[j].quantity = 1
        } else {
            this.productArray[i].checklist[j].quantity = 0
        }
    }

    getAllProductConditionList() {
        this._appService.getProductConditionList().subscribe(data => {
            const condRes: any = data;
            if (condRes.data != null) {
                this.conditionsList = condRes.data;
                for (const i of this.productArray) {
                    const condObj: any = this.conditionsList.find(status => 1 === status.id)
                    i.inventoryItem.inventoryConditionId.id = condObj.id;
                    if (i.inventoryItem.inventoryConditionId.id) {
                        this._appService.getStatusByInventoryCondition(i.inventoryItem.inventoryConditionId.id).subscribe((data) => {
                            const condRes: any = data;
                            this.statusList = condRes.data;
                        })
                    }
                }
            }
        })
    }
    statusObj: any = {
        'itemStatus': ''
    };
    getAllProductStatusList() {
        this._appService.getAllProductStatusTypes().subscribe(data => {
            const statusRes: any = data;
            if (statusRes.data != null) {
                this.statusList = statusRes.data;
                for (const i of this.productArray) {
                    this.statusObj = this.statusList.find(status => 5 === status.id)
                    i.inventoryItem.itemStatusId.id = this.statusObj.id
                }
            }
        })
    }

    getFacilitiesList() {
        this._appService.getFacilities().subscribe((data) => {
            const facilityRes: any = data;
            if (facilityRes.data != null) {
                this.facilitiesList = facilityRes.data;
            }
        })
    }

    getStatusList(value) {
        if (value.inventoryConditionId.id == 1) {
            this._appService.getStatusByInventoryCondition(value.inventoryConditionId.id).subscribe((data) => {
                const condRes: any = data;
                this.statusList = condRes.data;
            })
            value.itemStatusId.id = ''
        }
        else {
            this._appService.getStatusByInventoryCondition(value.inventoryConditionId.id).subscribe((data) => {
                const condRes: any = data
                this.statusList1 = condRes.data;
            })
            value.itemStatusId.id = ''
        }
    }

    checklistPostObj: any = {
        array: []
    }
    formValid = false
    buttonStatus = false
    loaderBtn = false;
    addToInventoryAfterFacilityCheck() {
        if (this.facility.id === this.productArray[0].facility.id) {
            this.addToInventory();
        } else {
            this.facilityCheck("Are you sure to return product to different Facility")
        }
    }
    addToInventory() {
        this.buttonStatus = true
        const postObj: any = [
        ]
        const data: any = []
        for (let i = 0; i < this.productArray.length; i++) {
            //this.productArray[i].inventoryItem.comments=this.productArray[i].comments
            this.productArray[i].inventoryItem.createdUser = localStorage.getItem('userName');
            for (let facility of this.facilitiesList) {
                if (this.productArray[i].inventoryItem.facilityId.id == facility.id) {
                    this.productArray[i].inventoryItem.facilityId = {
                        id: this.facility.id, facility: this.facility.facility, facilityName: this.facility.facilityName
                    };
                } else if (this.productArray[i].inventoryItem.facilityId.id == '') {
                    this.formValid = false
                    break
                }

            }

            postObj.push(
                this.productArray[i].inventoryItem
            )

            if (this.productArray[i].inventoryItem.facilityId.id == '') {
                this.formValid = false
                break
            }
            else {
                this.formValid = true
            }

            if (this.productArray[i].inventoryItem.itemStatusId.id == '') {
                this.formValid = false
                break
            }
            else {
                this.formValid = true
            }

            if (this.productArray[i].checklist.length > 0) {
                const checklist: any = { checkListArray: [] }
                for (let j = 0; j < this.productArray[i].checklist.length; j++) {
                    checklist.checkListArray.push({
                        "accessoriesId": this.productArray[i].checklist[j].accessoriesId.id,
                        "accessoryCondition": this.productArray[i].checklist[j].accessoryCondition,
                        "quantity": this.productArray[i].checklist[j].accessoryCondition ? this.productArray[i].checklist[j].quantity : 0
                    })
                    checklist.inventoryItemId = {
                        id: this.productArray[i].inventoryItem.id
                    }
                }
                data.push(checklist)
            }
        }
        this.checklistPostObj.array = data;
        if (this.formValid) {
            this.loaderBtn = true;
            this._appService.addReturnItemToInventory(postObj, this.reason).subscribe((data: any) => {
                const resReslt: any = data;
                if (Number(this.reason) == 10) {
                    const id = localStorage.getItem("demoId");
                    const ob = {
                        "comments": localStorage.getItem("comments")
                    }
                    this._appService.addCommentsForReturn(ob, id).subscribe(data1 => {
                    })
                }
                if (resReslt.data != null) {
                    localStorage.setItem('removeNothanks', "ok")
                    if (this.checklistPostObj.array.length > 0) {
                        this._appService.updateChecklist(this.checklistPostObj).subscribe((data: any) => {
                            if (data.data != null) {
                                this.loaderBtn = false;
                                this.openDialog('Product & Checklist Successfully Added to Inventory ')
                            } else {
                                this.loaderBtn = false;
                                this.openDialog(data.error.message)
                            }
                        })
                    }
                    else {
                        this.loaderBtn = false;
                        this.openDialog('Product Successfully Added to Inventory')
                    }
                } else {
                    this.loaderBtn = false;
                    this.openDialog(data.error.message);
                }
            })
        }
    }

    expand(i) {
        if (this.productArray[i].condition) {
            this.productArray[i].condition = false;
        } else {
            this.productArray[i].condition = true;
        }
    }

    navigatetoHomePage() {
        this._router.navigate(['./inventory'])
    }
    openDialog(text) {
        const dialogRef = this.dialog.open(DialogComponent, { disableClose: true });
        localStorage.setItem('msg', text)
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.navigatetoHomePage()
            }
        });
    }
    facilityCheck(text) {
        localStorage.setItem('facilityCheck', "facility");
        localStorage.setItem('removeNothanks',"no")
        const dialogRef = this.dialog.open(DialogComponent, { disableClose: true });
        localStorage.setItem('msg', text)
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.addToInventory();
            } else {
                this.navigatetoHomePage()
            }
        });
    }
}




@Component({
    selector: 'dialog-content-example-dialog',
    template: `<img class="mat-typography img1" src="assets/img/logom.png" alt="MedicalBulkBuy" width="90%"   >
    <mat-dialog-content class="mat-typography" style="border-bottom:1px solid #ddd;border-top:1px solid #ddd; text-align:center">
     
    <h5 style="padding: 18px;margin: 0px;font-size: 14px;font-family: sans-serif;">{{msgText}}</h5>
    </mat-dialog-content>
   
    <mat-dialog-actions align="center" >
    <span *ngIf='fac==="facility"'>
    <button mat-stroked-button *ngIf='hideButton!=="ok"' [mat-dialog-close]="false">No Thanks</button>&nbsp;
    </span><button mat-stroked-button [mat-dialog-close]="true" cdkFocusInitial > 
    <mat-icon>done</mat-icon>
    Ok
</button>
    </mat-dialog-actions>
  `,
})

export class DialogComponent {
    constructor(public _router: Router) {

    }
    msgText: any; fac: any; hideButton;
    ngOnInit() {
        this.fac = localStorage.getItem('facilityCheck');
        this.msgText = localStorage.getItem('msg');
        this.hideButton = localStorage.getItem('removeNothanks')
    }
}

