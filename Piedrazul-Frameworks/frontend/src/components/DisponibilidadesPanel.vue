<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  medicos: {
    type: Array,
    default: () => [],
  },
})

const horarios = ref([])
const cargando = ref(false)
const guardando = ref(false)
const error = ref('')
const horarioEnEdicion = ref(null)
const formularioElemento = ref(null)

const dias = [
  'Lunes',
  'Martes',
  'Miércoles',
  'Jueves',
  'Viernes',
  'Sábado',
  'Domingo',
]

function nuevoFormulario() {
  return {
    medicoId: '',
    diaSemana: 1,
    horaInicio: '08:00',
    horaFin: '12:00',
    intervaloMinutos: 30,
    ventanaSemanas: 4,
    activo: true,
  }
}

const formulario = ref(nuevoFormulario())

function nombreMedico(id) {
  const medico = props.medicos.find((medico) => medico.id === id)
  return medico ? medico.nombreCompleto : `Médico ${id}`
}

function seleccionarMedico() {
  const medico = props.medicos.find(
    (medico) => medico.id === formulario.value.medicoId,
  )

  if (medico) {
    formulario.value.intervaloMinutos = medico.intervaloMinutos
  }
}

async function obtenerMensajeError(respuesta) {
  const detalle = await respuesta.json().catch(() => ({}))

  const validaciones = detalle.errors
    ? Object.values(detalle.errors).flat().join(' ')
    : ''

  return (
    detalle.mensaje ||
    validaciones ||
    `La operación falló (${respuesta.status}).`
  )
}

async function cargarHorarios() {
  cargando.value = true
  error.value = ''

  try {
    const respuesta = await fetch(
      'http://localhost:5000/api/disponibilidades',
    )

    if (!respuesta.ok) {
      throw new Error(await obtenerMensajeError(respuesta))
    }

    horarios.value = await respuesta.json()
  } catch (problema) {
    error.value = problema.message
  } finally {
    cargando.value = false
  }
}

function editarHorario(horario) {
  error.value = ''
  horarioEnEdicion.value = horario.id

  formulario.value = {
    medicoId: horario.medicoId,
    diaSemana: horario.diaSemana,
    horaInicio: horario.horaInicio,
    horaFin: horario.horaFin,
    intervaloMinutos: horario.intervaloMinutos,
    ventanaSemanas: horario.ventanaSemanas,
    activo: horario.activo,
  }

  formularioElemento.value?.scrollIntoView({
    behavior: 'smooth',
    block: 'start',
  })
}

function cancelarEdicion() {
  horarioEnEdicion.value = null
  formulario.value = nuevoFormulario()
  error.value = ''
}

async function guardarHorario() {
  guardando.value = true
  error.value = ''

  try {
    const editando = horarioEnEdicion.value !== null

    const url = editando
      ? `http://localhost:5000/api/disponibilidades/${horarioEnEdicion.value}`
      : 'http://localhost:5000/api/disponibilidades'

    const respuesta = await fetch(url, {
      method: editando ? 'PUT' : 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formulario.value),
    })

    if (!respuesta.ok) {
      throw new Error(await obtenerMensajeError(respuesta))
    }

    cancelarEdicion()
    await cargarHorarios()
  } catch (problema) {
    error.value = problema.message
  } finally {
    guardando.value = false
  }
}

async function eliminarHorario(horario) {
  const confirmado = window.confirm(
    `¿Deseas eliminar el horario de ${nombreMedico(horario.medicoId)}?`,
  )

  if (!confirmado) return

  guardando.value = true
  error.value = ''

  try {
    const respuesta = await fetch(
      `http://localhost:5000/api/disponibilidades/${horario.id}`,
      {
        method: 'DELETE',
      },
    )

    if (!respuesta.ok) {
      throw new Error(await obtenerMensajeError(respuesta))
    }

    if (horarioEnEdicion.value === horario.id) {
      cancelarEdicion()
    }

    await cargarHorarios()
  } catch (problema) {
    error.value = problema.message
  } finally {
    guardando.value = false
  }
}

onMounted(cargarHorarios)
</script>

<template>
  <section ref="formularioElemento" class="tarjeta">
    <h2>
      {{
        horarioEnEdicion !== null
          ? 'Editar disponibilidad'
          : 'Registrar disponibilidad'
      }}
    </h2>

    <p>Define el día y las horas de atención de un médico.</p>

    <p v-if="error" class="error" role="alert">
      {{ error }}
    </p>

    <form @submit.prevent="guardarHorario">
      <fieldset :disabled="guardando || cargando" class="formulario">
        <label>
          Médico
          <select
            v-model.number="formulario.medicoId"
            required
            @change="seleccionarMedico"
          >
            <option disabled value="">Selecciona un médico</option>

            <option
              v-for="medico in props.medicos"
              :key="medico.id"
              :value="medico.id"
            >
              {{ medico.nombreCompleto }}
              {{ medico.activo ? '' : '(Inactivo)' }}
            </option>
          </select>
        </label>

        <label>
          Día de la semana
          <select v-model.number="formulario.diaSemana" required>
            <option
              v-for="(dia, indice) in dias"
              :key="indice"
              :value="indice + 1"
            >
              {{ dia }}
            </option>
          </select>
        </label>

        <label>
          Hora de inicio
          <input
            v-model="formulario.horaInicio"
            type="time"
            step="60"
            required
          />
        </label>

        <label>
          Hora de fin
          <input
            v-model="formulario.horaFin"
            type="time"
            step="60"
            required
          />
        </label>

        <label>
          Intervalo de atención en minutos
          <input
            v-model.number="formulario.intervaloMinutos"
            type="number"
            min="5"
            max="240"
            step="1"
            required
          />
        </label>

        <label>
          Ventana en semanas
          <input
            v-model.number="formulario.ventanaSemanas"
            type="number"
            min="1"
            max="52"
            step="1"
            required
          />
        </label>

        <label class="casilla">
          <input v-model="formulario.activo" type="checkbox" />
          Disponibilidad activa
        </label>

        <div class="acciones">
          <button type="submit">
            {{
              guardando
                ? 'Guardando...'
                : horarioEnEdicion !== null
                  ? 'Guardar cambios'
                  : 'Guardar horario'
            }}
          </button>

          <button
            v-if="horarioEnEdicion !== null"
            type="button"
            class="secundario"
            @click="cancelarEdicion"
          >
            Cancelar edición
          </button>
        </div>
      </fieldset>
    </form>
  </section>

  <section class="tarjeta">
    <h2>Disponibilidades registradas</h2>

    <button :disabled="cargando || guardando" @click="cargarHorarios">
      Actualizar horarios
    </button>

    <p v-if="cargando">Cargando horarios...</p>

    <p v-else-if="horarios.length === 0 && !error">
      No hay horarios registrados.
    </p>

    <table v-else-if="horarios.length > 0">
      <thead>
        <tr>
          <th>ID</th>
          <th>Médico</th>
          <th>Día</th>
          <th>Inicio</th>
          <th>Fin</th>
          <th>Intervalo</th>
          <th>Semanas</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="horario in horarios" :key="horario.id">
          <td>{{ horario.id }}</td>
          <td>{{ nombreMedico(horario.medicoId) }}</td>
          <td>{{ dias[horario.diaSemana - 1] }}</td>
          <td>{{ horario.horaInicio }}</td>
          <td>{{ horario.horaFin }}</td>
          <td>{{ horario.intervaloMinutos }} min</td>
          <td>{{ horario.ventanaSemanas }}</td>
          <td>{{ horario.activo ? 'Activo' : 'Inactivo' }}</td>

          <td>
            <div class="acciones">
              <button
                :disabled="guardando || cargando"
                @click="editarHorario(horario)"
              >
                Editar
              </button>

              <button
                class="peligro"
                :disabled="guardando || cargando"
                @click="eliminarHorario(horario)"
              >
                Eliminar
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>