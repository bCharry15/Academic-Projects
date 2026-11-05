import { Injectable, computed, inject, signal } from '@angular/core';
import { PELICULAS } from '../data/peliculas.data';
import { EstadoSilla, FilaVista, Silla, SillaVista } from '../models';
import { SalaAccion } from './sala.actions';
import { SalaEfectos } from './sala.effects';
import { estadoInicial, salaReducer } from './sala.reducer';

@Injectable({ providedIn: 'root' })
export class SalaStore {
  private readonly efectos = inject(SalaEfectos);

  private readonly _estado = signal(estadoInicial);

  readonly estado = this._estado.asReadonly();

  // Selectores
  readonly peliculas = PELICULAS;

  readonly peliculaActiva = computed(
    () => PELICULAS.find(
      (p) => p.id === this._estado().peliculaActivaId,
    )!,
  );

  private readonly idsOcupadas = computed(
    () => new Set(
      this._estado().ocupadasPorPelicula[
        this._estado().peliculaActivaId
      ],
    ),
  );

  private readonly idsSeleccionadas = computed(
    () => new Set(this._estado().seleccionadas),
  );

  readonly sillasSeleccionadas = computed<Silla[]>(() => {
    const porId = new Map(
      this._estado().sillas.map((s) => [s.id, s]),
    );

    return this._estado().seleccionadas.map(
      (id) => porId.get(id)!,
    );
  });

  readonly cantidadSeleccionadas = computed(
    () => this._estado().seleccionadas.length,
  );

  readonly total = computed(() =>
    this.sillasSeleccionadas().reduce(
      (suma, s) => suma + s.precio,
      0,
    ),
  );

  readonly maxPorCompra = computed(
    () => this._estado().maxPorCompra,
  );

  readonly cantidadDisponibles = computed(
    () =>
      this._estado().sillas.length -
      this.idsOcupadas().size -
      this.cantidadSeleccionadas(),
  );

  readonly filas = computed<FilaVista[]>(() => {
    const ocupadas = this.idsOcupadas();
    const seleccionadas = this.idsSeleccionadas();

    const estadoDe = (id: string): EstadoSilla =>
      ocupadas.has(id)
        ? 'ocupada'
        : seleccionadas.has(id)
          ? 'seleccionada'
          : 'libre';

    const filas = new Map<string, SillaVista[]>();

    for (const silla of this._estado().sillas) {
      const fila = filas.get(silla.fila) ?? [];

      fila.push({
        silla,
        estado: estadoDe(silla.id),
      });

      filas.set(silla.fila, fila);
    }

    return [...filas].map(([letra, sillas]) => ({
      letra,
      sillas,
    }));
  });

  readonly estadoCompra = computed(
    () => this._estado().estadoCompra,
  );

  readonly procesando = computed(
    () => this.estadoCompra() === 'procesando',
  );

  readonly notificacion = computed(
    () => this._estado().notificacion,
  );

  // Única entrada para solicitar cambios de estado
  dispatch(accion: SalaAccion): void {
    this._estado.update(
      (estado) => salaReducer(estado, accion),
    );

    this.efectos.ejecutar(
      accion,
      this._estado(),
      (a) => this.dispatch(a),
    );
  }
}