import { ServerDTO } from '../dtos/server-dto';
import { Status } from '../enum/status.enum';
import { ServerModel } from './server.model';

export function adaptServerDTO(serverDTO: ServerDTO): ServerModel {
  return {
    id: serverDTO.id,
    ipAddress: serverDTO.ipAddress,
    name: serverDTO.name,
    memory: serverDTO.memory,
    type: serverDTO.type,
    imageUrl: serverDTO.imageUrl,
    status: serverDTO.status as Status,
  };
}
