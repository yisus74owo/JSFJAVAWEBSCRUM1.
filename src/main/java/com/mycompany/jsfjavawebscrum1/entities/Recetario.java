/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.entities;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "recetario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recetario.findAll", query = "SELECT r FROM Recetario r"),
    @NamedQuery(name = "Recetario.findByIdRecetario", query = "SELECT r FROM Recetario r WHERE r.idRecetario = :idRecetario"),
    @NamedQuery(name = "Recetario.findByNombremedicoveterinario", query = "SELECT r FROM Recetario r WHERE r.nombremedicoveterinario = :nombremedicoveterinario"),
    @NamedQuery(name = "Recetario.findByMedicamento", query = "SELECT r FROM Recetario r WHERE r.medicamento = :medicamento"),
    @NamedQuery(name = "Recetario.findByDosis", query = "SELECT r FROM Recetario r WHERE r.dosis = :dosis"),
    @NamedQuery(name = "Recetario.findByFrecuencia", query = "SELECT r FROM Recetario r WHERE r.frecuencia = :frecuencia"),
    @NamedQuery(name = "Recetario.findByRecomendaciones", query = "SELECT r FROM Recetario r WHERE r.recomendaciones = :recomendaciones"),
    @NamedQuery(name = "Recetario.findByDieta", query = "SELECT r FROM Recetario r WHERE r.dieta = :dieta"),
    @NamedQuery(name = "Recetario.findByObservaciones", query = "SELECT r FROM Recetario r WHERE r.observaciones = :observaciones")})
public class Recetario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRecetario")
    private Integer idRecetario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_medico_veterinario")
    private String nombremedicoveterinario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Medicamento")
    private String medicamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Dosis")
    private double dosis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Frecuencia")
    private String frecuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Recomendaciones")
    private String recomendaciones;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Dieta")
    private String dieta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Observaciones")
    private String observaciones;
    @OneToMany(mappedBy = "recetarioidRecetario", fetch = FetchType.LAZY)
    private List<Mascota> mascotaList;
    @JoinColumn(name = "Historia_Clinica_idHistoria_Clinica", referencedColumnName = "idHistoria_Clinica")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HistoriaClinica historiaClinicaidHistoriaClinica;

    public Recetario() {
    }

    public Recetario(Integer idRecetario) {
        this.idRecetario = idRecetario;
    }

    public Recetario(Integer idRecetario, String nombremedicoveterinario, String medicamento, double dosis, String frecuencia, String recomendaciones, String dieta, String observaciones) {
        this.idRecetario = idRecetario;
        this.nombremedicoveterinario = nombremedicoveterinario;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.recomendaciones = recomendaciones;
        this.dieta = dieta;
        this.observaciones = observaciones;
    }

    public Integer getIdRecetario() {
        return idRecetario;
    }

    public void setIdRecetario(Integer idRecetario) {
        this.idRecetario = idRecetario;
    }

    public String getNombremedicoveterinario() {
        return nombremedicoveterinario;
    }

    public void setNombremedicoveterinario(String nombremedicoveterinario) {
        this.nombremedicoveterinario = nombremedicoveterinario;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public double getDosis() {
        return dosis;
    }

    public void setDosis(double dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<Mascota> getMascotaList() {
        return mascotaList;
    }

    public void setMascotaList(List<Mascota> mascotaList) {
        this.mascotaList = mascotaList;
    }

    public HistoriaClinica getHistoriaClinicaidHistoriaClinica() {
        return historiaClinicaidHistoriaClinica;
    }

    public void setHistoriaClinicaidHistoriaClinica(HistoriaClinica historiaClinicaidHistoriaClinica) {
        this.historiaClinicaidHistoriaClinica = historiaClinicaidHistoriaClinica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecetario != null ? idRecetario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recetario)) {
            return false;
        }
        Recetario other = (Recetario) object;
        if ((this.idRecetario == null && other.idRecetario != null) || (this.idRecetario != null && !this.idRecetario.equals(other.idRecetario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.Recetario[ idRecetario=" + idRecetario + " ]";
    }
    
}
