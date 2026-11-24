package com.empleados.bean;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Entregable extends EntidadBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String descripcion;
    private LocalDate fechaProgramada;
    private BigDecimal monto;
    private Boolean aprobado;
    private Contrato contrato;

    public Entregable() {
        super();
        this.aprobado = Boolean.FALSE;
    }

    public Entregable(String codigo, String descripcion, LocalDate fechaProgramada, BigDecimal monto, Boolean aprobado) {
        this();
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaProgramada = fechaProgramada;
        this.monto = monto;
        this.aprobado = aprobado != null ? aprobado : Boolean.FALSE;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}