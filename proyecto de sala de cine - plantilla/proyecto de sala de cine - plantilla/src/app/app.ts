import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ReservaPage } from './features/reserva/pages/reserva/reserva-page';
import { FooterComponent } from './layout/footer/footer';
import { HeaderComponent } from './layout/header/header';

@Component({
  selector: 'app-root',
  imports: [HeaderComponent, FooterComponent, ReservaPage],
  templateUrl: './app.html',
  styleUrl: './app.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App {}
