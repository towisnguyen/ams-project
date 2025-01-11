import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDeleteAssetComponent } from '././confirm-delete-asset.component';

describe('ConfirmRemoveDeviceComponent', () => {
  let component: ConfirmDeleteAssetComponent;
  let fixture: ComponentFixture<ConfirmDeleteAssetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConfirmDeleteAssetComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ConfirmDeleteAssetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
