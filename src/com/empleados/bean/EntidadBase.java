package com.empleados.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class EntidadBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected UUID id;

    protected EntidadBase() {
        this.id = UUID.randomUUID();
    }

    protected EntidadBase(UUID id) {
        this.id = id != null ? id : UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadBase that = (EntidadBase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}