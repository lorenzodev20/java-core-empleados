package com.empleados;

import com.empleados.bean.*;
import com.empleados.enums.Sexo;
import com.empleados.enums.SituacionContrato;
import com.empleados.service.PlanillaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App {
    static void main() {
        System.out.println("Gestión de empleados \n");
        System.out.println("Creando datos de la empresa .... ");
        Empresa empresa = crearDatosBaseDeEmpresa();
        System.out.println("Datos de la empresa creados!");

        final Map<String, UnidadOrganica> unidadOrganicaMap = new HashMap<>();
        for (UnidadOrganica unidadOrganica : empresa.getUnidadesOrganicas()) {
            if (unidadOrganica != null && unidadOrganica.getId() != null && unidadOrganica.getNombreCorto() != null) {
                unidadOrganicaMap.put(unidadOrganica.getNombreCorto(), unidadOrganica);
            }
        }

        UnidadOrganica unidadTec = unidadOrganicaMap.get("Tecnología");
        UnidadOrganica unidadAdmin = unidadOrganicaMap.get("Administración");

        Cargo cargoRhh = unidadAdmin.getCargos().stream()
                .filter(c -> "Analista RHH".equalsIgnoreCase(c.getNombreCorto()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el cargo Analista RHH"));
        Cargo cargoAnalista = unidadTec.getCargos().stream()
                .filter(c -> "Analista 1".equalsIgnoreCase(c.getNombreCorto()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el cargo Analista 1"));
        Cargo cargoLiderTi = unidadTec.getCargos().stream()
                .filter(c -> "Líder TI".equalsIgnoreCase(c.getNombreCorto()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el cargo Líder TI"));

        // Empleados
        Empleado empleado01 = new EmpleadoFijo(
                "E001",
                "Ballesta",
                "Oviedo",
                "Juana Maria",
                Sexo.FEMENINO,
                cargoRhh,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(10000));
        LocalDate fechaInicio = LocalDate.parse("2026-10-01");
        LocalDate fechaFin = LocalDate.parse("2028-10-31");

        Contrato contrato1 = new Contrato(fechaInicio, fechaFin, SituacionContrato.ACTIVO, BigDecimal.valueOf(3000), empleado01);
        empleado01.agregarContrato(contrato1);
        empleado01.setUnidadOrganica(unidadAdmin);


        double nroHorasSemanal = 20;
        double horasTrabajadas = 80;
        Empleado empleado02 = new EmpleadoXHoras(
                "E002",
                "Rojo",
                "Garces",
                "Lorenzo Antonio",
                Sexo.FEMENINO,
                cargoLiderTi,
                nroHorasSemanal,
                BigDecimal.valueOf(40),
                horasTrabajadas
        );

        Contrato contrato2 = new Contrato(fechaInicio, fechaFin, SituacionContrato.ACTIVO, BigDecimal.valueOf(0), empleado02);
        empleado02.agregarContrato(contrato2);
        empleado02.setUnidadOrganica(unidadTec);

        //Empleado 03
        double nroHorasSemanal01 = 40;
        Empleado empleado03 = new EmpleadoXProyecto(
                "E003",
                "Rojo",
                "Mejia",
                "Antonio Ramón",
                Sexo.MASCULINO,
                cargoAnalista,
                nroHorasSemanal01
        );

        Contrato contrato3 = new Contrato(fechaInicio, fechaFin, SituacionContrato.ACTIVO, BigDecimal.valueOf(0), empleado03);
        empleado03.agregarContrato(contrato3);
        empleado03.setUnidadOrganica(unidadTec);
        Entregable ent1 = new Entregable("ENT-01", "Fase 1: Análisis", LocalDate.of(2026, 10, 20), new BigDecimal("2000"), true);
        Entregable ent2 = new Entregable("ENT-02", "Fase 2: Desarrollo", LocalDate.of(2026, 10, 20), new BigDecimal("1500"), true);
        Entregable ent3 = new Entregable("ENT-03", "Fase 3: Pruebas", LocalDate.of(2026, 10, 20), new BigDecimal("500"), false);
        List<Entregable> entregables03 = new ArrayList<>();
        entregables03.add(ent1);
        entregables03.add(ent2);
        entregables03.add(ent3);
        ent1.setContrato(contrato3);
        ent2.setContrato(contrato3);
        ent3.setContrato(contrato3);
        contrato3.setEntregables(entregables03);

        //Empleado 04
        double nroHorasSemanal02 = 40;
        int entregablesAprobados = 2500;
        Empleado empleado04 = new EmpleadoXProyectoNacionales(
                "E004",
                "Rojo",
                "",
                "Ramón",
                Sexo.MASCULINO,
                cargoRhh,
                nroHorasSemanal02,
                BigDecimal.valueOf(300)

        );

        Contrato contrato4 = new Contrato(fechaInicio, fechaFin, SituacionContrato.ACTIVO, BigDecimal.valueOf(0), empleado04);
        empleado04.agregarContrato(contrato4);
        empleado04.setUnidadOrganica(unidadAdmin);

        Entregable ent4 = new Entregable("ENT-02", "Fase 2: Desarrollo", LocalDate.of(2026, 10, 20), new BigDecimal("2500"), true);
        ent3.setContrato(contrato4);
        List<Entregable> entregables04 = new ArrayList<>();
        entregables04.add(ent4);
        contrato4.setEntregables(entregables04);
        // Planilla

        PlanillaService planillaService = new PlanillaService();

        planillaService.registrarEmpleado(empleado01);
        planillaService.registrarEmpleado(empleado02);
        planillaService.registrarEmpleado(empleado03);
        planillaService.registrarEmpleado(empleado04);
        YearMonth mesProceso = YearMonth.of(2026, Month.OCTOBER);

        System.out.println("=== PLANILLA DEL MES (" + mesProceso + ") ===");
        planillaService.listarPlanillaOrdenada(mesProceso, true).forEach(emp -> {
            System.out.printf("Código: %s | Nombre: %s | Unidad: %s | Cargo: %s |Remuneración: S/ %.2f%n",
                    emp.getCodigo(),
                    emp.obtenerNombreCompleto(),
                    emp.getUnidadOrganica() != null ? emp.getUnidadOrganica().getNombreCorto() : "N/A",
                    emp.getCargo() != null ? emp.getCargo().getNombreCorto() : "N/A",
                    emp.calcularRemuneracionMensual(mesProceso));
        });

        System.out.println("Total por Unidad Orgánica: " + unidadAdmin.getNombreLargo() + " -> S/" + planillaService.totalizarPagosPorUnidad(unidadAdmin, mesProceso));
        System.out.println("Total por Unidad Orgánica: " + unidadTec.getNombreLargo() + " -> S/" + planillaService.totalizarPagosPorUnidad(unidadTec, mesProceso));
    }

    private static Empresa crearDatosBaseDeEmpresa() {
        Empresa empresa = new Empresa(
                "EchoForge Developers INC",
                "1234480",
                "CR 43A NRO 1 Sur Edificio TowerBank, Oficina 5045, Medellín, Antioquia, CO");

        UnidadOrganica unidadOrganica1 = new UnidadOrganica("Tecnología", "Departamento de Tecnología", empresa);
        UnidadOrganica unidadOrganica2 = new UnidadOrganica("Administración", "Departamento de Administración", empresa);

        Cargo cargo1 = new Cargo("Analista 1", "Analista Nivel 1", empresa);
        Cargo cargo2 = new Cargo("Líder TI", "Líder Tecnología e Información", empresa);
        Cargo cargo3 = new Cargo("Analista RHH", "Analista de Recursos Humanos", empresa);

        unidadOrganica1.agregarCargo(cargo1);
        unidadOrganica1.agregarCargo(cargo2);
        unidadOrganica2.agregarCargo(cargo3);

        empresa.agregarUnidadOrganica(unidadOrganica1);
        empresa.agregarUnidadOrganica(unidadOrganica2);

        return empresa;
    }
}
