/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yisus
 */
@Entity
@Table(name = "factura_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaVenta.findAll", query = "SELECT f FROM FacturaVenta f"),
    @NamedQuery(name = "FacturaVenta.findByIdFacuraventa", query = "SELECT f FROM FacturaVenta f WHERE f.idFacuraventa = :idFacuraventa"),
    @NamedQuery(name = "FacturaVenta.findByNombrecliente", query = "SELECT f FROM FacturaVenta f WHERE f.nombrecliente = :nombrecliente"),
    @NamedQuery(name = "FacturaVenta.findByFechafactura", query = "SELECT f FROM FacturaVenta f WHERE f.fechafactura = :fechafactura")})
public class FacturaVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idFacura_venta")
    private String idFacuraventa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_cliente")
    private String nombrecliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha_factura")
    @Temporal(TemporalType.DATE)
    private Date fechafactura;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturaVenta", fetch = FetchType.LAZY)
    private List<DetalleFacturaVenta> detalleFacturaVentaList;
    @JoinColumn(name = "Productos_idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Productos productosidProducto;

    public FacturaVenta() {
    }

    public FacturaVenta(String idFacuraventa) {
        this.idFacuraventa = idFacuraventa;
    }

    public FacturaVenta(String idFacuraventa, String nombrecliente, Date fechafactura) {
        this.idFacuraventa = idFacuraventa;
        this.nombrecliente = nombrecliente;
        this.fechafactura = fechafactura;
    }

    public String getIdFacuraventa() {
        return idFacuraventa;
    }

    public void setIdFacuraventa(String idFacuraventa) {
        this.idFacuraventa = idFacuraventa;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public Date getFechafactura() {
        return fechafactura;
    }

    public void setFechafactura(Date fechafactura) {
        this.fechafactura = fechafactura;
    }

    @XmlTransient
    public List<DetalleFacturaVenta> getDetalleFacturaVentaList() {
        return detalleFacturaVentaList;
    }

    public void setDetalleFacturaVentaList(List<DetalleFacturaVenta> detalleFacturaVentaList) {
        this.detalleFacturaVentaList = detalleFacturaVentaList;
    }

    public Productos getProductosidProducto() {
        return productosidProducto;
    }

    public void setProductosidProducto(Productos productosidProducto) {
        this.productosidProducto = productosidProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacuraventa != null ? idFacuraventa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaVenta)) {
            return false;
        }
        FacturaVenta other = (FacturaVenta) object;
        if ((this.idFacuraventa == null && other.idFacuraventa != null) || (this.idFacuraventa != null && !this.idFacuraventa.equals(other.idFacuraventa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.FacturaVenta[ idFacuraventa=" + idFacuraventa + " ]";
    }
    
}
