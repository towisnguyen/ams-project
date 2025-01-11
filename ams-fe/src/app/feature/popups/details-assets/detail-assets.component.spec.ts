import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailAssetsComponent } from './detail-assets.component';

describe('DetailAssetsComponent', () => {
  let component: DetailAssetsComponent;
  let fixture: ComponentFixture<DetailAssetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailAssetsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailAssetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
