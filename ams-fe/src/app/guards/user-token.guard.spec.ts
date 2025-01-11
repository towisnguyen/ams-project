import { TestBed } from '@angular/core/testing';

import { UserTokenGuard } from './user-token.guard';

describe('UserTokenGuard', () => {
  let guard: UserTokenGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(UserTokenGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
