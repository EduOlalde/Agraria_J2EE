package com.ecofield.modelos;

/**
 * Representa una parcela en el sistema.
 *
 * @author Eduardo Olalde
 */
public class Parcela {

    private final int numParcela;
    private String idCatastro;
    private double extension;
    private int propietario;

    public Parcela(int numParcela, String idCatastro, double extension, int propietario) {
        this.numParcela = numParcela;
        this.idCatastro = idCatastro;
        this.extension = extension;
        this.propietario = propietario;
    }

    public int getNumParcela() {
        return numParcela;
    }

    public String getIdCatastro() {
        return idCatastro;
    }

    public double getExtension() {
        return extension;
    }

    public int getPropietario() {
        return propietario;
    }

    public void setIdCatastro(String idCatastro) {
        this.idCatastro = idCatastro;
    }

    public void setExtension(double extension) {
        this.extension = extension;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "Parcela{" + "numParcela=" + numParcela + ", idCatastro=" + idCatastro + ", extension=" + extension + ", propietario=" + propietario + '}';
    }

}
