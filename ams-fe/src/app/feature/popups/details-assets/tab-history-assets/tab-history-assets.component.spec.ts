import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabHistoryComponent } from './tab-history-assets.component';

describe('TabHistoryComponent', () => {
  let component: TabHistoryComponent;
  let fixture: ComponentFixture<TabHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
