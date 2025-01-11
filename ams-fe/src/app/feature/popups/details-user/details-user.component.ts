import { Component, Inject } from '@angular/core';
import { UserControllerService } from '../../../api/userController.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-details-user',
  templateUrl: './details-user.component.html',
  styleUrls: ['./details-user.component.scss']
})
export class DetailsUserComponent {

  fullData: any;
  name = '';

  constructor(private userControllerService: UserControllerService,
    @Inject(MAT_DIALOG_DATA) public data: {id: any}) {}

    ngOnInit(): void {
      this.getDetails()
    }

  getDetails() {
    this.userControllerService.getUserByIdUsingGET(this.data.id).subscribe (response =>{
      console.log(this.data.id)
      this.fullData = response
      console.log(this.fullData)
      this.name = this.fullData.fullName
    })
  }
}
