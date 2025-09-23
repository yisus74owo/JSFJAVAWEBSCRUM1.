/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.CabeceraOrdenCompra;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author yisus
 */
@Stateless
public class CabeceraOrdenCompraFacade extends AbstractFacade<CabeceraOrdenCompra> implements CabeceraOrdenCompraFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_JSFJAVAWEBSCRUM1_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CabeceraOrdenCompraFacade() {
        super(CabeceraOrdenCompra.class);
    }
    
}
