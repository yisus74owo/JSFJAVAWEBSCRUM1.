package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad Roles
 */
@Entity
@Table(name = "roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByIdRol", query = "SELECT r FROM Roles r WHERE r.idRol = :idRol"),
    @NamedQuery(name = "Roles.findByNombrerol", query = "SELECT r FROM Roles r WHERE r.nombrerol = :nombrerol")
})
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idRol")
    private Integer idRol;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Nombre_rol")
    private String nombrerol;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolidRol", fetch = FetchType.LAZY)
    private List<Usuarios> usuariosList;

    // Constructor vacío
    public Roles() {
    }

    // Constructor con id
    public Roles(Integer idRol) {
        this.idRol = idRol;
    }

    // Constructor con todos los campos
    public Roles(Integer idRol, String nombrerol) {
        this.idRol = idRol;
        this.nombrerol = nombrerol;
    }

    // Getters y setters
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombrerol() {
        return nombrerol;
    }

    public void setNombrerol(String nombrerol) {
        this.nombrerol = nombrerol;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    // hashCode y equals
    @Override
    public int hashCode() {
        return (idRol != null ? idRol.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) obj;
        return (this.idRol != null || other.idRol == null) && (this.idRol == null || this.idRol.equals(other.idRol));
    }

    // toString
    @Override
    public String toString() {
        return "Roles{idRol=" + idRol + ", nombrerol=" + nombrerol + "}";
    }
}

