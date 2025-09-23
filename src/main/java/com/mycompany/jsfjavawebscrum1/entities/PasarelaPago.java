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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pasarela_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PasarelaPago.findAll", query = "SELECT p FROM PasarelaPago p"),
    @NamedQuery(name = "PasarelaPago.findByTotal", query = "SELECT p FROM PasarelaPago p WHERE p.total = :total"),
    @NamedQuery(name = "PasarelaPago.findByMetodoPago", query = "SELECT p FROM PasarelaPago p WHERE p.metodoPago = :metodoPago"),
    @NamedQuery(name = "PasarelaPago.findByDireccionEnvio", query = "SELECT p FROM PasarelaPago p WHERE p.direccionEnvio = :direccionEnvio"),
    @NamedQuery(name = "PasarelaPago.findByIdPasarelaPago", query = "SELECT p FROM PasarelaPago p WHERE p.idPasarelaPago = :idPasarelaPago")})
public class PasarelaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Total")
    private double total;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Metodo_Pago")
    private String metodoPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Direccion_Envio")
    private String direccionEnvio;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPasarela_Pago")
    private Integer idPasarelaPago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasarelaPagoidPasarelaPago", fetch = FetchType.LAZY)
    private List<CarritoCompra> carritoCompraList;
    @JoinColumn(name = "Informacion_Domicilio_Direccion_Envio", referencedColumnName = "Direccion_Envio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private InformacionDomicilio informacionDomicilioDireccionEnvio;

    public PasarelaPago() {
    }

    public PasarelaPago(Integer idPasarelaPago) {
        this.idPasarelaPago = idPasarelaPago;
    }

    public PasarelaPago(Integer idPasarelaPago, double total, String metodoPago, String direccionEnvio) {
        this.idPasarelaPago = idPasarelaPago;
        this.total = total;
        this.metodoPago = metodoPago;
        this.direccionEnvio = direccionEnvio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public Integer getIdPasarelaPago() {
        return idPasarelaPago;
    }

    public void setIdPasarelaPago(Integer idPasarelaPago) {
        this.idPasarelaPago = idPasarelaPago;
    }

    @XmlTransient
    public List<CarritoCompra> getCarritoCompraList() {
        return carritoCompraList;
    }

    public void setCarritoCompraList(List<CarritoCompra> carritoCompraList) {
        this.carritoCompraList = carritoCompraList;
    }

    public InformacionDomicilio getInformacionDomicilioDireccionEnvio() {
        return informacionDomicilioDireccionEnvio;
    }

    public void setInformacionDomicilioDireccionEnvio(InformacionDomicilio informacionDomicilioDireccionEnvio) {
        this.informacionDomicilioDireccionEnvio = informacionDomicilioDireccionEnvio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPasarelaPago != null ? idPasarelaPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PasarelaPago)) {
            return false;
        }
        PasarelaPago other = (PasarelaPago) object;
        if ((this.idPasarelaPago == null && other.idPasarelaPago != null) || (this.idPasarelaPago != null && !this.idPasarelaPago.equals(other.idPasarelaPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.PasarelaPago[ idPasarelaPago=" + idPasarelaPago + " ]";
    }
    
}
