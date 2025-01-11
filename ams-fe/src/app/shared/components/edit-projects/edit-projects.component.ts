import { ProjectControllerService } from './../../../api/projectController.service';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-projects',
  templateUrl: './edit-projects.component.html',
  styleUrls: ['./edit-projects.component.scss']
})
export class EditProjectsComponent {
  editProjectsForm!: FormGroup;
  submitted = true;
  projectInfo: any;

  constructor(
    private projectControllerService: ProjectControllerService,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<EditProjectsComponent>,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA)
    public currentProject: {
      id: number;
    }
  ) {}

  ngOnInit(): void {
    this.editProjectsForm = new FormGroup({
      projectName: new FormControl(''),
      description: new FormControl(''),
      status: new FormControl(''),
    });
    this.getProjectInfo();
  }

  getProjectInfo() {
    this.projectControllerService
    .getProjectByIdUsingGET(this.currentProject.id)
    .subscribe((res) => {
      this.projectInfo = res;
      this.editProjectsForm = this.formBuilder.group({
        projectName: [this.projectInfo.projectName, Validators.required],
        description: [this.projectInfo.description, Validators.required],
        status: [this.projectInfo.status, Validators.required],
      });
    })
    }

  editProjects(editProjectsForm) {
    this.projectControllerService
    .updateProjectUsingPUT(editProjectsForm.value,this.projectInfo.id)
    .subscribe();
     console.log(editProjectsForm.value);
     this.closeForm;
    window.location.reload();
  }

  closeForm() {
    this.dialog.closeAll();
  }
}
