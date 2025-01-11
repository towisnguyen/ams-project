import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { AssetControllerService } from 'src/app/api/assetController.service';

@Component({
  selector: 'app-confirm-delete-asset',
  templateUrl: './confirm-delete-asset.component.html',
  styleUrls: ['./confirm-delete-asset.component.scss']
})
export class ConfirmDeleteAssetComponent implements OnInit{

  assetName = ''

  constructor(
    private assetControllerService: AssetControllerService,
    @Inject(MAT_DIALOG_DATA) public data: {id: number, name: string},
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.assetName = this.data.name
  }

  deleteAsset() {
    this.assetControllerService.deleteAssetByIdUsingDELETE(this.data.id).subscribe()
    console.log('Deleted')
    this.closePopup()
    window.location.reload();
  }

  closePopup() {
    this.dialog.closeAll()
  }
}
