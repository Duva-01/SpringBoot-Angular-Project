import {Component, DestroyRef, OnInit} from '@angular/core';
import {DatePipe, NgForOf, NgIf} from "@angular/common";

import {RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {CreateUserDialogComponent} from "../create-user-dialog/create-user-dialog.component";
import {UpdateUserDialogComponent} from "../update-user-dialog/update-user-dialog.component";
import {Address} from "../../../models/address";
import {HeaderComponent} from "../../header/header.component";
import {User} from "../../../models/user";
import {calculateAge, UserService} from "../../../services/user.service";
import {AddressService, getMainAddress} from "../../../services/address.service";
import {DeleteDialogComponent} from "../../../shared/delete-dialog/delete-dialog.component";
import {AuthService} from "../../../services/auth.service";
import {UserImagesDialogComponent} from "../user-images-dialog/user-images-dialog.component";

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    DatePipe,
    FormsModule,
    CreateUserDialogComponent,
    NgIf,
    UpdateUserDialogComponent,
    HeaderComponent,
    DeleteDialogComponent,
    UserImagesDialogComponent,
  ],
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.css'
})
export class UsersListComponent implements OnInit{

  users: { user: User, addresses: Address[] }[] = [];
  selectedUserIndex: number = 0;
  selectedUserId: string = "";

  isAddingUser = false;
  isUpdatingUser = false;
  isDeletingUser = false;
  isManagingUserImage = false;

  loggedUserId: string | undefined = "";


  constructor( private userService: UserService, private addressService: AddressService, private authService: AuthService
  ) {
    this.loggedUserId = authService.getUser()?.id;
  }

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: users => {
        this.users = [];
        users.forEach((user, index) => {
          this.addressService.getUserAddresses(user.id).subscribe({
            next: addresses => {
              this.users.push({ user, addresses });

              if (index === 0) {
                this.selectedUserIndex = 0;
              }

            },
            error: error => console.error(error)
          });
        });

      },
      error: error => console.error(error)
    });
  }

  onSelectUser(index: number) {
    this.selectedUserIndex = index;
  }

  convertToDate(timeString: string): Date {
    const today = new Date();
    const [hours, minutes, seconds] = timeString.split(':').map(Number);
    return new Date(today.getFullYear(), today.getMonth(), today.getDate(), hours, minutes, seconds);
  }

  onCreate() {
    this.isAddingUser = true;
  }

  onCreateClose() {
    this.isAddingUser = false;
  }

  onUpdate() {

    if(this.users[this.selectedUserIndex] != null) {
      this.selectedUserId = this.users[this.selectedUserIndex].user.id;
      this.isUpdatingUser = true;
    }
  }

  onUpdateClose() {
    this.isUpdatingUser = false;
  }

  confirmDelete() {

    if( this.users[this.selectedUserIndex].user.id != this.loggedUserId ) {
      this.isDeletingUser = true;
    }else {
      alert("Can't delete logged user");
    }
  }

  onCancelDelete() {
    this.isDeletingUser = false;
  }

  onConfirmDelete() {
    const selectedUser = this.users.at(this.selectedUserIndex);
    if (selectedUser?.user.id) {

      this.userService.deleteUser(selectedUser.user.id).subscribe({
        next: () => {
          console.log('User deleted successfully');
          this.users.splice(this.selectedUserIndex, 1);
          this.selectedUserIndex = this.users.length > 0 ? 0 : -1;
          this.isDeletingUser = false;
        },
        error: error => console.error('Error deleting user:', error)
      });
    } else {
      console.error('Selected user is not valid.');
      this.isDeletingUser = false;
    }
  }

  onUserImage(){

    if(this.users[this.selectedUserIndex] != null) {
      this.selectedUserId = this.users[this.selectedUserIndex].user.id;
      this.isManagingUserImage = true;
    }
  }

  onUserImageClose(){
    this.isManagingUserImage = false;
  }

  protected readonly calculateAge = calculateAge;
  protected readonly getMainAddress = getMainAddress;
}
