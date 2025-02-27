/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofield.modelos;

import java.util.List;

/**
 *
 * @author Eduardo Olalde
 */
public class Maquinista extends Usuario {

    private List<Integer> especialidades;

    public Maquinista() {
        super();
    }

    public Maquinista(int id, String nombre, String email, boolean habilitado, List<Integer> especialidades) {
        super(id, nombre, email, habilitado);
        this.especialidades = especialidades;
    }

    public List<Integer> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Integer> especialidades) {
        this.especialidades = especialidades;
    }

    @Override
    public String toString() {
        return super.toString() +  ", especialidades=" + especialidades + '}';
    }

}
