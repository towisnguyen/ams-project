import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserControllerService } from 'src/app/api/userController.service';

@Component({
  selector: 'app-tab-details-user',
  templateUrl: './tab-details-user.component.html',
  styleUrls: ['./tab-details-user.component.scss'],
})
export class TabDetailsUserComponent {
  fullData: any;

  constructor(
    private userControllerService: UserControllerService,
    @Inject(MAT_DIALOG_DATA) public data: { id: any }
  ) {}

  ngOnInit(): void {
    this.getDetails();
  }

  getDetails() {
    this.userControllerService
      .getUserByIdUsingGET(this.data.id)
      .subscribe((response) => {
        this.fullData = response;
        console.log(this.fullData);
      });
  }
}
