package com.efo.emailForms;

import static j2html.TagCreator.body;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import org.springframework.stereotype.Component;

@Component
public class ApproveEmailForm {
	
	public String createApproveEmail(String name, String budgetTitle) {
		String result = html(
				
				head(
					style()	
					),
				body(
					h3("Dear " + name + ","),
					h3("Your budget titled '" + budgetTitle + "', has been approved.")
				)).renderFormatted();
		
		return result;
	}
	
	

}
