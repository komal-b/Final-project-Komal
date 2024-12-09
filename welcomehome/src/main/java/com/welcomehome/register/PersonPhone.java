package com.welcomehome.register;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.stereotype.Repository;

@Repository
public class PersonPhone {

    @Column
    private String userName;
    @Column
    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PersonPhone{" +
                "userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
