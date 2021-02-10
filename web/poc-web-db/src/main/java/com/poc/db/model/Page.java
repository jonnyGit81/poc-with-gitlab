package com.poc.db.model;

import com.poc.db.base.BaseModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="page",
        indexes = {
                @Index(columnList = "module_id", name = "page_module_id_idx")
        })
public class Page extends BaseModel implements Serializable {
    private static final long serialVersionUID = -1606204634140872089L;

    @Column(name="name", unique=true, length = 120)
    private String name;

    @Column(name="label", length = 150, nullable = false)
    private String label;

    @Column(name="url", length = 200)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="module_id", nullable = false)
    private Module module;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="root_module_id", nullable = false)
    private Module rootModule;

    @Column(name="display_order", nullable = false)
    private int displayOrder;

    @Column(name="visible", nullable = false)
    private boolean visible = true;

    @Column(name="admin_page", nullable = false)
    private boolean adminPage = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Module getRootModule() {
        return rootModule;
    }

    public void setRootModule(Module rootModule) {
        this.rootModule = rootModule;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isAdminPage() {
        return adminPage;
    }

    public void setAdminPage(boolean adminPage) {
        this.adminPage = adminPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id='" + id + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(this.id == null) {
            return  false;
        }
        if(! super.equals(o) ) {
            return false;
        }
        if(o instanceof Page && o.getClass().equals(getClass())) {
            return this.name.equals(((Page) o).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
