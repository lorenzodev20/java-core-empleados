package com.empleados.bean;

import com.empleados.enums.Sexo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.List;

final public class EmpleadoFijo extends Empleado implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal porcentajeComision;
    private BigDecimal ventasMes;

    public EmpleadoFijo() {
        super();
    }

    public EmpleadoFijo(BigDecimal porcentajeComision, BigDecimal ventasMes) {
        this();
        setPorcentajeComision(porcentajeComision);
        setVentasMes(ventasMes);
    }

    public EmpleadoFijo(String codigo, String apellidoPaterno, String apellidoMaterno,
                        String nombres, Sexo sexo, Cargo cargo,
                        BigDecimal porcentajeComision, BigDecimal ventasMes) {
        super(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo);
        setPorcentajeComision(porcentajeComision);
        setVentasMes(ventasMes);
    }

    public BigDecimal getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(BigDecimal porcentajeComision) {
        BigDecimal cero = BigDecimal.ZERO;
        BigDecimal cien = BigDecimal.valueOf(100);
        if (porcentajeComision == null || porcentajeComision.compareTo(cero) < 0 || porcentajeComision.compareTo(cien) > 0) {
            throw new IllegalArgumentException("El porcentaje de comisión debe estar entre cero o cien");
        }
        this.porcentajeComision = porcentajeComision;
    }

    public BigDecimal getVentasMes() {
        return ventasMes;
    }

    public void setVentasMes(BigDecimal ventasMes) {
        if (ventasMes == null || ventasMes.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Las ventas del mes no puede ser menor a cero");
        }
        this.ventasMes = ventasMes;
    }


    @Override
    public BigDecimal calcularRemuneracionMensual(YearMonth mesProceso) {
        Contrato contratoActivo = obtenerContratoActivoVigente(mesProceso);
        if (contratoActivo == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal sueldoBase = contratoActivo.getSueldo() != null ? contratoActivo.getSueldo() : BigDecimal.ZERO;
        BigDecimal ventas = this.ventasMes.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : this.ventasMes;
        BigDecimal porcentaje = this.porcentajeComision != null ? this.porcentajeComision : BigDecimal.ZERO;

        BigDecimal comision = ventas.multiply(porcentaje)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return sueldoBase.add(comision);
    }
}
