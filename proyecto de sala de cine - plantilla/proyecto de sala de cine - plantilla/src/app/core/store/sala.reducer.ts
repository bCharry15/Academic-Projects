import { OCUPADAS_INICIALES, PELICULAS } from '../data/peliculas.data';
import {
  MAX_SILLAS_POR_COMPRA,
  crearDistribucionSala,
} from '../data/sala.data';
import { PeliculaId, SalaState } from '../models';
import { SalaAccion } from './sala.actions';

export const estadoInicial: SalaState = {
  peliculaActivaId: PELICULAS[0].id,
  sillas: crearDistribucionSala(),
  ocupadasPorPelicula: OCUPADAS_INICIALES,
  seleccionadas: [],
  maxPorCompra: MAX_SILLAS_POR_COMPRA,
  estadoCompra: 'inactiva',
  notificacion: null,
};

export function salaReducer(
  estado: SalaState,
  accion: SalaAccion,
): SalaState {
  const procesando = estado.estadoCompra === 'procesando';

  switch (accion.type) {
    case 'seleccionarPelicula': {
      if (
        procesando ||
        accion.peliculaId === estado.peliculaActivaId
      ) {
        return estado;
      }

      return {
        ...estado,
        peliculaActivaId: accion.peliculaId,
        seleccionadas: [],
        estadoCompra: 'inactiva',
        notificacion: null,
      };
    }

    case 'alternarSilla': {
      if (procesando) {
        return aviso(
          estado,
          'info',
          'Espera a que termine de procesarse la compra.',
        );
      }

      if (!estado.sillas.some((s) => s.id === accion.sillaId)) {
        return estado;
      }

      // No permitir seleccionar sillas ocupadas.
      if (
        estado.ocupadasPorPelicula[
          estado.peliculaActivaId
        ].includes(accion.sillaId)
      ) {
        return aviso(
          estado,
          'error',
          `La silla ${accion.sillaId} ya está ocupada.`,
        );
      }

      // Si ya estaba seleccionada, quitarla.
      if (estado.seleccionadas.includes(accion.sillaId)) {
        return {
          ...estado,
          seleccionadas: estado.seleccionadas.filter(
            (id) => id !== accion.sillaId,
          ),
          estadoCompra: 'inactiva',
          notificacion: null,
        };
      }

      // No superar el máximo de sillas por compra.
      if (estado.seleccionadas.length >= estado.maxPorCompra) {
        return aviso(
          estado,
          'error',
          `Solo puedes comprar ${estado.maxPorCompra} sillas por transacción.`,
        );
      }

      // Agregar la silla: ya verificamos que no esté repetida.
      return {
        ...estado,
        seleccionadas: [
          ...estado.seleccionadas,
          accion.sillaId,
        ],
        estadoCompra: 'inactiva',
        notificacion: null,
      };
    }

    case 'confirmarCompra': {
      if (procesando) return estado;

      if (estado.seleccionadas.length === 0) {
        return aviso(
          estado,
          'error',
          'Selecciona al menos una silla antes de comprar.',
        );
      }

      return {
        ...estado,
        estadoCompra: 'procesando',
        notificacion: {
          tipo: 'info',
          texto: 'Procesando la compra con el servidor…',
        },
      };
    }

    case 'compraConfirmada': {
      if (!procesando) return estado;

      return {
        ...estado,
        ocupadasPorPelicula: agregarOcupadas(
          estado,
          accion.peliculaId,
          accion.sillas,
        ),
        seleccionadas: [],
        estadoCompra: 'confirmada',
        notificacion: {
          tipo: 'exito',
          texto: `¡Compra confirmada! Sillas ${accion.sillas.join(', ')} · Código ${accion.codigo}`,
        },
      };
    }

    case 'compraRechazada': {
      if (!procesando) return estado;

      const tomadas = new Set(accion.sillasTomadas);

      return {
        ...estado,
        ocupadasPorPelicula: agregarOcupadas(
          estado,
          accion.peliculaId,
          accion.sillasTomadas,
        ),
        seleccionadas: estado.seleccionadas.filter(
          (id) => !tomadas.has(id),
        ),
        estadoCompra: 'rechazada',
        notificacion: {
          tipo: 'error',
          texto: accion.motivo,
        },
      };
    }

    case 'descartarNotificacion':
      return estado.notificacion
        ? { ...estado, notificacion: null }
        : estado;

    default:
      return estado;
  }
}

function aviso(
  estado: SalaState,
  tipo: 'info' | 'error',
  texto: string,
): SalaState {
  return {
    ...estado,
    notificacion: { tipo, texto },
  };
}

function agregarOcupadas(
  estado: SalaState,
  peliculaId: PeliculaId,
  sillas: readonly string[],
): SalaState['ocupadasPorPelicula'] {
  const actuales = estado.ocupadasPorPelicula[peliculaId];

  return {
    ...estado.ocupadasPorPelicula,
    [peliculaId]: [...new Set([...actuales, ...sillas])],
  };
}