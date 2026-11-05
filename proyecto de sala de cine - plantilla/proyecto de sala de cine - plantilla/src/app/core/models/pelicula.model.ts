export type PeliculaId = 'titanic' | 'terminator' | 'vengadores';

export interface Pelicula {
  readonly id: PeliculaId;
  readonly titulo: string;
  readonly anio: number;
  readonly duracionMin: number;
  readonly clasificacion: string;
  readonly genero: string;
  readonly horario: string;
  readonly sala: string;
  /** Ruta de la imagen del póster (carpeta public/). */
  readonly imagen: string;
  /** Color de acento usado en la tarjeta de la película. */
  readonly tono: string;
}
