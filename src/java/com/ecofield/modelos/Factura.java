package com.ecofield.modelos;

import java.sql.Date;

/**
 * Representa una factura en el sistema.
 * Esta clase contiene información sobre la factura, como el ID de la factura, el ID del trabajo asociado,
 * el estado de la factura, las fechas de emisión y pago, y el monto de la factura.
 * 
 * @author Eduardo Olalde
 */
public class Factura {

    /** ID único de la factura */
    private final int idFactura;
    
    /** ID del trabajo asociado a la factura */
    private final int idTrabajo;
    
    /** Estado actual de la factura (e.g., pendiente, pagada) */
    private String estado;
    
    /** Fecha de emisión de la factura */
    private final Date fechaEmision;
    
    /** Fecha en que se realizó el pago de la factura */
    private Date fechaPago;
    
    /** Monto total de la factura */
    private double monto;

    /**
     * Constructor de la clase Factura.
     * 
     * @param idFactura El ID único de la factura.
     * @param idTrabajo El ID del trabajo asociado a la factura.
     * @param estado El estado de la factura (e.g., pendiente, pagada).
     * @param fechaEmision La fecha en que se emitió la factura.
     * @param fechaPago La fecha en que se pagó la factura (puede ser nula si no ha sido pagada).
     * @param monto El monto total de la factura.
     */
    public Factura(int idFactura, int idTrabajo, String estado, Date fechaEmision, Date fechaPago, double monto) {
        this.idFactura = idFactura;
        this.idTrabajo = idTrabajo;
        this.estado = estado;
        this.fechaEmision = fechaEmision;
        this.fechaPago = fechaPago;
        this.monto = monto;
    }

    /**
     * Obtiene el ID de la factura.
     * 
     * @return El ID de la factura.
     */
    public int getIdFactura() {
        return idFactura;
    }

    /**
     * Obtiene el ID del trabajo asociado a la factura.
     * 
     * @return El ID del trabajo.
     */
    public int getIdTrabajo() {
        return idTrabajo;
    }

    /**
     * Obtiene la fecha de emisión de la factura.
     * 
     * @return La fecha de emisión de la factura.
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Obtiene el estado de la factura.
     * 
     * @return El estado de la factura.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la factura.
     * 
     * @param estado El nuevo estado de la factura.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la fecha de pago de la factura.
     * 
     * @return La fecha de pago de la factura (puede ser nula si no ha sido pagada).
     */
    public Date getFechaPago() {
        return fechaPago;
    }

    /**
     * Establece la fecha de pago de la factura.
     * 
     * @param fechaPago La fecha en que se pagó la factura.
     */
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * Obtiene el monto total de la factura.
     * 
     * @return El monto de la factura.
     */
    public double getMonto() {
        return monto;
    }

    /**
     * Establece el monto total de la factura.
     * 
     * @param monto El nuevo monto de la factura.
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * Devuelve una representación en formato de cadena de la factura.
     * 
     * @return Una cadena que representa la factura con todos sus atributos.
     */
    @Override
    public String toString() {
        return "Factura{"
                + "idFactura=" + idFactura
                + ", idTrabajo=" + idTrabajo
                + ", estado=" + estado
                + ", fechaEmision=" + fechaEmision
                + ", fechaPago=" + fechaPago
                + ", monto=" + monto
                + '}';
    }
}
