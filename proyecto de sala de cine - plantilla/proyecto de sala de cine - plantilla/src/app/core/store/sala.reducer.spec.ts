import { SalaState } from '../models';
import { SalaAcciones as A } from './sala.actions';
import { estadoInicial, salaReducer } from './sala.reducer';

const aplicar = (estado: SalaState, ...acciones: Parameters<typeof salaReducer>[1][]) =>
  acciones.reduce(salaReducer, estado);

describe('salaReducer', () => {
  it('selecciona y libera una silla al alternarla', () => {
    const s1 = salaReducer(estadoInicial, A.alternarSilla('A1'));
    expect(s1.seleccionadas).toEqual(['A1']);
    const s2 = salaReducer(s1, A.alternarSilla('A1'));
    expect(s2.seleccionadas).toEqual([]);
  });

  it('no permite seleccionar sillas ocupadas', () => {
    // A3 está ocupada en Titanic
    const s = salaReducer(estadoInicial, A.alternarSilla('A3'));
    expect(s.seleccionadas).toEqual([]);
    expect(s.notificacion?.tipo).toBe('error');
  });

  it('no repite sillas', () => {
    const s = aplicar(estadoInicial, A.alternarSilla('A1'), A.alternarSilla('A2'));
    expect(new Set(s.seleccionadas).size).toBe(s.seleccionadas.length);
  });

  it('no supera el máximo por compra', () => {
    const ids = ['A1', 'A2', 'A5', 'A6', 'A7', 'A8', 'A9'];
    const s = aplicar(estadoInicial, ...ids.map((id) => A.alternarSilla(id)));
    expect(s.seleccionadas.length).toBe(estadoInicial.maxPorCompra);
    expect(s.notificacion?.tipo).toBe('error');
  });

  it('no confirma una compra vacía', () => {
    const s = salaReducer(estadoInicial, A.confirmarCompra());
    expect(s.estadoCompra).toBe('inactiva');
  });

  it('compraConfirmada mueve la selección a ocupadas', () => {
    const s = aplicar(
      estadoInicial,
      A.alternarSilla('A1'),
      A.confirmarCompra(),
      A.compraConfirmada('titanic', ['A1'], 'CINE-TEST'),
    );
    expect(s.seleccionadas).toEqual([]);
    expect(s.ocupadasPorPelicula.titanic).toContain('A1');
    expect(s.estadoCompra).toBe('confirmada');
  });

  it('compraRechazada marca como ocupada la silla tomada por otro usuario', () => {
    const s = aplicar(
      estadoInicial,
      A.alternarSilla('A1'),
      A.alternarSilla('A2'),
      A.confirmarCompra(),
      A.compraRechazada('titanic', ['A2'], 'Otro usuario reservó primero'),
    );
    expect(s.seleccionadas).toEqual(['A1']);
    expect(s.ocupadasPorPelicula.titanic).toContain('A2');
    expect(s.estadoCompra).toBe('rechazada');
  });

  it('bloquea cambios mientras se procesa la compra', () => {
    const procesando = aplicar(estadoInicial, A.alternarSilla('A1'), A.confirmarCompra());
    const s = aplicar(procesando, A.alternarSilla('A2'), A.seleccionarPelicula('terminator'));
    expect(s.seleccionadas).toEqual(['A1']);
    expect(s.peliculaActivaId).toBe('titanic');
  });

  it('cambiar de película limpia la selección', () => {
    const s = aplicar(estadoInicial, A.alternarSilla('A1'), A.seleccionarPelicula('vengadores'));
    expect(s.peliculaActivaId).toBe('vengadores');
    expect(s.seleccionadas).toEqual([]);
  });
});
