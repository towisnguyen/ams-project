import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectsManagementComponent } from './projects-management.component';

describe('ProjectsManagementComponent', () => {
  let component: ProjectsManagementComponent;
  let fixture: ComponentFixture<ProjectsManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectsManagementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectsManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
