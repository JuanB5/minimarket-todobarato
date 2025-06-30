Minimarket TODO BARATO es una aplicación web para gestión de inventario, clientes, productos y categorías en un minimarket, desarrollada con **Spring Boot**, **Thymeleaf**, **Hibernate/JPA** y **Bootstrap 5**.

Características principales

- **Dashboard** con estadísticas globales de productos, clientes y categoría.
- Gestión completa de **productos**:
  - Listado, búsqueda, alta, edición, eliminación lógica y control de stock bajo.
- Gestión completa de **clientes**:
  - Listado, búsqueda, alta, edición y eliminación lógica.
- Vista de **categorías** (si está implementada).
- Navegación fluida bajo la ruta base **`/todobarato`** y redirección desde la raíz `/`.
- Interfaz moderna y responsiva con **Bootstrap** e **iconos Font Awesome 6**.
- Lógica de negocio en Java: stock mínimo, precios formateados, estados activos/inactivos.

---

Cómo ejecutarlo localmente

### Requisitos previos

- Java 17 (o compatible con tu versión de Spring Boot)
- Maven o Gradle (según configuración)
- Base de datos configurada (H2, MySQL, PostgreSQL, etc.)
- Variables de entorno o `application.properties` bien configuradas

### Para correr el proyecto

```bash
git clone https://github.com/JuanB5/minimarket-todobarato.git
cd minimarket-todobarato
./mvnw spring-boot:run
