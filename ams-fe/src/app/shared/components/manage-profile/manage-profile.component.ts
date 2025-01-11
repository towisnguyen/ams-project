import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AppUserDto } from 'src/app/model/appUserDto';
import { NgForm } from '@angular/forms';
import { UserControllerService } from 'src/app/api/userController.service';

@Component({
  selector: 'app-manage-profile',
  templateUrl: './manage-profile.component.html',
  styleUrls: ['./manage-profile.component.scss'],
})
export class ManageProfileComponent implements OnInit {
  public userProfile: any;
  public userInfo: any;
  public userID: any;

  @ViewChild('editProfileForm') form!: NgForm;

  constructor(
    private userControllerService: UserControllerService,
    public dialogRef: MatDialogRef<ManageProfileComponent>,
    @Inject(MAT_DIALOG_DATA)
    public currentUser: {
      id: number;
      address: string;
      avatar: string;
      birthDate: string;
      email: string;
      firstName: string;
      fullName: string;
      gender: string;
      lastName: string;
      password: string;
      phone: string;
      role: string;
      status: string;
      username: string;
    }
  ) {}

  ngOnInit(): void {
    this.userID = this.currentUser.id;
    this.onGetUsers(this.userID);
    console.warn('id is' + this.userID);

    this.form?.setValue({
      username: this.currentUser.username,
      password: this.currentUser.password,
      email: this.currentUser.email,
      firstName: this.currentUser.firstName,
      lastName: this.currentUser.lastName,
      fullName: this.currentUser.fullName,
      phone: this.currentUser.phone,
      birthDate: this.currentUser.birthDate,
      gender: this.currentUser.gender,
      address: this.currentUser.address,
      role: this.currentUser.role,
      avatar: this.currentUser.avatar,
      status: this.currentUser.status,
    });
  }

  onExitClick(): void {
    this.dialogRef.close();
  }

  onGetUsers(id: number) {
    console.warn('onGetUsers selected' + id);
    this.userControllerService.getUserByIdUsingGET(id).subscribe((response) => {
      this.userInfo = response;
    });
  }

  onEditUser(id: any) {
    var body: AppUserDto = this.userInfo;
    this.userControllerService
      .updateUserUsingPUT(body, id)
      .subscribe((result) => {
        console.warn('update user: ' + result.fullName);
      });
    this.dialogRef.close();
    window.location.reload();
  }
}
