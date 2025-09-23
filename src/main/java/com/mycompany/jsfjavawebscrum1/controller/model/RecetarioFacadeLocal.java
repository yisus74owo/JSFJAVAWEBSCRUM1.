/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.Recetario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface RecetarioFacadeLocal {

    void create(Recetario recetario);

    void edit(Recetario recetario);

    void remove(Recetario recetario);

    Recetario find(Object id);

    List<Recetario> findAll();

    List<Recetario> findRange(int[] range);

    int count();
    
}
