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
    private int ventasMes;

    public EmpleadoFijo() {
        super();
    }

    public EmpleadoFijo(BigDecimal porcentajeComision, int ventasMes) {
        this();
        this.porcentajeComision = porcentajeComision;
        this.ventasMes = ventasMes;
    }

    public BigDecimal getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(BigDecimal porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public int getVentasMes() {
        return ventasMes;
    }

    public void setVentasMes(int ventasMes) {
        this.ventasMes = ventasMes;
    }


    @Override
    public BigDecimal calcularRemuneracionMensual(YearMonth mesProceso) {
        Contrato contratoActivo = obtenerContratoActivoVigente(mesProceso);
        if (contratoActivo == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal sueldoBase = contratoActivo.getSueldo() != null ? contratoActivo.getSueldo() : BigDecimal.ZERO;
        BigDecimal ventas = this.ventasMes != 0? BigDecimal.valueOf(this.ventasMes) : BigDecimal.ZERO;
        BigDecimal porcentaje = this.porcentajeComision != null ? this.porcentajeComision : BigDecimal.ZERO;

        BigDecimal comision = ventas.multiply(porcentaje)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return sueldoBase.add(comision);
    }
}
