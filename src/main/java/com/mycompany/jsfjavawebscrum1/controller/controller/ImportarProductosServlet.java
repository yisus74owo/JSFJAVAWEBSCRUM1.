package com.mycompany.jsfjavawebscrum1.controller.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.mycompany.jsfjavawebscrum1.controller.model.ProductosFacadeLocal;
import com.mycompany.jsfjavawebscrum1.entities.Productos;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;

@WebServlet("/api/importar-productos")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 10,     // 10 MB máximo
    maxRequestSize = 1024 * 1024 * 50,  // 50 MB máximo
    fileSizeThreshold = 1024 * 1024     // 1 MB threshold
)
public class ImportarProductosServlet extends HttpServlet {

    @EJB
    private ProductosFacadeLocal productosFacade;
    
    private static final Logger logger = Logger.getLogger(ImportarProductosServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            logger.info("Iniciando proceso de importación de productos...");
            
            // Verificar si se subió un archivo
            Part filePart = request.getPart("archivoExcel");
            if (filePart == null || filePart.getSize() == 0) {
                logger.warning("No se ha seleccionado ningún archivo.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"success\": false, \"error\": \"No se ha seleccionado ningún archivo.\"}");
                return;
            }

            // Verificar extensión del archivo
            String fileName = filePart.getSubmittedFileName();
            if (!fileName.toLowerCase().endsWith(".xlsx")) {
                logger.warning("Archivo con extensión no válida: " + fileName);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"success\": false, \"error\": \"Solo se permiten archivos Excel (.xlsx).\"}");
                return;
            }

            logger.info("Procesando archivo: " + fileName);
            
            // Procesar el archivo Excel
            InputStream fileContent = filePart.getInputStream();
            List<ProductoImportado> productosImportados = procesarArchivoExcel(fileContent);
            
            logger.info("Archivo procesado. " + productosImportados.size() + " registros encontrados.");
            
            // Importar productos a la base de datos
            ResultadoImportacion resultado = importarProductos(productosImportados);
            
            logger.info("Importación completada. Éxitos: " + resultado.getImportadosExitosos() + ", Errores: " + resultado.getErrores());
            
            // Devolver resultado
            out.write("{" +
                "\"success\": true," +
                "\"message\": \"Importación completada.\"," +
                "\"totalRegistros\": " + resultado.getTotalRegistros() + "," +
                "\"importadosExitosos\": " + resultado.getImportadosExitosos() + "," +
                "\"errores\": " + resultado.getErrores() + "," +
                "\"detalleErrores\": " + convertirListaAJson(resultado.getDetalleErrores()) +
            "}");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al procesar el archivo", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"success\": false, \"error\": \"Error al procesar el archivo: " + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }

    private List<ProductoImportado> procesarArchivoExcel(InputStream fileContent) throws IOException {
        List<ProductoImportado> productos = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(fileContent)) {
            Sheet sheet = workbook.getSheetAt(0); // Primera hoja
            
            // Validar que la hoja tenga datos
            if (sheet.getLastRowNum() < 1) {
                throw new IOException("El archivo Excel está vacío o no tiene datos");
            }
            
            // Leer encabezados para validar estructura
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("El archivo no tiene encabezados válidos");
            }
            
            logger.info("Estructura del archivo validada. Procesando " + sheet.getLastRowNum() + " filas...");

            // Procesar filas de datos (empezando desde la fila 1, saltando encabezados)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    logger.warning("Fila " + (i + 1) + " está vacía, omitiendo...");
                    continue;
                }

                // Saltar filas completamente vacías
                if (esFilaVacia(row)) {
                    continue;
                }

                ProductoImportado producto = new ProductoImportado();
                producto.setNumeroFila(i + 1);

                try {
                    // Leer cada celda según la plantilla
                    producto.setNombreProducto(obtenerValorCelda(row.getCell(0))); // Columna A: Nombre
                    producto.setCategoria(obtenerValorCelda(row.getCell(1)));     // Columna B: Categoría
                    producto.setMarca(obtenerValorCelda(row.getCell(2)));         // Columna C: Marca
                    producto.setCantidadProducto(obtenerValorNumerico(row.getCell(3))); // Columna D: Cantidad
                    producto.setPrecio(obtenerValorDecimal(row.getCell(4)));      // Columna E: Precio
                    producto.setProveedor(obtenerValorCelda(row.getCell(5)));     // Columna F: Proveedor
                    producto.setFechaCaducidad(obtenerValorFecha(row.getCell(6))); // Columna G: Fecha_Caducidad
                    producto.setDescripcion(obtenerValorCelda(row.getCell(7)));   // Columna H: Descripción
                    producto.setStockProducto(obtenerValorNumerico(row.getCell(8))); // Columna I: Stock_Minimo

                    productos.add(producto);
                    logger.fine("Fila " + (i + 1) + " procesada correctamente: " + producto.getNombreProducto());
                    
                } catch (Exception e) {
                    String errorMsg = "Error en fila " + (i + 1) + ": " + e.getMessage();
                    producto.setError(errorMsg);
                    productos.add(producto);
                    logger.warning(errorMsg);
                }
            }
        }
        
        return productos;
    }

    private boolean esFilaVacia(Row row) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = obtenerValorCelda(cell);
                if (value != null && !value.trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private ResultadoImportacion importarProductos(List<ProductoImportado> productosImportados) {
        ResultadoImportacion resultado = new ResultadoImportacion();
        resultado.setTotalRegistros(productosImportados.size());

        for (ProductoImportado productoImportado : productosImportados) {
            try {
                if (productoImportado.getError() != null) {
                    resultado.agregarError(productoImportado.getNumeroFila(), productoImportado.getError());
                    continue;
                }

                // Validar campos obligatorios
                if (!validarProducto(productoImportado)) {
                    resultado.agregarError(productoImportado.getNumeroFila(), "Campos obligatorios faltantes o inválidos");
                    continue;
                }

                // Verificar si el producto ya existe (por nombre)
                if (productosFacade.existeProductoPorNombre(productoImportado.getNombreProducto())) {
                    resultado.agregarError(productoImportado.getNumeroFila(), 
                        "El producto '" + productoImportado.getNombreProducto() + "' ya existe");
                    continue;
                }

                // ✅ CORRECCIÓN: Usar los nombres EXACTOS de los métodos
                Productos producto = new Productos();
                producto.setNombreProducto(productoImportado.getNombreProducto() != null ? 
                    productoImportado.getNombreProducto().trim() : "");
                producto.setCategoria(productoImportado.getCategoria() != null ? 
                    productoImportado.getCategoria().trim() : "");
                producto.setMarcaProducto(productoImportado.getMarca() != null ?  // ✅ setMarcaProducto
                    productoImportado.getMarca().trim() : "");
                producto.setCantidadProducto(Math.max(0, productoImportado.getCantidadProducto()));
                producto.setPrecioProducto(productoImportado.getPrecio() != null ?  // ✅ setPrecioProducto
                    productoImportado.getPrecio().doubleValue() : 0.0);
                producto.setNombreProveedor(productoImportado.getProveedor() != null ?  // ✅ setNombreProveedor
                    productoImportado.getProveedor().trim() : "");
                producto.setFechaCaducidad(productoImportado.getFechaCaducidad());
                producto.setDescripcionProducto(productoImportado.getDescripcion() != null ?  // ✅ setDescripcionProducto
                    productoImportado.getDescripcion().trim() : "");
                producto.setStockProducto(Math.max(0, productoImportado.getStockProducto()));
                producto.setFechaIngreso(new Date());
                
                // Guardar el producto
                productosFacade.create(producto);
                resultado.incrementarImportadosExitosos();
                
                logger.info("Producto creado exitosamente: " + productoImportado.getNombreProducto());

            } catch (Exception e) {
                String errorMsg = "Error al guardar producto '" + productoImportado.getNombreProducto() + "': " + e.getMessage();
                resultado.agregarError(productoImportado.getNumeroFila(), errorMsg);
                logger.log(Level.SEVERE, errorMsg, e);
            }
        }

        return resultado;
    }

    private boolean validarProducto(ProductoImportado producto) {
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            logger.warning("Nombre del producto es obligatorio");
            return false;
        }
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            logger.warning("Categoría es obligatoria para: " + producto.getNombreProducto());
            return false;
        }
        if (producto.getCantidadProducto() <= 0) {
            logger.warning("Cantidad debe ser mayor a 0 para: " + producto.getNombreProducto());
            return false;
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            logger.warning("Precio debe ser mayor a 0 para: " + producto.getNombreProducto());
            return false;
        }
        if (producto.getStockProducto() < 0) {
            logger.warning("Stock mínimo no puede ser negativo para: " + producto.getNombreProducto());
            return false;
        }
        return true;
    }

    // Métodos auxiliares para leer celdas (sin cambios)
    private String obtenerValorCelda(Cell cell) {
        if (cell == null) return null;
        
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    } else {
                        double numValue = cell.getNumericCellValue();
                        if (numValue == Math.floor(numValue)) {
                            return String.valueOf((int) numValue);
                        } else {
                            return String.valueOf(numValue);
                        }
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return obtenerValorCeldaFormula(cell);
                case BLANK:
                    return null;
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.warning("Error al leer valor de celda: " + e.getMessage());
            return null;
        }
    }

    private String obtenerValorCeldaFormula(Cell cell) {
        try {
            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            
            switch (cellValue.getCellType()) {
                case STRING:
                    return cellValue.getStringValue();
                case NUMERIC:
                    return String.valueOf(cellValue.getNumberValue());
                case BOOLEAN:
                    return String.valueOf(cellValue.getBooleanValue());
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private int obtenerValorNumerico(Cell cell) {
        if (cell == null) return 0;
        
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (int) Math.round(cell.getNumericCellValue());
                case STRING:
                    try {
                        return Integer.parseInt(cell.getStringCellValue().trim());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                case FORMULA:
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue cellValue = evaluator.evaluate(cell);
                    if (cellValue.getCellType() == CellType.NUMERIC) {
                        return (int) Math.round(cellValue.getNumberValue());
                    }
                    return 0;
                default:
                    return 0;
            }
        } catch (Exception e) {
            logger.warning("Error al leer valor numérico: " + e.getMessage());
            return 0;
        }
    }

    private BigDecimal obtenerValorDecimal(Cell cell) {
        if (cell == null) return BigDecimal.ZERO;
        
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return BigDecimal.valueOf(cell.getNumericCellValue());
                case STRING:
                    try {
                        return new BigDecimal(cell.getStringCellValue().trim().replace(",", "."));
                    } catch (NumberFormatException e) {
                        return BigDecimal.ZERO;
                    }
                case FORMULA:
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue cellValue = evaluator.evaluate(cell);
                    if (cellValue.getCellType() == CellType.NUMERIC) {
                        return BigDecimal.valueOf(cellValue.getNumberValue());
                    }
                    return BigDecimal.ZERO;
                default:
                    return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            logger.warning("Error al leer valor decimal: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    private Date obtenerValorFecha(Cell cell) {
        if (cell == null) return null;
        
        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String fechaStr = cell.getStringCellValue().trim();
                String[] formatos = {"yyyy-MM-dd", "dd/MM/yyyy", "MM/dd/yyyy", "dd-MM-yyyy"};
                
                for (String formato : formatos) {
                    try {
                        return new SimpleDateFormat(formato).parse(fechaStr);
                    } catch (ParseException e) {
                        // Continuar con el siguiente formato
                    }
                }
            }
        } catch (Exception e) {
            logger.warning("Error al leer fecha: " + e.getMessage());
        }
        return null;
    }

    private String convertirListaAJson(List<String> lista) {
        if (lista == null || lista.isEmpty()) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            if (i > 0) sb.append(",");
            String escaped = lista.get(i)
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
            sb.append("\"").append(escaped).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }

    // Clases auxiliares
    private static class ProductoImportado {
        private int numeroFila;
        private String nombreProducto;
        private String categoria;
        private String marca;
        private int cantidadProducto;
        private BigDecimal precio;
        private String proveedor;
        private Date fechaCaducidad;
        private String descripcion;
        private int stockProducto;
        private String error;

        public int getNumeroFila() { return numeroFila; }
        public void setNumeroFila(int numeroFila) { this.numeroFila = numeroFila; }
        public String getNombreProducto() { return nombreProducto; }
        public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }
        public String getMarca() { return marca; }
        public void setMarca(String marca) { this.marca = marca; }
        public int getCantidadProducto() { return cantidadProducto; }
        public void setCantidadProducto(int cantidadProducto) { this.cantidadProducto = cantidadProducto; }
        public BigDecimal getPrecio() { return precio; }
        public void setPrecio(BigDecimal precio) { this.precio = precio; }
        public String getProveedor() { return proveedor; }
        public void setProveedor(String proveedor) { this.proveedor = proveedor; }
        public Date getFechaCaducidad() { return fechaCaducidad; }
        public void setFechaCaducidad(Date fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public int getStockProducto() { return stockProducto; }
        public void setStockProducto(int stockProducto) { this.stockProducto = stockProducto; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }

    private static class ResultadoImportacion {
        private int totalRegistros;
        private int importadosExitosos;
        private int errores;
        private List<String> detalleErrores = new ArrayList<>();

        public void incrementarImportadosExitosos() { importadosExitosos++; }
        public void agregarError(int fila, String mensaje) { 
            errores++; 
            detalleErrores.add("Fila " + fila + ": " + mensaje);
        }

        public int getTotalRegistros() { return totalRegistros; }
        public void setTotalRegistros(int totalRegistros) { this.totalRegistros = totalRegistros; }
        public int getImportadosExitosos() { return importadosExitosos; }
        public void setImportadosExitosos(int importadosExitosos) { this.importadosExitosos = importadosExitosos; }
        public int getErrores() { return errores; }
        public void setErrores(int errores) { this.errores = errores; }
        public List<String> getDetalleErrores() { return detalleErrores; }
        public void setDetalleErrores(List<String> detalleErrores) { this.detalleErrores = detalleErrores; }
    }
}