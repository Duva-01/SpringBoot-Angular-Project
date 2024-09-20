import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobPositionsListComponent } from './job-positions-list.component';

describe('JobPositionsListComponent', () => {
  let component: JobPositionsListComponent;
  let fixture: ComponentFixture<JobPositionsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JobPositionsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JobPositionsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
