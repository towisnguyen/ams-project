import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabDetailsComponent } from './tab-details-assets.component';

describe('TabDetailsComponent', () => {
  let component: TabDetailsComponent;
  let fixture: ComponentFixture<TabDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
