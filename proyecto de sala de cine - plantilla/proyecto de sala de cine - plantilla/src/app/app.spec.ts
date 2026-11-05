import { TestBed } from '@angular/core/testing';
import { App } from './app';
import { appConfig } from './app.config';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: appConfig.providers,
    }).compileComponents();
  });

  it('renderiza header, main y footer', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelector('app-header')).toBeTruthy();
    expect(el.querySelector('main')).toBeTruthy();
    expect(el.querySelector('app-footer')).toBeTruthy();
  });

  it('pinta las 80 sillas de la sala', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const el = fixture.nativeElement as HTMLElement;
    expect(el.querySelectorAll('app-silla').length).toBe(80);
  });

  it('al tocar una silla libre se actualizan mapa y encabezado', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const el = fixture.nativeElement as HTMLElement;

    const libre = el.querySelector<HTMLButtonElement>('button[title="Silla A1, general, libre"]')!;
    libre.click();
    await fixture.whenStable();

    expect(el.querySelector('button[title="Silla A1, general, seleccionada"]')).toBeTruthy();
    expect(el.querySelector('.header__badge')?.textContent?.trim()).toBe('1');
  });

  it('el total del encabezado suma el precio de las sillas seleccionadas', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const el = fixture.nativeElement as HTMLElement;

    el.querySelector<HTMLButtonElement>('button[title="Silla A1, general, libre"]')!.click();
    el.querySelector<HTMLButtonElement>('button[title="Silla H1, preferencial, libre"]')!.click();
    await fixture.whenStable();

    // 14.000 (general) + 20.000 (preferencial)
    expect(el.querySelector('.header__total')?.textContent).toContain('34.000');
  });

  it('el color de la silla se deriva de su estado', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const el = fixture.nativeElement as HTMLElement;
    const color = (titulo: string) =>
      el.querySelector<HTMLElement>(`button[title="${titulo}"]`)!.style.getPropertyValue('--color-silla');

    expect(color('Silla A1, general, libre')).toBe('var(--silla-libre)');
    expect(color('Silla A3, general, ocupada')).toBe('var(--silla-ocupada)');

    el.querySelector<HTMLButtonElement>('button[title="Silla A1, general, libre"]')!.click();
    await fixture.whenStable();
    expect(color('Silla A1, general, seleccionada')).toBe('var(--silla-seleccionada)');
  });
});
