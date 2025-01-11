import { TestBed } from '@angular/core/testing';

import { RoleUserGuard } from './role-user.guard';

describe('RoleUserGuard', () => {
  let guard: RoleUserGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RoleUserGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
