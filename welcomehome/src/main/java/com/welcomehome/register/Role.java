package com.welcomehome.register;

import org.springframework.data.relational.core.mapping.Column;

public class Role {


    private String roleID;

    private String rDescription;

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getrDescription() {
        return rDescription;
    }

    public void setrDescription(String rDescription) {
        this.rDescription = rDescription;
    }
}
