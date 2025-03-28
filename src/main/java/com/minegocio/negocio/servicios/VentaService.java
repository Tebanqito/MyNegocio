package com.minegocio.negocio.servicios;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.minegocio.negocio.entidades.Cliente;
import com.minegocio.negocio.entidades.Producto;
import com.minegocio.negocio.entidades.Venta;
import com.minegocio.negocio.entidades.VentaDetalle;
import com.minegocio.negocio.repositorios.ClienteRepository;
import com.minegocio.negocio.repositorios.ProductoRepository;
import com.minegocio.negocio.repositorios.VentaRepository;
import com.minegocio.negocio.requests.VentaRequestDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Transactional
    public Venta registrarVenta(Long clienteId, List<VentaRequestDetalle> detalles) {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());

        if (clienteId != null) {
            Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
            venta.setCliente(cliente);
        }

        double total = 0.0;

        for (VentaRequestDetalle detalleRequest : detalles) {
            Producto producto = productoRepository.findById(detalleRequest.getProductoId()).orElseThrow();

            if (producto.getStock() < detalleRequest.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - detalleRequest.getCantidad());
            productoRepository.save(producto);

            VentaDetalle detalle = new VentaDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecioUnitario());
            detalle.setSubtotal(detalleRequest.getCantidad() * producto.getPrecioUnitario());

            venta.getDetalles().add(detalle);
            total += detalle.getSubtotal();
        }

        venta.setTotal(total);
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

    public List<Venta> obtenerPorFecha(LocalDate fecha) {
        return ventaRepository.findByFechaBetween(fecha.atStartOfDay(), fecha.plusDays(1).atStartOfDay());
    }

    public List<Venta> obtenerPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return ventaRepository.findByFechaBetween(desde.atStartOfDay(), hasta.plusDays(1).atStartOfDay());
    }

    public List<Venta> obtenerPorCliente(String cliente) {
        return ventaRepository.findByClienteNombreIgnoreCase(cliente);
    }

    public List<Venta> obtenerPorMontoMinimo(double minimo) {
        return ventaRepository.findByTotalGreaterThanEqual(minimo);
    }

    public List<Venta> obtenerPorProducto(String nombreProducto) {
        return ventaRepository.findByProducto(nombreProducto);
    }
}