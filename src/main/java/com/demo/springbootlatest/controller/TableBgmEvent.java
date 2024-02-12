package com.demo.springbootlatest.controller;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;

public class TableBgmEvent implements PdfPTableEvent {

	    private Image backgroundImage;
	    
	    private Rectangle rectangle;

	    public TableBgmEvent(Image image,Rectangle rectangle) {
	        this.backgroundImage = image;
	        this.rectangle = rectangle;
	    }

	    public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases) {
	        try {
	            PdfContentByte canvas = canvases[PdfPTable.BASECANVAS];
	            backgroundImage.scaleAbsolute(table.getTotalWidth(), table.getTotalHeight()); // Adjust image size to cell
	            backgroundImage.setAbsolutePosition(rectangle.getLeft(),rectangle.getBottom()); // Position image at bottom left corner of cell
	            canvas.addImage(backgroundImage);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
