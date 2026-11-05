import { PeliculaId } from '../models';

/**
 * Acciones: la única forma de expresar una intención de cambio.
 * Los componentes nunca modifican el estado directamente.
 */
export const SalaAcciones = {
  seleccionarPelicula: (peliculaId: PeliculaId) =>
    ({ type: 'seleccionarPelicula', peliculaId }) as const,

  alternarSilla: (sillaId: string) => ({ type: 'alternarSilla', sillaId }) as const,

  confirmarCompra: () => ({ type: 'confirmarCompra' }) as const,

  compraConfirmada: (peliculaId: PeliculaId, sillas: readonly string[], codigo: string) =>
    ({ type: 'compraConfirmada', peliculaId, sillas, codigo }) as const,

  compraRechazada: (peliculaId: PeliculaId, sillasTomadas: readonly string[], motivo: string) =>
    ({ type: 'compraRechazada', peliculaId, sillasTomadas, motivo }) as const,

  descartarNotificacion: () => ({ type: 'descartarNotificacion' }) as const,
};

export type SalaAccion = ReturnType<(typeof SalaAcciones)[keyof typeof SalaAcciones]>;
