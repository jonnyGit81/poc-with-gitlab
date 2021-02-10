package com.poc.web.form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteAdminUserFormTest {


    @Test
    void getId() {
        DeleteAdminUserForm form = new DeleteAdminUserForm();
        form.setId(1L);
        assertEquals(1L, form.getId());
    }

    @Test
    void setId() {
    }
}
