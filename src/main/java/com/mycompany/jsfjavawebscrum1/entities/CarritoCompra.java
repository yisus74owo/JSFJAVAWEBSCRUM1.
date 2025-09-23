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
@Table(name = "carrito_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarritoCompra.findAll", query = "SELECT c FROM CarritoCompra c"),
    @NamedQuery(name = "CarritoCompra.findByIdCarritoCompra", query = "SELECT c FROM CarritoCompra c WHERE c.idCarritoCompra = :idCarritoCompra"),
    @NamedQuery(name = "CarritoCompra.findByTotal", query = "SELECT c FROM CarritoCompra c WHERE c.total = :total"),
    @NamedQuery(name = "CarritoCompra.findByCantidad", query = "SELECT c FROM CarritoCompra c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "CarritoCompra.findByMetodoPago", query = "SELECT c FROM CarritoCompra c WHERE c.metodoPago = :metodoPago"),
    @NamedQuery(name = "CarritoCompra.findByDireccionEnvio", query = "SELECT c FROM CarritoCompra c WHERE c.direccionEnvio = :direccionEnvio")})
public class CarritoCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCarrito_Compra")
    private Integer idCarritoCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Total")
    private double total;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cantidad")
    private int cantidad;
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
    @JoinColumn(name = "Landing_page_idLanding_page", referencedColumnName = "idLanding_page")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LandingPage landingpageidLandingpage;
    @JoinColumn(name = "Pasarela_Pago_idPasarela_Pago", referencedColumnName = "idPasarela_Pago")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PasarelaPago pasarelaPagoidPasarelaPago;

    public CarritoCompra() {
    }

    public CarritoCompra(Integer idCarritoCompra) {
        this.idCarritoCompra = idCarritoCompra;
    }

    public CarritoCompra(Integer idCarritoCompra, double total, int cantidad, String metodoPago, String direccionEnvio) {
        this.idCarritoCompra = idCarritoCompra;
        this.total = total;
        this.cantidad = cantidad;
        this.metodoPago = metodoPago;
        this.direccionEnvio = direccionEnvio;
    }

    public Integer getIdCarritoCompra() {
        return idCarritoCompra;
    }

    public void setIdCarritoCompra(Integer idCarritoCompra) {
        this.idCarritoCompra = idCarritoCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public LandingPage getLandingpageidLandingpage() {
        return landingpageidLandingpage;
    }

    public void setLandingpageidLandingpage(LandingPage landingpageidLandingpage) {
        this.landingpageidLandingpage = landingpageidLandingpage;
    }

    public PasarelaPago getPasarelaPagoidPasarelaPago() {
        return pasarelaPagoidPasarelaPago;
    }

    public void setPasarelaPagoidPasarelaPago(PasarelaPago pasarelaPagoidPasarelaPago) {
        this.pasarelaPagoidPasarelaPago = pasarelaPagoidPasarelaPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCarritoCompra != null ? idCarritoCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarritoCompra)) {
            return false;
        }
        CarritoCompra other = (CarritoCompra) object;
        if ((this.idCarritoCompra == null && other.idCarritoCompra != null) || (this.idCarritoCompra != null && !this.idCarritoCompra.equals(other.idCarritoCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.CarritoCompra[ idCarritoCompra=" + idCarritoCompra + " ]";
    }
    
}
