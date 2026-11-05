import { PeliculaId } from './pelicula.model';
import { Silla } from './silla.model';

export type EstadoCompra = 'inactiva' | 'procesando' | 'confirmada' | 'rechazada';

export interface Notificacion {
  readonly tipo: 'info' | 'exito' | 'error';
  readonly texto: string;
}

/** Única fuente de verdad de la aplicación. */
export interface SalaState {
  readonly peliculaActivaId: PeliculaId;
  readonly sillas: readonly Silla[];
  readonly ocupadasPorPelicula: Readonly<Record<PeliculaId, readonly string[]>>;
  readonly seleccionadas: readonly string[];
  readonly maxPorCompra: number;
  readonly estadoCompra: EstadoCompra;
  readonly notificacion: Notificacion | null;
}
