package com.poc.db.base;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 3412645638388762383L;

    public static final String FIELD_ID = "id";
    public static final String FIELD_VERSION = "version";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Long id;

    @Version
    @Column(name = "version", nullable = false)
    public Long version;

    @Column(name = "created_date", nullable = false)
    public LocalDateTime createdDate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(this.id == null) {
            return  false;
        }
        if(o instanceof BaseModel && o.getClass().equals(getClass())) {
            return this.id.equals(((BaseModel) o).getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
       int hash = 5;
       hash = 43 * hash + Objects.hashCode(this.id);
       return hash;
    }
}
