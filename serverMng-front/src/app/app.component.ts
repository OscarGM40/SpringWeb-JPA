import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import {
  BehaviorSubject,
  catchError,
  map,
  Observable,
  of,
  startWith,
} from 'rxjs';
import { ServerDTO } from './dtos/server-dto';
import { DataState } from './enum/data-state.enum';
import { Status } from './enum/status.enum';
import { AppState } from './interfaces/app-state';
import { CustomResponse } from './interfaces/custom-response';
import { NotificationService } from './services/notification.service';
import { ServerService } from './services/server.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppComponent implements OnInit {
  // con una strategia onPush,Angular solo mirará por la asignación de name una vez,cualquier reasignación no la detectará,la tengo que disparar yo con EventEmitters,Observables o @Input.Interesante
  name = 'Junior';
  // creo una propiedad de tipo observable<AppState<T>>
  appState$!: Observable<AppState<CustomResponse>>;
  // estas variables nunca cambian,realmente la ChangeDetectionStrategy.onPush no interfiere negativamente
  readonly DataState = DataState;
  readonly Status = Status;
  /* fijate que crea un BehaviorSubject y también su contrapartida como Observable.¿Porqué lo hizo? */
  private filterSubject = new BehaviorSubject<string>('');
  // también vamos a guardar la Response manualmente
  private dataSubject = new BehaviorSubject<CustomResponse | null>(null);
  filterStatus$ = this.filterSubject.asObservable();
  // SUbject and Observable para el spinner
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();

  constructor(
    private serverService: ServerService,
    private notifier: NotificationService
  ) {}

  ngOnInit() {
    // llamo al servicio para obtener el estado de la aplicación
    this.appState$ = this.serverService.servers$.pipe(
      map<CustomResponse, AppState<CustomResponse>>((response) => {
        this.dataSubject.next(response);
        this.notifier.onSuccess(response.message);
        return {
          dataState: DataState.LOADED_STATE,
          appData: {
            ...response,
            data: {
              servers: response.data.servers?.reverse(),
            },
          },
        };
      }),
      startWith<AppState<CustomResponse>>({
        dataState: DataState.LOADING_STATE,
      }),
      catchError((error) => {
        this.notifier.onError(error);
        return of({
          dataState: DataState.ERROR_STATE,
          error: error, // puede resumirse pues son iguales
        });
      })
    );
  }

  pingServer(ipAddress: string) {
    this.filterSubject.next(ipAddress);
    this.appState$ = this.serverService.$ping$(ipAddress).pipe(
      map<CustomResponse, AppState<CustomResponse>>((response) => {
        this.notifier.onWarning(response.message);
        this.dataSubject.value!.data.servers![
          this.dataSubject.value!.data.servers!.findIndex(
            (server) => server.id === response.data.server!.id
          )
        ] = response.data.server!;
        this.filterSubject.next('');
        return {
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value!,
        };
      }),
      startWith<AppState<CustomResponse>>({
        dataState: DataState.LOADED_STATE,
        appData: this.dataSubject.value!,
      }),
      catchError((error) => {
        this.filterSubject.next('');
        this.notifier.onError(error);
        return of({
          dataState: DataState.ERROR_STATE,
          error: error, // puede resumirse pues son iguales
        });
      })
    );
  }

  filterServers(status: Status) {
    this.appState$ = this.serverService
      .filter$(status, this.dataSubject.value!)
      .pipe(
        map<CustomResponse, AppState<CustomResponse>>((response) => {
          this.notifier.onInfo(response.message);
          return {
            dataState: DataState.LOADED_STATE,
            appData: {
              ...response,
              data: {
                servers: [
                  ...this.dataSubject.value!.data.servers!.filter((s) => {
                    if (status === 'ALL') return true;
                    else return s.status === status;
                  }),
                ],
              },
            },
          };
        }),
        startWith<AppState<CustomResponse>>({
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value!,
        }),
        catchError((error) => {
          this.filterSubject.next('');
          this.notifier.onError(error);
          return of({
            dataState: DataState.ERROR_STATE,
            error: error, // puede resumirse pues son iguales
          });
        })
      );
  }

  saveServer(serverForm: NgForm) {
    this.isLoading.next(true);
    this.appState$ = this.serverService.save$(serverForm.value).pipe(
      map<CustomResponse, AppState<CustomResponse>>((response) => {
        this.notifier.onSuccess(response.message);
        this.dataSubject.next({
          ...response,
          data: {
            servers: [
              response.data.server!,
              ...this.dataSubject.value!.data.servers!,
            ],
          },
        });
        document.getElementById('closeModal')!.click();
        this.isLoading.next(false);
        serverForm.resetForm({ status: this.Status.SERVER_DOWN });
        return {
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value!,
        };
      }),
      startWith<AppState<CustomResponse>>({
        dataState: DataState.LOADED_STATE,
        appData: this.dataSubject.value!,
      }),
      catchError((error) => {
        this.isLoading.next(false);
        this.notifier.onError(error);
        return of({
          dataState: DataState.ERROR_STATE,
          error: error, // puede resumirse pues son iguales
        });
      })
    );
  }

  deleteServer(server: ServerDTO) {
    this.appState$ = this.serverService.delete$(server.id.toString()).pipe(
      map<CustomResponse, AppState<CustomResponse>>((response) => {
        this.notifier.onWarning(response.message);
        this.dataSubject.next({
          ...response,
          data: {
            servers: this.dataSubject.value!.data.servers!.filter(
              (s) => s.id !== server.id
            ),
          },
        });
        return {
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value!,
        };
      }),
      startWith<AppState<CustomResponse>>({
        dataState: DataState.LOADED_STATE,
        appData: this.dataSubject.value!,
      }),
      catchError((error) => {
        this.filterSubject.next('');
        this.notifier.onError(error);
        return of({
          dataState: DataState.ERROR_STATE,
          error: error, // puede resumirse pues son iguales
        });
      })
    );
  }

  printReport() {
    this.notifier.onSuccess('Report generated successfully!');
    /* para generar un Excel necesito el dataType,despues buscar en el DOM la tabla */
    let dataType = 'application/vnd.ms-excel.sheet.macroEnabled.12';
    /* hay que reemplazar los espacios con la  HTMLEntity */
    let tableSelect = document.getElementById('servers');
    let tableHtml = tableSelect?.outerHTML.replace(/ /g, '%20');
    let downloadLink = document.createElement('a');
    document.body.appendChild(downloadLink);
    downloadLink.href = 'data:' + dataType + ', ' + tableHtml;
    downloadLink.download = 'server-report.xls';
    // tras hacer el click borro el <anchor>
    downloadLink.click();
    document.body.removeChild(downloadLink);
  }
  
  printPDF() {
    this.notifier.onInfo('Accediendo al menú de impresión...');
    /* si quisiera guardarlo como PDF desde el menu de impresión lo puedo guardar como PDF.Fijate que saber esto es importante. */
    window.print();
  }
}
