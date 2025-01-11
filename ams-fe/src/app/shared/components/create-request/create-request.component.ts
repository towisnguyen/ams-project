import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AssetControllerService } from '../../../api/assetController.service';
import { UserControllerService } from '../../../api/userController.service';
import { RequestControllerService } from '../../../api/requestController.service';
import { RequestDto } from 'src/app/model/requestDto';
import { JwtAuthenticationControllerService } from '../../../api/jwtAuthenticationController.service';
import { ProjectControllerService } from 'src/app/api/projectController.service';

@Component({
  selector: 'app-create-request',
  templateUrl: './create-request.component.html',
  styleUrls: ['./create-request.component.scss'],
})
export class CreateRequestComponent implements OnInit {
  public typeRequest = '';
  public infoOfAsset: any;
  public dataCreateRequest: any;
  public listAdminUsernames: string[] = [];
  public listAdminIDs: number[] = [];
  public listUserUsernames: string[] = [];
  public listUserIDs: number[] = [];
  public userIdSend: any;
  public userIdReceive = 1;
  public approverRequired = false;
  public typeRequired = false;
  public transferRequired = false;
  public transferInvalid = false;
  public approverInvalid = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { id: number },
    private assetControllerService: AssetControllerService,
    private userControllerService: UserControllerService,
    private requestControllerService: RequestControllerService,
    public dialogRef: MatDialogRef<CreateRequestComponent>,
    private jwtAuthenticationControllerService: JwtAuthenticationControllerService,
    private projectControllerService: ProjectControllerService
  ) {}

  ngOnInit(): void {
    this.getDataAsset();
    this.getDataOfAdmins();
    this.getUserIdSend();
  }

  chooseTypeRequest(value: string) {
    this.typeRequired = false;
    this.typeRequest = value;
  }

  getDataAsset() {
    this.assetControllerService
      .findAssetByIdUsingGET(this.data.id)
      .subscribe((response) => {
        this.infoOfAsset = response;
        console.log(response);
        this.getAllStatus();
        this.getAllCategories();
        this.getAllProjects();
      });
  }

  getUserIdSend() {
    this.jwtAuthenticationControllerService
      .getUserInfoUsingGET()
      .subscribe((response) => {
        this.userIdSend = response.id;
        console.log('data user: ', this.userIdSend);
      });
  }

  getDataOfAdmins() {
    this.userControllerService
      .filterAppUserByRoleUsingGET(undefined, '', undefined, 'ADMIN', '')
      .subscribe((response) => {
        for (let index = 0; index < response.data.length; index++) {
          this.listAdminUsernames.push(response.data[index].username);
          this.listAdminIDs.push(response.data[index].id);
        }
      });
  }

  getDataOfUsers() {
    this.userControllerService
      .getAllUsersPageUsingGET(100, '', undefined, '')
      .subscribe((response) => {
        for (let index = 0; index < response.data.length; index++) {
          this.listUserUsernames.push(response.data[index].username);
          this.listUserIDs.push(response.data[index].id);
        }
      });
  }

  getAllStatus() {
    this.assetControllerService
      .getTotalAssetsByStatusUsingGET()
      .subscribe((totalStatus) => {
        for (let index = 0; index < totalStatus.status.length; index++) {
          if (this.infoOfAsset.status_id === totalStatus.status[index].id) {
            this.infoOfAsset['status'] = totalStatus.status[index].name;
          }
        }
      });
  }

  getAllCategories() {
    this.assetControllerService
      .getTotalAssetsByCategoriesUsingGET()
      .subscribe((totalCategories) => {
        for (
          let index = 0;
          index < totalCategories.categories.length;
          index++
        ) {
          if (
            this.infoOfAsset.category_id ===
            totalCategories.categories[index].id
          ) {
            this.infoOfAsset['category'] =
              totalCategories.categories[index].name;
          }
        }
      });
  }

  getAllProjects() {
    this.projectControllerService.getAllProjectsUsingGET().subscribe((res) => {
      console.log(res);
      var totalProjects: any;
      totalProjects = res;
      for (let index = 0; index < totalProjects.length; index++) {
        if (this.infoOfAsset.project_id === totalProjects[index].id) {
          this.infoOfAsset['project'] = totalProjects[index].projectName;
          break;
        }
      }
    });
  }

  createRequest(dataCreateRequest: any) {
    console.log(dataCreateRequest);
    if (dataCreateRequest.transfer !== undefined) {
      this.userIdReceive =
        this.listUserIDs[
          this.listUserUsernames.indexOf(dataCreateRequest.transfer)
        ];
    }
    let dto: RequestDto = {
      assetIds: this.infoOfAsset.id,
      reason: dataCreateRequest.reason,
      requestType: this.typeRequest,
      status: 'open',
      updateAt: undefined,
      userIdApprove: [
        this.listAdminIDs[
          this.listAdminUsernames.indexOf(dataCreateRequest.approver)
        ],
      ],
      userIdReceive: [this.userIdReceive],
      userIdSend: [this.userIdSend],
    };
    this.requestControllerService
      .createNewRequestUsingPOST(dto)
      .subscribe((result) => {
        console.log(dto);
        console.log(result);
      });
  }

  onSubmitClick(dataCreateRequest: any): void {
    var formIsValid = true;
    dataCreateRequest.value.type = this.typeRequest;
    if (dataCreateRequest.value.approver) {
      if (this.listAdminUsernames.includes(dataCreateRequest.value.approver)) {
        this.approverInvalid = false;
      } else {
        formIsValid = false;
        this.approverInvalid = true;
      }
    } else {
      formIsValid = false;
      this.approverRequired = true;
    }
    if (dataCreateRequest.value.type) {
      if (dataCreateRequest.value.type === 'transfer') {
        if (dataCreateRequest.value.transfer) {
          if (
            this.listUserUsernames.includes(dataCreateRequest.value.transfer)
          ) {
            this.transferInvalid = false;
          } else {
            formIsValid = false;
            this.transferInvalid = true;
          }
        } else {
          formIsValid = false;
          this.transferRequired = true;
        }
      }
    } else {
      formIsValid = false;
      this.typeRequired = true;
    }
    if (formIsValid) {
      this.dialogRef.close();
    }
  }

  onExitClick(): void {
    this.dialogRef.close();
  }
}
