package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.Productos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.Calendar;

@Stateless
public class ProductosFacade extends AbstractFacade<Productos> implements ProductosFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_JSFJAVAWEBSCRUM1_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductosFacade() {
        super(Productos.class);
    }

    @Override
    public List<Productos> findStockBajo(double porcentaje, int limite) {
        TypedQuery<Productos> q = em.createQuery(
            "SELECT p FROM Productos p WHERE p.cantidadProducto < (p.stockProducto * :porc) ORDER BY (p.cantidadProducto / p.stockProducto) ASC",
            Productos.class
        );
        q.setParameter("porc", porcentaje);
        q.setMaxResults(limite);
        return q.getResultList();
    }

    @Override
    public List<Productos> findProximosExpirar(int meses, int limite) {
        // ✅ CORRECCIÓN: Consulta más simple y compatible
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, meses);
        Date fechaLimite = calendar.getTime();
        
        TypedQuery<Productos> q = em.createQuery(
            "SELECT p FROM Productos p WHERE p.fechaCaducidad BETWEEN CURRENT_DATE AND :fechaLimite ORDER BY p.fechaCaducidad ASC",
            Productos.class
        );
        q.setParameter("fechaLimite", fechaLimite);
        q.setMaxResults(limite);
        return q.getResultList();
    }

    @Override
    public List<Object[]> resumenPorCategorias() {
        return em.createQuery(
            "SELECT p.categoria, COUNT(p) FROM Productos p GROUP BY p.categoria", Object[].class
        ).getResultList();
    }
}