/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yisus
 */
@Entity
@Table(name = "novedad_inventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadInventario.findAll", query = "SELECT n FROM NovedadInventario n"),
    @NamedQuery(name = "NovedadInventario.findByIDNovedad", query = "SELECT n FROM NovedadInventario n WHERE n.novedadInventarioPK.iDNovedad = :iDNovedad"),
    @NamedQuery(name = "NovedadInventario.findByIDProducto", query = "SELECT n FROM NovedadInventario n WHERE n.iDProducto = :iDProducto"),
    @NamedQuery(name = "NovedadInventario.findByIDInsumo", query = "SELECT n FROM NovedadInventario n WHERE n.iDInsumo = :iDInsumo"),
    @NamedQuery(name = "NovedadInventario.findByFechaNovedad", query = "SELECT n FROM NovedadInventario n WHERE n.fechaNovedad = :fechaNovedad"),
    @NamedQuery(name = "NovedadInventario.findByDescripcion", query = "SELECT n FROM NovedadInventario n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NovedadInventario.findByInventarioidInsumo", query = "SELECT n FROM NovedadInventario n WHERE n.inventarioidInsumo = :inventarioidInsumo"),
    @NamedQuery(name = "NovedadInventario.findByProductosidProducto", query = "SELECT n FROM NovedadInventario n WHERE n.novedadInventarioPK.productosidProducto = :productosidProducto")})
public class NovedadInventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NovedadInventarioPK novedadInventarioPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_Producto")
    private int iDProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_Insumo")
    private int iDInsumo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha_Novedad")
    @Temporal(TemporalType.DATE)
    private Date fechaNovedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Inventario_idInsumo")
    private int inventarioidInsumo;
    @JoinColumn(name = "Productos_idProducto", referencedColumnName = "idProducto", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Productos productos;

    public NovedadInventario() {
    }

    public NovedadInventario(NovedadInventarioPK novedadInventarioPK) {
        this.novedadInventarioPK = novedadInventarioPK;
    }

    public NovedadInventario(NovedadInventarioPK novedadInventarioPK, int iDProducto, int iDInsumo, Date fechaNovedad, String descripcion, int inventarioidInsumo) {
        this.novedadInventarioPK = novedadInventarioPK;
        this.iDProducto = iDProducto;
        this.iDInsumo = iDInsumo;
        this.fechaNovedad = fechaNovedad;
        this.descripcion = descripcion;
        this.inventarioidInsumo = inventarioidInsumo;
    }

    public NovedadInventario(int iDNovedad, int productosidProducto) {
        this.novedadInventarioPK = new NovedadInventarioPK(iDNovedad, productosidProducto);
    }

    public NovedadInventarioPK getNovedadInventarioPK() {
        return novedadInventarioPK;
    }

    public void setNovedadInventarioPK(NovedadInventarioPK novedadInventarioPK) {
        this.novedadInventarioPK = novedadInventarioPK;
    }

    public int getIDProducto() {
        return iDProducto;
    }

    public void setIDProducto(int iDProducto) {
        this.iDProducto = iDProducto;
    }

    public int getIDInsumo() {
        return iDInsumo;
    }

    public void setIDInsumo(int iDInsumo) {
        this.iDInsumo = iDInsumo;
    }

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getInventarioidInsumo() {
        return inventarioidInsumo;
    }

    public void setInventarioidInsumo(int inventarioidInsumo) {
        this.inventarioidInsumo = inventarioidInsumo;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (novedadInventarioPK != null ? novedadInventarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadInventario)) {
            return false;
        }
        NovedadInventario other = (NovedadInventario) object;
        if ((this.novedadInventarioPK == null && other.novedadInventarioPK != null) || (this.novedadInventarioPK != null && !this.novedadInventarioPK.equals(other.novedadInventarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.NovedadInventario[ novedadInventarioPK=" + novedadInventarioPK + " ]";
    }
    
}
