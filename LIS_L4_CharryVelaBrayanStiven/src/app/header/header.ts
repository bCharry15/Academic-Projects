import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header {

  public nombres: string = 'Juan Perez';

  public apellidos: string = '';

  public disciplina: string =
    'Soy desarrollador Backend especialista en node.js y en Experiencia de usuario';

  public descripcion: string =
    'Estudiante de Ingeniería de Sistemas apasionado por el desarrollo Backend';
}