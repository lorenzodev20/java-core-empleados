# CASO PRÁCTICO — Gestión de empleados y contratos con POO en Java

*Galaxy Training — Programa Java Web Developer | Java Core*

## I. Objetivo

Desarrollar una aplicación de consola que registre empleados de diferentes modalidades, gestione sus contratos y calcule su remuneración mensual mediante herencia, polimorfismo, encapsulación, agregación, composición, clases abstractas, clases finales y colecciones.

## II. Caso

La empresa Tecnologías Andinas S.A.C. necesita organizar sus unidades, cargos, empleados y contratos. Actualmente calcula los pagos por separado, lo que dificulta consolidar la planilla. Se solicita implementar un modelo orientado a objetos a partir del diagrama proporcionado y demostrar que una misma operación calcula pagos distintos según el tipo de empleado.

Alcance: aplicación en memoria, con datos de prueba y ejecución desde `main`. No se requiere base de datos, interfaz gráfica ni API. Las fórmulas son reglas didácticas del caso y no representan una liquidación laboral legal.

## III. Actividades

| N.º | Actividad | Tiempo |
|---|---|---|
| 1 | Analizar el diagrama y precisar responsabilidades, jerarquías y multiplicidades. | 15 min |
| 2 | Implementar la clase base, entidades, enumeraciones y validaciones. | 25 min |
| 3 | Implementar la jerarquía de empleados y el cálculo polimórfico. | 35 min |
| 4 | Gestionar relaciones, contratos y entregables mediante colecciones. | 30 min |
| 5 | Ejecutar consultas, escenarios de cálculo y validaciones. | 30 min |
| 6 | Preparar el README y las evidencias (diagrama UML opcional). | 15 min |
| | **Total de desarrollo** | **150 min** |

Modalidad sugerida: individual o en parejas. Demostración y revisión: 10 minutos adicionales. Prerrequisito: JDK 26 o superior y un IDE de Java.

## IV. Modelo de clases a implementar

Conservar los nombres del diagrama. Las siguientes precisiones completan el modelo para que sea implementable y evaluable (el diagrama UML actualizado es opcional).

| Clase o tipo | Responsabilidad y atributos mínimos |
|---|---|
| `EntidadBase` (abstracta) | `id` final de tipo `UUID`, generado al construir. Base común de las clases del dominio; no aplica a enums ni a la clase de ejecución. |
| `Empresa` | `razonSocial`, `ruc` y `direccionFiscal`; colección de unidades orgánicas. |
| `UnidadOrganica` | `nombreCorto` y `nombreLargo`; referencia a `Empresa` y colección de cargos. |
| `Cargo` | `nombreCorto` y `nombreLargo`; referencia a `UnidadOrganica`. |
| `Empleado` (abstracta) | `codigo`, `apellidoPaterno`, `apellidoMaterno`, `nombres`, `Sexo`, `Cargo` y `UnidadOrganica`. Declara `calcularRemuneracionMensual()` abstracto y `obtenerNombreCompleto()` concreto. |
| `EmpleadoFijo` (final) | Hereda de `Empleado`; `porcentajeComision` y `ventasMes`. |
| `EmpleadoTemporal` (abstracta) | Hereda de `Empleado`; `nroHorasSemanal` común a empleados temporales. |
| `EmpleadoXProyecto` | Hereda de `EmpleadoTemporal`; remunera según entregables aprobados del mes. |
| `EmpleadoXProyectoNacionales` (final) | Hereda de `EmpleadoXProyecto`; `viaticoMensual`. |
| `EmpleadoXHoras` (final) | Hereda de `EmpleadoTemporal`; `tarifaHora` y `horasTrabajadasMes`. |
| `Contrato` | `fechaInicio`, `fechaTermino` opcional, `SituacionContrato` y `sueldo`; referencia a `Empleado` y colección de entregables. |
| `Entregable` (final) | Clase adicional: `codigo`, `descripcion`, `fechaProgramada`, `monto` y `aprobado`. |
| Enumeraciones | `Sexo`: FEMENINO, MASCULINO, NO_ESPECIFICADO, cada uno con `indice` y `valor`. `SituacionContrato`: ACTIVO, FINALIZADO. |

La nota «clase genérica» del diagrama se interpreta como una superclase común, `EntidadBase`. No exige parámetros de tipo `<T>`. Las colecciones sí deben utilizar genéricos. El rótulo «Entregables» se concreta como una clase relacionada con `Contrato`.

## V. Relaciones y reglas del dominio

| Relación | Multiplicidad y comportamiento |
|---|---|
| Empresa y UnidadOrganica | **Composición**: una empresa contiene 1..* unidades; cada unidad pertenece a una empresa y se crea dentro de ella. |
| UnidadOrganica y Cargo | **Agregación**: una unidad agrupa 0..* cargos. Un cargo pertenece a una unidad a la vez y puede reasignarse si no tiene empleados asociados. |
| Empleado y estructura | Cada empleado tiene exactamente un cargo y una unidad. La unidad del empleado debe coincidir con la unidad del cargo. |
| Empleado y Contrato | **Asociación**: un empleado conserva 0..* contratos históricos, como máximo uno ACTIVO. Cada contrato corresponde a un solo empleado. |
| Contrato y Entregable | **Composición**: un contrato de proyecto contiene 0..* entregables; cada entregable pertenece a un solo contrato. No se comparten instancias entre contratos. |

**Encapsulación:** mantener atributos privados y permitir cambios mediante constructores o métodos que validen reglas. Devolver copias o vistas no modificables de las colecciones. No exponer setters indiscriminados. Usar `super` en la construcción y `@Override` en los cálculos concretos.

### Reglas de remuneración mensual

Trabajar con un mes de proceso `YearMonth` y un contrato ACTIVO que cubra todo ese mes. Si no existe, el empleado queda fuera de la planilla y su cálculo devuelve 0. No se exige prorrateo. En proyectos, considerar solo entregables aprobados cuya fecha programada esté en el mes de proceso.

| Tipo de empleado | Fórmula |
|---|---|
| Fijo | sueldo del contrato + ventasMes × porcentajeComision / 100 |
| Por horas | tarifaHora × horasTrabajadasMes; sueldo del contrato = 0 |
| Por proyecto | Suma de montos de entregables aprobados del mes; sueldo del contrato = 0 |
| Proyecto nacional | Cálculo de `EmpleadoXProyecto` mediante `super` + viaticoMensual |

El mes de proceso debe estar disponible para el cálculo de cada empleado, por constructor o configuración validada previa. Se admite como alternativa la firma `calcularRemuneracionMensual(YearMonth mes)`, usada uniformemente en toda la jerarquía.

**Validaciones:** códigos únicos de empleado, RUC de 11 dígitos, textos obligatorios, montos y horas no negativos, comisión entre 0 y 100, horas semanales entre 1 y 48 en temporales y fecha de término no anterior al inicio. El RUC solo se valida por formato. Rechazar contratos activos duplicados y entregables en contratos que no sean de proyecto.

## VI. Datos de prueba y resultados esperados

Crear una empresa, dos unidades —Tecnología y Administración— y tres cargos. Registrar los siguientes empleados con contratos activos del 01/10/2026 al 31/10/2026. El mes de proceso es octubre de 2026; todos los importes se expresan en soles.

| Código | Tipo | Datos del mes | Pago esperado |
|---|---|---|---|
| E001 | Fijo | Sueldo 3 000; ventas 10 000; comisión 5 %. | 3 500,00 |
| E002 | Por horas | Tarifa 40; 80 horas del mes; 20 horas semanales. | 3 200,00 |
| E003 | Por proyecto | 40 horas semanales; entregables: 2 000 y 1 500 aprobados, 500 pendiente. | 3 500,00 |
| E004 | Proyecto nacional | 40 horas semanales; entregable aprobado 2 500; viático 300. | 2 800,00 |
| | **Total de planilla** | | **13 000,00** |

Asignar E001 y E004 a Administración y E002 y E003 a Tecnología. Usar fechas programadas de octubre en los entregables anteriores. Incorporar además un entregable aprobado de septiembre por S/ 900 en E003: debe excluirse del cálculo de octubre.

## VII. Colecciones y operaciones obligatorias

Gestionar empleados en `List<Empleado>` y buscarlos por código con `Map<String, Empleado>`. Mantener ambas colecciones consistentes al registrar o retirar empleados. Usar `List<Contrato>`, `List<UnidadOrganica>`, `List<Cargo>` y `List<Entregable>` donde corresponda.

Implementar:

- registrar empleado;
- buscar por código;
- listar por unidad;
- listar contratos activos;
- aprobar un entregable;
- listar la planilla ordenada por nombre completo;
- calcular el total de pagos;
- totalizar pagos por unidad. Administración debe totalizar S/ 6 300 y Tecnología S/ 6 700.

La planilla debe recorrer referencias de tipo `Empleado` y llamar al método de cálculo sobrescrito. **No se permite** resolver las modalidades con cadenas de `instanceof`, comparaciones de nombres de clase o `switch` por tipo de empleado.

## VIII. Escenarios de aceptación

1. Con los cuatro empleados, obtener los cuatro pagos y el total esperado de S/ 13 000.
2. Aprobar el entregable pendiente de E003: su pago cambia a S/ 4 000 y el total a S/ 13 500.
3. Buscar un código inexistente: informar el resultado sin provocar una caída.
4. Intentar código duplicado, comisión 105, monto negativo y contrato con fechas invertidas: rechazar cada operación con un mensaje claro.
5. Finalizar el contrato de E002: excluirlo de la planilla y devolver 0 en su cálculo. Ejecutar este escenario de forma independiente sobre los datos iniciales: total esperado S/ 9 800.

Usar `BigDecimal` para dinero y porcentajes, con redondeo a dos decimales mediante `HALF_UP` al presentar cada pago. Capturar las excepciones de validación en la capa de consola para continuar la demostración.

## IX. Entregables del participante

1. Proyecto Java con código fuente organizado en paquetes, sin archivos compilados ni dependencias innecesarias.
2. *(Opcional)* Diagrama UML actualizado con visibilidad, herencia, clases abstractas y finales, multiplicidades, agregación y composición.
3. README con requisitos, instrucciones exactas de compilación y ejecución, decisiones del modelo y fórmulas aplicadas.
4. Evidencias de ejecución de los cinco escenarios, con entradas, resultados esperados y resultados obtenidos.

## X. Demostración y revisión en 10 minutos

Mostrar la jerarquía y explicar qué clases no pueden instanciarse o extenderse. Ejecutar la planilla mediante `List<Empleado>`, demostrar el cambio al aprobar un entregable y ejecutar dos rechazos de validación. Mostrar las relaciones entre objetos y explicar cómo se protege una colección frente a modificaciones externas.

## XI. Rúbrica de evaluación sobre 20 puntos

| Criterio | Evidencia para puntaje completo | Máximo |
|---|---|---|
| Herencia y abstracción | Jerarquía completa, `EntidadBase` común, `Empleado` y `EmpleadoTemporal` abstractas; constructores con `super`. | 4 |
| Polimorfismo | Cálculos sobrescritos correctos y planilla ejecutada desde `List<Empleado>` sin discriminar subtipos. | 4 |
| Encapsulación y final | Atributos privados, reglas validadas, colecciones protegidas, id inmutable y clases finales justificadas. | 3 |
| Relaciones del dominio | Asociaciones consistentes; agregación y composición demostradas con multiplicidades y control de pertenencia. | 3 |
| Colecciones y consultas | `List` y `Map` tipados y consistentes; búsqueda, filtros, orden y totales por unidad correctos. | 3 |
| Ejecución y entrega | Cinco escenarios verificables, programa ejecutable y README coherente con el código *(sin requisito de diagrama UML en la rúbrica)*. | 3 |
| **Total** | | **20** |

Escala por criterio: asignar el máximo si cumple toda la evidencia; la mitad si la implementación funciona parcialmente o la evidencia está incompleta; y 0 si está ausente o no funciona. Se permiten medios puntos. La suma máxima es 20.

**Condición para acreditar polimorfismo:** si el cálculo depende de `instanceof`, del nombre de clase o de un `switch` por modalidad, ese criterio obtiene 0 puntos aunque los importes coincidan. Las validaciones deben comprobarse con ejecución, no únicamente describirse.
