package com.efo.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TabAlignment;

@Component
public class PdfUtilities {
	@Value("${efo.formimagePath}")
	private String imagePath;
	
	@Autowired
	PdfTable pdfTable;
	
	public PdfDocument createForm( String targetFile) throws FileNotFoundException {
		
		File file = new File(targetFile);
		file.getParentFile().mkdirs();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(targetFile));
		
		return pdfDoc;
	}
	
	public Document createLayout(PdfDocument pdfDoc, String pageSize) {
		Document doc = null;
		
		switch (pageSize) {
		case "A0": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A0), true);
			break;
		case "A1": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A1), true);
			break;
		case "A2": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A2), true);;
			break;
		case "A3": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A3), true);
			break;
		case "A4": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A4), true);
			break;
		case "A5": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A5), true);
			break;
		case "A6": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A6), true);
			break;
		case "A7": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A7), true);
			break;
		case "A8": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A8), true);
			break;
		default:
			doc = new Document(pdfDoc, new PageSize(PageSize.Default), true);
			break;
		}

		return doc;
	}
	
	public Table addTable(float[] widths, float topMargin) {
		Table table = pdfTable.addTable(widths, topMargin);
		
		return table;
	}
	
	public void addLabels(Table table, String[] labels, String color, float fontSize, PdfFont font) {
			pdfTable.addLabels(table, labels, color, fontSize, font);
	}
	
	public void addRow(Table table, Object[] data, String color, String[] justify, float fontSize, PdfFont font) {
			pdfTable.addRow(table, data, color, justify, fontSize, font);
	}
	
	public Document centerText(Document doc, String text, float fontSize, PdfFont font) {
		PageSize pagesize = PageSize.A4;
        float w = pagesize.getWidth() - doc.getLeftMargin() - doc.getRightMargin();

        List<TabStop> tabstops = new ArrayList<TabStop>();

        tabstops.add(new TabStop(w / 2, TabAlignment.CENTER));
        tabstops.add(new TabStop(w, TabAlignment.LEFT));
        Paragraph p = new Paragraph();
        if (font != null) {
        	p.setFont(font);
        }
        p.setFontSize(fontSize);

        p.addTabStops(tabstops);
        p.add(new Tab()).add(text).add(new Tab());

        doc.add(p);
		
        return doc;
	}

	public Image addImage(Document doc, float x, float y, String imageFile) throws MalformedURLException {
		
		ImageData data = ImageDataFactory.create(imagePath + "/" + imageFile); 
		Image image = new Image(data);
		image.setFixedPosition(x,y);
		doc.add(image);
		
		return image;
	}
	
    class MyLine implements ILineDrawer {
        private float lineWidth = 1;
        private float offset = 5;
        private Color color = Color.BLACK;

        @Override
        public void draw(PdfCanvas canvas, Rectangle drawArea) {

            canvas.saveState()
                .setStrokeColor(color)
                .setLineWidth(lineWidth)
                .moveTo(drawArea.getX(), drawArea.getY() + lineWidth / 2 + offset)
                .lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + lineWidth / 2 + offset)
                .stroke()
                .restoreState();
        }

		@Override
		public Color getColor() {
			return this.color;
		}
		@Override
		public void setColor(Color color) {
			this.color = color;
		}

		@Override
		public float getLineWidth() {
			return this.lineWidth;
		}

		@Override
		public void setLineWidth(float lineWidth) {
			this.lineWidth = lineWidth;
		}
    }
}
