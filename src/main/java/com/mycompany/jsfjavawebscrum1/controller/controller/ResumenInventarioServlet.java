package com.mycompany.jsfjavawebscrum1.controller.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import org.json.JSONArray;
import org.json.JSONObject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.mycompany.jsfjavawebscrum1.controller.model.ProductosFacadeLocal;
import com.mycompany.jsfjavawebscrum1.entities.Productos;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.poi.util.Units;

@WebServlet("/api/resumen")
public class ResumenInventarioServlet extends HttpServlet {

    @EJB
    private ProductosFacadeLocal productosFacade;

    private static final int DIAS_EXPIRACION = 30; // solo 30 días

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String exportType = request.getParameter("export");

        if (exportType != null) {
            generarExportacion(exportType, response);
        } else {
            generarJSON(response);
        }
    }

    private void generarJSON(HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        JSONObject resumen = generarDatosResumen();
        response.getWriter().print(resumen.toString());
    }

    private void generarExportacion(String exportType, HttpServletResponse response) throws IOException {
        switch (exportType.toLowerCase()) {
            case "excel":
                exportarExcel(response);
                break;
            case "word":
                exportarWord(response);
                break;
            case "pdf":
                exportarPDF(response);
                break;
            default:
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Tipo de exportación no válido\"}");
        }
    }

    // ================== EXPORTAR EXCEL ==================
    private void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=resumen_inventario.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            // Hoja stock bajo
            Sheet stockSheet = workbook.createSheet("Stock Bajo");
            crearHeaderStockBajo(stockSheet, workbook);
            llenarDatosStockBajo(stockSheet);

            // Hoja caducidad
            Sheet expiracionSheet = workbook.createSheet("Próximos a Expirar");
            crearHeaderExpiracion(expiracionSheet, workbook);
            llenarDatosExpiracion(expiracionSheet);

            // Hoja categorías
            Sheet categoriasSheet = workbook.createSheet("Resumen Categorías");
            crearHeaderCategorias(categoriasSheet, workbook);
            llenarDatosCategorias(categoriasSheet);

            for (int i = 0; i < 5; i++) {
                stockSheet.autoSizeColumn(i);
                expiracionSheet.autoSizeColumn(i);
                categoriasSheet.autoSizeColumn(i);
            }

            // Insertar gráfico en hoja Categorías
            byte[] graficoBytes = generarGraficoCategorias();
            int pictureIdx = workbook.addPicture(graficoBytes, Workbook.PICTURE_TYPE_PNG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = categoriasSheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(5);
            anchor.setRow1(2);
            drawing.createPicture(anchor, pictureIdx).resize();

            try (OutputStream out = response.getOutputStream()) {
                workbook.write(out);
            }
        }
    }

    // ================== EXPORTAR WORD ==================
    private void exportarWord(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=resumen_inventario.docx");

        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("RESUMEN DE INVENTARIO - OHANA");
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            XWPFParagraph dateParagraph = document.createParagraph();
            dateParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun dateRun = dateParagraph.createRun();
            dateRun.setText("Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
            dateRun.setFontSize(10);
            dateRun.setItalic(true);

            agregarSeccionWord(document, "STOCK BAJO");
            agregarTablaStockBajo(document);

            agregarSeccionWord(document, "PRÓXIMOS A EXPIRAR (<=30 días)");
            agregarTablaExpiracion(document);

            agregarSeccionWord(document, "RESUMEN POR CATEGORÍAS");
            agregarTablaCategorias(document);

            // Insertar gráfico
            byte[] graficoBytes = generarGraficoCategorias();
            try (ByteArrayInputStream is = new ByteArrayInputStream(graficoBytes)) {
                XWPFParagraph p = document.createParagraph();
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = p.createRun();
                run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, "grafico.png", Units.toEMU(400), Units.toEMU(300));
            }

            try (OutputStream out = response.getOutputStream()) {
                document.write(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== EXPORTAR PDF ==================
    private void exportarPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=resumen_inventario.pdf");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
                // Título
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
                cs.beginText();
                cs.newLineAtOffset(50, 750);
                cs.showText("RESUMEN DE INVENTARIO - OHANA");
                cs.endText();

                // Fecha
                cs.setFont(PDType1Font.HELVETICA, 10);
                cs.beginText();
                cs.newLineAtOffset(50, 730);
                cs.showText("Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
                cs.endText();

                int yPos = 700;

                // Stock bajo
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.beginText();
                cs.newLineAtOffset(50, yPos);
                cs.showText("STOCK BAJO:");
                cs.endText();
                yPos -= 20;

                List<Productos> stockBajo = productosFacade.findStockBajo(0.4, 10);
                cs.setFont(PDType1Font.HELVETICA, 10);
                for (Productos p : stockBajo) {
                    double porcentaje = p.getStockProducto() > 0 ? (p.getCantidadProducto() * 100.0) / p.getStockProducto() : 0;
                    cs.beginText();
                    cs.newLineAtOffset(60, yPos);
                    cs.showText(p.getNombreProducto() + " - " + String.format("%.1f%%", porcentaje));
                    cs.endText();
                    yPos -= 15;
                }

                // Expiración
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.beginText();
                cs.newLineAtOffset(50, yPos - 20);
                cs.showText("PRÓXIMOS A EXPIRAR (<=30 días):");
                cs.endText();
                yPos -= 40;

                List<Productos> expiran = productosFacade.findProximosExpirar(DIAS_EXPIRACION, 10);
                cs.setFont(PDType1Font.HELVETICA, 10);
                for (Productos p : expiran) {
                    if (p.getFechaCaducidad() != null) {
                        long diff = p.getFechaCaducidad().getTime() - System.currentTimeMillis();
                        long diasRestantes = diff / (24 * 60 * 60 * 1000);
                        if (diasRestantes <= DIAS_EXPIRACION && diasRestantes >= 0) {
                            String fechaCad = new SimpleDateFormat("dd/MM/yyyy").format(p.getFechaCaducidad());
                            cs.beginText();
                            cs.newLineAtOffset(60, yPos);
                            cs.showText(p.getNombreProducto() + " - Vence: " + fechaCad);
                            cs.endText();
                            yPos -= 15;
                        }
                    }
                }

                // Categorías
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.beginText();
                cs.newLineAtOffset(50, yPos - 20);
                cs.showText("RESUMEN POR CATEGORÍAS:");
                cs.endText();
                yPos -= 40;

                List<Object[]> categorias = productosFacade.resumenPorCategorias();
                int total = categorias.stream().mapToInt(f -> ((Long) f[1]).intValue()).sum();

                cs.setFont(PDType1Font.HELVETICA, 10);
                for (Object[] fila : categorias) {
                    String categoria = (String) fila[0];
                    int cantidad = ((Long) fila[1]).intValue();
                    double porcentaje = total > 0 ? (cantidad * 100.0) / total : 0;
                    cs.beginText();
                    cs.newLineAtOffset(60, yPos);
                    cs.showText(categoria + ": " + cantidad + " productos (" + String.format("%.1f%%", porcentaje) + ")");
                    cs.endText();
                    yPos -= 15;
                }

                // Gráfico al final
                byte[] graficoBytes = generarGraficoCategorias();
                BufferedImage chartImg = javax.imageio.ImageIO.read(new ByteArrayInputStream(graficoBytes));
                PDImageXObject pdImage = LosslessFactory.createFromImage(document, chartImg);
                cs.drawImage(pdImage, 100, 100, 400, 300);
            }

            document.save(response.getOutputStream());
        }
    }

    // ================== GRAFICO ==================
    private byte[] generarGraficoCategorias() throws IOException {
        List<Object[]> resCat = productosFacade.resumenPorCategorias();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Object[] fila : resCat) {
            dataset.setValue((String) fila[0], ((Long) fila[1]).intValue());
        }

        JFreeChart chart = ChartFactory.createPieChart("Distribución por Categorías", dataset, true, false, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        BufferedImage image = chart.createBufferedImage(500, 400);
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    // ================== Helpers (Excel y Word) ==================
    private void crearHeaderStockBajo(Sheet sheet, Workbook wb) {
        Row r = sheet.createRow(0);
        String[] h = {"ID", "Producto", "Cantidad", "Stock Mínimo", "Porcentaje", "Estado"};
        CellStyle s = crearEstiloHeader(wb);
        for (int i = 0; i < h.length; i++) {
            Cell c = r.createCell(i);
            c.setCellValue(h[i]);
            c.setCellStyle(s);
        }
    }

    private void llenarDatosStockBajo(Sheet sheet) {
        List<Productos> bajos = productosFacade.findStockBajo(0.4, 100);
        int rowNum = 1;
        for (Productos p : bajos) {
            Row r = sheet.createRow(rowNum++);
            double porcentaje = p.getStockProducto() > 0 ? (p.getCantidadProducto() * 100.0) / p.getStockProducto() : 0;
            String estado = porcentaje < 20 ? "CRÍTICO" : porcentaje < 40 ? "BAJO" : "NORMAL";
            r.createCell(0).setCellValue(p.getIdProducto());
            r.createCell(1).setCellValue(p.getNombreProducto());
            r.createCell(2).setCellValue(p.getCantidadProducto());
            r.createCell(3).setCellValue(p.getStockProducto());
            r.createCell(4).setCellValue(porcentaje);
            r.createCell(5).setCellValue(estado);
        }
    }

    private void crearHeaderExpiracion(Sheet sheet, Workbook wb) {
        Row r = sheet.createRow(0);
        String[] h = {"ID", "Producto", "Fecha Caducidad", "Días Restantes", "Estado"};
        CellStyle s = crearEstiloHeader(wb);
        for (int i = 0; i < h.length; i++) {
            Cell c = r.createCell(i);
            c.setCellValue(h[i]);
            c.setCellStyle(s);
        }
    }

    private void llenarDatosExpiracion(Sheet sheet) {
        List<Productos> expiran = productosFacade.findProximosExpirar(DIAS_EXPIRACION, 100);
        int rowNum = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Productos p : expiran) {
            if (p.getFechaCaducidad() != null) {
                long diff = p.getFechaCaducidad().getTime() - System.currentTimeMillis();
                long dias = diff / (24 * 60 * 60 * 1000);
                if (dias <= DIAS_EXPIRACION && dias >= 0) {
                    Row r = sheet.createRow(rowNum++);
                    r.createCell(0).setCellValue(p.getIdProducto());
                    r.createCell(1).setCellValue(p.getNombreProducto());
                    r.createCell(2).setCellValue(sdf.format(p.getFechaCaducidad()));
                    r.createCell(3).setCellValue(dias);
                    r.createCell(4).setCellValue("CRÍTICO");
                }
            }
        }
    }

    private void crearHeaderCategorias(Sheet sheet, Workbook wb) {
        Row r = sheet.createRow(0);
        String[] h = {"Categoría", "Cantidad", "Porcentaje"};
        CellStyle s = crearEstiloHeader(wb);
        for (int i = 0; i < h.length; i++) {
            Cell c = r.createCell(i);
            c.setCellValue(h[i]);
            c.setCellStyle(s);
        }
    }

    private void llenarDatosCategorias(Sheet sheet) {
        List<Object[]> resCat = productosFacade.resumenPorCategorias();
        int rowNum = 1;
        int total = resCat.stream().mapToInt(f -> ((Long) f[1]).intValue()).sum();
        for (Object[] fila : resCat) {
            Row r = sheet.createRow(rowNum++);
            String categoria = (String) fila[0];
            int cantidad = ((Long) fila[1]).intValue();
            double porcentaje = total > 0 ? (cantidad * 100.0) / total : 0;
            r.createCell(0).setCellValue(categoria);
            r.createCell(1).setCellValue(cantidad);
            r.createCell(2).setCellValue(String.format("%.2f%%", porcentaje));
        }
    }

    private void agregarSeccionWord(XWPFDocument doc, String titulo) {
        XWPFParagraph sec = doc.createParagraph();
        XWPFRun r = sec.createRun();
        r.setText(titulo);
        r.setBold(true);
        r.setFontSize(14);
        r.addBreak();
    }

    private void agregarTablaStockBajo(XWPFDocument doc) {
        List<Productos> bajos = productosFacade.findStockBajo(0.4, 10);
        XWPFTable t = doc.createTable();
        XWPFTableRow h = t.getRow(0);
        h.getCell(0).setText("Producto");
        h.addNewTableCell().setText("Cantidad");
        h.addNewTableCell().setText("Stock Mínimo");
        h.addNewTableCell().setText("Porcentaje");
        h.addNewTableCell().setText("Estado");
        for (Productos p : bajos) {
            XWPFTableRow r = t.createRow();
            double porcentaje = p.getStockProducto() > 0 ? (p.getCantidadProducto() * 100.0) / p.getStockProducto() : 0;
            String estado = porcentaje < 20 ? "CRÍTICO" : porcentaje < 40 ? "BAJO" : "NORMAL";
            r.getCell(0).setText(p.getNombreProducto());
            r.getCell(1).setText(String.valueOf(p.getCantidadProducto()));
            r.getCell(2).setText(String.valueOf(p.getStockProducto()));
            r.getCell(3).setText(String.format("%.1f%%", porcentaje));
            r.getCell(4).setText(estado);
        }
    }

    private void agregarTablaExpiracion(XWPFDocument doc) {
        List<Productos> expiran = productosFacade.findProximosExpirar(DIAS_EXPIRACION, 10);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        XWPFTable t = doc.createTable();
        XWPFTableRow h = t.getRow(0);
        h.getCell(0).setText("Producto");
        h.addNewTableCell().setText("Fecha Caducidad");
        h.addNewTableCell().setText("Días Restantes");
        h.addNewTableCell().setText("Estado");
        for (Productos p : expiran) {
            if (p.getFechaCaducidad() != null) {
                long diff = p.getFechaCaducidad().getTime() - System.currentTimeMillis();
                long dias = diff / (24 * 60 * 60 * 1000);
                if (dias <= DIAS_EXPIRACION && dias >= 0) {
                    XWPFTableRow r = t.createRow();
                    r.getCell(0).setText(p.getNombreProducto());
                    r.getCell(1).setText(sdf.format(p.getFechaCaducidad()));
                    r.getCell(2).setText(String.valueOf(dias));
                    r.getCell(3).setText("CRÍTICO");
                }
            }
        }
    }

    private void agregarTablaCategorias(XWPFDocument doc) {
        List<Object[]> resCat = productosFacade.resumenPorCategorias();
        XWPFTable t = doc.createTable();
        XWPFTableRow h = t.getRow(0);
        h.getCell(0).setText("Categoría");
        h.addNewTableCell().setText("Cantidad");
        h.addNewTableCell().setText("Porcentaje");
        int total = resCat.stream().mapToInt(f -> ((Long) f[1]).intValue()).sum();
        for (Object[] fila : resCat) {
            XWPFTableRow r = t.createRow();
            String categoria = (String) fila[0];
            int cantidad = ((Long) fila[1]).intValue();
            double porcentaje = total > 0 ? (cantidad * 100.0) / total : 0;
            r.getCell(0).setText(categoria);
            r.getCell(1).setText(String.valueOf(cantidad));
            r.getCell(2).setText(String.format("%.2f%%", porcentaje));
        }
    }

    private CellStyle crearEstiloHeader(Workbook wb) {
        CellStyle s = wb.createCellStyle();
        Font f = wb.createFont();
        f.setBold(true);
        f.setColor(IndexedColors.WHITE.getIndex());
        s.setFont(f);
        s.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        s.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        s.setAlignment(HorizontalAlignment.CENTER);
        return s;
    }

    // ================== JSON ==================
    private JSONObject generarDatosResumen() {
        JSONObject resumen = new JSONObject();

        JSONArray stockBajo = new JSONArray();
        List<Productos> bajos = productosFacade.findStockBajo(0.4, 10);
        for (Productos p : bajos) {
            JSONObject o = new JSONObject();
            o.put("idProducto", p.getIdProducto());
            o.put("Nombre_Producto", p.getNombreProducto());
            o.put("Cantidad_Producto", p.getCantidadProducto());
            o.put("Stock_Producto", p.getStockProducto());
            stockBajo.put(o);
        }
        resumen.put("stockBajo", stockBajo);

        JSONArray proximosExpirar = new JSONArray();
        List<Productos> expiran = productosFacade.findProximosExpirar(DIAS_EXPIRACION, 10);
        for (Productos p : expiran) {
            if (p.getFechaCaducidad() != null) {
                long diff = p.getFechaCaducidad().getTime() - System.currentTimeMillis();
                long dias = diff / (24 * 60 * 60 * 1000);
                if (dias <= DIAS_EXPIRACION && dias >= 0) {
                    JSONObject o = new JSONObject();
                    o.put("idProducto", p.getIdProducto());
                    o.put("Nombre_Producto", p.getNombreProducto());
                    o.put("Fecha_Caducidad", new SimpleDateFormat("yyyy-MM-dd").format(p.getFechaCaducidad()));
                    proximosExpirar.put(o);
                }
            }
        }
        resumen.put("proximosExpirar", proximosExpirar);

        JSONObject resumenCategorias = new JSONObject();
        JSONArray categorias = new JSONArray();
        int total = 0;
        List<Object[]> resCat = productosFacade.resumenPorCategorias();
        for (Object[] fila : resCat) {
            JSONObject c = new JSONObject();
            String nombreCategoria = (String) fila[0];
            int cantidad = ((Long) fila[1]).intValue();
            c.put("categoria", nombreCategoria);
            c.put("cantidad", cantidad);
            categorias.put(c);
            total += cantidad;
        }
        resumenCategorias.put("categorias", categorias);
        resumenCategorias.put("total", total);
        resumen.put("resumenCategorias", resumenCategorias);

        return resumen;
    }
}
