import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserControllerService } from 'src/app/api/userController.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RoleControllerService } from 'src/app/api/roleController.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {
  pageSize = 10;
  pageIndex = 0;
  length = 50;
  columnDisplay: any;
  infoOfUser: any;
  idOfUser: any;
  allUser: any;
  allRole: any;
  allStatus: any;
  editUsersForm: FormGroup;
  avatar: any;
  avatarFileError = '';
  displayAvatarName = '';
  urlAvatar : any;

  constructor(
    private userControllerService: UserControllerService,
    private roleControllerService: RoleControllerService,
    public dialogRef: MatDialogRef<EditUserComponent>,
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
    this.getAllRole();
    this.getAllStatus();
    this.idOfUser = this.currentUser.id;
    this.onGetUsers(this.idOfUser);

    this.editUsersForm = new FormGroup({
      id: new FormControl(''),
      username: new FormControl(''),
      password: new FormControl(''),
      email: new FormControl(''),
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      fullName: new FormControl(''),
      phone: new FormControl(''),
      birthDate: new FormControl(''),
      gender: new FormControl(''),
      address: new FormControl(''),
      role: new FormControl(''),
      avatar: new FormControl(''),
      status: new FormControl(''),
    });
  }

  // Get All User with Paginator
  getAllUserData() {
    this.userControllerService
      .getAllUsersPageUsingGET(this.pageSize, '', this.pageIndex, 'desc')
      .subscribe((res) => {
        this.allUser = res['data'];
      });
  }

  // Get User by ID
  onGetUsers(id: number) {
    console.warn('onGetUsers selected' + id);
    this.userControllerService.getUserByIdUsingGET(id).subscribe((x) => {
      this.editUsersForm = new FormGroup({
        id: new FormControl(x['id']),
        username: new FormControl(x['username'], [Validators.required]),
        password: new FormControl(x['password'], [Validators.required]),
        email: new FormControl(x['email'], [
          Validators.required,
          Validators.email,
        ]),
        firstName: new FormControl(x['firstName'], [Validators.required]),
        lastName: new FormControl(x['lastName'], [Validators.required]),
        fullName: new FormControl(x['fullName'], [Validators.required]),
        phone: new FormControl(x['phone'], [Validators.required]),
        birthDate: new FormControl(x['birthDate']),
        gender: new FormControl(x['gender'], [Validators.required]),
        address: new FormControl(x['address']),
        role: new FormControl(x['role'], [Validators.required]),
        avatar: new FormControl(x['avatar']),
        status: new FormControl(x['status'], [Validators.required]),
      });
    });
  }

  // Edit single User
  onEditUser(editUsersForm: any, id: number) {
    this.userControllerService
      .updateUserUsingPUT(editUsersForm.value, id)
      .subscribe((result) => {
      });
      if(this.avatar){
        this.userControllerService.uploadAvatarUsingPUTForm(id,this.avatar).subscribe(res=>{
        }
        );
      }
    this.dialogRef.close();
    window.location.reload();
  }

  // Close Add User Dialog
  onExitClick(): void {
    console.log('close dialog without edit anything');
    this.dialogRef.close();
    window.location.reload();
  }

  // Get all Role
  getAllRole() {
    this.roleControllerService
      .getAllRolesPageUsingGET(10, '', 0, '')
      .subscribe((r) => {
        this.allRole = r['data'];
      });
  }

  // Get all Status
  getAllStatus() {
    this.userControllerService.getAllStatusUsingGET().subscribe((s) => {
      this.allStatus = s;
    });
  }

  selectAvatar(avatar:any){
    this.avatar = avatar.target.files[0];
    if(this.avatar.name.length>33){
      this.displayAvatarName = this.avatar.name.substring(0,10) + '...' + this.avatar.name.slice(-10);
    }
    else{
      this.displayAvatarName = this.avatar.name;
    }
    if(this.avatar.size > 1024000){
      this.avatarFileError='size';
    }
    else if(!this.avatar.type.includes('image')){
        this.avatarFileError='type';
    }
    else{
      this.avatarFileError='';
    }
    if (avatar.target.files && avatar.target.files[0]) {
      var reader = new FileReader();
      reader.readAsDataURL(avatar.target.files[0]);
      reader.onload = (event) => {
        this.urlAvatar = event.target.result;
      }
    }
  }
}
