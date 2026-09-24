import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Medicos } from './medicos';

describe('Medicos', () => {
  let component: Medicos;
  let fixture: ComponentFixture<Medicos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Medicos],
    }).compileComponents();

    fixture = TestBed.createComponent(Medicos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
