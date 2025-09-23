package com.mycompany.jsfjavawebscrum1.controller.controller;

import com.mycompany.jsfjavawebscrum1.entities.Usuarios;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/api/user-info")
public class UserInfoServlet extends HttpServlet {

    // Encabezados CORS
    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORSHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        setCORSHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("usuario") == null) {
                System.out.println("DEBUG - UserInfoServlet: Sesión nula o atributo 'usuario' no encontrado.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"No autenticado\"}");
                return;
            }

            // Ahora usamos tu entidad JPA "Usuarios"
            Usuarios usuario = (Usuarios) session.getAttribute("usuario");

            System.out.println("DEBUG - UserInfoServlet: Usuario encontrado en sesión: " + usuario.getCorreo());

            // Construimos el JSON manualmente
            String json = String.format(
                    "{\"nombre\":\"%s\", \"apellido\":\"%s\", \"email\":\"%s\", \"rol\":\"%s\"}",
                    usuario.getNombreusuario(),
                    usuario.getApellidousuario(),
                    usuario.getCorreo(),
                    usuario.getRolidRol().getNombrerol()  // O ajusta al nombre del campo de Roles
            );

            response.getWriter().write(json);

        } catch (ClassCastException e) {
            System.err.println("ERROR - UserInfoServlet: Error de cast al recuperar usuario de sesión. " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno de sesión\"}");
        } catch (Exception e) {
            System.err.println("ERROR - UserInfoServlet: Error inesperado: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error del servidor\"}");
        }
    }
}
