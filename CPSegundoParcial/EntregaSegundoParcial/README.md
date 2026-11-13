# Clínica Piedra Azul — Segundo parcial (Laboratorio de Ingeniería del Software III)

| Carpeta | Contenido |
|---|---|
| `backend/` | Spring Boot 3 + Spring Security + JWT (puerto 5000, MySQL) |
| `frontend/` | Angular 18 con Atomic Design (puerto 4200) |
| `pruebas/` | Colección Postman/Thunder del backend y plan de pruebas del frontend |

## Cómo ejecutar

1. **MySQL** encendido con usuario `root` / clave `root` (se cambia en `backend/src/main/resources/application.properties`). La base `bdclinica` se crea sola.
2. **Backend**: abrir `backend/` y ejecutar `SpringBootSecurityJwtApplication` (o `./mvnw spring-boot:run`). Al arrancar crea las tablas y carga los datos de prueba.
3. **Frontend**: en `frontend/` ejecutar `npm install` y luego `npm start`. Abrir http://localhost:4200.

## Usuarios de prueba

| Usuario | Contraseña | Rol | Datos |
|---|---|---|---|
| `admin` | `admin123` | Administrador | — |
| `paciente1` | `paciente123` | Paciente | Ana Torres, 3 citas |
| `paciente2` | `paciente123` | Paciente | Carlos Muñoz, 1 cita |

## Servicios del backend

| Método y ruta | Acceso |
|---|---|
| `POST /api/auth/signin` | Público |
| `GET /api/publico/medicos` | Público |
| `GET /api/citas/mis-citas` | Paciente (solo las propias) |
| `GET /api/medicos` · `POST /api/medicos` | Administrador |
| `GET /api/pacientes` · `POST /api/pacientes` | Administrador |
| `GET /api/citas` · `POST /api/citas` | Administrador |
| `GET /api/cedulas/{cedula}/registrada` | Administrador (lo consume el validador asíncrono) |

Sin token en ruta privada → **401**. Token de un rol no autorizado → **403**.

## Rutas del frontend

| Ruta | Acceso |
|---|---|
| `/`, `/medicos`, `/login` | Público |
| `/mis-citas` | Paciente |
| `/admin/medicos`, `/admin/medicos/nuevo`, `/admin/pacientes`, `/admin/pacientes/nuevo`, `/admin/citas`, `/admin/citas/nueva` | Administrador |

## Atomic Design (`frontend/src/app/ui`)

- **Átomos**: `tabla` (reutilizable), `boton`, `titulo`, `logo`, `alerta`, `cargando`
- **Moléculas**: `tabla-con-titulo` (título + tabla), `campo-formulario` (etiqueta + control + error)
- **Organismos**: `encabezado`, `menu-navegacion`, `pie-pagina`, `listado-medicos`, `listado-pacientes`, `listado-citas`, `formulario-login`, `formulario-medico`, `formulario-paciente`, `formulario-cita`, `carrusel-promociones`, `tienda-productos`
- **Plantilla**: `plantilla-principal` (responsive: móvil, tableta y escritorio)
- **Páginas**: `src/app/pages` (todas usan la plantilla)

Seguridad en `frontend/src/app/core`: `guards/auth.guard.ts` (Guard 1), `guards/rol.guard.ts` (Guard 2), `interceptors/jwt.interceptor.ts`.

## Fotos del equipo médico

El directorio público y el listado administrativo muestran miniaturas. El formulario de registro permite elegir un retrato del catálogo local o dejar **Sin foto**. Si no hay foto disponible, el listado muestra las iniciales.

Las imágenes están en `frontend/src/assets/images/medicos/`. Los seis médicos de demostración tienen un retrato asociado por su cédula ficticia en el backend. Estos retratos son ilustrativos. Para los médicos nuevos, se guarda únicamente el nombre del archivo elegido.

La configuración original del backend usa `spring.jpa.hibernate.ddl-auto=create`, que recrea las tablas al arrancar. Para probar esta versión con una base existente y conservar los registros, haz una copia de respaldo y, desde PowerShell en `backend/`, ejecuta:

```powershell
$env:SPRING_JPA_HIBERNATE_DDL_AUTO = 'update'
.\mvnw.cmd spring-boot:run
```

Hibernate añadirá la columna opcional `foto` al esquema. El frontend se inicia desde `frontend/` con `npm ci` y `npm start`.
