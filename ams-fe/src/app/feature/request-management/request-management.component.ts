import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import {
  DialogRequestComponent,
  ConfirmDialogModel,
} from 'src/app/shared/components/dialog-request/dialog-request.component';
import { RequestControllerService } from 'src/app/api/requestController.service';
import { RequestDto } from '../../model/requestDto';
import { ActivatedRoute, ActivationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { AssetControllerService } from 'src/app/api/assetController.service';
import { DetailAssetsComponent } from '../popups/details-assets/detail-assets.component';
import { UserControllerService } from 'src/app/api/userController.service';
import { JwtAuthenticationControllerService } from 'src/app/api/jwtAuthenticationController.service';
import { DetailsUserComponent } from '../popups/details-user/details-user.component';

@Component({
  selector: 'app-request-management',
  templateUrl: './request-management.component.html',
  styleUrls: ['./request-management.component.scss'],
})
export class RequestManagementComponent {
  key: any;
  data: any;
  dataOrigin: any;
  response: any;
  datakey = [
    'ID',
    'Asset Name',
    'Inventory Asset Number',
    'Request Type',
    'Details',
    'Reason',
    'Status',
    'Update Time',
  ];

  length = 50;
  pageSize = 5;
  pageIndex = 0;
  pageSizeOptions = [2, 5, 10];

  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;
  result: string = '';
  public statuses = ['Open', 'Closed', 'Approved', 'Rejected'];
  public types = ['Fix', 'Transfer', 'Upgrade'];
  public selectedStatus = 'All';
  public selectedType = 'All';
  public selectedSearch = '';
  public selectedDevice = '';
  public currentUserId: any;

  selectData: any = null;
  assetid: any = null;

  constructor(
    public dialog: MatDialog,
    private requestController: RequestControllerService,
    private router: Router,
    private assetControllerService: AssetControllerService,
    private userControllerService: UserControllerService,
    private jwtAuthenticationControllerService: JwtAuthenticationControllerService
  ) {
    this.router.events
      .pipe(filter((routerEvent) => routerEvent instanceof ActivationEnd))
      .subscribe((routerEvent: ActivationEnd | any) => {
        const data = routerEvent.snapshot.data;
        if (data?.status) {
          console.log('status', data.status);
          this.selectedStatus = data.status;
        }
      });
  }

  ngOnInit(): void {
    this.getAllRequestByFilter();
    this.jwtAuthenticationControllerService
      .getUserInfoUsingGET()
      .subscribe((response) => {
        this.currentUserId = response.id;
      });
  }

  public pageEvent: PageEvent | undefined;
  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.getAllRequestByFilter();
  }

  getAllRequestByFilter(
    selectedName?: string,
    selectedDevice?: string,
    selectedState?: string,
    selectedType?: string
  ) {
    var state = this.selectedStatus.toLowerCase();
    var type = this.selectedType.toLowerCase();
    if (this.selectedStatus === 'All') {
      state = '';
    }
    if (this.selectedType === 'All') {
      type = '';
    }
    this.requestController
      .searchRequestByCertainFieldsUsingGET(
        this.selectedSearch,
        this.selectedDevice,
        undefined,
        this.pageIndex,
        type,
        this.pageSize,
        'desc',
        state
      )
      .subscribe((res) => {
        console.log(res);
        this.response = res;
        this.data = this.response.data;
        this.dataOrigin = this.data;
        this.length = this.response.totalItems;
        for (let index = 0; index < res['totalItems']; index++) {
          this.data[index]['receiveUserID'] = 77;
          this.data[index]['sendUserID'] = 75;
          this.data[index]['approveUserID'] = 1;
          this.userControllerService
            .getUserByIdUsingGET(this.data[index]['receiveUserID'])
            .subscribe((res2) => {
              this.data[index]['receiveUserName'] = res2.fullName;
            });
          this.userControllerService
            .getUserByIdUsingGET(this.data[index]['sendUserID'])
            .subscribe((res3) => {
              this.data[index]['sendUserName'] = res3.fullName;
            });
        }
      });
  }

  isAdmin() {
    if (localStorage.getItem('role') === 'ADMIN') {
      return true;
    }
    return false;
  }

  selectRequest(evt: any, request: any) {
    var target = evt.target;
    if (target.checked) {
      const checklist = document.getElementsByClassName('mycheck');
      for (let i = 0; i < checklist.length; i++) {
        var item = checklist[i] as HTMLInputElement;
        item.checked = false;
      }
      target.checked = true;
      this.selectData = request;
      console.log(this.selectData);
    } else {
      this.selectData = null;
    }
  }

  approveRequest(id?: number, assetId?: number, receiveUserID?: number): void {
    if (id === undefined) {
      if (this.selectData === null) {
        const message = `Please choose request to approve`;
        const dialogData = new ConfirmDialogModel(
          "Can't Approve request ",
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });
      } else if (this.selectData.status === 'closed') {
        const message = `Please reopen request to approve`;
        const dialogData = new ConfirmDialogModel(
          "Can't Approve request. Reopen request " + this.selectData.id,
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });

        dialogRef.afterClosed().subscribe((dialogResult) => {
          this.result = dialogResult;
          if (dialogResult) {
            this.reopen(Number(this.selectData.id));
          } else {
            console.log('Not Reopen ' + this.selectData.id);
          }
        });
      } else {
        const message = `Are you sure you want to do this?`;

        const dialogData = new ConfirmDialogModel(
          'Approve request ' + this.selectData.id,
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });

        dialogRef.afterClosed().subscribe((dialogResult) => {
          this.result = dialogResult;
          if (dialogResult) {
            this.approve(
              Number(this.selectData.id),
              Number(this.selectData.assetId),
              Number(this.selectData.receiveUserID)
            );
          } else {
            console.log('Not Approve ' + id);
          }
        });
      }
    } else {
      const message = `Are you sure you want to do this?`;

      const dialogData = new ConfirmDialogModel(
        'Approve request ' + id,
        message
      );

      const dialogRef = this.dialog.open(DialogRequestComponent, {
        data: dialogData,
      });

      dialogRef.afterClosed().subscribe((dialogResult) => {
        this.result = dialogResult;
        if (dialogResult) {
          this.approve(Number(id), Number(assetId), Number(receiveUserID));
        } else {
          console.log('Not Approve ' + id);
        }
      });
    }
  }

  closeRequest(id?: number): void {
    if (id === undefined) {
      if (this.selectData === null) {
        const message = `Please choose request to close`;
        const dialogData = new ConfirmDialogModel(
          "Can't Close request ",
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });
      } else {
        const message = `Are you sure you want to do this?`;

        const dialogData = new ConfirmDialogModel(
          'Close request ' + this.selectData.id,
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });

        dialogRef.afterClosed().subscribe((dialogResult) => {
          this.result = dialogResult;
          if (dialogResult) {
            this.close(Number(this.selectData.id));
          } else {
            console.log('Not close ' + this.selectData.id);
          }
        });
      }
    } else {
      const message = `Are you sure you want to do this?`;

      const dialogData = new ConfirmDialogModel('Close request ' + id, message);

      const dialogRef = this.dialog.open(DialogRequestComponent, {
        data: dialogData,
      });

      dialogRef.afterClosed().subscribe((dialogResult) => {
        this.result = dialogResult;
        if (dialogResult) {
          this.close(Number(id));
        } else {
          console.log('Not close ' + id);
        }
      });
    }
  }

  reopenRequest(id?: number): void {
    if (id === undefined) {
      if (this.selectData === null) {
        const message = `Please choose request to reopen`;
        const dialogData = new ConfirmDialogModel(
          "Can't Reopen request ",
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });
      } else {
        const message = `Are you sure you want to do this?`;

        const dialogData = new ConfirmDialogModel(
          'Reopen request ' + this.selectData.id,
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });

        dialogRef.afterClosed().subscribe((dialogResult) => {
          this.result = dialogResult;
          if (dialogResult) {
            console.log('Reopen ' + Number(this.selectData.id));
            this.reopen(Number(this.selectData.id));
          } else {
            console.log('Not reopen ' + this.selectData.id);
          }
        });
      }
    } else {
      const message = `Are you sure you want to do this?`;

      const dialogData = new ConfirmDialogModel(
        'Reopen request ' + id,
        message
      );

      const dialogRef = this.dialog.open(DialogRequestComponent, {
        data: dialogData,
      });

      dialogRef.afterClosed().subscribe((dialogResult) => {
        this.result = dialogResult;
        if (dialogResult) {
          this.reopen(Number(id));
        } else {
          console.log('Not Reopen ' + id);
        }
      });
    }
  }

  rejectRequest(id?: number): void {
    if (id === undefined) {
      if (this.selectData === null) {
        const message = `Please choose request to reject`;
        const dialogData = new ConfirmDialogModel(
          "Can't Reject request ",
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });
      } else if (this.selectData.status === 'closed') {
        const message = `Please reopen request to reject`;
        const dialogData = new ConfirmDialogModel(
          "Can't Reject request.Reopen request " + this.selectData.id,
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });

        dialogRef.afterClosed().subscribe((dialogResult) => {
          this.result = dialogResult;
          if (dialogResult) {
            this.reopen(Number(this.selectData.id));
          } else {
            console.log('Not Reopen ' + this.selectData.id);
          }
        });
      } else {
        const message = `Are you sure you want to do this?`;

        const dialogData = new ConfirmDialogModel(
          'Reject request ' + this.selectData.id,
          message
        );

        const dialogRef = this.dialog.open(DialogRequestComponent, {
          data: dialogData,
        });

        dialogRef.afterClosed().subscribe((dialogResult) => {
          this.result = dialogResult;
          if (dialogResult) {
            this.reject(Number(this.selectData.id));
          } else {
            console.log('Not Reject ' + this.selectData.id);
          }
        });
      }
    } else {
      const message = `Are you sure you want to do this?`;

      const dialogData = new ConfirmDialogModel(
        'Reject request ' + id,
        message
      );

      const dialogRef = this.dialog.open(DialogRequestComponent, {
        data: dialogData,
      });

      dialogRef.afterClosed().subscribe((dialogResult) => {
        this.result = dialogResult;
        if (dialogResult) {
          this.reject(Number(id));
        } else {
          console.log('Not Reject ' + id);
        }
      });
    }
  }

  reopen(id: number) {
    var body = { status: '' };
    body.status = 'open';
    console.log(body);
    this.requestController
      .updateRequestUsingPUT(body, Number(id))
      .subscribe((data) => {
        this.getAllRequestByFilter();
      });
  }

  approve(id: number, assetId: number, receiveUserID: number) {
    var body = { status: '' };
    body.status = 'approved';
    console.log(body);
    var inforOfDevice: any;
    this.assetControllerService
      .findAssetByIdUsingGET(Number(assetId))
      .subscribe((data) => {
        inforOfDevice = data;
        inforOfDevice.user_id = Number(receiveUserID);
        this.assetControllerService
          .updateAssetByIdUsingPUT(inforOfDevice, Number(assetId))
          .subscribe();
      });
    this.requestController
      .updateRequestUsingPUT(body, Number(id))
      .subscribe((data) => {
        this.getAllRequestByFilter();
      });
  }

  close(id: number) {
    var body = { status: '' };
    body.status = 'closed';
    console.log(body);
    this.requestController
      .updateRequestUsingPUT(body, Number(id))
      .subscribe((data) => {
        this.getAllRequestByFilter();
      });
  }

  reject(id: number) {
    var body = { status: '' };
    body.status = 'rejected';
    console.log(body);
    this.requestController
      .updateRequestUsingPUT(body, Number(id))
      .subscribe((data) => {
        this.getAllRequestByFilter();
      });
  }

  openAssetDetailsDialog(assetID: number, assetName: string) {
    this.dialog.open(DetailAssetsComponent, {
      data: {
        id: assetID,
        name: assetName,
      },
    });
  }

  openUserDetailsDialog(id: any) {
    var userId = id;
    this.dialog.open(DetailsUserComponent, {
      data: { id: userId },
    });
  }
}
