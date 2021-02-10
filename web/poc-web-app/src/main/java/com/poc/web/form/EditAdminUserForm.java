package com.poc.web.form;


import javax.validation.constraints.NotNull;

public class EditAdminUserForm {
    @NotNull(message = "User ID are required")
    private Long id;
    private String accessLevel;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "EditAdminUserForm{" +
                "id=" + id +
                ", accessLevel='" + accessLevel + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
