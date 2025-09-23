/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.PasarelaPago;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author yisus
 */
@Stateless
public class PasarelaPagoFacade extends AbstractFacade<PasarelaPago> implements PasarelaPagoFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_JSFJAVAWEBSCRUM1_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PasarelaPagoFacade() {
        super(PasarelaPago.class);
    }
    
}
