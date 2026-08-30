// Utilidad simple de notificaciones tipo "toast" para la capa de presentación.
function mostrarNotificacion(mensaje, tipo = "exito", duracionMs = 3000) {
  const contenedor = document.getElementById("notificaciones");
  if (!contenedor) return;

  const toast = document.createElement("div");
  toast.className = `notificacion notificacion-${tipo}`;
  toast.textContent = mensaje;

  contenedor.appendChild(toast);

  setTimeout(() => {
    toast.remove();
  }, duracionMs);
}
