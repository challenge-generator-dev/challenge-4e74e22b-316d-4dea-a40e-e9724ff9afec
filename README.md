# Implementación de idempotencia en el procesamiento de pagos

En el contexto de un sistema de procesamiento de pagos para una entidad financiera, es crucial garantizar que las transacciones sean idempotentes para evitar duplicaciones no deseadas en caso de reintentos. El sistema recibe solicitudes de pago desde tres canales diferentes: móvil, web y API de socios. Cada solicitud debe ser procesada de manera idempotente, asegurando que una misma solicitud no genere más de un pago. Los pagos deben ser persistentes y, en caso de fallo temporal del sistema, deben ser recuperados automáticamente sin reprocesar los ya emitidos.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Idempotencia en pagos |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Canal de entrada operativo persistiendo solicitudes con idempotencia

**Objetivo:** Implementar un canal de entrada que acepte solicitudes de pago y las persista de manera idempotente.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Diseña un mecanismo para asegurar la idempotencia de las solicitudes de pago.
- Persiste cada solicitud con una clave de idempotencia única.
- Garantiza que la misma solicitud no genere más de un pago.

**Entregable:** Canal de entrada que acepta y persiste solicitudes de pago de manera idempotente.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo manejar solicitudes duplicadas.
- Piensa en cómo garantizar que la persistencia sea efectiva y no genere duplicados.

</details>

### Fase 2: Consolidación de los tres canales sin pérdida ante backpressure de uno

**Objetivo:** Integrar los tres canales de entrada y asegurar que no haya pérdida de datos ante backpressure en alguno de ellos.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Consolida los canales de entrada móvil, web y API de socios en un único punto de entrada.
- Implementa un mecanismo para manejar la backpressure y asegurar que no haya pérdida de datos.

**Entregable:** Sistema consolidado que acepta solicitudes de los tres canales y maneja backpressure sin pérdida de datos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo distribuir la carga entre los canales.
- Piensa en estrategias para manejar la backpressure de manera efectiva.

</details>

### Fase 3: Recuperación automática ante caída del consumidor downstream sin reprocesar emitidas

**Objetivo:** Implementar un mecanismo de recuperación automática en caso de caída del consumidor downstream, sin reprocesar solicitudes ya emitidas.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Diseña un mecanismo de recuperación automática para el caso de que el consumidor downstream caiga.
- Asegura que las solicitudes ya emitidas no sean reprocesadas.

**Entregable:** Mecanismo de recuperación automática que no reprocesa solicitudes ya emitidas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo identificar y almacenar solicitudes ya emitidas.
- Piensa en cómo recuperar automáticamente sin reprocesar.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la idempotencia y por qué es importante en el procesamiento de pagos?
- **paraQueSirve**: ¿Para qué sirve la idempotencia en el contexto de este reto?
- **comoSeUsa**: ¿Cómo se usa la idempotencia para evitar duplicados en las solicitudes de pago?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar la idempotencia y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica el diseño de un sistema idempotente en el procesamiento de pagos?

## Criterios de Evaluacion

- Implementación de un canal de entrada idempotente.
- Consolidación de tres canales sin pérdida de datos ante backpressure.
- Recuperación automática sin reprocesar solicitudes ya emitidas.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
