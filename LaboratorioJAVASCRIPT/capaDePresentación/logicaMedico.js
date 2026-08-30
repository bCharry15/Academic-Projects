const formMedico =
  document.getElementById("formMedico");

const medicoSelect =
  document.getElementById("medicoSelect");

const btnAgregarMedico =
  document.getElementById("btnAgregarMedico");

const horaInicioAtencionMedico =
  document.getElementById(
    "horaInicioAtencionMedico"
  );

const horaFinAtencionMedico =
  document.getElementById(
    "horaFinAtencionMedico"
  );


// ==========================================================
// VALIDAR HORARIO DEL MÉDICO
// ==========================================================

function validarHorarioMedico() {

  const horarioInvalido =

    horaInicioAtencionMedico.value &&

    horaFinAtencionMedico.value &&

    horaFinAtencionMedico.value <=
      horaInicioAtencionMedico.value;


  horaFinAtencionMedico.setCustomValidity(

    horarioInvalido

      ? "La hora de fin debe ser mayor que la hora de inicio"

      : ""
  );


  btnAgregarMedico.disabled =
    !formularioMedicoValido();
}


// ==========================================================
// VALIDAR MIENTRAS CAMBIAN LOS CAMPOS
// ==========================================================

formMedico.addEventListener(
  "input",
  validarHorarioMedico
);


formMedico.addEventListener(
  "change",
  validarHorarioMedico
);


// ==========================================================
// REGISTRAR MÉDICO
// ==========================================================

formMedico.addEventListener(
  "submit",
  (e) => {

    e.preventDefault();


    // Validación del horario
    validarHorarioMedico();


    // Validaciones del taller
    if (!validarCamposMedico()) {
      return;
    }


    // Validaciones propias de HTML
    if (!formMedico.reportValidity()) {
      return;
    }


    try {

      const medico =
        gestionarMedicos.registrarMedico(

          document
            .getElementById("nombresMedico")
            .value
            .trim(),

          document
            .getElementById("apellidosMedico")
            .value
            .trim(),

          document
            .getElementById("especialidadMedico")
            .value
            .trim(),

          horaInicioAtencionMedico.value,

          horaFinAtencionMedico.value,

          document
            .getElementById("anosExperienciaMedico")
            .value,

          document
            .getElementById("bibliografiaMedico")
            .value
            .trim()
        );


      // ====================================================
      // AGREGAR MÉDICO AL SELECT DE CITAS
      // ====================================================

      const option =
        document.createElement("option");

      option.value = medico.id;

      option.textContent =
        `${medico.nombres} ${medico.apellidos} — ${medico.especialidad}`;

      medicoSelect.appendChild(option);


      // ====================================================
      // LIMPIAR FORMULARIO
      // ====================================================

      formMedico.reset();

      limpiarErroresFormulario(
        formMedico
      );

      btnAgregarMedico.disabled = true;


      // ====================================================
      // NOTIFICACIÓN
      // ====================================================

      mostrarNotificacion(

        `Médico ${medico.nombres} ${medico.apellidos} registrado con éxito`

      );


    } catch (error) {

      mostrarNotificacion(
        error.message,
        "error"
      );
    }
  }
);