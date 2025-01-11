import { Component, OnInit } from '@angular/core';
import { AssetControllerService } from 'src/app/api/assetController.service';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-bar-charts',
  templateUrl: './bar-charts.component.html',
  styleUrls: ['./bar-charts.component.scss'],
})
export class BarChartsComponent implements OnInit {
  barChart: any = [];

  constructor( private assetControllerService: AssetControllerService ){
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    this.loadProjectData()
   }

    private loadProjectData() {
      this.assetControllerService.getTotalAssetsByProjectsUsingGET().subscribe((data) => {
        const projectNames = data.projects.map((project: any) => project.name);
        const projectItems = data.projects.map((project: any) => project.count);

      this.barChart = new Chart ('barChart', {
        type: 'bar',
        data: {
          labels: projectNames,
          datasets: [
          {
            barPercentage: 0.5,
            maxBarThickness: 80,
            data: projectItems,
            label: 'Assets',
            borderWidth: 1,
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
