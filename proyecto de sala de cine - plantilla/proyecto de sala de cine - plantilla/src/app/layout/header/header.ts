import { CurrencyPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { SalaStore } from '../../core/store';

/** El encabezado lee los mismos selectores del store: siempre está sincronizado. */
@Component({
  selector: 'app-header',
  imports: [CurrencyPipe],
  templateUrl: './header.html',
  styleUrl: './header.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderComponent {
  protected readonly store = inject(SalaStore);

  protected readonly frase =
    'Cada gran historia merece la mejor silla: elige la tuya y vive la magia del cine.';
}
