package com.ecofield.modelos;

import com.ecofield.utils.Seguridad;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Representa un usuario en el sistema. Esta clase almacena la información
 * personal de un usuario, como su nombre, correo electrónico, teléfono,
 * contraseña y estado de habilitación. Además, gestiona los roles del usuario
 * en el sistema.
 *
 * @author Eduardo Olalde
 */
public class Usuario {

    /**
     * El identificador único del usuario
     */
    private int id;

    /**
     * El nombre completo del usuario
     */
    private String nombre;

    /**
     * El correo electrónico del usuario
     */
    private String email;

    /**
     * La contraseña del usuario
     */
    private String contrasenia;

    /**
     * El número de teléfono del usuario
     */
    private String telefono;

    /**
     * Indica si el usuario está habilitado en el sistema
     */
    private boolean habilitado;

    /**
     * La lista de roles asociados al usuario
     */
    private List<Rol> roles = new ArrayList<>();

    /**
     * Constructor vacío para crear un usuario sin datos iniciales.
     */
    public Usuario() {
    }

    /**
     * Constructor para crear un usuario con su identificador y nombre.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre completo del usuario.
     */
    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Constructor para crear un usuario con su identificador, nombre, correo
     * electrónico y estado de habilitación.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre completo del usuario.
     * @param email El correo electrónico del usuario.
     * @param habilitado El estado de habilitación del usuario (true si está
     * habilitado, false si no lo está).
     */
    public Usuario(int id, String nombre, String email, boolean habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.habilitado = habilitado;
    }

    /**
     * Constructor para crear un usuario con su identificador, nombre y correo
     * electrónico.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre completo del usuario.
     * @param email El correo electrónico del usuario.
     */
    public Usuario(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    /**
     * Constructor para crear un usuario con su nombre, correo electrónico,
     * contraseña y teléfono.
     *
     * @param nombre El nombre completo del usuario.
     * @param email El correo electrónico del usuario.
     * @param contrasenia La contraseña del usuario.
     * @param telefono El número de teléfono del usuario.
     */
    public Usuario(String nombre, String email, String contrasenia, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
    }

    /**
     * Constructor para crear un usuario con todos sus datos: id, nombre, email,
     * contraseña, teléfono, habilitación y roles.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre completo del usuario.
     * @param email El correo electrónico del usuario.
     * @param contrasenia La contraseña del usuario.
     * @param telefono El número de teléfono del usuario.
     * @param habilitado El estado de habilitación del usuario.
     */
    public Usuario(int id, String nombre, String email, String contrasenia, String telefono, boolean habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.habilitado = habilitado;
    }

    /**
     * Constructor para crear un usuario con su id, nombre, email, teléfono,
     * habilitación y lista de roles.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre completo del usuario.
     * @param email El correo electrónico del usuario.
     * @param telefono El número de teléfono del usuario.
     * @param habilitado El estado de habilitación del usuario.
     * @param roles La lista de roles asociados al usuario.
     */
    public Usuario(int id, String nombre, String email, String telefono, boolean habilitado, List<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = null;
        this.telefono = telefono;
        this.habilitado = habilitado;
        this.roles = roles;
    }

    /**
     * Verifica si la contraseña ingresada coincide con la almacenada.
     *
     * @param passwordIngresada La contraseña ingresada para verificar.
     * @return true si la contraseña ingresada es correcta, false si no lo es.
     */
    public boolean verificarContrasena(String passwordIngresada) {
        return Seguridad.verificarPassword(passwordIngresada, this.contrasenia);
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return El identificador único del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario.
     *
     * @param id El identificador único del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return El nombre completo del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del usuario.
     *
     * @param nombre El nombre completo del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Establece la contraseña del usuario usando encriptación con BCrypt.
     *
     * @param contrasenia La contraseña del usuario.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = Seguridad.hashPassword(contrasenia);
    }

    /**
     * Obtiene el número de teléfono del usuario.
     *
     * @return El número de teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del usuario.
     *
     * @param telefono El número de teléfono del usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Verifica si el usuario está habilitado en el sistema.
     *
     * @return true si el usuario está habilitado, false si no lo está.
     */
    public boolean isHabilitado() {
        return habilitado;
    }

    /**
     * Establece el estado de habilitación del usuario.
     *
     * @param habilitado El estado de habilitación del usuario.
     */
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * Obtiene la lista de roles asociados al usuario.
     *
     * @return La lista de roles del usuario.
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * Establece la lista de roles asociados al usuario.
     *
     * @param roles La lista de roles a asociar al usuario.
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    /**
     * Devuelve una representación en formato de cadena del usuario.
     *
     * @return Una cadena que representa el usuario con todos sus detalles.
     */
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", email=" + email + ", contrasenia=" + contrasenia + ", telefono=" + telefono + ", habilitado=" + habilitado + ", roles=" + roles + '}';
    }

}
