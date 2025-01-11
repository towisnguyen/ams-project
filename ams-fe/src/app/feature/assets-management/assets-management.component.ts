import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { AssetControllerService } from 'src/app/api/assetController.service';
import { AddDeviceComponent } from 'src/app/shared/components/add-device/add-device.component';
import { EditDeviceComponent } from 'src/app/shared/components/edit-device/edit-device.component';
import { ConfirmDeleteAssetComponent } from 'src/app/shared/components/remove-device/confirm-delete-asset/confirm-delete-asset.component';
import { DetailAssetsComponent } from '../popups/details-assets/detail-assets.component';
import { CreateRequestComponent } from 'src/app/shared/components/create-request/create-request.component';
import { AssetSupplierControllerService } from 'src/app/api/assetSupplierController.service';
import { ProjectControllerService } from 'src/app/api/projectController.service';
import { UserControllerService } from 'src/app/api/userController.service';

@Component({
  selector: 'app-assets-management',
  templateUrl: './assets-management.component.html',
  styleUrls: ['./assets-management.component.scss'],
})
export class AssetsManagementComponent implements OnInit {
  allAssets: any;
  totalAssets: number;
  assetID: number;
  assetName: string;
  status: any;
  categories: any;
  projects: any;
  suppliers: any;
  ownerships: any;
  currentRole: any;

  constructor(
    private userControllerService: UserControllerService,
    private assetControllerService: AssetControllerService,
    private supplierControllerService: AssetSupplierControllerService,
    private projectControllerService: ProjectControllerService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getAllAssets();
    this.getAllOwnerships();
    this.getAllStatus();
    this.getAllCategories();
    this.getAllProjects();
    this.getAllSuppliers();

    this.currentRole = localStorage.getItem('role');
  }

  getAllAssets() {
    this.assetControllerService
      .getAllAssetsUsingGET(undefined, undefined, this.pageIndex, this.pageSize)
      .subscribe((res) => {
        this.allAssets = res['assetList'];
        this.totalAssets = res['totalItems'];
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

  searchCategory = '';
  searchOwner = '';
  searchIMEI = '';
  searchInventory = '';
  searchName = '';
  searchProject = '';
  searchSerial = '';
  searchStatus = '';
  searchSupplier = '';
  applySearch() {
    this.assetControllerService
      .filterByCertainFieldsUsingGET(
        this.searchCategory,
        this.searchOwner,
        this.searchIMEI,
        this.searchInventory,
        this.searchName,
        this.pageSize,
        '',
        this.pageIndex,
        this.searchProject,
        this.searchSerial,
        'desc',
        this.searchStatus,
        this.searchSupplier
      )
      .subscribe((searching) => {
        this.allAssets = searching['data'];
        this.totalAssets = searching['totalItems'];
      });
  }

  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [2, 5, 10];
  disabled = false;
  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  pageEvent: PageEvent | undefined;
  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.totalAssets = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.applySearch();
  }

  addNewAsset(): void {
    this.dialog.open(AddDeviceComponent, {
      hasBackdrop: true,
    });
  }

  editAsset(id: number): void {
    this.assetID = id;
    this.dialog.open(EditDeviceComponent, {
      data: { id: this.assetID },
      hasBackdrop: true,
    });
  }

  removeAsset(id: number, name: string) {
    this.assetID = id;
    this.assetName = name;
    this.dialog.open(ConfirmDeleteAssetComponent, {
      data: { id: this.assetID, name: this.assetName },
      hasBackdrop: true,
    });
  }

  createRequest(id: number) {
    this.assetID = id;
    this.dialog.open(CreateRequestComponent, {
      data: { id: this.assetID },
      hasBackdrop: true,
    });
  }

  viewDetails(id: number, name: string) {
    this.assetID = id;
    this.assetName = name;
    this.dialog.open(DetailAssetsComponent, {
      data: { id: this.assetID, name: this.assetName },
      hasBackdrop: true,
    });
  }
}
