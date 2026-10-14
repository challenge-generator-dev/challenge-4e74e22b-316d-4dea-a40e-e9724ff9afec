# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de idempotencia en el procesamiento de pagos**.

| | |
|---|---|
| Tema | Idempotencia en pagos |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con patrón CQRS para comandos idempotentes |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.postgresql:postgresql 42.7.3
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.springframework.boot:spring-boot-starter-actuator n/a
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.5.0
- org.projectlombok:lombok 1.18.30
- org.springframework.boot:spring-boot-starter-test n/a
- org.junit.jupiter:junit-jupiter-api n/a
- org.mockito:mockito-core 5.11.0

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Canal de entrada operativo persistiendo solicitudes con idempotencia**: Canal de entrada que acepta y persiste solicitudes de pago de manera idempotente.
- **Fase 2 — Consolidación de los tres canales sin pérdida ante backpressure de uno**: Sistema consolidado que acepta solicitudes de los tres canales y maneja backpressure sin pérdida de datos.
- **Fase 3 — Recuperación automática ante caída del consumidor downstream sin reprocesar emitidas**: Mecanismo de recuperación automática que no reprocesa solicitudes ya emitidas.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (38)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/payments/domain/ports/in/ProcessPaymentUseCase.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/domain/ports/out/PaymentRepositoryPort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/domain/ports/out/IdempotencyRepositoryPort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/PaymentJpaAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/PaymentJpaAdapter.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `PaymentRequest.idempotencyKey`
      Se invoca `idempotencyKey` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `PaymentRepositoryPort.findByIdempotencyKey`
      Se invoca `findByIdempotencyKey` sobre `PaymentRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `IdempotencyRepositoryPort.findByIdempotencyKey`
      Se invoca `findByIdempotencyKey` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `IdempotencyRepositoryPort.save`
      Se invoca `save` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `IdempotencyRepositoryPort.updateStatus`
      Se invoca `updateStatus` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/ProcessPaymentService.java` — `PaymentStatus.name`
      Se invoca `name` sobre `PaymentStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/PaymentJpaAdapter.java` — `PaymentJpaRepository.save`
      Se invoca `save` sobre `PaymentJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java` — `IdempotencyJpaRepository.save`
      Se invoca `save` sobre `IdempotencyJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java` — `IdempotencyEntity.setProcessedAt`
      Se invoca `setProcessedAt` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.idempotencyKey`
      Se invoca `idempotencyKey` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.amount`
      Se invoca `amount` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.currency`
      Se invoca `currency` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.payerId`
      Se invoca `payerId` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.payeeId`
      Se invoca `payeeId` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequestDto.description`
      Se invoca `description` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java` — `PaymentRepositoryPort.save`
      Se invoca `save` sobre `PaymentRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java` — `IdempotencyRepositoryPort.save`
      Se invoca `save` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java` — `IdempotencyRepositoryPort.findByIdempotencyKey`
      Se invoca `findByIdempotencyKey` sobre `IdempotencyRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.idempotencyKey`
      Se invoca `idempotencyKey` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.accountId`
      Se invoca `accountId` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.amount`
      Se invoca `amount` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.currency`
      Se invoca `currency` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentRequestDto.paymentType`
      Se invoca `paymentType` sobre `PaymentRequestDto`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (23)

- `pom.xml`
- `src/main/java/com/pragma/payments/Application.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/payments/domain/model/PaymentRequest.java`
- `src/main/java/com/pragma/payments/domain/model/PaymentStatus.java`
- `src/main/java/com/pragma/payments/domain/ports/in/ProcessPaymentUseCase.java`
- `src/main/java/com/pragma/payments/domain/ports/out/PaymentRepositoryPort.java`
- `src/main/java/com/pragma/payments/domain/ports/out/IdempotencyRepositoryPort.java`
- `src/main/java/com/pragma/payments/domain/exception/DuplicatePaymentException.java`
- `src/main/java/com/pragma/payments/domain/exception/PaymentProcessingException.java`
- `src/main/java/com/pragma/payments/domain/exception/BackpressureException.java`
- `src/main/java/com/pragma/payments/infrastructure/persistence/entities/PaymentEntity.java`
- `src/main/java/com/pragma/payments/infrastructure/persistence/entities/IdempotencyEntity.java`
- `src/main/java/com/pragma/payments/infrastructure/persistence/repositories/PaymentJpaRepository.java`
- `src/main/java/com/pragma/payments/infrastructure/persistence/repositories/IdempotencyJpaRepository.java`
- `src/main/java/com/pragma/payments/application/ProcessPaymentService.java`
- `src/main/java/com/pragma/payments/infrastructure/adapters/PaymentJpaAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/adapters/IdempotencyJpaAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java`
- `src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java`
- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java`
- `src/test/java/com/pragma/payments/application/ProcessPaymentServiceTest.java`
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/payments`
- `src/main/java/com/pragma/payments/application`
- `src/main/java/com/pragma/payments/domain`
- `src/main/java/com/pragma/payments/domain/model`
- `src/main/java/com/pragma/payments/domain/ports`
- `src/main/java/com/pragma/payments/domain/exception`
- `src/main/java/com/pragma/payments/infrastructure`
- `src/main/java/com/pragma/payments/infrastructure/adapters`
- `src/main/java/com/pragma/payments/infrastructure/config`
- `src/main/java/com/pragma/payments/infrastructure/controllers`
- `src/main/java/com/pragma/payments/infrastructure/persistence`
- `src/main/java/com/pragma/payments/infrastructure/persistence/entities`
- `src/main/java/com/pragma/payments/infrastructure/persistence/repositories`
- `src/main/resources`
- `src/test/java/com/pragma/payments`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con patrón CQRS para comandos idempotentes**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2
- Brecha que el reto ataca: Comprueba manejo de errores y reintentos.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
