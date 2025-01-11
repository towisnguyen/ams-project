import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogRequestComponent } from './dialog-request.component';

describe('DialogRequestComponent', () => {
  let component: DialogRequestComponent;
  let fixture: ComponentFixture<DialogRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
