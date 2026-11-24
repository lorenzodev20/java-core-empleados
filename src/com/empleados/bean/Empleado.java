package com.empleados.bean;

import com.empleados.enums.Sexo;
import com.empleados.enums.SituacionContrato;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Empleado extends EntidadBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private Sexo sexo;
    private Cargo cargo;
    private UnidadOrganica unidadOrganica;
    private final List<Contrato> contratos;

    public Empleado() {
        super();
        this.contratos = new ArrayList<>();
    }

    public Empleado(String codigo, String apellidoPaterno, String apellidoMaterno, String nombres, Sexo sexo, Cargo cargo) {
        this(); // Debe ser la primera línea
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código de empleado es obligatorio");
        }
        if ((nombres == null || nombres.isBlank()) || (apellidoPaterno == null || apellidoPaterno.isBlank())) {
            throw new IllegalArgumentException("Los nombres y/o apellidos son obligatorios");
        }
        this.codigo = codigo.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno != null ? apellidoMaterno.trim() : null;
        this.nombres = nombres.trim();
        this.sexo = sexo;
        this.cargo = cargo;
    }

    public void agregarContrato(Contrato contrato) {
        if (contrato == null) {
            throw new IllegalArgumentException("El contrato no puede ser nulo");
        }
        if (contrato.getSituacionContrato() == SituacionContrato.ACTIVO) {
            boolean yaTieneActivo = this.contratos.stream()
                    .anyMatch(c -> c.getSituacionContrato() == SituacionContrato.ACTIVO);
            if (yaTieneActivo) {
                throw new IllegalStateException("El empleado ya tiene un contrato ACTIVO");
            }
        }
        contrato.setEmpleado(this);
        this.contratos.add(contrato);
    }

    protected Contrato obtenerContratoActivoVigente(YearMonth mesProceso) {
        Objects.requireNonNull(mesProceso, "El mes de proceso es obligatorio");
        LocalDate inicioMes = mesProceso.atDay(1);
        LocalDate finMes = mesProceso.atEndOfMonth();

        return contratos.stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getSituacionContrato() == SituacionContrato.ACTIVO)
                .filter(c -> c.getFechaInicio() != null && !c.getFechaInicio().isAfter(inicioMes))
                .filter(c -> c.getFechaTermino() == null || !c.getFechaTermino().isBefore(finMes))
                .findFirst()
                .orElse(null);
    }

    public abstract BigDecimal calcularRemuneracionMensual(YearMonth mesProceso);

    public String obtenerNombreCompleto() {
        return Stream.of(this.nombres, this.apellidoPaterno, this.apellidoMaterno)
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" "));
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public UnidadOrganica getUnidadOrganica() {
        return unidadOrganica;
    }

    public void setUnidadOrganica(UnidadOrganica unidadOrganica) {
        this.unidadOrganica = unidadOrganica;
    }

    public List<Contrato> getContratos() {
        return Collections.unmodifiableList(contratos);
    }
}