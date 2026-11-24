package com.empleados.bean;

import com.empleados.enums.Sexo;

import java.io.Serial;

public abstract class EmpleadoTemporal extends Empleado {
    @Serial
    private static final long serialVersionUID = 1L;

    private double nroHorasSemanal;

    public EmpleadoTemporal() {
        super();
    }

    public EmpleadoTemporal(String codigo, String apellidoPaterno, String apellidoMaterno,
                            String nombres, Sexo sexo, Cargo cargo) {
        super(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo);
    }

    public EmpleadoTemporal(String codigo, String apellidoPaterno, String apellidoMaterno,
                            String nombres, Sexo sexo, Cargo cargo, double nroHorasSemanal) {
        this(codigo, apellidoPaterno, apellidoMaterno, nombres, sexo, cargo);
        setNroHorasSemanal(nroHorasSemanal);
    }

    public double getNroHorasSemanal() {
        return nroHorasSemanal;
    }

    public void setNroHorasSemanal(double nroHorasSemanal) {
        if (nroHorasSemanal < 1 || nroHorasSemanal > 48) {
            throw new IllegalArgumentException("Las horas semanales deben estar entre 1 y 48");
        }
        this.nroHorasSemanal = nroHorasSemanal;
    }
}