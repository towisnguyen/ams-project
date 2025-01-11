import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AssetControllerService } from '../../../../api/assetController.service';
import { Component, Inject, OnInit } from '@angular/core';

@Component({
  selector: 'app-tab-details-assets',
  templateUrl: './tab-details-assets.component.html',
  styleUrls: ['./tab-details-assets.component.scss']
})
export class TabDetailsComponent implements OnInit{

  fullData: any;

  constructor(
    private assetControllerService: AssetControllerService,
    @Inject(MAT_DIALOG_DATA) public data: { name: any }
  ) {}

  ngOnInit(): void {
    this.getDetails();
  }

  getDetails() {
    let button = document.getElementById('button');
    this.assetControllerService
      .getAllAssetsUsingGET(this.data.name)
      .subscribe((response) => {
        this.fullData = response;
        this.fullData = this.fullData.assetList[0];
        if (this.fullData.statusName === 'In Use') {
          button.style.backgroundColor = 'green';
        } else {
          button.style.backgroundColor = 'red';
        }
      });
  }
}
