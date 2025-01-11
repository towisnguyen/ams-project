import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HeaderbarComponent } from './components/headerbar/headerbar.component';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddUserComponent } from './components/add-user/add-user.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { DeleteUserComponent } from './components/delete-user/delete-user.component';
import { FirstCapitalizePipe } from './pipes/first-capitalize.pipe';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AddDeviceComponent } from './components/add-device/add-device.component';
import { EditDeviceComponent } from './components/edit-device/edit-device.component';
import { TransferDeviceComponent } from './components/transfer-device/transfer-device.component';
import { RemoveDeviceComponent } from './components/remove-device/remove-device.component';
import { DialogRequestComponent } from './components/dialog-request/dialog-request.component';
import { CreateRequestComponent } from './components/create-request/create-request.component';
import { ClickOutsideDirective } from './components/Directive/click-outside.directive';
import { ManageProfileComponent } from './components/manage-profile/manage-profile.component';
import { RoleControllerService } from '../api/roleController.service';
import { MatBadgeModule } from '@angular/material/badge';

@NgModule({
  declarations: [
    BreadcrumbComponent,
    SidebarComponent,
    HeaderbarComponent,
    AddUserComponent,
    DeleteUserComponent,
    FirstCapitalizePipe,
    EditUserComponent,
    AddDeviceComponent,
    EditDeviceComponent,
    TransferDeviceComponent,
    RemoveDeviceComponent,
    DialogRequestComponent,
    CreateRequestComponent,
    ClickOutsideDirective,
    ManageProfileComponent,
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    RouterModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatToolbarModule,
    MatIconModule,
    MatDividerModule,
    MatButtonModule,
    MatMenuModule,
    MatListModule,
    MatSidenavModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatBadgeModule,
  ],
  exports: [
    BreadcrumbComponent,
    SidebarComponent,
    HeaderbarComponent,
    AddDeviceComponent,
    EditDeviceComponent,
    TransferDeviceComponent,
    RemoveDeviceComponent,
    ManageProfileComponent,
  ],
  providers: [RoleControllerService],
})
export class SharedModule {}
