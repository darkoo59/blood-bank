import { Component, OnInit } from '@angular/core';
import { take } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { QrCodesService } from './qr-codes.service';

@Component({
  selector: 'app-qr-codes',
  templateUrl: './qr-codes.component.html',
  styleUrls: ['./qr-codes.component.scss']
})
export class QrCodesComponent implements OnInit {

  qrCodes: any[] = [];

  constructor(private m_QrCodesService: QrCodesService, private m_UserService: UserService) { }

  ngOnInit() {
    this.m_UserService.m_Data$.pipe(take(1)).subscribe(data => {
      this.m_QrCodesService.fetchQRCodes(data?.id).pipe(take(1)).subscribe(resp => {
        console.log(resp)
        // for (let i = 0; i < resp.byteLength; i++) {
          const blob = new Blob([new Uint8Array(resp)], { type: 'image/png' });
          const blobUrl = URL.createObjectURL(blob);
          this.qrCodes.push(blobUrl);
        // }
      })
    })
  }

}
