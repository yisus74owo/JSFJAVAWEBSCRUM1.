package com.mycompany.jsfjavawebscrum1.controller.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import com.mycompany.jsfjavawebscrum1.controller.model.ProductosFacadeLocal;
import com.mycompany.jsfjavawebscrum1.entities.Productos;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/api/resumen")
public class ResumenInventarioServlet extends HttpServlet {

    @EJB
    private ProductosFacadeLocal productosFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            JSONObject resumen = new JSONObject();

            // ✅ STOCK BAJO - Con manejo de errores
            JSONArray stockBajo = new JSONArray();
            try {
                List<Productos> bajos = productosFacade.findStockBajo(0.4, 10);
                for (Productos p : bajos) {
                    JSONObject producto = new JSONObject();
                    producto.put("idProducto", p.getIdProducto());
                    producto.put("Nombre_Producto", p.getNombreProducto());
                    producto.put("Cantidad_Producto", p.getCantidadProducto());
                    producto.put("Stock_Producto", p.getStockProducto());
                    stockBajo.put(producto);
                }
            } catch (Exception e) {
                System.err.println("Error en stock bajo: " + e.getMessage());
            }
            resumen.put("stockBajo", stockBajo);

            // ✅ PRÓXIMOS A EXPIRAR - Con manejo de errores
            JSONArray proximosExpirar = new JSONArray();
            try {
                List<Productos> expiran = productosFacade.findProximosExpirar(3, 10);
                for (Productos p : expiran) {
                    JSONObject producto = new JSONObject();
                    producto.put("idProducto", p.getIdProducto());
                    producto.put("Nombre_Producto", p.getNombreProducto());
                    
                    if (p.getFechaCaducidad() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        producto.put("Fecha_Caducidad", sdf.format(p.getFechaCaducidad()));
                    } else {
                        producto.put("Fecha_Caducidad", "");
                    }
                    
                    proximosExpirar.put(producto);
                }
            } catch (Exception e) {
                System.err.println("Error en próximos a expirar: " + e.getMessage());
                e.printStackTrace();
            }
            resumen.put("proximosExpirar", proximosExpirar);

            // ✅ RESUMEN CATEGORÍAS - Con manejo de errores
            JSONObject resumenCategorias = new JSONObject();
            JSONArray categorias = new JSONArray();
            int total = 0;
            
            try {
                List<Object[]> resCat = productosFacade.resumenPorCategorias();
                for (Object[] fila : resCat) {
                    JSONObject categoria = new JSONObject();
                    String nombreCategoria = (String) fila[0];
                    Long cantidadLong = (Long) fila[1];
                    int cant = cantidadLong != null ? cantidadLong.intValue() : 0;
                    
                    categoria.put("categoria", nombreCategoria);
                    categoria.put("cantidad", cant);
                    categoria.put("color", getColorForCategory(nombreCategoria));
                    categorias.put(categoria);
                    total += cant;
                }
            } catch (Exception e) {
                System.err.println("Error en resumen categorías: " + e.getMessage());
            }
            
            resumenCategorias.put("categorias", categorias);
            resumenCategorias.put("total", total);
            resumen.put("resumenCategorias", resumenCategorias);

            out.print(resumen.toString());

        } catch (Exception e) {
            System.err.println("ERROR GENERAL: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject error = new JSONObject();
            error.put("error", "Error al generar resumen. Revisa logs del servidor.");
            out.print(error.toString());
        }
    }

    private String getColorForCategory(String categoria) {
        switch(categoria) {
            case "Medicamento": return "#067f7f";
            case "Alimento": return "#4CAF50";
            case "Accesorio": return "#2196F3";
            case "Insumo": return "#9C27B0";
            default: return "#cccccc";
        }
    }
}