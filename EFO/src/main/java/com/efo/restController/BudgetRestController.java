package com.efo.restController;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.service.BudgetItemsService;


@RestController
@RequestMapping("/rest/")
public class BudgetRestController {
	
	@Autowired
	private BudgetItemsService budgetService;
	
	@RequestMapping("sumchildren")
	public String sumChildren(@RequestParam(value = "user_id") Integer user_id, 
							  @RequestParam(value = "parent") String parent) throws JSONException {
		
		Double sum = budgetService.sumChildren(user_id, parent);
		if (sum == null) sum = 0.0;
		
		JSONObject json = new JSONObject();
		json.put("sum", sum);
		
		return json.toString();
	}

}
