import { Component } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { RentalService } from '../rental-products.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
    selector: 'pay-image',
    templateUrl: './rentalpay-image.component.html',
    styles: [`   
    `]
})

export class ShowPaymentImageComponent {

    loading = false;
    constructor(
        private readonly route: ActivatedRoute,
        private readonly router: Router, private readonly rentalService: RentalService,
        private readonly _sanitizer: DomSanitizer,
    ) {
    }
    dispatchId: any; dispatchImage: any; dispatchImage1: any; dispatchImage2: any;
    ngOnInit() {
        this.route.params.forEach((params: Params) => {
            if (params['id']) {
                this.dispatchId = params['id'];
            } else {
                console.log("Dispatch id is not getting")
            }
        })
        if (this.dispatchId != null) {
            this.rentalService.getDispatchImage(this.dispatchId).subscribe((data: any) => {
                if (data.error == null) {
                    if (data.data[0]) {
                        this.dispatchImage = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + data.data[0].binaryData)
                    }
                    if (data.data[1]) {
                        this.dispatchImage1 = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + data.data[1].binaryData)
                    }
                    if (data.data[2]) {
                        this.dispatchImage2 = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + data.data[2].binaryData)
                    }
                } else {
                    alert(data.error.message);
                    this.router.navigate(['/rental-products/rentals'])
                }
            })
        }
    }



}
