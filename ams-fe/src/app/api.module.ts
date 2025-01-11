import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AssetControllerService } from './api/assetController.service';
import { AssetSupplierControllerService } from './api/assetSupplierController.service';
import { JwtAuthenticationControllerService } from './api/jwtAuthenticationController.service';
import { ProjectControllerService } from './api/projectController.service';
import { RequestControllerService } from './api/requestController.service';
import { RoleControllerService } from './api/roleController.service';
import { UserControllerService } from './api/userController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    AssetControllerService,
    AssetSupplierControllerService,
    JwtAuthenticationControllerService,
    ProjectControllerService,
    RequestControllerService,
    RoleControllerService,
    UserControllerService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders<ApiModule> {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
