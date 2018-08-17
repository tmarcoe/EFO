package com.efo.emailForms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static j2html.TagCreator.*;

import com.efo.entity.CommonFields;
import com.efo.entity.RetailSales;
import com.efo.entity.SalesItem;
import com.efo.entity.User;
import com.efo.service.UserService;

@Component
public class SalesReceipt {
	
	@Autowired
	private UserService userService;
	
	public String createSalesReceipt(RetailSales sales) {
		User user = userService.retrieve(sales.getCustomer_id());
		CommonFields commonFields = user.getCommon();
		double subtotal = 0.0;
		double tax = 0.0;
		double grandTotal = 0.0;
		
		for(SalesItem item : sales.getSalesItem()) {
			subtotal += item.getRegular_price();
		}
		tax = subtotal * 0.08;
		grandTotal = subtotal + tax;
		
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
				h3("Sales Receipt for " + user.getCustomer().getFirstname() + " " + user.getCustomer().getLastname()),
				h3("Invoice # " + sales.getInvoice_num()),
				address(
						join(commonFields.getAddress1(),br(),
							commonFields.getAddress2(),br(),
							commonFields.getCity() + ", " + commonFields.getRegion() + ", " + commonFields.getPostalCode(), br(),
							commonFields.getCountry(), br(), hr()
							)
						),
				table(attrs("#sales.tableview"),
					tbody(
						tr(
							th("Item"),
							th("Qty"),
							th("Price"),
							th("Tax")
						),
						each(sales.getSalesItem(), salesItem ->
							tr(
								td(salesItem.getProduct_name()),
								td(String.valueOf(salesItem.getQty())),
								td(String.format("%.2f",salesItem.getQty() * salesItem.getRegular_price())),
								td(String.format("%.2f",(salesItem.getQty() * salesItem.getRegular_price()) * .08))
							)	
						)			
					),
					tfoot(
							tr(
								td(" "),
								td(" "),
								td(" "),
								td("Subtotal: " + String.format("%.2f",subtotal))
							),
							tr(
								td(" "),
								td(" "),
								td(" "),
								td("Tax: " + String.format("%.2f",tax))
							),
							tr(
								td(" "),
								td(" "),
								td(" "),
								td("Grand Total: " + String.format("%.2f",grandTotal))
							)
						)
				)) 
			).renderFormatted();
		
		return result;
	}

}
