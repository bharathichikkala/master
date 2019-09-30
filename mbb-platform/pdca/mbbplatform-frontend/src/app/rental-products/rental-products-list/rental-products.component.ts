import { Component, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Response, Headers } from '@angular/http'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { endponitConfig } from '../../../environments/endpoint';
import { ReturnService } from '../returns.service';
import { ModalDirective } from 'ngx-bootstrap';
import { IMyOptions, IMyDateModel, IMyDate } from 'mydatepicker';
import * as moment from 'moment';
import { RentalService } from '../rental-products.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { AppState } from '../../app.service';

declare var $;
@Component({
    selector: 'returns-list',
    templateUrl: './rental-products.component.html',
    styles: [``]
})
export class RentalComponent {
    loading = false
    public QueryData: any = { startDate: '', endDate: '' };
    @ViewChild('rentalModal') public rentalModal: ModalDirective;
    @ViewChild('extensionModal') public extensionModal: ModalDirective;
    @ViewChild('dispatchImageModal') public dispatchImageModal: ModalDirective;
    @ViewChild('convertedDetailsModel') public convertedDetailsModel: ModalDirective;
    @ViewChild('orderModal') public orderModal: ModalDirective;

    private readonly model: Object = { date: { year: 2019, month: 10, day: 9 } };
    private readonly startDate: any;
    private readonly endDate: any;
    public QueryDataEndDate;
    public QueryDataStartDate;
    public isSearchQuerySubmitted;
    errorMessage: any = '';
    serviceErrorResponse: any = '';
    serviceErrorData: any = '';
    locationDetails = []
    locationId = 0;
    serviceTypes = [{ id: 0, name: 'All' }, { id: 1, name: 'Oxygen Concentrators' }, { id: 2, name: 'Auto CPAP' }, { id: 3, name: 'BIPAP' },
    { id: 4, name: 'Portable Oxygen Concentrators' }, { id: 5, name: 'Stationary Concentrators' }, { id: 6, name: 'Power WheelChairs' }, { id: 7, name: 'Patient Hoist' }]
    serviceId = 0;
    statuses: any = [{ id: 0, status: 'ALL' }, { "id": 1, "status": "PENDING" }, { "id": 2, "status": "DISPATCHED" },
    {
        "id": 3,
        "status": "ORDERED"
    },
    {
        "id": 4,
        "status": "RETURNED"
    },
    {
        "id": 5,
        "status": "REQUESTED"
    },
    {
        "id": 6,
        "status": "CONTACTED"
    },
    {
        "id": 7,
        "status": "EXTENDED"
    },
    {
        "id": 8,
        "status": "CLOSED"
    },
    {
        "id": 9,
        "status": "PROCESSED"
    }]
    statusType = 0;
    d = new Date()
    private readonly myDatePickerOptions: IMyOptions = {
        openSelectorOnInputClick: true,
        inline: false,
        editableDateField: false,
        disableDateRanges: [{
            begin: { year: (new Date()).getFullYear(), month: (new Date()).getMonth() + 1, day: (new Date()).getDate() + 1 },
            end: { year: 9999, month: 12, day: 31 }
        }],
        dateFormat: 'dd-mm-yyyy',
        showTodayBtn: false,
        showClearDateBtn: false,
        height: '30px',
        selectionTxtFontSize: '14px',
        indicateInvalidDate: true,
    };
    constructor(
        private readonly http: HttpClient, private readonly rentalService: RentalService,
        public router: Router,public appService:AppState, private readonly _sanitizer: DomSanitizer, private readonly cdr: ChangeDetectorRef) {
            this.appService.getAllRentalFacilities().subscribe(data => {
                let parse_obj = data.data;
                parse_obj.unshift({ id: 0, facility: 'All', facilityName: 'All' });
                this.locationDetails = data.data;
            })
        }
    returnStatus: any = 0;
    refundStatus: any = 0;
    channel: any = 0;
    returnType: any = 0;
    userType: any;
    dateFormt: any = 'YYYY-MM-DD';
    orderForm: FormGroup;
    invoiceNumber;
    ngOnInit() {
        this.orderForm = new FormGroup({
            'invoiceNumber': new FormControl('', Validators.compose([Validators.required]))
        })
        this.userType = sessionStorage.getItem('userRole')
        this.QueryData.endDate = { 'date': { 'year': this.d.getFullYear(), 'month': this.d.getMonth() + 1, 'day': this.d.getDate() } }
        this.QueryData.startDate = {
            'date': {
                'year': this.d.getFullYear(), 'month': this.d.getMonth(),
                'day': this.d.getDate() === 29 ? 28 : this.d.getDate() === 30 ? 28 : this.d.getDate() === 31 ? 28 : this.d.getDate()
            }
        }
        const stYear = this.QueryData.startDate.date.year;
        const stMonth = this.QueryData.startDate.date.month;
        const stDay = this.QueryData.startDate.date.day;
        const startDate = `${stYear}-${stMonth}-${stDay}`;
        this.QueryDataStartDate = moment(startDate, this.dateFormt).format(this.dateFormt);

        const endYear = this.QueryData.endDate.date.year;
        const endMonth = this.QueryData.endDate.date.month;
        const endDay = this.QueryData.endDate.date.day;
        const endDate = `${endYear}-${endMonth}-${endDay}`;
        this.QueryDataEndDate = moment(endDate, this.dateFormt).format(this.dateFormt);
    }
    public QuerySubmit(): void {
        if (this.QueryData.startDate !== '') {
            const stYear = this.QueryData.startDate.date.year;
            const stMonth = this.QueryData.startDate.date.month;
            const stDay = this.QueryData.startDate.date.day;
            const startDate = `${stYear}-${stMonth}-${stDay}`;
            this.QueryDataStartDate = moment(startDate, this.dateFormt).format(this.dateFormt);
        } else {
            this.QueryDataStartDate = ''
        }

        if (this.QueryData.endDate !== '') {
            const endYear = this.QueryData.endDate.date.year;
            const endMonth = this.QueryData.endDate.date.month;
            const endDay = this.QueryData.endDate.date.day;
            const endDate = `${endYear}-${endMonth}-${endDay}`;
            this.QueryDataEndDate = moment(endDate, this.dateFormt).format(this.dateFormt);
        } else {
            this.QueryDataEndDate = '';
        }

        if (this.QueryDataStartDate === '' && this.QueryDataEndDate === '') {
            this.isSearchQuerySubmitted = true;
        } else {
            try {
                if (new Date(this.QueryDataStartDate) > new Date(this.QueryDataEndDate)) {
                    this.errorMessage = 'End date should be greater than start date';
                    setTimeout(() => {
                        this.errorMessage = '';
                    }, 3000)
                } else {
                    this.errorMessage = '';
                    this.loadingView()
                }
            } catch (error) {
                this.serviceErrorResponse = error.exception;
                this.serviceErrorData = true;
            }
            this.isSearchQuerySubmitted = false;
        }
    };
    twoDigit(number) {
        return (number < 10 ? '0' : '') + number
    }
    loadingView() {
        const table = $('#RentalDataTable table').DataTable();
        table.search("").draw();
        table.ajax.reload();
    }
    headers = new HttpHeaders().set('Authorization', JSON.parse(sessionStorage.getItem("Autherization")))
    private RentalDetailsData(res) {
        const body = res;
        if (body) {
            return body.data
        } else {
            return {}
        }
    }
    RentalDetails = {
        dom: 'Bfrtip',
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        ajax: (data, callback, settings) => {
            this.loading = true;
            this.http.get(endponitConfig.RENTALS_API + `getAllRentalProducts/${this.QueryDataStartDate}/${this.QueryDataEndDate}/${this.statusType}/${this.locationId}/${this.serviceId}`,
                { headers: this.headers })
                .map(this.RentalDetailsData)
                .subscribe((jsondata) => {
                    this.loading = false;
                    callback({ aaData: jsondata == null ? [] : jsondata })
                })
        },
        columns: [
            {
                data: 'rental.rentalId',
                "render": (data, type, row, meta) => {
                    if (data == null) {
                        data = '-'
                    }
                    if (row['rental'].dispatchStatusId.id !== 7) {
                        return data
                    } else {
                        return `<a class="view">${data}</a>`
                    }
                }
            },
            {
                data: 'rental.customerDetailsId.name',
                "render": (data) => {
                    if (data == null) {
                        data = '-'
                    }
                    return `<a class="showCustomerDetails">${data}</a>`;
                }
            },
            {
                data: 'rental.customerDetailsId.phone',
                "render": (data) => {
                    if (data == null) {
                        data = '-'
                    }
                    return data;
                }
            },
            {
                data: 'rental.rentalServiceType.type',
                "render": (type) => {
                    if (type == null) {
                        type = '-'
                    }
                    return type;
                }
            },
            {
                data: 'rental.requestedDate',
                "render": (data) => {
                    if (data == null) {
                        return '-'
                    }
                    const dArr = data.split("-");
                    return `${dArr[2]}-${dArr[1]}-${dArr[0]}`;
                }
            },
            {
                data: 'rental.dispatchId.paymentModes.types',
                "render": (paymentModes) => {
                    if (paymentModes == null) {
                        paymentModes = '-'
                    }
                    return paymentModes;
                }
            },
            {
                data: 'rental.rentalAmount',
                "render": (data) => {
                    if (data == null) {
                        data = '-'
                    }
                    if (data % 1 === 0) {
                        return data + ".00"
                    }
                    return data;
                }
            },
            {
                data: 'rental.rentalDays',
                "render": (rentalDays) => {
                    if (rentalDays == null) {
                        rentalDays = '-'
                    }
                    return rentalDays;
                }
            },
            {
                data: 'rental.invoiceNumber',
                "render": (invoiceNumber) => {
                    if (invoiceNumber == null) {
                        invoiceNumber = '-'
                    }
                    return invoiceNumber;
                }
            },
            {
                data: 'skuCode',
                "render": (skuCode) => {
                    if (skuCode == null) {
                        skuCode = '-'
                    }
                    return skuCode;
                }
            },
            {
                data: 'productName',
                "render": (productName) => {
                    if (productName == null) {
                        productName = '-'
                    }
                    return productName;
                }
            },
            {
                data: 'rental.deliveredBy',
                "render": (deliveredBy) => {
                    if (deliveredBy == null) {
                        deliveredBy = '-'
                    }
                    return deliveredBy;
                }
            },
            {
                data: 'rental.dispatchStatusId.status',
                "render": (status) => {
                    if (status == null) {
                        status = '-'
                    }
                    return status;
                }
            },
            {
                data: 'qrCode',
                "render": (barCode) => {
                    if (barCode == null) {
                        barCode = '-'
                    }
                    return barCode;
                }
            },
            {
                data: 'enabled',
                orderable: false,
                className: 'text-center',
                "render": (data, type, row, meta) => {
                    if (!row['paymentImage']) {
                        return '-'
                    }
                    return '<a title="Image" class="show"> <i class="fa fa-eye"></i></a>';
                }
            },
            {
                data: 'enabled',
                orderable: false,
                className: 'editcenter',
                "render": (data, type, row, meta) => {
                    if (row['extension']) {
                        return '<a title="Rent Product Details" class="eye"> <i class="fa fa-eye"></i></a> / <a class="extends"> Extend</a>'
                    }
                    if (row['rental'].dispatchStatusId.id === 7 || row['rental'].dispatchStatusId.id === 2) {
                        return '<a title="Rent Product Details" class="eye"> <i class="fa fa-eye"></i></a>'
                    }
                    if (row['rental'].dispatchStatusId.id === 5 || row['rental'].dispatchStatusId.id === 8 || row['rental'].dispatchStatusId.id === 1
                        || row['rental'].dispatchStatusId.id === 6) {
                        return '<a class="edit"> <i class="fa fa-edit"></i></a> / <a class="convert"> Rent</a>'
                    }
                    return '<a class="eye" title="Rent Product Details"> <i class="fa fa-eye"></i></a>'
                }
            }
        ],
        "aaSorting": [[0, "desc"]],
        buttons: [
            {
                text: '<i class="fa fa-refresh"></i> Refresh',
                className: 'moreColumns btn btn-sm dataTableCustomButtonMargin',
                action: (e, dt, node, config) => {
                    if ($.fn.DataTable.isDataTable('#RentalDataTable table')) {
                        this.loadingView()
                    }
                }
            },
            { extend: 'colvis', text: 'More Columns', className: 'moreColumns' },
            {
                extend: 'csv', className: "moreColumns",
                text: '<i class="fa fa-file-o"> CSV Download</i>',
                exportOptions: {
                    orthogonal: 'sort'
                },
                filename: () => { return this.getExportFileName(); }

            }
        ],
        "columnDefs": [{ width: 80, targets: 15 },
        {
            "targets": [5],
            "visible": false,
            "searchable": true
        },
        {
            "targets": [7],
            "visible": false,
            "searchable": true
        },

        {
            "targets": [9],
            "visible": false,
            "searchable": true
        },
        {
            "targets": [10],
            "visible": false,
            "searchable": true
        },
        {
            "targets": [11],
            "visible": false,
            "searchable": true
        },
        {
            "targets": [13],
            "visible": false,
            "searchable": true
        },

        ],
        rowCallback: (row: Node, data: any | Object, index: number) => {
            const self = this;
            $('td', row).unbind('click');
            $('a.showCustomerDetails', row).bind('click', () => {
                self.customerDetails(data);
            });
            $('a.edit', row).bind('click', () => {
                this.editRental(data);
            });
            $('a.extends', row).bind('click', () => {
                this.extendRental(data);
            });
            $('a.view', row).bind('click', () => {
                this.viewRental(data);
            });
            $('a.convert', row).bind('click', () => {
                this.convertRental(data);
            });
            $('a.show', row).bind('click', () => {
                this.showImage(data);
            });
            $('a.eye', row).bind('click', () => {
                this.showDetails(data);
            });
            $('a.order', row).bind('click', () => {
                this.orderRequest(data);
            });
            return row;
        },
    };
    rentalId: any;
    orderRequest(data) {
        this.rentalId = data.rental.id;
        this.orderModal.show();
    }
    rentalSuccess; rentalFail; submitted = false; btnDisable = false;
    makeOrder() {
        this.btnDisable = true;
        if (this.orderForm.valid) {
            this.rentalService.makeOrder(this.invoiceNumber, this.rentalId).subscribe((data: any) => {
                if (data.error == null) {
                    this.rentalSuccess = 'Order Generated Successfully';
                    setTimeout(() => {
                        this.orderModal.hide();
                        this.rentalSuccess = '';
                        this.loading = false;
                        this.router.navigate(['./rental-products'])
                    }, 3000)
                } else {
                    this.btnDisable = false;
                    this.rentalFail = data.error.message;
                    this.loading = false;
                    setTimeout(() => {
                        this.rentalFail = '';
                    }, 3000)
                }
            })
        } else {
            this.btnDisable = false;
            this.submitted = true;
        }
    }

    hideOrder() {
        this.orderModal.hide()
        this.orderForm.get('invoiceNumber').setValue('');
    }
    dispatchImage: any; dispatchImage1: any; dispatchImage2: any;
    showImage(data) {
        // if (data.dispatchId != null) {
        //     this.rentalService.getDispatchImage(data.dispatchId).subscribe((data: any) => {
        //         if (data.error == null) {
        //             if (data.data[0]) {
        //                 this.dispatchImage = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + data.data[0].binaryData)
        //             }
        //             if (data.data[1]) {
        //                 this.dispatchImage1 = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + data.data[1].binaryData)
        //             }
        //             if (data.data[2]) {
        //                 this.dispatchImage2 = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + data.data[2].binaryData)
        //             }
        //             this.dispatchImageModal.show();
        //         } else {
        //             alert(data.error.message)
        //         }
        //     })
        // }
        const link = ['/rental-products/payimage', data.dispatchId];
        this.router.navigate(link);
    }
    convertRental(data) {
        const link = ['/rental-products/convert', data.rental.id];
        this.router.navigate(link);
    }
    viewExtensions: any;
    viewRental(data) {
        this.rentalService.viewExtensions(data.rental.id).subscribe(data => {
            if (data.error == null) {
                this.viewExtensions = data.data;
                this.extensionModal.show();
            } else {
                alert(data.error.message)
            }
        })
    }
    extendRental(data) {
        const link = ['/rental-products/extend', data.rental.id];
        this.router.navigate(link);
    }
    editRental(data) {
        const link = ['/rental-products/update', data.rental.id];
        this.router.navigate(link);
    }
    custDetails: any; details = false;
    customerDetails(data) {
        this.details = true;
        this.custDetails = data.rental.customerDetailsId;
        this.rentalModal.show()
    }
    convertedDetails: any; convertShow = false;
    showDetails(data) {
        this.convertShow = true;
        this.convertedDetails = data.rental;
        this.convertedDetailsModel.show();
    }
    getExportFileName() {
        return 'Rental_Report_' + this.formatedDate()
    }
    formatedDate() {
        const date = new Date(),
            year = date.getFullYear(),
            month = (date.getMonth() + 1).toString(),
            formatedMonth = (month.length === 1) ? ("0" + month) : month,
            day = date.getDate().toString(),
            formatedDay = (day.length === 1) ? ("0" + day) : day,
            hour = date.getHours().toString(),
            formatedHour = (hour.length === 1) ? ("0" + hour) : hour,
            minute = date.getMinutes().toString(),
            formatedMinute = (minute.length === 1) ? ("0" + minute) : minute,
            second = date.getSeconds().toString(),
            formatedSecond = (second.length === 1) ? ("0" + second) : second;
        return `${formatedDay}${formatedMonth}${year}_${formatedHour}${formatedMinute}${formatedSecond}`;
    };
}

