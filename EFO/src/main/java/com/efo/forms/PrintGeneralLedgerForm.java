package com.efo.forms;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Component;

import com.efo.entity.GeneralLedger;
import com.efo.pdf.PdfUtilities;
import com.efo.service.GeneralLedgerService;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Component
public class PrintGeneralLedgerForm {
	@Value("${efo.documentsPath}")
	private String targetFile;
	private PdfFont font = null;
	private PdfFont boldFont = null;
		
	@Autowired
	PdfUtilities pdfUtilities;
	
	@Autowired
	private GeneralLedgerService generalLedgerService;
	
	public void print(Date from, Date to) throws IOException {
		String headingPattern = "MMMMM dd, yyyy";
		SimpleDateFormat df = new SimpleDateFormat(headingPattern);
		String period = String.format("From: %-20s To: %s", df.format(from), df.format(to));
		font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		boldFont = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		final String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		final float[] widths = {80f, 70f,70f,70f,190f};
		final String[] labels = {"Date","Account Number","Debit","Credit", "Description"};
		final String labelColor = "F7F9B9";
		
		PdfDocument pdfDoc = pdfUtilities.createForm(targetFile + "/gl" + date + ".pdf");
		Document doc = pdfUtilities.createLayout(pdfDoc, "A4");
		pdfUtilities.centerText(doc, "General Ledger", 18, boldFont);
		pdfUtilities.centerText(doc, period, 12, boldFont);

		pdfUtilities.addImage(doc, 5, 720, "FetalImage.png");
		doc.add(new Paragraph("\n\n"));
		
		Table table = pdfUtilities.addTable(widths, 0);
		
		
		pdfUtilities.addLabels(table, labels, labelColor, 8, boldFont);
		PagedListHolder<GeneralLedger> glList = generalLedgerService.getPagedList(from, to);
		populateTable(doc, table, glList.getSource());
		
		
		doc.add(table);
		doc.close();
	}
	
	private Table populateTable(Document doc, Table table, List<GeneralLedger> data) {
		
		String dateFormat = "MMMMM dd, yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		String[] row = new String[5];
		final String[] justify = {"L", "L", "R", "R", "L"};
		final String oddRowColor = "D4FFFC";
		final String evenRowColor = "FFFFFF";
		int rowNum = 1;
		
		for (GeneralLedger item : data) {
			row[0] = simpleDateFormat.format(item.getEntryDate());
			row[1] = item.getAccountNum();
			if (item.getDebitAmt() > 0f) {
				row[2] = String.format("%.2f", item.getDebitAmt());
			}else{
				row[2] = "";
			}
			if (item.getCreditAmt() > 0f) {
				row[3] = String.format("%.2f", item.getCreditAmt());
			}else{
				row[3] = "";
			}
			row[4] = item.getDescription();
			
			if (rowNum % 2 == 1) {
				pdfUtilities.addRow(table, row, oddRowColor, justify, 8, font);
			}else{
				pdfUtilities.addRow(table, row, evenRowColor, justify, 8, font);
			}
			
			rowNum++;
		}

		return table;
	}

}
