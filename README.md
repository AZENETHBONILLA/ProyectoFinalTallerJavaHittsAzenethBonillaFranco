Task Manager API

Gestor de Tareas con Spring Boot, JWT y Swagger
---
El Task Manager es una API REST desarrollada en Spring Boot que permite crear, listar, actualizar y eliminar tareas personales.
Incluye autenticación mediante JWT (JSON Web Tokens) y control de acceso por roles (USER / ADMIN).

Este proyecto forma parte de una práctica profesional para comprender:

- Arquitectura Spring Boot (MVC + JPA + Security)

- Uso de Swagger para documentar APIs
  
- Manejo de JWT para autenticación segura

Tecnologías Utilizadas:

☕ Java 17+	Lenguaje principal

🌱 Spring Boot 3.x	Framework base

🔒 Spring Security + JWT	Autenticación y autorización

🗄️ MySQL	Base de datos relacional

🧩 Spring Data JPA	Acceso a datos y ORM

🧰 Lombok	Simplificación de código

📘 Swagger / OpenAPI 3	Documentación de la API

🧪 Postman	Pruebas de endpoints

Cómo Ejecutar:
1. Clonar el repositorio
git clone https://github.com/<tu-usuario>/task-manager.git
cd task-manager

2. Configurar la base de datos MySQL
Crea una base llamada bd_taskmanager y edita el archivo application.properties:

---
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager

spring.datasource.username=tu_usuario

spring.datasource.password=tu_contraseña

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

---

3. Ejecutar la aplicación
mvn spring-boot:run

✅ La API estará disponible en:
http://localhost:8080

---

🔐 Autenticación y Roles
Rol	Permisos:

👤 USER	Crear y listar tareas

🛠️ ADMIN	Crear, listar, actualizar y eliminar tareas

---

Autora:
Azeneth Bonilla

Ingeniera en Sistemas Computacionales
