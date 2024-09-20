import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {CreateUserDialogComponent} from "../../users/create-user-dialog/create-user-dialog.component";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule, ValidatorFn,
  Validators
} from "@angular/forms";
import {UpdateUserDialogComponent} from "../../users/update-user-dialog/update-user-dialog.component";
import {Gender} from "../../../models/gender";
import {GenderService} from "../../../services/gender.service";
import {DeleteDialogComponent} from "../../../shared/delete-dialog/delete-dialog.component";
import {User} from "../../../models/user";
import {UserService} from "../../../services/user.service";


@Component({
  selector: 'app-genders-list',
  standalone: true,
  imports: [
    CreateUserDialogComponent,
    DatePipe,
    NgForOf,
    ReactiveFormsModule,
    UpdateUserDialogComponent,
    FormsModule,
    DeleteDialogComponent,
    NgIf,
  ],
  templateUrl: './genders-list.component.html',
  styleUrl: './genders-list.component.css'
})
export class GendersListComponent implements OnInit {

  @Output() close = new EventEmitter<void>();
  @Output() changesSaved = new EventEmitter<void>();

  gendersForm: FormGroup;
  selectedGenderIndex: number | null = null;
  editMode: boolean = false;
  users: User[] = [];

  constructor(private fb: FormBuilder, private genderService: GenderService, private userService: UserService) {
    this.gendersForm = this.fb.group({
      genders: this.fb.array([])
    });

    this.userService.getAllUsers().subscribe(users => this.users = users);
  }

  ngOnInit() {
    this.loadGenders();
  }

  get genders() {
    return this.gendersForm.get('genders') as FormArray;
  }

  loadGenders() {
    this.genderService.getAllGenders().subscribe({
      next: genders => {
        genders.forEach(gender => this.addGenderControl(gender));
      },
      error: error => console.error(error)
    });
  }

  addGenderControl(gender: Gender | null = null) {
    const genderGroup = this.fb.group({
      id: [gender?.id || null],
      name: [{ value: gender?.name || '', disabled: true }, [Validators.required, this.nameValidator()]],
    });

    this.genders.push(genderGroup);
  }

  onCreateNewGender() {
    const genderGroup = this.fb.group({
      id: [null],
      name: ['', [Validators.required, this.nameValidator()]],
    });
    this.genders.push(genderGroup);
    this.selectedGenderIndex = this.genders.length - 1;
    this.editMode = true;
    genderGroup.get('name')?.enable();
  }

  onUpdateGender() {
    if (this.selectedGenderIndex !== null) {
      this.editMode = true;
      this.genders.at(this.selectedGenderIndex).get('name')?.enable();
    } else {
      console.error('No gender selected for update');
    }
  }

  deleteGender() {

    if (this.selectedGenderIndex !== null) {

      const selectedGenderControl = this.genders.at(this.selectedGenderIndex);
      const genderId = selectedGenderControl.get('id')?.value;

      if(this.users.some(user => user.gender.id === genderId)){
        alert("You can't delete a gender that is being used!");
        return;
      }

      if (genderId) {
        this.genders.removeAt(this.selectedGenderIndex);
        this.selectedGenderIndex = this.genders.length > 0 ? 0 : null;
      } else {
        this.genders.removeAt(this.selectedGenderIndex);
        this.selectedGenderIndex = this.genders.length > 0 ? 0 : null;
      }

    } else {
      console.error('No gender selected for deletion');
    }
  }

  saveGenders() {

    if (this.gendersForm.invalid) {

      this.genders.controls.forEach(genderGroup => {
        genderGroup.markAllAsTouched();
      });

      alert('Please fix the errors before saving the form.');
      return;
    }

    const gendersChanges = this.genders.controls.map((genderGroup) => genderGroup.getRawValue());

    console.log(gendersChanges);
    this.genderService.saveGendersChanges(gendersChanges).subscribe({
      next: () => {
        console.log('Genders saved successfully');
        this.editMode = false;
        this.genders.controls.forEach(genderGroup => genderGroup.get('name')?.disable());
        this.changesSaved.emit();
      },
      error: error => console.error('Error saving genders:', error)
    });
  }

  selectGender(index: number) {
    this.selectedGenderIndex = index;
    this.editMode = false;
    this.genders.controls.forEach(genderGroup => genderGroup.get('name')?.disable());
  }

  private nameValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (!control || !control.parent) {
        return null;
      }
      const currentName = control.value;
      const genders = this.genders.controls;
      const duplicate = genders.some((genderControl) =>
        genderControl !== control.parent && genderControl.get('name')?.value === currentName
      );
      return duplicate ? { duplicateName: true } : null;
    };
  }

  onCancel() {
    this.close.emit();
  }
}
