import { Component, OnInit } from '@angular/core';
import { AssetControllerService } from 'src/app/api/assetController.service';
import { Chart, registerables } from 'chart.js';
import { Project } from '../../../../model/project';


@Component({
  selector: 'app-status-chart',
  templateUrl: './status-chart.component.html',
  styleUrls: ['./status-chart.component.scss']
})
export class StatusChartComponent implements OnInit {

  statusChart: any = [];
  colorForBar = [
    'rgba(169, 62, 133, 1)', 'rgba(51, 95, 133, 1)', 'rgba(143, 168, 103, 1)', 'rgba(75, 192, 192, 1)', 'rgba(54, 162, 235, 1)',
    'rgba(153, 102, 255, 0.9)', 'rgba(201, 203, 207, 0.9)', 'rgba(255, 255, 106, 1)', 'rgba(255, 255, 216, 1)', 'rgba(255, 206, 216, 1)',
    'rgba(155, 206, 216, 1)', 'rgba(155, 169, 216, 1)', 'rgba(90, 169, 216, 1)', 'rgba(90, 169, 141, 1)', 'rgba(90, 169, 141, 0.9)',
    'rgba(104, 107, 141, 0.9)', 'rgba(104, 0, 141, 0.9)', 'rgba(115, 56, 80, 0.9)', 'rgba(180, 56, 80, 0.9)', 'rgba(180, 216, 133, 0.9)',
    ];
    constructor( private assetControllerService: AssetControllerService ){
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    this.assetControllerService.getTotalAssetsByProjectsUsingGET().subscribe((data) => {
      const projectNames = data.projects.map((project: any) => project.name);
      const itemsOn = data.projects.map((project: any) => project.status_on);
      const itemsOff = data.projects.map((project: any) => project.status_off);
      const itemsMaintenance = data.projects.map((project: any) => project.status_maintenance);

      this.statusChart = new Chart ('statusChart', {
      type: 'bar',
      data: {
        labels: projectNames,
        datasets: [
          {
            maxBarThickness: 80,
            data: itemsOn,
            borderColor: `#009900`,
            label: 'Powered On',
            backgroundColor: `#009900`,
            borderWidth: 1,
          },
          {
            maxBarThickness: 80,
            data: itemsOff,
            borderColor: `#CC0000`,
            label: 'Powered Off',
            backgroundColor: `#CC0000`,
            borderWidth: 1,
          },
          {
            maxBarThickness: 80,
            data: itemsMaintenance,
            borderColor: `#CCCC00`,
            label: 'Maintenance',
            backgroundColor: `#CCCC00`,
            borderWidth: 1,
          },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
        }
      });
    });
  }
}
