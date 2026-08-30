// ==========================================================
// FUNCIÓN PARA VALIDAR CAMPOS OBLIGATORIOS
// ==========================================================

function validarCampoObligatorio(campo, errorElement, mensaje) {

  if (campo.value.trim() === "") {

    errorElement.textContent = mensaje;

    return false;
  }

  errorElement.textContent = "";

  return true;
}


// ==========================================================
// FUNCIÓN PARA VALIDAR LONGITUD
// ==========================================================

function validarLongitud(campo, errorElement, min, max, mensaje) {

  const longitud = campo.value.trim().length;

  if (longitud < min || longitud > max) {

    errorElement.textContent = mensaje;

    return false;
  }

  errorElement.textContent = "";

  return true;
}


// ==========================================================
// FUNCIÓN PARA VALIDAR SELECT
// ==========================================================

function validarSeleccion(campo, errorElement, mensaje) {

  if (campo.value === "") {

    errorElement.textContent = mensaje;

    return false;
  }

  errorElement.textContent = "";

  return true;
}


// ==========================================================
// FUNCIÓN REUTILIZABLE PARA NOMBRES Y APELLIDOS
// ==========================================================

function validarTextoConLongitud(
  campo,
  errorElement,
  nombreCampo
) {

  const obligatorio = validarCampoObligatorio(
    campo,
    errorElement,
    `El campo ${nombreCampo} es obligatorio`
  );

  if (!obligatorio) {
    return false;
  }

  return validarLongitud(
    campo,
    errorElement,
    1,
    20,
    `El campo ${nombreCampo} debe tener entre 1 y 20 caracteres`
  );
}


// ==========================================================
// VALIDAR FORMULARIO DEL MÉDICO
// ==========================================================

function validarCamposMedico() {

  const nombres =
    document.getElementById("nombresMedico");

  const apellidos =
    document.getElementById("apellidosMedico");

  const especialidad =
    document.getElementById("especialidadMedico");

  const anosExperiencia =
    document.getElementById("anosExperienciaMedico");

  const horaInicio =
    document.getElementById("horaInicioAtencionMedico");

  const horaFin =
    document.getElementById("horaFinAtencionMedico");

  const bibliografia =
    document.getElementById("bibliografiaMedico");


  const nombresValido = validarTextoConLongitud(
    nombres,
    document.getElementById("errorNombresMedico"),
    "nombres"
  );


  const apellidosValido = validarTextoConLongitud(
    apellidos,
    document.getElementById("errorApellidosMedico"),
    "apellidos"
  );


  const especialidadValida = validarCampoObligatorio(
    especialidad,
    document.getElementById("errorEspecialidadMedico"),
    "La especialidad es obligatoria"
  );


  const experienciaValida = validarCampoObligatorio(
    anosExperiencia,
    document.getElementById("errorAnosExperienciaMedico"),
    "Los años de experiencia son obligatorios"
  );


  const horaInicioValida = validarCampoObligatorio(
    horaInicio,
    document.getElementById(
      "errorHoraInicioAtencionMedico"
    ),
    "La hora de inicio es obligatoria"
  );


  const horaFinValida = validarCampoObligatorio(
    horaFin,
    document.getElementById(
      "errorHoraFinAtencionMedico"
    ),
    "La hora de fin es obligatoria"
  );


  const bibliografiaValida = validarCampoObligatorio(
    bibliografia,
    document.getElementById(
      "errorBibliografiaMedico"
    ),
    "La bibliografía es obligatoria"
  );


  return (
    nombresValido &&
    apellidosValido &&
    especialidadValida &&
    experienciaValida &&
    horaInicioValida &&
    horaFinValida &&
    bibliografiaValida
  );
}


// ==========================================================
// VALIDAR FORMULARIO DEL PACIENTE
// ==========================================================

function validarCamposPaciente() {

  const nombres =
    document.getElementById("nombresPaciente");

  const apellidos =
    document.getElementById("apellidosPaciente");


  const nombresValido = validarTextoConLongitud(
    nombres,
    document.getElementById("errorNombresPaciente"),
    "nombres"
  );


  const apellidosValido = validarTextoConLongitud(
    apellidos,
    document.getElementById("errorApellidosPaciente"),
    "apellidos"
  );


  return nombresValido && apellidosValido;
}


// ==========================================================
// VALIDAR FORMULARIO DE CITA
// ==========================================================

function validarCamposCitaTaller() {

  const fecha =
    document.getElementById("fecha");

  const horaInicio =
    document.getElementById("horaInicio");

  const horaFin =
    document.getElementById("horaFin");

  const medico =
    document.getElementById("medicoSelect");

  const paciente =
    document.getElementById("pacienteSelect");


  const fechaValida = validarCampoObligatorio(
    fecha,
    document.getElementById("errorFecha"),
    "La fecha es obligatoria"
  );


  const horaInicioValida = validarCampoObligatorio(
    horaInicio,
    document.getElementById("errorHoraInicio"),
    "La hora de inicio es obligatoria"
  );


  const horaFinValida = validarCampoObligatorio(
    horaFin,
    document.getElementById("errorHoraFin"),
    "La hora de fin es obligatoria"
  );


  const medicoValido = validarSeleccion(
    medico,
    document.getElementById("errorMedicoSelect"),
    "Debe seleccionar un médico"
  );


  const pacienteValido = validarSeleccion(
    paciente,
    document.getElementById("errorPacienteSelect"),
    "Debe seleccionar un paciente"
  );


  return (
    fechaValida &&
    horaInicioValida &&
    horaFinValida &&
    medicoValido &&
    pacienteValido
  );
}


// ==========================================================
// FUNCIONES AUXILIARES PARA HABILITAR BOTONES
// ==========================================================

function esLongitudValida(campo, min, max) {

  const longitud = campo.value.trim().length;

  return longitud >= min && longitud <= max;
}


function tieneTexto(campo) {

  return campo.value.trim() !== "";
}


// ==========================================================
// COMPROBAR SI TODO EL FORMULARIO MÉDICO ES VÁLIDO
// ==========================================================

function formularioMedicoValido() {

  const formulario =
    document.getElementById("formMedico");

  const nombres =
    document.getElementById("nombresMedico");

  const apellidos =
    document.getElementById("apellidosMedico");

  const especialidad =
    document.getElementById("especialidadMedico");

  const bibliografia =
    document.getElementById("bibliografiaMedico");


  return (
    formulario.checkValidity() &&

    tieneTexto(nombres) &&

    tieneTexto(apellidos) &&

    tieneTexto(especialidad) &&

    tieneTexto(bibliografia) &&

    esLongitudValida(nombres, 1, 20) &&

    esLongitudValida(apellidos, 1, 20)
  );
}


// ==========================================================
// COMPROBAR SI TODO EL FORMULARIO PACIENTE ES VÁLIDO
// ==========================================================

function formularioPacienteValido() {

  const formulario =
    document.getElementById("formPaciente");

  const nombres =
    document.getElementById("nombresPaciente");

  const apellidos =
    document.getElementById("apellidosPaciente");


  return (
    formulario.checkValidity() &&

    tieneTexto(nombres) &&

    tieneTexto(apellidos) &&

    esLongitudValida(nombres, 1, 20) &&

    esLongitudValida(apellidos, 1, 20)
  );
}


// ==========================================================
// ACTUALIZAR BOTÓN MÉDICO
// ==========================================================

function actualizarBotonMedico() {

  const boton =
    document.getElementById("btnAgregarMedico");

  boton.disabled = !formularioMedicoValido();
}


// ==========================================================
// ACTUALIZAR BOTÓN PACIENTE
// ==========================================================

function actualizarBotonPaciente() {

  const boton =
    document.getElementById("btnAgregarPaciente");

  boton.disabled = !formularioPacienteValido();
}


// ==========================================================
// ACTUALIZAR BOTÓN CITA
// ==========================================================

function actualizarBotonCita() {

  const formulario =
    document.getElementById("formCitas");

  const boton =
    document.getElementById("btnAgregarCita");

  boton.disabled = !formulario.checkValidity();
}


// ==========================================================
// LIMPIAR MENSAJES DE ERROR
// ==========================================================

function limpiarErroresFormulario(formulario) {

  const errores =
    formulario.querySelectorAll(".error");

  errores.forEach((labelError) => {

    labelError.textContent = "";

  });
}


// ==========================================================
// EVENTOS PARA CAMPOS CON LONGITUD
// ==========================================================

function agregarEventosTextoLongitud(
  campo,
  errorElement,
  nombreCampo,
  actualizarBoton
) {

  const validar = () => {

    validarTextoConLongitud(
      campo,
      errorElement,
      nombreCampo
    );

    actualizarBoton();
  };


  // Se ejecuta mientras el usuario escribe
  campo.addEventListener(
    "input",
    validar
  );


  // Se ejecuta cuando cambia de foco
  campo.addEventListener(
    "blur",
    validar
  );
}


// ==========================================================
// EVENTOS PARA CAMPOS OBLIGATORIOS
// ==========================================================

function agregarEventosObligatorio(
  campo,
  errorElement,
  mensaje,
  actualizarBoton
) {

  const validar = () => {

    validarCampoObligatorio(
      campo,
      errorElement,
      mensaje
    );

    actualizarBoton();
  };


  // Mientras cambia el valor
  campo.addEventListener(
    "input",
    validar
  );


  // Cuando se selecciona un valor
  campo.addEventListener(
    "change",
    validar
  );


  // Cuando cambia de foco
  campo.addEventListener(
    "blur",
    validar
  );
}


// ==========================================================
// EVENTOS PARA SELECT
// ==========================================================

function agregarEventosSelect(
  campo,
  errorElement,
  mensaje,
  actualizarBoton
) {

  const validar = () => {

    validarSeleccion(
      campo,
      errorElement,
      mensaje
    );

    actualizarBoton();
  };


  // Cuando selecciona una opción
  campo.addEventListener(
    "change",
    validar
  );


  // Cuando cambia de foco
  campo.addEventListener(
    "blur",
    validar
  );
}


// ==========================================================
// AGREGAR TODOS LOS EVENTOS DE VALIDACIÓN
// ==========================================================

function validarCamposAlCambiarFoco() {


  // ========================================================
  // MÉDICO
  // ========================================================

  agregarEventosTextoLongitud(

    document.getElementById("nombresMedico"),

    document.getElementById(
      "errorNombresMedico"
    ),

    "nombres",

    actualizarBotonMedico
  );


  agregarEventosTextoLongitud(

    document.getElementById("apellidosMedico"),

    document.getElementById(
      "errorApellidosMedico"
    ),

    "apellidos",

    actualizarBotonMedico
  );


  agregarEventosObligatorio(

    document.getElementById(
      "especialidadMedico"
    ),

    document.getElementById(
      "errorEspecialidadMedico"
    ),

    "La especialidad es obligatoria",

    actualizarBotonMedico
  );


  agregarEventosObligatorio(

    document.getElementById(
      "anosExperienciaMedico"
    ),

    document.getElementById(
      "errorAnosExperienciaMedico"
    ),

    "Los años de experiencia son obligatorios",

    actualizarBotonMedico
  );


  agregarEventosObligatorio(

    document.getElementById(
      "horaInicioAtencionMedico"
    ),

    document.getElementById(
      "errorHoraInicioAtencionMedico"
    ),

    "La hora de inicio es obligatoria",

    actualizarBotonMedico
  );


  agregarEventosObligatorio(

    document.getElementById(
      "horaFinAtencionMedico"
    ),

    document.getElementById(
      "errorHoraFinAtencionMedico"
    ),

    "La hora de fin es obligatoria",

    actualizarBotonMedico
  );


  agregarEventosObligatorio(

    document.getElementById(
      "bibliografiaMedico"
    ),

    document.getElementById(
      "errorBibliografiaMedico"
    ),

    "La bibliografía es obligatoria",

    actualizarBotonMedico
  );


  // ========================================================
  // PACIENTE
  // ========================================================

  agregarEventosTextoLongitud(

    document.getElementById(
      "nombresPaciente"
    ),

    document.getElementById(
      "errorNombresPaciente"
    ),

    "nombres",

    actualizarBotonPaciente
  );


  agregarEventosTextoLongitud(

    document.getElementById(
      "apellidosPaciente"
    ),

    document.getElementById(
      "errorApellidosPaciente"
    ),

    "apellidos",

    actualizarBotonPaciente
  );


  // ========================================================
  // CITA
  // ========================================================

  agregarEventosObligatorio(

    document.getElementById("fecha"),

    document.getElementById(
      "errorFecha"
    ),

    "La fecha es obligatoria",

    actualizarBotonCita
  );


  agregarEventosObligatorio(

    document.getElementById(
      "horaInicio"
    ),

    document.getElementById(
      "errorHoraInicio"
    ),

    "La hora de inicio es obligatoria",

    actualizarBotonCita
  );


  agregarEventosObligatorio(

    document.getElementById(
      "horaFin"
    ),

    document.getElementById(
      "errorHoraFin"
    ),

    "La hora de fin es obligatoria",

    actualizarBotonCita
  );


  agregarEventosSelect(

    document.getElementById(
      "medicoSelect"
    ),

    document.getElementById(
      "errorMedicoSelect"
    ),

    "Debe seleccionar un médico",

    actualizarBotonCita
  );


  agregarEventosSelect(

    document.getElementById(
      "pacienteSelect"
    ),

    document.getElementById(
      "errorPacienteSelect"
    ),

    "Debe seleccionar un paciente",

    actualizarBotonCita
  );


  // Estado inicial de los botones

  actualizarBotonMedico();

  actualizarBotonPaciente();

  actualizarBotonCita();
}


// ==========================================================
// EJECUTAR CUANDO EL DOM ESTÉ CARGADO
// ==========================================================

document.addEventListener(
  "DOMContentLoaded",
  validarCamposAlCambiarFoco
);