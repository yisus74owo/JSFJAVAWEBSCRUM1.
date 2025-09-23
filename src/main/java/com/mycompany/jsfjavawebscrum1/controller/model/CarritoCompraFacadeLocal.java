/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.CarritoCompra;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface CarritoCompraFacadeLocal {

    void create(CarritoCompra carritoCompra);

    void edit(CarritoCompra carritoCompra);

    void remove(CarritoCompra carritoCompra);

    CarritoCompra find(Object id);

    List<CarritoCompra> findAll();

    List<CarritoCompra> findRange(int[] range);

    int count();
    
}
