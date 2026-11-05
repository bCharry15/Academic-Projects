import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { Pelicula, PeliculaId } from '../../../../core/models';

@Component({
  selector: 'app-cartelera',
  templateUrl: './cartelera.html',
  styleUrl: './cartelera.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CarteleraComponent {
  readonly peliculas = input.required<readonly Pelicula[]>();
  readonly activaId = input.required<PeliculaId>();
  readonly bloqueada = input(false);

  readonly seleccionar = output<PeliculaId>();
}
