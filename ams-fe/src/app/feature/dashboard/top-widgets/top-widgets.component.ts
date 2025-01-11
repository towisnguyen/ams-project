import { Component, OnInit } from '@angular/core';
import { AssetControllerService } from 'src/app/api/assetController.service';

@Component({
  selector: 'app-top-widgets',
  templateUrl: './top-widgets.component.html',
  styleUrls: ['./top-widgets.component.scss'],
})
export class TopWidgetsComponent implements OnInit {
  projects =  [];
  sumOfAsset: any;
  projectItems: any;
  numberAssetOn: any;
  numberAssetOff: any;
  numberAssetMaintenance: any;
  sumOfAssetOn: any;
  sumOfAssetOff: any;
  sumOfAssetMaintenance: any;
  constructor( private assetControllerService: AssetControllerService ){ }

  ngOnInit(): void {
    this.assetControllerService.getTotalAssetsByProjectsUsingGET().subscribe((data) => {
      this.projects  = data.projects.map((project: any) => project.name);
      this.projectItems = data.projects.map((project: any) => project.count);
      this.numberAssetOn = data.projects.map((project: any) => project.status_on);
      this.numberAssetOff = data.projects.map((project: any) => project.status_off);
      this.numberAssetMaintenance = data.projects.map((project: any) => project.status_maintenance);
      // calculator values to show on top-widget
      this.sumOfAsset = this.projectItems.reduce((partialSum: any, value: any) => partialSum + value, 0);
      this.sumOfAssetOn = this.numberAssetOn.reduce((partialSum: any, value: any) => partialSum + value, 0);
      this.sumOfAssetOff = this.numberAssetOff.reduce((partialSum: any, value: any) => partialSum + value, 0);
      this.sumOfAssetMaintenance = this.numberAssetMaintenance.reduce((partialSum: any, value: any) => partialSum + value, 0);
    });
  }
}
