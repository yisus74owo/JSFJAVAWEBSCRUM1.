package com.mycompany.jsfjavawebscrum1.controller.controller;

import com.mycompany.jsfjavawebscrum1.controller.model.UsuariosFacadeLocal;
import com.mycompany.jsfjavawebscrum1.entities.Usuarios;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @EJB
    private UsuariosFacadeLocal usuariosFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Evitar caché
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Captura de parámetros
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validación de entrada
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.xhtml?error=1");
            return;
        }

        try {
            // Buscar usuario por correo
            Usuarios usuarioEntity = usuariosFacade.findByCorreo(email.trim());

            if (usuarioEntity == null) {
                // Usuario no encontrado
                response.sendRedirect(request.getContextPath() + "/login.xhtml?error=1");
                return;
            }

            // Obtener contraseña encriptada desde la entidad
            String storedHash = usuarioEntity.getContraseña();

            if (storedHash == null || storedHash.isEmpty()) {
                LOGGER.log(Level.WARNING, "Usuario {0} sin contraseña almacenada.", email);
                response.sendRedirect(request.getContextPath() + "/login.xhtml?error=2");
                return;
            }

            // Validar contraseña
            boolean passwordMatches = false;
            try {
                passwordMatches = BCrypt.checkpw(password, storedHash);
            } catch (IllegalArgumentException ex) {
                LOGGER.log(Level.WARNING, "Hash inválido en BD para usuario {0}: {1}", new Object[]{email, ex.getMessage()});
            }

            if (passwordMatches) {
                // Login exitoso: guardar en sesión
                HttpSession session = request.getSession(true);
                session.setAttribute("usuario", usuarioEntity);
                session.setMaxInactiveInterval(30 * 60); // 30 min

                // Redirección (ajusta a .html si corresponde en tu app)
                response.sendRedirect(request.getContextPath() + "/inventorymodule.xhtml");
            } else {
                // Contraseña incorrecta
                response.sendRedirect(request.getContextPath() + "/login.xhtml?error=1");
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en LoginServlet", e);
            response.sendRedirect(request.getContextPath() + "/login.xhtml?error=2");
        }
    }
}
