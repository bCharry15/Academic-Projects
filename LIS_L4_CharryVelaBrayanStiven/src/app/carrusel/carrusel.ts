import { Component } from '@angular/core';

@Component({
  selector: 'app-carrusel',
  imports: [],
  templateUrl: './carrusel.html',
  styleUrl: './carrusel.css'
})
export class Carrusel {

  public tituloPrincipal: string = 'Tu salud es nuestra prioridad';

  public descripcionPrincipal: string =
    'Encuentra profesionales de la salud y atención especializada.';

  public tituloEspecialistas: string = 'Médicos especialistas';

  public descripcionEspecialistas: string =
    'Contamos con profesionales preparados para brindarte una atención de calidad.';

  public tituloCitas: string = 'Atención para ti';

  public descripcionCitas: string =
    'Consulta nuestras especialidades y encuentra el profesional que necesitas.';
}