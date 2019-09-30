import { Component, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { endponitConfig } from '../../../environments/endpoint';
import { SKUService } from '../sku.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ModalDirective } from 'ngx-bootstrap';
import { AppState } from '../../app.service';

declare var $;
@Component({
    selector: 'sku-list',
    templateUrl: './sku.component.html',

})
export class SKUComponent {
    @ViewChild('lgModal') public lgModal: ModalDirective;
    @ViewChild('accessoryModal') public accessoryModal: ModalDirective;

    loading = false;
    constructor(private readonly http: HttpClient,public appService:AppState, private readonly _sanitizer: DomSanitizer, public router: Router, private readonly skuService: SKUService, ) {
        this.appService.getAllFacilities().subscribe(data => {
            let parse_obj = data.data;
            parse_obj.unshift({ id: 0, facility: 'All', facilityName: 'All' });
            this.locationDetails = data.data;
        })
    }
    ngOnInit() {
    }
    locationDetails: any = []
    skuStatuses: any = [{ id: 1, status: 'ACTIVE' }, { id: 0, status: 'IN-ACTIVE' }]
    skuStatus: any = 1;
    headers = new HttpHeaders().set('Authorization', JSON.parse(sessionStorage.getItem("Autherization")))
    private SKUDetailsData(res) {
        const body = res;
        if (body) {
            return body
        } else {
            return {}
        }
    }
    columnNames: any;
    tableId='#SKUDataTable table';
    order;
    SKUDetails = {
        dom: 'Bfrtip',
        "pagingType": "simple_numbers",
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        "paging": true,
        "filter": true,
        serverSide: true,
        ajax: (data, callback, settings) => {
            const table = $('#SKUDataTable table').DataTable();
            const info = table.page.info();     
            if (table.order()[0][1] === 'asc') {
                this.order = 0;
            } else {
                this.order = 1;
            }
            for (let i = 0; i < this.SKUDetails.columns.length; i++) {
                if (table.order()[0][0] + 1 === this.SKUDetails.columns[i].responsivePriority) {
                    this.columnNames = this.SKUDetails.columns[i].data;
                }
            }
            this.loading = true;
            if (table.search().trim().length >= 1) {
                this.http.get(`${endponitConfig.INVENTORY_ENDPOINT}getAllSkusBasedOnSorting/${this.facilityId}/${this.skuStatus}/${info.page}/${data.length}/${this.columnNames}/${this.order}/${table.search()}`, { headers: this.headers })
                    .map(this.SKUDetailsData)
                    .catch((error: any) => {
                        return Observable.throwError(error);
                    })
                    .subscribe((jsondata:any) => {
                        this.loading = false;
                        callback({
                            data: jsondata.data == null ? [] : jsondata.data.list,
                            recordsTotal: jsondata.data == null ? [] : jsondata.data.total,
                            recordsFiltered: jsondata.data == null ? [] : jsondata.data.total,
                        })
                    })
            }
            else {
                this.http.get(`${endponitConfig.INVENTORY_ENDPOINT}getAllSkusBasedOnSorting/${this.facilityId}/${this.skuStatus}/${info.page}/${data.length}/${this.columnNames}/${this.order}/null`, { headers: this.headers })
                    .map(this.SKUDetailsData)
                    .catch((error: any) => {
                        return Observable.throwError(error);
                    })
                    .subscribe((jsondata) => {
                        this.loading = false;
                        callback({
                            data: jsondata.data == null ? [] : jsondata.data.list,
                            recordsTotal: jsondata.data == null ? [] : jsondata.data.total,
                            recordsFiltered: jsondata.data == null ? [] : jsondata.data.total,
                        })
                    })
            }

        },
        columns: [
            {
                data: 'skuCode', responsivePriority: 1
            },
            {
                data: 'SKUImage', responsivePriority: 2, className: 'text-center',
                'render': (data, type, full, meta) => {
                    if (data != null) {
                        return '<a><img class="sku_view" id="hover_view" src="data:image/png;base64,' + data + '" /></a>';
                    } else {
                        return '-';
                    }
                }
            },
            {
                data: 'accessoriesStatus',
                responsivePriority: 3,
                "className": "text-center",
                "render": (data, type, row, meta) => {
                    if (row['accessoryStatus']) {
                        return '<a title="Child SKUs" class="inventory-view"> <i class="fa fa-eye"></i></a>'
                    } else {
                        return '-'
                    }
                }
            },
            {
                data: 'productName', responsivePriority: 4
            },
            {
                data: 'thresholdLevel', responsivePriority: 5,  "className": "text-center"
            },
            {
                data: 'serialNumberStatus', responsivePriority: 6, "className": "text-center",
                render: (data) => {
                    if (data) {
                        return '<a> <i class="fa fa-check"></i></a>'
                    } else {
                        return '<a> <i style="color:#ff9980" class="fa fa-close"></i></a>'
                    }
                }
            },
            {
                data: 'description', responsivePriority: 7,
            },
            {
                data: 'barcodeId', responsivePriority: 8
            },
            {
                data: 'enabled',
                responsivePriority: 9,
                orderable: false,
                className: 'editcenter',
                "render": (data, type, row, meta) => {
                    if (row['productImage']) {
                        return '<a class="editor_edit"> <i class="fa fa-edit"></i></a>/<a title="Product Image" class="editor_view"> <i class="fa fa-picture-o" aria-hidden="true"></i></a>'
                    } else {
                        return '<a class="editor_edit"> <i class="fa fa-edit"></i></a>/<a class="editor_view">Add Image</a>'
                    }
                }
            }
        ],
        buttons: [
            {
                text: '<i class="fa fa-refresh"></i> Refresh',
                className: 'moreColumns btn btn-sm dataTableCustomButtonMargin',
                action: (e, dt, node, config) => {
                    if ($.fn.DataTable.isDataTable(this.tableId)) {
                        this.loadingView()
                    }
                }
            },
            { extend: 'colvis', text: 'More Columns', className: 'moreColumns', },
            {
                extend: 'csv', className: "moreColumns",
                text: '<i class="fa fa-file-o"> CSV Download</i>',
                exportOptions: {
                    columns: [0, 1, 3, 4, 5, 6, 7],
                    orthogonal: 'sort'
                },
                filename: () => { return this.getExportFileName(); }
            }
        ],
        "aaSorting": [[0, 'desc']],
        columnDefs: [
            { width: 70, targets: 0 }, { width: 50, targets: 1 }, { width: 50, targets: 2 }, { width: 350, targets: 3 }, { width: 80, targets: 4 },
            { width: 80, targets: 5 }, { width: 500, targets: 6 }, { width: 50, targets: 7 }, { width: 90, targets: 8 },
            {
                targets: [0],
                render: (data, type, row, meta) => {
                    if (type === 'sort') {
                        const char = data.slice(0, 1);
                        if (char === 0) {
                            return `"${data}"`
                        }
                        return data
                    }
                    return data;
                },
            },
            {
                "targets": [4],
                "orderable": false
            },
            {
                "targets": [5],
                "visible": false,
                "searchable": true
            },
            {
                "targets": [6],
                "visible": false,
                "searchable": true
            },]
        ,
        rowCallback: (row: Node, data: any | Object, index: number) => {
            $('td', row).unbind('click');
            $('a.editor_edit', row).bind('click', () => {
                this.editSKU(data);
            });
            $('a.editor_view', row).bind('click', () => {
                this.viewImage(data);
            });
            $('.sku_view', row).bind('click', () => {
                this.skuImage(data);
            });

            $('a.editor_remove', row).bind('click', () => {
                if (data.id === Number(localStorage.getItem('userData'))) {
                    window.alert('Logged in user can not be deleted')
                }
            });
            $('a.inventory-view', row).bind('click', () => {
                this.inventoryView(data);
            });
            return row;
        },
    };
    accessoryDetails: any;
    inventoryView(allData) {
        this.skuService.viewAccessoriesCount(allData.Inventoryid).subscribe((data: any) => {
            this.accessoryDetails = data.data;
            if (data.data.length !== 0) {
                this.accessoryModal.show()
            }
        })
    }
    public editSKU(skuData: any) {
        const link = ['/sku/update', skuData.skuCode];
        this.router.navigate(link);
    }
    public viewImage(skuData: any) {
        const link = ['/sku/view', skuData.skuCode];
        this.router.navigate(link);
    }
    skuImg: any; prodName: any; skuCod: any;
    public skuImage(skuData: any) {
        this.prodName = skuData.productName;
        this.skuCod = skuData.skuCode;
        this.skuService.getSKUImage(this.skuCod).subscribe((data: any) => {
            if (data != null) {
                const image = data.data.productImage;
                this.skuImg = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + image)
                this.lgModal.show();
            } else {
                alert(data.error.message)
            }
        })       
    }
    getExportFileName() {
        return 'SKU_Report_' + this.formatedDate()
    }
    loadingView() {
        const table = $(this.tableId).DataTable();
        table.search("").draw();
        this.loading = true;
        table.ajax.reload();       
    }
    facilityId = 0;
    getLocationDetails() {
        this.loadingView();
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
