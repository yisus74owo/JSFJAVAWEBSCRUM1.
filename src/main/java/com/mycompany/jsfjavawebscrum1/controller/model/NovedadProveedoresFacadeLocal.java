/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.NovedadProveedores;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface NovedadProveedoresFacadeLocal {

    void create(NovedadProveedores novedadProveedores);

    void edit(NovedadProveedores novedadProveedores);

    void remove(NovedadProveedores novedadProveedores);

    NovedadProveedores find(Object id);

    List<NovedadProveedores> findAll();

    List<NovedadProveedores> findRange(int[] range);

    int count();
    
}
