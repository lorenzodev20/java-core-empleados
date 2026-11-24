package com.empleados.bean;

import com.empleados.enums.Sexo;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.YearMonth;

public final class EmpleadoXProyectoNacionales extends EmpleadoXProyecto {
    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal viaticoMensual;

    public EmpleadoXProyectoNacionales() {
        super();
        this.viaticoMensual = BigDecimal.ZERO;
    }

    public EmpleadoXProyectoNacionales(BigDecimal viaticoMensual) {
        this();
        setViaticoMensual(viaticoMensual);
    }

    public EmpleadoXProyectoNacionales(String codigo, String apellidoPaterno, String apellidoMaterno,
                                       String nombres, Sexo sexo, Cargo cargo, BigDecimal viaticoMensual) {
        super(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo);
        setViaticoMensual(viaticoMensual);
    }

    public EmpleadoXProyectoNacionales(String codigo, String apellidoPaterno, String apellidoMaterno,
                                       String nombres, Sexo sexo, Cargo cargo, double nroHorasSemanal,
                                       BigDecimal viaticoMensual) {
        super(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo, nroHorasSemanal);
        setViaticoMensual(viaticoMensual);
    }

    public BigDecimal getViaticoMensual() {
        return viaticoMensual;
    }

    public void setViaticoMensual(BigDecimal viaticoMensual) {
        if (viaticoMensual != null && viaticoMensual.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El viático mensual no puede ser negativo");
        }
        this.viaticoMensual = viaticoMensual != null ? viaticoMensual : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calcularRemuneracionMensual(YearMonth mesProceso) {
        if (obtenerContratoActivoVigente(mesProceso) == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal salarioProyecto = super.calcularRemuneracionMensual(mesProceso);
        BigDecimal viatico = this.viaticoMensual != null ? this.viaticoMensual : BigDecimal.ZERO;
        return salarioProyecto.add(viatico);
    }
}