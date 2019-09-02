package com.efo.pdf;

import org.springframework.stereotype.Component;

import com.itextpdf.kernel.color.WebColors;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

@Component
public class PdfTable {
	

	public Table addTable(float[] widths, float topMargin) {
		
		Table table = new Table(widths);
		table.setMarginTop(topMargin);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);
		
		return table;
	}
	
	public void addLabels(Table table, String[] labels, String color, float fontSize, PdfFont font ) {
		
		for (int i = 0; i < labels.length; i++) {
			Paragraph lbl = new Paragraph(labels[i]);
			lbl.setFixedLeading(0);
			lbl.setMultipliedLeading(1);
			Cell cell = new Cell();
			cell.setTextAlignment(TextAlignment.CENTER);
			cell.setBold();			
			cell.setBackgroundColor(WebColors.getRGBColor(color));
			cell.setFontSize(fontSize);
			if (font != null) {
				cell.setFont(font);
			}
			cell.add(lbl);
			table.addHeaderCell(cell);
		}
		
	}
	
	public void addRow(Table table, Object[] data, String color, String[] justify, float fontSize, PdfFont font) {
		for (int i=0; i < data.length; i++) {
			Paragraph cellData = new Paragraph(data[i].toString());
			cellData.setFixedLeading(0);
			cellData.setMultipliedLeading(1);
			Cell cell = new Cell();
			cell.setBackgroundColor(WebColors.getRGBColor(color));
			cell.setFontSize(fontSize);
			if (font != null) {
				cell.setFont(font);
			}
			
			switch (justify[i]) {
			case "l":
			case "L":
				cell.setTextAlignment(TextAlignment.LEFT);
				break;
			case "c":
			case "C":
				cell.setTextAlignment(TextAlignment.CENTER);
				break;
			case "r":
			case "R":
				cell.setTextAlignment(TextAlignment.RIGHT);
				break;
			}

			cell.add(cellData);
			table.addCell(cell);
		}
	}

}
