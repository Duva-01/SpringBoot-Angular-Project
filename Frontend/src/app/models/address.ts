import {User} from "./user";

export interface Address {
  id: string;
  street_name: string;
  street_number: string;
  main_address: boolean;
  user: User;
}
