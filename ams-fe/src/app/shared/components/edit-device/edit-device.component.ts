import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { AssetControllerService } from 'src/app/api/assetController.service';
import { AssetSupplierControllerService } from 'src/app/api/assetSupplierController.service';
import { ProjectControllerService } from 'src/app/api/projectController.service';
import { UserControllerService } from 'src/app/api/userController.service';

@Component({
  selector: 'app-edit-device',
  templateUrl: './edit-device.component.html',
  styleUrls: ['./edit-device.component.scss'],
})
export class EditDeviceComponent implements OnInit {
  updateForm!: FormGroup;
  submitted = true;
  assetInfo: any;
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
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: { id: number }
  ) {}

  ngOnInit(): void {
    this.updateForm = new FormGroup({
      id: new FormControl(''),
      name: new FormControl(''),
      category_id: new FormControl(''),
      imei: new FormControl(''),
      serialNumber: new FormControl(''),
      inventoryNumber: new FormControl(''),
      status_id: new FormControl(''),
      ownership: new FormControl(''),
      user_id: new FormControl(''),
      project_id: new FormControl(''),
      supplier_id: new FormControl(''),
      comments: new FormControl(''),
    });

    this.getAssetInfo();
    this.getAllOwnerships();
    this.getAllStatus();
    this.getAllCategories();
    this.getAllProjects();
    this.getAllSuppliers();
  }

  getAssetInfo() {
    this.assetControllerService
      .findAssetByIdUsingGET(this.data.id)
      .subscribe((res) => {
        this.assetInfo = res;
        this.updateForm = this.formBuilder.group({
          id: [this.assetInfo.id, Validators.required],
          name: [this.assetInfo.name, Validators.required],
          category_id: [this.assetInfo.category_id, Validators.required],
          imei: [this.assetInfo.imei, Validators.required],
          serialNumber: [this.assetInfo.serialNumber, Validators.required],
          inventoryNumber: [
            this.assetInfo.inventoryNumber,
            Validators.required,
          ],
          status_id: [this.assetInfo.status_id, Validators.required],
          ownership: [this.assetInfo.ownership, Validators.required],
          user_id: [this.assetInfo.user_id, Validators.required],
          project_id: [this.assetInfo.project_id, Validators.required],
          supplier_id: [this.assetInfo.supplier_id, Validators.required],
          comments: [this.assetInfo.comments],
        });
        console.log(res);
      });
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

  update(updateForm) {
    this.submitted = true;
    if (updateForm.invalid) {
      return;
    }
    this.assetControllerService
      .updateAssetByIdUsingPUT(updateForm.value, this.assetInfo.id)
      .subscribe();
    console.log(updateForm.value);
    this.closeForm();
    window.location.reload();
  }
}
