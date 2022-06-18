import { Component, OnInit } from '@angular/core';
import { catchError, map, Observable, of, startWith } from 'rxjs';
import { DataState } from './enum/data-state.enum';
import { AppState } from './interfaces/app-state';
import { CustomResponse } from './interfaces/custom-response';
import { ServerService } from './services/server.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  // creo una propiedad de tipo observable<AppState<T>>
  appState$!: Observable<AppState<CustomResponse>>;
  
  constructor(private serverService: ServerService) {}

  ngOnInit() {
    // llamo al servicio para obtener el estado de la aplicación
    this.appState$ = this.serverService.servers$
    .pipe(
      map<CustomResponse,AppState<CustomResponse>>(response => ({
          dataState: DataState.LOADED_STATE,
          appData: response,
        })
      ),
      startWith<AppState<CustomResponse>>({ 
        dataState: DataState.LOADING_STATE,
      }),
      catchError(error => {
        return of({
          dataState: DataState.ERROR_STATE,
          error: error // puede resumirse pues son iguales
        });
      })
    );
  }
  
}
