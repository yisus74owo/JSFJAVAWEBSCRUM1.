/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.FacturaVenta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface FacturaVentaFacadeLocal {

    void create(FacturaVenta facturaVenta);

    void edit(FacturaVenta facturaVenta);

    void remove(FacturaVenta facturaVenta);

    FacturaVenta find(Object id);

    List<FacturaVenta> findAll();

    List<FacturaVenta> findRange(int[] range);

    int count();
    
}
