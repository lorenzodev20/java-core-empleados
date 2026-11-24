package com.empleados.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Empresa extends EntidadBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String razonSocial;
    private String ruc;
    private String direccionFiscal;

    // Colección de unidades orgánicas (Relación 1 a Muchos)
    private List<UnidadOrganica> unidadesOrganicas;

    // Constructor vacío inicializando la lista para evitar NullPointerException
    public Empresa() {
        super();
        this.unidadesOrganicas = new ArrayList<>();
    }

    public Empresa(String razonSocial, String ruc, String direccionFiscal) {
        this();
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.direccionFiscal = direccionFiscal;
    }

    // Metodo helper para mantener la relación bidireccional limpia
    public void agregarUnidadOrganica(UnidadOrganica unidad) {
        if (unidad != null) {
            this.unidadesOrganicas.add(unidad);
            unidad.setEmpresa(this);
        }
    }

    // Getters y Setters
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public List<UnidadOrganica> getUnidadesOrganicas() {
        return unidadesOrganicas;
    }

    public void setUnidadesOrganicas(List<UnidadOrganica> unidadesOrganicas) {
        this.unidadesOrganicas = unidadesOrganicas != null ? unidadesOrganicas : new ArrayList<>();
    }
}
