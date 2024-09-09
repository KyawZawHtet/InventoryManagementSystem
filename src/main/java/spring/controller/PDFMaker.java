package spring.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import spring.dto.InvoiceDetailsDto;

public class PDFMaker {

	public static byte[] makePDF(String oid, String date, String invId, String customer, String address, List<InvoiceDetailsDto> invDt)
	{	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = null;
		PdfWriter writer = new PdfWriter(baos);
		PdfDocument pdfDoc = new PdfDocument(writer);
		document = new Document(pdfDoc);
		Paragraph header = new Paragraph("TRI Angel");
		header.setFontColor(ColorConstants.BLACK);
		header.setTextAlignment(TextAlignment.CENTER);
		document.add(header);
		document.add(new Paragraph("Invoice Number : " + invId));
		document.add(new Paragraph("Order ID : " + oid));
		document.add(new Paragraph("Date : " + date));
		document.add(new Paragraph("Company : " + customer));
		document.add(new Paragraph("Address : " + address));
  
		Table table = new Table(new float[]{1, 1, 1, 1, 1, 1});
		table.setWidth(UnitValue.createPercentValue(100));
		table.setFixedLayout();
		table.addCell(new Cell().add(new Paragraph("Name")));
//		table.addCell(new Cell().add(new Paragraph("Description")));
		table.addCell(new Cell().add(new Paragraph("Quantity")));
		table.addCell(new Cell().add(new Paragraph("Price")));
		table.addCell(new Cell().add(new Paragraph("Expired")));
		table.addCell(new Cell().add(new Paragraph("Total")));
		document.add(table);
		
		double total = 0;
		for(InvoiceDetailsDto dto: invDt)
		{
			double subTotal = dto.getQuantity() * dto.getPrice();
			Table dt = showTable(dto.getPName(), dto.getDescription(), dto.getQuantity(), dto.getPrice(), dto.getExpired(), subTotal);
			document.add(dt);
			total += subTotal;
		}
		
		document.add(new Paragraph("Total: " + total).setTextAlignment(TextAlignment.RIGHT));

		document.close();
		
		return baos.toByteArray();
	}
	
	
	public static Table showTable(String name, String desc, double qty, double price, String expired, double subTotal)
	{
		Table table = new Table(new float[]{1, 1, 1, 1, 1, 1});
		table.setWidth(UnitValue.createPercentValue(100));
        table.setFixedLayout();
		table.addCell(new Cell().add(new Paragraph(name)));
//	    table.addCell(new Cell().add(new Paragraph(desc)));
	    table.addCell(new Cell().add(new Paragraph(qty + "")));
	    table.addCell(new Cell().add(new Paragraph(price + "")));
	    table.addCell(new Cell().add(new Paragraph(expired + "")));
	    table.addCell(new Cell().add(new Paragraph(subTotal + "")));
	    
	    return table;
	}
	
}
