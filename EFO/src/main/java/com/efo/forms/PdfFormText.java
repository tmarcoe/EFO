package com.efo.forms;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PdfFormText {
	private float xPos;
	private float yPos;
	private float fontSize;
	private String align;
	private String text;
	
	public float getxPos() {
		return xPos;
	}
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}
	public float getyPos() {
		return yPos;
	}
	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	public float getFontSize() {
		return fontSize;
	}
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public static PdfFormText buildText(NodeList nList) {
		PdfFormText text = new PdfFormText();
		for (int i=0; i < nList.getLength() ; i++) {
			Node nd = nList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				String value = nd.getFirstChild().getNodeValue();
				switch (nd.getNodeName()) {
				case "xPos":
					text.setxPos(Float.valueOf(value));
					break;
					
				case "yPos":
					text.setyPos(Float.valueOf(value));
					break;
				
				case "fontSize":
					text.setFontSize(Integer.valueOf(value));
					break;
					
				case "align":
					text.setAlign(value);
					break;
					
				case "text":
					text.setText(value);
					break;
					
				}
			}
		}

		return text;
	}
}
