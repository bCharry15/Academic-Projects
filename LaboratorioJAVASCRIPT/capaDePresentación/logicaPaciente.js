const formPaciente =
  document.getElementById("formPaciente");

const pacienteSelect =
  document.getElementById("pacienteSelect");

const btnAgregarPaciente =
  document.getElementById(
    "btnAgregarPaciente"
  );


// ==========================================================
// HABILITAR O DESHABILITAR BOTÓN
// ==========================================================

function actualizarEstadoFormularioPaciente() {

  btnAgregarPaciente.disabled =
    !formularioPacienteValido();
}


// ==========================================================
// VALIDAR MIENTRAS ESCRIBE
// ==========================================================

formPaciente.addEventListener(
  "input",
  actualizarEstadoFormularioPaciente
);


formPaciente.addEventListener(
  "change",
  actualizarEstadoFormularioPaciente
);


// ==========================================================
// REGISTRAR PACIENTE
// ==========================================================

formPaciente.addEventListener(
  "submit",
  (e) => {

    e.preventDefault();


    // Validaciones creadas para el taller

    if (!validarCamposPaciente()) {
      return;
    }


    // Validaciones propias de HTML

    if (!formPaciente.reportValidity()) {
      return;
    }


    const nombres =
      document
        .getElementById("nombresPaciente")
        .value
        .trim();


    const apellidos =
      document
        .getElementById("apellidosPaciente")
        .value
        .trim();


    // ======================================================
    // REGISTRAR PACIENTE
    // ======================================================

    const paciente =
      gestionarPacientes.registrarPaciente(
        nombres,
        apellidos
      );


    console.log(
      "Paciente registrado:",
      paciente
    );


    // ======================================================
    // AGREGAR PACIENTE AL SELECT DE CITAS
    // ======================================================

    const option =
      document.createElement("option");

    option.value = paciente.id;

    option.textContent =
      `${paciente.nombres} ${paciente.apellidos}`;

    pacienteSelect.appendChild(option);


    // ======================================================
    // LIMPIAR FORMULARIO
    // ======================================================

    formPaciente.reset();

    limpiarErroresFormulario(
      formPaciente
    );

    btnAgregarPaciente.disabled = true;


    // ======================================================
    // NOTIFICACIÓN
    // ======================================================

    mostrarNotificacion(

      `Paciente ${paciente.nombres} ${paciente.apellidos} registrado con éxito`

    );
  }
);