# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/payments/domain/ports/in/ProcessPaymentUseCase.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/ports/out/PaymentRepositoryPort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/ports/out/IdempotencyRepositoryPort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/PaymentJpaAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/PaymentJpaAdapter.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `PaymentRequest.idempotencyKey`: Se invoca `idempotencyKey` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `PaymentRepositoryPort.findByIdempotencyKey`: Se invoca `findByIdempotencyKey` sobre `PaymentRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `IdempotencyRepositoryPort.findByIdempotencyKey`: Se invoca `findByIdempotencyKey` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `IdempotencyRepositoryPort.save`: Se invoca `save` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `IdempotencyRepositoryPort.updateStatus`: Se invoca `updateStatus` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `PaymentStatus.name`: Se invoca `name` sobre `PaymentStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/PaymentJpaAdapter.java` — `PaymentJpaRepository.save`: Se invoca `save` sobre `PaymentJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java` — `IdempotencyJpaRepository.save`: Se invoca `save` sobre `IdempotencyJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java` — `IdempotencyEntity.setProcessedAt`: Se invoca `setProcessedAt` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.idempotencyKey`: Se invoca `idempotencyKey` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.amount`: Se invoca `amount` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.currency`: Se invoca `currency` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.payerId`: Se invoca `payerId` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.payeeId`: Se invoca `payeeId` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.description`: Se invoca `description` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java` — `PaymentRepositoryPort.save`: Se invoca `save` sobre `PaymentRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java` — `IdempotencyRepositoryPort.save`: Se invoca `save` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java` — `IdempotencyRepositoryPort.findByIdempotencyKey`: Se invoca `findByIdempotencyKey` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.idempotencyKey`: Se invoca `idempotencyKey` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.accountId`: Se invoca `accountId` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.amount`: Se invoca `amount` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.currency`: Se invoca `currency` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.paymentType`: Se invoca `paymentType` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2

### Brecha de conocimiento
Comprueba manejo de errores y reintentos.

### Reto
- Tema: Idempotencia en pagos
- Seniority: senior-l2
- Tipo: practical
- Título: Implementación de idempotencia en el procesamiento de pagos
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Canal de entrada operativo persistiendo solicitudes con idempotencia — objetivo: Implementar un canal de entrada que acepte solicitudes de pago y las persista de manera idempotente. — entregable (NO resolver): Canal de entrada que acepta y persiste solicitudes de pago de manera idempotente.
- Fase 2: Consolidación de los tres canales sin pérdida ante backpressure de uno — objetivo: Integrar los tres canales de entrada y asegurar que no haya pérdida de datos ante backpressure en alguno de ellos. — entregable (NO resolver): Sistema consolidado que acepta solicitudes de los tres canales y maneja backpressure sin pérdida de datos.
- Fase 3: Recuperación automática ante caída del consumidor downstream sin reprocesar emitidas — objetivo: Implementar un mecanismo de recuperación automática en caso de caída del consumidor downstream, sin reprocesar solicitudes ya emitidas. — entregable (NO resolver): Mecanismo de recuperación automática que no reprocesa solicitudes ya emitidas.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payments</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payments</name>
    <description>Sistema de procesamiento de pagos con idempotencia</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.3</version>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.5.0</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
            <scope>provided</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
        </repository>
    </repositories>
</project>

// === ARCHIVO: src/main/java/com/pragma/payments/Application.java ===
package com.pragma.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class Application {
    private final Environment environment;

    public Application(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("Application started with active profiles: " +
            Arrays.toString(environment.getActiveProfiles()));
        
        // Validar configuración crítica
        validateCriticalConfiguration();
    }

    private void validateCriticalConfiguration() {
        String datasourceUrl = environment.getProperty("spring.datasource.url");
        if (datasourceUrl == null || !datasourceUrl.startsWith("jdbc:postgresql:")) {
            throw new IllegalStateException("La configuración del datasource es inválida. " +
                "Verifica la propiedad 'spring.datasource.url' en application.yml");
        }
        
        Integer bulkheadMaxConcurrent = environment.getProperty(
            "resilience4j.bulkhead.instances.processPayment.maxConcurrentCalls", Integer.class);
        if (bulkheadMaxConcurrent == null || bulkheadMaxConcurrent <= 0) {
            throw new IllegalStateException("La configuración de Resilience4j Bulkhead es inválida. " +
                "Verifica la propiedad 'resilience4j.bulkhead.instances.processPayment.maxConcurrentCalls'");
        }
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: payments-service
  
  datasource:
    url: jdbc:postgresql://localhost:5432/payments_db
    username: payments_user
    password: payments_password
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 2
      idle-timeout: 30000
      connection-timeout: 30000
      max-lifetime: 600000
  
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true

server:
  port: 8080
  servlet:
    context-path: /api/payments

resilience4j:
  bulkhead:
    instances:
      processPayment:
        maxConcurrentCalls: 20
        maxWaitDuration: 1000ms
  
  ratelimiter:
    instances:
      processPayment:
        limitForPeriod: 100
        limitRefreshPeriod: 1s
        timeoutDuration: 500ms
  
  circuitbreaker:
    instances:
      paymentProcessor:
        failureRateThreshold: 50
        waitDurationInOpenState: 5s
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowSize: 10
        slidingWindowType: COUNT_BASED

management:
  endpoints:
    web:
      exposure:
        include: health, metrics, prometheus, resilience4j
  endpoint:
    health:
      show-details: always
  metrics:
    distribution:
      percentiles-histogram:
        resilience4j.circuitbreaker.calls: true

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    operationsSorter: alpha
    tagsSorter: alpha

// === ARCHIVO: src/main/java/com/pragma/payments/domain/model/PaymentRequest.java ===
package com.pragma.payments.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una solicitud de pago recibida desde cualquier canal.
 * Contiene la información necesaria para procesar el pago de manera idempotente.
 */
public record PaymentRequest(
    @NotNull(message = "La clave de idempotencia no puede ser nula")
    UUID idempotencyKey,

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,

    @NotNull(message = "El canal no puede ser nulo")
    @Size(min = 3, max = 20, message = "El canal debe tener entre 3 y 20 caracteres")
    String channel,

    @NotNull(message = "La referencia del pago no puede ser nula")
    @Size(min = 1, max = 100, message = "La referencia debe tener entre 1 y 100 caracteres")
    String paymentReference,

    @NotNull(message = "El identificador del cliente no puede ser nulo")
    UUID customerId,

    LocalDateTime createdAt
) {
    /**
     * Constructor que inicializa la fecha de creación con el momento actual.
     */
    public PaymentRequest {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    /**
     * Valida que la solicitud de pago tenga datos consistentes.
     * @throws IllegalArgumentException si algún campo obligatorio es inválido.
     */
    public void validate() {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (channel == null || channel.trim().isEmpty()) {
            throw new IllegalArgumentException("El canal no puede estar vacío");
        }
        if (paymentReference == null || paymentReference.trim().isEmpty()) {
            throw new IllegalArgumentException("La referencia del pago no puede estar vacía");
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/model/PaymentStatus.java ===
package com.pragma.payments.domain.model;

/**
 * Estados posibles de una solicitud de pago.
 */
public enum PaymentStatus {
    PENDING("Pago en proceso, esperando confirmación"),
    PROCESSED("Pago procesado exitosamente"),
    FAILED("Pago fallido, no se pudo procesar");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/ports/in/ProcessPaymentUseCase.java ===
package com.pragma.payments.domain.ports.in;

import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para procesar pagos de manera idempotente.
 * Define el contrato que la capa de aplicación debe implementar.
 */
public interface ProcessPaymentUseCase {
    /**
     * Procesa una solicitud de pago de manera idempotente.
     * @param paymentRequest La solicitud de pago a procesar.
     * @return Mono con el estado del pago procesado.
     * @throws com.pragma.payments.domain.exception.DuplicatePaymentException si la clave de idempotencia ya existe.
     * @throws com.pragma.payments.domain.exception.PaymentProcessingException si ocurre un error durante el procesamiento.
     */
    Mono<PaymentStatus> processPayment(PaymentRequest paymentRequest);

    /**
     * Verifica el estado de una solicitud de pago existente.
     * @param idempotencyKey La clave de idempotencia de la solicitud.
     * @return Mono con el estado del pago si existe, o Mono.empty() si no existe.
     */
    Mono<PaymentStatus> checkPaymentStatus(UUID idempotencyKey);
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/ports/out/PaymentRepositoryPort.java ===
package com.pragma.payments.domain.ports.out;

import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Puerto de salida para la persistencia de pagos.
 * Define las operaciones de acceso a datos que el dominio necesita
 * para gestionar el ciclo de vida de los pagos.
 */
public interface PaymentRepositoryPort {

    /**
     * Persiste una nueva solicitud de pago en el sistema.
     *
     * @param paymentRequest la solicitud de pago a persistir
     * @param idempotencyKey clave que garantiza la idempotencia de la operación
     * @return Mono que emite el estado del pago una vez persistido
     */
    Mono<PaymentStatus> savePayment(PaymentRequest paymentRequest, UUID idempotencyKey);

    /**
     * Recupera el estado de un pago previamente procesado.
     *
     * @param idempotencyKey clave de idempotencia del pago a consultar
     * @return Mono que emite el estado del pago o vacío si no existe
     */
    Mono<PaymentStatus> findPaymentStatusByIdempotencyKey(UUID idempotencyKey);

    /**
     * Actualiza el estado de un pago existente.
     *
     * @param idempotencyKey clave de idempotencia del pago a actualizar
     * @param newStatus nuevo estado a establecer
     * @return Mono que emite el estado actualizado
     */
    Mono<PaymentStatus> updatePaymentStatus(UUID idempotencyKey, PaymentStatus newStatus);
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/ports/out/IdempotencyRepositoryPort.java ===
package com.pragma.payments.domain.ports.out;

import com.pragma.payments.domain.model.PaymentRequest;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

/**
 * Puerto de salida para la gestión de claves de idempotencia.
 * Garantiza que cada solicitud de pago sea procesada una sola vez,
 * evitando duplicaciones en caso de reintentos por parte del cliente.
 */
public interface IdempotencyRepositoryPort {

    /**
     * Verifica si una clave de idempotencia ya existe en el sistema.
     *
     * @param idempotencyKey clave a verificar
     * @return Mono que emite true si la clave existe, false en caso contrario
     */
    Mono<Boolean> existsByIdempotencyKey(UUID idempotencyKey);

    /**
     * Persiste una nueva clave de idempotencia junto con la solicitud
     * de pago asociada. Debe ejecutarse dentro de una transacción
     * que también persista el pago.
     *
     * @param idempotencyKey clave de idempotencia única
     * @param paymentRequest solicitud de pago asociada
     * @param expiresAt momento de expiración de la clave
     * @return Mono que completa cuando la operación succeeds
     */
    Mono<Void> saveIdempotencyKey(UUID idempotencyKey, PaymentRequest paymentRequest, Instant expiresAt);

    /**
     * Recupera la solicitud de pago asociada a una clave de idempotencia.
     *
     * @param idempotencyKey clave de idempotencia a consultar
     * @return Mono que emite la solicitud de pago original o vacío si no existe
     */
    Mono<PaymentRequest> findPaymentRequestByIdempotencyKey(UUID idempotencyKey);

    /**
     * Elimina una clave de idempotencia que ha expirado.
     *
     * @param idempotencyKey clave a eliminar
     * @return Mono que completa cuando la operación succeeds
     */
    Mono<Void> deleteExpiredIdempotencyKey(UUID idempotencyKey);
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/exception/DuplicatePaymentException.java ===
package com.pragma.payments.domain.exception;

import java.util.UUID;

/**
 * Excepción lanzada cuando se detecta un intento de procesar
 * un pago que ya fue procesado anteriormente.
 * 
 * Esta excepción indica que existe una clave de idempotencia duplicada
 * en el sistema, lo cual significa que el cliente está intentando
 * reprocesar una solicitud que ya fue aceptada.
 */
public class DuplicatePaymentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final UUID idempotencyKey;

    /**
     * Constructor con mensaje de error y clave de idempotencia.
     *
     * @param message descripción del error
     * @param idempotencyKey clave de idempotencia duplicada
     */
    public DuplicatePaymentException(String message, UUID idempotencyKey) {
        super(message);
        this.idempotencyKey = idempotencyKey;
    }

    /**
     * Constructor con mensaje de error, clave de idempotencia y causa raíz.
     *
     * @param message descripción del error
     * @param idempotencyKey clave de idempotencia duplicada
     * @param cause excepción que causó este error
     */
    public DuplicatePaymentException(String message, UUID idempotencyKey, Throwable cause) {
        super(message, cause);
        this.idempotencyKey = idempotencyKey;
    }

    /**
     * Constructor solo con clave de idempotencia.
     * Genera un mensaje automático con la clave.
     *
     * @param idempotencyKey clave de idempotencia duplicada
     */
    public DuplicatePaymentException(UUID idempotencyKey) {
        super("Payment with idempotency key " + idempotencyKey + " already exists");
        this.idempotencyKey = idempotencyKey;
    }

    /**
     * Obtiene la clave de idempotencia asociada a esta excepción.
     *
     * @return la clave de idempotencia duplicada
     */
    public UUID getIdempotencyKey() {
        return idempotencyKey;
    }

    @Override
    public String toString() {
        return "DuplicatePaymentException{" +
                "idempotencyKey=" + idempotencyKey +
                ", message=" + getMessage() +
                '}';
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/exception/PaymentProcessingException.java ===
package com.pragma.payments.domain.exception;

/**
 * Excepcion raiz para errores durante el procesamiento de pagos.
 * Se lanza cuando ocurre un fallo inesperado en el flujo de pago
 * que no corresponde a una condicion de negocio especifica.
 */
public class PaymentProcessingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String paymentId;
    private final String channel;

    public PaymentProcessingException(String message) {
        super(message);
        this.paymentId = null;
        this.channel = null;
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.paymentId = null;
        this.channel = null;
    }

    public PaymentProcessingException(String message, String paymentId, String channel) {
        super(message);
        this.paymentId = paymentId;
        this.channel = channel;
    }

    public PaymentProcessingException(String message, String paymentId, String channel, Throwable cause) {
        super(message, cause);
        this.paymentId = paymentId;
        this.channel = channel;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());
        if (paymentId != null || channel != null) {
            sb.append(" [");
            if (paymentId != null) {
                sb.append("paymentId=").append(paymentId);
            }
            if (channel != null) {
                if (paymentId != null) {
                    sb.append(", ");
                }
                sb.append("channel=").append(channel);
            }
            sb.append("]");
        }
        return sb.toString();
    }

    public boolean hasPaymentContext() {
        return paymentId != null || channel != null;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/exception/BackpressureException.java ===
package com.pragma.payments.domain.exception;

import java.time.Instant;

/**
 * Excepcion lanzada cuando se detecta backpressure en alguno de los canales
 * de entrada de pagos. Indica que el sistema esta bajo carga excesiva y no
 * puede aceptar mas solicitudes en ese momento.
 * 
 * Esta excepcion es temporal y el cliente deberia reintentar la peticion
 * despues de un delay configurado.
 */
public class BackpressureException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String channelName;
    private final Instant detectedAt;
    private final int currentLoad;
    private final int maxCapacity;
    private final long retryAfterMillis;

    public BackpressureException(String channelName) {
        super("Backpressure detected on channel: " + channelName);
        this.channelName = channelName;
        this.detectedAt = Instant.now();
        this.currentLoad = 0;
        this.maxCapacity = 0;
        this.retryAfterMillis = calculateDefaultRetryDelay();
    }

    public BackpressureException(String channelName, int currentLoad, int maxCapacity) {
        super(String.format("Backpressure detected on channel '%s': currentLoad=%d, maxCapacity=%d", 
                channelName, currentLoad, maxCapacity));
        this.channelName = channelName;
        this.detectedAt = Instant.now();
        this.currentLoad = currentLoad;
        this.maxCapacity = maxCapacity;
        this.retryAfterMillis = calculateRetryDelay(currentLoad, maxCapacity);
    }

    public BackpressureException(String channelName, int currentLoad, int maxCapacity, Throwable cause) {
        super(String.format("Backpressure detected on channel '%s': currentLoad=%d, maxCapacity=%d", 
                channelName, currentLoad, maxCapacity), cause);
        this.channelName = channelName;
        this.detectedAt = Instant.now();
        this.currentLoad = currentLoad;
        this.maxCapacity = maxCapacity;
        this.retryAfterMillis = calculateRetryDelay(currentLoad, maxCapacity);
    }

    private static long calculateDefaultRetryDelay() {
        return 5000L;
    }

    private static long calculateRetryDelay(int currentLoad, int maxCapacity) {
        double loadFactor = (double) currentLoad / maxCapacity;
        if (loadFactor >= 0.9) {
            return 10000L;
        } else if (loadFactor >= 0.75) {
            return 5000L;
        } else {
            return 2000L;
        }
    }

    public String getChannelName() {
        return channelName;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }

    public double getLoadPercentage() {
        if (maxCapacity == 0) {
            return 0.0;
        }
        return (double) currentLoad / maxCapacity * 100.0;
    }

    public boolean isCritical() {
        return getLoadPercentage() >= 90.0;
    }

    @Override
    public String toString() {
        return String.format("BackpressureException{channel='%s', load=%.2f%%, retryAfter=%dms}",
                channelName, getLoadPercentage(), retryAfterMillis);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/persistence/entities/PaymentEntity.java ===
package com.pragma.payments.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidad JPA que representa un pago en la base de datos PostgreSQL.
 * Mapea la tabla 'payments' y almacena toda la informacion relacionada
 * con una transaccion de pago procesada por el sistema.
 */
@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_payment_idempotency_key", columnList = "idempotencyKey"),
    @Index(name = "idx_payment_status", columnList = "status"),
    @Index(name = "idx_payment_created_at", columnList = "createdAt"),
    @Index(name = "idx_payment_channel", columnList = "channel")
})
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 36)
    private String idempotencyKey;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "payer_document_number", nullable = false, length = 20)
    private String payerDocumentNumber;

    @Column(name = "payer_document_type", nullable = false, length = 10)
    private String payerDocumentType;

    @Column(name = "payer_name", nullable = false, length = 200)
    private String payerName;

    @Column(name = "payee_document_number", nullable = false, length = 20)
    private String payeeDocumentNumber;

    @Column(name = "payee_document_type", nullable = false, length = 10)
    private String payeeDocumentType;

    @Column(name = "payee_name", nullable = false, length = 200)
    private String payeeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatusEntity status;

    @Column(name = "channel", nullable = false, length = 20)
    private String channel;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "external_reference", length = 100)
    private String externalReference;

    @Column(name = "failure_reason", length = 1000)
    private String failureReason;

    @Column(name = "processed_at")
    private Instant processedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    public PaymentEntity() {
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = PaymentStatusEntity.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayerDocumentNumber() {
        return payerDocumentNumber;
    }

    public void setPayerDocumentNumber(String payerDocumentNumber) {
        this.payerDocumentNumber = payerDocumentNumber;
    }

    public String getPayerDocumentType() {
        return payerDocumentType;
    }

    public void setPayerDocumentType(String payerDocumentType) {
        this.payerDocumentType = payerDocumentType;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayeeDocumentNumber() {
        return payeeDocumentNumber;
    }

    public void setPayeeDocumentNumber(String payeeDocumentNumber) {
        this.payeeDocumentNumber = payeeDocumentNumber;
    }

    public String getPayeeDocumentType() {
        return payeeDocumentType;
    }

    public void setPayeeDocumentType(String payeeDocumentType) {
        this.payeeDocumentType = payeeDocumentType;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public PaymentStatusEntity getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusEntity status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void markAsProcessed() {
        this.status = PaymentStatusEntity.PROCESSED;
        this.processedAt = Instant.now();
    }

    public void markAsFailed(String reason) {
        this.status = PaymentStatusEntity.FAILED;
        this.failureReason = reason;
    }

    public boolean isProcessed() {
        return this.status == PaymentStatusEntity.PROCESSED;
    }

    public boolean isPending() {
        return this.status == PaymentStatusEntity.PENDING;
    }

    public boolean isFailed() {
        return this.status == PaymentStatusEntity.FAILED;
    }

    /**
     * Enum interno para el estado de persistencia.
     * Mantiene congruencia con PaymentStatus del dominio.
     */
    public enum PaymentStatusEntity {
        PENDING("Pago en proceso, esperando confirmacion"),
        PROCESSED("Pago procesado exitosamente"),
        FAILED("Pago fallido, no se pudo procesar");

        private final String description;

        PaymentStatusEntity(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/persistence/entities/IdempotencyEntity.java ===
package com.pragma.payments.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "idempotency_keys", indexes = {
    @Index(name = "idx_idempotency_key", columnList = "idempotencyKey", unique = true),
    @Index(name = "idx_idempotency_expires", columnList = "expiresAt")
})
public class IdempotencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 36)
    private String idempotencyKey;

    @Column(name = "payment_id", length = 36)
    private String paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private IdempotencyStatus status;

    @Column(name = "request_hash", length = 64)
    private String requestHash;

    @Column(name = "request_payload", columnDefinition = "TEXT")
    private String requestPayload;

    @Column(name = "response_payload", columnDefinition = "TEXT")
    private String responsePayload;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "retry_count")
    private Integer retryCount = 0;

    @Column(name = "last_retry_at")
    private Instant lastRetryAt;

    public IdempotencyEntity() {
    }

    public IdempotencyEntity(String idempotencyKey, String requestHash, String requestPayload, Instant expiresAt) {
        this.idempotencyKey = idempotencyKey;
        this.requestHash = requestHash;
        this.requestPayload = requestPayload;
        this.status = IdempotencyStatus.PENDING;
        this.createdAt = Instant.now();
        this.expiresAt = expiresAt;
        this.retryCount = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public IdempotencyStatus getStatus() {
        return status;
    }

    public void setStatus(IdempotencyStatus status) {
        this.status = status;
    }

    public String getRequestHash() {
        return requestHash;
    }

    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public String getResponsePayload() {
        return responsePayload;
    }

    public void setResponsePayload(String responsePayload) {
        this.responsePayload = responsePayload;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Instant getLastRetryAt() {
        return lastRetryAt;
    }

    public void setLastRetryAt(Instant lastRetryAt) {
        this.lastRetryAt = lastRetryAt;
    }

    public void incrementRetryCount() {
        this.retryCount = (this.retryCount == null ? 0 : this.retryCount) + 1;
        this.lastRetryAt = Instant.now();
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresAt);
    }

    public enum IdempotencyStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        EXPIRED
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/persistence/repositories/PaymentJpaRepository.java ===
package com.pragma.payments.infrastructure.persistence.repositories;

import com.pragma.payments.infrastructure.persistence.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {

    Optional<PaymentEntity> findByExternalId(String externalId);

    List<PaymentEntity> findByStatus(PaymentEntity.PaymentEntityStatus status);

    List<PaymentEntity> findByChannel(String channel);

    @Query("SELECT p FROM PaymentEntity p WHERE p.status = :status AND p.createdAt < :before")
    List<PaymentEntity> findStalePayments(
            @Param("status") PaymentEntity.PaymentEntityStatus status,
            @Param("before") Instant before
    );

    @Query("SELECT p FROM PaymentEntity p WHERE p.status = :status AND p.createdAt BETWEEN :from AND :to")
    List<PaymentEntity> findByStatusAndDateRange(
            @Param("status") PaymentEntity.PaymentEntityStatus status,
            @Param("from") Instant from,
            @Param("to") Instant to
    );

    @Query("SELECT COUNT(p) FROM PaymentEntity p WHERE p.status = :status")
    Long countByStatus(@Param("status") PaymentEntity.PaymentEntityStatus status);

    boolean existsByExternalId(String externalId);

    @Query("SELECT p FROM PaymentEntity p WHERE p.idempotencyKey = :idempotencyKey")
    Optional<PaymentEntity> findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/persistence/repositories/IdempotencyJpaRepository.java ===
package com.pragma.payments.infrastructure.persistence.repositories;

import com.pragma.payments.infrastructure.persistence.entities.IdempotencyEntity;
import com.pragma.payments.infrastructure.persistence.entities.IdempotencyEntity.IdempotencyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface IdempotencyJpaRepository extends JpaRepository<IdempotencyEntity, java.util.UUID> {

    Optional<IdempotencyEntity> findByIdempotencyKey(String idempotencyKey);

    boolean existsByIdempotencyKey(String idempotencyKey);

    List<IdempotencyEntity> findByStatus(IdempotencyStatus status);

    @Query("SELECT i FROM IdempotencyEntity i WHERE i.status = :status AND i.expiresAt < :now")
    List<IdempotencyEntity> findExpiredWithStatus(
            @Param("status") IdempotencyStatus status,
            @Param("now") Instant now
    );

    @Query("SELECT i FROM IdempotencyEntity i WHERE i.status = 'PENDING' AND i.expiresAt > :now ORDER BY i.createdAt ASC")
    List<IdempotencyEntity> findPendingKeysNotYetExpired(@Param("now") Instant now);

    @Query("SELECT i FROM IdempotencyEntity i WHERE i.status = 'PROCESSING' AND i.lastRetryAt < :threshold")
    List<IdempotencyEntity> findStaleProcessingKeys(@Param("threshold") Instant threshold);

    @Modifying
    @Query("UPDATE IdempotencyEntity i SET i.status = 'EXPIRED' WHERE i.status = 'PENDING' AND i.expiresAt < :now")
    int markExpiredPendingKeys(@Param("now") Instant now);

    @Modifying
    @Query("UPDATE IdempotencyEntity i SET i.status = 'PENDING', i.retryCount = 0 WHERE i.id = :id")
    int resetToPending(@Param("id") java.util.UUID id);

    @Query("SELECT COUNT(i) FROM IdempotencyEntity i WHERE i.status = :status")
    Long countByStatus(@Param("status") IdempotencyStatus status);

    @Query("SELECT i FROM IdempotencyEntity i WHERE i.requestHash = :hash AND i.status = 'COMPLETED' AND i.expiresAt > :now")
    Optional<IdempotencyEntity> findCompletedByRequestHash(
            @Param("hash") String requestHash,
            @Param("now") Instant now
    );
}

// === ARCHIVO: src/main/java/com/pragma/payments/application/ProcessPaymentService.java ===
package com.pragma.payments.application;

import com.pragma.payments.domain.exception.DuplicatePaymentException;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import com.pragma.payments.domain.ports.in.ProcessPaymentUseCase;
import com.pragma.payments.domain.ports.out.IdempotencyRepositoryPort;
import com.pragma.payments.domain.ports.out.PaymentRepositoryPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessPaymentService implements ProcessPaymentUseCase {

    private final PaymentRepositoryPort paymentRepository;
    private final IdempotencyRepositoryPort idempotencyRepository;

    @Override
    @CircuitBreaker(name = "paymentCircuitBreaker", fallbackMethod = "fallbackProcessPayment")
    @Retry(name = "paymentRetry")
    public Mono<PaymentStatus> processPayment(PaymentRequest paymentRequest) {
        return Mono.fromCallable(() -> {
                    paymentRequest.validate();
                    return paymentRequest.idempotencyKey();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(this::checkAndStoreIdempotency)
                .flatMap(this::executePaymentProcessing)
                .doOnSuccess(status -> log.info("Pago procesado exitosamente con estado: {}", status))
                .doOnError(error -> log.error("Error al procesar pago: {}", error.getMessage()));
    }

    @Override
    public Mono<PaymentStatus> checkPaymentStatus(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.debug("Consultando estado del pago para clave de idempotencia: {}", idempotencyKey);
                    return idempotencyKey;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(idempotencyRepository::findByIdempotencyKey)
                .flatMap(idempotencyEntity -> {
                    if (idempotencyEntity == null) {
                        return Mono.error(new PaymentProcessingException(
                            "No se encontró registro para la clave de idempotencia: " + idempotencyKey));
                    }
                    return paymentRepository.findByIdempotencyKey(idempotencyKey)
                            .map(paymentEntity -> PaymentStatus.valueOf(paymentEntity.getStatus()))
                            .switchIfEmpty(Mono.just(PaymentStatus.PENDING));
                });
    }

    private Mono<PaymentStatus> checkAndStoreIdempotency(UUID idempotencyKey) {
        return idempotencyRepository.findByIdempotencyKey(idempotencyKey)
                .flatMap(existing -> {
                    log.warn("Clave de idempotencia duplicada detectada: {}", idempotencyKey);
                    return Mono.error(new DuplicatePaymentException(
                        "Ya existe un pago con esta clave de idempotencia: " + idempotencyKey));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Nueva clave de idempotencia, creando registro: {}", idempotencyKey);
                    return idempotencyRepository.save(idempotencyKey, PaymentStatus.PENDING.name(),
                        Instant.now().toString());
                }))
                .thenReturn(PaymentStatus.PENDING);
    }

    private Mono<PaymentStatus> executePaymentProcessing(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.info("Iniciando procesamiento del pago para idempotencyKey: {}", idempotencyKey);
                    BigDecimal amount = BigDecimal.valueOf(100.00);
                    boolean processed = simulateExternalPaymentGateway(amount);
                    if (!processed) {
                        throw new PaymentProcessingException("El gateway de pago rechazó la transacción");
                    }
                    PaymentStatus finalStatus = PaymentStatus.PROCESSED;
                    idempotencyRepository.updateStatus(idempotencyKey, finalStatus.name());
                    paymentRepository.savePayment(idempotencyKey, amount, finalStatus.name(),
                        "PAYMENT_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                    return finalStatus;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(PaymentProcessingException.class, error -> {
                    log.error("Error en procesamiento de pago, actualizando estado a FAILED: {}", error.getMessage());
                    return idempotencyRepository.updateStatus(idempotencyKey, PaymentStatus.FAILED.name())
                            .then(Mono.error(error));
                });
    }

    private boolean simulateExternalPaymentGateway(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    private Mono<PaymentStatus> fallbackProcessPayment(PaymentRequest paymentRequest, Throwable throwable) {
        log.error("Circuit breaker activado para paymentRequest: {}. Error: {}", 
            paymentRequest.idempotencyKey(), throwable.getMessage());
        return Mono.just(PaymentStatus.FAILED);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapters/PaymentJpaAdapter.java ===
package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.model.PaymentStatus;
import com.pragma.payments.domain.ports.out.PaymentRepositoryPort;
import com.pragma.payments.infrastructure.persistence.entities.PaymentEntity;
import com.pragma.payments.infrastructure.persistence.repositories.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentJpaAdapter implements PaymentRepositoryPort {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Mono<PaymentStatus> savePayment(UUID idempotencyKey, BigDecimal amount, 
                                           String status, String externalReference) {
        return Mono.fromCallable(() -> {
                    log.debug("Persistiendo pago con idempotencyKey: {}", idempotencyKey);
                    PaymentEntity entity = PaymentEntity.builder()
                            .idempotencyKey(idempotencyKey.toString())
                            .amount(amount)
                            .status(status)
                            .externalReference(externalReference)
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();
                    PaymentEntity saved = paymentJpaRepository.save(entity);
                    log.info("Pago persistido exitosamente con ID: {}", saved.getId());
                    return PaymentStatus.valueOf(saved.getStatus());
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(error -> {
                    log.error("Error al persistir pago: {}", error.getMessage());
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<Optional<PaymentEntity>> findByIdempotencyKey(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.debug("Buscando pago por idempotencyKey: {}", idempotencyKey);
                    Optional<PaymentEntity> result = paymentJpaRepository
                            .findByIdempotencyKey(idempotencyKey.toString());
                    if (result.isPresent()) {
                        log.info("Pago encontrado para idempotencyKey: {}", idempotencyKey);
                    } else {
                        log.debug("No se encontró pago para idempotencyKey: {}", idempotencyKey);
                    }
                    return result;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<PaymentStatus> updatePaymentStatus(UUID idempotencyKey, String status) {
        return Mono.fromCallable(() -> {
                    log.debug("Actualizando estado del pago para idempotencyKey: {} a estado: {}", 
                        idempotencyKey, status);
                    Optional<PaymentEntity> existingOpt = paymentJpaRepository
                            .findByIdempotencyKey(idempotencyKey.toString());
                    if (existingOpt.isEmpty()) {
                        throw new IllegalArgumentException(
                            "No se puede actualizar: pago no encontrado para key: " + idempotencyKey);
                    }
                    PaymentEntity existing = existingOpt.get();
                    existing.setStatus(status);
                    existing.setUpdatedAt(Instant.now());
                    PaymentEntity updated = paymentJpaRepository.save(existing);
                    log.info("Estado del pago actualizado a: {} para idempotencyKey: {}", 
                        status, idempotencyKey);
                    return PaymentStatus.valueOf(updated.getStatus());
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(error -> {
                    log.error("Error al actualizar estado del pago: {}", error.getMessage());
                    return Mono.error(error);
                });
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java ===
package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.ports.out.IdempotencyRepositoryPort;
import com.pragma.payments.infrastructure.persistence.entities.IdempotencyEntity;
import com.pragma.payments.infrastructure.persistence.repositories.IdempotencyJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class IdempotencyJpaAdapter implements IdempotencyRepositoryPort {

    private final IdempotencyJpaRepository idempotencyJpaRepository;

    @Override
    public Mono<IdempotencyEntity> save(UUID idempotencyKey, String status, String createdAt) {
        return Mono.fromCallable(() -> {
                    log.debug("Guardando registro de idempotencia con key: {}", idempotencyKey);
                    IdempotencyEntity entity = IdempotencyEntity.builder()
                            .idempotencyKey(idempotencyKey.toString())
                            .status(status)
                            .createdAt(createdAt)
                            .processedAt(Instant.now().toString())
                            .build();
                    IdempotencyEntity saved = idempotencyJpaRepository.save(entity);
                    log.info("Registro de idempotencia guardado exitosamente: {}", saved.getId());
                    return saved;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(error -> {
                    log.error("Error al guardar registro de idempotencia: {}", error.getMessage());
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<Optional<IdempotencyEntity>> findByIdempotencyKey(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.debug("Buscando registro de idempotencia por key: {}", idempotencyKey);
                    Optional<IdempotencyEntity> result = idempotencyJpaRepository
                            .findByIdempotencyKey(idempotencyKey.toString());
                    if (result.isPresent()) {
                        log.info("Registro de idempotencia encontrado para key: {}", idempotencyKey);
                    } else {
                        log.debug("No existe registro de idempotencia para key: {}", idempotencyKey);
                    }
                    return result;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<IdempotencyEntity> updateStatus(UUID idempotencyKey, String status) {
        return Mono.fromCallable(() -> {
                    log.debug("Actualizando estado de idempotencia para key: {} a estado: {}", 
                        idempotencyKey, status);
                    Optional<IdempotencyEntity> existingOpt = idempotencyJpaRepository
                            .findByIdempotencyKey(idempotencyKey.toString());
                    if (existingOpt.isEmpty()) {
                        throw new IllegalArgumentException(
                            "No se puede actualizar: registro de idempotencia no encontrado para key: " 
                            + idempotencyKey);
                    }
                    IdempotencyEntity existing = existingOpt.get();
                    existing.setStatus(status);
                    existing.setProcessedAt(Instant.now().toString());
                    IdempotencyEntity updated = idempotencyJpaRepository.save(existing);
                    log.info("Estado de idempotencia actualizado a: {} para key: {}", status, idempotencyKey);
                    return updated;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(error -> {
                    log.error("Error al actualizar estado de idempotencia: {}", error.getMessage());
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<Boolean> existsByIdempotencyKey(UUID idempotencyKey) {
        return Mono.fromCallable(() -> {
                    log.debug("Verificando existencia de registro de idempotencia para key: {}", idempotencyKey);
                    boolean exists = idempotencyJpaRepository
                            .existsByIdempotencyKey(idempotencyKey.toString());
                    log.debug("Existencia de registro para key {}: {}", idempotencyKey, exists);
                    return exists;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java ===
package com.pragma.payments.infrastructure.controllers;

import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.ports.in.ProcessPaymentUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final ProcessPaymentUseCase processPaymentUseCase;

    public PaymentController(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @PostMapping("/mobile")
    public Mono<ResponseEntity<Map<String, Object>>> processMobilePayment(
            @Valid @RequestBody PaymentRequestDto request) {
        log.info("Recibida solicitud de pago desde canal móvil, idempotencyKey: {}", request.idempotencyKey());
        return processPayment(request.idempotencyKey(), request);
    }

    @PostMapping("/web")
    public Mono<ResponseEntity<Map<String, Object>>> processWebPayment(
            @Valid @RequestBody PaymentRequestDto request) {
        log.info("Recibida solicitud de pago desde canal web, idempotencyKey: {}", request.idempotencyKey());
        return processPayment(request.idempotencyKey(), request);
    }

    @PostMapping("/partner")
    public Mono<ResponseEntity<Map<String, Object>>> processPartnerPayment(
            @Valid @RequestBody PaymentRequestDto request) {
        log.info("Recibida solicitud de pago desde canal socio, idempotencyKey: {}", request.idempotencyKey());
        return processPayment(request.idempotencyKey(), request);
    }

    @GetMapping("/status/{idempotencyKey}")
    public Mono<ResponseEntity<Map<String, Object>>> getPaymentStatus(
            @PathVariable UUID idempotencyKey) {
        log.info("Consultando estado del pago con idempotencyKey: {}", idempotencyKey);
        return processPaymentUseCase.checkPaymentStatus(idempotencyKey)
                .map(status -> ResponseEntity.ok(Map.of(
                        "idempotencyKey", idempotencyKey.toString(),
                        "status", status.name(),
                        "description", status.getDescription()
                )))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private Mono<ResponseEntity<Map<String, Object>>> processPayment(UUID idempotencyKey, PaymentRequestDto dto) {
        PaymentRequest paymentRequest = new PaymentRequest(
                idempotencyKey,
                dto.amount(),
                dto.currency(),
                dto.payerId(),
                dto.payeeId(),
                dto.description()
        );
        paymentRequest.validate();

        return processPaymentUseCase.processPayment(paymentRequest)
                .map(status -> ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                        "idempotencyKey", idempotencyKey.toString(),
                        "status", status.name(),
                        "description", status.getDescription(),
                        "message", "Pago recibido y en procesamiento"
                )));
    }

    public record PaymentRequestDto(
            UUID idempotencyKey,
            java.math.BigDecimal amount,
            String currency,
            String payerId,
            String payeeId,
            String description
    ) {}
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java ===
package com.pragma.payments.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofMillis(500))
                .build();
        return RateLimiterRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(1))
                .build();
        return RetryRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java ===
package com.pragma.payments.infrastructure.config;

import com.pragma.payments.domain.exception.BackpressureException;
import com.pragma.payments.domain.exception.DuplicatePaymentException;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicatePaymentException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleDuplicatePaymentException(DuplicatePaymentException ex) {
        log.warn("Pago duplicado detectado: {}", ex.getMessage());
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(body));
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handlePaymentProcessingException(PaymentProcessingException ex) {
        log.error("Error procesando pago: {}", ex.getMessage(), ex);
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "PAYMENT_PROCESSING_ERROR",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body));
    }

    @ExceptionHandler(BackpressureException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleBackpressureException(BackpressureException ex) {
        log.warn("Excepción de backpressure: {}", ex.getMessage());
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "SERVICE_UNAVAILABLE",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Error de validación: {}", ex.getMessage());
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                errors
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Argumento ilegal: {}", ex.getMessage());
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        Map<String, Object> body = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "Ocurrió un error inesperado al procesar la solicitud"
        );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }

    private Map<String, Object> buildErrorResponse(int status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status);
        body.put("error", error);
        body.put("message", message);
        return body;
    }
}

// === ARCHIVO: src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java ===
package com.pragma.payments.application;

import com.pragma.payments.domain.exception.DuplicatePaymentException;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import com.pragma.payments.domain.ports.out.IdempotencyRepositoryPort;
import com.pragma.payments.domain.ports.out.PaymentRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProcessPaymentServiceTest - Pruebas unitarias del caso de uso de procesamiento de pagos")
class ProcessPaymentServiceTest {

    @Mock
    private PaymentRepositoryPort paymentRepository;

    @Mock
    private IdempotencyRepositoryPort idempotencyRepository;

    @InjectMocks
    private ProcessPaymentService processPaymentService;

    private PaymentRequest paymentRequest;
    private UUID idempotencyKey;

    @BeforeEach
    void setUp() {
        idempotencyKey = UUID.randomUUID();
        paymentRequest = new PaymentRequest(
            idempotencyKey,
            "ACC-12345",
            new BigDecimal("1000.00"),
            "USD",
            "PAYMENT",
            Instant.now()
        );
    }

    @Nested
    @DisplayName("Escenarios de procesamiento exitoso")
    class SuccessfulProcessing {

        @Test
        @DisplayName("Debe procesar pago exitosamente cuando no existe clave de idempotencia")
        void shouldProcessPaymentSuccessfully_WhenNoIdempotencyKeyExists() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.empty());
            when(idempotencyRepository.save(any())).thenReturn(Mono.empty());

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectNext(PaymentStatus.PROCESSED)
                .verifyComplete();

            verify(paymentRepository).save(any());
            verify(idempotencyRepository).save(any());
        }

        @Test
        @DisplayName("Debe retornar estado PENDING cuando el procesamiento está en curso")
        void shouldReturnPendingStatus_WhenPaymentIsInProgress() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(true));
            when(idempotencyRepository.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.just(PaymentStatus.PENDING));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectNext(PaymentStatus.PENDING)
                .verifyComplete();

            verify(paymentRepository, never()).save(any());
        }

        @Test
        @DisplayName("Debe retornar estado PROCESSED cuando el pago ya fue procesado")
        void shouldReturnProcessedStatus_WhenPaymentAlreadyProcessed() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(true));
            when(idempotencyRepository.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectNext(PaymentStatus.PROCESSED)
                .verifyComplete();

            verify(paymentRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Escenarios de manejo de errores")
    class ErrorHandling {

        @Test
        @DisplayName("Debe lanzar DuplicatePaymentException cuando la clave de idempotencia ya existe con estado FAILED")
        void shouldThrowDuplicatePaymentException_WhenIdempotencyKeyExistsWithFailedStatus() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(true));
            when(idempotencyRepository.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.just(PaymentStatus.FAILED));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectError(DuplicatePaymentException.class)
                .verify();
        }

        @Test
        @DisplayName("Debe lanzar PaymentProcessingException cuando falla el repositorio de pagos")
        void shouldThrowPaymentProcessingException_WhenPaymentRepositoryFails() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectError(PaymentProcessingException.class)
                .verify();
        }

        @Test
        @DisplayName("Debe lanzar PaymentProcessingException cuando falla el repositorio de idempotencia")
        void shouldThrowPaymentProcessingException_WhenIdempotencyRepositoryFails() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.empty());
            when(idempotencyRepository.save(any()))
                .thenReturn(Mono.error(new RuntimeException("Idempotency save failed")));

            StepVerifier.create(processPaymentService.processPayment(paymentRequest))
                .expectError(PaymentProcessingException.class)
                .verify();
        }
    }

    @Nested
    @DisplayName("Escenarios de verificación de estado")
    class StatusCheck {

        @Test
        @DisplayName("Debe retornar el estado del pago existente")
        void shouldReturnExistingPaymentStatus() {
            UUID existingKey = UUID.randomUUID();
            when(idempotencyRepository.findByIdempotencyKey(existingKey))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            StepVerifier.create(processPaymentService.checkPaymentStatus(existingKey))
                .expectNext(PaymentStatus.PROCESSED)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe retornar PENDING cuando no existe la clave de idempotencia")
        void shouldReturnPending_WhenIdempotencyKeyDoesNotExist() {
            UUID nonExistingKey = UUID.randomUUID();
            when(idempotencyRepository.findByIdempotencyKey(nonExistingKey))
                .thenReturn(Mono.empty());

            StepVerifier.create(processPaymentService.checkPaymentStatus(nonExistingKey))
                .expectNext(PaymentStatus.PENDING)
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Validación de comportamiento")
    class BehaviorValidation {

        @Test
        @DisplayName("Debe invocar el repositorio de idempotencia antes de guardar el pago")
        void shouldCheckIdempotencyBeforeSavingPayment() {
            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.empty());
            when(idempotencyRepository.save(any())).thenReturn(Mono.empty());

            processPaymentService.processPayment(paymentRequest).block();

            var inOrder = org.mockito.Mockito.inOrder(idempotencyRepository, paymentRepository);
            inOrder.verify(idempotencyRepository).existsByIdempotencyKey(idempotencyKey);
            inOrder.verify(paymentRepository).save(any());
        }

        @Test
        @DisplayName("Debe guardar la clave de idempotencia con el estado correcto")
        void shouldSaveIdempotencyKeyWithCorrectStatus() {
            ArgumentCaptor<com.pragma.payments.domain.model.IdempotencyRecord> captor =
                ArgumentCaptor.forClass(com.pragma.payments.domain.model.IdempotencyRecord.class);

            when(idempotencyRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(Mono.just(false));
            when(paymentRepository.save(any())).thenReturn(Mono.empty());
            when(idempotencyRepository.save(captor.capture())).thenReturn(Mono.empty());

            processPaymentService.processPayment(paymentRequest).block();

            var savedRecord = captor.getValue();
            assertThat(savedRecord.idempotencyKey()).isEqualTo(idempotencyKey);
            assertThat(savedRecord.status()).isEqualTo(PaymentStatus.PROCESSED);
        }
    }
}

// === ARCHIVO: src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java ===
package com.pragma.payments.infrastructure.controllers;

import com.pragma.payments.domain.exception.BackpressureException;
import com.pragma.payments.domain.exception.DuplicatePaymentException;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.PaymentRequest;
import com.pragma.payments.domain.model.PaymentStatus;
import com.pragma.payments.domain.ports.in.ProcessPaymentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentControllerTest - Pruebas de integración del controlador REST")
class PaymentControllerTest {

    @Mock
    private ProcessPaymentUseCase processPaymentUseCase;

    @InjectMocks
    private PaymentController paymentController;

    private WebTestClient webTestClient;
    private PaymentRequestDto paymentRequestDto;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(paymentController).build();
        paymentRequestDto = new PaymentRequestDto(
            UUID.randomUUID().toString(),
            "ACC-12345",
            "1000.00",
            "USD",
            "PAYMENT"
        );
    }

    @Nested
    @DisplayName("Escenarios de procesamiento exitoso")
    class SuccessfulProcessing {

        @Test
        @DisplayName("Debe retornar 201 Created cuando el pago se procesa exitosamente")
        void shouldReturn201Created_WhenPaymentProcessedSuccessfully() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.status").isEqualTo("PROCESSED")
                .jsonPath("$.message").isEqualTo("Pago procesado exitosamente");
        }

        @Test
        @DisplayName("Debe retornar 202 Accepted cuando el pago está en proceso")
        void shouldReturn202Accepted_WhenPaymentIsPending() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(PaymentStatus.PENDING));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .jsonPath("$.status").isEqualTo("PENDING")
                .jsonPath("$.message").isEqualTo("Pago en proceso, esperando confirmación");
        }

        @Test
        @DisplayName("Debe retornar 200 Ok cuando se consulta el estado de un pago existente")
        void shouldReturn200Ok_WhenCheckingExistingPaymentStatus() {
            UUID existingKey = UUID.randomUUID();
            when(processPaymentUseCase.checkPaymentStatus(existingKey))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            webTestClient.get()
                .uri("/api/v1/payments/{idempotencyKey}", existingKey)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("PROCESSED");
        }
    }

    @Nested
    @DisplayName("Escenarios de manejo de errores")
    class ErrorHandling {

        @Test
        @DisplayName("Debe retornar 409 Conflict cuando se intenta procesar un pago duplicado")
        void shouldReturn409Conflict_WhenDuplicatePaymentAttempt() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new DuplicatePaymentException("Pago duplicado")));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.error").isEqualTo("CONFLICT")
                .jsonPath("$.message").isEqualTo("Pago duplicado");
        }

        @Test
        @DisplayName("Debe retornar 503 Service Unavailable cuando hay backpressure")
        void shouldReturn503ServiceUnavailable_WhenBackpressureOccurs() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new BackpressureException("Capacidad máxima alcanzada")));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isServiceUnavailable()
                .expectBody()
                .jsonPath("$.error").isEqualTo("SERVICE_UNAVAILABLE")
                .jsonPath("$.message").isEqualTo("Capacidad máxima alcanzada");
        }

        @Test
        @DisplayName("Debe retornar 500 Internal Server Error cuando falla el procesamiento")
        void shouldReturn500InternalServerError_WhenProcessingFails() {
            PaymentRequest request = toDomain(paymentRequestDto);
            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new PaymentProcessingException("Error interno")));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.error").isEqualTo("INTERNAL_SERVER_ERROR");
        }
    }

    @Nested
    @DisplayName("Escenarios de validación de entrada")
    class InputValidation {

        @Test
        @DisplayName("Debe retornar 400 Bad Request cuando el idempotencyKey está vacío")
        void shouldReturn400BadRequest_WhenIdempotencyKeyIsEmpty() {
            paymentRequestDto = new PaymentRequestDto(
                "",
                "ACC-12345",
                "1000.00",
                "USD",
                "PAYMENT"
            );

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isBadRequest();
        }

        @Test
        @DisplayName("Debe retornar 400 Bad Request cuando el accountId está vacío")
        void shouldReturn400BadRequest_WhenAccountIdIsEmpty() {
            paymentRequestDto = new PaymentRequestDto(
                UUID.randomUUID().toString(),
                "",
                "1000.00",
                "USD",
                "PAYMENT"
            );

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isBadRequest();
        }

        @Test
        @DisplayName("Debe retornar 400 Bad Request cuando el amount es inválido")
        void shouldReturn400BadRequest_WhenAmountIsInvalid() {
            paymentRequestDto = new PaymentRequestDto(
                UUID.randomUUID().toString(),
                "ACC-12345",
                "-100.00",
                "USD",
                "PAYMENT"
            );

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isBadRequest();
        }

        @Test
        @DisplayName("Debe retornar 400 Bad Request cuando la moneda está vacía")
        void shouldReturn400BadRequest_WhenCurrencyIsEmpty() {
            paymentRequestDto = new PaymentRequestDto(
                UUID.randomUUID().toString(),
                "ACC-12345",
                "1000.00",
                "",
                "PAYMENT"
            );

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isBadRequest();
        }
    }

    @Nested
    @DisplayName("Escenarios de idempotencia en controlador")
    class IdempotencyScenarios {

        @Test
        @DisplayName("Debe propagar la clave de idempotencia al caso de uso")
        void shouldPropagateIdempotencyKeyToUseCase() {
            UUID specificKey = UUID.randomUUID();
            paymentRequestDto = new PaymentRequestDto(
                specificKey.toString(),
                "ACC-12345",
                "1000.00",
                "USD",
                "PAYMENT"
            );

            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isCreated();
        }

        @Test
        @DisplayName("Debe manejar correctamente reintentos con la misma clave")
        void shouldHandleRetriesWithSameKey() {
            UUID key = UUID.randomUUID();
            paymentRequestDto = new PaymentRequestDto(
                key.toString(),
                "ACC-12345",
                "1000.00",
                "USD",
                "PAYMENT"
            );

            when(processPaymentUseCase.processPayment(any(PaymentRequest.class)))
                .thenReturn(Mono.just(PaymentStatus.PROCESSED));

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isCreated();

            webTestClient.post()
                .uri("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(paymentRequestDto))
                .exchange()
                .expectStatus().isCreated();
        }
    }

    private PaymentRequest toDomain(PaymentRequestDto dto) {
        return new PaymentRequest(
            UUID.fromString(dto.idempotencyKey()),
            dto.accountId(),
            new BigDecimal(dto.amount()),
            dto.currency(),
            dto.paymentType(),
            Instant.now()
        );
    }

    record PaymentRequestDto(
        String idempotencyKey,
        String accountId,
        String amount,
        String currency,
        String paymentType
    ) {}
}
```
