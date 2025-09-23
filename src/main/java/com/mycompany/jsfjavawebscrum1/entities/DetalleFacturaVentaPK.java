/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author yisus
 */
@Embeddable
public class DetalleFacturaVentaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idDetalle_factura_venta")
    private int idDetallefacturaventa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Factura_venta_idFacura_venta")
    private String facturaventaidFacuraventa;

    public DetalleFacturaVentaPK() {
    }

    public DetalleFacturaVentaPK(int idDetallefacturaventa, String facturaventaidFacuraventa) {
        this.idDetallefacturaventa = idDetallefacturaventa;
        this.facturaventaidFacuraventa = facturaventaidFacuraventa;
    }

    public int getIdDetallefacturaventa() {
        return idDetallefacturaventa;
    }

    public void setIdDetallefacturaventa(int idDetallefacturaventa) {
        this.idDetallefacturaventa = idDetallefacturaventa;
    }

    public String getFacturaventaidFacuraventa() {
        return facturaventaidFacuraventa;
    }

    public void setFacturaventaidFacuraventa(String facturaventaidFacuraventa) {
        this.facturaventaidFacuraventa = facturaventaidFacuraventa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDetallefacturaventa;
        hash += (facturaventaidFacuraventa != null ? facturaventaidFacuraventa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFacturaVentaPK)) {
            return false;
        }
        DetalleFacturaVentaPK other = (DetalleFacturaVentaPK) object;
        if (this.idDetallefacturaventa != other.idDetallefacturaventa) {
            return false;
        }
        if ((this.facturaventaidFacuraventa == null && other.facturaventaidFacuraventa != null) || (this.facturaventaidFacuraventa != null && !this.facturaventaidFacuraventa.equals(other.facturaventaidFacuraventa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.DetalleFacturaVentaPK[ idDetallefacturaventa=" + idDetallefacturaventa + ", facturaventaidFacuraventa=" + facturaventaidFacuraventa + " ]";
    }
    
}
