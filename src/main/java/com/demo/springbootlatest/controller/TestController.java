package com.demo.springbootlatest.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/test")
@Slf4j
public class TestController {

	@Autowired
	private CompletableFutureEx futureEx;

	@GetMapping("/async-test")
	public ResponseEntity<?> getHi() {

		Long intialTime = System.currentTimeMillis();

		Map<Long, String> testReturn = futureEx.testReturnExeguter("Test");

		Long finalTime = System.currentTimeMillis();
		System.out.println("Total Time : " + (finalTime - intialTime));

		return ResponseEntity.ok(testReturn);
	}

	@PostMapping("/generate-pdf")
	public ResponseEntity<?> generatePdf(@RequestBody ReqSearch search) throws Exception {
		try {

			ByteArrayOutputStream outputStream = getPdf();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", "Sample.pdf");

			InputStreamResource inputStreamResource = new InputStreamResource(
					new ByteArrayInputStream(outputStream.toByteArray()));

			outputStream.flush();// Flush the output stream

			return ResponseEntity.ok().headers(headers).body(inputStreamResource);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public ByteArrayOutputStream getPdf() throws Exception {
		ByteArrayOutputStream baos;
		try {
			Rectangle pagesize = new Rectangle(1754, 1240);
			// Create a new document
//			Document document = new Document(PageSize.A4, 30, 30, 45, 30);
			Document document = new Document(pagesize, 30, 30, 45, 30);

			baos = new ByteArrayOutputStream();

			// Create a new PDF writer
			PdfWriter.getInstance(document, baos);

			// Open the document
			document.open();

			PdfPTable totalCard = PdfHelper.createNoBorderTable(3,11f,11f,100);
			totalCard.setTotalWidth(new float[] {49.8f,0.4f,49.8f});
			PdfPTable frontCard = frontCard();
			PdfPTable middleSpacee = PdfHelper.createTable(1,11f,11f,100);
			PdfPTable backCard = backCard();
			
			totalCard.addCell(frontCard);
			totalCard.addCell(middleSpacee);
			totalCard.addCell(backCard);
			
			document.add(totalCard);
			
			// Close the document
			document.close();
		} catch (Exception e) {
			log.error("error in generate Pdf ", e);
			throw new Exception(e);
		}
		return baos;
	}
	
	public PdfPTable frontCard() throws Exception {
		String bgmPath = "src/main/resources/images/Card Front.png";
		String logoPath = "src/main/resources/images/test-logo2.jpg";
		
		PdfPTable frontCard = PdfHelper.createTable(1,11f,11f,100);
		PdfPCell cell = new PdfPCell();
		
		PdfPTable innerTable = PdfHelper.createTable(1,15f,15f,100);
		PdfHelper.createLogo(innerTable,logoPath,20,10,100,Element.ALIGN_LEFT);
		PdfHelper.noBorderCell(innerTable,"Sathish Kumar",25,null,10,Element.ALIGN_LEFT);
		
		cell.addElement(innerTable);
		
		PdfHelper.imageBgm(bgmPath, frontCard,cell, 500);
		
		return frontCard;
	}
	
	public PdfPTable backCard() throws Exception {
		String bgmPath = "src/main/resources/images/Card Back.png";
		String logoPath = "src/main/resources/images/test-logo2.jpg";
		
		PdfPTable backCard = PdfHelper.createTable(1,11f,11f,100);
		PdfPCell cell = new PdfPCell();
		
		PdfPTable innerTable = PdfHelper.createTable(1,15f,15f,100);
		PdfHelper.noBorderCell(innerTable,"Sathish Kumar",25,null,10,Element.ALIGN_LEFT);
		
		PdfHelper.createLogo(innerTable,logoPath,0,10,60,Element.ALIGN_RIGHT);
		cell.addElement(innerTable);
		
		PdfHelper.imageBgm(bgmPath, backCard,cell, 500);
		return backCard;
	}
}
