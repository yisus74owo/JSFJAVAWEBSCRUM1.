package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author yisus
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_JSFJAVAWEBSCRUM1_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
    /**
     * Busca un usuario por correo. Hace JOIN FETCH del rol para evitar LazyInitialization
     * y maneja correctamente NoResult/NonUnique.
     */
    @Override
    public Usuarios findByCorreo(String correo) {
        try {
            TypedQuery<Usuarios> query = em.createQuery(
                "SELECT u FROM Usuarios u JOIN FETCH u.rolidRol WHERE u.correo = :correo",
                Usuarios.class
            );
            query.setParameter("correo", correo);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nure) {
            // Si por alguna razón hay duplicados, devolvemos el primero
            List<Usuarios> list = em.createQuery(
                "SELECT u FROM Usuarios u JOIN FETCH u.rolidRol WHERE u.correo = :correo",
                Usuarios.class
            )
            .setParameter("correo", correo)
            .setMaxResults(1)
            .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
