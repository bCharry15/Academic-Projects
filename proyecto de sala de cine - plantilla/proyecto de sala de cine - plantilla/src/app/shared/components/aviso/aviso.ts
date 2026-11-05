import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { Notificacion } from '../../../core/models';

const ICONOS: Record<Notificacion['tipo'], string> = { info: 'ℹ', exito: '✓', error: '!' };

@Component({
  selector: 'app-aviso',
  template: `
    <div [class]="'aviso aviso--' + notificacion().tipo" role="status" aria-live="polite">
      <span class="aviso__icono" aria-hidden="true">{{ iconos[notificacion().tipo] }}</span>
      <p class="aviso__texto">{{ notificacion().texto }}</p>
      <button type="button" class="aviso__cerrar" aria-label="Cerrar aviso" (click)="cerrar.emit()">✕</button>
    </div>
  `,
  styles: `
    .aviso {
      display: flex;
      align-items: center;
      gap: 0.75rem;
      padding: 0.7rem 0.9rem;
      border: 1px solid var(--color);
      border-radius: 12px;
      background: color-mix(in srgb, var(--color) 14%, var(--superficie));
      animation: entrar 0.2s ease;
    }
    .aviso--info { --color: #60a5fa; }
    .aviso--exito { --color: #34d399; }
    .aviso--error { --color: #f87171; }
    .aviso__icono {
      flex: none;
      display: grid;
      place-items: center;
      width: 24px;
      height: 24px;
      border-radius: 50%;
      background: var(--color);
      color: #0b0d12;
      font-weight: 800;
      font-size: 0.8rem;
    }
    .aviso__texto {
      flex: 1;
      margin: 0;
      font-size: 0.9rem;
    }
    .aviso__cerrar {
      border: none;
      background: transparent;
      color: var(--texto-tenue);
      cursor: pointer;
      font-size: 0.9rem;
    }
    @keyframes entrar {
      from { opacity: 0; transform: translateY(-4px); }
    }
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AvisoComponent {
  readonly notificacion = input.required<Notificacion>();
  readonly cerrar = output<void>();

  protected readonly iconos = ICONOS;
}
