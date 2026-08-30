class CitaRepository {
  constructor() {
    this.citas = [];
  }

  agregar(cita) {
    this.citas.push(cita);
  }

  obtenerTodas() {
    return this.citas;
  }

  buscarPorId(id) {
    return this.citas.find(c => c.id === id);
  }

  siguienteId() {
    return this.citas.length + 1;
  }
}

const citaRepo = new CitaRepository();
