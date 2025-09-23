/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "acciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acciones.findAll", query = "SELECT a FROM Acciones a"),
    @NamedQuery(name = "Acciones.findByIdAccion", query = "SELECT a FROM Acciones a WHERE a.idAccion = :idAccion"),
    @NamedQuery(name = "Acciones.findByAccionescol", query = "SELECT a FROM Acciones a WHERE a.accionescol = :accionescol"),
    @NamedQuery(name = "Acciones.findByNombrerol", query = "SELECT a FROM Acciones a WHERE a.nombrerol = :nombrerol")})
public class Acciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAccion")
    private Integer idAccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Accionescol")
    private String accionescol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_rol")
    private String nombrerol;

    public Acciones() {
    }

    public Acciones(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public Acciones(Integer idAccion, String accionescol, String nombrerol) {
        this.idAccion = idAccion;
        this.accionescol = accionescol;
        this.nombrerol = nombrerol;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public String getAccionescol() {
        return accionescol;
    }

    public void setAccionescol(String accionescol) {
        this.accionescol = accionescol;
    }

    public String getNombrerol() {
        return nombrerol;
    }

    public void setNombrerol(String nombrerol) {
        this.nombrerol = nombrerol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccion != null ? idAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acciones)) {
            return false;
        }
        Acciones other = (Acciones) object;
        if ((this.idAccion == null && other.idAccion != null) || (this.idAccion != null && !this.idAccion.equals(other.idAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.Acciones[ idAccion=" + idAccion + " ]";
    }
    
}
