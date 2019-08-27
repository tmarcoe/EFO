package com.efo.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ProfileUtils {
	
	
	public static Object[] getObject(String varString) throws ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		final String daoPath = "com.efo.entity.";

		if ("".compareTo(varString) == 0) return null;
		
		
		String[] vars = varString.split(";");
		Object[] result = new Object[vars.length];
		
		for (int i=0; i < vars.length; i++) {
			String[] parms = vars[i].split(",");
			switch(parms[1].toLowerCase()) {
				case "decimal":
					result[i] = (Double) Double.valueOf(parms[2]);
					break;
				case "number":
					result[i] = (Long) Long.valueOf(parms[2]);
					break;
				case "string":
					result[i] = (String) parms[2];
					break;
				case "date":
					result[i] = (Date) dateFormat.parse(parms[2]);
					break;
				case "boolean":
					if ("true".compareTo(parms[2]) == 0) {
						result[i] = (Boolean) true;
					}else{
						result[i] = (Boolean) false;
					}
					break;
				case "object":
					result[i] = Class.forName(parms[2]).newInstance();
					break;
				case "dao":
					result[i] = Class.forName(daoPath + parms[2]).newInstance(); 
					break;
				default:
					result[i] = null;
					break;
			}
		}
		return result;
	}
	
	public static String prepareVariableString(String key, String value, String input) {
		String keys[] = key.split(",");
		String values[] = value.split(",");
		String result = input;
		
		if (keys.length != values.length) return "ERROR:keys do not match the values";
		
		for(int i=0; i < keys.length; i++) {
			if (result.toLowerCase().contains(keys[i])) {
				result = result.replace(keys[i], values[i]);
			}
		}
		
		return result;
	}

}
