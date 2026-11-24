package com.empleados.bean;

import com.empleados.enums.SituacionContrato;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Contrato extends EntidadBase {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private SituacionContrato situacionContrato;
    private BigDecimal sueldo;
    private Empleado empleado;
    private List<Entregable> entregables;

    public Contrato() {
        super();
        this.entregables = new ArrayList<>();
    }

    public Contrato(LocalDate fechaInicio, LocalDate fechaTermino, SituacionContrato situacionContrato, BigDecimal sueldo, Empleado empleado) {
        this();
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.situacionContrato = situacionContrato;
        this.sueldo = sueldo;
        this.empleado = empleado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public SituacionContrato getSituacionContrato() {
        return situacionContrato;
    }

    public void setSituacionContrato(SituacionContrato situacionContrato) {
        this.situacionContrato = situacionContrato;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Entregable> getEntregables() {
        return entregables;
    }

    public void setEntregables(List<Entregable> entregables) {
        this.entregables = entregables != null ? entregables : new ArrayList<>();
    }
}
