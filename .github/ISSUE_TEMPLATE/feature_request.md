name: Feature request
about: Sugiere una mejora o nueva funcionalidad para el sistema de gestión de buques y horarios
title: '[FEATURE] Breve descripción de la idea'
labels: enhancement
assignees: 

body:
- type: markdown
  attributes:
  value: |
  ### 💡 Solicitud de funcionalidad
  Describe una nueva idea o mejora para el sistema.

- type: textarea
  id: problem-description
  attributes:
  label: ¿Está relacionado con algún problema?
  description: Describe el problema que estás tratando de resolver. Por ejemplo: "Es frustrante que no pueda filtrar los horarios por ciudad..."
  placeholder: Ej. Siempre me frustra que no pueda ver los buques que tienen horarios activos en una ciudad específica.
  validations:
  required: true

- type: textarea
  id: proposed-solution
  attributes:
  label: ¿Qué solución propones?
  placeholder: Me gustaría tener un filtro en el endpoint GET /api/horarios que permita buscar por ciudad.
  validations:
  required: true

- type: textarea
  id: alternatives-considered
  attributes:
  label: Alternativas que has considerado
  placeholder: Ej. También pensé en exportar los datos y filtrarlos manualmente, pero no es eficiente.
  validations:
  required: false

- type: textarea
  id: additional-context
  attributes:
  label: Contexto adicional
  description: Añade cualquier otro detalle, diseño o imagen para explicar mejor tu idea.
