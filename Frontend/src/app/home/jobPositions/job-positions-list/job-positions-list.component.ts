import {Component, EventEmitter, OnInit, Output} from '@angular/core';

import {JobPosition} from "../../../models/jobPosition";
import {JobPositionService} from "../../../services/job-position.service";
import {DeleteDialogComponent} from "../../../shared/delete-dialog/delete-dialog.component";
import {NgForOf, NgIf} from "@angular/common";
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {Gender} from "../../../models/gender";
import {UserService} from "../../../services/user.service";
import {User} from "../../../models/user";

@Component({
  selector: 'app-job-positions-list',
  standalone: true,
  imports: [
    DeleteDialogComponent,
    NgForOf,
    ReactiveFormsModule,
    FormsModule,
    NgIf,
  ],
  templateUrl: './job-positions-list.component.html',
  styleUrl: './job-positions-list.component.css'
})
export class JobPositionsListComponent implements OnInit{

  @Output() close = new EventEmitter<void>();
  @Output() changesSaved = new EventEmitter<void>();

  jobPositionForm: FormGroup;
  selectedJobPositionIndex: number | null = null;
  editMode: boolean = false;
  users: User[] = [];

  constructor(private fb: FormBuilder, private jobPositionService: JobPositionService, private userService: UserService) {
    this.jobPositionForm = this.fb.group({
      jobPositions: this.fb.array([])
    });

    this.userService.getAllUsers().subscribe(users => this.users = users);
  }

  ngOnInit() {
    this.loadJobPositions();
  }

  get jobPositions() {
    return this.jobPositionForm.get('jobPositions') as FormArray;
  }


  loadJobPositions() {
    this.jobPositionService.getAllJobPositions().subscribe({
      next: jobPositions => {
        jobPositions.forEach(jobPosition => this.addJobPositionControl(jobPosition));
      },
      error: error => console.error(error)
    });
  }

  addJobPositionControl(jobPosition: JobPosition | null = null) {
    const jobPositionGroup = this.fb.group({
      id: [jobPosition?.id || null],
      name: [{ value: jobPosition?.name || '', disabled: true }, [Validators.required, this.nameValidator()]],
    });

    this.jobPositions.push(jobPositionGroup);
  }

  onCreateNewJobPosition() {
    const jobPositionGroup = this.fb.group({
      id: [null],
      name: ['', [Validators.required, this.nameValidator()]],
    });
    this.jobPositions.push(jobPositionGroup);
    this.selectedJobPositionIndex = this.jobPositions.length - 1;
    this.editMode = true;
    jobPositionGroup.get('name')?.enable();
  }

  onUpdateJobPosition() {
    if (this.selectedJobPositionIndex !== null) {
      this.editMode = true;
      this.jobPositions.at(this.selectedJobPositionIndex).get('name')?.enable();
    } else {
      console.error('No gender selected for update');
    }
  }

  deleteJobPosition() {
    if (this.selectedJobPositionIndex !== null) {

      const selectedJobPositionControl = this.jobPositions.at(this.selectedJobPositionIndex);
      const jobPositionId = selectedJobPositionControl.get('id')?.value;

      if(this.users.some(user => user.job_position.id === jobPositionId)){
        alert("You can't delete a Job Position that is being used!");
        return;
      }

      if (jobPositionId) {
        this.jobPositions.removeAt(this.selectedJobPositionIndex);
        this.selectedJobPositionIndex = this.jobPositions.length > 0 ? 0 : null;
      } else {
        this.jobPositions.removeAt(this.selectedJobPositionIndex);
        this.selectedJobPositionIndex = this.jobPositions.length > 0 ? 0 : null;
      }
    } else {
      console.error('No Job Position selected for deletion');
    }
  }

  saveJobPositions() {

    if (this.jobPositionForm.invalid) {

      this.jobPositions.controls.forEach(jobPositionGroup => {
        jobPositionGroup.markAllAsTouched();
      });

      alert('Please fix the errors before saving the form.');
      return;
    }

    const jobPositionsChanges = this.jobPositions.controls.map((jobPositionGroup) => jobPositionGroup.getRawValue());

    this.jobPositionService.saveJobPositionsChanges(jobPositionsChanges).subscribe({
      next: () => {
        console.log('Job Positions saved successfully');
        this.editMode = false;
        this.jobPositions.controls.forEach(jobPositionGroup => jobPositionGroup.get('name')?.disable());
        this.changesSaved.emit();
      },
      error: error => console.error('Error saving Job Position:', error)
    });
  }

  selectJobPosition(index: number) {
    this.selectedJobPositionIndex = index;
    this.editMode = false;
    this.jobPositions.controls.forEach(jobPositionGroup => jobPositionGroup.get('name')?.disable());
  }

  private nameValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (!control || !control.parent) {
        return null;
      }
      const currentName = control.value;
      const jobPositions = this.jobPositions.controls;
      const duplicate = jobPositions.some((jobPositionControl) =>
        jobPositionControl !== control.parent && jobPositionControl.get('name')?.value === currentName
      );
      return duplicate ? { duplicateName: true } : null;
    };
  }

  onCancel() {
    this.close.emit();
  }

}
