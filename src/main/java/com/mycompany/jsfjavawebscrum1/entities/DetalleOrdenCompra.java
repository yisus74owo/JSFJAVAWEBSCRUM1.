/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "detalle_orden_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleOrdenCompra.findAll", query = "SELECT d FROM DetalleOrdenCompra d"),
    @NamedQuery(name = "DetalleOrdenCompra.findByIdDetallefacturaventa", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.detalleOrdenCompraPK.idDetallefacturaventa = :idDetallefacturaventa"),
    @NamedQuery(name = "DetalleOrdenCompra.findByCantidadproductos", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.cantidadproductos = :cantidadproductos"),
    @NamedQuery(name = "DetalleOrdenCompra.findByDetalleproductos", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.detalleproductos = :detalleproductos"),
    @NamedQuery(name = "DetalleOrdenCompra.findByNombreproductos", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.nombreproductos = :nombreproductos"),
    @NamedQuery(name = "DetalleOrdenCompra.findByIva", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.iva = :iva"),
    @NamedQuery(name = "DetalleOrdenCompra.findByTotal", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.total = :total"),
    @NamedQuery(name = "DetalleOrdenCompra.findByDescuentos", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.descuentos = :descuentos"),
    @NamedQuery(name = "DetalleOrdenCompra.findByMetodopago", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.metodopago = :metodopago"),
    @NamedQuery(name = "DetalleOrdenCompra.findByOrdencompraidOrdenCompra", query = "SELECT d FROM DetalleOrdenCompra d WHERE d.detalleOrdenCompraPK.ordencompraidOrdenCompra = :ordencompraidOrdenCompra")})
public class DetalleOrdenCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetalleOrdenCompraPK detalleOrdenCompraPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cantidad_productos")
    private int cantidadproductos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Detalle_productos")
    private String detalleproductos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_productos")
    private String nombreproductos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IVA")
    private float iva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Total")
    private double total;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Descuentos")
    private float descuentos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Metodo_pago")
    private String metodopago;
    @JoinColumn(name = "Orden_compra_idOrden_Compra", referencedColumnName = "idOrden_Compra", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CabeceraOrdenCompra cabeceraOrdenCompra;

    public DetalleOrdenCompra() {
    }

    public DetalleOrdenCompra(DetalleOrdenCompraPK detalleOrdenCompraPK) {
        this.detalleOrdenCompraPK = detalleOrdenCompraPK;
    }

    public DetalleOrdenCompra(DetalleOrdenCompraPK detalleOrdenCompraPK, int cantidadproductos, String detalleproductos, String nombreproductos, float iva, double total, float descuentos, String metodopago) {
        this.detalleOrdenCompraPK = detalleOrdenCompraPK;
        this.cantidadproductos = cantidadproductos;
        this.detalleproductos = detalleproductos;
        this.nombreproductos = nombreproductos;
        this.iva = iva;
        this.total = total;
        this.descuentos = descuentos;
        this.metodopago = metodopago;
    }

    public DetalleOrdenCompra(int idDetallefacturaventa, int ordencompraidOrdenCompra) {
        this.detalleOrdenCompraPK = new DetalleOrdenCompraPK(idDetallefacturaventa, ordencompraidOrdenCompra);
    }

    public DetalleOrdenCompraPK getDetalleOrdenCompraPK() {
        return detalleOrdenCompraPK;
    }

    public void setDetalleOrdenCompraPK(DetalleOrdenCompraPK detalleOrdenCompraPK) {
        this.detalleOrdenCompraPK = detalleOrdenCompraPK;
    }

    public int getCantidadproductos() {
        return cantidadproductos;
    }

    public void setCantidadproductos(int cantidadproductos) {
        this.cantidadproductos = cantidadproductos;
    }

    public String getDetalleproductos() {
        return detalleproductos;
    }

    public void setDetalleproductos(String detalleproductos) {
        this.detalleproductos = detalleproductos;
    }

    public String getNombreproductos() {
        return nombreproductos;
    }

    public void setNombreproductos(String nombreproductos) {
        this.nombreproductos = nombreproductos;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public float getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(float descuentos) {
        this.descuentos = descuentos;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }

    public CabeceraOrdenCompra getCabeceraOrdenCompra() {
        return cabeceraOrdenCompra;
    }

    public void setCabeceraOrdenCompra(CabeceraOrdenCompra cabeceraOrdenCompra) {
        this.cabeceraOrdenCompra = cabeceraOrdenCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleOrdenCompraPK != null ? detalleOrdenCompraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleOrdenCompra)) {
            return false;
        }
        DetalleOrdenCompra other = (DetalleOrdenCompra) object;
        if ((this.detalleOrdenCompraPK == null && other.detalleOrdenCompraPK != null) || (this.detalleOrdenCompraPK != null && !this.detalleOrdenCompraPK.equals(other.detalleOrdenCompraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.DetalleOrdenCompra[ detalleOrdenCompraPK=" + detalleOrdenCompraPK + " ]";
    }
    
}
