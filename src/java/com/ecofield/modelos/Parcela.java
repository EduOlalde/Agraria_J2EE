package com.ecofield.modelos;

/**
 * Representa una parcela en el sistema.
 * Esta clase almacena información relevante sobre una parcela, como el número de parcela,
 * el ID catastral, la extensión y el propietario.
 * 
 * @author Eduardo Olalde
 */
public class Parcela {

    /** El número de identificación de la parcela */
    private int numParcela;

    /** El ID catastral de la parcela */
    private String idCatastro;

    /** La extensión de la parcela en hectáreas u otras unidades de medida */
    private double extension;

    /** El ID del propietario de la parcela */
    private int propietario;

    /**
     * Constructor de la clase Parcela sin el número de parcela.
     * 
     * @param idCatastro El ID catastral de la parcela.
     * @param extension La extensión de la parcela en unidades de medida.
     * @param propietario El ID del propietario de la parcela.
     */
    public Parcela(String idCatastro, double extension, int propietario) {
        this.idCatastro = idCatastro;
        this.extension = extension;
        this.propietario = propietario;
    }

    /**
     * Constructor de la clase Parcela con todos los parámetros.
     * 
     * @param numParcela El número de identificación de la parcela.
     * @param idCatastro El ID catastral de la parcela.
     * @param extension La extensión de la parcela en unidades de medida.
     * @param propietario El ID del propietario de la parcela.
     */
    public Parcela(int numParcela, String idCatastro, double extension, int propietario) {
        this.numParcela = numParcela;
        this.idCatastro = idCatastro;
        this.extension = extension;
        this.propietario = propietario;
    }

    /**
     * Establece el número de parcela.
     * 
     * @param numParcela El número de identificación de la parcela.
     */
    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    /**
     * Obtiene el número de parcela.
     * 
     * @return El número de identificación de la parcela.
     */
    public int getNumParcela() {
        return numParcela;
    }

    /**
     * Obtiene el ID catastral de la parcela.
     * 
     * @return El ID catastral de la parcela.
     */
    public String getIdCatastro() {
        return idCatastro;
    }

    /**
     * Obtiene la extensión de la parcela.
     * 
     * @return La extensión de la parcela en unidades de medida.
     */
    public double getExtension() {
        return extension;
    }

    /**
     * Obtiene el ID del propietario de la parcela.
     * 
     * @return El ID del propietario de la parcela.
     */
    public int getPropietario() {
        return propietario;
    }

    /**
     * Establece el ID catastral de la parcela.
     * 
     * @param idCatastro El ID catastral de la parcela.
     */
    public void setIdCatastro(String idCatastro) {
        this.idCatastro = idCatastro;
    }

    /**
     * Establece la extensión de la parcela.
     * 
     * @param extension La extensión de la parcela en unidades de medida.
     */
    public void setExtension(double extension) {
        this.extension = extension;
    }

    /**
     * Establece el ID del propietario de la parcela.
     * 
     * @param propietario El ID del propietario de la parcela.
     */
    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    /**
     * Devuelve una representación en formato de cadena de la parcela.
     * 
     * @return Una cadena que representa la parcela con todos sus atributos.
     */
    @Override
    public String toString() {
        return "Parcela{" + "numParcela=" + numParcela + ", idCatastro=" + idCatastro + ", extension=" + extension + ", propietario=" + propietario + '}';
    }

}
