import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registro',
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './registro.html',
  styleUrl: './registro.css'
})
export class Registro {

  public nombre: string = '';
  public correo: string = '';
  public telefono: string = '';
  public especialidad: string = '';

  public errorNombre: string = '';
  public errorCorreo: string = '';
  public errorTelefono: string = '';
  public errorEspecialidad: string = '';

  public registroExitoso: boolean = false;


  public validarNombre(): boolean {

    this.errorNombre = '';

    if (this.nombre.trim() === '') {

      this.errorNombre = 'El nombre es obligatorio.';
      return false;

    }

    if (this.nombre.trim().length < 3) {

      this.errorNombre =
        'El nombre debe contener al menos 3 caracteres.';

      return false;

    }

    return true;
  }


  public validarCorreo(): boolean {

    this.errorCorreo = '';

    const expresionCorreo =
      /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (this.correo.trim() === '') {

      this.errorCorreo =
        'El correo electrónico es obligatorio.';

      return false;

    }

    if (!expresionCorreo.test(this.correo)) {

      this.errorCorreo =
        'Ingrese un correo electrónico válido.';

      return false;

    }

    return true;
  }


  public validarTelefono(): boolean {

    this.errorTelefono = '';

    const expresionTelefono =
      /^[0-9]{7,10}$/;

    if (this.telefono.trim() === '') {

      this.errorTelefono =
        'El teléfono es obligatorio.';

      return false;

    }

    if (!expresionTelefono.test(this.telefono)) {

      this.errorTelefono =
        'El teléfono debe contener entre 7 y 10 números.';

      return false;

    }

    return true;
  }


  public validarEspecialidad(): boolean {

    this.errorEspecialidad = '';

    if (this.especialidad === '') {

      this.errorEspecialidad =
        'Debe seleccionar una especialidad.';

      return false;

    }

    return true;
  }


  public registrar(): void {

    this.registroExitoso = false;

    const nombreValido =
      this.validarNombre();

    const correoValido =
      this.validarCorreo();

    const telefonoValido =
      this.validarTelefono();

    const especialidadValida =
      this.validarEspecialidad();


    if (
      nombreValido &&
      correoValido &&
      telefonoValido &&
      especialidadValida
    ) {

      this.registroExitoso = true;

    }

  }

}