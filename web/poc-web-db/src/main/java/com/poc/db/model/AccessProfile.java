package com.poc.db.model;

import com.poc.db.base.BaseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "access_profile")
public class AccessProfile extends BaseModel implements Serializable {
    private static final long serialVersionUID = -5937990802699980170L;

    @Column(name = "name", length = 100, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "access_profile_page",
            joinColumns = @JoinColumn(name = "access_profile_id"), inverseJoinColumns = @JoinColumn(name = "page_id"))
    private Set<Page> pages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }
}
