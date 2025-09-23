package com.mycompany.jsfjavawebscrum1.controller.controller;

import com.mycompany.jsfjavawebscrum1.controller.model.ProductosFacadeLocal;
import com.mycompany.jsfjavawebscrum1.entities.Productos;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/productos")
public class TablaProductosServlet extends HttpServlet {

    @EJB
    private ProductosFacadeLocal productosFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String filtroNombre = request.getParameter("nombre");
        String filtroCategoria = request.getParameter("categoria");

        try {
            List<Productos> productos = productosFacade.findAll();

            // Aplicar filtros si existen
            if (filtroNombre != null && !filtroNombre.trim().isEmpty()) {
                productos = productos.stream()
                        .filter(p -> p.getNombreProducto().toLowerCase().contains(filtroNombre.toLowerCase()))
                        .collect(Collectors.toList());
            }

            if (filtroCategoria != null && !filtroCategoria.trim().isEmpty()) {
                productos = productos.stream()
                        .filter(p -> p.getCategoria().equalsIgnoreCase(filtroCategoria))
                        .collect(Collectors.toList());
            }

            imprimirResultados(productos, out);

        } catch (Exception e) {
            out.println("<tr><td colspan='11'>Error: " + e.getMessage() + "</td></tr>");
        }
    }

    private void imprimirResultados(List<Productos> productos, PrintWriter out) {
        for (Productos p : productos) {
            int id = p.getIdProducto();
            out.println("<tr>");
            out.println("<td>" + p.getNombreProducto() + "</td>");
            out.println("<td>" + p.getCategoria() + "</td>");
            out.println("<td>" + p.getMarcaProducto() + "</td>");
            out.println("<td>" + p.getCantidadProducto() + "</td>");
            out.println("<td>$" + p.getPrecioProducto() + "</td>");
            out.println("<td>" + p.getNombreProveedor() + "</td>");
            out.println("<td>" + p.getFechaCaducidad() + "</td>");
            out.println("<td>" + p.getDescripcionProducto() + "</td>");
            out.println("<td>" + p.getStockProducto() + "</td>");
            out.println("<td>" +
                    "<button class='delete-btn' onclick='eliminarProducto(" + id + ", this)'>🗑️</button> " +
                    "<button class='edit-btn' onclick='editarProducto(" + id + ")'>✏️</button>" +
                    "</td>");
            out.println("</tr>");
        }
    }
}
