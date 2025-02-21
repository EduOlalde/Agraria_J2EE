
package com.ecofield.modelos;

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
    private boolean habilitado;

    public Usuario(int id, String nombre, String email, String contrasenia, boolean habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.habilitado = habilitado;
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

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", email=" + email + ", contrasenia=" + contrasenia + ", habilitado=" + habilitado + '}';
    }

}
