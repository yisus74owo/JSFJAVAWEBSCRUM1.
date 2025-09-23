package com.mycompany.jsfjavawebscrum1.controller.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycompany.jsfjavawebscrum1.entities.Productos;
import com.mycompany.jsfjavawebscrum1.entities.Usuarios;
import com.mycompany.jsfjavawebscrum1.controller.model.ProductosFacadeLocal;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.Date;
import java.util.stream.Collectors;

@WebServlet(name = "CrearProductoServlet", urlPatterns = {"/api/productos/crear"})
public class CrearProductoServlet extends HttpServlet {

    @EJB
    private ProductosFacadeLocal productosFacade;

    // Configurar CORS
    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORSHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCORSHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"No autenticado. Por favor, inicie sesión.\"}");
            return;
        }

        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null || !"Administrador".equalsIgnoreCase(usuario.getRolidRol().getNombrerol())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"Acceso denegado. Solo administradores pueden crear productos.\"}");
            return;
        }

        try (PrintWriter out = response.getWriter()) {
            String requestBody = request.getReader().lines().collect(Collectors.joining());

            if (requestBody == null || requestBody.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Cuerpo de solicitud vacío\"}");
                return;
            }

            // Usar la clase interna Producto como DTO
            Producto productoJson = new Gson().fromJson(requestBody, Producto.class);

            if (validarProducto(productoJson)) {
                Productos producto = new Productos();
                producto.setNombreProducto(productoJson.getNombreProducto());
                producto.setCategoria(productoJson.getCategoria());
                producto.setMarcaProducto(productoJson.getMarcaProducto());
                producto.setCantidadProducto(productoJson.getCantidadProducto());
                producto.setPrecioProducto(productoJson.getPrecioProducto());
                producto.setNombreProveedor(productoJson.getNombreProveedor());
                producto.setFechaCaducidad(productoJson.getFechaCaducidad());
                producto.setDescripcionProducto(productoJson.getDescripcionProducto());
                producto.setStockProducto(productoJson.getStockProducto());

                productosFacade.create(producto); // ✅ Persistencia con EJB

                out.write("{\"success\":true,\"message\":\"Producto creado correctamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Datos del producto inválidos o incompletos\"}");
            }

        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"JSON mal formado: " + escapeJson(e.getMessage()) + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error inesperado: " + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private boolean validarProducto(Producto producto) {
        return producto.getNombreProducto() != null && !producto.getNombreProducto().trim().isEmpty()
                && producto.getCategoria() != null && !producto.getCategoria().trim().isEmpty()
                && producto.getMarcaProducto() != null && !producto.getMarcaProducto().trim().isEmpty()
                && producto.getCantidadProducto() >= 0
                && producto.getPrecioProducto() >= 0
                && producto.getStockProducto() >= 0;
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "'").replace("\n", " ").replace("\r", " ");
    }

    // DTO para recibir JSON (coincide con los nombres esperados del front)
    public static class Producto {
        private String nombreProducto;
        private String categoria;
        private String marcaProducto;
        private int cantidadProducto;
        private double precioProducto;
        private String nombreProveedor;
        private Date fechaCaducidad;
        private String descripcionProducto;
        private int stockProducto;

        public String getNombreProducto() { return nombreProducto; }
        public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }
        public String getMarcaProducto() { return marcaProducto; }
        public void setMarcaProducto(String marcaProducto) { this.marcaProducto = marcaProducto; }
        public int getCantidadProducto() { return cantidadProducto; }
        public void setCantidadProducto(int cantidadProducto) { this.cantidadProducto = cantidadProducto; }
        public double getPrecioProducto() { return precioProducto; }
        public void setPrecioProducto(double precioProducto) { this.precioProducto = precioProducto; }
        public String getNombreProveedor() { return nombreProveedor; }
        public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }
        public Date getFechaCaducidad() { return fechaCaducidad; }
        public void setFechaCaducidad(Date fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
        public String getDescripcionProducto() { return descripcionProducto; }
        public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }
        public int getStockProducto() { return stockProducto; }
        public void setStockProducto(int stockProducto) { this.stockProducto = stockProducto; }
    }
}
