import { TestBed } from '@angular/core/testing';

import { RoleAdminGuard } from './role-admin.guard';

describe('RoleGuard', () => {
  let guard: RoleAdminGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RoleAdminGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
