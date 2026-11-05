import {
  ChangeDetectionStrategy,
  Component,
  input,
  output,
} from '@angular/core';
import { PASILLO_DESPUES_DE } from '../../../../core/data/sala.data';
import { FilaVista } from '../../../../core/models';
import { SillaComponent } from '../silla/silla';

@Component({
  selector: 'app-fila-sala',
  imports: [SillaComponent],
  templateUrl: './fila-sala.html',
  styleUrl: './fila-sala.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FilaSalaComponent {
  readonly fila = input.required<FilaVista>();

  readonly sillaAlternada = output<string>();

  protected readonly pasilloDespuesDe = PASILLO_DESPUES_DE;
}