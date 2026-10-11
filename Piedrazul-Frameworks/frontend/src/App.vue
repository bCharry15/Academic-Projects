<script setup>
import { ref, onMounted } from 'vue'
import DisponibilidadesPanel from './components/DisponibilidadesPanel.vue'

const medicos = ref([])
const cargando = ref(false)
const guardando = ref(false)
const error = ref('')
const medicoEnEdicion = ref(null)

function nuevoFormularioMedico() {
  return {
    nombreCompleto: '',
    especialidad: '',
    intervaloMinutos: 30,
    activo: true,
  }
}

const formularioMedico = ref(nuevoFormularioMedico())

async function obtenerMensajeError(respuesta) {
  const detalle = await respuesta.json().catch(() => ({}))

  const mensajeValidacion = detalle.errors
    ? Object.values(detalle.errors).flat().join(' ')
    : ''

  return (
    detalle.mensaje ||
    mensajeValidacion ||
    `La operación falló (${respuesta.status}).`
  )
}

async function cargarMedicos() {
  cargando.value = true
  error.value = ''

  try {
    const respuesta = await fetch('http://localhost:5000/api/medicos')

    if (!respuesta.ok) {
      throw new Error(await obtenerMensajeError(respuesta))
    }

    medicos.value = await respuesta.json()
  } catch (problema) {
    error.value = problema.message
  } finally {
    cargando.value = false
  }
}

function editarMedico(medico) {
  error.value = ''
  medicoEnEdicion.value = medico.id

  formularioMedico.value = {
    nombreCompleto: medico.nombreCompleto,
    especialidad: medico.especialidad,
    intervaloMinutos: medico.intervaloMinutos,
    activo: medico.activo,
  }

  window.scrollTo({ top: 0, behavior: 'smooth' })
}

function cancelarEdicion() {
  medicoEnEdicion.value = null
  formularioMedico.value = nuevoFormularioMedico()
  error.value = ''
}

async function guardarMedico() {
  guardando.value = true
  error.value = ''

  try {
    const editando = medicoEnEdicion.value !== null

    const url = editando
      ? `http://localhost:5000/api/medicos/${medicoEnEdicion.value}`
      : 'http://localhost:5000/api/medicos'

    const respuesta = await fetch(url, {
      method: editando ? 'PUT' : 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formularioMedico.value),
    })

    if (!respuesta.ok) {
      throw new Error(await obtenerMensajeError(respuesta))
    }

    cancelarEdicion()
    await cargarMedicos()
  } catch (problema) {
    error.value = problema.message
  } finally {
    guardando.value = false
  }
}

async function eliminarMedico(medico) {
  const confirmado = window.confirm(
    `¿Deseas eliminar al médico ${medico.nombreCompleto}?`,
  )

  if (!confirmado) return

  guardando.value = true
  error.value = ''

  try {
    const respuesta = await fetch(
      `http://localhost:5000/api/medicos/${medico.id}`,
      {
        method: 'DELETE',
      },
    )

    if (!respuesta.ok) {
      throw new Error(await obtenerMensajeError(respuesta))
    }

    if (medicoEnEdicion.value === medico.id) {
      cancelarEdicion()
    }

    await cargarMedicos()
  } catch (problema) {
    error.value = problema.message
  } finally {
    guardando.value = false
  }
}

onMounted(cargarMedicos)
</script>

<template>
  <main class="contenedor">
    <h1>Piedrazul</h1>
    <p>Administración de médicos y disponibilidad</p>

    <p v-if="error" class="error" role="alert">
      {{ error }}
    </p>

    <section class="tarjeta">
      <h2>
        {{ medicoEnEdicion !== null ? 'Editar médico' : 'Registrar médico' }}
      </h2>

      <form @submit.prevent="guardarMedico">
        <fieldset :disabled="guardando || cargando" class="formulario">
          <label>
            Nombre completo
            <input
              v-model.trim="formularioMedico.nombreCompleto"
              type="text"
              maxlength="120"
              placeholder="Ejemplo: Carlos Demo"
              required
            />
          </label>

          <label>
            Especialidad
            <input
              v-model.trim="formularioMedico.especialidad"
              type="text"
              maxlength="100"
              placeholder="Ejemplo: Pediatría"
              required
            />
          </label>

          <label>
            Intervalo de atención en minutos
            <input
              v-model.number="formularioMedico.intervaloMinutos"
              type="number"
              min="5"
              max="240"
              step="1"
              required
            />
          </label>

          <label class="casilla">
            <input v-model="formularioMedico.activo" type="checkbox" />
            Médico activo
          </label>

          <div class="acciones">
            <button type="submit">
              {{
                guardando
                  ? 'Guardando...'
                  : medicoEnEdicion !== null
                    ? 'Guardar cambios'
                    : 'Guardar médico'
              }}
            </button>

            <button
              v-if="medicoEnEdicion !== null"
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
      <h2>Médicos registrados</h2>

      <button :disabled="cargando || guardando" @click="cargarMedicos">
        Actualizar lista
      </button>

      <p v-if="cargando">Cargando médicos...</p>

      <p v-else-if="medicos.length === 0 && !error">
        No hay médicos registrados.
      </p>

      <table v-else-if="medicos.length > 0">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Especialidad</th>
            <th>Intervalo</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="medico in medicos" :key="medico.id">
            <td>{{ medico.id }}</td>
            <td>{{ medico.nombreCompleto }}</td>
            <td>{{ medico.especialidad }}</td>
            <td>{{ medico.intervaloMinutos }} minutos</td>
            <td>{{ medico.activo ? 'Activo' : 'Inactivo' }}</td>

            <td>
              <div class="acciones">
                <button
                  :disabled="guardando || cargando"
                  @click="editarMedico(medico)"
                >
                  Editar
                </button>

                <button
                  class="peligro"
                  :disabled="guardando || cargando"
                  @click="eliminarMedico(medico)"
                >
                  Eliminar
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </section>

    <DisponibilidadesPanel :medicos="medicos" />
  </main>
</template>