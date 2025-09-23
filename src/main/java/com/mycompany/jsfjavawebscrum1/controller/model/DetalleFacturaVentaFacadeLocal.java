/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.DetalleFacturaVenta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface DetalleFacturaVentaFacadeLocal {

    void create(DetalleFacturaVenta detalleFacturaVenta);

    void edit(DetalleFacturaVenta detalleFacturaVenta);

    void remove(DetalleFacturaVenta detalleFacturaVenta);

    DetalleFacturaVenta find(Object id);

    List<DetalleFacturaVenta> findAll();

    List<DetalleFacturaVenta> findRange(int[] range);

    int count();
    
}
