package com.efo.restController;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.entity.Customer;
import com.efo.entity.Employee;
import com.efo.entity.FluidInventory;
import com.efo.entity.Product;
import com.efo.entity.Vendor;
import com.efo.service.CustomerService;
import com.efo.service.EachInventoryService;
import com.efo.service.EmployeeService;
import com.efo.service.ProductService;
import com.efo.service.VendorService;

@RestController
@RequestMapping("/rest/")
public class QueryController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EachInventoryService inventoryService;

	@RequestMapping("lookupname")
	public String lookupName(@RequestParam(value = "name") String name) throws JSONException {
		List<Product> product = productService.nameSearch(name);

		return productToJson(product);
	}

	@RequestMapping("lookupcustomer")
	public String lookupCustomer(@RequestParam(value = "name") String name) throws JSONException {
		List<Customer> customer = customerService.queryCustomer(name);
		
		return customerToJson(customer);
	}
	
	@RequestMapping("lookupvendor")
	public String lookupVendor(@RequestParam(value = "name") String name, @RequestParam(value= "type") String type) throws JSONException  {
		List<Vendor> vendorList = vendorService.queryVendow(name, type);
		
		return vendorToJson(vendorList);
	}
	
	@RequestMapping("lookupemployee")
	public String lookupEmployee(@RequestParam(value = "name") String name) throws JSONException {
		
		List<Employee> employeeList = employeeService.queryEmployee(name);
		
		return employeeToJson(employeeList);
	}
	
	@RequestMapping("checkstock")
	public String checkStock(@RequestParam(value = "sku") String sku ) throws JSONException {
		FluidInventory inventory = null;
		Product product = productService.retrieve(sku);
		if ("Each".compareTo(product.getUnit()) == 0 || "Pack".compareTo(product.getUnit()) == 0) {
			inventory = new FluidInventory();
			inventory.setSku(sku);
			inventory.setAmt_committed(inventoryService.getAmountCommitted(sku));
			inventory.setAmt_ordered(inventoryService.getAmountOrdered(sku));
			inventory.setAmt_in_stock(inventoryService.getAmountReceived(sku));
		}else{
			inventory = product.getFluidInventory();
		}
		
		return inventoryToJson(inventory);
	}
	
	private String vendorToJson(List<Vendor> v) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (Vendor item : v) {
			JSONObject suggestion = new JSONObject();
			JSONObject vendor = new JSONObject();
			vendor.put("user_id", item.getUser_id());
			vendor.put("company_name", item.getCompany_name());
			vendor.put("salutation", item.getSalutation());
			vendor.put("firstname", item.getFirstname());
			vendor.put("lastname", item.getLastname());
			vendor.put("type", item.getType());
			vendor.put("category", item.getCategory());
			vendor.put("keywords", item.getKeywords());
			
			suggestion.put("value", item.getCompany_name());
			suggestion.put("data", vendor);
			jsonArray.put(suggestion);
		}
		return jsonArray.toString();
	}
	private String inventoryToJson(FluidInventory i) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("sku", i.getSku());
		json.put("amt_in_stock", i.getAmt_in_stock());
		json.put("amt_committed", i.getAmt_committed());
		json.put("amt_ordered", i.getAmt_ordered());

		return json.toString();
	}
	
	
	private String productToJson(List<Product> p) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (Product item : p) {
			JSONObject suggestion = new JSONObject();
			JSONObject product = new JSONObject();
			product.put("sku", item.getSku());
			product.put("upc", item.getUpc());
			product.put("product_name", item.getProduct_name());
			product.put("price", item.getPrice());
			product.put("unit", item.getUnit());
			product.put("category", item.getCategory());
			product.put("subcategory", item.getSubcategory());
			product.put("keywords", item.getKeywords());
			product.put("on_sale", item.isOn_sale());
			product.put("discontinue", item.isDiscontinue());

			suggestion.put("value", item.getProduct_name());
			suggestion.put("data", product);
			jsonArray.put(suggestion);
		}
		
		return jsonArray.toString();
	}

	private String customerToJson(List<Customer> c) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for(Customer item : c) {
			JSONObject suggestion = new JSONObject();
			suggestion.put("value", item.getFirstname() + " " + item.getLastname());
			suggestion.put("data", item.getUser_id());
			jsonArray.put(suggestion);
		}
		
		return jsonArray.toString();
	}
	
	private String employeeToJson(List<Employee> e) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (Employee item : e) {
			JSONObject suggestion = new JSONObject();
			suggestion.put("value", item.getFirstname() + " " + item.getLastname());
			suggestion.put("data", item.getEmp_financial().getHourlyRate());
			jsonArray.put(suggestion);
		}
		
		return jsonArray.toString();
	}
}
