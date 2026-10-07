import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ClienteService } from '../servicios/cliente';
import Swal from 'sweetalert2';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { Cliente } from '../modelos/cliente';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, RouterLink, SweetAlert2Module],
  templateUrl: './clientes.html',
  styleUrl: './clientes.css'
})
export class ClientesComponent {
  public clientes = signal<Cliente[]>([]);

  constructor(
    private objClienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.objClienteService.getClientes().subscribe(
      (clientes) => {
        this.clientes.set(clientes);
      }
    );
  }
}