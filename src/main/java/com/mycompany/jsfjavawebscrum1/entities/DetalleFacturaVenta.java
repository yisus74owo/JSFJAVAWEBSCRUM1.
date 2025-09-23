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
@Table(name = "detalle_factura_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleFacturaVenta.findAll", query = "SELECT d FROM DetalleFacturaVenta d"),
    @NamedQuery(name = "DetalleFacturaVenta.findByIdDetallefacturaventa", query = "SELECT d FROM DetalleFacturaVenta d WHERE d.detalleFacturaVentaPK.idDetallefacturaventa = :idDetallefacturaventa"),
    @NamedQuery(name = "DetalleFacturaVenta.findByCantidadproductos", query = "SELECT d FROM DetalleFacturaVenta d WHERE d.cantidadproductos = :cantidadproductos"),
    @NamedQuery(name = "DetalleFacturaVenta.findByDetalleproductos", query = "SELECT d FROM DetalleFacturaVenta d WHERE d.detalleproductos = :detalleproductos"),
    @NamedQuery(name = "DetalleFacturaVenta.findByNombreproductos", query = "SELECT d FROM DetalleFacturaVenta d WHERE d.nombreproductos = :nombreproductos"),
    @NamedQuery(name = "DetalleFacturaVenta.findByIva", query = "SELECT d FROM DetalleFacturaVenta d WHERE d.iva = :iva"),
    @NamedQuery(name = "DetalleFacturaVenta.findByTotal", query = "SELECT d FROM DetalleFacturaVenta d WHERE d.total = :total"),
    @NamedQuery(name = "DetalleFacturaVenta.findByDescuentos", query = "SELECT d FROM DetalleFacturaVenta d WHERE d.descuentos = :descuentos"),
    @NamedQuery(name = "DetalleFacturaVenta.findByFacturaventaidFacuraventa", query = "SELECT d FROM DetalleFacturaVenta d WHERE d.detalleFacturaVentaPK.facturaventaidFacuraventa = :facturaventaidFacuraventa")})
public class DetalleFacturaVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetalleFacturaVentaPK detalleFacturaVentaPK;
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
    @JoinColumn(name = "Factura_venta_idFacura_venta", referencedColumnName = "idFacura_venta", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FacturaVenta facturaVenta;

    public DetalleFacturaVenta() {
    }

    public DetalleFacturaVenta(DetalleFacturaVentaPK detalleFacturaVentaPK) {
        this.detalleFacturaVentaPK = detalleFacturaVentaPK;
    }

    public DetalleFacturaVenta(DetalleFacturaVentaPK detalleFacturaVentaPK, int cantidadproductos, String detalleproductos, String nombreproductos, float iva, double total, float descuentos) {
        this.detalleFacturaVentaPK = detalleFacturaVentaPK;
        this.cantidadproductos = cantidadproductos;
        this.detalleproductos = detalleproductos;
        this.nombreproductos = nombreproductos;
        this.iva = iva;
        this.total = total;
        this.descuentos = descuentos;
    }

    public DetalleFacturaVenta(int idDetallefacturaventa, String facturaventaidFacuraventa) {
        this.detalleFacturaVentaPK = new DetalleFacturaVentaPK(idDetallefacturaventa, facturaventaidFacuraventa);
    }

    public DetalleFacturaVentaPK getDetalleFacturaVentaPK() {
        return detalleFacturaVentaPK;
    }

    public void setDetalleFacturaVentaPK(DetalleFacturaVentaPK detalleFacturaVentaPK) {
        this.detalleFacturaVentaPK = detalleFacturaVentaPK;
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

    public FacturaVenta getFacturaVenta() {
        return facturaVenta;
    }

    public void setFacturaVenta(FacturaVenta facturaVenta) {
        this.facturaVenta = facturaVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleFacturaVentaPK != null ? detalleFacturaVentaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFacturaVenta)) {
            return false;
        }
        DetalleFacturaVenta other = (DetalleFacturaVenta) object;
        if ((this.detalleFacturaVentaPK == null && other.detalleFacturaVentaPK != null) || (this.detalleFacturaVentaPK != null && !this.detalleFacturaVentaPK.equals(other.detalleFacturaVentaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.DetalleFacturaVenta[ detalleFacturaVentaPK=" + detalleFacturaVentaPK + " ]";
    }
    
}
