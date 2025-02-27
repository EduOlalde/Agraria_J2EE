package com.ecofield.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario en el sistema.
 *
 * @author Eduardo Olalde
 */
public class Usuario {

    private int id;
    private String nombre;
    private String email;
    private String contrasenia;
    private String telefono;
    private boolean habilitado;
    private List<Rol> roles = new ArrayList<>();

    public Usuario(String nombre, String email, String contrasenia, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
    }

    public Usuario(int id, String nombre, String email, String contrasenia, String telefono, boolean habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.habilitado = habilitado;
    }
    
    public Usuario(int id, String nombre, String email, String telefono, boolean habilitado, List<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = null;
        this.telefono = telefono;
        this.habilitado = habilitado;
        this.roles = roles;
    }
    
    public boolean verificarContrasena(String passwordIngresada) {
        return passwordIngresada.equals(this.contrasenia);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", email=" + email + ", contrasenia=" + contrasenia + ", telefono=" + telefono + ", habilitado=" + habilitado + ", roles=" + roles + '}';
    }

}
