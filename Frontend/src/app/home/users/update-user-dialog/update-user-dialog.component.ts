import { Component, EventEmitter, Input, OnInit, Output, DestroyRef, inject } from '@angular/core';
import {
  AbstractControl,
  AsyncValidatorFn,
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule, ValidationErrors, ValidatorFn,
  Validators
} from "@angular/forms";

import { debounceTime, map, Observable, of, Subscription, switchMap } from 'rxjs';
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {User} from "../../../models/user";
import {Gender} from "../../../models/gender";
import {JobPosition} from "../../../models/jobPosition";
import {Address} from "../../../models/address";
import {UserService} from "../../../services/user.service";
import {GenderService} from "../../../services/gender.service";
import {JobPositionService} from "../../../services/job-position.service";
import {AddressService} from "../../../services/address.service";

@Component({
  selector: 'app-update-user-dialog',
  standalone: true,
  imports: [
    DatePipe,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './update-user-dialog.component.html',
  styleUrls: ['./update-user-dialog.component.css']
})
export class UpdateUserDialogComponent implements OnInit {

  @Input({ required: true }) userId!: string;
  @Output() close = new EventEmitter<void>();
  @Output() userUpdated = new EventEmitter<void>();

  user!: User;
  users: User[] = [];
  userForm!: FormGroup;

  genders: Gender[] = [];
  jobPositions: JobPosition[] = [];

  selectedMainAddressIndex: number | null = null;
  selectedAddressIndex: number | null = null;
  backendAddresses: Address[] = [];
  deletedAddresses: string[] = [];
  isEditing = false;

  private destroyRef = inject(DestroyRef);

  constructor(private fb: FormBuilder, private userService: UserService,
              private genderService: GenderService,
              private jobPositionService: JobPositionService,
              private addressService: AddressService) { }

  ngOnInit() {
    this.userService.getAllUsers().subscribe(users => this.users = users);
    this.genderService.getAllGenders().subscribe(genders => this.genders = genders);
    this.jobPositionService.getAllJobPositions().subscribe(jobPositions => this.jobPositions = jobPositions);

    this.userService.getUser(this.userId).subscribe(user => {
      this.user = user;

      this.userForm = this.fb.group({
        username: [this.user.username, [Validators.required, Validators.minLength(3)], [this.usernameValidator()]],
        password: [this.user.password, [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,}$/)]],
        creationDate: [this.user.creation_datetime],
        firstName: [this.user.first_name, [Validators.required]],
        firstSurname: [this.user.first_surname, [Validators.required]],
        secondSurname: [this.user.second_surname],
        birthDate: [this.formatDateForInput(this.user.birthdate), [Validators.required, this.birthDateValidator()]],
        gender: [this.user.gender.name, [Validators.required]],
        jobPosition: [this.user.job_position?.name ?? "-"],
        breakfastTime: [this.user.time_of_breakfast, [Validators.required]],
        addresses: this.fb.array([])
      });

      this.addressService.getUserAddresses(this.userId).subscribe((addresses) => {
        this.backendAddresses = addresses;

        addresses.forEach((address, index) => {
          if (address.main_address) {
            this.selectedMainAddressIndex = index;
            this.selectedAddressIndex = index;
          }
          this.addresses.push(this.fb.group({
            id: [address.id],
            street_name: [{ value: address.street_name, disabled: index !== this.selectedAddressIndex }],
            street_number: [{ value: address.street_number, disabled: index !== this.selectedAddressIndex }],
            main_address: [address.main_address],
            user_id: [this.userId]
          }));
        });
      });
    });
  }

  formatDateForInput(dateString: string): string {
    const date = new Date(dateString);
    return date.toISOString().slice(0, 16);
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
      const addressId = this.addresses.at(index).value.id;
      if (addressId) {
        this.deletedAddresses.push(addressId);
      }
      this.addresses.removeAt(index);
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
    this.isEditing = false;

    this.addresses.controls.forEach((addressGroup, i) => {
        addressGroup.get('street_name')?.disable();
        addressGroup.get('street_number')?.disable();
    });
  }

  updateAddress() {

    this.isEditing = !this.isEditing;
    this.addresses.controls.forEach((addressGroup, i) => {
      if (i === this.selectedAddressIndex && this.isEditing) {
        addressGroup.get('street_name')?.enable();
        addressGroup.get('street_number')?.enable();
      }else{
        addressGroup.get('street_name')?.disable();
        addressGroup.get('street_number')?.disable();
      }
    });
  }

  onCancel() {
    this.close.emit();
  }

  onSubmit() {
    if (this.userForm.valid) {
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
        id: this.user.id,
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

      let addresses: Address[] = this.addresses.controls.map((addressGroup, index) => {
        const address = addressGroup.value;
        return {
          ...address,
          street_name: addressGroup.get('street_name')?.getRawValue(),
          street_number: addressGroup.get('street_number')?.getRawValue(),
          main_address: index === this.selectedMainAddressIndex,
          user: user,
        };
      });

      console.log(addresses);

      this.userService.updateUser({ user, addresses }).subscribe(() => {
        this.userUpdated.emit();
        this.close.emit();
      });
    } else {
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
  private isUsernameTaken(username: string): boolean {
    let usersFiltered: User[] = this.users.filter(user => user.id != this.userId);
    return usersFiltered.some(user => user.username === username);
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
