import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { ProjectControllerService } from 'src/app/api/projectController.service';
import { AddProjectsComponent } from 'src/app/shared/components/add-projects/add-projects.component';
import { DeleteProjectComponent } from 'src/app/shared/components/delete-project/delete-project.component';
import { EditProjectsComponent } from 'src/app/shared/components/edit-projects/edit-projects.component';

@Component({
  selector: 'app-projects-management',
  templateUrl: './projects-management.component.html',
  styleUrls: ['./projects-management.component.scss']
})
export class ProjectsManagementComponent implements OnInit {
  projectID: number;
  totalProject: number;
  projectName: string;
  allStatus = ['Active', 'Deactive'];
  data: any;
  allProject: any;
  searchProjectName = '';
  selectedStatus= '';
  currentRole: any;


  constructor(
    private projectControllerService: ProjectControllerService,
    public dialog: MatDialog,
    private router: Router) {}

  ngOnInit(): void {
    this.getAllProjectsData();
    this.currentRole = localStorage.getItem('role');
  }

  @ViewChild('sidenav') sidenav: MatSidenav | undefined;
  public isOpened = false;


// Get All Projects with Paginator
  length = 0;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [10, 25, 50, 100];
  disabled = false;
  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  pageEvent: PageEvent | undefined;
  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.totalProject = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.searchProject();
    this.getAllProjectsData();
  }
  getAllProjectsData() {
    this.projectControllerService
      .getAllProjectsPagingUsingGET(undefined,this.pageIndex,this.pageSize, undefined)
      .subscribe((res) => {
        this.allProject = res['projectList'];
        this.totalProject = res['totalItems'];
        console.log(this.allProject);
      });
  }

  //Add Project
  openAddProjectDialog() {
    this.dialog.open(AddProjectsComponent, {
      hasBackdrop: true,
    });
  }

  //Edit Project
  openEditProjectDialog(id: number): void {
    this.projectID = id;
    this.dialog.open(EditProjectsComponent, {
      data: { id: this.projectID },
      hasBackdrop: true,
    });
  }

  //Delete Project
  openDeleteProjectDialog(id: number, name: string) {
    this.projectID = id;
    this.projectName= name;
    this.dialog.open(DeleteProjectComponent,{
      data: {id: this.projectID, name: this.projectName},
      hasBackdrop: true,
    });
  }

  //Search Project
  searchProject() {
    this.projectControllerService
    .searchByKeywordUsingGET(this.searchProjectName, '', this.pageIndex, this.pageSize)
      .subscribe((searching) => {
      console.log(searching);
      this.allProject = searching['projectList'];
      this.totalProject = searching['totalItems'];
    });
  }
}
