package com.efo.forms;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXML {
	
	public PdfForm createDocument(String filePath) throws ParserConfigurationException, SAXException, IOException {
		PdfForm pdfDoc = new PdfForm();
		pdfDoc.setPageSize("default");
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File(filePath));

		doc.getDocumentElement().normalize();
		Node root = getNodeByName("pdfDoc", doc.getChildNodes());
		if (root == null) {
			throw new ParserConfigurationException("<pdfDoc> not present");
		}
		NodeList nodeList = root.getChildNodes();
		for(int i=0; i < nodeList.getLength(); i++) {
			Node nd = nodeList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				String value = nd.getFirstChild().getNodeValue();
				switch (nd.getNodeName()) {
				case "pageSize":
					pdfDoc.setPageSize(value);
					break;
				case "direction":
					pdfDoc.setDirection(Integer.valueOf(value));
					break;
				case "header":
					pdfDoc = buildHeader(pdfDoc, nd.getChildNodes());
					break;
				case "content":
					pdfDoc = buildContent(pdfDoc, nd.getChildNodes());
					break;
				case "footer":
					pdfDoc = buildFooter(pdfDoc, nd.getChildNodes());
					break;
				}
				
			}
		}
		
		return pdfDoc;
	}
	
	private PdfForm buildHeader(PdfForm pdfForm, NodeList nList) {
		if (nList == null) return pdfForm;
		pdfForm.setHeader(pdfForm.new Header());
		for (int i=0; i < nList.getLength() ; i++) {
			Node nd = nList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				switch (nd.getNodeName()) {
				case "table":
					pdfForm.getHeader().form.add((PdfFormTable) buildTable(nd.getChildNodes()));
					break;
					
				case "image":
					pdfForm.getHeader().form.add((PdfFormImage) buildImage(nd.getChildNodes()));
					break;
					
				case "text":
					pdfForm.getHeader().form.add((PdfFormText) buildText(nd.getChildNodes()));
					break;
				}
			}
		}
		
		return pdfForm;
		
	}
	
	private PdfForm buildContent(PdfForm pdfDoc, NodeList nList) {
		if (nList == null) return pdfDoc;
		pdfDoc.setContent(pdfDoc.new Content());
		for (int i=0; i < nList.getLength() ; i++) {
			Node nd = nList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				switch (nd.getNodeName()) {
				case "table":
					pdfDoc.getContent().form.add((PdfFormTable) buildTable(nd.getChildNodes()));
					break;
					
				case "image":
					pdfDoc.getContent().form.add((PdfFormImage) buildImage(nd.getChildNodes()));
					break;
					
				case "text":
					pdfDoc.getContent().form.add((PdfFormText) buildText(nd.getChildNodes()));
					break;
				}
			}
		}
		
		return pdfDoc;
	}
	
	private PdfForm buildFooter(PdfForm pdfDoc, NodeList nList) {
		if (nList == null) return pdfDoc;
		pdfDoc.setFooter(pdfDoc.new Footer());
		for (int i=0; i < nList.getLength() ; i++) {
			Node nd = nList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				switch (nd.getNodeName()) {
				case "table":
					pdfDoc.getFooter().form.add((PdfFormTable) buildTable(nd.getChildNodes()));
					break;
					
				case "image":
					pdfDoc.getFooter().form.add((PdfFormImage) buildImage(nd.getChildNodes()));
					break;
					
				case "text":
					pdfDoc.getFooter().form.add((PdfFormText) buildText(nd.getChildNodes()));
					break;
				}
			}
		}
		
		return pdfDoc;
	}
	

	private PdfFormTable buildTable(NodeList nList) {
		
		return PdfFormTable.buildTable(nList);
	}
	
	private PdfFormImage buildImage(NodeList nList) {
		return PdfFormImage.buildImage(nList);
	}
	
	private PdfFormText buildText(NodeList nList) {
		return PdfFormText.buildText(nList);
	}
	
	private Node getNodeByName(String nodeName, NodeList nList) {
		Node result = null;
		for (int i = 0; i < nList.getLength(); i++) {
			if (nodeName.compareTo(nList.item(i).getNodeName()) == 0) {
				result = nList.item(i);
				break;
			}

		}

		return result;
	}
	
}
