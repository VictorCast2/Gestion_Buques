name: Bug report
about: Reporta un error en el sistema de gestión de buques y horarios
title: '[BUG] Título claro del error'
labels: bug
assignees: 'VictorCast2'

body:
- type: markdown
  attributes:
  value: |
  ### 🐛 Descripción del error
  Explica de forma clara qué está fallando en el sistema.

- type: textarea
  id: bug-description
  attributes:
  label: ¿Qué sucede?
  placeholder: Ejemplo: Al registrar un nuevo horario para un buque, el sistema lanza un error 500...
  validations:
  required: true

- type: textarea
  id: reproduction-steps
  attributes:
  label: ¿Cómo reproducirlo?
  description: Describe los pasos para reproducir el error.
  value: |
  1. Ir a '...'
  2. Hacer clic en '...'
  3. Ingresar datos '...'
  4. Ver el error
  validations:
  required: true

- type: textarea
  id: expected-behavior
  attributes:
  label: Comportamiento esperado
  placeholder: Ejemplo: El nuevo horario debería guardarse correctamente en la colección `horarios`.
  validations:
  required: true

- type: textarea
  id: screenshots
  attributes:
  label: Capturas de pantalla
  description: Adjunta imágenes si ayudan a entender el problema.

- type: input
  id: api-endpoint
  attributes:
  label: Endpoint afectado (si aplica)
  placeholder: Ej. POST /api/horarios, GET /api/buques/{id}

- type: textarea
  id: error-logs
  attributes:
  label: Logs del error
  description: Pega aquí el mensaje de error que aparece en consola o el log de Spring Boot.
  placeholder: Ejemplo: `org.springframework.data.mongodb.UncategorizedMongoDbException: ...`

- type: input
  id: spring-version
  attributes:
  label: Versión de Spring Boot
  placeholder: Ej. 3.2.1

- type: input
  id: mongodb-version
  attributes:
  label: Versión de MongoDB
  placeholder: Ej. 6.0.4

- type: textarea
  id: additional-context
  attributes:
  label: Contexto adicional
  description: Cualquier otra información útil (por ejemplo, cambios recientes, dependencias nuevas, etc).