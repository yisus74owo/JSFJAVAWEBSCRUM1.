package com.mycompany.jsfjavawebscrum1.controller.controller;

import com.mycompany.jsfjavawebscrum1.controller.model.RolesFacadeLocal;
import com.mycompany.jsfjavawebscrum1.controller.model.UsuariosFacadeLocal;
import com.mycompany.jsfjavawebscrum1.entities.Usuarios;
import com.mycompany.jsfjavawebscrum1.entities.Roles;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    @EJB
    private UsuariosFacadeLocal usuariosFacade;

    @EJB
    private RolesFacadeLocal rolesFacade;

    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        setCORSHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCORSHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {

            String nombre = request.getParameter("Nombre_usuario");
            String apellido = request.getParameter("Apellido_usuario");
            String correo = request.getParameter("Correo");
            String contrasena = request.getParameter("Contrasena");
            String telefono = request.getParameter("Telefono");
            String direccion = request.getParameter("Direccion");
            String tipoDocumento = request.getParameter("Tipo_documento");
            String documento = request.getParameter("Documento_usuario");
            String nombreRol = request.getParameter("rol");

            // --- Validaciones básicas ---
            if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty() ||
                contrasena == null || contrasena.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                direccion == null || direccion.trim().isEmpty() ||
                tipoDocumento == null || tipoDocumento.trim().isEmpty() ||
                documento == null || documento.trim().isEmpty() ||
                nombreRol == null || nombreRol.trim().isEmpty()) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Todos los campos son obligatorios.\"}");
                return;
            }

            if (contrasena.length() < 8 || contrasena.length() > 16) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"La contraseña debe tener entre 8 y 16 caracteres.\"}");
                return;
            }

            if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Formato de correo electrónico inválido.\"}");
                return;
            }

            // --- Validar si el correo ya existe ---
            if (usuariosFacade.findByCorreo(correo) != null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.write("{\"error\":\"El correo ya está registrado.\"}");
                return;
            }

            // --- Obtener rol ---
            Roles rol = rolesFacade.findByNombre(nombreRol);
            if (rol == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Rol inválido o no existe.\"}");
                return;
            }

            // --- Crear usuario ---
            Usuarios usuario = new Usuarios();
            usuario.setNombreusuario(nombre);
            usuario.setApellidousuario(apellido);
            usuario.setCorreo(correo);
            usuario.setContraseña(BCrypt.hashpw(contrasena, BCrypt.gensalt()));
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);
            usuario.setTipodocumento(tipoDocumento);
            usuario.setDocumentousuario(documento);
            usuario.setEstadousuario((short) 1);
            usuario.setRolidRol(rol);

            // --- Guardar usuario ---
            try {
                usuariosFacade.create(usuario);
                out.write("{\"success\":true,\"message\":\"Usuario registrado exitosamente.\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"error\":\"" + escapeJson("Error al registrar usuario: " + e.getMessage()) + "\"}");
            }
        }
    }

    private String escapeJson(String input) {
        return input == null ? "" : input.replace("\"", "'").replace("\n", " ").replace("\r", " ");
    }
}
