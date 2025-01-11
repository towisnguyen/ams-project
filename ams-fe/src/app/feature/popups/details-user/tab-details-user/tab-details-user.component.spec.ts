import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabDetailsUserComponent } from './tab-details-user.component';

describe('TabDetailsUserComponent', () => {
  let component: TabDetailsUserComponent;
  let fixture: ComponentFixture<TabDetailsUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabDetailsUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabDetailsUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
