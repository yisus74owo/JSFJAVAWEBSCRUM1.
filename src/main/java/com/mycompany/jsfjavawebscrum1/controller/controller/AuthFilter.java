package com.mycompany.jsfjavawebscrum1.controller.controller;

import com.mycompany.jsfjavawebscrum1.entities.Usuarios;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthFilter inicializado.");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String method = request.getMethod();

        System.out.println("DEBUG - AuthFilter: Interceptando URI: '" + requestURI + "', Método: '" + method + "'");

        // ✅ CORRECCIÓN: Verificar si la solicitud es pública
        if (isPublicRequest(requestURI, contextPath)) {
            System.out.println("DEBUG - AuthFilter: URI '" + requestURI + "' es pública. Saltando autenticación.");
            chain.doFilter(request, response);
            return;
        }

        // Validar sesión de usuario
        if (session == null || session.getAttribute("usuario") == null) {
            System.out.println("DEBUG - AuthFilter: Sesión no encontrada o usuario no logueado para URI: '" + requestURI + "'. Redirigiendo a login.");
            response.sendRedirect(response.encodeRedirectURL(contextPath + "/login.xhtml?redirect=" + requestURI));
            return;
        }

        // Obtener usuario y rol
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        String rol = usuario.getRolidRol().getNombrerol();

        System.out.println("DEBUG - AuthFilter: Usuario autenticado. Rol: '" + rol + "' para URI: '" + requestURI + "'.");

        // Verificar permisos
        if (!hasPermission(requestURI, rol, method, contextPath)) {
            System.out.println("DEBUG - AuthFilter: Permiso denegado para Rol: '" + rol + "' en URI: '" + requestURI + "'.");
            handleUnauthorizedAccess(response, rol, contextPath);
            return;
        }

        // Agregar headers de seguridad
        if (!isResourceRequest(requestURI)) {
            setSecurityHeaders(response);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AuthFilter destruido.");
    }

    // --- Métodos Auxiliares ---
    private boolean isPublicRequest(String requestURI, String contextPath) {
        // ✅ CORRECCIÓN: Agregar todas las páginas públicas necesarias
        boolean loginPageRequest = requestURI.equals(contextPath + "/login.xhtml");
        boolean registerPageRequest = requestURI.equals(contextPath + "/registro.xhtml") || 
                                     requestURI.equals(contextPath + "/registro.html");
        boolean homePageRequest = requestURI.equals(contextPath + "/") ||
                requestURI.equals(contextPath + "/OHANA.xhtml") ||
                requestURI.equals(contextPath + "/OHANA.html") ||
                requestURI.equals(contextPath + "/index.xhtml") ||
                requestURI.equals(contextPath + "/index.html");
        boolean recoverPageRequest = requestURI.equals(contextPath + "/recover.xhtml") ||
                                    requestURI.equals(contextPath + "/restaurar.xhtml");

        boolean loginServletRequest = requestURI.equals(contextPath + "/LoginServlet");
        boolean registerServletRequest = requestURI.equals(contextPath + "/registro");
        boolean logoutServletRequest = requestURI.equals(contextPath + "/CerrarSesionServlet");

        boolean resourceRequest = isResourceRequest(requestURI);

        // ✅ CORRECCIÓN: También permitir archivos .html y páginas de autenticación
        boolean isHtmlPage = requestURI.endsWith(".html") && 
                           (requestURI.contains("login") || 
                            requestURI.contains("registro") || 
                            requestURI.contains("OHANA") || 
                            requestURI.contains("index"));

        return loginPageRequest || registerPageRequest || homePageRequest || recoverPageRequest ||
                loginServletRequest || registerServletRequest || logoutServletRequest ||
                resourceRequest || isHtmlPage;
    }

    private boolean isResourceRequest(String requestURI) {
        return requestURI.endsWith(".css") ||
                requestURI.endsWith(".js") ||
                requestURI.endsWith(".png") ||
                requestURI.endsWith(".jpg") ||
                requestURI.endsWith(".jpeg") ||
                requestURI.endsWith(".gif") ||
                requestURI.endsWith(".ico") ||
                requestURI.endsWith(".woff") ||
                requestURI.endsWith(".ttf") ||
                requestURI.endsWith(".woff2") ||
                requestURI.contains("/resources/") ||
                requestURI.contains("/javax.faces.resource/") ||
                requestURI.contains("/images/") ||
                requestURI.contains("/assets/");
    }

    private boolean hasPermission(String requestURI, String rol, String method, String contextPath) {
        final String ROL_ADMINISTRADOR = "Administrador";
        final String ROL_AUXILIAR = "Auxiliar";

        System.out.println("DEBUG - AuthFilter.hasPermission: Evaluando URI: '" + requestURI + "', Rol: '" + rol + "', Método: '" + method + "'");

        // 🔹 Ahora soporta crear, editar y producto con contains
        if (requestURI.contains("crearproducto.xhtml") ||
            requestURI.contains("editarproducto.xhtml") ||
            requestURI.contains("producto.xhtml")) {
            return ROL_ADMINISTRADOR.equalsIgnoreCase(rol);
        }

        if (requestURI.startsWith(contextPath + "/api/productos")) {
            if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
                return ROL_ADMINISTRADOR.equalsIgnoreCase(rol);
            }
            return true; // GET permitido
        }

        if (requestURI.startsWith(contextPath + "/api/clientes") ||
                requestURI.startsWith(contextPath + "/api/usuarios")) {
            return ROL_ADMINISTRADOR.equalsIgnoreCase(rol);
        }

        if (requestURI.startsWith(contextPath + "/api/consultas") ||
                requestURI.startsWith(contextPath + "/api/historias")) {
            return ROL_ADMINISTRADOR.equalsIgnoreCase(rol) || "Veterinario".equalsIgnoreCase(rol);
        }

        return true; // permiso por defecto
    }

    private void handleUnauthorizedAccess(HttpServletResponse response, String rol, String contextPath) throws IOException {
        if ("Auxiliar".equalsIgnoreCase(rol)) {
            response.sendRedirect(response.encodeRedirectURL(contextPath + "/acceso-denegado.xhtml?rol=Auxiliar"));
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"No tienes permisos suficientes para acceder a este recurso.\"}");
        }
    }

    private void setSecurityHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-XSS-Protection", "1; mode=block");
    }
}