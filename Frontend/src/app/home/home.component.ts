import {Component, ViewChild} from '@angular/core';
import {HeaderComponent} from "./header/header.component";
import {UsersListComponent} from "./users/users-list/users-list.component";
import {GendersListComponent} from "./genders/genders-list/genders-list.component";
import {JobPositionsListComponent} from "./jobPositions/job-positions-list/job-positions-list.component";
import {CreateUserDialogComponent} from "./users/create-user-dialog/create-user-dialog.component";


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    HeaderComponent,
    UsersListComponent,
    GendersListComponent,
    JobPositionsListComponent,
    CreateUserDialogComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  @ViewChild('usersList') usersListComponent!: UsersListComponent;

  isManagingGenders = false;
  isManagingJobPositions = false;

  onManagingGender() {
    this.isManagingGenders = true;
  }

  onManagingGenderClose() {
    this.isManagingGenders = false;
  }

  onManagingJobPosition() {
    this.isManagingJobPositions = true;
  }

  onManagingJobPositionClose() {
    this.isManagingJobPositions = false;
  }

  onUsersChanged() {
    this.usersListComponent.loadUsers();
  }
}
