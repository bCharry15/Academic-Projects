import { Injectable, inject } from '@angular/core';
import { SalaState } from '../models';
import { CompraApiService } from '../services/compra-api.service';
import { SalaAccion, SalaAcciones } from './sala.actions';

export type Despachar = (accion: SalaAccion) => void;

/**
 * Efectos: aíslan los procesos asíncronos (llamadas al "servidor").
 * Reaccionan a acciones ya reducidas y, al terminar, despachan nuevas acciones.
 */
@Injectable({ providedIn: 'root' })
export class SalaEfectos {
  private readonly api = inject(CompraApiService);

  ejecutar(accion: SalaAccion, estado: SalaState, despachar: Despachar): void {
    if (accion.type !== 'confirmarCompra' || estado.estadoCompra !== 'procesando') return;

    const peliculaId = estado.peliculaActivaId;
    this.api
      .confirmar({ peliculaId, sillas: estado.seleccionadas })
      .then((r) =>
        despachar(
          r.ok
            ? SalaAcciones.compraConfirmada(peliculaId, r.sillas, r.codigo)
            : SalaAcciones.compraRechazada(peliculaId, r.sillasTomadas, r.motivo),
        ),
      )
      .catch(() =>
        despachar(
          SalaAcciones.compraRechazada(peliculaId, [], 'No fue posible contactar al servidor.'),
        ),
      );
  }
}
