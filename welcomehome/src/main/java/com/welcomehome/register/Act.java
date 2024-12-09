package com.welcomehome.register;

import org.springframework.data.relational.core.mapping.Column;

public class Act {

    @Column
    private String userName;
    @Column
    private Role role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
