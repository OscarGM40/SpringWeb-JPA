import { Status } from "../enum/status.enum";

export interface ServerModel {
  id: number;
  ipAddress:string;
  name:string;
  memory:string;
  type:string;
  imageUrl:string;
  status: Status
}