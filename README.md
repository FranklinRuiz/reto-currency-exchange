# Documentación de Arquitectura - Reto Currency Exchange

## 📌 Visión General

El proyecto **Reto Currency Exchange** es una aplicación basada en **Java 21**, construida con **Spring WebFlux** para manejar concurrencia de manera reactiva. Sigue el patrón de **Arquitectura Hexagonal (Ports & Adapters)**, promoviendo una separación clara entre la lógica de negocio y sus interfaces externas.

---

## 🏛️ Arquitectura Hexagonal

La aplicación se estructura en las siguientes capas:

### **1️⃣ Dominio (Core de Negocio)**

📂 `src/main/java/pe/reto/retocurrencyexchange/domain/`

- 📂 `model/`
    - 📂 `audit/` → Contiene entidades de auditoría como `AuditEntity`.
    - **`ExchangeApiResponse`**, **`ExchangeRate`**, **`ExchangeTransaction`** → Entidades principales del dominio.
- 📂 `port/`
    - 📂 `in/` → Contiene la interfaz `ExchangeUseCase` que define los casos de uso.
    - 📂 `out/` → Contiene los puertos de salida `ExchangeRateRepositoryPort` y `ExchangeTransactionRepositoryPort` que definen la persistencia.

### **2️⃣ Aplicación (Lógica de Negocio)**

📂 `src/main/java/pe/reto/retocurrencyexchange/application/`

- 📂 `service/`
    - **`AuthService`** → Maneja la autenticación de usuarios.
    - **`ExchangeServiceImpl`** → Implementa la lógica del caso de uso de conversión de moneda.
- 📂 `exception/`
    - **`ExchangeRateException`** → Excepción específica de tasas de cambio.
    - **`GlobalExceptionHandler`** → Manejador global de excepciones.

### **3️⃣ Adaptadores (Interfaz con el exterior)**

📂 `src/main/java/pe/reto/retocurrencyexchange/adapter/`

- 📂 `configuration/` → Configuración de seguridad y JWT.
- 📂 `dto/`
    - 📂 `request/` → Contiene objetos de solicitud.
    - 📂 `response/` → Contiene objetos de respuesta.
- 📂 `in.web.controller/` → Controladores REST.
- 📂 `out.persistence/`
    - 📂 `entity/` → Contiene las entidades persistentes.
    - 📂 `repository/` → Interfaces y adaptadores de persistencia.

### **4️⃣ Aplicación Principal**

📂 `src/main/java/pe/reto/retocurrencyexchange/`

- **`CurrencyExchangeApplication.java`** → Clase principal de arranque (Spring Boot).

### **5️⃣ Recursos**

📂 `src/main/resources/`

- **`application.yml`** → Configuración de propiedades de la aplicación.
- **`schema.sql`** → Script SQL para la base de datos.

---

## 🔧 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring WebFlux** (reactivo)
- **JWT** para autenticación
- **H2**
- **Docker & Docker Compose**
- **Nginx** como proxy inverso

---

## 📡 Comunicación entre Capas

La comunicación dentro de la arquitectura sigue los principios de **inyección de dependencias** y el uso de **Interfaces como puertos**:

- Los **servicios del dominio** dependen de **interfaces**, no de implementaciones.
- Los **adaptadores** implementan esas interfaces y actúan como puentes con tecnologías externas.
- Los **controladores REST** solo interactúan con los **casos de uso** sin lógica de negocio dentro.

---

## 📑 Endpoints REST

Algunos de los endpoints expuestos en `adapter/in.web.controller/`:

| Método | Endpoint            | Descripción                      |
| ------ | ------------------- | -------------------------------- |
| `POST` | `/api/auth/login`   | Autenticación con JWT            |
| `GET`  | `/api/exchange`     | Obtener tipos de cambio          |
| `POST` | `/api/exchange`     | Realizar conversión de moneda    |
| `GET`  | `/api/exchange/all` | Listar todas las transacciones   |
| `POST` | `/api/rates`        | Guardar una nueva tasa de cambio |
| `PUT`  | `/api/rates/{id}`   | Actualizar una tasa de cambio    |
| `GET`  | `/api/rates`        | Listar todas las tasas de cambio |

Para más detalles, revisar la colección de **Postman** incluida en el proyecto.

---

## 🏗️ Despliegue

### **Local**

1. Construir con Maven:
   ```sh
   ./mvnw clean package
   ```
2. Ejecutar con Docker Compose:
   ```sh
   docker-compose up --build
   ```

### **Producción**

- Se recomienda desplegar en **Docker**.
- Uso de **NGINX** como balanceador.