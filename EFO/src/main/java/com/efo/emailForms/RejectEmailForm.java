package com.efo.emailForms;

import static j2html.TagCreator.body;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import org.springframework.stereotype.Component;

@Component
public class RejectEmailForm {
	
	public String createRejectEmail(String name, String budgetTitle, String reason) {
		String result = html(
				
				head(
					style()	
					),
				body(
					h3("Dear " + name + ","),
					h3("Your budget titled '" + budgetTitle + "' has been rejected. The reason(s) given are as follows:"),
					h3(reason),
					h3("Please address these issues to prevent any delays.")
				)).renderFormatted();
		
		return result;
	}
	
	

}
