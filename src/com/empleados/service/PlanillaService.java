package com.empleados.service;

import com.empleados.bean.*;
import com.empleados.enums.SituacionContrato;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class PlanillaService {

    private final List<Empleado> empleados = new ArrayList<>();
    private final Map<String, Empleado> empleadosPorCodigo = new HashMap<>();

    // 1. Registrar empleado (mantiene List y Map consistentes)
    public void registrarEmpleado(Empleado empleado) {
        if (empleado == null || empleado.getCodigo() == null || empleado.getCodigo().isBlank()) {
            throw new IllegalArgumentException("El empleado y su código son obligatorios");
        }
        String codigo = empleado.getCodigo().trim();
        if (empleadosPorCodigo.containsKey(codigo)) {
            throw new IllegalArgumentException("Ya existe un empleado con código: " + codigo);
        }
        empleados.add(empleado);
        empleadosPorCodigo.put(codigo, empleado);
    }

    // Retirar empleado (mantiene List y Map consistentes)
    public boolean retirarEmpleado(String codigo) {
        if (codigo == null) return false;
        Empleado removido = empleadosPorCodigo.remove(codigo.trim());
        if (removido != null) {
            empleados.remove(removido);
            return true;
        }
        return false;
    }

    // 2. Buscar por código O(1) usando el Map
    public Optional<Empleado> buscarPorCodigo(String codigo) {
        if (codigo == null) return Optional.empty();
        return Optional.ofNullable(empleadosPorCodigo.get(codigo.trim()));
    }

    // 3. Listar por unidad orgánica
    public List<Empleado> listarPorUnidad(UnidadOrganica unidad) {
        if (unidad == null) return Collections.emptyList();
        return empleados.stream()
                .filter(e -> e.getUnidadOrganica() != null
                        && Objects.equals(e.getUnidadOrganica().getId(), unidad.getId()))
                .collect(Collectors.toList());
    }

    // 4. Listar contratos activos
    public List<Contrato> listarContratosActivos() {
        return empleados.stream()
                .flatMap(e -> e.getContratos().stream())
                .filter(c -> c.getSituacionContrato() == SituacionContrato.ACTIVO)
                .collect(Collectors.toList());
    }

    // 5. Aprobar un entregable (validando que pertenezca a un contrato de proyecto)
    public void aprobarEntregable(Entregable entregable) {
        if (entregable == null) {
            throw new IllegalArgumentException("El entregable no puede ser nulo");
        }
        entregable.setAprobado(true);
    }

    // 6. Listar planilla ordenada por nombre completo (solo empleados con remuneración > 0 / contrato activo vigente)
    public List<Empleado> listarPlanillaOrdenada(YearMonth mesProceso, boolean byCodigo) {
        return empleados.stream()
                .filter(e -> e.calcularRemuneracionMensual(mesProceso).compareTo(BigDecimal.ZERO) >= 0)
                .sorted(Comparator.comparing(byCodigo?Empleado::getCodigo : Empleado::obtenerNombreCompleto, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    // 7. Calcular total de pagos usando POLIMORFISMO puro (sin instanceof ni switch)
    public BigDecimal calcularTotalPagos(YearMonth mesProceso) {
        return empleados.stream()
                .map(e -> e.calcularRemuneracionMensual(mesProceso)) // Polimorfismo puro
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 8. Totalizar pagos por unidad orgánica
    public BigDecimal totalizarPagosPorUnidad(UnidadOrganica unidad, YearMonth mesProceso) {
        return listarPorUnidad(unidad).stream()
                .map(e -> e.calcularRemuneracionMensual(mesProceso)) // Polimorfismo puro
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}