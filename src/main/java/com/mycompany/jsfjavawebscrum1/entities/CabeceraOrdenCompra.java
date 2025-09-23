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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "cabecera_orden_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CabeceraOrdenCompra.findAll", query = "SELECT c FROM CabeceraOrdenCompra c"),
    @NamedQuery(name = "CabeceraOrdenCompra.findByIdOrdenCompra", query = "SELECT c FROM CabeceraOrdenCompra c WHERE c.idOrdenCompra = :idOrdenCompra"),
    @NamedQuery(name = "CabeceraOrdenCompra.findByFecha", query = "SELECT c FROM CabeceraOrdenCompra c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CabeceraOrdenCompra.findByNombreproveedor", query = "SELECT c FROM CabeceraOrdenCompra c WHERE c.nombreproveedor = :nombreproveedor"),
    @NamedQuery(name = "CabeceraOrdenCompra.findByCorreo", query = "SELECT c FROM CabeceraOrdenCompra c WHERE c.correo = :correo")})
public class CabeceraOrdenCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrden_Compra")
    private Integer idOrdenCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_proveedor")
    private String nombreproveedor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Correo")
    private String correo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cabeceraOrdenCompra", fetch = FetchType.LAZY)
    private List<DetalleOrdenCompra> detalleOrdenCompraList;
    @JoinColumn(name = "Productos_idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Productos productosidProducto;
    @JoinColumn(name = "Proveedor_idProveedor", referencedColumnName = "idProveedor")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proveedor proveedoridProveedor;

    public CabeceraOrdenCompra() {
    }

    public CabeceraOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public CabeceraOrdenCompra(Integer idOrdenCompra, Date fecha, String nombreproveedor, String correo) {
        this.idOrdenCompra = idOrdenCompra;
        this.fecha = fecha;
        this.nombreproveedor = nombreproveedor;
        this.correo = correo;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @XmlTransient
    public List<DetalleOrdenCompra> getDetalleOrdenCompraList() {
        return detalleOrdenCompraList;
    }

    public void setDetalleOrdenCompraList(List<DetalleOrdenCompra> detalleOrdenCompraList) {
        this.detalleOrdenCompraList = detalleOrdenCompraList;
    }

    public Productos getProductosidProducto() {
        return productosidProducto;
    }

    public void setProductosidProducto(Productos productosidProducto) {
        this.productosidProducto = productosidProducto;
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
        hash += (idOrdenCompra != null ? idOrdenCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabeceraOrdenCompra)) {
            return false;
        }
        CabeceraOrdenCompra other = (CabeceraOrdenCompra) object;
        if ((this.idOrdenCompra == null && other.idOrdenCompra != null) || (this.idOrdenCompra != null && !this.idOrdenCompra.equals(other.idOrdenCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.CabeceraOrdenCompra[ idOrdenCompra=" + idOrdenCompra + " ]";
    }
    
}
