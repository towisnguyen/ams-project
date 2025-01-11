import { ProjectControllerService } from './../../../api/projectController.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-add-projects',
  templateUrl: './add-projects.component.html',
  styleUrls: ['./add-projects.component.scss']
})
export class AddProjectsComponent implements OnInit {
  addProjectsForm!: FormGroup;
  submitted = false;

  constructor(
    private projectControllerService: ProjectControllerService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<AddProjectsComponent>
  ) {}

  ngOnInit(): void {
    this.addProjectsForm = this.formBuilder.group({
      projectName: [null, Validators.required],
      description: [null, Validators.required],
      status: [null, Validators.required],
    });
  }


  addNewProjects(addProjectsForm) {
    console.log(addProjectsForm.value);
    this.projectControllerService.createProjectUsingPOST(addProjectsForm.value).subscribe((result) => {
      this.dialogRef.close();
      window.location.reload();
      console.log(result);
    });
  }

  closeForm() {
    this.dialog.closeAll();
  }
}
