package com.poc.db.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.db.model.Module;

import java.io.Serializable;
import java.util.List;

public class ModuleDto implements Serializable {
    private static final long serialVersionUID = -4529561748980947184L;
    private Long id;
    private String name;
    private int displayOrder;
    private String url;
    private String icon;
    private String label;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ModuleDto> subModules;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PageDto> pages;

    private ModuleDto() {

    }

    public static ModuleDto toDto (Module module) {
        if(null == module) {
            return null;
        }
        ModuleDto dto = new ModuleDto();
        dto.setId(module.getId());
        dto.setName(module.getName());
        dto.setDisplayOrder(module.getDisplayOrder());
        dto.setIcon(module.getIconName());
        dto.setUrl(module.getUrl());
        dto.setLabel(module.getLabel());
        return dto;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private void setIcon(String icon) {
        this.icon = icon;
    }

    private void setLabel(String label) {
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public String getUrl() {
        return url;
    }

    public String getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModuleDto moduleDto = (ModuleDto) o;

        if (!id.equals(moduleDto.id)) {
            return false;
        }
        return name.equals(moduleDto.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public List<ModuleDto> getSubModules() {
        return subModules;
    }

    public void setSubModules(List<ModuleDto> subModules) {
        this.subModules = subModules;
    }

    public List<PageDto> getPages() {
        return pages;
    }

    public void setPages(List<PageDto> pages) {
        this.pages = pages;
    }
}
