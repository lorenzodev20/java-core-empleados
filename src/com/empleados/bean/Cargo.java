package com.empleados.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cargo extends EntidadBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nombreCorto;
    private String nombreLargo;
    private Empresa empresa;
    private List<UnidadOrganica> unidadesOrganicas;

    public Cargo() {
        super();
        this.unidadesOrganicas = new ArrayList<>();
    }

    public Cargo(String nombreCorto, String nombreLargo, Empresa empresa) {
        this(); // Llama al constructor vacío de arriba (ejecuta super() e inicializa la lista)
        this.nombreCorto = nombreCorto;
        this.nombreLargo = nombreLargo;
        this.empresa = empresa;
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

    public List<UnidadOrganica> getUnidadesOrganicas() {
        return unidadesOrganicas;
    }

    public void setUnidadesOrganicas(List<UnidadOrganica> unidadesOrganicas) {
        this.unidadesOrganicas = unidadesOrganicas;
    }
}