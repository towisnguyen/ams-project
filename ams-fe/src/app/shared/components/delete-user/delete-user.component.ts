import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserControllerService } from 'src/app/api/userController.service';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss'],
})
export class DeleteUserComponent implements OnInit {
  public item: any;
  public id: any;
  pageSize = 10;
  pageIndex = 0;
  public allUser: any;
  public length: any;
  public key: any;
  public nameOfUser: any;
  public idOfUser: any;

  ngOnInit(): void {
    this.nameOfUser = this.data.name;
    this.idOfUser = this.data.id;
    this.getAllUserData();
  }

  constructor(
    private userControllerService: UserControllerService,
    public dialogRef: MatDialogRef<DeleteUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: number; name: string }
  ) {}

  // Get All User with Paginator
  getAllUserData() {
    this.userControllerService
      .getAllUsersPageUsingGET(this.pageSize, '', this.pageIndex, 'desc')
      .subscribe((res) => {
        this.allUser = res['data'];
      });
  }

  // Delete selected User and close dialog
  onDeleteExitClick(id: number) {
    console.warn('delete: ' + id);
    this.dialogRef.close();
    this.userControllerService.deleteUserUsingDELETE(id).subscribe();
    window.location.reload();
  }

  // Close Delete dialog
  onCancelExitClick(): void {
    console.log("close dialog, doesn't delete any user");
    this.dialogRef.close();
    window.location.reload();
  }

  // Get Users by ID
  onGetUsers(id: number) {
    console.warn(id);
    this.userControllerService.getUserByIdUsingGET(id).subscribe();
  }

  // Delete multi Users
  onDeleteMultiUsers() {}
}
