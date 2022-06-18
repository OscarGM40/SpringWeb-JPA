import { DataState } from "../enum/data-state.enum";

export interface AppState<T> {
  dataState: DataState;
  appData?: T;
  error?: string;
}

const myData: Record<string,{firstName:string, lastName:string}> = {
  "123-123-123": {firstName: "John", lastName: "Doe"},
  "124-124-124": {firstName: "Sarah", lastName: "Connor"},
  "125-125-125": {firstName: "Jane", lastName: "Connor"},
}
// Un Record<K,V> no es más que un tipo donde K es el tipo de las keys y V es el tipo de los values
// un union type(la palabra ya me dice que solo lo puedo usar como type,es altamente recomendado sobre una enum,que si va a compilar)
type Country = "uk" | "us" | "es";
type User = {
  firstName: string;
  lastName: string;
}
// llegando pues al Record<K,V> deseado
const anotherData: Record<Country,User> = {
  us: {firstName: "John", lastName: "Doe"},
  uk: {firstName: "Sarah", lastName: "Connor"},
  es: {firstName: "Jane", lastName: "Connor"},
}
const ACTIONS = { GET_DATA_SUCCESS: "GET_DATA_SUCCESS", GET_DATA_ERROR: "GET_DATA_ERROR", GET_DATA_LOADING: "GET_DATA_LOADING", }