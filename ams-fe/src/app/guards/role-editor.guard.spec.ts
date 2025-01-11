import { TestBed } from '@angular/core/testing';

import { RoleEditorGuard } from './role-editor.guard';

describe('RoleEditorGuard', () => {
  let guard: RoleEditorGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RoleEditorGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
