package com.efo.forms;


import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PdfFormTable {
	private String title;
	private String[] labels;
	private float[] widths;
	private List<Column> columns;
	private List<Totals> totals;
	private NamedNodeMap attributes;
	private String hdrColor;
	private String oddRowColor;
	private String evenRowColor;
	private String totalsColor;
	private float topMargin;
	
	private PdfFormTable() {
		
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getLabels() {
		return labels;
	}
	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public float[] getWidths() {
		return widths;
	}

	public void setWidths(float[] widths) {
		this.widths = widths;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> fields) {
		this.columns = fields;
	}
	
	public List<Totals> getTotals() {
		return totals;
	}

	public void setTotals(List<Totals> totals) {
		this.totals = totals;
	}

	public String getHdrColor() {
		return hdrColor;
	}

	public void setHdrColor(String hdrColor) {
		this.hdrColor = hdrColor;
	}

	public String getOddRowColor() {
		return oddRowColor;
	}

	public void setOddRowColor(String oddRowColor) {
		this.oddRowColor = oddRowColor;
	}

	public String getEvenRowColor() {
		return evenRowColor;
	}

	public void setEvenRowColor(String evenRowColor) {
		this.evenRowColor = evenRowColor;
	}

	public String getTotalsColor() {
		return totalsColor;
	}

	public void setTotalsColor(String totalsColor) {
		this.totalsColor = totalsColor;
	}

	public float getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(float topMargin) {
		this.topMargin = topMargin;
	}

	public static PdfFormTable buildTable(NodeList nList) {
		PdfFormTable table = new PdfFormTable();
		table.setColumns(new ArrayList<Column>());
		table.setTotals(new ArrayList<Totals>());
		table.setHdrColor("FFFFFF");
		table.setOddRowColor("FFFFFF");
		table.setEvenRowColor("FFFFFF");
		table.setTotalsColor("FFFFFF");
		table.setTopMargin(0);
		Node name = null;
		Node totalTo = null;
		for (int i = 0; i < nList.getLength(); i++) {
			Node nd = nList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				String value = nd.getFirstChild().getNodeValue();
				switch (nd.getNodeName()) {
				case "topMargin":
					table.setTopMargin(Float.valueOf(value));
					break;
					
				case "title":
					table.setTitle(value);
					break;
					
				case "labels":
					String[] lbl = value.split(",");
					table.setLabels(lbl);
					break;
				case "widths":
					table.setWidths(table.parseWidths(value));
					break;
					
				case "headerColor":
					table.setHdrColor(value);
					break;
					
				case "oddRowColor":
					table.setOddRowColor(value);
					break;
					
				case "evenRowColor":
					table.setEvenRowColor(value);
					break;
					
				case "totalsColor":
					table.setTotalsColor(value);
					break;
										
				case "column":
					table.attributes = nd.getAttributes();
					if (table.attributes == null ) break;
					name = table.attributes.getNamedItem("name");
					if (name == null) break;
					totalTo = table.attributes.getNamedItem("totalTo");
					table.getColumns().add(table.addColumn(nd.getChildNodes(), name.getNodeValue(), totalTo)); 
					break;
					
				case "total":
					table.attributes = nd.getAttributes();
					if (table.attributes == null) break;
					name = table.attributes.getNamedItem("name");
					if (name == null) break;
					totalTo = table.attributes.getNamedItem("totalTo");
					table.getTotals().add(table.addTotal(nd.getChildNodes(), name.getNodeValue(), totalTo));
					break;
				}
			}
		}

		return table;
	}
	private float[] parseWidths(String widths) {
		String[] splitWidths = widths.split(",");
		
		float[] result = new float[splitWidths.length];
		for (int i=0; i < splitWidths.length; i++) {
			result[i] = Float.valueOf(splitWidths[i]);
		}
		
		return result;
	}
	private Column addColumn(NodeList nList, String name, Node totalTo) {
		Column field = new Column();
		field.setName(name);
		if (totalTo != null) {
			field.setTotalTo(totalTo.getNodeValue());
		}
		for (int i=0; i < nList.getLength(); i++) {
			Node nd = nList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				String value = nd.getFirstChild().getNodeValue();
				switch(nd.getNodeName()) {
				case "name":
					field.setName(value);
					break;
				case "align":
					field.setAlign(value);
					break;
				case "type":
					field.setType(value);
					break;
				case "format":
					field.setFormat(value);
					break;
				}
			}
		}
		
		return field;
	}
	
	
	private Totals addTotal(NodeList nList, String name, Node totalTo) {
		NamedNodeMap attributes;
		Totals total = new Totals();
		total.setName(name);
		total.setDescriptionColumn(1);
		total.setDisplayColumn(1);
		if (totalTo != null) {
			total.setTotalTo(totalTo.getNodeValue());
		}
		for (int i=0; i < nList.getLength(); i++) {
			Node nd = nList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				String value = nd.getFirstChild().getNodeValue();
				switch(nd.getNodeName()) {
				case "description":
					attributes = nd.getAttributes();
					if (attributes != null) {
						Node column = attributes.getNamedItem("column");
						total.setDescriptionColumn(Integer.valueOf(column.getNodeValue()));
					}
					total.setDescription(value);
					break;
					
				case "format":
					attributes = nd.getAttributes();
					if (attributes != null) {
						Node column = attributes.getNamedItem("column");
						total.setDisplayColumn((Integer.valueOf(column.getNodeValue())));
					}
					total.setFormat(value);
					break;
					
				case "align":
					total.setAlign(value);
					break;
				}
			}
		}
		
		return total;
	}
	
	class Column {
		private String name;
		private String align;
		private Object data;
		private String type;
		private String format;
		private String totalTo;
		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAlign() {
			return align;
		}
		public void setAlign(String align) {
			this.align = align;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public String getTotalTo() {
			return totalTo;
		}
		public void setTotalTo(String totalTo) {
			this.totalTo = totalTo;
		}
	}
	
	class Totals {
		private String name;
		private String description;
		private int descriptionColumn;
		private int displayColumn;
		private String align;
		private String format;
		private String totalTo;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		public int getDescriptionColumn() {
			return descriptionColumn;
		}
		public void setDescriptionColumn(int descriptionColumn) {
			this.descriptionColumn = descriptionColumn;
		}
		public int getDisplayColumn() {
			return displayColumn;
		}
		public void setDisplayColumn(int displayColumn) {
			this.displayColumn = displayColumn;
		}
		public String getAlign() {
			return align;
		}
		public void setAlign(String align) {
			this.align = align;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public String getTotalTo() {
			return totalTo;
		}
		public void setTotalTo(String totalTo) {
			this.totalTo = totalTo;
		}
	}
	
}
