import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Medico } from './medico';

@Component({
  selector: 'app-medicos',
  imports: [
    CommonModule
  ],
  templateUrl: './medicos.html',
  styleUrl: './medicos.css'
})
export class Medicos {

  public especialidadSeleccionada: string = 'Todos';

  public descripcionEspecialidad: string =
    'Conoce todos los profesionales disponibles en nuestras diferentes especialidades médicas.';

  public medicos: Medico[] = [

    {
      nombre: 'Dra. Laura Gómez',
      especialidad: 'Cardiología',
      experiencia: '10 años de experiencia',
      descripcion:
        'Especialista en prevención, diagnóstico y tratamiento de enfermedades cardiovasculares.',
      icono: 'fa-heart-pulse',
      imagen: '/img/medicos/laura.jpg'
    },

    {
      nombre: 'Dr. Carlos Rodríguez',
      especialidad: 'Pediatría',
      experiencia: '8 años de experiencia',
      descripcion:
        'Especialista en atención integral de niños y adolescentes.',
      icono: 'fa-baby',
      imagen: '/img/medicos/carlos.jpg'
    },

    {
      nombre: 'Dra. Andrea Martínez',
      especialidad: 'Dermatología',
      experiencia: '7 años de experiencia',
      descripcion:
        'Especialista en diagnóstico y tratamiento de enfermedades de la piel.',
      icono: 'fa-hand-dots',
      imagen: '/img/medicos/andrea.jpg'
    },

    {
      nombre: 'Dr. Felipe Torres',
      especialidad: 'Neurología',
      experiencia: '12 años de experiencia',
      descripcion:
        'Especialista en enfermedades relacionadas con el sistema nervioso.',
      icono: 'fa-brain',
      imagen: '/img/medicos/felipe.jpg'
    },

    {
      nombre: 'Dra. Natalia López',
      especialidad: 'Cardiología',
      experiencia: '9 años de experiencia',
      descripcion:
        'Especialista en cuidado cardiovascular y prevención de enfermedades del corazón.',
      icono: 'fa-heart-pulse',
      imagen: '/img/medicos/natalia.jpg'
    },

    {
      nombre: 'Dr. Santiago Pérez',
      especialidad: 'Pediatría',
      experiencia: '6 años de experiencia',
      descripcion:
        'Especialista en crecimiento, desarrollo y bienestar infantil.',
      icono: 'fa-baby',
      imagen: '/img/medicos/santiago.jpg'
    }

  ];


  public cambiarEspecialidad(especialidad: string): void {

    this.especialidadSeleccionada = especialidad;

    switch (especialidad) {

      case 'Cardiología':

        this.descripcionEspecialidad =
          'La cardiología se encarga de la prevención, diagnóstico y tratamiento de las enfermedades relacionadas con el corazón y el sistema cardiovascular.';

        break;

      case 'Pediatría':

        this.descripcionEspecialidad =
          'La pediatría se enfoca en la atención médica, crecimiento, desarrollo y prevención de enfermedades en niños y adolescentes.';

        break;

      case 'Dermatología':

        this.descripcionEspecialidad =
          'La dermatología se encarga del diagnóstico, prevención y tratamiento de enfermedades relacionadas con la piel.';

        break;

      case 'Neurología':

        this.descripcionEspecialidad =
          'La neurología estudia, diagnostica y trata enfermedades relacionadas con el cerebro, la médula espinal y el sistema nervioso.';

        break;

      default:

        this.descripcionEspecialidad =
          'Conoce todos los profesionales disponibles en nuestras diferentes especialidades médicas.';

        break;

    }

  }


  public obtenerMedicosFiltrados(): Medico[] {

    if (this.especialidadSeleccionada === 'Todos') {

      return this.medicos;

    }

    return this.medicos.filter(
      medico =>
        medico.especialidad === this.especialidadSeleccionada
    );

  }

}