import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserImagesDialogComponent } from './user-images-dialog.component';

describe('UserImagesDialogComponent', () => {
  let component: UserImagesDialogComponent;
  let fixture: ComponentFixture<UserImagesDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserImagesDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserImagesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
