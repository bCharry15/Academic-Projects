import { Injectable } from '@angular/core';
import { OCUPADAS_INICIALES } from '../data/peliculas.data';
import { PeliculaId } from '../models';

export interface SolicitudCompra {
  readonly peliculaId: PeliculaId;
  readonly sillas: readonly string[];
}

export type RespuestaCompra =
  | { readonly ok: true; readonly codigo: string; readonly sillas: readonly string[] }
  | { readonly ok: false; readonly sillasTomadas: readonly string[]; readonly motivo: string };

const LATENCIA_MS = 1200;

/**
 * Simula el servidor de reservas (no hay backend todavía).
 * Mantiene su propia copia de las sillas ocupadas, que puede diferir del
 * cliente cuando "otro usuario" reserva primero.
 */
@Injectable({ providedIn: 'root' })
export class CompraApiService {
  private readonly ocupadasServidor = new Map<PeliculaId, Set<string>>(
    (Object.keys(OCUPADAS_INICIALES) as PeliculaId[]).map((id) => [
      id,
      new Set(OCUPADAS_INICIALES[id]),
    ]),
  );

  async confirmar(solicitud: SolicitudCompra): Promise<RespuestaCompra> {
    await esperar(LATENCIA_MS);
    const ocupadas = this.ocupadasServidor.get(solicitud.peliculaId)!;

    const sillasTomadas = solicitud.sillas.filter((id) => ocupadas.has(id));
    if (sillasTomadas.length > 0) {
      return {
        ok: false,
        sillasTomadas,
        motivo: `Otro usuario reservó primero: ${sillasTomadas.join(', ')}. Elige otra silla.`,
      };
    }

    solicitud.sillas.forEach((id) => ocupadas.add(id));
    return { ok: true, codigo: generarCodigo(), sillas: [...solicitud.sillas] };
  }
}

function esperar(ms: number): Promise<void> {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

function generarCodigo(): string {
  return 'CINE-' + Math.random().toString(36).slice(2, 8).toUpperCase();
}
