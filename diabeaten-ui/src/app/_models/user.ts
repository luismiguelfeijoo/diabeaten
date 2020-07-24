export class User {
  id: number;
  username: string;
  password: string;
  roles: any[];
  authdata?: string;
  monitors?: User[];
  name?: string;
}
