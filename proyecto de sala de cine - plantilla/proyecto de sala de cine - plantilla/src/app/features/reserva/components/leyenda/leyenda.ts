import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-leyenda',
  template: `
    <ul class="leyenda">
      <li><span class="muestra muestra--libre"></span>Libre</li>
      <li><span class="muestra muestra--seleccionada"></span>Seleccionada</li>
      <li><span class="muestra muestra--ocupada"></span>Ocupada</li>
      <li><span class="muestra muestra--libre muestra--preferencial"></span>Preferencial</li>
    </ul>
  `,
  styles: `
    .leyenda {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      gap: 0.5rem 1.25rem;
      margin: 0;
      padding: 0;
      list-style: none;
      font-size: 0.8rem;
      color: var(--texto-tenue);
    }
    li {
      display: flex;
      align-items: center;
      gap: 0.45rem;
    }
    .muestra {
      width: 16px;
      height: 16px;
      border-radius: 5px 5px 3px 3px;
    }
    .muestra--libre { background: var(--silla-libre); }
    .muestra--seleccionada { background: var(--silla-seleccionada); }
    .muestra--ocupada { background: var(--silla-ocupada); }
    .muestra--preferencial {
      outline: 1.5px solid var(--acento-preferencial);
      outline-offset: 1px;
    }
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LeyendaComponent {}
