package com.mycompany.jsfjavawebscrum1.controller.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycompany.jsfjavawebscrum1.controller.model.ProductosFacadeLocal;
import com.mycompany.jsfjavawebscrum1.entities.Productos;
import com.mycompany.jsfjavawebscrum1.entities.Usuarios;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "EditarProductoServlet", urlPatterns = {"/api/productos/editar/*"})
public class EditarProductoServlet extends HttpServlet {

    @EJB
    private ProductosFacadeLocal productosFacade;

    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORSHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCORSHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"No autenticado\"}");
            return;
        }

        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        // ✅ CORRECCIÓN: Permitir tanto Administrador como Auxiliar
        if (!"Administrador".equalsIgnoreCase(usuario.getRolidRol().getNombrerol()) &&
            !"Auxiliar".equalsIgnoreCase(usuario.getRolidRol().getNombrerol())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"Acceso denegado\"}");
            return;
        }

        try (PrintWriter out = response.getWriter()) {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Se requiere ID de producto\"}");
                return;
            }

            String[] parts = pathInfo.split("/");
            if (parts.length != 2) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Formato de URL inválido\"}");
                return;
            }

            try {
                int idProducto = Integer.parseInt(parts[1]);
                Productos producto = productosFacade.find(idProducto);

                if (producto != null) {
                    out.write(new Gson().toJson(producto));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"error\":\"Producto no encontrado\"}");
                }

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"ID inválido\"}");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCORSHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"No autenticado\"}");
            return;
        }

        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (!"Administrador".equalsIgnoreCase(usuario.getRolidRol().getNombrerol())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"Solo administradores pueden editar productos\"}");
            return;
        }

        try (PrintWriter out = response.getWriter()) {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Se requiere ID de producto\"}");
                return;
            }

            String[] parts = pathInfo.split("/");
            if (parts.length != 2) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Formato de URL inválido\"}");
                return;
            }

            try {
                int idProducto = Integer.parseInt(parts[1]);
                String requestBody = request.getReader().lines().collect(Collectors.joining());

                if (requestBody == null || requestBody.trim().isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"error\":\"Cuerpo de solicitud vacío\"}");
                    return;
                }

                Productos productoActualizar = new Gson().fromJson(requestBody, Productos.class);
                Productos productoExistente = productosFacade.find(idProducto);

                if (productoExistente == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"error\":\"Producto no encontrado\"}");
                    return;
                }

                productoExistente.setNombreProducto(productoActualizar.getNombreProducto());
                productoExistente.setCategoria(productoActualizar.getCategoria());
                productoExistente.setMarcaProducto(productoActualizar.getMarcaProducto());
                productoExistente.setCantidadProducto(productoActualizar.getCantidadProducto());
                productoExistente.setPrecioProducto(productoActualizar.getPrecioProducto());
                productoExistente.setNombreProveedor(productoActualizar.getNombreProveedor());
                productoExistente.setFechaCaducidad(productoActualizar.getFechaCaducidad());
                productoExistente.setDescripcionProducto(productoActualizar.getDescripcionProducto());
                productoExistente.setStockProducto(productoActualizar.getStockProducto());

                productosFacade.edit(productoExistente);

                out.write("{\"success\":true,\"message\":\"Producto actualizado correctamente\"}");

            } catch (JsonSyntaxException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"JSON mal formado: " + escapeJson(e.getMessage()) + "\"}");
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"ID inválido\"}");
            }
        }
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "'").replace("\n", " ").replace("\r", " ");
    }
}