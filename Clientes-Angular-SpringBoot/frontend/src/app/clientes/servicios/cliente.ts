import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders
} from '@angular/common/http';
import { Cliente } from '../modelos/cliente';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private httpHeaders = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  private urlEndPoint: string = 'http://localhost:8085/api/clientes';

  constructor(private http: HttpClient) {}

  getClientes(): Observable<Cliente[]> {
    console.log('Listando clientes desde el servicio');
    return this.http.get<Cliente[]>(this.urlEndPoint);
  }

  create(cliente: Cliente): Observable<Cliente> {
    console.log('Creando desde el servicio');
    return this.http.post<Cliente>(
      this.urlEndPoint,
      cliente,
      { headers: this.httpHeaders }
    );
  }

  verificarCodigo(codigo: string): Observable<boolean> {
  return this.http.get<boolean>(
    `${this.urlEndPoint}/codigo/${codigo}`
  );
}
}