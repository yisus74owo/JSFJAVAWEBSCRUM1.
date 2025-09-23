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
@Table(name = "informacion_domicilio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformacionDomicilio.findAll", query = "SELECT i FROM InformacionDomicilio i"),
    @NamedQuery(name = "InformacionDomicilio.findByTelefono", query = "SELECT i FROM InformacionDomicilio i WHERE i.telefono = :telefono"),
    @NamedQuery(name = "InformacionDomicilio.findByCorreo", query = "SELECT i FROM InformacionDomicilio i WHERE i.correo = :correo"),
    @NamedQuery(name = "InformacionDomicilio.findByHorarioEntrega", query = "SELECT i FROM InformacionDomicilio i WHERE i.horarioEntrega = :horarioEntrega"),
    @NamedQuery(name = "InformacionDomicilio.findByDireccionEnvio", query = "SELECT i FROM InformacionDomicilio i WHERE i.direccionEnvio = :direccionEnvio"),
    @NamedQuery(name = "InformacionDomicilio.findByEstadoEntrega", query = "SELECT i FROM InformacionDomicilio i WHERE i.estadoEntrega = :estadoEntrega"),
    @NamedQuery(name = "InformacionDomicilio.findByAuxiliaridAuxiliar", query = "SELECT i FROM InformacionDomicilio i WHERE i.auxiliaridAuxiliar = :auxiliaridAuxiliar")})
public class InformacionDomicilio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Telefono")
    private int telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Horario_Entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horarioEntrega;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Direccion_Envio")
    private String direccionEnvio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Estado_Entrega")
    private String estadoEntrega;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Auxiliar_idAuxiliar")
    private int auxiliaridAuxiliar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "informacionDomicilioDireccionEnvio", fetch = FetchType.LAZY)
    private List<PasarelaPago> pasarelaPagoList;

    public InformacionDomicilio() {
    }

    public InformacionDomicilio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public InformacionDomicilio(String direccionEnvio, int telefono, String correo, Date horarioEntrega, String estadoEntrega, int auxiliaridAuxiliar) {
        this.direccionEnvio = direccionEnvio;
        this.telefono = telefono;
        this.correo = correo;
        this.horarioEntrega = horarioEntrega;
        this.estadoEntrega = estadoEntrega;
        this.auxiliaridAuxiliar = auxiliaridAuxiliar;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getHorarioEntrega() {
        return horarioEntrega;
    }

    public void setHorarioEntrega(Date horarioEntrega) {
        this.horarioEntrega = horarioEntrega;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public int getAuxiliaridAuxiliar() {
        return auxiliaridAuxiliar;
    }

    public void setAuxiliaridAuxiliar(int auxiliaridAuxiliar) {
        this.auxiliaridAuxiliar = auxiliaridAuxiliar;
    }

    @XmlTransient
    public List<PasarelaPago> getPasarelaPagoList() {
        return pasarelaPagoList;
    }

    public void setPasarelaPagoList(List<PasarelaPago> pasarelaPagoList) {
        this.pasarelaPagoList = pasarelaPagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (direccionEnvio != null ? direccionEnvio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformacionDomicilio)) {
            return false;
        }
        InformacionDomicilio other = (InformacionDomicilio) object;
        if ((this.direccionEnvio == null && other.direccionEnvio != null) || (this.direccionEnvio != null && !this.direccionEnvio.equals(other.direccionEnvio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.InformacionDomicilio[ direccionEnvio=" + direccionEnvio + " ]";
    }
    
}
