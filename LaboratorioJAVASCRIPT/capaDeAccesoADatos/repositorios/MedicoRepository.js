class MedicoRepository {
  constructor() {
    this.medicos = [];
  }

  agregar(medico) {
    this.medicos.push(medico);
  }

  obtenerTodos() {
    return this.medicos;
  }

  buscarPorId(id) {
    return this.medicos.find(m => m.id === id);
  }

  siguienteId() {
    return this.medicos.length + 1;
  }
}

const medicoRepository = new MedicoRepository();

