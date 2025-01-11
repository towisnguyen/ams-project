import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { RoleControllerService } from 'src/app/api/roleController.service';
import { UserControllerService } from 'src/app/api/userController.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss'],
})
export class AddUserComponent implements OnInit {
  data: any;
  roleData: any;
  key: any;
  users: any;
  length = 50;
  pageSize = 10;
  pageIndex = 0;
  columnDisplay: any;
  allRole: any;
  allStatus: any;
  avatar: any;
  displayAvatarName = '';
  avatarFileError='';
  urlAvatar : any;

  usersForm: FormGroup;

  constructor(
    private userControllerService: UserControllerService,
    private roleControllerService: RoleControllerService,
    public dialogRef: MatDialogRef<AddUserComponent>
  ) {}
  ngOnInit(): void {
    this.getAllUserData();
    this.getAllRole();
    this.getAllStatus();

    this.usersForm = new FormGroup({
      username: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required, Validators.email]),
      firstName: new FormControl(null, [Validators.required]),
      lastName: new FormControl(null, [Validators.required]),
      fullName: new FormControl(null, [Validators.required]),
      phone: new FormControl(null, [Validators.required]),
      birthDate: new FormControl(null),
      gender: new FormControl('', [Validators.required]),
      address: new FormControl(null),
      role: new FormControl('', [Validators.required]),
      avatar: new FormControl(null),
      status: new FormControl('', [Validators.required]),
    });
  }

  // Get All Users with Paginator
  getAllUserData() {
    this.userControllerService
      .getAllUsersPageUsingGET(this.pageSize, '', this.pageIndex, 'desc')
      .subscribe((res) => {
        this.data = res['data'];
        this.length = res['totalItems'];
        this.columnDisplay = this.data[0];
        this.key = res['data'][0];
      });
  }

  // Add User
  onAddUser(usersForm) {
    this.userControllerService
      .createNewUserUsingPOST(usersForm.value)
      .subscribe((result) => {
        if(this.avatar){
          this.userControllerService.uploadAvatarUsingPUTForm(result.id,this.avatar).subscribe(res=>{
            this.dialogRef.close();
            window.location.reload();
          }
          );
        }
        else{
          this.dialogRef.close();
          window.location.reload();
        }
      });
  }

  // Close Add User Dialog
  onExitClick(): void {
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

  removeAvatar(){
    this.avatar=undefined;
    this.displayAvatarName='';
    this.urlAvatar=null;
    this.avatarFileError='';
  }
}
