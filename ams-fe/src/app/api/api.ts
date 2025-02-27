export * from './assetController.service';
import { AssetControllerService } from './assetController.service';
export * from './assetSupplierController.service';
import { AssetSupplierControllerService } from './assetSupplierController.service';
export * from './jwtAuthenticationController.service';
import { JwtAuthenticationControllerService } from './jwtAuthenticationController.service';
export * from './projectController.service';
import { ProjectControllerService } from './projectController.service';
export * from './requestController.service';
import { RequestControllerService } from './requestController.service';
export * from './roleController.service';
import { RoleControllerService } from './roleController.service';
export * from './userController.service';
import { UserControllerService } from './userController.service';
export const APIS = [AssetControllerService, AssetSupplierControllerService, JwtAuthenticationControllerService, ProjectControllerService, RequestControllerService, RoleControllerService, UserControllerService];
