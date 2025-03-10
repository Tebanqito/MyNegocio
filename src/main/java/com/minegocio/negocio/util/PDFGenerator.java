package com.minegocio.negocio.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.minegocio.negocio.dto.ReciboDTO;
import com.minegocio.negocio.dto.ProductoDTO;

import java.io.ByteArrayOutputStream;

public class PDFGenerator {

    public static byte[] generarReciboPDF(ReciboDTO recibo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("Recibo de Venta"));
            document.add(new Paragraph("Número: " + recibo.getNumeroRecibo()));
            document.add(new Paragraph("Fecha: " + recibo.getFecha()));
            document.add(new Paragraph("Cliente: " + recibo.getCliente()));
            document.add(new Paragraph("Productos:"));

            for (ProductoDTO producto : recibo.getProductos()) {
                document.add(new Paragraph("- " + producto.getNombre() +
                        " | Cantidad: " + producto.getCantidad() +
                        " | Precio Unitario: $" + producto.getPrecioUnitario() +
                        " | Subtotal: $" + producto.getSubtotal()));
            }

            document.add(new Paragraph("Total: $" + recibo.getTotal()));

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return baos.toByteArray();
    }
}
