package com.efo.forms;

import java.util.ArrayList;
import java.util.List;

public class PdfForm {
	
	private String pageSize;
	private int direction;
	private Header header;
	private Content content;
	private Footer footer;
	private float height;
	private float width;
	
	
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Footer getFooter() {
		return footer;
	}

	public void setFooter(Footer footer) {
		this.footer = footer;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	class Header {
		protected List<Object> form;

		public Header() {
			this.form = new ArrayList<Object>();
		}
		
	}
	
	class Content {
		protected List<Object> form;
		
		public Content() {
			this.form = new ArrayList<Object>();
		}
		
	}
	
	class Footer {
		protected List<Object> form;
		
		public Footer() {
			this.form = new ArrayList<Object>();
		}
	}
	
}
