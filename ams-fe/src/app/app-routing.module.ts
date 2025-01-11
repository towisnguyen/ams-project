import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DefaultLayoutComponent } from './layouts/default-layout/default-layout.component';
import { DashboardComponent } from './feature/dashboard/dashboard.component';
import { LoginPageComponent } from './feature/auth/login-page/login-page.component';
import { AssetsManagementComponent } from './feature/assets-management/assets-management.component';
import { UsersManagementComponent } from './feature/users-management/users-management.component';
import { RequestManagementComponent } from './feature/request-management/request-management.component';
import { UserTokenGuard } from './guards/user-token.guard';
import { RoleAdminGuard } from './guards/role-admin.guard';
import { PieChartsComponent } from './feature/dashboard/charts/pie-charts/pie-charts.component';
import { BarChartsComponent } from './feature/dashboard/charts/bar-charts/bar-charts.component';
import { ProjectsManagementComponent } from './feature/projects-management/projects-management.component';

const routes: Routes = [
  { path: '', component: LoginPageComponent },
  { path: 'login', component: LoginPageComponent },
  {
    path: 'home',
    component: DefaultLayoutComponent,
    data: {
      breadcrumb: 'Home',
    },
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
        data: {
          breadcrumb: '',
        },
      },
      {
        path: 'projects',
        canActivate: [UserTokenGuard],
        data: {
          breadcrumb: 'Projects Management',
          onSelect: 'Projects Management',
        },
        children: [
          {
            path: 'all',
            component: ProjectsManagementComponent,
            data: {
              breadcrumb: 'All Projects',
              onSelectSidebar: 'All Projects',
            },
          },
          {
            path: '',
            redirectTo: 'all',
            pathMatch: 'full',
            data: {
              breadcrumb: '',
            },
          },
        ],
      },
      {
        path: 'users',
        canActivate: [UserTokenGuard],
        data: {
          breadcrumb: 'Users Management',
          onSelect: 'Users Management',
        },
        children: [
          {
            path: 'allusers',
            component: UsersManagementComponent,
            data: {
              breadcrumb: 'All Users',
              onSelectSidebar: 'All Users',
            },
          },
          {
            path: '',
            redirectTo: 'allusers',
            pathMatch: 'full',
            data: {
              breadcrumb: '',
            },
          },
        ],
      },
      {
        path: 'dashboard',
        canActivate: [UserTokenGuard, RoleAdminGuard],
        data: {
          breadcrumb: 'Dashboards',
          onSelect: 'Dashboards',
        },
        children: [
          {
            path: '',
            redirectTo: 'alldashboard',
            pathMatch: 'full',
            data: {
              breadcrumb: '',
            },
          },
          {
            path: 'alldashboard',
            component: DashboardComponent,
            data: {
              breadcrumb: 'All Dashboard',
              onSelectSidebar: 'All Dashboard',
            },
          },
          {
            path: 'assetsdashboard',
            component: PieChartsComponent,
            data: {
              breadcrumb: 'Assets Dashboard',
              onSelectSidebar: 'Assets Dashboard',
            },
          },
          {
            path: 'projectsdashboard',
            component: BarChartsComponent,
            data: {
              breadcrumb: 'Projects Dashboard',
              onSelectSidebar: 'Projects Dashboard',
            },
          },
        ],
      },
      {
        path: 'assets',
        canActivate: [UserTokenGuard],
        data: {
          breadcrumb: 'Assets',
          onSelect: 'Assets',
        },
        children: [
          {
            path: '',
            redirectTo: 'allassets',
            pathMatch: 'full',
            data: {
              breadcrumb: '',
            },
          },
          {
            path: 'allassets',
            component: AssetsManagementComponent,
            data: {
              breadcrumb: 'All Assets',
              onSelectSidebar: 'All Assets',
            },
          },
        ],
      },
      {
        path: 'requests',
        canActivate: [UserTokenGuard],
        data: {
          breadcrumb: 'Requests',
          onSelect: 'Requests',
        },
        children: [
          {
            path: '',
            redirectTo: 'allrequests',
            pathMatch: 'full',
            data: {
              breadcrumb: '',
            },
          },
          {
            path: 'allrequests',
            component: RequestManagementComponent,
            data: {
              breadcrumb: 'All Requests',
              onSelectSidebar: 'All Requests',
            },
          },
          {
            path: 'approved',
            component: RequestManagementComponent,
            data: {
              breadcrumb: 'Approved',
              status: 'Approved',
              onSelectSidebar: 'Approved',
            },
          },
          {
            path: 'closed',
            component: RequestManagementComponent,
            data: {
              breadcrumb: 'Closed',
              status: 'Closed',
              onSelectSidebar: 'Closed',
            },
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
  constructor() {}
}
