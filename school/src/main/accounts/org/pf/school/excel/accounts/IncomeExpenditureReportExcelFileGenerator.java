package org.pf.school.excel.accounts;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pf.school.model.accounts.Expenditure;
import org.pf.school.model.accounts.HeadOfAccount;
import org.pf.school.model.accounts.Income;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class IncomeExpenditureReportExcelFileGenerator {
	
	private List<Income> listIncome;
	private List<Expenditure> listExpenditure;
	
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
	
    public IncomeExpenditureReportExcelFileGenerator(List<Income> listIncome, List<Expenditure> listExpenditure) {
        this.listExpenditure = listExpenditure;
        this.listIncome = listIncome;
        
        workbook = new XSSFWorkbook();
    }
    
    private void writeHeader() {
        sheet = workbook.createSheet("Accounts Report");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);
        
        row = sheet.createRow(1);
        
        createCell(row, 0, "Income", style);
        createCell(row, 11, "Expenditure", style);
        
        row = sheet.createRow(2);
        row = sheet.createRow(3);
        
        createCell(row, 0, "Update Date", style);
        createCell(row, 1, "Transaction Date", style);
        createCell(row, 2, "Account Head", style);
        createCell(row, 3, "Receipt Date", style);
        createCell(row, 4, "Receipt No.", style);
        createCell(row, 5, "Narration", style);
        createCell(row, 6, "Received From", style);
        createCell(row, 7, "Mode of Receipt", style);
        createCell(row, 8, "Amount", style);
        createCell(row, 9, "Purpose", style);
        
        createCell(row, 11, "Update Date", style);
        createCell(row, 12, "Transaction Date", style);
        createCell(row, 13, "Account Head", style);
        createCell(row, 14, "Voucher Date", style);
        createCell(row, 15, "Voucher / Invoice No.", style);
        createCell(row, 16, "Narration", style);
        createCell(row, 17, "Paid To", style);
        createCell(row, 18, "Mode of Payment", style);
        createCell(row, 19, "Amount", style);
        createCell(row, 20, "Purpose", style);
    }
    
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell == null) {
        	cell.setCellValue("");
        } else {
	        if (valueOfCell instanceof Integer) {
	            cell.setCellValue((Integer) valueOfCell);
	        } else if (valueOfCell instanceof Long) {
	            cell.setCellValue((Long) valueOfCell);
	        } else if (valueOfCell instanceof String) {
	            cell.setCellValue((String) valueOfCell);
	        } else if (valueOfCell instanceof BigDecimal) {
	        	cell.setCellValue(((BigDecimal) valueOfCell).toString());
	        } else if (valueOfCell instanceof Date) {
	        	cell.setCellValue((Date) valueOfCell);
	        } else if (valueOfCell instanceof HeadOfAccount) {
	        	cell.setCellValue(((HeadOfAccount) valueOfCell).getName());
	        }
	        else {
	            cell.setCellValue((Boolean) valueOfCell);
	        }
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 5;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(9);
        style.setFont(font);
        
        CellStyle styleDate = workbook.createCellStyle();
        CellStyle styleDateFull = workbook.createCellStyle();
        
        styleDate.setFont(font);
        styleDateFull.setFont(font);
        
        CreationHelper createHelper = workbook.getCreationHelper();
        
        styleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy")); // Example format
        styleDateFull.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm:ss")); // Example format

        
        
        for (Income record: listIncome) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getRecordAddDate(), styleDateFull);
            createCell(row, columnCount++, record.getTransactionDate(), styleDate);
            createCell(row, columnCount++, record.getHeadOfAccount().getName(), style);
            createCell(row, columnCount++, record.getReceiptDate(), styleDate);
            createCell(row, columnCount++, record.getReceiptNumber(), style);
            createCell(row, columnCount++, record.getNarration(), style);
            createCell(row, columnCount++, record.getReceivedFrom(), style);
            createCell(row, columnCount++, record.getModeOfReceipt(), style);
            createCell(row, columnCount++, record.getAmount(), style);
            createCell(row, columnCount++, record.getTowards(), style);
        }
        
        rowCount = 5;
        for (Expenditure record: listExpenditure) {
            Row row = sheet.getRow(rowCount);
            if (row == null) row = sheet.createRow(rowCount);
            rowCount++;
            int columnCount = 11;
            createCell(row, columnCount++, record.getRecordAddDate(), styleDateFull);
            createCell(row, columnCount++, record.getTransactionDate(), styleDate);
            createCell(row, columnCount++, record.getHeadOfAccount().getName(), style);
            createCell(row, columnCount++, record.getVoucherDate(), styleDate);
            createCell(row, columnCount++, record.getVoucherInvoiceNumber(), style);
            createCell(row, columnCount++, record.getNarration(), style);
            createCell(row, columnCount++, record.getPaidTo(), style);
            createCell(row, columnCount++, record.getModeOfPayment(), style);
            createCell(row, columnCount++, record.getAmount(), style);
            createCell(row, columnCount++, record.getTowards(), style);
        }
    }
    
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
        outputStream.close();
    }
}
