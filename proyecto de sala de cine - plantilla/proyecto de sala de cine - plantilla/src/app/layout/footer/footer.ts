import { ChangeDetectionStrategy, Component } from '@angular/core';

/** Datos ficticios del cine, solo para la simulación. */
const CINE = {
  nombre: 'CineSala Estrella del Valle',
  eslogan: 'Tu cine de barrio con experiencia de estreno.',
  direccion: 'Calle 15 # 7-42, Centro Comercial Los Andes, Local 210',
  ciudad: 'Popayán, Cauca · Colombia',
  telefono: '+57 602 555 0147',
  correo: 'contacto@cinesala-ficticio.co',
  horario: 'Lunes a domingo · 12:00 m. – 11:30 p. m.',
  redes: [
    { nombre: 'Facebook', usuario: '/CineSalaEstrella', icono: 'f' },
    { nombre: 'Instagram', usuario: '@cinesala.estrella', icono: '◎' },
    { nombre: 'X', usuario: '@CineSalaEV', icono: '𝕏' },
    { nombre: 'TikTok', usuario: '@cinesala.ev', icono: '♪' },
    { nombre: 'YouTube', usuario: 'CineSala TV', icono: '▶' },
  ],
} as const;

@Component({
  selector: 'app-footer',
  templateUrl: './footer.html',
  styleUrl: './footer.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FooterComponent {
  protected readonly cine = CINE;
  protected readonly anio = new Date().getFullYear();
}
