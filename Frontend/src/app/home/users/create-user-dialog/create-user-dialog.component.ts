import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  AsyncValidatorFn,
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule, ValidationErrors, ValidatorFn,
  Validators
} from "@angular/forms";
import { DatePipe, NgForOf, NgIf } from "@angular/common";
import {debounceTime, map, Observable, of, switchMap} from "rxjs";
import {User} from "../../../models/user";
import {Gender} from "../../../models/gender";
import {JobPosition} from "../../../models/jobPosition";
import {UserService} from "../../../services/user.service";
import {GenderService} from "../../../services/gender.service";
import {JobPositionService} from "../../../services/job-position.service";
import {AddressService} from "../../../services/address.service";

@Component({
  selector: 'app-create-user-dialog',
  standalone: true,
  imports: [
    FormsModule,
    DatePipe,
    ReactiveFormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './create-user-dialog.component.html',
  styleUrl: './create-user-dialog.component.css'
})
export class CreateUserDialogComponent implements OnInit {

  @Output() close = new EventEmitter<void>();
  @Output() userCreated = new EventEmitter<void>();

  userForm: FormGroup;
  users: User[] = [];
  genders: Gender[] = [];
  jobPositions: JobPosition[] = [];
  selectedMainAddressIndex: number | null = null;
  selectedAddressIndex: number | null = null;

  constructor(private fb: FormBuilder, private userService: UserService,
              private genderService: GenderService,
              private jobPositionService: JobPositionService,
              private addressService: AddressService) {

    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)], [this.usernameValidator()]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,}$/)]],
      creationDate: [new Date()],
      firstName: ['', [Validators.required]],
      firstSurname: ['', [Validators.required]],
      secondSurname: [''],
      birthDate: ['', [Validators.required, this.birthDateValidator()]],
      gender: ['Male', [Validators.required]],
      jobPosition: ['-'],
      breakfastTime: ['', [Validators.required]],
      addresses: this.fb.array([])
    });
  }

  ngOnInit() {
    this.userService.getAllUsers().subscribe(users => this.users = users);
    this.genderService.getAllGenders().subscribe(genders => this.genders = genders);
    this.jobPositionService.getAllJobPositions().subscribe(jobPositions => this.jobPositions = jobPositions);
  }

  get addresses() {
    return this.userForm.get('addresses') as FormArray;
  }

  addAddress() {
    const newAddress = this.fb.group({
      id: [''],
      street_name: [''],
      street_number: [''],
      main_address: [false],
      user_id: ['']
    });
    this.addresses.push(newAddress);
  }

  deleteAddress(index: number | null) {
    if (index !== null) {
      this.addresses.removeAt(index);
      if (this.selectedMainAddressIndex === index) {
        this.selectedMainAddressIndex = null;
      }
      if (this.selectedAddressIndex === index) {
        this.selectedAddressIndex = null;
      }
    }
  }

  selectMainAddress(index: number) {
    this.selectedMainAddressIndex = index;
    this.addresses.controls.forEach((addressGroup, i) => {
      addressGroup.get('main_address')?.setValue(i === index);
    });
  }

  selectAddress(index: number) {
    this.selectedAddressIndex = index;
  }

  onSubmit() {

    if (this.userForm.valid) {
      const username = this.userForm.get('username')?.value;

      if (this.isUsernameTaken(username)) {
        alert('Username is already taken.');
      } else {
        const selectedGender = this.genders.find(gender => gender.name === this.userForm.controls['gender'].value);
        const selectedJobPosition = this.jobPositions.find(jobPosition => jobPosition.name === this.userForm.controls['jobPosition'].value) ?? null;

        if (!selectedGender) {
          console.error('Gender or Job Position not found!');
          return;
        }

        let timeOfBreakfast = this.userForm.controls['breakfastTime'].value;
        if (timeOfBreakfast.length === 5) {
          timeOfBreakfast += ':00';
        }

        let user: User = {
          id: "",
          username: this.userForm.controls['username'].value,
          password: this.userForm.controls['password'].value,
          creation_datetime: this.userForm.controls['creationDate'].value,
          first_name: this.userForm.controls['firstName'].value,
          first_surname: this.userForm.controls['firstSurname'].value,
          second_surname: this.userForm.controls['secondSurname'].value,
          birthdate: this.userForm.controls['birthDate'].value,
          gender: selectedGender,
          job_position: selectedJobPosition,
          time_of_breakfast: timeOfBreakfast,
        };

        this.userService.createUser(user).subscribe((newUser) => {
          this.addresses.controls.forEach((addressGroup, index) => {
            const address = addressGroup.value;
            address.user = newUser;
            address.main_address = index === this.selectedMainAddressIndex;
            this.addressService.createAddress(address).subscribe();
          });

          this.userCreated.emit();
          this.close.emit();
        });
      }
    }
    else {
      alert("Please fix the errors before saving the form.");
      this.markFormGroupTouched(this.userForm);
    }
  }

  private markFormGroupTouched(formGroup: FormGroup | FormArray) {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);

      if (control instanceof FormGroup || control instanceof FormArray) {
        this.markFormGroupTouched(control);
      } else {
        control?.markAsTouched();
      }
    });
  }

  onCancel() {
    this.close.emit();
  }

  private isUsernameTaken(username: string): boolean {
    return this.users.some(user => user.username === username);
  }

  private usernameValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<{ [key: string]: any } | null> => {
      return of(control.value).pipe(
        debounceTime(300),
        switchMap(username => of(this.isUsernameTaken(username))),
        map(isTaken => (isTaken ? { usernameTaken: true } : null))
      );
    };
  }

  birthDateValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const birthDate = new Date(control.value);
      const today = new Date();

      today.setHours(0, 0, 0, 0);

      return birthDate < today ? null : { birthDateInvalid: true };
    };
  }


}
