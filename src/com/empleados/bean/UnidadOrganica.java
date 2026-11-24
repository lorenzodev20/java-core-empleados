package com.empleados.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UnidadOrganica extends EntidadBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nombreCorto;
    private String nombreLargo;
    private Empresa empresa;
    private List<Cargo> cargos;

    public UnidadOrganica() {
        super();
        this.cargos = new ArrayList<>();
    }

    public UnidadOrganica(String nombreCorto, String nombreLargo, Empresa empresa) {
        this(); // Llama al constructor vacío: ejecuta super() e inicializa this.cargos
        this.nombreCorto = nombreCorto;
        this.nombreLargo = nombreLargo;
        this.empresa = empresa;
    }

    // Método helper para agregar un cargo individualmente
    public void agregarCargo(Cargo cargo) {
        if (cargo != null) {
            this.cargos.add(cargo);
        }
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    // Setter estándar de JavaBean
    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos != null ? cargos : new ArrayList<>();
    }
}