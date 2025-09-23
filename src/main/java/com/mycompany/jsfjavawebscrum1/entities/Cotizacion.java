/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yisus
 */
@Entity
@Table(name = "cotizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cotizacion.findAll", query = "SELECT c FROM Cotizacion c"),
    @NamedQuery(name = "Cotizacion.findByIdCotizacion", query = "SELECT c FROM Cotizacion c WHERE c.idCotizacion = :idCotizacion"),
    @NamedQuery(name = "Cotizacion.findByNombreProveedor", query = "SELECT c FROM Cotizacion c WHERE c.nombreProveedor = :nombreProveedor"),
    @NamedQuery(name = "Cotizacion.findByIDProducto", query = "SELECT c FROM Cotizacion c WHERE c.iDProducto = :iDProducto"),
    @NamedQuery(name = "Cotizacion.findByCantidadProducto", query = "SELECT c FROM Cotizacion c WHERE c.cantidadProducto = :cantidadProducto")})
public class Cotizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCotizacion")
    private Integer idCotizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_Proveedor")
    private String nombreProveedor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_Producto")
    private int iDProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cantidad_Producto")
    private int cantidadProducto;
    @JoinColumn(name = "Proveedor_idProveedor", referencedColumnName = "idProveedor")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proveedor proveedoridProveedor;

    public Cotizacion() {
    }

    public Cotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Cotizacion(Integer idCotizacion, String nombreProveedor, int iDProducto, int cantidadProducto) {
        this.idCotizacion = idCotizacion;
        this.nombreProveedor = nombreProveedor;
        this.iDProducto = iDProducto;
        this.cantidadProducto = cantidadProducto;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getIDProducto() {
        return iDProducto;
    }

    public void setIDProducto(int iDProducto) {
        this.iDProducto = iDProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Proveedor getProveedoridProveedor() {
        return proveedoridProveedor;
    }

    public void setProveedoridProveedor(Proveedor proveedoridProveedor) {
        this.proveedoridProveedor = proveedoridProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCotizacion != null ? idCotizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cotizacion)) {
            return false;
        }
        Cotizacion other = (Cotizacion) object;
        if ((this.idCotizacion == null && other.idCotizacion != null) || (this.idCotizacion != null && !this.idCotizacion.equals(other.idCotizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.Cotizacion[ idCotizacion=" + idCotizacion + " ]";
    }
    
}
