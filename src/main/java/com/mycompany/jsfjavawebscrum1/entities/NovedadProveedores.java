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
@Table(name = "novedad_proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NovedadProveedores.findAll", query = "SELECT n FROM NovedadProveedores n"),
    @NamedQuery(name = "NovedadProveedores.findByIdNovedad", query = "SELECT n FROM NovedadProveedores n WHERE n.idNovedad = :idNovedad"),
    @NamedQuery(name = "NovedadProveedores.findByNovedadDescripcion", query = "SELECT n FROM NovedadProveedores n WHERE n.novedadDescripcion = :novedadDescripcion"),
    @NamedQuery(name = "NovedadProveedores.findByFacturaEstado", query = "SELECT n FROM NovedadProveedores n WHERE n.facturaEstado = :facturaEstado")})
public class NovedadProveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idNovedad")
    private Integer idNovedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Novedad_Descripcion")
    private String novedadDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Factura_Estado")
    private String facturaEstado;
    @JoinColumn(name = "Proveedor_idProveedor", referencedColumnName = "idProveedor")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proveedor proveedoridProveedor;

    public NovedadProveedores() {
    }

    public NovedadProveedores(Integer idNovedad) {
        this.idNovedad = idNovedad;
    }

    public NovedadProveedores(Integer idNovedad, String novedadDescripcion, String facturaEstado) {
        this.idNovedad = idNovedad;
        this.novedadDescripcion = novedadDescripcion;
        this.facturaEstado = facturaEstado;
    }

    public Integer getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Integer idNovedad) {
        this.idNovedad = idNovedad;
    }

    public String getNovedadDescripcion() {
        return novedadDescripcion;
    }

    public void setNovedadDescripcion(String novedadDescripcion) {
        this.novedadDescripcion = novedadDescripcion;
    }

    public String getFacturaEstado() {
        return facturaEstado;
    }

    public void setFacturaEstado(String facturaEstado) {
        this.facturaEstado = facturaEstado;
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
        hash += (idNovedad != null ? idNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NovedadProveedores)) {
            return false;
        }
        NovedadProveedores other = (NovedadProveedores) object;
        if ((this.idNovedad == null && other.idNovedad != null) || (this.idNovedad != null && !this.idNovedad.equals(other.idNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.NovedadProveedores[ idNovedad=" + idNovedad + " ]";
    }
    
}
