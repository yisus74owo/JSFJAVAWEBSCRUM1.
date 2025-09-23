package com.mycompany.jsfjavawebscrum1.controller.controller;

import com.mycompany.jsfjavawebscrum1.controller.model.ProductosFacadeLocal;
import com.mycompany.jsfjavawebscrum1.entities.Productos;
import com.mycompany.jsfjavawebscrum1.entities.Usuarios;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/productos/*") // 🔹 ahora concuerda con tu fetch DELETE
public class EliminarProductoServlet extends HttpServlet {

    @EJB
    private ProductosFacadeLocal productosFacade;

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\":false, \"message\":\"No autenticado\"}");
            return;
        }

        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null || !"Administrador".equalsIgnoreCase(usuario.getRolidRol().getNombrerol())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"success\":false, \"message\":\"Requiere rol de Administrador\"}");
            return;
        }

        String pathInfo = request.getPathInfo(); // ejemplo: /5
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false, \"message\":\"ID no proporcionado\"}");
            return;
        }

        try {
            int idProducto = Integer.parseInt(pathInfo.substring(1));
            Productos producto = productosFacade.find(idProducto);

            if (producto != null) {
                productosFacade.remove(producto);
                response.getWriter().write("{\"success\":true, \"message\":\"Producto eliminado correctamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"success\":false, \"message\":\"Producto no encontrado\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false, \"message\":\"ID inválido\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\":false, \"message\":\"Error interno: " + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "'").replace("\n", " ").replace("\r", " ");
    }
}
