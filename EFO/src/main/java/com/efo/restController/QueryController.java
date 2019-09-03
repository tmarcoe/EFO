package com.efo.restController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.efo.entity.Investor;
import com.efo.entity.Profiles;
import com.efo.entity.Vendor;
import com.efo.service.CustomerService;
import com.efo.service.EmployeeService;
import com.efo.service.InvestorService;
import com.efo.service.ProfilesService;
import com.efo.service.TimeSheetItemsService;
import com.efo.service.TimeSheetService;
import com.efo.service.TransactionsService;
import com.efo.service.VendorService;

@RestController
@RequestMapping("/rest/")
public class QueryController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private InvestorService investorService;
	
	@Autowired
	private TransactionsService transactionsService;
	
	@Autowired
	private ProfilesService profilesService;
	
	@Autowired
	private TimeSheetService timeSheetService;
	
	@Autowired
	private TimeSheetItemsService timeSheetItemsService;
	
	@RequestMapping("lookupcustomer")
	public String lookupCustomer(@RequestParam(value = "name") String name) throws JSONException {
		List<Customer> customer = customerService.queryCustomer(name);
		
		return customerToJson(customer);
	}
	
	@RequestMapping("lookupvendor")
	public String lookupVendor(@RequestParam(value = "name") String name,@RequestParam(value = "type") String type ) throws JSONException  {
		List<Vendor> vendorList = vendorService.queryVendow(name, type);
		
		return vendorToJson(vendorList);
	}
	
	@RequestMapping("lookupinvestor")
	public String lookupInvestor(@RequestParam(value = "name") String name) throws JSONException {
		
		List<Investor> investorList = investorService.queryInvestor(name);
		
		return investorToJson(investorList);
	}
	
	@RequestMapping("lookupemployee")
	public String lookupEmployee(@RequestParam(value = "name") String name) throws JSONException {
		
		List<Employee> employeeList = employeeService.queryEmployee(name);
		
		return employeeToJson(employeeList);
	}
	
	@RequestMapping("overheadexists")
	public String overheadExists(@RequestParam(value = "name") String name, @RequestParam(value = "profilename") String profileName ) throws JSONException {
		boolean exists = transactionsService.overheadExists(name, profileName);
		
		return overheadExistToJson(exists);
	}
	
	@RequestMapping("showterms")
	public String showTerms(@RequestParam(value = "profilename") String profileName) throws JSONException {
		
		Profiles profile = profilesService.retrieve(profileName);
		
		
		return showTermsToJson(profile.isShow_credit_terms());
	}
	
	@RequestMapping("accountnumexists")
	public String accountNumExists(@RequestParam(value = "accountNum") String accountNum, 
									@RequestParam(value = "reference") Long reference) throws JSONException {
		JSONObject json = new JSONObject();
		
		json.put("exists", timeSheetItemsService.accountNumExists(accountNum, reference));
		
		return json.toString();
	}
	
	@RequestMapping("sameperiod")
	public String samePeriod(@RequestParam(value = "begin") String begin, 
							 @RequestParam(value = "user_id") Long user_id, 
							 @RequestParam(value = "reference") Long reference) throws ParseException, JSONException {
		JSONObject json = new JSONObject();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		
		json.putOpt("exists",  timeSheetService.checkIfPeriodExists(dateFormat.parse(begin), user_id, reference));
		
		
		return json.toString();
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
	
	private String investorToJson(List<Investor> i) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for(Investor item: i) {
			JSONObject suggestion = new JSONObject();
			JSONObject investor = new JSONObject();
			investor.put("user_id", item.getUser_id());
			investor.put("firstname", item.getFirstname());
			investor.put("lastname", item.getLastname());
			investor.put("male_female", item.getMale_female());
			investor.put("shares", item.getShares());
			investor.put("preferred", item.isPreferred());
			investor.putOpt("since", item.getSince());
			
			suggestion.put("value", item.getFirstname() + " " + item.getLastname());
			suggestion.put("data", investor);
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
			suggestion.put("rate", item.getEmp_financial().getPay_rate());
			suggestion.put("type", item.getEmp_type());
			
			jsonArray.put(suggestion);
		}
		
		return jsonArray.toString();
	}
	
	
	private String overheadExistToJson(boolean exists) throws JSONException {
		JSONObject jsonExists = new JSONObject();
		jsonExists.put("exists", exists);
		
		return jsonExists.toString();
	}
	
	private String showTermsToJson(boolean showTerms) throws JSONException {
		JSONObject jsonShowTerms = new JSONObject();
		jsonShowTerms.put("showCreditTerms", showTerms);
		
		return jsonShowTerms.toString();
	}
	
}
