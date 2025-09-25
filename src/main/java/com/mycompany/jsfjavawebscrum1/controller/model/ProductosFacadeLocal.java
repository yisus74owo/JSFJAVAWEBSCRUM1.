package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.Productos;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProductosFacadeLocal {

    void create(Productos productos);

    void edit(Productos productos);

    void remove(Productos productos);

    Productos find(Object id);

    List<Productos> findAll();

    List<Productos> findRange(int[] range);

    int count();

    // Métodos adicionales para el resumen
    List<Productos> findStockBajo(double porcentaje, int limite);

    List<Productos> findProximosExpirar(int meses, int limite);

    List<Object[]> resumenPorCategorias();

    public boolean existeProductoPorNombre(String nombreProducto);
}
