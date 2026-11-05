import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { FilaVista } from '../../../../core/models';
import { FilaSalaComponent } from '../fila-sala/fila-sala';

/** Mapa de la sala: contiene filas, que a su vez contienen sillas. */
@Component({
  selector: 'app-mapa-sala',
  imports: [FilaSalaComponent],
  templateUrl: './mapa-sala.html',
  styleUrl: './mapa-sala.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MapaSalaComponent {
  readonly filas = input.required<readonly FilaVista[]>();
  readonly nombreSala = input<string>('');
  readonly bloqueado = input(false);

  readonly sillaAlternada = output<string>();
}
