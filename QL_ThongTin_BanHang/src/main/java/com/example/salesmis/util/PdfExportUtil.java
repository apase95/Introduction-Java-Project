package com.example.salesmis.util;

import com.example.salesmis.model.entity.OrderDetail;
import com.example.salesmis.model.entity.SalesOrder;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class PdfExportUtil {

    public static void exportInvoice(SalesOrder order, String destPath) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(destPath));
        document.open();

        // Font
        BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(bf, 18, Font.BOLD);
        Font boldFont = new Font(bf, 12, Font.BOLD);
        Font normalFont = new Font(bf, 12, Font.NORMAL);

        // Header
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Order Info
        document.add(new Paragraph("Mã HĐ: " + order.getOrderNo(), boldFont));
        document.add(new Paragraph("Ngày lập: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont));
        
        String cusName = order.getCustomer() != null ? order.getCustomer().getFullName() : "Khách lẻ";
        document.add(new Paragraph("Khách hàng: " + cusName, normalFont));
        
        String phone = (order.getCustomer() != null && order.getCustomer().getPhone() != null) ? order.getCustomer().getPhone() : "";
        if (!phone.isEmpty()) document.add(new Paragraph("SĐT: " + phone, normalFont));
        
        String address = (order.getCustomer() != null && order.getCustomer().getAddress() != null) ? order.getCustomer().getAddress() : "";
        if (!address.isEmpty()) document.add(new Paragraph("Địa chỉ: " + address, normalFont));
        
        if (order.getDiningTable() != null) {
            document.add(new Paragraph("Bàn: " + order.getDiningTable().getTableName(), boldFont));
            if (order.getDiningTable().getZone() != null) {
                document.add(new Paragraph("Khu vực: " + order.getDiningTable().getZone().getZoneName(), normalFont));
            }
        } else {
            document.add(new Paragraph("Loại: Mua mang đi (Takeaway)", boldFont));
        }

        document.add(new Paragraph(" "));


        // Table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{4f, 1f, 2.5f, 2.5f}); // Product, Qty, UnitPrice, LineTotal

        // Table Headers
        addCell(table, "Sản phẩm", boldFont, true);
        addCell(table, "SL", boldFont, true);
        addCell(table, "Đơn giá", boldFont, true);
        addCell(table, "Thành tiền", boldFont, true);

        // Table Data
        for (OrderDetail d : order.getOrderDetails()) {
            String pName = d.getProduct().getProductName();
            if (d.getRecipe() != null) {
                pName += " (" + d.getRecipe().getVariationName() + ")";
            }
            addCell(table, pName, normalFont, false);
            addCell(table, String.valueOf(d.getQuantity()), normalFont, false);
            addCell(table, String.format("%,.0f", d.getUnitPrice()), normalFont, false);
            addCell(table, String.format("%,.0f", d.getLineTotal()), normalFont, false);
        }

        document.add(table);
        document.add(new Paragraph(" "));

        // Total
        Paragraph totalParagraph = new Paragraph("Tổng tiền: " + String.format("%,.0f", order.getTotalAmount()) + " VNĐ", boldFont);
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalParagraph);

        document.close();
    }

    private static void addCell(PdfPTable table, String text, Font font, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        table.addCell(cell);
    }
}
