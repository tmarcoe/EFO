package com.efo.reports;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.efo.service.CustomerService;


@Component
public class MaleVsFemaleReport {
	
	@Autowired
	private CustomerService customerService;
	
	public String maleVsFemale() throws JSONException {
		Object[] counts = customerService.getMaleFemaleCount();
		
		BigInteger t = (BigInteger) counts[0];
		BigDecimal m =  (BigDecimal) counts[1];
		BigDecimal f = (BigDecimal) counts[2];
		BigDecimal ttl = new BigDecimal(t);
		
		
		
		double mPercent = ((m.doubleValue()/ ttl.doubleValue()) * 100.00);
		double fPercent = ((f.doubleValue()/ ttl.doubleValue()) * 100.00);
		
		return mfToJSON(mPercent, fPercent, "Male vs Female Customers").toString();
	}
	
	private JSONObject mfToJSON(Double mPercent, Double fPercent, String reportTitle) throws JSONException {
		JSONObject json = new JSONObject();
		
		JSONObject d1 = new JSONObject();
		JSONObject options = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject title = new JSONObject();
		
		JSONArray datasets = new JSONArray();
		JSONArray labels = new JSONArray();
		JSONArray colors = new JSONArray();
		JSONArray genders = new JSONArray();
		
		
		colors.put("#3333cc");
		colors.put("#ff6699");
		
		labels.put("Male");
		labels.put("Female");

		genders.put(mPercent);
		genders.put(fPercent);
		
		json.put("type", "pie");
		json.put("data", data);
		data.put("labels", labels);
		data.put("datasets", datasets);
		datasets.put(d1);
		d1.put("label", "Gender Demographics" );
		d1.put("backgroundColor", colors);
		d1.put("data", genders);
		json.put("options", options);
		options.put("responsive", false);
		options.put("display", true);
		options.put("title", title);
		title.put("display", true);
		title.put("text", reportTitle);
		title.put("fontSize", 28);
				
		return json;
	}

}
