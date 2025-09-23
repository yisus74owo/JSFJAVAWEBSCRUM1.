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
public class NovedadInventarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_Novedad")
    private int iDNovedad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Productos_idProducto")
    private int productosidProducto;

    public NovedadInventarioPK() {
    }

    public NovedadInventarioPK(int iDNovedad, int productosidProducto) {
        this.iDNovedad = iDNovedad;
        this.productosidProducto = productosidProducto;
    }

    public int getIDNovedad() {
        return iDNovedad;
    }

    public void setIDNovedad(int iDNovedad) {
        this.iDNovedad = iDNovedad;
    }

    public int getProductosidProducto() {
        return productosidProducto;
    }

    public void setProductosidProducto(int productosidProducto) {
        this.productosidProducto = productosidProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDNovedad;
        hash += (int) productosidProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadInventarioPK)) {
            return false;
        }
        NovedadInventarioPK other = (NovedadInventarioPK) object;
        if (this.iDNovedad != other.iDNovedad) {
            return false;
        }
        if (this.productosidProducto != other.productosidProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.NovedadInventarioPK[ iDNovedad=" + iDNovedad + ", productosidProducto=" + productosidProducto + " ]";
    }
    
}
