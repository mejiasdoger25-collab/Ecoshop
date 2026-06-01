# 🌱 EcoShop

## Descripción

EcoShop es una aplicación web desarrollada con Spring Boot para la gestión y venta de productos ecológicos y sostenibles.

El proyecto parte de un enunciado académico que requería la implementación de una tienda ecológica con gestión de productos, clientes y pedidos. Sin embargo, durante su desarrollo se ha ampliado significativamente tanto el modelo de datos como la lógica de negocio, incorporando nuevas funcionalidades orientadas a una experiencia de compra más realista y una administración más completa de la plataforma.

---

# Objetivos del proyecto

- Gestionar un catálogo de productos ecológicos.
- Administrar clientes y pedidos.
- Controlar el stock de productos.
- Aplicar reglas de negocio relacionadas con sostenibilidad.
- Gestionar usuarios autenticados mediante Spring Security.
- Ofrecer una experiencia de compra mediante carrito de compra.
- Proporcionar estadísticas de negocio mediante dashboard.
- Aplicar buenas prácticas de arquitectura MVC y persistencia con JPA.

---

# Tecnologías utilizadas

## Backend

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security

## Persistencia

- H2 Database

## Frontend

- Thymeleaf
- HTML5
- CSS3
- Bootstrap
- JavaScript

## Herramientas

- Maven
- Git
- GitHub

---

# Arquitectura

El proyecto sigue una arquitectura multicapa:

- Controllers
- Services
- Repositories
- Entities (Modelo)
- Security
- Exception Handling
- Views Thymeleaf

Además, se implementa una capa base genérica de servicios (`BaseServiceImp`) para reutilizar operaciones CRUD comunes.

---

# Modelo de datos

## Entidades obligatorias del enunciado

### Producto

- Nombre
- Precio
- Stock
- Origen
- Certificado ecológico

### Cliente

- Nombre
- Email
- Teléfono

### Pedido

- Código
- Fecha de envío
- Total

### Línea de pedido

Relación intermedia entre pedidos y productos:

- Cantidad
- Precio unitario
- Subtotal

---

# Ampliaciones realizadas

## Categorías

Se ha añadido una nueva entidad:

### Categoría

Permite organizar el catálogo por familias de productos.

Atributos:

- Nombre
- Icono

Relaciones:

- Una categoría contiene múltiples productos.
- Un producto pertenece a una categoría.

---

## Carrito de compra

Funcionalidad completamente añadida respecto al enunciado original.

Características:

- Carrito persistente en sesión.
- Añadir productos.
- Eliminar productos.
- Modificar cantidades.
- Vaciar carrito.
- Cálculo automático del total.
- Conversión automática del carrito en pedido.

---

## Dashboard administrativo

Implementación de un panel de estadísticas con consultas agregadas sobre la base de datos.

Permite visualizar:

- Ingresos totales.
- Productos más vendidos.
- Categorías más vendidas.
- Rendimiento comercial.
- Información global del sistema.

Esta funcionalidad no estaba contemplada en el proyecto base.

---

## Sistema de usuarios y autenticación

Se ha ampliado el modelo con:

- Registro de usuarios.
- Login.
- Gestión de roles.
- Asociación entre usuario y cliente.

Roles implementados:

### ADMIN

Acceso completo:

- Gestión de productos.
- Gestión de categorías.
- Gestión de clientes.
- Gestión de pedidos.
- Dashboard.

### USER / CLIENTE

Acceso limitado:

- Navegación por catálogo.
- Gestión de carrito.
- Realización de pedidos.
- Consulta de información personal.

---

# Modelo de producto ampliado

La entidad Producto ha sido enriquecida respecto al enunciado inicial.

Nuevos atributos:

- Descripción
- Fecha de caducidad
- Imagen
- Stock mínimo
- Categoría asociada

Esto permite una gestión mucho más realista del catálogo.

---

# Modelo de cliente ampliado

Se incorporan nuevos atributos:

- Fecha de registro
- Dinero total gastado
- Saldo disponible
- Cliente VIP
- Usuario asociado

---

# Modelo de pedido ampliado

Se añaden:

- Estado del pedido
- Fecha de devolución
- Cliente asociado

---

# Lógica de negocio implementada

## Gestión inteligente de precios

Los precios no son estáticos.

Se aplican reglas de negocio como:

### Descuento ecológico

Los productos certificados reciben descuento automático.

### Incremento por escasez

Los productos con stock inferior al mínimo recomendado pueden modificar su precio.

### Producto del día

Existe lógica para destacar ofertas especiales.

---

## Gestión de stock

- Control automático del stock.
- Validación antes de confirmar compras.
- Actualización tras generar pedidos.

---

## Gestión de clientes

- Clientes VIP.
- Control de saldo.
- Historial de compras.
- Seguimiento del gasto total.

---

# Validaciones

Se utilizan validaciones mediante Bean Validation.

Ejemplos:

- Campos obligatorios.
- Emails válidos.
- Teléfonos válidos.
- Longitudes mínimas y máximas.
- Precios positivos.
- Stock no negativo.
- Fechas futuras.
- Restricciones de negocio.

---

# Gestión de excepciones

Además de las excepciones exigidas por el enunciado, se ha desarrollado un sistema centralizado de gestión de errores.

Excepciones implementadas:

- InsufficientStockException
- InvalidCertificateException
- InsufficientBalanceException
- UsernameNotFoundException

Mediante:

- Controller Advice
- Manejo global de errores
- Páginas de respuesta controladas

---

# Consultas implementadas

Se han desarrollado numerosas consultas derivadas y personalizadas con Spring Data JPA.

## Productos

- Búsqueda por nombre.
- Búsqueda por descripción.
- Productos ecológicos.
- Productos sin certificación.
- Productos por origen.
- Productos con poco stock.
- Productos por rango de precio.
- Productos próximos a caducar.
- Productos destacados.

## Clientes

- Clientes VIP.
- Clientes por gasto acumulado.
- Clientes registrados desde una fecha.
- Ranking de clientes.

## Pedidos

- Pedidos por cliente.
- Pedidos por estado.
- Pedidos por rango de fechas.
- Pedidos ordenados cronológicamente.

## Categorías

- Categorías más vendidas.
- Categorías menos vendidas.
- Unidades vendidas por categoría.

---

# Seguridad

Implementada con Spring Security.

Características:

- Login personalizado.
- Autenticación de usuarios.
- Autorización por roles.
- Protección de rutas.
- Control de acceso a funcionalidades administrativas.

---

# Interfaz web

Desarrollada con:

- Thymeleaf
- Bootstrap
- CSS personalizado
- JavaScript

Características:

- Diseño responsive.
- Formularios validados.
- Navegación intuitiva.
- Gestión visual del carrito.
- Dashboard administrativo.
- Listados paginados.

---

# Datos de prueba

La aplicación incluye un sistema de carga inicial de datos mediante:

- DataSeed

Permite arrancar la aplicación con información precargada para pruebas y demostraciones.

---

# Estructura principal del proyecto

```text
controller/
service/
repository/
model/
security/
exception/
data/
templates/
static/
```

---

# Ejecución

## Requisitos

- Java 21 (o versión configurada en Maven)
- Maven

## Arranque

```bash
git clone <repositorio>
cd ecoshop
mvn spring-boot:run
```

Acceso:

```text
http://localhost:9000/
```

---

# Mejoras respecto al enunciado original

✅ Categorías

✅ Carrito de compra

✅ Dashboard estadístico

✅ Sistema completo de usuarios

✅ Gestión avanzada de stock

✅ Gestión de saldo de clientes

✅ Clientes VIP

✅ Excepciones personalizadas

✅ Consultas avanzadas JPA

✅ Sistema de ofertas y descuentos

✅ Producto del día

✅ Arquitectura de servicios reutilizable

✅ Validaciones ampliadas

✅ Seguridad avanzada

---

# Autor: Germán Mejías Domínguez

Proyecto desarrollado con ampliaciones a partir del proyecto final de 1º DAM sobre una tienda ecológica utilizando Spring Boot, JPA, Thymeleaf y Spring Security.
