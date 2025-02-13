
package com.ecofield.modelos;

import com.ecofield.enums.EstadoFactura;
import java.util.Date;

/**
 * Representa una factura en el sistema.
 *
 * @author Eduardo Olalde
 */
public class Factura {

    private final int idFactura;
    private final int idTrabajo;
    private EstadoFactura estado;
    private final Date fechaEmision;
    private Date fechaPago;
    private double monto;

    public Factura(int idFactura, int idTrabajo, EstadoFactura estado, Date fechaEmision, Date fechaPago, double monto) {
        this.idFactura = idFactura;
        this.idTrabajo = idTrabajo;
        this.estado = estado;
        this.fechaEmision = fechaEmision;
        this.fechaPago = fechaPago;
        this.monto = monto;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public int getIdTrabajo() {
        return idTrabajo;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

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
