import { CurrencyPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, input, output } from '@angular/core';
import { EstadoCompra, Pelicula, Silla } from '../../../../core/models';

@Component({
  selector: 'app-resumen-compra',
  imports: [CurrencyPipe],
  templateUrl: './resumen-compra.html',
  styleUrl: './resumen-compra.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ResumenCompraComponent {
  readonly pelicula = input.required<Pelicula>();
  readonly sillas = input.required<readonly Silla[]>();
  readonly total = input.required<number>();
  readonly maximo = input.required<number>();
  readonly disponibles = input.required<number>();
  readonly estadoCompra = input.required<EstadoCompra>();

  readonly confirmar = output<void>();
  readonly quitarSilla = output<string>();

  protected readonly procesando = computed(() => this.estadoCompra() === 'procesando');
  protected readonly puedeComprar = computed(
    () => this.sillas().length > 0 && !this.procesando(),
  );}
