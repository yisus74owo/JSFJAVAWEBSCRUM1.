/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.DetalleOrdenCompra;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface DetalleOrdenCompraFacadeLocal {

    void create(DetalleOrdenCompra detalleOrdenCompra);

    void edit(DetalleOrdenCompra detalleOrdenCompra);

    void remove(DetalleOrdenCompra detalleOrdenCompra);

    DetalleOrdenCompra find(Object id);

    List<DetalleOrdenCompra> findAll();

    List<DetalleOrdenCompra> findRange(int[] range);

    int count();
    
}
