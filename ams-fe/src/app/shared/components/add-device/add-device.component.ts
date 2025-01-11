import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AssetControllerService } from 'src/app/api/assetController.service';
import { AssetSupplierControllerService } from 'src/app/api/assetSupplierController.service';
import { ProjectControllerService } from 'src/app/api/projectController.service';
import { UserControllerService } from 'src/app/api/userController.service';

@Component({
  selector: 'app-add-device',
  templateUrl: './add-device.component.html',
  styleUrls: ['./add-device.component.scss'],
})
export class AddDeviceComponent implements OnInit {
  addForm!: FormGroup;
  submitted = false;
  status: any;
  categories: any;
  projects: any;
  suppliers: any;
  ownerships: any;

  constructor(
    private userControllerService: UserControllerService,
    private assetControllerService: AssetControllerService,
    private supplierControllerService: AssetSupplierControllerService,
    private projectControllerService: ProjectControllerService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      name: [null, Validators.required],
      category_id: ['', Validators.required],
      imei: [null, Validators.required],
      serialNumber: [null, Validators.required],
      inventoryNumber: [null, Validators.required],
      status_id: ['', Validators.required],
      ownership: ['', Validators.required],
      user_id: ['', Validators.required],
      project_id: ['', Validators.required],
      supplier_id: ['', Validators.required],
      comments: [null],
    });

    this.getAllOwnerships();
    this.getAllStatus();
    this.getAllCategories();
    this.getAllProjects();
    this.getAllSuppliers();
  }

  getAllOwnerships() {
    this.userControllerService
      .getAllUsersPageUsingGET(100, '', undefined, '')
      .subscribe((res) => {
        this.ownerships = res['data'];
      });
  }

  getAllStatus() {
    this.assetControllerService
      .getTotalAssetsByStatusUsingGET()
      .subscribe((res) => {
        this.status = res['status'];
      });
  }

  getAllCategories() {
    this.assetControllerService
      .getTotalAssetsByCategoriesUsingGET()
      .subscribe((res) => {
        this.categories = res['categories'];
      });
  }

  getAllProjects() {
    this.projectControllerService.getAllProjectsUsingGET().subscribe((res) => {
      this.projects = res;
    });
  }

  getAllSuppliers() {
    this.supplierControllerService
      .getAllSuppliersUsingGET('', undefined, 100)
      .subscribe((res) => {
        this.suppliers = res['assetList'];
      });
  }

  closeForm() {
    this.dialog.closeAll();
  }

  addNew(addForm) {
    this.submitted = true;
    if (addForm.invalid) {
      return;
    }
    this.assetControllerService.createAssetUsingPOST(addForm.value).subscribe();
    console.log(addForm.value);
    this.closeForm();
    window.location.reload();
  }
}
