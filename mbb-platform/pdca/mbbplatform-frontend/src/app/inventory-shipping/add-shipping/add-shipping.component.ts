import { Component, EventEmitter, Input, OnInit, Output, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ValidatorFn, FormControl, AbstractControl, FormArray, FormsModule } from '@angular/forms';
import { InventoryShippingService } from '../inventory-shipping.service'
import { endponitConfig } from '../../environments/endpoints';
import { Global } from '../../shared/global';

@Component({
    selector: 'add-shipping',
    templateUrl: './add-shipping.component.html'
})

export class AddShippingComponent {
    facilityForm: FormGroup;
    facilityTypes: any = [];
    selectedSourceFacility: any = null;
    selectedDestinationFacility: any = null;
    skuForm: FormGroup;
    poDetails: any;
    submitted = false;
    vendorDetails: any = {};
    vendorsNameList: any = [];
    successAlertMessages: any;
    errorAlertMessages: any;
    public poDate: Date;
    comments: any = '';
    locationSubmit = false;
    adminType: any;
    loading = false;
    fac = sessionStorage.getItem("facilityId");
    constructor(
        private readonly _shippingService: InventoryShippingService,
        private readonly route: ActivatedRoute,
        private readonly fb: FormBuilder,
        private readonly router: Router,
    ) {
        this.adminType = sessionStorage.getItem('userRole') === 'SUPERADMIN' ? true : false;
    }
    getMessage() {
        this.facilityForm.get('sourceFacility').setValue(sessionStorage.getItem('facilityId'))
        this.getDestinationLocations()
    }
    timer; facilityList: any = []
    ngAfterViewInit() {
        // this.timer = setInterval(() => {
        //     if (this.userService.getMessage()) {
        //         this.getMessage()
        //     }
        // }, 1500);
    }
    ngOnInit() {
        this.getAllFacilitys();
        this.facilityForm = new FormGroup({
            sourceFacility: new FormControl({ value: sessionStorage.getItem('facilityId'), disabled: sessionStorage.getItem('userRole') === 'SUPERADMIN' ? false : true }, Validators.required),
            destinationFacility: new FormControl(null, Validators.required),
        })
        this.facilityList = JSON.parse(sessionStorage.getItem("items"));

    }
    ngOnDestroy() {
        if (this.timer) {
            clearInterval(this.timer);
        }
    }

    facilityTypes2: any = [];
    getDestinationLocations() {
        this.facilityForm.get('destinationFacility').setValue(null)
        this.facilityAddedStatus = false;
        this.facilityTypes2 = [];
        this.facilityTypes.find((data) => {
            if (data.id != this.facilityForm.get('sourceFacility').value) {
                this.facilityTypes2.push(data)
            }
        })
    }
    selectFacility(e) {
        sessionStorage.setItem('facilityId', e.target.value);
        this.getMessage()
    }
    destinationChange() {
        this.facilityAddedStatus = false;
    }


    getAllFacilitys() {
        this._shippingService.getAllFacilityTypes().subscribe((data: any) => {
            if (data.error == null) {
                this.facilityTypes = data.data
                this.getDestinationLocations()
            }
        })
    }

    facilityAddedStatus: any;
    facilityCreationError: '';
    addFacilityDetails() {
        if (this.facilityForm.valid) {
            this.selectedSourceFacility = this.facilityForm.get('sourceFacility').value;
            this.selectedDestinationFacility = this.facilityForm.get('destinationFacility').value;
            this._shippingService.checkPackage(this.selectedSourceFacility, this.selectedDestinationFacility).subscribe((data) => {
                if (data.error == null) {
                    this.facilityAddedStatus = true;
                    this.addSkuFormDetails();
                    this.skuForm.value.items = [];
                    this.skuForm.reset();
                } else {
                    this.facilityCreationError = data.error.message;
                    setTimeout(() => {
                        this.facilityCreationError = '';
                    }, 3000)
                }
            })

        } else {
            this.locationSubmit = true;
        }
    }

    addSkuFormDetails() {
        this.skuForm = new FormGroup({
            'items': new FormArray([])
        });
        this.onAddItem()
    }


    itemsAddStatus = false;
    onAddItem() {
        this.itemsAddStatus = true;
        (this.skuForm.get('items') as FormArray).push(this.createItem());
    }

    static compareValueValidators(formGroup) {
        const available = formGroup.controls.available;
        const available_t = formGroup.controls.available_t;
        const returnObj: any = {};
        if (available.value < available_t.value) {
            returnObj.AVAILABLE = true
        }
        if (formGroup.controls.qc.value < formGroup.controls.qc_t.value) {
            returnObj.QC = true
        }
        if (formGroup.controls.unavailble.value < formGroup.controls.unavailble_t.value) {
            returnObj.UNAVAILABLE = true
        }
        if (available.value > 0 && available_t.value == 0 && formGroup.controls.qc.value >= 0 && formGroup.controls.qc_t.value == 0 && formGroup.controls.unavailble.value >= 0 && formGroup.controls.unavailble_t.value == 0) {
            returnObj.EMPTYROW = true
        }

        return returnObj;
    }

    createItem() {
        return new FormGroup({
            'skuCode': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(30)])),
            'itemName': new FormControl(null, Validators.required),
            'available': new FormControl(0, [Validators.required, Validators.pattern('^[0-9]*$')]),
            'available_t': new FormControl(0, [Validators.required, Validators.pattern('^[0-9]*$')]),
            'qc': new FormControl(0, [Validators.required, Validators.pattern('^[0-9]*$')]),
            'qc_t': new FormControl(0, [Validators.required, Validators.pattern('^[0-9]*$')]),
            'unavailble': new FormControl(0, [Validators.required, Validators.pattern('^[0-9]*$')]),
            'unavailble_t': new FormControl(0, [Validators.required, Validators.pattern('^[0-9]*$')]),
        }, AddShippingComponent.compareValueValidators);
    }

    productNameStatus = false;
    skuCodeErrorMsg: any;
    /**
     * productName
     * Quantity
     */
    skuCheckingStatus = false;
    skuCheckingErrorMsg = '';
    getSKUDetails(index) {
        this.skuCheckingStatus = true;
        this.productNameStatus = true;
        const formObj: any = this.skuForm.get('items') as FormArray;
        if (index) {
            for (let i = 0; i < this.skuForm.get('items').value.length; i++) {
                if (this.skuForm.value.items[index].skuCode == this.skuForm.value.items[i].skuCode && i != index) {
                    this.skuCheckingStatus = false;
                    this.skuCodeErrorMsg = "SKU already exists";
                    setTimeout(() => {
                        this.skuCodeErrorMsg = '';
                    }, 3000)
                }
            }
        }
        if (this.skuCheckingStatus) {
            this._shippingService.getSKUdetails(this.skuForm.value.items[index].skuCode, this.selectedSourceFacility).subscribe((data) => {
                if (data.error == null) {
                    if (data.data.available == 0 && data.data.pendingQualityCheck == 0 && data.data.unAvailable == 0) {
                        this.skuCodeErrorMsg = "Required Inventory not available for transfering for this SKU Id: " + this.skuForm.value.items[index].skuCode;
                        setTimeout(() => {
                            this.skuCodeErrorMsg = '';
                        }, 3000)
                    } else {
                        formObj.controls[index].get('itemName').setValue(data.data.productName)
                        formObj.controls[index].get('available').setValue(data.data.available)
                        formObj.controls[index].get('qc').setValue(data.data.pendingQualityCheck)
                        formObj.controls[index].get('unavailble').setValue(data.data.unAvailable)
                        formObj.controls[index].get('available_t').setValue(0)
                        formObj.controls[index].get('qc_t').setValue(0)
                        formObj.controls[index].get('unavailble_t').setValue(0)
                        this.productNameStatus = false;
                    }
                } else {
                    // this.skuCodeErrorMsg = 'This SKU Id ' + this.skuForm.value.items[index].skuCode + ' not available ';
                    this.skuCodeErrorMsg = data.error.message;
                    formObj.controls[index].get('skuCode').setValue('')
                    formObj.controls[index].get('itemName').setValue('')
                    formObj.controls[index].get('available').setValue('')
                    formObj.controls[index].get('qc').setValue('')
                    formObj.controls[index].get('unavailble').setValue('')
                    formObj.controls[index].get('available_t').setValue('')
                    formObj.controls[index].get('qc_t').setValue('')
                    formObj.controls[index].get('unavailble_t').setValue('')
                    setTimeout(() => {
                        this.skuCodeErrorMsg = '';
                    }, 4000)
                }
            })
        }

    }

    itemDetails: any = [];

    skusAddingStatus;
    buttonStatus: any = false;
    addPackage() {
        this.submitted = false;
        if (this.skuForm.valid) {
            if (this.itemsAddStatus) {
                this.loading = true;
                const skuObj: any = []
                const formObj: any = this.skuForm.get('items') as FormArray;
                for (let i = 0; i < this.skuForm.value.items.length; i++) {
                    skuObj.push({
                        "skuCode": formObj.controls[i].get('skuCode').value,
                        "productName": formObj.controls[i].get('itemName').value,
                        "available": formObj.controls[i].get('available_t').value,
                        "pendingQualityCheck": formObj.controls[i].get('qc_t').value,
                        "unAvailable": formObj.controls[i].get('unavailble_t').value,
                        "enable": true,
                        "comments": this.comments
                    })
                }

                this._shippingService.addPackageSKUdetails(skuObj, this.selectedSourceFacility, this.selectedDestinationFacility).subscribe((data: any) => {
                    if (data.error == null) {
                        this.buttonStatus = true;
                        this.successAlertMessages = "Package Added Successfully";
                        setTimeout(() => {
                            this.loading = false;
                            this.successAlertMessages = ''
                            this.skusAddingStatus = true;
                            this.buttonStatus = false;
                            this.router.navigate(['/shipping'])
                        }, 3000)
                    } else {
                        this.loading = false;
                        this.errorAlertMessages = data.error.message;
                        setTimeout(() => {
                            this.buttonStatus = false;
                            this.errorAlertMessages = '';
                        }, 3000)
                    }
                })
            } else {
                this.loading = false;
                alert('Please add atleast one SKU')
            }
        } else {
            this.loading = false;
            this.submitted = true;
        }
    }

    onDeleteItem(index) {
        (this.skuForm.get('items') as FormArray).removeAt(index);
        if (this.skuForm.get('items').value.length === 0) {
            this.itemsAddStatus = false;
        }
    }

    // HomePage
    gotoHomePage() {
        this.router.navigate(['/shipping'])
    }

}




