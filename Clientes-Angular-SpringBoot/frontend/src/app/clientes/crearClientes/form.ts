import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

import {
  FormsModule,
  ReactiveFormsModule,
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
  ValidationErrors,
  ValidatorFn,
  AsyncValidatorFn
} from '@angular/forms';

import { of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import Swal from 'sweetalert2';

import { ClienteService } from '../servicios/cliente';
import { Cliente } from '../modelos/cliente';

@Component({
  selector: 'app-form',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    SweetAlert2Module,
    HttpClientModule
  ],
  templateUrl: './form.html',
  styleUrl: './form.css'
})
export class FormComponent {
  public formulario!: FormGroup;
  public titulo: string = 'Crear cliente';

  // Se conserva mientras actualizamos el HTML anterior.
  public cliente: Cliente = new Cliente();

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.formulario = new FormGroup({
      codigo: new FormControl(
        '',
        [
          Validators.required,
          validarFormatocodigo()
        ],
        [
          codigoDuplicadoValidator(this.clienteService)
        ]
      ),
      nombre: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(20)
      ]),
      apellido: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(20)
      ]),
      email: new FormControl('', [
        Validators.required,
        Validators.email,
        validarCorreoUnicauca()
      ])
    });
  }

  public crearCliente(): void {
    console.log('Creando cliente');

    const cliente = this.formulario.value;

    this.clienteService.create(cliente).subscribe({
      next: (response) => {
        console.log('Cliente creado exitosamente');

        this.router.navigate(['clientes/listarClientes']);

        Swal.fire(
          'Nuevo cliente',
          `Cliente ${response.nombre} creado con éxito!`,
          'success'
        );
      },
      error: (err) => {
        console.error('Error al crear cliente:', err.message);
      }
    });
  }
}

export function validarCorreoUnicauca(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const email = control.value;

    if (!email) {
      return null;
    }

    const dominio = '@unicauca.edu.co';

    return email.endsWith(dominio)
      ? null
      : { dominioInvalido: true };
  };
}

export function codigoDuplicadoValidator(
  clienteService: ClienteService
): AsyncValidatorFn {
  return (control: AbstractControl) => {
    if (!control.value) {
      return of(null);
    }

    return clienteService.verificarCodigo(control.value).pipe(
      map(existe => (
        existe ? { codigoDuplicado: true } : null
      )),
      catchError(() => of(null))
    );
  };
}

function validarFormatocodigo(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const valor = control.value;

    if (!valor) {
      return null;
    }

    const valido = /^\d{3}456$/.test(valor);

    return valido ? null : { codigoInvalido: true };
  };
}