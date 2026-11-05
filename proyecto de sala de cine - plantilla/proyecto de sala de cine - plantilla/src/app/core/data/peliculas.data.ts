import { Pelicula, PeliculaId } from '../models';

export const PELICULAS: readonly Pelicula[] = [
  {
    id: 'titanic',
    titulo: 'Titanic',
    anio: 1997,
    duracionMin: 195,
    clasificacion: '+12',
    genero: 'Drama · Romance',
    horario: '7:00 p. m.',
    sala: 'Sala 1',
    imagen: 'peliculas/titanic.svg',
    tono: '#38bdf8',
  },
  {
    id: 'terminator',
    titulo: 'Terminator',
    anio: 1984,
    duracionMin: 107,
    clasificacion: '+15',
    genero: 'Ciencia ficción · Acción',
    horario: '8:30 p. m.',
    sala: 'Sala 2',
    imagen: 'peliculas/terminator.svg',
    tono: '#f43f5e',
  },
  {
    id: 'vengadores',
    titulo: 'Vengadores',
    anio: 2012,
    duracionMin: 143,
    clasificacion: '+7',
    genero: 'Acción · Superhéroes',
    horario: '9:15 p. m.',
    sala: 'Sala 3',
    imagen: 'peliculas/vengadores.svg',
    tono: '#a78bfa',
  },
];

/** Sillas ya vendidas al iniciar la simulación. */
export const OCUPADAS_INICIALES: Readonly<Record<PeliculaId, readonly string[]>> = {
  titanic: ['A3', 'A4', 'C5', 'C6', 'D1', 'E7', 'E8', 'G4', 'G5', 'H2'],
  terminator: ['B2', 'B3', 'D5', 'D6', 'D7', 'F1', 'F10', 'H5', 'H6'],
  vengadores: [
    'A5', 'A6', 'B4', 'B5', 'B6', 'B7', 'C1', 'C2', 'D4', 'D5',
    'E5', 'E6', 'F3', 'F8', 'G1', 'G2', 'G6', 'G7', 'H4', 'H9', 'H10',
  ],
};
