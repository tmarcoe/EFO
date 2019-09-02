package com.efo.util;

import java.util.ArrayList;
import java.util.List;

public class TestPdf {
	private int line;
	private String description;
	private double price;
	private double tax;
	
	
	
	public TestPdf() {
	}

	private TestPdf(int line, String description, double price, double tax) {

		this.line = line;
		this.description = description;
		this.price = price;
		this.tax = tax;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public List<TestPdf> buildList() {
		List<TestPdf> pdfList = new ArrayList<TestPdf>();
		pdfList.add(new TestPdf(1, "This is it", 5.2, 0.52));
		pdfList.add(new TestPdf(2, "The quick brown fox", 15.12, 1.51));
		pdfList.add(new TestPdf(3, "This is a test", 29.7, 2.9));
		pdfList.add(new TestPdf(4, "This is it", 75.3, 7.53));
		pdfList.add(new TestPdf(5, "of the emergency", 55.6, 5.56));
		pdfList.add(new TestPdf(6, "botched up system", 73.2, 7.32));
		pdfList.add(new TestPdf(7, "Know it of forget it", 49.9, 4.99));
		pdfList.add(new TestPdf(8, "We will return you now", 537.73, 53.77));
		
		
		return pdfList;
	}

}
