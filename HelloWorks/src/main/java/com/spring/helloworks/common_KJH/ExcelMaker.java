package com.spring.helloworks.common_KJH;

import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.helloworks.model.BillnotaxDetailVO_KJH;
import com.spring.helloworks.model.BillnotaxVO_KJH;
import com.spring.helloworks.model.BilltaxDetailVO_KJH;
import com.spring.helloworks.model.BilltaxVO_KJH;
import com.spring.helloworks.model.TransactionDetailVO_KJH;
import com.spring.helloworks.model.TransactionVO_KJH;
import com.spring.helloworks.service.InterHelloWorksService_KJH;

@Component
public class ExcelMaker {
	
	@Autowired
	private InterHelloWorksService_KJH service;
	
	public SXSSFWorkbook excelOfBilltax(String[] seqArr) {
				
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		String sheetName = "";
		
		int cnt = 1;
		
		Map<String, String> paraMap = new HashMap<> ();
		
		for(String seq : seqArr) {
			
			paraMap.put("seq", seq);
				
			BilltaxVO_KJH doc = service.getBilltaxDoc(paraMap);
			
			List<BilltaxDetailVO_KJH> detailList = service.getDetailBilltaxList(paraMap);
			
			sheetName = (!"".equals(doc.getCustomer_comp()))?"세금계산서_" + doc.getCustomer_comp() + cnt:"세금계산서_" + doc.getCustomer_name() + cnt;
			
			cnt++;
			
			SXSSFSheet sheet = workbook.createSheet(sheetName);
			
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 1000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 5000);
			sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 8000);
			
			int rowLocation = 0;
			
			// 제목부분
			Font titleFont = workbook.createFont();
			titleFont.setFontHeight((short)500);
			titleFont.setColor(IndexedColors.RED.getIndex());
			titleFont.setBold(true);
			
			Font redFont = workbook.createFont();
			redFont.setColor(IndexedColors.RED.getIndex());
			redFont.setBold(true);
			
			CellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			titleStyle.setFont(titleFont);
			titleStyle.setBorderTop(BorderStyle.THIN);
			titleStyle.setBorderBottom(BorderStyle.THIN);
			titleStyle.setBorderLeft(BorderStyle.THIN);
			titleStyle.setBorderRight(BorderStyle.THIN);
			titleStyle.setTopBorderColor(IndexedColors.RED.getIndex());
			titleStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
			titleStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
			titleStyle.setRightBorderColor(IndexedColors.RED.getIndex());
			
			CellStyle normalStyle = workbook.createCellStyle();
			normalStyle.setAlignment(HorizontalAlignment.CENTER);
			normalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			normalStyle.setFont(redFont);
			normalStyle.setBorderTop(BorderStyle.THIN);
			normalStyle.setBorderBottom(BorderStyle.THIN);
			normalStyle.setBorderLeft(BorderStyle.THIN);
			normalStyle.setBorderRight(BorderStyle.THIN);
			normalStyle.setTopBorderColor(IndexedColors.RED.getIndex());
			normalStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
			normalStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
			normalStyle.setRightBorderColor(IndexedColors.RED.getIndex());
			
			CellStyle valStyle = workbook.createCellStyle(); // 2칸 합칠 것
			valStyle.setAlignment(HorizontalAlignment.CENTER);
			valStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			valStyle.setBorderTop(BorderStyle.THIN);
			valStyle.setBorderBottom(BorderStyle.THIN);
			valStyle.setBorderLeft(BorderStyle.THIN);
			valStyle.setBorderRight(BorderStyle.THIN);
			valStyle.setTopBorderColor(IndexedColors.RED.getIndex());
			valStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
			valStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
			valStyle.setRightBorderColor(IndexedColors.RED.getIndex());
			
			CellStyle moneyStyle = workbook.createCellStyle();
	        moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
	        moneyStyle.setAlignment(HorizontalAlignment.CENTER);
	        moneyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        moneyStyle.setBorderTop(BorderStyle.THIN);
	        moneyStyle.setBorderBottom(BorderStyle.THIN);
	        moneyStyle.setBorderLeft(BorderStyle.THIN);
	        moneyStyle.setBorderRight(BorderStyle.THIN);
	        moneyStyle.setTopBorderColor(IndexedColors.RED.getIndex());
	        moneyStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
	        moneyStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
	        moneyStyle.setRightBorderColor(IndexedColors.RED.getIndex());
	        
			Cell cell;
			
			Row row1 = sheet.createRow(rowLocation);
			
			for(int i=0; i<5; i++) {
				cell = row1.createCell(i);
				
				cell.setCellStyle(titleStyle);
				cell.setCellValue("세금계산서");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4));
			
			cell = row1.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("일련번호");
			
			
			for(int i=6; i<8; i++) {
				cell = row1.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue("HELLOWORKS-" + doc.getBilltax_seq());
			}
			
			///////////////////////////////////////////////////////	
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			Row row2 = sheet.createRow(++rowLocation);
			
			for(int i=0; i<5; i++) {
				cell = row2.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("공급자");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4));
			
			for(int i=5; i<8; i++) {
				cell = row2.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("공급받는자");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 5, 7));
			
			Row row3 = sheet.createRow(++rowLocation);
			
			cell = row3.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("등록번호");
			
			for(int i=1; i<5; i++) {
				cell = row3.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row3.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("등록번호");
			
			for(int i=6; i<8; i++) {
				cell = row3.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));

			Row row4 = sheet.createRow(++rowLocation);
			
			cell = row4.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("상호(업체명)");
			
			for(int i=1; i<5; i++) {
				cell = row4.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_comp());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row4.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("상호(업체명)");
			
			for(int i=6; i<8; i++) {
				cell = row4.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_comp());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			

			Row row5 = sheet.createRow(++rowLocation);
			
			cell = row5.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("성명");
			
			for(int i=1; i<5; i++) {
				cell = row5.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_name());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row5.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("성명");
			
			for(int i=6; i<8; i++) {
				cell = row5.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_name());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			Row row6 = sheet.createRow(++rowLocation);
			
			cell = row6.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("사업장주소");
			
			for(int i=1; i<5; i++) {
				cell = row6.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_addr());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row6.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("사업장주소");
			
			for(int i=6; i<8; i++) {
				cell = row6.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_addr());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			

			Row row7 = sheet.createRow(++rowLocation);
			
			cell = row7.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("업종");
			
			for(int i=1; i<5; i++) {
				cell = row7.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row7.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("업종");
			
			for(int i=6; i<8; i++) {
				cell = row7.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			///////////////////////////////////////////////////////
			
			Row row8 = sheet.createRow(++rowLocation);
			
			cell = row8.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("작성일");
			
			for(int i=1; i<5; i++) {
				cell = row8.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("공급가액");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			for(int i=5; i<6; i++) {
				cell = row8.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("세액");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 5, 6));
			
			cell = row8.createCell(7);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("비고");
			
			Row row9 = sheet.createRow(++rowLocation);
			
			cell = row9.createCell(0);
			cell.setCellStyle(valStyle);
			cell.setCellValue(doc.getRegdate().substring(0,10));
			
			for(int i=1; i<5; i++) {
				cell = row9.createCell(i);
				
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(doc.getTotalprice());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			for(int i=5; i<7; i++) {
				cell = row9.createCell(i);
				
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(doc.getTaxprice());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 5, 6));
			
			cell = row9.createCell(7);
			cell.setCellStyle(valStyle);
			cell.setCellValue("");
			
			
			
			////////////////////////////////////////////////////////////////////////
			
			Row bodyRow = null;
			
			bodyRow = sheet.createRow(++rowLocation);
			
			cell = bodyRow.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("공급일자");
			
			cell = bodyRow.createCell(1);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("품목");
			
			cell = bodyRow.createCell(2);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("규격");
			
			cell = bodyRow.createCell(3);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("수량");
			
			cell = bodyRow.createCell(4);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("단가");
			
			cell = bodyRow.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("공급가액");
			
			cell = bodyRow.createCell(6);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("세액");
			
			cell = bodyRow.createCell(7);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("비고");
			
			for(int i=0; i<detailList.size(); i++) {
				
				BilltaxDetailVO_KJH dvo = detailList.get(i);
				
				bodyRow = sheet.createRow(++rowLocation);
				
				cell = bodyRow.createCell(0);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSelldate().substring(0,10));
				
				cell = bodyRow.createCell(1);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSellprod());
				
				cell = bodyRow.createCell(2);
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
				
				cell = bodyRow.createCell(3);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSellamount());
				
				cell = bodyRow.createCell(4);
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(Long.parseLong(dvo.getSelloneprice()));
				
				cell = bodyRow.createCell(5);
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(Long.parseLong(dvo.getSelltotalprice()));
				
				cell = bodyRow.createCell(6);
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(Long.parseLong(dvo.getSelltax()));
				
				cell = bodyRow.createCell(7);
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
				
			}
				
			
		}
			
		return workbook;
	
	}
	
	public SXSSFWorkbook excelOfBillnotax(String[] seqArr) {
		
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		String sheetName = "";
		
		int cnt = 1;
		
		Map<String, String> paraMap = new HashMap<> ();
		
		for(String seq : seqArr) {
			
			paraMap.put("seq", seq);
			
			BillnotaxVO_KJH doc = service.getBillnotaxDoc(paraMap);
			
			List<BillnotaxDetailVO_KJH> detailList = service.getDetailBillnotaxList(paraMap);
			
			sheetName = (!"".equals(doc.getCustomer_comp()))?"계산서_" + doc.getCustomer_comp() + cnt:"계산서_" + doc.getCustomer_name() + cnt;
			
			cnt++;
			
			SXSSFSheet sheet = workbook.createSheet(sheetName);
			
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 1000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 5000);
			sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 8000);
			
			int rowLocation = 0;
			
			// 제목부분
			Font titleFont = workbook.createFont();
			titleFont.setFontHeight((short)500);
			titleFont.setColor(IndexedColors.RED.getIndex());
			titleFont.setBold(true);
			
			Font redFont = workbook.createFont();
			redFont.setColor(IndexedColors.RED.getIndex());
			redFont.setBold(true);
			
			CellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			titleStyle.setFont(titleFont);
			titleStyle.setBorderTop(BorderStyle.THIN);
			titleStyle.setBorderBottom(BorderStyle.THIN);
			titleStyle.setBorderLeft(BorderStyle.THIN);
			titleStyle.setBorderRight(BorderStyle.THIN);
			titleStyle.setTopBorderColor(IndexedColors.RED.getIndex());
			titleStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
			titleStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
			titleStyle.setRightBorderColor(IndexedColors.RED.getIndex());
			
			CellStyle normalStyle = workbook.createCellStyle();
			normalStyle.setAlignment(HorizontalAlignment.CENTER);
			normalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			normalStyle.setFont(redFont);
			normalStyle.setBorderTop(BorderStyle.THIN);
			normalStyle.setBorderBottom(BorderStyle.THIN);
			normalStyle.setBorderLeft(BorderStyle.THIN);
			normalStyle.setBorderRight(BorderStyle.THIN);
			normalStyle.setTopBorderColor(IndexedColors.RED.getIndex());
			normalStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
			normalStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
			normalStyle.setRightBorderColor(IndexedColors.RED.getIndex());
			
			CellStyle valStyle = workbook.createCellStyle(); // 2칸 합칠 것
			valStyle.setAlignment(HorizontalAlignment.CENTER);
			valStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			valStyle.setBorderTop(BorderStyle.THIN);
			valStyle.setBorderBottom(BorderStyle.THIN);
			valStyle.setBorderLeft(BorderStyle.THIN);
			valStyle.setBorderRight(BorderStyle.THIN);
			valStyle.setTopBorderColor(IndexedColors.RED.getIndex());
			valStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
			valStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
			valStyle.setRightBorderColor(IndexedColors.RED.getIndex());
			
			CellStyle moneyStyle = workbook.createCellStyle();
	        moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
	        moneyStyle.setAlignment(HorizontalAlignment.CENTER);
	        moneyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        moneyStyle.setBorderTop(BorderStyle.THIN);
	        moneyStyle.setBorderBottom(BorderStyle.THIN);
	        moneyStyle.setBorderLeft(BorderStyle.THIN);
	        moneyStyle.setBorderRight(BorderStyle.THIN);
	        moneyStyle.setTopBorderColor(IndexedColors.RED.getIndex());
	        moneyStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
	        moneyStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
	        moneyStyle.setRightBorderColor(IndexedColors.RED.getIndex());
	        
			Cell cell;
			
			Row row1 = sheet.createRow(rowLocation);
			
			for(int i=0; i<5; i++) {
				cell = row1.createCell(i);
				
				cell.setCellStyle(titleStyle);
				cell.setCellValue("계산서");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4));
			
			cell = row1.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("일련번호");
			
			
			for(int i=6; i<8; i++) {
				cell = row1.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue("HELLOWORKS-" + doc.getBillnotax_seq());
			}
			
			///////////////////////////////////////////////////////	
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			Row row2 = sheet.createRow(++rowLocation);
			
			for(int i=0; i<5; i++) {
				cell = row2.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("공급자");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4));
			
			for(int i=5; i<8; i++) {
				cell = row2.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("공급받는자");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 5, 7));
			
			Row row3 = sheet.createRow(++rowLocation);
			
			cell = row3.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("등록번호");
			
			for(int i=1; i<5; i++) {
				cell = row3.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row3.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("등록번호");
			
			for(int i=6; i<8; i++) {
				cell = row3.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));

			Row row4 = sheet.createRow(++rowLocation);
			
			cell = row4.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("상호(업체명)");
			
			for(int i=1; i<5; i++) {
				cell = row4.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_comp());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row4.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("상호(업체명)");
			
			for(int i=6; i<8; i++) {
				cell = row4.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_comp());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			

			Row row5 = sheet.createRow(++rowLocation);
			
			cell = row5.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("성명");
			
			for(int i=1; i<5; i++) {
				cell = row5.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_name());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row5.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("성명");
			
			for(int i=6; i<8; i++) {
				cell = row5.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_name());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			Row row6 = sheet.createRow(++rowLocation);
			
			cell = row6.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("사업장주소");
			
			for(int i=1; i<5; i++) {
				cell = row6.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_addr());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row6.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("사업장주소");
			
			for(int i=6; i<8; i++) {
				cell = row6.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_addr());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			

			Row row7 = sheet.createRow(++rowLocation);
			
			cell = row7.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("업종");
			
			for(int i=1; i<5; i++) {
				cell = row7.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row7.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("업종");
			
			for(int i=6; i<8; i++) {
				cell = row7.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			///////////////////////////////////////////////////////
			
			Row row8 = sheet.createRow(++rowLocation);
			
			cell = row8.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("작성일");
			
			for(int i=1; i<5; i++) {
				cell = row8.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("공급가액");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			for(int i=5; i<8; i++) {
				cell = row8.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("비고");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 5, 7));
								
			Row row9 = sheet.createRow(++rowLocation);
			
			cell = row9.createCell(0);
			cell.setCellStyle(valStyle);
			cell.setCellValue(doc.getRegdate().substring(0,10));
			
			for(int i=1; i<5; i++) {
				cell = row9.createCell(i);
				
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(doc.getTotalprice());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			for(int i=5; i<8; i++) {
				cell = row9.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 5, 7));
								
			////////////////////////////////////////////////////////////////////////
			
			Row bodyRow = null;
			
			bodyRow = sheet.createRow(++rowLocation);
			
			cell = bodyRow.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("공급일자");
			
			cell = bodyRow.createCell(1);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("품목");
			
			cell = bodyRow.createCell(2);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("규격");
			
			cell = bodyRow.createCell(3);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("수량");
			
			cell = bodyRow.createCell(4);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("단가");
			
			cell = bodyRow.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("공급가액");
			
			cell = bodyRow.createCell(6);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("비고");
			
			cell = bodyRow.createCell(7);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("비고");
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			for(int i=0; i<detailList.size(); i++) {
				
				BillnotaxDetailVO_KJH dvo = detailList.get(i);
				
				bodyRow = sheet.createRow(++rowLocation);
				
				cell = bodyRow.createCell(0);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSelldate().substring(0,10));
				
				cell = bodyRow.createCell(1);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSellprod());
				
				cell = bodyRow.createCell(2);
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
				
				cell = bodyRow.createCell(3);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSellamount());
				
				cell = bodyRow.createCell(4);
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(Long.parseLong(dvo.getSelloneprice()));
				
				cell = bodyRow.createCell(5);
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(Long.parseLong(dvo.getSelltotalprice()));
				
				cell = bodyRow.createCell(6);
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
				
				cell = bodyRow.createCell(7);
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
				
				sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
				
			}
			
		}
		
		return workbook;
		
	}
	
	public SXSSFWorkbook excelOfTransaction(String[] seqArr) {
		
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		String sheetName = "";
		
		int cnt = 1;
		
		Map<String, String> paraMap = new HashMap<> ();
		
		for(String seq : seqArr) {
			
			paraMap.put("seq", seq);
			
			TransactionVO_KJH doc = service.getTransactionDoc(paraMap);
			
			List<TransactionDetailVO_KJH> detailList = service.getDetailTransactionList(paraMap);
			
			sheetName = (!"".equals(doc.getCustomer_comp()))?"거래명세서_" + doc.getCustomer_comp() + cnt:"거래명세서_" + doc.getCustomer_name() + cnt;
			
			cnt++;
			
			SXSSFSheet sheet = workbook.createSheet(sheetName);
			
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 1000);
			sheet.setColumnWidth(3, 3000);
			sheet.setColumnWidth(4, 4000);
			sheet.setColumnWidth(5, 5000);
			sheet.setColumnWidth(6, 5000);
			sheet.setColumnWidth(7, 8000);
			
			int rowLocation = 0;
			
			// 제목부분
			Font titleFont = workbook.createFont();
			titleFont.setFontHeight((short)500);
			titleFont.setColor(IndexedColors.BLUE.getIndex());
			titleFont.setBold(true);
			
			Font redFont = workbook.createFont();
			redFont.setColor(IndexedColors.BLUE.getIndex());
			redFont.setBold(true);
			
			CellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			titleStyle.setFont(titleFont);
			titleStyle.setBorderTop(BorderStyle.THIN);
			titleStyle.setBorderBottom(BorderStyle.THIN);
			titleStyle.setBorderLeft(BorderStyle.THIN);
			titleStyle.setBorderRight(BorderStyle.THIN);
			titleStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
			titleStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
			titleStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
			titleStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
			
			CellStyle normalStyle = workbook.createCellStyle();
			normalStyle.setAlignment(HorizontalAlignment.CENTER);
			normalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			normalStyle.setFont(redFont);
			normalStyle.setBorderTop(BorderStyle.THIN);
			normalStyle.setBorderBottom(BorderStyle.THIN);
			normalStyle.setBorderLeft(BorderStyle.THIN);
			normalStyle.setBorderRight(BorderStyle.THIN);
			normalStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
			normalStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
			normalStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
			normalStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
			
			CellStyle valStyle = workbook.createCellStyle(); // 2칸 합칠 것
			valStyle.setAlignment(HorizontalAlignment.CENTER);
			valStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			valStyle.setBorderTop(BorderStyle.THIN);
			valStyle.setBorderBottom(BorderStyle.THIN);
			valStyle.setBorderLeft(BorderStyle.THIN);
			valStyle.setBorderRight(BorderStyle.THIN);
			valStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
			valStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
			valStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
			valStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
			
			CellStyle moneyStyle = workbook.createCellStyle();
	        moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
	        moneyStyle.setAlignment(HorizontalAlignment.CENTER);
	        moneyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        moneyStyle.setBorderTop(BorderStyle.THIN);
	        moneyStyle.setBorderBottom(BorderStyle.THIN);
	        moneyStyle.setBorderLeft(BorderStyle.THIN);
	        moneyStyle.setBorderRight(BorderStyle.THIN);
	        moneyStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
	        moneyStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
	        moneyStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
	        moneyStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
	        
			Cell cell;
			
			Row row1 = sheet.createRow(rowLocation);
			
			for(int i=0; i<5; i++) {
				cell = row1.createCell(i);
				
				cell.setCellStyle(titleStyle);
				cell.setCellValue("거래명세표");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4));
			
			cell = row1.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("작성일자");
			
			
			for(int i=6; i<8; i++) {
				cell = row1.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getRegdate().substring(0,10));
			}
			
			///////////////////////////////////////////////////////	
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			Row row2 = sheet.createRow(++rowLocation);
			
			for(int i=0; i<5; i++) {
				cell = row2.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("공급자");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4));
			
			for(int i=5; i<8; i++) {
				cell = row2.createCell(i);
				
				cell.setCellStyle(normalStyle);
				cell.setCellValue("공급받는자");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 5, 7));
			
			Row row3 = sheet.createRow(++rowLocation);
			
			cell = row3.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("등록번호");
			
			for(int i=1; i<5; i++) {
				cell = row3.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row3.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("등록번호");
			
			for(int i=6; i<8; i++) {
				cell = row3.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));

			Row row4 = sheet.createRow(++rowLocation);
			
			cell = row4.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("상호(업체명)");
			
			for(int i=1; i<5; i++) {
				cell = row4.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_comp());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row4.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("상호(업체명)");
			
			for(int i=6; i<8; i++) {
				cell = row4.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_comp());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			

			Row row5 = sheet.createRow(++rowLocation);
			
			cell = row5.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("성명");
			
			for(int i=1; i<5; i++) {
				cell = row5.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_name());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row5.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("성명");
			
			for(int i=6; i<8; i++) {
				cell = row5.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_name());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			Row row6 = sheet.createRow(++rowLocation);
			
			cell = row6.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("사업장주소");
			
			for(int i=1; i<5; i++) {
				cell = row6.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_addr());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row6.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("사업장주소");
			
			for(int i=6; i<8; i++) {
				cell = row6.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_addr());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			

			Row row7 = sheet.createRow(++rowLocation);
			
			cell = row7.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("업종");
			
			for(int i=1; i<5; i++) {
				cell = row7.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getMycompany_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 1, 4));
			
			cell = row7.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("업종");
			
			for(int i=6; i<8; i++) {
				cell = row7.createCell(i);
				
				cell.setCellStyle(valStyle);
				cell.setCellValue(doc.getCustomer_id());
			}
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			///////////////////////////////////////////////////////
			
			Row bodyRow = null;
			
			bodyRow = sheet.createRow(++rowLocation);
			
			cell = bodyRow.createCell(0);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("공급일자");
			
			cell = bodyRow.createCell(1);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("품목");
			
			cell = bodyRow.createCell(2);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("규격");
			
			cell = bodyRow.createCell(3);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("수량");
			
			cell = bodyRow.createCell(4);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("단가");
			
			cell = bodyRow.createCell(5);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("금액");
			
			cell = bodyRow.createCell(6);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("비고");
			
			cell = bodyRow.createCell(7);
			cell.setCellStyle(normalStyle);
			cell.setCellValue("비고");
			
			sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
			
			for(int i=0; i<detailList.size(); i++) {
				
				TransactionDetailVO_KJH dvo = detailList.get(i);
				
				bodyRow = sheet.createRow(++rowLocation);
				
				cell = bodyRow.createCell(0);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSelldate().substring(0,10));
				
				cell = bodyRow.createCell(1);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSellprod());
				
				cell = bodyRow.createCell(2);
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
				
				cell = bodyRow.createCell(3);
				cell.setCellStyle(valStyle);
				cell.setCellValue(dvo.getSellamount());
				
				cell = bodyRow.createCell(4);
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(Long.parseLong(dvo.getSelloneprice()));
				
				cell = bodyRow.createCell(5);
				cell.setCellStyle(moneyStyle);
				cell.setCellValue(Long.parseLong(dvo.getSelltotalprice()));
				
				cell = bodyRow.createCell(6);
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
				
				cell = bodyRow.createCell(7);
				cell.setCellStyle(valStyle);
				cell.setCellValue("");
				
				sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 6, 7));
				
			}
			
		}
		
		return workbook;
		
	}
	
}
