package com.efo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.efo.forms.ItextForm;
import com.efo.forms.ReadXML;

@Configuration
public class PdfForms {
	
	@Bean
	public ReadXML getXMLDoc() {
		return new ReadXML();
	}
	
	@Bean
	public ItextForm itextForm() {
		return new ItextForm();
	}
}
