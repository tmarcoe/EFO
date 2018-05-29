package com.efo.forms;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PdfFormImage {
	private int xPos;
	private int yPos;
	private float width;
	private float height;
	private String filePath;
	
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	public static PdfFormImage buildImage(NodeList nList) {
		PdfFormImage image = new PdfFormImage();
		for (int i=0; i < nList.getLength() ; i++) {
			Node nd = nList.item(i);
			if (nd.getNodeType() == Node.ELEMENT_NODE) {
				String value = nd.getFirstChild().getNodeValue();
				switch (nd.getNodeName()) {
				case "xPos":
					image.setxPos(Integer.valueOf(value));
					break;
					
				case "yPos":
					image.setyPos(Integer.valueOf(value));
					break;
					
				case "width":
					image.setWidth(Float.valueOf(value));
					break;
					
				case "height":
					image.setHeight(Float.valueOf(value));
					break;
					
				case "filePath":
					image.setFilePath(value);
					break;
				}
			}
		}
		
		return image;
	}
}
