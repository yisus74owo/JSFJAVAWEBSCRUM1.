/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.Mascota;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface MascotaFacadeLocal {

    void create(Mascota mascota);

    void edit(Mascota mascota);

    void remove(Mascota mascota);

    Mascota find(Object id);

    List<Mascota> findAll();

    List<Mascota> findRange(int[] range);

    int count();
    
}
