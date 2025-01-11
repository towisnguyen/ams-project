import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferDeviceComponent } from './transfer-device.component';

describe('TransferDeviceComponent', () => {
  let component: TransferDeviceComponent;
  let fixture: ComponentFixture<TransferDeviceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferDeviceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransferDeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
