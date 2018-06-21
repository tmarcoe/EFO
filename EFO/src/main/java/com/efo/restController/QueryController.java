package com.efo.restController;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.entity.Product;
import com.efo.service.ProductService;

@RestController
@RequestMapping("/rest/")
public class QueryController {

	@Autowired
	ProductService productService;

	@RequestMapping("lookupname")
	public String lookupName(@RequestParam(value = "name") String name) throws JSONException {
		Product product = productService.nameSearch(name);

		return productToJson(product);
	}

	private String productToJson(Product p) throws JSONException {
		JSONObject json = new JSONObject();
		if (p != null) {
			json.put("sku", p.getSku());
			json.put("upc", p.getUpc());
			json.put("product_name", p.getProduct_name());
			json.put("price", p.getPrice());
			json.put("unit", p.getUnit());
			json.put("category", p.getCategory());
			json.put("subcategory", p.getSubcategory());
			json.put("keywords", p.getKeywords());
			json.put("on_sale", p.isOn_sale());
			json.put("discontinue", p.isDiscontinue());
		}else{
			json.put("sku", "");
			json.put("upc", "");
			json.put("product_name", "");
			json.put("price", Double.valueOf(0.00));
			json.put("unit", "");
			json.put("category", "");
			json.put("subcategory", "");
			json.put("keywords", "");
			json.put("on_sale", Boolean.valueOf(false));
			json.put("discontinue",Boolean.valueOf(false));			
		}

		return json.toString();
	}
}
