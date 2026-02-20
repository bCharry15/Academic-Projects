/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.model;

/**
 *
 * @author jpuen
 */
public class Usuario {

    public enum Rol {
        ADMIN,
        USER
    }

    private int id;
    private String username;
    private String passwordHash;
    private Rol rol;

    public Usuario() {}

    public Usuario(int id, String username, String passwordHash, Rol rol) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public boolean esAdmin() { return this.rol == Rol.ADMIN; }

    @Override
    public String toString() {
        return username + " [" + rol.name() + "]";
    }
}

