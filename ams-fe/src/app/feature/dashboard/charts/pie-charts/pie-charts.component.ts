import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { AssetControllerService } from 'src/app/api/assetController.service';


@Component({
  selector: 'app-pie-charts',
  templateUrl: './pie-charts.component.html',
  styleUrls: ['./pie-charts.component.scss'],
})
export class PieChartsComponent implements OnInit {

  pieChart: any = [];
  colorForPie = [
    '#736AFF', '#6698FF', '#82CAFF', '#B0E2FF', '#307D7E', '#3EA99F', '#43BFC7', '#77BFC7', '#92C7C7', '#8EEBEC',
    '#4EE2EC', '#00FFFF', '#B7CEEC', '#C2DFFF', '#ADDFFF', '#BDEDFF', '#E0FFFF', '#E9CFEC', '#E3E4FA', '#FDEEF4', '#FCDFFF',
    '#F9B7FF', '#E6A9EC', '#C38EC7', '#D2B9D3', '#C6AEC7', '#64E986', '#6AFB92', '#87F717', '#8AFB17', '#B1FB17', '#CCFB5D',
    '#BCE954','#A0C544', 	'#FFFF00', '#FFF380', '#EDDA74', '#EAC117', '#FBB917', 	'#E9AB17', 	'#AF7817',
  ];

  constructor( private assetControllerService: AssetControllerService ){
    Chart.register(...registerables);
  }

  ngOnInit(): void {
      this.assetControllerService.getTotalAssetsByCategoriesUsingGET().subscribe((data) => {
        const categorieNames = data.categories.map((categorie: any) => categorie.name);
        const categorieItems = data.categories.map((categorie: any) => categorie.count);

      this.pieChart = new Chart ('pieChart', {
        type: 'pie',
        data: {
          labels: categorieNames,
          datasets: [
            {
              data: categorieItems,
              label: 'Assets',
              borderWidth: 1.5,
              backgroundColor: this.colorForPie,
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false
        }
        });
      });
  }
}
