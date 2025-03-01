/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.modelos;

import java.util.List;

/**
 * Representa a un maquinista en el sistema.
 * Esta clase extiende de la clase {@link Usuario} y agrega la funcionalidad de manejar las especialidades del maquinista.
 * Un maquinista tiene una lista de especialidades que indica los tipos de máquinas con los que está capacitado.
 * 
 * @author Eduardo Olalde
 */
public class Maquinista extends Usuario {

    /** Lista de especialidades del maquinista (ID de los tipos de máquinas) */
    private List<Integer> especialidades;

    /**
     * Constructor vacío de la clase Maquinista.
     * Este constructor invoca al constructor de la clase {@link Usuario} sin parámetros.
     */
    public Maquinista() {
        super();
    }

    /**
     * Constructor de la clase Maquinista con parámetros.
     * 
     * @param id El ID del maquinista.
     * @param nombre El nombre del maquinista.
     * @param email El correo electrónico del maquinista.
     * @param habilitado El estado de habilitación del maquinista.
     * @param especialidades La lista de especialidades del maquinista, representadas por los ID de los tipos de máquinas con los que está capacitado.
     */
    public Maquinista(int id, String nombre, String email, boolean habilitado, List<Integer> especialidades) {
        super(id, nombre, email, habilitado);
        this.especialidades = especialidades;
    }

    /**
     * Obtiene la lista de especialidades del maquinista.
     * 
     * @return La lista de especialidades del maquinista.
     */
    public List<Integer> getEspecialidades() {
        return especialidades;
    }

    /**
     * Establece las especialidades del maquinista.
     * 
     * @param especialidades La lista de especialidades del maquinista.
     */
    public void setEspecialidades(List<Integer> especialidades) {
        this.especialidades = especialidades;
    }

    /**
     * Devuelve una representación en formato de cadena del maquinista.
     * La representación incluye los atributos heredados de la clase {@link Usuario} y las especialidades del maquinista.
     * 
     * @return Una cadena que representa al maquinista con todos sus atributos.
     */
    @Override
    public String toString() {
        return super.toString() + ", especialidades=" + especialidades + '}';
    }

}
