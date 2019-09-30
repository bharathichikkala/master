import { Component, ViewChild } from '@angular/core';
import { Response, Headers } from '@angular/http'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { endponitConfig } from '../../../environments/endpoint';
import { DemoProductsService } from '../demo-products.service';
import { ModalDirective } from 'ngx-bootstrap';
import { IMyOptions, IMyDateModel, IMyDate } from 'mydatepicker';
import * as moment from 'moment';
import { retry } from 'rxjs/operators';

declare var $;
@Component({
    selector: 'products-list',
    templateUrl: './products-list.component.html',
    styles: [``]
})
export class DemoProductsListComponent {
    loading = false
    public QueryData: any = { startDate: '', endDate: '' };

    d = new Date()
    private readonly myDatePickerOptions: IMyOptions = {
        openSelectorOnInputClick: true,
        inline: false,
        editableDateField: false,
        disableDateRanges: [{
            begin: {
                year: (new Date()).getFullYear(), month: (new Date()).getMonth() + 1,
                day: (new Date()).getDate() + 1
            }, end: { year: 9999, month: 12, day: 31 }
        }],
        dateFormat: 'dd-mm-yyyy',
        showTodayBtn: false,
        showClearDateBtn: false,
        height: '30px',
        selectionTxtFontSize: '14px',
        indicateInvalidDate: true,
    };
    adminType: any;
    constructor(
        private demoProductsService: DemoProductsService,
        private readonly http: HttpClient,
        public router: Router) {
        this.adminType = sessionStorage.getItem('userRole') == 'SUPERADMIN' ? true : false;
    }
    returnStatus: any = 0;
    refundStatus: any = 0;
    channel: any = 0;
    returnType: any = 0;
    userType: any;
    warranty_id: any = 0
    statusId: any = 0
    warrantyStatuses = [{ id: 0, name: "All" }, { id: 1, name: "Yes" }, { id: 2, name: "No" }]
    quotationStatuses = [{ id: 0, status: "ALL" }, { id: 1, status: "PENDING" }, { id: 2, status: "DISPATCHED" }, { id: 3, status: "ORDERED" }, { id: 4, status: "RETURNED" }];
    QueryDataStartDate: any
    paymentStatuses = [{ id: 0, status: "All" }, { id: 1, status: "Completed" }, { id: 2, status: "Pending" }]
    QueryDataEndDate: any;
    paymentStatusId: any = 0;
    isSearchQuerySubmitted: any
    errorMessage: any = '';
    serviceErrorResponse: any = '';
    serviceErrorData: any = '';
    dateFormat: any = 'YYYY-MM-DD';
    // @ViewChild('taxModal') public taxModal: ModalDirective;
    ngOnInit() {
        this.userType = sessionStorage.getItem('userRole')
        this.QueryData.endDate = { 'date': { 'year': this.d.getFullYear(), 'month': this.d.getMonth() + 1, 'day': this.d.getDate() } }
        this.QueryData.startDate = {
            'date': {
                'year': this.d.getFullYear(), 'month': this.d.getMonth(),
                'day': this.d.getDate() == 29 ? 28 : this.d.getDate() == 30 ? 28 : this.d.getDate() == 31 ? 28 : this.d.getDate()
            }
        }
        const stYear = this.QueryData.startDate.date.year;
        const stMonth = this.QueryData.startDate.date.month;
        const stDay = this.QueryData.startDate.date.day;
        const startDate = stYear + '-' + stMonth + '-' + stDay;
        this.QueryDataStartDate = moment(startDate, this.dateFormat).format(this.dateFormat);

        const endYear = this.QueryData.endDate.date.year;
        const endMonth = this.QueryData.endDate.date.month;
        const endDay = this.QueryData.endDate.date.day;
        const endDate = endYear + '-' + endMonth + '-' + endDay;
        this.QueryDataEndDate = moment(endDate, this.dateFormat).format(this.dateFormat);
    }
    public QuerySubmit(): void {
        if (this.QueryData.startDate !== '') {
            const stYear = this.QueryData.startDate.date.year;
            const stMonth = this.QueryData.startDate.date.month;
            const stDay = this.QueryData.startDate.date.day;
            const startDate = stYear + '-' + stMonth + '-' + stDay;
            this.QueryDataStartDate = moment(startDate, this.dateFormat).format(this.dateFormat);
        } else {
            this.QueryDataStartDate = ''
        }

        if (this.QueryData.endDate !== '') {
            const endYear = this.QueryData.endDate.date.year;
            const endMonth = this.QueryData.endDate.date.month;
            const endDay = this.QueryData.endDate.date.day;
            const endDate = endYear + '-' + endMonth + '-' + endDay;
            this.QueryDataEndDate = moment(endDate, this.dateFormat).format(this.dateFormat);
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

    }
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
    editProduct(data) {
        localStorage.setItem("demoId", data.demo.id);
        localStorage.setItem("customerId", data.demo.customerDetailsId.id)
        this.router.navigate(['/demo-products/update-product'])
    }
    viewData(data) {
        localStorage.setItem("productId", data.demo.id);
        this.router.navigate(['/demo-products/view-product'])
    }
    convertToOrder(data) {
        localStorage.setItem("productDemoId", data.demo.demoId);
        this.router.navigate(['/demo-products/demo-to-order'])
    }
    public details_id: any;
    mailStatus: boolean = false;
    public textClass: any = 'text-center';
    ReturnDetails = {
        dom: 'Bfrtip',
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        ajax: (data, callback, settings) => {
            this.loading = true;
            this.http.get(endponitConfig.DEMO_PRODUCTS_API + 'getAllDemoProductsDetails/' + this.QueryDataStartDate + '/' + this.QueryDataEndDate + '/' + this.statusId, { headers: this.headers })
                .map(this.ReturnDetailsData)
                .subscribe((jsondata) => {
                    this.loading = false;
                    callback({ aaData: jsondata == null ? [] : jsondata })
                })
        },
        columns: [
            {
                data: 'demo.demoId', responsivePriority: 1,
                "render": function (data) {
                    if (data == null) {
                        data = '-'
                    }
                    return data;
                }
            },
            {
                data: 'skuCode', responsivePriority: 2,
                "render": function (data) {
                    if (data == null) {
                        data = '-'
                    }
                    return data;
                }
            },
            {
                data: 'productName', class: this.textClass, responsivePriority: 3,
                "render": function (data) {
                    if (data == null) {
                        data = "-"
                    }
                    return data;
                }
            },
            // {
            //     data: 'demo.productPrice', responsivePriority: 4,
            //     class: this.textClass,
            //     "render": function (data) {
            //         if (data == null) {
            //             data = "-"
            //         }
            //         return data;
            //     }
            // },
            {
                data: 'demo.demoPrice', responsivePriority: 4,
                class: this.textClass,
                "render": function (data) {
                    if (data == null) {
                        data = "FREE"
                    }
                    return data;
                }
            },
            {
                data: 'demo.dispatchId.demoGivenBy', responsivePriority: 5,
                class: this.textClass,
                "render": function (data) {
                    if (data == null) {
                        data = "-"
                    }
                    return data;
                }
            },
            {
                data: 'demo.dispatchId.paymentModes.types', responsivePriority: 6,
                class: this.textClass,
                "render": function (data) {
                    if (data == null) {
                        data = "-"
                    }
                    return data;
                }
            },
            {
                data: 'demo.customerDetailsId', responsivePriority: 7,
                class: this.textClass,
                "render": function (data) {
                    if (data == null) {
                        data = "-"
                    }
                    return data.name + ',<br>' + data.emailId + ',<br>' + data.phone;
                }
            },
            {
                data: 'qrCode', responsivePriority: 8,
                class: this.textClass,
                "render": function (data) {
                    if (data == '') {
                        data = "-"
                    }
                    return data;
                }
            },
            {
                data: 'demo.dispatcstatusId.status', responsivePriority: 9,
                class: this.textClass,
                "render": function (data) {
                    if (data == null) {
                        data = "-"
                    }
                    return data;
                }
            },
            {
                data: 'demo.createdTime', responsivePriority: 10,
                class: this.textClass,
                "render": function (data) {
                    if (data == null) {
                        data = "-"
                    }
                    else {
                        const arr = data.substring(0, 10).split("-");
                        data = arr[2] + '-' + arr[1] + '-' + arr[0];
                    }
                    return data;
                }
            },
            {
                data: null,
                orderable: false,
                className: this.textClass,
                render: (data, type, row, meta) => {
                    if (data.demo.dispatcstatusId.status !== "PENDING") {
                        if (data.demo.dispatcstatusId.status == "DISPATCHED") {
                            if (this.userType !== 'DISPATCHER') {
                                return '<a title="View" class="editor_view"><i class="fa fa-eye"></i></a>/<a class="convert">Convert to Order</a>'
                            }
                            else {
                                return '<a title="View" class="editor_view"><i class="fa fa-eye"></i></a>'
                            }
                        }
                        else {
                            return '<a title="View" class="editor_view"><i class="fa fa-eye"></i></a>'
                        }

                    }
                    else {
                        if (this.userType !== 'DISPATCHER') {
                            return '<a title="View" class="editor_view"><i class="fa fa-eye"></i></a>/<a title="Update" class="editor_edit"> <i class="fa fa-edit"></i></a>'
                        }
                        else {
                            return '<a title="View" class="editor_view"><i class="fa fa-eye"></i></a>';
                        }
                    }

                },
                responsivePriority: 11
            }
        ],
        "aaSorting": [[0, "desc"]],
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
                    columns: [0, 1, 2, 3, 4, 5, 6, 7],
                    orthogonal: 'sort'
                },
                filename: () => { return this.getExportFileName(); }

            }
        ],
        "columnDefs": [
            {
                "targets": [6],
                "visible": false,
                "searchable": true
            },
            {
                "targets": [4],
                "visible": false,
                "searchable": true
            },
            {
                "targets": [7],
                "visible": false,
                "searchable": true
            }
        ],
        rowCallback: (row: Node, data: any | Object, index: number) => {
            $('td', row).unbind('click');
            $('a.editor_edit', row).bind('click', () => {
                this.editProduct(data);
            });
            // $('a.mail', row).bind('click', () => {
            //     this.sendEmail(data);
            // });
            $('a.editor_view', row).bind('click', () => {
                this.viewData(data);
            });
            $('a.convert', row).bind('click', () => {
                this.convertToOrder(data);
            });
            // $('a.payment', row).bind('click', () => {
            //     this.paymentDetails(data);
            // })
            return row;
        },
    };

    returnDetails; table;

    dispatchedDate1;


    comments: any = '';
    commentsError: any;
    loaderbtn = false; cancel = false;
    sucessMsg: any = '';
    errorMsg: any = '';
    getExportFileName() {
        return 'Demo_Products_Report_' + this.formatedDate()
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

