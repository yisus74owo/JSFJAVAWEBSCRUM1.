/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.Promocion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface PromocionFacadeLocal {

    void create(Promocion promocion);

    void edit(Promocion promocion);

    void remove(Promocion promocion);

    Promocion find(Object id);

    List<Promocion> findAll();

    List<Promocion> findRange(int[] range);

    int count();
    
}
