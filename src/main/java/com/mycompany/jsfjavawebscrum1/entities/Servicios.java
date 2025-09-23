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
@Table(name = "servicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicios.findAll", query = "SELECT s FROM Servicios s"),
    @NamedQuery(name = "Servicios.findByIdServicio", query = "SELECT s FROM Servicios s WHERE s.idServicio = :idServicio"),
    @NamedQuery(name = "Servicios.findByNombreServicio", query = "SELECT s FROM Servicios s WHERE s.nombreServicio = :nombreServicio"),
    @NamedQuery(name = "Servicios.findByCategoriaServicio", query = "SELECT s FROM Servicios s WHERE s.categoriaServicio = :categoriaServicio"),
    @NamedQuery(name = "Servicios.findByPrecioServicio", query = "SELECT s FROM Servicios s WHERE s.precioServicio = :precioServicio"),
    @NamedQuery(name = "Servicios.findByCorreo", query = "SELECT s FROM Servicios s WHERE s.correo = :correo"),
    @NamedQuery(name = "Servicios.findByNombreMascota", query = "SELECT s FROM Servicios s WHERE s.nombreMascota = :nombreMascota"),
    @NamedQuery(name = "Servicios.findByDescripcionServicio", query = "SELECT s FROM Servicios s WHERE s.descripcionServicio = :descripcionServicio"),
    @NamedQuery(name = "Servicios.findByCalendarioServicio", query = "SELECT s FROM Servicios s WHERE s.calendarioServicio = :calendarioServicio")})
public class Servicios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idServicio")
    private Integer idServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_Servicio")
    private String nombreServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Categoria_Servicio")
    private String categoriaServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Precio_Servicio")
    private double precioServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Nombre_Mascota")
    private String nombreMascota;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Descripcion_Servicio")
    private String descripcionServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Calendario_Servicio")
    @Temporal(TemporalType.DATE)
    private Date calendarioServicio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviciosidServicio", fetch = FetchType.LAZY)
    private List<Calendario> calendarioList;

    public Servicios() {
    }

    public Servicios(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Servicios(Integer idServicio, String nombreServicio, String categoriaServicio, double precioServicio, String correo, String nombreMascota, String descripcionServicio, Date calendarioServicio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.categoriaServicio = categoriaServicio;
        this.precioServicio = precioServicio;
        this.correo = correo;
        this.nombreMascota = nombreMascota;
        this.descripcionServicio = descripcionServicio;
        this.calendarioServicio = calendarioServicio;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getCategoriaServicio() {
        return categoriaServicio;
    }

    public void setCategoriaServicio(String categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    public double getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(double precioServicio) {
        this.precioServicio = precioServicio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public Date getCalendarioServicio() {
        return calendarioServicio;
    }

    public void setCalendarioServicio(Date calendarioServicio) {
        this.calendarioServicio = calendarioServicio;
    }

    @XmlTransient
    public List<Calendario> getCalendarioList() {
        return calendarioList;
    }

    public void setCalendarioList(List<Calendario> calendarioList) {
        this.calendarioList = calendarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicios)) {
            return false;
        }
        Servicios other = (Servicios) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.Servicios[ idServicio=" + idServicio + " ]";
    }
    
}
