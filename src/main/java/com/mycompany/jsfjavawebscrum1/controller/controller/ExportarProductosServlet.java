package com.mycompany.jsfjavawebscrum1.controller.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Apache POI
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mycompany.jsfjavawebscrum1.entities.Usuarios;
import com.mycompany.jsfjavawebscrum1.entities.Productos;
import com.mycompany.jsfjavawebscrum1.controller.model.ProductosFacadeLocal;
import javax.ejb.EJB;

@WebServlet("/api/productos/exportar")
public class ExportarProductosServlet extends HttpServlet {

    @EJB
    private ProductosFacadeLocal productosFacade;

    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setCORSHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCORSHeaders(response);

        HttpSession session = request.getSession(false);

        // --- Verificación de sesión y rol ---
        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"No autenticado. Por favor, inicie sesión.\"}");
            return;
        }

        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null || !usuario.getRolidRol().getNombrerol().equalsIgnoreCase("Administrador")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Acceso denegado. Solo administradores pueden exportar productos.\"}");
            return;
        }

        // --- Configuración de descarga ---
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "inventario_productos_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (Workbook workbook = new XSSFWorkbook(); OutputStream outputStream = response.getOutputStream()) {

            Sheet sheet = workbook.createSheet("Inventario Productos");

            // --- Estilos ---
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setBorderBottom(BorderStyle.THIN);
            dataCellStyle.setBorderTop(BorderStyle.THIN);
            dataCellStyle.setBorderLeft(BorderStyle.THIN);
            dataCellStyle.setBorderRight(BorderStyle.THIN);
            dataCellStyle.setWrapText(true);

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.cloneStyleFrom(dataCellStyle);
            CreationHelper createHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

            // --- Encabezados ---
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Nombre", "Categoría", "Marca", "Cantidad", "Precio", "Proveedor", "Caducidad", "Descripción", "Stock"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // --- Llenado de datos usando Facade ---
            List<Productos> productosList = productosFacade.findAll();
            int rowNum = 1;
            for (Productos producto : productosList) {
                Row row = sheet.createRow(rowNum++);

                Cell idCell = row.createCell(0);
                idCell.setCellValue(producto.getIdProducto());
                idCell.setCellStyle(dataCellStyle);

                Cell nombreCell = row.createCell(1);
                nombreCell.setCellValue(producto.getNombreProducto());
                nombreCell.setCellStyle(dataCellStyle);

                Cell categoriaCell = row.createCell(2);
                categoriaCell.setCellValue(producto.getCategoria());
                categoriaCell.setCellStyle(dataCellStyle);

                Cell marcaCell = row.createCell(3);
                marcaCell.setCellValue(producto.getMarcaProducto());
                marcaCell.setCellStyle(dataCellStyle);

                Cell cantidadCell = row.createCell(4);
                cantidadCell.setCellValue(producto.getCantidadProducto());
                cantidadCell.setCellStyle(dataCellStyle);

                Cell precioCell = row.createCell(5);
                precioCell.setCellValue(producto.getPrecioProducto());
                precioCell.setCellStyle(dataCellStyle);

                Cell proveedorCell = row.createCell(6);
                proveedorCell.setCellValue(producto.getNombreProveedor());
                proveedorCell.setCellStyle(dataCellStyle);

                Cell caducidadCell = row.createCell(7);
                if (producto.getFechaCaducidad() != null) {
                    caducidadCell.setCellValue(producto.getFechaCaducidad());
                    caducidadCell.setCellStyle(dateCellStyle);
                } else {
                    caducidadCell.setCellValue("");
                    caducidadCell.setCellStyle(dataCellStyle);
                }

                Cell descripcionCell = row.createCell(8);
                descripcionCell.setCellValue(producto.getDescripcionProducto());
                descripcionCell.setCellStyle(dataCellStyle);

                Cell stockCell = row.createCell(9);
                stockCell.setCellValue(producto.getStockProducto());
                stockCell.setCellStyle(dataCellStyle);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Error de I/O: " + escapeJson(e.getMessage()) + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Error interno: " + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "'").replace("\n", " ").replace("\r", " ");
    }
}
