import { Component, OnInit } from '@angular/core';
import { AssetControllerService } from 'src/app/api/assetController.service';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-line-charts',
  templateUrl: './line-charts.component.html',
  styleUrls: ['./line-charts.component.scss'],
})
export class LineChartsComponent implements OnInit {

  lineChart: any = [];
  colorForLine = ['rgba(255, 99, 132, 1)', 'rgba(255, 159, 64, 1)', 'rgba(255, 205, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(54, 162, 235, 1)',];

  constructor( private assetControllerService: AssetControllerService ){
    Chart.register(...registerables);
  }

  ngOnInit(): void {
     this.loadProjectData()
   }

    private loadProjectData() {
      this.assetControllerService.getTotalAssetsByCategoriesUsingGET().subscribe((data) => {
        const categorieNames = data.categories.map((categorie: any) => categorie.name);
        const categorieItems = data.categories.map((categorie: any) => categorie.count);

    this.lineChart = new Chart ('lineChart', {
        type: 'line',
        data: {
          labels: categorieNames,
          datasets: [
            {
              data: categorieItems,
              label: 'Assets',
              borderWidth: 1.5,
              borderColor: this.colorForLine,
              backgroundColor: this.colorForLine,
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
