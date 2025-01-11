import { ProjectControllerService } from 'src/app/api/projectController.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-project',
  templateUrl: './delete-project.component.html',
  styleUrls: ['./delete-project.component.scss']
})
export class DeleteProjectComponent {
  projectName= '';

  constructor(
    private projectControllerService: ProjectControllerService,
    @Inject(MAT_DIALOG_DATA)
    public data: {id: number, name: string},
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.projectName = this.data.name;
  }

  deleteProject() {
    this.projectControllerService.deleteProjectUsingDELETE(this.data.id).subscribe();
    this.closePopup();
    window.location.reload();
  }

  closePopup() {
    this.dialog.closeAll();
  }
}
