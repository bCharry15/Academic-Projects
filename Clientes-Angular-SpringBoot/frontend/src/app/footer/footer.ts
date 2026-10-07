import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  imports: [],
  templateUrl: './footer.html',
  styleUrl: './footer.css'
})
export class Footer {

  public proyecto: any = {
    anio: '2026',
    nombreProyecto: 'Proyecto de Clase'
  };

  public tecnologia: any = {
    leyenda: 'WebApp desarrollada con',
    tec1: 'Angular',
    tec2: 'Spring Boot'
  };

  public autor: string = 'Desarrollado por estudiante de Ingeniería de Sistemas';
}