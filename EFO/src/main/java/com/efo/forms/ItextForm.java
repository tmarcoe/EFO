package com.efo.forms;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.support.PagedListHolder;
import org.xml.sax.SAXException;

import com.efo.forms.PdfFormTable.Column;
import com.efo.forms.PdfFormTable.Totals;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.WebColors;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

public class ItextForm {
	private Map<String, Variable> var;
	private Map<String, String> fld;

	public void createForm(PagedListHolder<?> data, String sourceFile, String targetFile)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException, ParserConfigurationException, SAXException {
		PdfForm pdfForm = new ReadXML().createDocument(sourceFile);
		File file = new File(targetFile);
		var = new HashMap<String, Variable>();
		fld = new HashMap<String, String>();
		Document doc = null;
		Rectangle rect;
		file.getParentFile().mkdirs();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(targetFile));
		switch (pdfForm.getPageSize()) {
		case "A0": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A0), true);
			rect = doc.getPageEffectiveArea(PageSize.A0);
			break;
		case "A1": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A1), true);
			rect = doc.getPageEffectiveArea(PageSize.A1);
			break;
		case "A2": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A2), true);
			rect = doc.getPageEffectiveArea(PageSize.A2);
			break;
		case "A3": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A3), true);
			rect = doc.getPageEffectiveArea(PageSize.A3);
			break;
		case "A4": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A4), true);
			rect = doc.getPageEffectiveArea(PageSize.A4);
			break;
		case "A5": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A5), true);
			rect = doc.getPageEffectiveArea(PageSize.A5);
			break;
		case "A6": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A6), true);
			rect = doc.getPageEffectiveArea(PageSize.A6);
			break;
		case "A7": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A7), true);
			rect = doc.getPageEffectiveArea(PageSize.A7);
			break;
		case "A8": 
			doc = new Document(pdfDoc, new PageSize(PageSize.A8), true);
			rect = doc.getPageEffectiveArea(PageSize.A8);
			break;
		default:
			doc = new Document(pdfDoc, new PageSize(PageSize.Default), true);
			rect = doc.getPageEffectiveArea(PageSize.Default);
			break;
		}
		pdfForm.setHeight(rect.getHeight());
		pdfForm.setWidth(rect.getWidth());
		displayHeader(doc, pdfDoc, pdfForm, pdfForm.getHeader(), data);
		displayContent(doc, pdfDoc, pdfForm, pdfForm.getContent(), data);
		displayFooter(doc, pdfDoc, pdfForm, pdfForm.getFooter(), data);
		
		doc.close();
	}

	private void displayHeader(Document doc, PdfDocument pdfDoc, PdfForm pdfForm, PdfForm.Header header, PagedListHolder<?> data)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {
		for (Object item : header.form) {
			if (item instanceof PdfFormImage) {
				addImage(doc, pdfDoc, pdfForm, (PdfFormImage) item);
			} else if (item instanceof PdfFormText) {
				addText(doc, pdfDoc, pdfForm, (PdfFormText) item);
			} else if (item instanceof PdfFormTable) {
				addTable(doc, pdfDoc, (PdfFormTable) item, data);

			}
		}

	}

	private void displayContent(Document doc, PdfDocument pdfDoc, PdfForm pdfForm, PdfForm.Content content, PagedListHolder<?> data)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {
		for (Object item : content.form) {
			if (item instanceof PdfFormImage) {
				addImage(doc, pdfDoc, pdfForm, (PdfFormImage) item);
			} else if (item instanceof PdfFormText) {
				addText(doc, pdfDoc, pdfForm, (PdfFormText) item);
			} else if (item instanceof PdfFormTable) {
				addTable(doc, pdfDoc, (PdfFormTable) item, data);

			}
		}
	}

	private void displayFooter(Document doc, PdfDocument pdfDoc, PdfForm pdfForm, PdfForm.Footer footer, PagedListHolder<?> data)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {
		for (Object item : footer.form) {
			if (item instanceof PdfFormImage) {
				addImage(doc, pdfDoc, pdfForm, (PdfFormImage) item);
			} else if (item instanceof PdfFormText) {
				addText(doc, pdfDoc, pdfForm, (PdfFormText) item);
			} else if (item instanceof PdfFormTable) {
				addTable(doc, pdfDoc, (PdfFormTable) item, data);

			}
		}
	}

	private Document addTable(Document doc, PdfDocument pdfDoc, PdfFormTable pdfFormTable, PagedListHolder<?> data)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {
		boolean oddRow = true;
		Table table = new Table(pdfFormTable.getWidths());
		table.setMarginTop(pdfFormTable.getTopMargin());
		table.setHorizontalAlignment(HorizontalAlignment.CENTER);
		table = addLabels(pdfFormTable, table);
		for (Object item : data.getSource()) {
			table = addRow(pdfFormTable, table, item, oddRow);
			oddRow = !oddRow;
		}
		for (Totals item : pdfFormTable.getTotals()) {
			table = addTotalsRow(pdfFormTable, table, item);
		}
		doc.add(table);
		return doc;
	}

	private Table addTotalsRow(PdfFormTable pdfFormTable, Table table, Totals item) {
		for (int i=0; i < pdfFormTable.getLabels().length ; i++) {
			Cell cell = new Cell();
			cell.setBackgroundColor(WebColors.getRGBColor(pdfFormTable.getTotalsColor()));
			if (item.getDescriptionColumn() == (i+1)) {
				Paragraph par = new Paragraph(item.getDescription());
				par.setFixedLeading(0);
				par.setMultipliedLeading(1);
				cell.setTextAlignment(TextAlignment.RIGHT);
				cell.add(par);
				
			}
			if (item.getDisplayColumn() == (i+1)) {
				Variable v = var.get(item.getName());
				
				Paragraph par = new Paragraph(String.format(item.getFormat(), v.getValue()));
				par.setFixedLeading(0);
				par.setMultipliedLeading(1);
				cell.setTextAlignment(TextAlignment.RIGHT);
				cell.add(par);
				if (item.getTotalTo() != null) {
					
					addTotalVar(item.getTotalTo(), v.getValue(), v.getType());
				}

			}
			if (item.getDescriptionColumn() != (i+1) && item.getDisplayColumn() != (i+1) ) {
				cell.add(" ");
			}
			table.addCell(cell);
		}
		return table;
	}

	private Table addLabels(PdfFormTable pdfFormTable, Table table) {
		String[] labels = pdfFormTable.getLabels();

		for (int i = 0; i < labels.length; i++) {
			Paragraph lbl = new Paragraph(labels[i]);
			lbl.setFixedLeading(0);
			lbl.setMultipliedLeading(1);
			Cell cell = new Cell();
			cell.setTextAlignment(TextAlignment.CENTER);
			cell.setBold();			
			cell.setBackgroundColor(WebColors.getRGBColor(pdfFormTable.getHdrColor()));
			cell.add(lbl);
			table.addHeaderCell(cell);
		}

		return table;
	}

	private Table addRow(PdfFormTable pdfFormTable, Table table, Object data, boolean oddRow)
			throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<Column> fieldList = pdfFormTable.getColumns();

		for (Column field : fieldList) {
			String displayData = String.format(field.getFormat(), getFieldByName(field.getName(), data));
			field.setData(getFieldByName(field.getName(), data));
			Paragraph cellData = new Paragraph(displayData);
			cellData.setFixedLeading(0);
			cellData.setMultipliedLeading(1);
			Cell cell = new Cell();
			if (oddRow == true) {
				cell.setBackgroundColor(WebColors.getRGBColor(pdfFormTable.getOddRowColor()));
			} else {
				cell.setBackgroundColor(WebColors.getRGBColor(pdfFormTable.getEvenRowColor()));
			}
			switch (field.getAlign()) {
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

			if (field.getTotalTo() != null) {
				addTotalVar(field.getTotalTo(), field.getData(), field.getType());
			}
		}

		return table;
	}

	private void addTotalVar(String name, Object data, String type) {
		if (var.containsKey(name)) {
			Variable valueVar = var.get(name);
			Object value = valueVar.getValue();
			Object addTo = data;
			switch (valueVar.getType()) {
			case INTEGER: {
				int lVal = (int) value;
				int rVal = (int) addTo;
				valueVar.value = lVal + rVal;
			}
				break;
			case LONG: {
				long lVal = (long) value;
				long rVal = (long) addTo;
				valueVar.value = lVal + rVal;
			}
				break;
			case FLOAT: {
				float lVal = (float) value;
				float rVal = (float) addTo;
				valueVar.value = lVal + rVal;
			}
				break;
			case DOUBLE: {
				double lVal = (double) value;
				double rVal = (double) addTo;
				valueVar.value = lVal + rVal;
			}
				break;
			default:
				break;
			}

			var.put(name, valueVar);
		} else {
			Variable valueVar = new Variable();
			valueVar.setValue(data);
			switch (type) {
			case "integer":
				valueVar.type = Variable.Type.INTEGER;
				break;
			case "long":
				valueVar.type = Variable.Type.LONG;
				break;
			case "float":
				valueVar.type = Variable.Type.FLOAT;
				break;
			case "double":
				valueVar.type = Variable.Type.DOUBLE;
				break;
			case "string":
				valueVar.type = Variable.Type.STRING;
				break;
			case "date":
				valueVar.type = Variable.Type.DATE;
				break;
			}
			
			var.put(name, valueVar);
		}

	}

	private void addTotalVar(String name, Object data, Variable.Type type) {
		if (var.containsKey(name)) {
			Variable valueVar = var.get(name);
			Object value = valueVar.getValue();
			Object addTo = data;
			switch (valueVar.getType()) {
			case INTEGER: {
				int lVal = (int) value;
				int rVal = (int) addTo;
				valueVar.value = lVal + rVal;
			}
				break;
			case LONG: {
				long lVal = (long) value;
				long rVal = (long) addTo;
				valueVar.value = lVal + rVal;
			}
				break;
			case FLOAT: {
				float lVal = (float) value;
				float rVal = (float) addTo;
				valueVar.value = lVal + rVal;
			}
				break;
			case DOUBLE: {
				double lVal = (double) value;
				double rVal = (double) addTo;
				valueVar.value = lVal + rVal;
			}
				break;
			default:
				break;
			}

			var.put(name, valueVar);
		} else {
			Variable valueVar = new Variable();
			valueVar.setValue(data);
			valueVar.type = type;
			var.put(name, valueVar);
		}

	}

	
	
	private Object getFieldByName(String name, Object parentClass) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);

		Method method = parentClass.getClass().getMethod(methodName);

		return method.invoke(parentClass);
	}

	private Document addImage(Document doc, PdfDocument pdfDoc, PdfForm pdfForm, PdfFormImage pdfImage) throws MalformedURLException {
		ImageData imgData = ImageDataFactory.create(pdfImage.getFilePath());
		Image img = new Image(imgData);
		float[] x_y = translateCoordinates(pdfForm, pdfImage.getxPos(), pdfImage.getyPos());
		img.setFixedPosition(x_y[0], x_y[1]);
		doc.add(img);

		return doc;
	}

	private Document addText(Document doc, PdfDocument pdfDoc, PdfForm pdfForm, PdfFormText text) {
		Paragraph paragraph = new Paragraph(convertText(text.getText()));
	
		float[] x_y = translateCoordinates(pdfForm, text.getxPos(), text.getyPos());
		paragraph.setFontSize(text.getFontSize());
		paragraph.setFixedPosition(1, x_y[0], x_y[1], pdfForm.getWidth());
		doc.add(paragraph);
		
		return doc;

	}
	
	private String convertText(String text) {
		TextField tf = new TextField();
		tf.setStart(0);
		tf.setEnd(0);
		tf.setText(text);
		
		while (hasNext(tf) == true) {
			tf = getField(tf);
		}
		text = tf.getText();
		return text;
	}
	
	private boolean hasNext(TextField tf) {
		return (tf.getStart() != -1);
	}
	
	private TextField getField(TextField tf) {
		tf.setStart(tf.getText().indexOf("${"));
		
		if (tf.getStart() == -1) {
			return tf;
		}
		
		tf.setEnd(tf.getText().indexOf('}', tf.getStart()));
		
		tf.setVar(tf.getVar().substring(tf.getStart(), tf.getEnd() + 1));
		String fieldName = fld.get(tf.getVar().substring(2, tf.getVar().length() - 1));
		String replaceText = fld.get(fieldName);
		
		if (replaceText == null) {
			replaceText = "";
		}
		
		tf.setText(tf.getText().replace(tf.getVar(), replaceText));
		
		return tf;
	}
	
	private float[] translateCoordinates(PdfForm pdfForm, float x, float y) {
		float[] orderedPair = {(float)0.0, (float)0.0};
		
		orderedPair[0] = x;
		orderedPair[1] = pdfForm.getHeight() - y;
		
		return orderedPair;
	}
	static class Variable {
		enum Type {
			INTEGER, LONG, FLOAT, DOUBLE, DATE, STRING
		}

		private Object value;
		private Type type;

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}
	}
	
	class TextField {
		private int start;
		private int end;
		private String var;
		private String text;
		
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
		public String getVar() {
			return var;
		}
		public void setVar(String var) {
			this.var = var;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}

}
