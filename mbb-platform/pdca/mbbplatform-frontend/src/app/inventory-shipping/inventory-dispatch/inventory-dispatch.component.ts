import { Component, EventEmitter, Input, OnInit, Output, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Params,Router} from '@angular/router';
import { FormBuilder,FormsModule, FormGroup, Validators, ValidatorFn, FormControl, AbstractControl, FormArray } from '@angular/forms';
import { InventoryShippingService } from '../inventory-shipping.service'
import { endponitConfig } from '../../environments/endpoints';
import { Global } from '../../shared/global';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
    selector: 'inventory-dispatch',
    templateUrl: './inventory-dispatch.component.html'
})

export class InventoryDispatchComponent implements OnInit {
    selectedTransportType = null;
    transportTypes: any = [];
    ownTransportForm: FormGroup;
    privateTransportForm: FormGroup;
    documentsSubmitStatus = false;
    uploadedFiles: any = [];

    constructor(
        private readonly _sanitizer: DomSanitizer,
        private readonly _shippingService: InventoryShippingService,
        private readonly route: ActivatedRoute,
        private readonly fb: FormBuilder,
        private readonly router: Router,
        private readonly cdr: ChangeDetectorRef) {
    }
    packageName: any = "";
    packageId: any = "";
    ngOnInit() {
        this.route.params.forEach((params: Params) => {
            this.packageName = params['packageName']
            this.packageId = params['id']
        })
        this.transportValidations()
    }

    selectedImage: any;
    public picked(event, field) {
        this.selectedImage = field;
        const fileList: FileList = event.target.files;
        if (fileList.length > 0) {
            const file: File = fileList[0];
            if (field === 1) {
                this.handleInputChange(file);
            }
            else if (field === 2) {
                this.handleInputChange(file);
            }
            else if (field === 3) {
                this.handleInputChange(file);
            }
        }
        else {
            alert("No file selected");
        }
    }
    image1: any = '';
    image2: any = '';
    image3: any = '';

    handleInputChange(files) {
        const file = files;
        const reader = new FileReader();
        const n = file.type.lastIndexOf('/');
        const fileType = file.type.substring(n + 1);

        if (fileType === 'png' || fileType === 'jpg' || fileType === 'jpeg') {
            reader.onload = (e: any) => {
                file.src = e.target.result;
                if (this.selectedImage === 1) {
                    this.uploadedFiles[0] = file
                } else if (this.selectedImage === 2) {
                    this.uploadedFiles[1] = file
                } else {
                    this.uploadedFiles[2] = file
                }
                const id = this.selectedImage
                const reader = e.target;
                const base64result = reader.result.substr(reader.result.indexOf(',') + 1);
                switch (id) {
                    case 1:
                        this.image1 = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
                            + base64result);
                        break;
                    case 2:
                        this.image2 = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
                            + base64result);
                        break;
                    case 3:
                        this.image3 = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
                            + base64result);
                        break;
                }
            };
            reader.readAsDataURL(file);
        }
        else {
            const path1: any = document.getElementById('file-input1')
            const path2: any = document.getElementById('file-input2')
            const path3: any = document.getElementById('file-input3')
            path1.value = '';
            path2.value = '';
            path3.value = '';
            alert('Invalid file format');
            return;
        }
    }

    imgDelete(id) {
        this.uploadedFiles.splice(id, 1)
        switch (id) {
            case 1:
                this.image1 = null;
                break;
            case 2:
                this.image2 = null;
                break;
            case 3:
                this.image3 = null;
                break;
        }
    }

    loading = false;
    documentsSubmit() {
        const formData: FormData = new FormData();
        this.uploadedFiles.forEach(x => {
            formData.append('files', x, x.name);
        })
        formData.append('id', this.packageId)

        this._shippingService.uploadDocuments(formData, {}).subscribe((data: any) => {
            if (data.error == null) {
                this.transportSuccessMsg = "Transport details and documents uploaded successfully";
                setTimeout(() => {
                    this.loading = false;
                    this.transportSuccessMsg = '';
                    this.router.navigate(['./shipping'])
                }, 5000)
            }
        });
    }

    transportValidations() {
        this.ownTransportForm = new FormGroup({
            'driverName': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[a-zA-Z*" "]{3,30}$')])),
            'vehicleNumber': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[a-zA-Z0-9]{6,16}')])),
            'driverNumber': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[0-9]{10}$')])),
            'driverNumber_alter': new FormControl(null, Validators.compose([Validators.pattern('[0-9]{10}$')])),
        }, InventoryDispatchComponent.compareValueValidators);

        this.privateTransportForm = new FormGroup({
            'shippingAggr': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[a-zA-Z*" "]{3,10}$')])),
            'courierName': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[a-zA-Z*" "]{3,10}$')])),
            'trackingNumber': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_.-]{3,30}$')])),
        });
        this.getTransportTypes()
    }


    static compareValueValidators(formGroup) {
        const returnObj: any = {};
        if (formGroup.controls.driverNumber_alter.value === formGroup.controls.driverNumber.value) {
            returnObj.SAMENUMBER = true
        }
        return returnObj;
    }
    getTransportTypes() {
        this._shippingService.getTranportTypes().subscribe((data: any) => {
            if (data.error == null) {
                this.transportTypes = data.data;
            }
        })
    }
    ownSubmitted = false;
    submitted = false;
    selfTransportSuccessMsg = '';
    selfTransportErrorMsg = '';
    selfTransportDetails() {
        if (this.ownTransportForm.valid) {
            this.loading = true;
            const selfObj = {
                "driverName": this.ownTransportForm.get('driverName').value,
                "vehicleNumber": this.ownTransportForm.get('vehicleNumber').value,
                "driverNumber": this.ownTransportForm.get('driverNumber').value,
                driverAlternateNumber: this.ownTransportForm.get('driverNumber_alter').value,
                "transferInventoryId": {
                    "id": this.packageId
                }
            }
            this._shippingService.addSelfTransportDetails(selfObj).subscribe((data) => {
                if (data.error == null) {
                    this.documentsSubmit()
                } else {
                    this.loading = false;
                    this.selfTransportErrorMsg = data.error.message;
                    setTimeout(() => {
                        this.selfTransportErrorMsg = ''
                    }, 3000)
                }
            })
        } else {
            this.ownSubmitted = true;
        }
    }
    transportSuccessMsg;
    transportErrorMsg;
    aggregatorTransportDetails() {
        if (this.privateTransportForm.valid) {
            this.loading = true;
            const aggrObj = {
                "shippingAggregator": this.privateTransportForm.get('shippingAggr').value,
                "courierName": this.privateTransportForm.get('courierName').value,
                "trackingNumber": this.privateTransportForm.get('trackingNumber').value,
                "transferInventoryId": {
                    "id": this.packageId
                }
            }
            this._shippingService.addPrivateTransportDetails(aggrObj).subscribe((data) => {
                if (data.error == null) {
                    this.documentsSubmit()
                } else {
                    this.loading = false;
                    this.transportErrorMsg = data.error.message;
                    setTimeout(() => {
                        this.selfTransportErrorMsg = ''
                    }, 3000)
                }
            })
        } else {
            this.submitted = true;
        }
    }
}
