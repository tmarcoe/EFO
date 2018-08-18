package com.efo.emailForms;

import org.springframework.stereotype.Component;

import com.efo.entity.Budget;
import static j2html.TagCreator.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class BudgetReport {
		
	public String creatBudgetReport(Budget budget) {
		DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		String result = html(
				head(
						style(
								"table{ 	"
								   + "margin-left:auto;" 
								   + "margin-right:auto;"
								   + "margin-top: 2em;"
								   + "border-collapse: collapse;"
								   + "font-size: 12px;"
								   + "}"
							  + "table th{"
							  	   + "background: #f5f3c2;"
							  + "}"
							  + "table td{"
							  	   + "border: 1px solid black;"
							  	   + "text-align: right;"
							  + "}"
							  + "table tfoot{"
							  	   + "background: #dedbee;"
							  + "}"
							)	

					),
				body(
						h3(budget.getTitle()),
						h3("Budget Author: " + budget.getAuthor()),
						h3("Department: " + budget.getDepartment()),
						h3("Budget Period From " + df.format(budget.getBegin()) + " To " + df.format(budget.getEnd())),
						h3("Submitted on: " + df.format(budget.getSubmitted())),
						table(
								tbody(
										th("Budget Item"),
										th("Amount"),
										th("Justification"),
										each(budget.getBudgetItems(), budgetItems ->
											tr(
												td(budgetItems.getCategory()),
												td(String.format("%.02f", budgetItems.getAmount())),
												td(
													textarea(budgetItems.getJustification())
											  )
											)
										)
									),
								tfoot(
										tr(
											td("Budet Total: " + String.format("%.02f", budget.getTotal()))
										  )
									)
							)
					)
				).renderFormatted();
		
		return result;
	}

}
