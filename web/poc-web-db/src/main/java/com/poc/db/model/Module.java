package com.poc.db.model;

import com.poc.db.base.BaseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="module")
public class Module extends BaseModel implements Serializable {
    private static final long serialVersionUID = 5996138204305612923L;

    @Column(name = "name", length = 50, unique = true)
    private String name;

    @Column(name = "label", length = 150, nullable = false)
    private String label;

    @Column(name = "url", length = 200)
    private String url;

    @Column(name = "display_order", nullable = false)
    private int displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Module parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Module> childrens;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    private List<Page> pages;

    @Column(name = "icon_name", length = 30)
    private String iconName;

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

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Module getParent() {
        return parent;
    }

    public void setParent(Module parent) {
        this.parent = parent;
    }

    public List<Module> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Module> childrens) {
        this.childrens = childrens;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
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
        if(o instanceof Module && o.getClass().equals(getClass())) {
            return this.name.equals(((Module) o).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
       /* return "Module{" +
                "name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", displayOrder=" + displayOrder +
                 ", iconName='" + iconName + '\'' +
                '}';*/
        return "Module{" +
                "id='" + id + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
