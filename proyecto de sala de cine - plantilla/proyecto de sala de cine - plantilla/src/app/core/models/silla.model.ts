export type TipoSilla = 'general' | 'preferencial';

export type EstadoSilla = 'libre' | 'seleccionada' | 'ocupada';

/** Información fija de una silla (no cambia con la interacción). */
export interface Silla {
  readonly id: string;
  readonly fila: string;
  readonly numero: number;
  readonly tipo: TipoSilla;
  readonly precio: number;
}

/** Silla + estado derivado, lista para pintarse. */
export interface SillaVista {
  readonly silla: Silla;
  readonly estado: EstadoSilla;
}

export interface FilaVista {
  readonly letra: string;
  readonly sillas: readonly SillaVista[];
}
