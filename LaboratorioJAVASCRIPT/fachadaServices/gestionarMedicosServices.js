class GestionarMedicos {
  constructor(repoMedico) {
    this.medicoRepo = repoMedico;
  }

  registrarMedico(nombres, apellidos, especialidad, horaInicioAtencion, horaFinAtencion, anosExperiencia, bibliografia) {
    if (horaFinAtencion <= horaInicioAtencion) {
      throw new Error("La hora de fin de atención debe ser mayor que la hora de inicio");
    }
    const id = this.medicoRepo.siguienteId();
    const horarioAtencion = { horaInicio: horaInicioAtencion, horaFin: horaFinAtencion };
    const medico = new Medico(id, nombres, apellidos, especialidad, horarioAtencion, Number(anosExperiencia), bibliografia);
    this.medicoRepo.agregar(medico);
    return medico;
  }

  listarMedicos() { return this.medicoRepo.obtenerTodos(); }
  buscarMedico(id) { return this.medicoRepo.buscarPorId(id); }
}

const gestionarMedicos = new GestionarMedicos(medicoRepository);
