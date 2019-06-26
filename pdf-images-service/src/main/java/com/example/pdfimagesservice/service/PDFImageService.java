package com.example.pdfimagesservice.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;


import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.pdfimagesservice.model.PDFFile;

@Service
public class PDFImageService {

	/**
	 * Return a list of all images within the PDF file.
	 * 
	 * @param pdf file
	 * @return list of image from the PDF file
	 * 
	 */
	public Optional getContentFromPDF(MultipartFile pdf) {
				
		PDFFile pdfFile = new PDFFile();
		System.out.println("SERVICE getImageFromPDF");
		
		try {
			
			InputStream pdfStream = new ByteArrayInputStream(pdf.getBytes());
			PDDocument docPDF = PDDocument.load(pdfStream);
			List<PDImageXObject> lstImagesPDF = this.getImages(docPDF);
			
			
			pdfFile.setPagesNumber(docPDF.getNumberOfPages());
			pdfFile.setData(pdf.getBytes());
			pdfFile.setName(pdf.getName());
			pdfFile.setWordsNumber(this.countWords(docPDF));
			pdfFile.setImagesNumber(lstImagesPDF.size());
			pdfFile.setImages(lstImagesPDF);
			
			System.out.println("PagesNumber -> "+ pdfFile.getPagesNumber());
			System.out.println("WordsNumber -> "+ pdfFile.getWordsNumber());
			System.out.println("ImageNumber -> "+ pdfFile.getImagesNumber());
			
			docPDF.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.format("ERROR", e);
		}
		
		return Optional.of(pdfFile);
	}

	
	private int countWords(PDDocument docPDF) throws IOException {
		int wordsNumber = 0;
		PDFTextStripper stripper = new PDFTextStripper();
		
		StringTokenizer st = new StringTokenizer(stripper.getText(docPDF));
		wordsNumber = st.countTokens();
		
		return wordsNumber;
	}
	
	private List<PDImageXObject> getImages(PDDocument docPDF){
		List<PDImageXObject> lstImages = new ArrayList<PDImageXObject>();
		
		PDPageTree list = docPDF.getPages();
		try {
			
			for(PDPage page : list) {
				PDResources pdResources = page.getResources();
				
				for(COSName name: pdResources.getXObjectNames()) {
					
					PDXObject xObj = pdResources.getXObject(name);
					if(xObj instanceof PDImageXObject) {
						PDImageXObject image = (PDImageXObject) xObj;
						lstImages.add(image);
						
					}
				}
			}
		} catch (IOException e) {
		
			System.err.println("Exception while trying to create pdf document" + e);
		}
		return lstImages;
	}
	
}
