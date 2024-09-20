import {Gender} from "./gender";
import {JobPosition} from "./jobPosition";

export interface User {

  id: string;

  username: string;
  password: string;

  birthdate: string;
  creation_datetime: string;

  first_name: string;
  first_surname: string;
  second_surname: string;

  gender: Gender;
  job_position: JobPosition | any;

  time_of_breakfast: string;


}

