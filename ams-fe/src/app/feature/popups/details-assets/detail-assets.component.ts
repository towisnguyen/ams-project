import { AssetsManagementComponent } from '../../assets-management/assets-management.component';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AssetControllerService } from 'src/app/api/assetController.service';

// export interface data {
//   id: any
// }

@Component({
  selector: 'app-detail-assets',
  templateUrl: './detail-assets.component.html',
  styleUrls: ['./detail-assets.component.scss'],
})
export class DetailAssetsComponent {
  fullData: any;
  name = '';

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { id: any, name: any }
  ) {}

  ngOnInit(): void {
    this.name = this.data.name
  }
}
