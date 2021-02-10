package com.poc.db.dto;

import com.poc.db.model.Page;

import java.io.Serializable;

public  class PageDto implements Serializable {
    private static final long serialVersionUID = -7223263192483929991L;
    private Long id;
    private String name;
    private int displayOrder;
    private String url;
    private boolean visible;
    private String label;

    private PageDto() {

    }
    public static PageDto toDto(Page page) {
        PageDto pageDto = new PageDto();
        pageDto.setId(page.getId());
        pageDto.setName(page.getName());
        pageDto.setDisplayOrder(page.getDisplayOrder());
        String pageUrl = page.getUrl().startsWith("/") ? page.getUrl().substring(1) : page.getUrl();
        pageDto.setUrl(pageUrl);
        pageDto.setVisible(page.isVisible());
        pageDto.setLabel(page.getLabel());
        return pageDto;
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

    private void setVisible(boolean visible) {
        this.visible = visible;
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

    public boolean isVisible() {
        return visible;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        }
        if (o == null || getClass() != o.getClass())  {
            return false;
        }

        PageDto pageDto = (PageDto) o;

        if (!id.equals(pageDto.id)) {
            return false;
        }
        return name.equals(pageDto.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
