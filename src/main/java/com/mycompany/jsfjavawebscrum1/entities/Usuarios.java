package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdUsuario", query = "SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuarios.findByNombreusuario", query = "SELECT u FROM Usuarios u WHERE u.nombreusuario = :nombreusuario"),
    @NamedQuery(name = "Usuarios.findByApellidousuario", query = "SELECT u FROM Usuarios u WHERE u.apellidousuario = :apellidousuario"),
    @NamedQuery(name = "Usuarios.findByTelefono", query = "SELECT u FROM Usuarios u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuarios.findByDireccion", query = "SELECT u FROM Usuarios u WHERE u.direccion = :direccion"),
    @NamedQuery(name = "Usuarios.findByContraseña", query = "SELECT u FROM Usuarios u WHERE u.contraseña = :contraseña"),
    @NamedQuery(name = "Usuarios.findByEstadousuario", query = "SELECT u FROM Usuarios u WHERE u.estadousuario = :estadousuario"),
    @NamedQuery(name = "Usuarios.findByTipodocumento", query = "SELECT u FROM Usuarios u WHERE u.tipodocumento = :tipodocumento"),
    @NamedQuery(name = "Usuarios.findByDocumentousuario", query = "SELECT u FROM Usuarios u WHERE u.documentousuario = :documentousuario")
})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_usuario")
    private String nombreusuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Apellido_usuario")
    private String apellidousuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "Telefono")
    private String telefono;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Correo")
    private String correo;

    @Size(max = 45)
    @Column(name = "Direccion")
    private String direccion;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Contraseña")
    private String contraseña;

    @Basic(optional = false)
    @NotNull
    @Column(name = "Estado_usuario")
    private short estadousuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "Tipo_documento")
    private String tipodocumento;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Documento_usuario")
    private String documentousuario;

    @JoinColumn(name = "Rol_idRol", referencedColumnName = "idRol")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Roles rolidRol;

    public Usuarios() {
    }

    public Usuarios(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuarios(Integer idUsuario, String nombreusuario, String apellidousuario, String telefono, String correo, String contraseña, short estadousuario, String tipodocumento, String documentousuario) {
        this.idUsuario = idUsuario;
        this.nombreusuario = nombreusuario;
        this.apellidousuario = apellidousuario;
        this.telefono = telefono;
        this.correo = correo;
        this.contraseña = contraseña;
        this.estadousuario = estadousuario;
        this.tipodocumento = tipodocumento;
        this.documentousuario = documentousuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getApellidousuario() {
        return apellidousuario;
    }

    public void setApellidousuario(String apellidousuario) {
        this.apellidousuario = apellidousuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public short getEstadousuario() {
        return estadousuario;
    }

    public void setEstadousuario(short estadousuario) {
        this.estadousuario = estadousuario;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getDocumentousuario() {
        return documentousuario;
    }

    public void setDocumentousuario(String documentousuario) {
        this.documentousuario = documentousuario;
    }

    public Roles getRolidRol() {
        return rolidRol;
    }

    public void setRolidRol(Roles rolidRol) {
        this.rolidRol = rolidRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        return !((this.idUsuario == null && other.idUsuario != null) || 
                 (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario)));
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.Usuarios[ idUsuario=" + idUsuario + " ]";
    }
}
