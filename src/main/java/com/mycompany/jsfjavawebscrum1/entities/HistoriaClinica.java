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
@Table(name = "historia_clinica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriaClinica.findAll", query = "SELECT h FROM HistoriaClinica h"),
    @NamedQuery(name = "HistoriaClinica.findByIdHistoriaClinica", query = "SELECT h FROM HistoriaClinica h WHERE h.idHistoriaClinica = :idHistoriaClinica"),
    @NamedQuery(name = "HistoriaClinica.findByNombremedicoveterinario", query = "SELECT h FROM HistoriaClinica h WHERE h.nombremedicoveterinario = :nombremedicoveterinario"),
    @NamedQuery(name = "HistoriaClinica.findByAnamnesis", query = "SELECT h FROM HistoriaClinica h WHERE h.anamnesis = :anamnesis"),
    @NamedQuery(name = "HistoriaClinica.findByExamenFisicoGeneral", query = "SELECT h FROM HistoriaClinica h WHERE h.examenFisicoGeneral = :examenFisicoGeneral"),
    @NamedQuery(name = "HistoriaClinica.findByAbordajeDiagnostico", query = "SELECT h FROM HistoriaClinica h WHERE h.abordajeDiagnostico = :abordajeDiagnostico"),
    @NamedQuery(name = "HistoriaClinica.findByExamenesResultados", query = "SELECT h FROM HistoriaClinica h WHERE h.examenesResultados = :examenesResultados"),
    @NamedQuery(name = "HistoriaClinica.findByDiagnosticoDefinitivo", query = "SELECT h FROM HistoriaClinica h WHERE h.diagnosticoDefinitivo = :diagnosticoDefinitivo"),
    @NamedQuery(name = "HistoriaClinica.findByPlanTerapeutico", query = "SELECT h FROM HistoriaClinica h WHERE h.planTerapeutico = :planTerapeutico"),
    @NamedQuery(name = "HistoriaClinica.findByPronostico", query = "SELECT h FROM HistoriaClinica h WHERE h.pronostico = :pronostico"),
    @NamedQuery(name = "HistoriaClinica.findByEvolucion", query = "SELECT h FROM HistoriaClinica h WHERE h.evolucion = :evolucion"),
    @NamedQuery(name = "HistoriaClinica.findByObservaciones", query = "SELECT h FROM HistoriaClinica h WHERE h.observaciones = :observaciones"),
    @NamedQuery(name = "HistoriaClinica.findByAnexos", query = "SELECT h FROM HistoriaClinica h WHERE h.anexos = :anexos")})
public class HistoriaClinica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHistoria_Clinica")
    private Integer idHistoriaClinica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre_medico_veterinario")
    private String nombremedicoveterinario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Anamnesis")
    private String anamnesis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Examen_Fisico_General")
    private String examenFisicoGeneral;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Abordaje_Diagnostico")
    private String abordajeDiagnostico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Examenes_Resultados")
    private String examenesResultados;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Diagnostico_Definitivo")
    private String diagnosticoDefinitivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Plan_Terapeutico")
    private String planTerapeutico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Pronostico")
    private String pronostico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Evolucion")
    private String evolucion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Observaciones")
    private String observaciones;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Anexos")
    private String anexos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historiaClinicaidHistoriaClinica", fetch = FetchType.LAZY)
    private List<Recetario> recetarioList;

    public HistoriaClinica() {
    }

    public HistoriaClinica(Integer idHistoriaClinica) {
        this.idHistoriaClinica = idHistoriaClinica;
    }

    public HistoriaClinica(Integer idHistoriaClinica, String nombremedicoveterinario, String anamnesis, String examenFisicoGeneral, String abordajeDiagnostico, String examenesResultados, String diagnosticoDefinitivo, String planTerapeutico, String pronostico, String evolucion, String observaciones, String anexos) {
        this.idHistoriaClinica = idHistoriaClinica;
        this.nombremedicoveterinario = nombremedicoveterinario;
        this.anamnesis = anamnesis;
        this.examenFisicoGeneral = examenFisicoGeneral;
        this.abordajeDiagnostico = abordajeDiagnostico;
        this.examenesResultados = examenesResultados;
        this.diagnosticoDefinitivo = diagnosticoDefinitivo;
        this.planTerapeutico = planTerapeutico;
        this.pronostico = pronostico;
        this.evolucion = evolucion;
        this.observaciones = observaciones;
        this.anexos = anexos;
    }

    public Integer getIdHistoriaClinica() {
        return idHistoriaClinica;
    }

    public void setIdHistoriaClinica(Integer idHistoriaClinica) {
        this.idHistoriaClinica = idHistoriaClinica;
    }

    public String getNombremedicoveterinario() {
        return nombremedicoveterinario;
    }

    public void setNombremedicoveterinario(String nombremedicoveterinario) {
        this.nombremedicoveterinario = nombremedicoveterinario;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getExamenFisicoGeneral() {
        return examenFisicoGeneral;
    }

    public void setExamenFisicoGeneral(String examenFisicoGeneral) {
        this.examenFisicoGeneral = examenFisicoGeneral;
    }

    public String getAbordajeDiagnostico() {
        return abordajeDiagnostico;
    }

    public void setAbordajeDiagnostico(String abordajeDiagnostico) {
        this.abordajeDiagnostico = abordajeDiagnostico;
    }

    public String getExamenesResultados() {
        return examenesResultados;
    }

    public void setExamenesResultados(String examenesResultados) {
        this.examenesResultados = examenesResultados;
    }

    public String getDiagnosticoDefinitivo() {
        return diagnosticoDefinitivo;
    }

    public void setDiagnosticoDefinitivo(String diagnosticoDefinitivo) {
        this.diagnosticoDefinitivo = diagnosticoDefinitivo;
    }

    public String getPlanTerapeutico() {
        return planTerapeutico;
    }

    public void setPlanTerapeutico(String planTerapeutico) {
        this.planTerapeutico = planTerapeutico;
    }

    public String getPronostico() {
        return pronostico;
    }

    public void setPronostico(String pronostico) {
        this.pronostico = pronostico;
    }

    public String getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(String evolucion) {
        this.evolucion = evolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAnexos() {
        return anexos;
    }

    public void setAnexos(String anexos) {
        this.anexos = anexos;
    }

    @XmlTransient
    public List<Recetario> getRecetarioList() {
        return recetarioList;
    }

    public void setRecetarioList(List<Recetario> recetarioList) {
        this.recetarioList = recetarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistoriaClinica != null ? idHistoriaClinica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaClinica)) {
            return false;
        }
        HistoriaClinica other = (HistoriaClinica) object;
        if ((this.idHistoriaClinica == null && other.idHistoriaClinica != null) || (this.idHistoriaClinica != null && !this.idHistoriaClinica.equals(other.idHistoriaClinica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfjavawebscrum1.entities.HistoriaClinica[ idHistoriaClinica=" + idHistoriaClinica + " ]";
    }
    
}
