import { Component, ViewChild } from '@angular/core';
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
import { FormGroup, FormControl, Validators } from '@angular/forms';

declare var $;
@Component({
    selector: 'returns-list',
    templateUrl: './returns-list.component.html',
    styles: [``]
})
export class ReturnsComponent {
    loading = false
    public QueryData: any = { startDate: '', endDate: '' };
    @ViewChild('returnModal') public returnModal: ModalDirective;
    @ViewChild('refundModal') public refundModal: ModalDirective;
    @ViewChild('refAmtModal') public refAmtModal: ModalDirective;
    private readonly model: Object = { date: { year: 2019, month: 10, day: 9 } };
    private readonly startDate: any;
    private readonly endDate: any;
    public QueryDataEndDate;
    public QueryDataStartDate;
    public isSearchQuerySubmitted;
    errorMessage: any = '';
    serviceErrorResponse: any = '';
    serviceErrorData: any = '';
    returnStatuses: any = [{ id: 0, status: 'ALL' }, { id: 1, status: 'QC PENDING' }, { id: 2, status: 'QC DONE' }]
    refundStatuses: any = [{ id: 0, status: 'ALL' }, { id: 1, status: 'PENDING' }, { id: 2, status: 'REFUNDED' }]
    channels: any = [{ id: 0, channel: 'ALL' }, { id: 1, channel: 'MBB WOOCOMMERCE' }, { id: 3, channel: 'AMAZON' },
    { id: 4, channel: 'AMAZON FLEX' }, { id: 5, channel: 'FLIPKART' }, { id: 6, channel: 'SNAPDEAL' }, { id: 7, channel: 'SHOPCLUES' }, { id: 8, channel: 'CUSTOM' }]
    returnTypes: any = [{ id: 0, type: 'ALL' }, { id: -1, type: 'REASONS NOT ADDED' }, { id: 1, type: 'CUSTOMER RETURN' }, { id: 2, type: 'CANCELLED' },
    { id: 3, type: 'COURIER RETURN' }, { id: 4, type: 'REPLACEMENT' }]
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
        height: '33px',
        selectionTxtFontSize: '11px',
        indicateInvalidDate: true,
    };
    constructor(
        public returnService: ReturnService,
        private readonly http: HttpClient,
        public router: Router) {
    }
    returnStatus: any = 0;
    refundStatus: any = 0;
    channel: any = 0;
    returnType: any = 0;
    userType: any;
    dateFormt: any = 'YYYY-MM-DD';
    refundAmtForm: FormGroup;
    ngOnInit() {
        this.refundAmtForm = new FormGroup({
            'refundAmount': new FormControl('', Validators.compose([Validators.required, Validators.pattern('^[0-9]+([.][0-9]+)?$')]))
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
        const table = $('#ReturnDataTable table').DataTable();
        table.search("").draw();
        table.ajax.reload();
    }
    headers = new HttpHeaders().set('Authorization', JSON.parse(sessionStorage.getItem("Autherization")))
    private ReturnDetailsData(res) {
        const body = res;
        if (body) {
            return body.data
        } else {
            return {}
        }
    }
    ReturnDetails = {
        dom: 'Bfrtip',
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        ajax: (data, callback, settings) => {
            this.loading = true;
            this.http.get(endponitConfig.RETURNS_API + `getReturnProducts/${this.QueryDataStartDate}/${this.QueryDataEndDate}/${this.channel}/${this.returnStatus}/${this.refundStatus}/${this.returnType}`,
                { headers: this.headers })
                .map(this.ReturnDetailsData)
                .subscribe((jsondata) => {
                    this.loading = false;
                    callback({ aaData: jsondata == null ? [] : jsondata })
                })
        },
        columns: [
            {
                data: 'dispatch.returnRequestNo',
            },
            {
                data: 'dispatch.invoiceId',
                "render": (data) => {
                    if (data == null) {
                        data = '-'
                    }
                    return data;
                }
            },
            {
                data: 'skuAndProduct',
                "render": (data, type, row, meta) => {
                    return data;
                }
            },
            {
                data: 'dispatch.barcode'
            },
            {
                data: 'serialNumber',
                "render": (serialNumber) => {
                    if (serialNumber == null) {
                        serialNumber = '-'
                    }
                    return serialNumber;
                }
            },
            {
                data: 'facility',
                "render": (facility) => {
                    if (facility == null) {
                        facility = '-'
                    }
                    return facility;
                }
            },
            {
                data: 'dispatch.createdTime',
                "render": (data) => {
                    if (data == null) {
                        return "-";
                    }
                    if (data.length > 10) {
                        const date = new Date(data)
                        const dd: any = date.getDate();
                        const mm: any = date.getMonth() + 1;
                        const yyyy = date.getFullYear();
                        return `${this.twoDigit(dd)}-${this.twoDigit(mm)}-${yyyy}`;
                    }
                    else {
                        return data;
                    }
                }
            },
            {
                data: 'dispatch.returnedDate',
                "render": (data, type, row, meta) => {
                    if (data == null) {
                        return '-'
                    }
                    else {
                        const dateAr = data.split('-');
                        return `${dateAr[2]}-${dateAr[1]}-${dateAr[0]}`;
                    }
                }
            },
            {
                data: 'channel.channelName',
                "render": (channelName) => {
                    if (channelName == null) {
                        channelName = '-'
                    }
                    return channelName;
                }
            },
            {
                orderable: false,
                className: 'editcenter',
                "render": (data, type, row, meta): any | undefined => {
                    if (row['return'] && row['refund']) {
                        if (this.userType === 'INVENTORY_MANAG') {
                            return '<a class="return"><i class="fa fa-eye"></i>Return</a>'
                        } else {
                            return '<a class="return"><i class="fa fa-eye"></i>Return</a> /<a class="refund"> <i class="fa fa-eye"></i>Refund</a>'
                        }
                    }
                    if (!row['return'] && row['refund']) {
                        if (this.userType === 'INVENTORY_MANAG') {
                            return '<a class="add_return"><i class="fa fa-plus"></i>Reason </a>'
                        }
                        if (this.userType === 'RETURN_MANAG' || this.userType === 'ACCOUNTANT_MANAG' || this.userType === 'ACCOUNTANT') {
                            return '<a class="refund"> <i class="fa fa-eye"></i>Refund</a>'
                        }
                        if (this.userType === 'SUPERADMIN' || this.userType === 'ADMIN') {
                            return '<a class="add_return"><i class="fa fa-plus"></i>Reason </a>/<a class="refund"> <i class="fa fa-eye"></i>Refund</a>'
                        } else {
                            return '-'
                        }
                    } else if (row['return'] && !row['refund']) {
                        if (this.userType === 'ACCOUNTANT_MANAG' || this.userType === 'ACCOUNTANT' || this.userType === 'SUPERADMIN' || this.userType === 'ADMIN') {
                            if (row['refundStatus'] == 0) {
                                if (this.userType === 'SUPERADMIN' || this.userType === 'ADMIN') {
                                    return '<a class="return"><i class="fa fa-eye"></i>Return</a> / <a class="add_refund_amount"> <i class="fa fa-plus"></i>Amount</a>'
                                } else {
                                    return '<a class="return"><i class="fa fa-eye"></i>Return</a>'
                                }
                            } else {
                                return '<a class="return"><i class="fa fa-eye"></i>Return</a> / <a class="add_refund"> <i class="fa fa-plus"></i>Refund</a>'
                            }
                        }
                        if (this.userType === 'RETURN_MANAG' || this.userType === 'INVENTORY_MANAG') {
                            return '<a class="return"><i class="fa fa-eye"></i>Return</a>'
                        }
                        else {
                            return '-'
                        }
                    } else if (!row['return'] && !row['refund']) {
                        if (this.userType === 'INVENTORY_MANAG' || this.userType === 'SUPERADMIN' || this.userType === 'ADMIN') {
                            return '<a class="add_return"><i class="fa fa-plus"></i>Reason</a>'
                        }
                        else {
                            return '-'
                        }
                    }
                }
            }
        ],
        "aaSorting": [[6, "desc"]],
        buttons: [
            {
                text: '<i class="fa fa-refresh"></i> Refresh',
                className: 'moreColumns btn btn-sm dataTableCustomButtonMargin',
                action: (e, dt, node, config) => {
                    if ($.fn.DataTable.isDataTable('#ReturnDataTable table')) {
                        this.loadingView()
                    }
                }
            },
            { extend: 'colvis', text: 'More Columns', className: 'moreColumns' },
            {
                extend: 'csv', className: "moreColumns",
                text: '<i class="fa fa-file-o"> CSV Download</i>',
                exportOptions: {
                    columns: [0, 1, 2, 3, 4, 5, 6],
                    orthogonal: 'sort'
                },
                filename: () => { return this.getExportFileName(); }
            }
        ],
        "columnDefs": [
            // {
            //     "targets": [0],
            //     "visible": false,
            //     "searchable": true
            // },
            {
                "targets": [2],
                "visible": false,
                "searchable": true
            },
        ],
        rowCallback: (row: Node, data: any | Object, index: number) => {
            $('td', row).unbind('click');
            $('a.add_refund', row).bind('click', () => {
                this.refundDetails(data);
            });
            $('a.add_return', row).bind('click', () => {
                this.returnedDetails(data);
            });
            $('a.return', row).bind('click', () => {
                this.returnsView(data);
            });
            $('a.refund', row).bind('click', () => {
                this.refundsView(data);
            });
            $('a.add_refund_amount', row).bind('click', () => {
                this.addRefundAmount(data);
            });
            return row;
        },
    };
    returnsView(data) {
        const id = data.dispatch.id;
        this.showReturns(id);
    }
    refundsView(data) {
        const id = data.dispatch.id;
        this.showRefunds(id);
    }
    dispatchId;
    addRefundAmount(data) {
        this.dispatchId = data.dispatch.id;
        this.refAmtModal.show()
    }
    submitted = false; amountSuccess; amountFail; btnDisable = false;
    addAmount() {
        if (this.refundAmtForm.valid) {
            this.btnDisable = true;
            this.returnService.sendRefundAmountMailForAccountant(this.refundAmtForm.get('refundAmount').value, this.dispatchId).subscribe((data: any) => {
                if (data.error == null) {
                    this.amountSuccess = data.data;
                    setTimeout(() => {
                        this.btnDisable = false;
                        this.amountSuccess = '';
                        this.hide();
                        this.loadingView();
                    }, 3000)
                } else {
                    this.btnDisable = false;
                    this.amountFail = data.error.messaage;
                    setTimeout(() => {
                        this.amountFail = '';
                    }, 3000)
                }
            })
        } else {
            this.submitted = true;
        }
    }
    hide() {
        this.refundAmtForm.get('refundAmount').setValue('');
        this.refAmtModal.hide();
        this.submitted = false;
    }
    returnDetails; table;
    showReturns(id) {
        this.returnService.returnView(id).subscribe((data: any) => {
            if (data.length !== 0 && data.data != null) {
                this.table = true;
                this.returnDetails = data.data
                this.returnModal.show()
            } else {
                this.table = false;
                this.returnModal.hide()
            }
        })
    }
    refundedDetails; details;
    showRefunds(id) {
        this.returnService.refundView(id).subscribe((data: any) => {
            if (data.length !== 0 && data.data != null) {
                this.details = true;
                this.refundedDetails = data.data
                this.refundModal.show()
            } else {
                this.details = false;
                this.refundModal.hide()
            }
        })
    }
    dispatchedDate1;
    refundDetails(data) {
        if (data.dispatch.createdTime == null) {
            const date = `${this.d.getFullYear()}-${this.twoDigit(this.d.getMonth() + 1)}-${this.twoDigit(this.d.getDate())}`;
            this.dispatchedDate1 = date;
        }
        if (data.dispatch.createdTime.length > 10) {
            const date = new Date(data.dispatch.createdTime)
            const dd: any = date.getDate();
            const mm: any = date.getMonth() + 1;
            const yyyy = date.getFullYear();
            this.dispatchedDate1 = `${yyyy}-${this.twoDigit(mm)}-${this.twoDigit(dd)}`;
        }
        const link = ['./returns/refund', this.dispatchedDate1, data.dispatch.id, data.dispatch.invoiceId];
        this.router.navigate(link);
    }
    dispatchedDate;
    returnedDetails(data) {
        if (data.dispatch.createdTime == null) {
            const date = `${this.d.getFullYear()}-${this.twoDigit(this.d.getMonth() + 1)}-${this.twoDigit(this.d.getDate())}`;
            this.dispatchedDate = date;
        }
        if (data.dispatch.createdTime.length > 10) {
            const date = new Date(data.dispatch.createdTime)
            const dd: any = date.getDate();
            const mm: any = date.getMonth() + 1;
            const yyyy = date.getFullYear();
            this.dispatchedDate = `${yyyy}-${this.twoDigit(mm)}-${this.twoDigit(dd)}`;
        }
        const link = ['./returns/return', this.dispatchedDate, data.dispatch.id, data.dispatch.invoiceId];
        this.router.navigate(link);
    }

    comments: any = '';
    commentsError: any;
    loaderbtn = false; cancel = false;
    sucessMsg: any = '';
    errorMsg: any = '';
    getExportFileName() {
        return 'Inventory_Transfer_Report_' + this.formatedDate()
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

