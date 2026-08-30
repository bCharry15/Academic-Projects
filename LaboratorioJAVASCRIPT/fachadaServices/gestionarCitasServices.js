class GestionarCitas {
  constructor(medicoRepo, pacienteRepo, citaRepo) {
    this.medicoRepo = medicoRepo;
    this.pacienteRepo = pacienteRepo;
    this.citaRepo = citaRepo;
  }

  registrarCita(fecha, horaInicio, horaFin, idMedico, idPaciente) {
    if (horaFin <= horaInicio) {
      throw new Error("La hora de fin debe ser mayor que la hora de inicio");
    }
    const medico = this.medicoRepo.buscarPorId(idMedico);
    if (!medico) throw new Error("Médico no encontrado");
    const paciente = this.pacienteRepo.buscarPorId(idPaciente);
    if (!paciente) throw new Error("Paciente no encontrado");
    const id = this.citaRepo.siguienteId();
    const cita = new Cita(id, fecha, horaInicio, horaFin, medico, paciente);
    this.citaRepo.agregar(cita);
    return cita;
  }

  listarCitas() { return this.citaRepo.obtenerTodas(); }
  buscarCita(id) { return this.citaRepo.buscarPorId(id); }
}

const gestionarCitas = new GestionarCitas(medicoRepository, pacienteRepo, citaRepo);
