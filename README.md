# GestionEmpleados

Ejercicio personal para practicar Java Core y programación orientada a objetos. Sigue el caso práctico de la carpeta [`docs/`](docs/Caso_Practico_POO_Java_Core.md): gestión de empleados, contratos y cálculo de planilla mensual para una empresa.

## Qué se practica

- Herencia y clases abstractas (`EntidadBase`, `Empleado`, `EmpleadoTemporal`)
- Clases finales (`EmpleadoFijo`, `EmpleadoXHoras`, `Entregable`, ...)
- Polimorfismo: `calcularRemuneracionMensual()` distinto por tipo de empleado
- Encapsulación y validaciones
- Agregación y composición (`Empresa` → `UnidadOrganica` → `Cargo`, `Contrato` → `Entregable`)
- Enumeraciones (`Sexo`, `SituacionContrato`)
- Colecciones genéricas (`List`, `Map`) y capa de servicio (`PlanillaService`)

## Estructura

```
src/com/empleados/
├── App.java          # ejecución desde main
├── bean/             # entidades del dominio
├── enums/            # Sexo, SituacionContrato
└── service/          # PlanillaService
```

## Ejecución

Requiere JDK y un IDE (IntelliJ IDEA). Ejecutar `com.empleados.App`.
