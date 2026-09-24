import { Component } from '@angular/core';

import { Header } from './header/header';
import { Navbar } from './navbar/navbar';
import { Carrusel } from './carrusel/carrusel';
import { Medicos } from './medicos/medicos';
import { Registro } from './registro/registro';
import { Footer } from './footer/footer';

@Component({
  selector: 'app-root',
  imports: [
    Header,
    Navbar,
    Carrusel,
    Medicos,
    Registro,
    Footer
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

}