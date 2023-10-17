package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;

    @Column(unique = true)
    private String userName;

    private String password;

    public Admin(){
        this.userName=null;
        this.password=null;
    }

    public Admin(String userName,String password){
        this.userName=userName;
        this.password=password;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public void setUserName(String userName){
         this.userName=userName;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
