# Sistema de Gestión de Gimnasio (en desarrollo)
``` text
Este es un sistema de gestión integral para gimnasios, actualmente en desarrollo, desarrollado con Java, Spring Boot, JPA, PostgreSQL, HTML5, CSS3, JavaScript. El objetivo es ofrecer una solución completa para el manejo de membresías, ventas, asistencias, productos y clases, todo desde una interfaz web intuitiva.
```

## Estado del proyecto

**En desarrollo activo**  
En proceso de terminar las funcionalidades básicas CRUD para cada entitdad.  
Próximamente: autenticación, reportes, notificaciones y mejoras visuales.

---

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **JPA / Hibernate**
- **PostgreSQL** (con particiones mensuales)
- **HTML5, CSS3, JavaScript** (interfaz gráfica web)
- **Maven**

---

## Funcionalidades principales (actuales y planeadas)

| Módulo | Estado | Descripción |
|--------|--------|-------------|
| Autenticación de usuarios | Planeado | Inicio de sesión y roles con Auth0 |
| Gestión de usuarios | En proceso | CRUD de clientes |
| Membresías | En proceso | Registro y control de membresías |
| Asistencias | En proceso | Registro diario de ingresos al gimnasio |
| Clases y horarios | En proceso | Vista de clases aeróbicas y control de asistencia |
| Productos y ventas | Implementado | Inventario de productos y ventas |
| Reportes por fecha | Parcialmente implementado | Reportes de ingresos y asistencia mensuales |
| Notificaciones | Planeado | Recordatorios por vencimiento de membresía |

---

## Cómo ejecutar el proyecto

1. **Clona el repositorio:**
```bash
   git clone https://github.com/martkevz/gym.git
   cd gym
```

2. **Compilar y ejecutar:**
    
    ```bash
    mvn clean package
    mvn spring-boot:run
    ```
    
3. **Acceder a la aplicación:**
    
    
    ```bash
    http://localhost:8080
    ```