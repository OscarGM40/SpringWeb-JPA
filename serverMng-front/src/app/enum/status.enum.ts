export enum Status {
  ALL = 'ALL',
  SERVER_UP = 'SERVER_UP',
  SERVER_DOWN = 'SERVER_DOWN',
}

interface A {
  nombre:string;
  edad:number;
}

interface B {
  apellido:string;
  direccion:string;
}

// fijate que necesito usar type para uniones o intersecciones,no puedo con interfaces,asinto
type C =  A & B;

const moko: C = {
  apellido: 'moko',
  nombre: 'moko',
  edad: 45,
  direccion: 'moko'
}
