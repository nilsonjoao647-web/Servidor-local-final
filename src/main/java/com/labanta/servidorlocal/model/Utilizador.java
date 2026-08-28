package com.labanta.servidorlocal.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Utilizador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    // Construtor vazio
    public Utilizador() {

    }

    // Getters e Setters

    public Long getId() {return this.id;}
    public String getUsername() {return username;}
    public String getPassword() {return this.password;}
    public String getEmail() {return this.email;}

    public void setId(Long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}

}
