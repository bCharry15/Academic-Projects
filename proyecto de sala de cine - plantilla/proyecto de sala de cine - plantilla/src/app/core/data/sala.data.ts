import { Silla, TipoSilla } from '../models';

export const LETRAS_FILAS = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'] as const;
export const SILLAS_POR_FILA = 10;
/** Número de silla tras el cual hay un pasillo. */
export const PASILLO_DESPUES_DE = 5;
export const FILAS_PREFERENCIALES: readonly string[] = ['G', 'H'];
export const MAX_SILLAS_POR_COMPRA = 6;

export const PRECIOS: Readonly<Record<TipoSilla, number>> = {
  general: 14000,
  preferencial: 20000,
};

export function crearDistribucionSala(): Silla[] {
  return LETRAS_FILAS.flatMap((fila) => {
    const tipo: TipoSilla = FILAS_PREFERENCIALES.includes(fila) ? 'preferencial' : 'general';
    return Array.from({ length: SILLAS_POR_FILA }, (_, i) => ({
      id: `${fila}${i + 1}`,
      fila,
      numero: i + 1,
      tipo,
      precio: PRECIOS[tipo],
    }));
  });
}
