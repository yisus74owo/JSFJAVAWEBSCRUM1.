/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.CabeceraOrdenCompra;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface CabeceraOrdenCompraFacadeLocal {

    void create(CabeceraOrdenCompra cabeceraOrdenCompra);

    void edit(CabeceraOrdenCompra cabeceraOrdenCompra);

    void remove(CabeceraOrdenCompra cabeceraOrdenCompra);

    CabeceraOrdenCompra find(Object id);

    List<CabeceraOrdenCompra> findAll();

    List<CabeceraOrdenCompra> findRange(int[] range);

    int count();
    
}
