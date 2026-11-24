package com.empleados.bean;

import com.empleados.enums.Sexo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;

public class EmpleadoXProyecto extends EmpleadoTemporal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmpleadoXProyecto() {
        super();
    }

    public EmpleadoXProyecto(String codigo, String apellidoPaterno, String apellidoMaterno,
                             String nombres, Sexo sexo, Cargo cargo) {
        super(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo);
    }

    public EmpleadoXProyecto(String codigo, String apellidoPaterno, String apellidoMaterno,
                             String nombres, Sexo sexo, Cargo cargo, double nroHorasSemanal) {
        super(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo, nroHorasSemanal);
    }

    @Override
    public BigDecimal calcularRemuneracionMensual(YearMonth mesProceso) {
        Contrato contratoActivo = obtenerContratoActivoVigente(mesProceso);
        if (contratoActivo == null || contratoActivo.getEntregables() == null) {
            return BigDecimal.ZERO;
        }

        return contratoActivo.getEntregables().stream()
                .filter(Objects::nonNull)
                .filter(e -> Boolean.TRUE.equals(e.getAprobado()))
                .filter(e -> e.getFechaProgramada() != null
                        && YearMonth.from(e.getFechaProgramada()).equals(mesProceso))
                .map(Entregable::getMonto)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}