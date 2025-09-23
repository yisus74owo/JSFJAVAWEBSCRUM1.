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

/**
 *
 * @author yisus
 */
@Embeddable
public class DetalleOrdenCompraPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idDetalle_factura_venta")
    private int idDetallefacturaventa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Orden_compra_idOrden_Compra")
    private int ordencompraidOrdenCompra;

    public DetalleOrdenCompraPK() {
    }

    public DetalleOrdenCompraPK(int idDetallefacturaventa, int ordencompraidOrdenCompra) {
        this.idDetallefacturaventa = idDetallefacturaventa;
        this.ordencompraidOrdenCompra = ordencompraidOrdenCompra;
    }

    public int getIdDetallefacturaventa() {
        return idDetallefacturaventa;
    }

    public void setIdDetallefacturaventa(int idDetallefacturaventa) {
        this.idDetallefacturaventa = idDetallefacturaventa;
    }

    public int getOrdencompraidOrdenCompra() {
        return ordencompraidOrdenCompra;
    }

    public void setOrdencompraidOrdenCompra(int ordencompraidOrdenCompra) {
        this.ordencompraidOrdenCompra = ordencompraidOrdenCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDetallefacturaventa;
        hash += (int) ordencompraidOrdenCompra;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleOrdenCompraPK)) {
            return false;
        }
        DetalleOrdenCompraPK other = (DetalleOrdenCompraPK) object;
        if (this.idDetallefacturaventa != other.idDetallefacturaventa) {
            return false;
        }
        if (this.ordencompraidOrdenCompra != other.ordencompraidOrdenCompra) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.DetalleOrdenCompraPK[ idDetallefacturaventa=" + idDetallefacturaventa + ", ordencompraidOrdenCompra=" + ordencompraidOrdenCompra + " ]";
    }
    
}
