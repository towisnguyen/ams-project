import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabHistoryUserComponent } from './tab-history-user.component';

describe('TabHistoryUserComponent', () => {
  let component: TabHistoryUserComponent;
  let fixture: ComponentFixture<TabHistoryUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabHistoryUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabHistoryUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
