package com.minegocio.negocio.servicios;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.minegocio.negocio.entidades.Producto;
import com.minegocio.negocio.entidades.Venta;
import com.minegocio.negocio.entidades.VentaDetalle;
import com.minegocio.negocio.repositorios.ProductoRepository;
import com.minegocio.negocio.repositorios.VentaRepository;
import com.minegocio.negocio.requests.VentaRequestDetalle;
import com.minegocio.negocio.util.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Transactional
    public Venta registrarVenta(String cliente, List<VentaRequestDetalle> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos un producto en la venta.");
        }

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());

        double total = 0.0;

        for (VentaRequestDetalle detalleRequest : detalles) {
            Producto producto = productoRepository.findById(detalleRequest.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Producto con ID " + detalleRequest.getProductoId() + " no encontrado."));

            if (producto.getStock() < detalleRequest.getCantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Actualizar stock del producto
            producto.setStock(producto.getStock() - detalleRequest.getCantidad());
            productoRepository.save(producto);

            // Crear detalle de la venta
            VentaDetalle detalle = new VentaDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecioUnitario());
            detalle.setSubtotal(MathUtils.redondearPrecio(detalleRequest.getCantidad() * producto.getPrecioUnitario()));

            // Agregar el detalle a la venta
            venta.getDetalles().add(detalle);
            total += detalle.getSubtotal();
        }

        // Establecer el total de la venta
        venta.setTotal(MathUtils.redondearPrecio(total));

        // Guardar la venta
        return ventaRepository.save(venta);
    }

    public byte[] generarRecibo(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new IllegalArgumentException("La venta con ID " + ventaId + " no existe."));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Encabezado
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            document.add(new Paragraph("Recibo de Venta", boldFont));
            document.add(new Paragraph("Fecha: " + venta.getFecha().toString()));
            document.add(new Paragraph("Cliente: " + venta.getCliente()));
            document.add(Chunk.NEWLINE);

            // Tabla de productos
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 1, 1, 1});

            addTableHeader(table);
            for (VentaDetalle detalle : venta.getDetalles()) {
                table.addCell(detalle.getProducto().getNombre());
                table.addCell(String.valueOf(detalle.getCantidad()));
                table.addCell(String.format("%.2f", detalle.getPrecioUnitario()));
                table.addCell(String.format("%.2f", detalle.getSubtotal()));
            }

            document.add(table);

            // Total
            document.add(Chunk.NEWLINE);
            Paragraph totalParagraph = new Paragraph("Total: $" + String.format("%.2f", venta.getTotal()), boldFont);
            totalParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParagraph);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el recibo de la venta.", e);
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Producto", "Cantidad", "Precio Unitario", "Subtotal")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}