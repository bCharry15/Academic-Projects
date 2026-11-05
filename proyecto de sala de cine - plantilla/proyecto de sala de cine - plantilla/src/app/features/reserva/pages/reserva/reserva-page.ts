import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { PeliculaId } from '../../../../core/models';
import { SalaAcciones, SalaStore } from '../../../../core/store';
import { AvisoComponent } from '../../../../shared/components/aviso/aviso';
import { CarteleraComponent } from '../../components/cartelera/cartelera';
import { LeyendaComponent } from '../../components/leyenda/leyenda';
import { MapaSalaComponent } from '../../components/mapa-sala/mapa-sala';
import { ResumenCompraComponent } from '../../components/resumen-compra/resumen-compra';

/**
 * Componente contenedor ("inteligente"): es el único de la feature que
 * conoce el store. Lee selectores y traduce eventos de la UI en acciones.
 */
@Component({
  selector: 'app-reserva-page',
  imports: [
    AvisoComponent,
    CarteleraComponent,
    LeyendaComponent,
    MapaSalaComponent,
    ResumenCompraComponent,
  ],
  templateUrl: './reserva-page.html',
  styleUrl: './reserva-page.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReservaPage {
  protected readonly store = inject(SalaStore);

  protected seleccionarPelicula(id: PeliculaId): void {
    this.store.dispatch(SalaAcciones.seleccionarPelicula(id));
  }

  protected alternarSilla(sillaId: string): void {
    this.store.dispatch(SalaAcciones.alternarSilla(sillaId));
  }

  protected confirmarCompra(): void {
    this.store.dispatch(SalaAcciones.confirmarCompra());
  }

  protected descartarNotificacion(): void {
    this.store.dispatch(SalaAcciones.descartarNotificacion());
  }
}
