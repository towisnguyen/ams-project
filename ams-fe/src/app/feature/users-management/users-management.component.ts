import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserControllerService } from '../../api/userController.service';
import { PageEvent } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { AddUserComponent } from 'src/app/shared/components/add-user/add-user.component';
import { EditUserComponent } from 'src/app/shared/components/edit-user/edit-user.component';
import { DeleteUserComponent } from 'src/app/shared/components/delete-user/delete-user.component';
import { DetailsUserComponent } from '../popups/details-user/details-user.component';
import { AppUserDto } from 'src/app/model/appUserDto';
import { RoleControllerService } from '../../api/roleController.service';
import { MatTableDataSource } from '@angular/material/table';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  // changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'app-users-management',
  templateUrl: './users-management.component.html',
  styleUrls: ['./users-management.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class UsersManagementComponent implements OnInit {
  public key: any;
  public id: any;
  public data: any;
  public item: any;
  public idAsset: any;
  public idOfUser: any;
  public nameOfUser: any;
  public infoOfUser: any;
  public searchKey: any;
  currentRole: any;

  searchRole: any;
  searchStatus: any;
  allRole: any;
  allStatus: any;
  dataSource: any;
  fEmail: any;
  fPhone: any;
  fDob: any;
  fGender: any;
  fAddress: any;
  fRole: any;
  fAvatar: any;

  constructor(
    private userControllerService: UserControllerService,
    private roleControllerService: RoleControllerService,
    public dialog: MatDialog
  ) {}

  form: FormGroup = new FormGroup({
    userID: new FormControl(false),
    username: new FormControl(false),
    email: new FormControl(false),
    firstname: new FormControl(false),
    lastname: new FormControl(false),
    fullname: new FormControl(false),
    phone: new FormControl(false),
    dob: new FormControl(false),
    gender: new FormControl(false),
    address: new FormControl(false),
    role: new FormControl(false),
    avatar: new FormControl(false),
    status: new FormControl(false),
    action: new FormControl(false),
  });

  userID = this.form.get('userID');
  username = this.form.get('username');
  email = this.form.get('email');
  firstname = this.form.get('firstname');
  lastname = this.form.get('lastname');
  fullname = this.form.get('fullname');
  phone = this.form.get('phone');
  dob = this.form.get('dob');
  gender = this.form.get('gender');
  address = this.form.get('address');
  role = this.form.get('role');
  avatar = this.form.get('avatar');
  status = this.form.get('status');
  action = this.form.get('action');
  cbValues;

  columns: string[];

  columnDefinitions = [
    { def: 'userID', label: 'ID', selected: this.userID.value },
    { def: 'username', label: 'Username', selected: this.username.value },
    { def: 'email', label: 'Email', selected: this.email.value },
    { def: 'firstname', label: 'First Name', selected: this.firstname.value },
    { def: 'lastname', label: 'Last Name', selected: this.lastname.value },
    { def: 'fullname', label: 'Full Name', selected: this.fullname.value },
    { def: 'phone', label: 'Phone', selected: this.phone.value },
    { def: 'dob', label: 'D.O.B', selected: this.dob.value },
    { def: 'gender', label: 'Gender', selected: this.gender.value },
    { def: 'address', label: 'Address', selected: this.address.value },
    { def: 'role', label: 'Role', selected: this.role.value },
    { def: 'avatar', label: 'Avatar', selected: this.avatar.value },
    { def: 'status', label: 'Status', selected: this.status.value },
    { def: 'action', label: 'Action', selected: this.action.value },
  ];

  getDisplayedColumns() {
    this.columns = this.columnDefinitions
      .filter((cd) => cd.selected)
      .map((cd) => cd.def);
  }

  ngOnInit(): void {
    this.getAllUserData();
    this.getAllRole();
    this.getAllStatus();

    this.currentRole = localStorage.getItem('role');
  }

  columnDef() {
    this.columnDefinitions[0].selected = true;
    this.columnDefinitions[1].selected = true;
    this.columnDefinitions[3].selected = true;
    this.columnDefinitions[4].selected = true;
    this.columnDefinitions[5].selected = true;
    this.columnDefinitions[12].selected = true;
    this.columnDefinitions[13].selected = true;

    this.columnDefinitions[2].selected = this.email.value;
    this.columnDefinitions[6].selected = this.phone.value;
    this.columnDefinitions[7].selected = this.dob.value;
    this.columnDefinitions[8].selected = this.gender.value;
    this.columnDefinitions[9].selected = this.address.value;
    this.columnDefinitions[10].selected = this.role.value;
    this.columnDefinitions[11].selected = this.avatar.value;
  }

  showColumn() {
    const filterHide = JSON.parse(
      localStorage.getItem('filtered-columns-users')
    );

    if (filterHide) {
      this.fEmail = filterHide[2]['selected'];
      this.fPhone = filterHide[6]['selected'];
      this.fDob = filterHide[7]['selected'];
      this.fGender = filterHide[8]['selected'];
      this.fAddress = filterHide[9]['selected'];
      this.fRole = filterHide[10]['selected'];
      this.fAvatar = filterHide[11]['selected'];

      this.form.patchValue({
        email: this.fEmail,
        phone: this.fPhone,
        dob: this.fDob,
        gender: this.fGender,
        address: this.fAddress,
        role: this.fRole,
        avatar: this.fAvatar,
      });

      for (let i = 0; i < filterHide.length; i++) {
        this.columnDefinitions[i].selected = filterHide[i]['selected'];
      }
    } else {
      this.columnDef();
    }
  }

  onApplyFilter() {
    this.columnDef();

    this.getDisplayedColumns();
    localStorage.setItem(
      'filtered-columns-users',
      JSON.stringify(this.columnDefinitions)
    );
  }

  // Get all User
  getAllUserData() {
    this.userControllerService
      .getAllUsersPageUsingGET(this.pageSize, '', this.pageIndex, '')
      .subscribe((res) => {
        this.data = res['data'];
        this.length = res['totalItems'];
        this.columnDisplay = this.data[0];
        this.showColumn();
        this.getDisplayedColumns();

        this.dataSource = new MatTableDataSource(this.data);
      });
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

  // Add new User
  openAddUserDialog(): void {
    this.dialog.open(AddUserComponent, {
      width: '700px',
      height: '870px',
      hasBackdrop: true,
    });
  }

  // Get User to Delete
  openDeleteUserDialog(id: number, name: string) {
    this.idOfUser = id;
    this.nameOfUser = name;
    this.dialog.open(DeleteUserComponent, {
      data: { id: this.idOfUser, name: this.nameOfUser },
      width: '500px',
      hasBackdrop: true,
    });
  }

  // Get Users to Edit
  onEditSelectedUser(id?: number) {
    this.idOfUser = id;
    this.dialog.open(EditUserComponent, {
      data: { id: this.idOfUser },
      width: '700px',
      height: '870px',
      hasBackdrop: true,
    });
  }

  // Paginator
  length: any;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [2, 5, 10];
  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;
  public columnDisplay: any;
  public pageEvent: PageEvent | undefined;
  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.applySearch();
  }

  // Search
  applySearch() {
    this.userControllerService
      .filterUsersByRoleAndStatusUsingGET(
        this.searchKey,
        '',
        this.pageIndex,
        this.searchRole,
        this.pageSize,
        '',
        this.searchStatus
      )
      .subscribe((searching) => {
        this.data = searching['data'];
        this.length = searching['totalItems'];
        this.dataSource = new MatTableDataSource(this.data);
      });
  }

  openDetailsDialog(id: any) {
    this.idAsset = id;
    this.dialog.open(DetailsUserComponent, {
      data: { id: this.idAsset },
      hasBackdrop: true,
    });
  }

  // Get Users by ID
  onGetUsers(id: number) {
    console.log(id);
    this.userControllerService.getUserByIdUsingGET(id).subscribe();
    this.idOfUser = id;
  }

  // Edit single User
  onEditUser(id: number, value: any) {
    let dto: AppUserDto = {
      address: value.address,
      avatar: value.avatar,
      birthDate: value.birthDate,
      email: value.email,
      firstName: value.firstName,
      fullName: value.fullName,
      gender: value.gender,
      lastName: value.lastName,
      password: value.password,
      phone: value.phone,
      role: value.role,
      status: value.status,
      username: value.username,
    };
    console.log(id);
    this.userControllerService.updateUserUsingPUT(dto, id).subscribe();
  }
}
