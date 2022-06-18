import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
// fijate que ya lo hicieron todo modular incluso en el core,perfecto
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { Status } from '../enum/status.enum';
import { CustomResponse } from '../interfaces/custom-response';
import { adaptServerDTO } from '../models/server.adapter';
import { ServerModel } from '../models/server.model';

@Injectable({ providedIn: 'root' })
export class ServerService {
  // readonly para propiedades,const para variables
  private readonly apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Forma procedural para asintos
  /*   getServers(): Observable<CustomResponse> {
    return this.http.get<CustomResponse>('/api/servers');
  } */

  // forma reactiva
  servers$ = <Observable<CustomResponse>>this.http.get<CustomResponse>(`${this.apiUrl}/server/list`).pipe(
    tap<CustomResponse>(console.log),
      map<CustomResponse, any>((data) => {
        if (data.data.server) adaptServerDTO(data.data.server!);
        if (data.data.servers)
          data.data.servers.forEach((server) => adaptServerDTO(server));
        return data;
      }),
      catchError(this.handleError)
    );
  // guardar reactivamente un server 
  save$ = (server: ServerModel) => <Observable<CustomResponse>>this.http.post<CustomResponse>(`${this.apiUrl}/server/save`, server).pipe(
    tap<CustomResponse>(console.log),
      map<CustomResponse, any>((data) => {
        if (data.data.server) adaptServerDTO(data.data.server!);
        if (data.data.servers)
          data.data.servers.forEach((server) => adaptServerDTO(server));
        return data;
      }),
      catchError(this.handleError)
    );
    // realizar el ping
    $ping$ = (ipAddress: string) => <Observable<CustomResponse>>this.http.get<CustomResponse>(`${this.apiUrl}/server/ping/${ipAddress}`).pipe(
      tap<CustomResponse>(console.log),
        map<CustomResponse, any>((data) => {
          if (data.data.server) adaptServerDTO(data.data.server!);  
          if (data.data.servers)
            data.data.servers.forEach((server) => adaptServerDTO(server));
          return data;
        }
        ),
        catchError(this.handleError)
      );
  // eliminar un server
  delete$ = (serverId: string) => <Observable<CustomResponse>>this.http.delete<CustomResponse>(`${this.apiUrl}/server/delete/${serverId}`).pipe(
    tap<CustomResponse>(console.log),
      map<CustomResponse, any>((data) => {
        if (data.data.server) adaptServerDTO(data.data.server!);
        if (data.data.servers)
          data.data.servers.forEach((server) => adaptServerDTO(server));
        return data;
      }),
      catchError(this.handleError)
    );
  //filtrar
  filter$ = (status: Status,response: CustomResponse) => new Observable<CustomResponse>(suscriber => {
    suscriber.next(
      status === Status.ALL 
        ? { ...response, message: `Servers filtered by ${status}`}
        : { ...response, 
          message: response.data.servers!
          .filter(server => server.status === status).length > 0 
          ? `Servers filtered by ${status === Status.SERVER_UP 
            ? 'SERVER_UP' 
            : 'SERVER_DOWN'}` 
          : `No servers found with ${status} status`,
          data: { servers: response.data.servers!
            .filter(server => server.status === status) }
        }
    );
    suscriber.complete();
  }).pipe(
    tap<CustomResponse>(console.log),
    catchError(this.handleError)
  );

  private handleError(err: HttpErrorResponse): Observable<never> {
    console.log(err);
    // fijate que si tipo el error como HttpErrorResponse tengo acceso al message y al statusCode,algo que necesitaré en un ambiente empresarial
    return throwError(`${err.status} - ${err.message}`);
  }
}
