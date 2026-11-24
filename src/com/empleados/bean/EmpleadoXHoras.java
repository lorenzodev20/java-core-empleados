package com.empleados.bean;

import com.empleados.enums.Sexo;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.YearMonth;

public final class EmpleadoXHoras extends EmpleadoTemporal {
    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal tarifaHora;
    private double horasTrabajadasMes;

    public EmpleadoXHoras() {
        super();
        this.tarifaHora = BigDecimal.ZERO;
    }

    public EmpleadoXHoras(String codigo, String apellidoPaterno, String apellidoMaterno,
                          String nombres, Sexo sexo, Cargo cargo) {
        super(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo);
        this.tarifaHora = BigDecimal.ZERO;
    }

    public EmpleadoXHoras(String codigo, String apellidoPaterno, String apellidoMaterno,
                          String nombres, Sexo sexo, Cargo cargo, double nroHorasSemanal,
                          BigDecimal tarifaHora, double horasTrabajadasMes) {
        super(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo, nroHorasSemanal);
        this.tarifaHora = tarifaHora;
        this.horasTrabajadasMes = horasTrabajadasMes;
    }

    public BigDecimal getTarifaHora() {
        return tarifaHora;
    }

    public void setTarifaHora(BigDecimal tarifaHora) {
        if (tarifaHora != null && tarifaHora.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La tarifa por hora no puede ser negativa");
        }
        this.tarifaHora = tarifaHora != null ? tarifaHora : BigDecimal.ZERO;
    }

    public double getHorasTrabajadasMes() {
        return horasTrabajadasMes;
    }

    public void setHorasTrabajadasMes(double horasTrabajadasMes) {
        if (horasTrabajadasMes < 0) {
            throw new IllegalArgumentException("Las horas trabajadas del mes no pueden ser negativas");
        }
        this.horasTrabajadasMes = horasTrabajadasMes;
    }

    @Override
    public BigDecimal calcularRemuneracionMensual(YearMonth mesProceso) {
        Contrato contratoActivo = obtenerContratoActivoVigente(mesProceso);
        if (contratoActivo == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal tarifa = this.tarifaHora != null ? this.tarifaHora : BigDecimal.ZERO;
        BigDecimal horas = BigDecimal.valueOf(this.horasTrabajadasMes);
        return tarifa.multiply(horas);
    }
}