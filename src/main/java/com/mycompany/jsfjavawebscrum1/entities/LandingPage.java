/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yisus
 */
@Entity
@Table(name = "landing_page")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LandingPage.findAll", query = "SELECT l FROM LandingPage l"),
    @NamedQuery(name = "LandingPage.findByIdLandingpage", query = "SELECT l FROM LandingPage l WHERE l.idLandingpage = :idLandingpage"),
    @NamedQuery(name = "LandingPage.findByNombreProducto", query = "SELECT l FROM LandingPage l WHERE l.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "LandingPage.findByPrecioProducto", query = "SELECT l FROM LandingPage l WHERE l.precioProducto = :precioProducto"),
    @NamedQuery(name = "LandingPage.findByDescripcionProducto", query = "SELECT l FROM LandingPage l WHERE l.descripcionProducto = :descripcionProducto"),
    @NamedQuery(name = "LandingPage.findByCategoria", query = "SELECT l FROM LandingPage l WHERE l.categoria = :categoria"),
    @NamedQuery(name = "LandingPage.findByMarcaProducto", query = "SELECT l FROM LandingPage l WHERE l.marcaProducto = :marcaProducto")})
public class LandingPage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLanding_page")
    private Integer idLandingpage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_Producto")
    private String nombreProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Precio_Producto")
    private double precioProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Descripcion_Producto")
    private String descripcionProducto;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "Imagen_Producto")
    private byte[] imagenProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Categoria")
    private String categoria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Marca_Producto")
    private String marcaProducto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "landingpageidLandingpage", fetch = FetchType.LAZY)
    private List<Promocion> promocionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "landingpageidLandingpage", fetch = FetchType.LAZY)
    private List<CarritoCompra> carritoCompraList;

    public LandingPage() {
    }

    public LandingPage(Integer idLandingpage) {
        this.idLandingpage = idLandingpage;
    }

    public LandingPage(Integer idLandingpage, String nombreProducto, double precioProducto, String descripcionProducto, byte[] imagenProducto, String categoria, String marcaProducto) {
        this.idLandingpage = idLandingpage;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.descripcionProducto = descripcionProducto;
        this.imagenProducto = imagenProducto;
        this.categoria = categoria;
        this.marcaProducto = marcaProducto;
    }

    public Integer getIdLandingpage() {
        return idLandingpage;
    }

    public void setIdLandingpage(Integer idLandingpage) {
        this.idLandingpage = idLandingpage;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public byte[] getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(byte[] imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    @XmlTransient
    public List<Promocion> getPromocionList() {
        return promocionList;
    }

    public void setPromocionList(List<Promocion> promocionList) {
        this.promocionList = promocionList;
    }

    @XmlTransient
    public List<CarritoCompra> getCarritoCompraList() {
        return carritoCompraList;
    }

    public void setCarritoCompraList(List<CarritoCompra> carritoCompraList) {
        this.carritoCompraList = carritoCompraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLandingpage != null ? idLandingpage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LandingPage)) {
            return false;
        }
        LandingPage other = (LandingPage) object;
        if ((this.idLandingpage == null && other.idLandingpage != null) || (this.idLandingpage != null && !this.idLandingpage.equals(other.idLandingpage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.LandingPage[ idLandingpage=" + idLandingpage + " ]";
    }
    
}
