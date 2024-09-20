import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {UserService} from "../../../services/user.service";
import {UserImage} from "../../../models/userImage";

@Component({
  selector: 'app-user-images-dialog',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './user-images-dialog.component.html',
  styleUrl: './user-images-dialog.component.css'
})
export class UserImagesDialogComponent {
  @Input() userId!: string;
  @Output() close = new EventEmitter<void>();

  userImageForm: FormGroup;
  userImage: UserImage | null = null;

  constructor(private fb: FormBuilder, private userService: UserService) {
    this.userImageForm = this.fb.group({
      id: [null],
      image: ['', Validators.required],
      user: [this.userId, Validators.required]
    });
  }

  ngOnInit() {
    this.loadUserImage();
  }

  loadUserImage() {
    this.userService.getUserImage(this.userId).subscribe(userImage => {
      this.userImage = userImage;
      if (userImage && userImage.image) {
        this.userImageForm.patchValue({
          id: userImage.id,
          image: userImage.image,
          user: userImage.user.id
        });
      }
    });
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        const base64Image = (reader.result as string).split(',')[1];
        this.userImageForm.get('image')?.setValue(base64Image);
      };
      reader.readAsDataURL(file);
    }
  }

  onUploadCreateImage() {
    const imageControl = this.userImageForm.get('image');
    if (imageControl && imageControl.value) {
      const imageBase64 = imageControl.value as string;
      this.userService.createUserImage(this.userId, imageBase64).subscribe(
        () => {
          console.log('User Image created successfully');
          this.close.emit();
        },
        (error) => {
          console.error('Error creating user image:', error);
        }
      );
    } else {
      console.error('Image is required');
    }
  }


  deleteImage() {
    if (this.userImage?.id) {
      this.userService.deleteUserImage(this.userImage.id).subscribe(() => {
        console.log('User Image deleted successfully');
        this.close.emit();
      });
    }
  }

  onCancel() {
    this.close.emit();
  }
}
