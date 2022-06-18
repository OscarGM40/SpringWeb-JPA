import { ServerDTO } from "../dtos/server-dto";

export interface CustomResponse {
  timeStamp: Date;
  statusCode: number;
  status: string;
  reason: string;
  message: string;
  developerMessage: string;
  data: { servers?: ServerDTO[],server?: ServerDTO}
}