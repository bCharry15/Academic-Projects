const formCitas =
  document.getElementById("formCitas");

const tablaCitas =
  document.getElementById("tablaCitas");

const btnAgregarCita =
  document.getElementById("btnAgregarCita");

const horaInicioCita =
  document.getElementById("horaInicio");

const horaFinCita =
  document.getElementById("horaFin");


// ==========================================================
// VALIDAR HORARIO DE LA CITA
// ==========================================================

function validarFormularioCita() {

  const horarioInvalido =

    horaInicioCita.value &&

    horaFinCita.value &&

    horaFinCita.value <=
      horaInicioCita.value;


  horaFinCita.setCustomValidity(

    horarioInvalido

      ? "La hora de fin debe ser mayor que la hora de inicio"

      : ""
  );


  btnAgregarCita.disabled =
    !formCitas.checkValidity();
}


// ==========================================================
// VALIDAR MIENTRAS CAMBIAN LOS CAMPOS
// ==========================================================

formCitas.addEventListener(
  "input",
  validarFormularioCita
);


formCitas.addEventListener(
  "change",
  validarFormularioCita
);


// ==========================================================
// REGISTRAR CITA
// ==========================================================

formCitas.addEventListener(
  "submit",
  (e) => {

    e.preventDefault();


    // Validar horario
    validarFormularioCita();


    // Validaciones requeridas por el taller

    if (!validarCamposCitaTaller()) {
      return;
    }


    // Validaciones propias del formulario HTML

    if (!formCitas.reportValidity()) {
      return;
    }


    // ======================================================
    // OBTENER DATOS
    // ======================================================

    const fecha =
      document
        .getElementById("fecha")
        .value;


    const horaInicio =
      horaInicioCita.value;


    const horaFin =
      horaFinCita.value;


    const medicoId =
      Number(
        document
          .getElementById("medicoSelect")
          .value
      );


    const pacienteId =
      Number(
        document
          .getElementById("pacienteSelect")
          .value
      );


    try {

      // ====================================================
      // REGISTRAR CITA
      // ====================================================

      const cita =
        gestionarCitas.registrarCita(

          fecha,

          horaInicio,

          horaFin,

          medicoId,

          pacienteId
        );


      // ====================================================
      // CREAR FILA EN LA TABLA
      // ====================================================

      const fila =
        document.createElement("tr");


      fila.innerHTML = `

        <td>${cita.fecha}</td>

        <td>${cita.horaInicio}</td>

        <td>${cita.horaFin}</td>

        <td>
          ${cita.medico.nombres}
          ${cita.medico.apellidos}
        </td>

        <td>
          ${cita.paciente.nombres}
          ${cita.paciente.apellidos}
        </td>

      `;


      tablaCitas.appendChild(
        fila
      );


      // ====================================================
      // LIMPIAR FORMULARIO
      // ====================================================

      formCitas.reset();

      limpiarErroresFormulario(
        formCitas
      );

      btnAgregarCita.disabled = true;


      // ====================================================
      // NOTIFICACIÓN
      // ====================================================

      mostrarNotificacion(
        "Cita registrada con éxito"
      );


    } catch (error) {

      mostrarNotificacion(
        error.message,
        "error"
      );
    }
  }
);