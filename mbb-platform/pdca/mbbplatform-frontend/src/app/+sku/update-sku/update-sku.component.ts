import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { FormGroup, FormControl, AbstractControl, FormBuilder, Validators, FormArray } from '@angular/forms';
import { ModalDirective } from 'ngx-bootstrap';

import { SKUService } from '../sku.service';
import { DomSanitizer } from '@angular/platform-browser';
declare var $: any;
@Component({
    selector: 'update-sku',
    templateUrl: './update-sku.component.html'
})

export class UpdateSKUComponent {
    private readonly skuCodeList;
    public updateSKUForm: FormGroup;
    public skuObj: any = {}
    formValidate: boolean;
    successResponse;
    errorResponse;
    skuDetails = false;
    skuValue;
    submitted = true;
    loading = false;
    constructor(public skuService: SKUService, private readonly route: ActivatedRoute, private readonly _sanitizer: DomSanitizer, private readonly fb: FormBuilder,
        private readonly router: Router) {
        this.updateSKUForm = new FormGroup({
            'skucode': new FormControl(null, [Validators.required, Validators.maxLength(30)]),
            'productName': new FormControl(null, [Validators.required, Validators.maxLength(100)]),
            'serialNumber': new FormControl(null),
            'notification': new FormControl(false, [Validators.requiredTrue]),
            'accessories': new FormControl(false, [Validators.requiredTrue]),
            'description': new FormControl(null),
            'items': new FormArray([]),
            'skuItems': new FormArray([])

        })
    }
    hasItems: any; hasItem: boolean;
    onChange(index) {
        if (index === 0) {
            this.details[index].check = true;
            this.skuService.getSKUStatusCheck(this.skuObj.inventory.skuCode, this.details[index].check).subscribe((data: any) => {
                if (data.data != null) {
                } else {
                    this.hasItem = true;
                    this.hasItems = data.error.message;
                    setTimeout(() => {
                        this.hasItems = ''
                    }, 2500)
                }
            })
            this.details[index + 1].check = false;
        } else if (index === 1) {
            this.details[index].check = true;
            this.skuService.getSKUStatusCheck(this.skuObj.inventory.skuCode, false).subscribe((data: any) => {
                if (data.data != null) {
                } else {
                    this.updateSKUForm.get('notification').setValue(true)
                    this.details[index - 1].check = true;
                    this.details[index].check = false;
                    this.hasItem = true;
                    this.hasItems = data.error.message;
                    setTimeout(() => {
                        this.hasItems = ''
                    }, 2500)
                }
            })
            this.details[index - 1].check = false;
        }
    }
    details: any = [{ name: "ACTIVE", check: false }, { name: "IN-ACTIVE", check: false }]
    accessoriesDetails: any = [{ name: "YES", check: false }, { name: "NO", check: false }]
    accessoriesCheck: boolean;
    onChangeAccessories(index) {
        if (index === 0) {
            this.accessoriesCheck = true;
            this.accessoriesDetails[index].check = true;
            this.accessoriesDetails[index + 1].check = false;
        } else if (index === 1) {
            this.accessoriesCheck = false;
            this.skuObj.inventory.accessoriesStatus = false
            this.accessoriesDetails[index].check = true;
            this.accessoriesDetails[index - 1].check = false;
        }
    }
    userRole; isReadOnly = true;

    ngOnInit() {
        $('#example').tooltip({ placement: 'top' });

        this.userRole = sessionStorage.getItem('userRole');
        if (this.userRole === 'SUPERADMIN') {
            this.isReadOnly = false;
        }
        this.route.params.forEach((params: Params) => {
            if (params['skuCode'] !== undefined) {
                this.skuService.getSKUDetails(params['skuCode']).subscribe((data: any) => {
                    if (data.data != null) {
                        this.skuDetails = true;
                        this.skuObj = data.data;
                        if (this.skuObj.inventory.active) {
                            this.details[0].check = true;
                            this.updateSKUForm.get('notification').setValue(true)
                        } else {
                            this.details[1].check = true;
                            this.updateSKUForm.get('notification').setValue(true)
                        }
                        if (this.skuObj.inventory.accessoriesStatus) {
                            this.accessoriesCheck = true;
                            this.accessoriesDetails[0].check = true;
                            this.updateSKUForm.get('accessories').setValue(true)
                        } else {
                            this.accessoriesCheck = false;
                            this.accessoriesDetails[1].check = true;
                            this.updateSKUForm.get('accessories').setValue(true)
                        }
                        for (const i of this.skuObj.accessoriesList) {
                            (this.updateSKUForm.get('skuItems') as FormArray).push(this.validationCheck(i));
                        }
                        for (const i of this.skuObj.facilityWiseThreshold) {
                            (this.updateSKUForm.get('items') as FormArray).push(this.createItem(i));
                        }
                        this.getIdBySKU();
                    } else {
                        alert("No details added for this sku")
                    }
                })
            } else {
                this.router.navigate(['/sku'])
            }
        })
    }
    skuIds: any = { id: [] };
    getIdBySKU() {
        this.skuIds.id = []
        const formObj: any = this.updateSKUForm.get('skuItems') as FormArray;
        for (let skus of formObj.value) {
            this.skuService.getProductNameBySKU(skus.skuCode).subscribe((data: any) => {
                this.skuIds.id.push(data.data.id);
            })
        }
    }
    itemsAddStatus = false;
    onAddItem() {
        this.itemsAddStatus = true;
        (this.updateSKUForm.get('skuItems') as FormArray).push(this.validationCheck(''));
    }

    onDeleteItem(index) {
        (this.updateSKUForm.get('skuItems') as FormArray).removeAt(index);
        if (this.updateSKUForm.get('skuItems').value.length === 0) {
            this.itemsAddStatus = false;
        }
        this.getIdBySKU()
    }
    validationCheck(obj) {
        if (obj) {
            return new FormGroup({
                'itemName': new FormControl(obj.productName, Validators.required),
                'skuCode': new FormControl(obj.sku, Validators.compose([Validators.required, Validators.maxLength(30)])),
            })
        }
        return new FormGroup({
            'itemName': new FormControl(null, Validators.required),
            'skuCode': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(30)])),
        })
    }
    arr: any = { id: [], item: [] }
    getProductName(index) {
        let skuCheckingStatus = true
        const formObj: any = this.updateSKUForm.get('skuItems') as FormArray;

        formObj.controls[index].get('itemName').setValue('')
        if (index) {
            for (let i = 0; i < this.updateSKUForm.get('skuItems').value.length; i++) {
                if (this.updateSKUForm.value.skuItems[index].skuCode === this.updateSKUForm.value.skuItems[i].skuCode && i !== index) {
                    skuCheckingStatus = false;
                    this.existSKUMessage = "SKU Already exists";
                    setTimeout(() => {
                        this.existSKUMessage = '';
                        formObj.controls[index].get('skuCode').setValue('')
                    }, 1500)
                }
            }
        }

        if (skuCheckingStatus && this.updateSKUForm.value.skuItems[index].skuCode) {
            this.skuService.getSKUdetails(this.updateSKUForm.value.skuItems[index].skuCode).subscribe((data) => {
                if (data.data != null) {
                    if (data.data.skuCode != this.skuObj.inventory.skuCode) {
                        skuCheckingStatus = false;
                        formObj.controls[index].get('itemName').setValue(data.data.productName);
                        this.arr.id.push(index)
                        this.arr.item.push(data.data.id)
                    } else {
                        this.existSKUMessage = "SKU is parent";
                        setTimeout(() => {
                            this.existSKUMessage = '';
                            formObj.controls[index].get('skuCode').setValue('')
                        }, 1500)
                    }
                } else {
                    this.existSKUMessage = data.error.message;
                    setTimeout(() => {
                        this.existSKUMessage = '';
                        formObj.controls[index].get('skuCode').setValue('')
                    }, 1500)
                }

            })
        }
    }

    createItem(obj) {
        if (obj) {
            return new FormGroup({
                'facility': new FormControl(obj.facility.facility, [Validators.required]),
                'thresholdLevel': new FormControl(obj.thresholdLevel, [Validators.required, Validators.pattern('^([1-9][0-9]{0,2})$')]),
            })
        }
        else {
            return new FormGroup({
                'thresholdLevel': new FormControl(obj.thresholdLevel, [Validators.required, Validators.pattern('^([1-9][0-9]{0,2})$')]),
            })
        }
    }
    existSKUMessage;
    successMessage = 'SKU details updated successfully';
    updateData() {
        if (this.accessoriesCheck) {
            if ((this.updateSKUForm.get('skuItems') as FormArray).length != 0) {
                this.updateSkuDetails()
            }
            else {
                this.existSKUMessage = "Please add child SKUs list";
                setTimeout(() => {
                    this.existSKUMessage = '';
                }, 2000)
            }
        } else {
            this.updateSkuDetails()
        }
    }
    isok: boolean; imageT; spin = false; validate = true;
    updateSkuDetails() {
        const formData: FormData = new FormData();
        const updateObj: any = { facilitiesThreshold: [] }
        if (this.updateSKUForm.valid) {
            this.loading = true;
            if (this.skuObj.inventory.description == null) {
                this.skuObj.inventory.description = '';
            }
            if (this.skuObj.inventory.serialNumberStatus == null) {
                this.skuObj.inventory.serialNumberStatus = false;
            }
            if (this.accessoriesCheck) {
                updateObj.accessoriesList = (this.skuIds.id).concat(this.arr.item)
            } else {
                updateObj.accessoriesList = []
            }
            for (let i = 0; i < this.updateSKUForm.get('items').value.length; i++) {
                updateObj.facilitiesThreshold.push({
                    facility: { id: i + 1, facility: this.updateSKUForm.value.items[i].facility, facilityName: this.updateSKUForm.value.items[i].facility },
                    thresholdLevel: this.updateSKUForm.value.items[i].thresholdLevel
                })
            }
            updateObj.inventory = {
                "id": this.skuObj.inventory.id,
                "skuCode": this.skuObj.inventory.skuCode,
                "productName": this.skuObj.inventory.productName,
                "description": this.skuObj.inventory.description,
                "serialNumberStatus": this.skuObj.inventory.serialNumberStatus,
                "accessoriesStatus": this.accessoriesCheck,
            }
            if (this.details[0].check === true) {
                updateObj.inventory.active = true
            }
            else if (this.details[1].check === true) {
                updateObj.inventory.active = false
            }
            updateObj.createdTime = new Date();
            formData.append('inventory', JSON.stringify(updateObj))
            updateObj.createdTime = new Date();
            let userId = sessionStorage.getItem('userData');
            this.getIdBySKU();
            this.skuService.updateSku(updateObj, updateObj.inventory.id, userId).subscribe((data: any) => {
                if (data.data != null) {
                    this.validate = false;
                    this.spin = true;
                    this.isok = true
                    setTimeout(() => {
                        this.loading = false;
                        this.router.navigate(['./sku'])
                    }, 2500)

                } else {
                    this.loading = false;
                    this.existSKUMessage = data.error.message;
                    setTimeout(() => {
                        this.router.navigate(['./sku'])
                    }, 2500)
                }
            })
        } else {
            this.formValidate = true;
        }
    }

    gotoPreviousPage() {
        setTimeout(() => {
            this.router.navigate(['./sku'])
        })
    }

}



