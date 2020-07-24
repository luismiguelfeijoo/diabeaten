import { Ratio } from './ratio';
import { Sensibility } from './sensibility';
import { User } from './user';

export class Patient {
  username: string;
  password: string;
  name: string;
  ratios: Ratio[];
  sensibilities: Sensibility[];
  totalBasal: number;
  dia: number;
  id?: number;
  monitors?: User[];
}
