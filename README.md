# 🚢 Gestión Inteligente de Horarios y Buques de Carga Puerto de Cartagena

Una aplicación web desarrollada con **Spring Boot**, diseñada para optimizar la **planificación, programación y gestión operativa de buques de carga** en el puerto de Cartagena. Con soporte de autenticación segura, control de accesos por roles, sesiones concurrentes y manejo eficiente de datos en tiempo real.

---

## 📌 Descripción del Proyecto

El puerto de Cartagena enfrenta desafíos críticos en la gestión manual de horarios, asignación de muelles y coordinación logística. Esta aplicación surge como una solución moderna para:

- Automatizar y centralizar la programación de buques.
- Mejorar la eficiencia operativa del puerto.
- Facilitar la colaboración entre autoridades portuarias, empresas navieras y operarios.
- Brindar visibilidad en tiempo real sobre el estado del puerto.

---

## ❓ Pregunta Problema

**¿Cómo desarrollar una aplicación web en Spring Security para la gestión de horarios y buques de carga para la ciudad de Cartagena?**

---

## 📝 Justificación

Actualmente, el puerto de Cartagena opera con métodos manuales o herramientas poco modernas para la programación de buques, lo que genera retrasos, costos adicionales y errores de coordinación. Esta plataforma proporciona:

✅ Control en tiempo real.  
✅ Gestión centralizada.  
✅ Autenticación segura.  
✅ Dashboards personalizados.

Con esta aplicación, se espera mejorar la productividad portuaria, reducir los tiempos de espera y elevar la competitividad logística de la región.

---

## 🎯 Objetivos

### 🎯 Objetivo General

Desarrollar un software web con **Spring Boot y Spring Security** que permita gestionar eficientemente los horarios y la programación de buques en el puerto de Cartagena.

### ✅ Objetivos Específicos

- Analizar necesidades de gestión de horarios y programación de buques.
- Diseñar una plataforma web centralizada y en tiempo real.
- Implementar autenticación y autorización avanzada con Spring Security.
- Incorporar JWT para control seguro de sesiones.
- Gestionar múltiples logins simultáneos con Redis.
- Ofrecer dashboards personalizables según roles.

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Uso Principal |
|------------|----------------|
| **Spring Boot** | Backend, controladores REST |
| **Spring Security** | Autenticación y autorización |
| **JWT (JSON Web Token)** | Gestión de sesiones |
| **MongoDB** | Base de datos principal |
| **Redis** | Cache y manejo de sesiones concurrentes |
| **Lombok** | Simplificación de código Java |
| **Maven** | Gestión de dependencias |

---

## 🔐 Seguridad y Autenticación

El sistema utiliza JWT para firmar los tokens de acceso y Redis para mantener múltiples sesiones activas por usuario.
