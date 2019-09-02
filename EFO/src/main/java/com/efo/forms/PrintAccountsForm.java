package com.efo.forms;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.efo.entity.ChartOfAccounts;
import com.efo.pdf.PdfUtilities;
import com.efo.service.ChartOfAccountsService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Component
public class PrintAccountsForm {
	@Value("${efo.documentsPath}")
	private String targetFile;
	String pattern = "yyyyMMddHHmmssSSS";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	@Autowired
	PdfUtilities pdfUtilities;
	
	@Autowired
	ChartOfAccountsService chartOfAccountsService;
	
	public void print() throws FileNotFoundException, MalformedURLException {
		String date = simpleDateFormat.format(new Date());
		final float[] widths = {70f,190f,70f,70f};
		final String[] labels = {"Account Number","Account Name","Balance","Account Type"};
		final String labelColor = "F7F9B9";
		
		PdfDocument pdfDoc = pdfUtilities.createForm(targetFile + "/cofa" + date + ".pdf");
		Document doc = pdfUtilities.createLayout(pdfDoc, "A4");
		pdfUtilities.centerText(doc, "Chart Of Accounts", 18, null);

		pdfUtilities.addImage(doc, 5, 720, "FetalImage.png");
		doc.add(new Paragraph("\n\n\n\n"));
		
		Table table = pdfUtilities.addTable(widths, 0);
		
		
		pdfUtilities.addLabels(table, labels, labelColor, 8, null);
		populateTable(table, chartOfAccountsService.getRawList());
		
		
		doc.add(table);
		doc.close();
	}
	
	
	
	private Table populateTable(Table table, List<ChartOfAccounts> data) {
		String[] row = new String[4];
		final String[] justify = {"L", "L", "R", "C"};
		final String oddRowColor = "D4FFFC";
		final String evenRowColor = "FFFFFF";
		int rowNum = 1;
		
		for (ChartOfAccounts item : data) {
			row[0] = item.getAccountNum();
			row[1] = item.getAccountName();
			row[2] = String.format("%.2f", item.getAccountBalance());
			row[3] = item.getAccountType();
			
			if (rowNum % 2 == 1) {
				pdfUtilities.addRow(table, row, oddRowColor, justify, 8, null);
			}else{
				pdfUtilities.addRow(table, row, evenRowColor, justify, 8, null);
			}
			rowNum++;
		}

		return table;
	}

}
