import { StorageService } from './authen/storage.service';
import { UserControllerService } from './api/userController.service';
import { RoleControllerService } from './api/roleController.service';
import { JwtAuthenticationControllerService } from './api/jwtAuthenticationController.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DEFAULT_OPTIONS } from '@angular/material/dialog';
import { MatPaginatorModule } from '@angular/material/paginator';
import {} from '@angular/material/';
import { AppRoutingModule } from './app-routing.module';
import { Interceptor } from './authen/authen.Interceptor';
import { RouterModule } from '@angular/router';
import { SharedModule } from './shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppComponent } from './app.component';
import { DefaultLayoutComponent } from './layouts/default-layout/default-layout.component';
import { AuthenticationLayoutComponent } from './layouts/authentication-layout/authentication-layout.component';
import { DashboardComponent } from './feature/dashboard/dashboard.component';
import { AuthenComponent } from './authen/authen.component';
import { AppRootComponent } from './app-root/app-root.component';
import { LoginPageComponent } from './feature/auth/login-page/login-page.component';
import { AssetsManagementComponent } from './feature/assets-management/assets-management.component';
import { UsersManagementComponent } from './feature/users-management/users-management.component';
import { RequestManagementComponent } from './feature/request-management/request-management.component';
import { TopWidgetsComponent } from './feature/dashboard/top-widgets/top-widgets.component';
import { LineChartsComponent } from './feature/dashboard/charts/line-charts/line-charts.component';
import { PieChartsComponent } from './feature/dashboard/charts/pie-charts/pie-charts.component';
import { BarChartsComponent } from './feature/dashboard/charts/bar-charts/bar-charts.component';
import { StatusChartComponent } from './feature/dashboard/charts/status-chart/status-chart.component';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatMenuModule } from '@angular/material/menu';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RequestControllerService } from './api/requestController.service';
import { AssetControllerService } from './api/assetController.service';
import { DetailAssetsComponent } from './feature/popups/details-assets/detail-assets.component';
import { MatTabsModule } from '@angular/material/tabs';
import { TabDetailsComponent } from './feature/popups/details-assets/tab-details-assets/tab-details-assets.component';
import { TabHistoryComponent } from './feature/popups/details-assets/tab-history-assets/tab-history-assets.component';
import { CamelcasePipe } from './shared/pipes/camelcase.pipe/camelcase.pipe';
import { DetailsUserComponent } from './feature/popups/details-user/details-user.component';
import { TabDetailsUserComponent } from './feature/popups/details-user/tab-details-user/tab-details-user.component';
import { TabHistoryUserComponent } from './feature/popups/details-user/tab-history-user/tab-history-user.component';
import { CategoryPipe } from './shared/pipes/category.pipe/category.pipe';
import { ProjectPipe } from './shared/pipes/project.pipe/project.pipe';
import { SupplierPipe } from './shared/pipes/supplier.pipe/supplier.pipe';
import { UserPipe } from './shared/pipes/user.pipe/user.pipe';
import { MatDivider, MatDividerModule } from '@angular/material/divider';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { CreateDialogComponent } from './feature/create-dialog/create-dialog.component';
import { MatOptionModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AssetSupplierControllerService } from './api/assetSupplierController.service';
import { ProjectControllerService } from './api/projectController.service';
import { EditProjectsComponent } from './shared/components/edit-projects/edit-projects.component';
import { ProjectsManagementComponent } from './feature/projects-management/projects-management.component';
import { AddProjectsComponent } from './shared/components/add-projects/add-projects.component';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { DeleteProjectComponent } from './shared/components/delete-project/delete-project.component';
import {MatButtonModule} from '@angular/material/button';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';

@NgModule({
  declarations: [
    AppComponent,
    DefaultLayoutComponent,
    AuthenticationLayoutComponent,
    DashboardComponent,
    AuthenComponent,
    AppRootComponent,
    LoginPageComponent,
    AssetsManagementComponent,
    UsersManagementComponent,
    RequestManagementComponent,
    TopWidgetsComponent,
    LineChartsComponent,
    PieChartsComponent,
    BarChartsComponent,
    StatusChartComponent,
    DetailAssetsComponent,
    TabDetailsComponent,
    TabHistoryComponent,
    DetailsUserComponent,
    TabDetailsUserComponent,
    TabHistoryUserComponent,
    CamelcasePipe,
    CategoryPipe,
    ProjectPipe,
    SupplierPipe,
    UserPipe,
    CreateDialogComponent,
    EditProjectsComponent,
    ProjectsManagementComponent,
    AddProjectsComponent,
    DeleteProjectComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    SharedModule,
    HttpClientModule,
    FormsModule,
    MatIconModule,
    MatPaginatorModule,
    MatTableModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatDialogModule,
    BrowserAnimationsModule,
    MatTabsModule,
    NgxPaginationModule,
    RouterModule,
    MatDividerModule,
    MatSelectModule,
    MatInputModule,
    MatOptionModule,
    MatCheckboxModule,
    MatButtonToggleModule,
    MatButtonModule
  ],
  providers: [
    UserControllerService,
    AssetControllerService,
    JwtAuthenticationControllerService,
    StorageService,
    RequestControllerService,
    RoleControllerService,
    AssetSupplierControllerService,
    ProjectControllerService,
    { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: { hasBackdrop: false } },
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },
    MatDialog,
    {
      provide: MatDialogRef,
      useValue: {},
    },
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
  ],
  bootstrap: [AppComponent],
  entryComponents: [CreateDialogComponent],
})
export class AppModule {}
