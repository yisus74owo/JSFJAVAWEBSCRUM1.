/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.HistoriaClinica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface HistoriaClinicaFacadeLocal {

    void create(HistoriaClinica historiaClinica);

    void edit(HistoriaClinica historiaClinica);

    void remove(HistoriaClinica historiaClinica);

    HistoriaClinica find(Object id);

    List<HistoriaClinica> findAll();

    List<HistoriaClinica> findRange(int[] range);

    int count();
    
}
