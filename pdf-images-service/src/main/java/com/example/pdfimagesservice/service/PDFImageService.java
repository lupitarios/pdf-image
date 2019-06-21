package com.example.pdfimagesservice.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
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
	public List<PDFFile> getImageFromPDF(MultipartFile pdf) {
		
		System.out.println("SERVICE getImageFromPDF");
		List<PDFFile> lstImages = new ArrayList<PDFFile>();
		try {
			
			InputStream pdfStream = new ByteArrayInputStream(pdf.getBytes());
			PDDocument docPDF = PDDocument.load(pdfStream);
			
			PDFFile pdfFile = new PDFFile();
			pdfFile.setPagesNumber(docPDF.getNumberOfPages());
			pdfFile.setData(pdf.getBytes());
			pdfFile.setName(pdf.getName());
			pdfFile.setWordsNumber(this.countWords(docPDF));
			pdfFile.setImagesNumber(0);
			
			System.out.println("PagesNumber -> "+ pdfFile.getPagesNumber());
			System.out.println("WordsNumber -> "+ pdfFile.getWordsNumber());
			
			lstImages.add(pdfFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.format("ERROR", e);
		}
		
		return lstImages;
	}
	
	/**
	 * Return the content of the PDF File.
	 * 
	 * @param pdf file
	 * @return content the PDF file
	 * 
	 */
	public Optional<String> getContentPDF(MultipartFile pdf) {
		
		try {
			InputStream pdfStream = new ByteArrayInputStream(pdf.getBytes());
			PDDocument docPDF = PDDocument.load(pdfStream); 
			PDFRenderer renderer = new PDFRenderer(docPDF); 
			List<BufferedImage> lstImages = new ArrayList<BufferedImage>();
			
			/*
			docPDF.getPages().forEach(page -> {
				BufferedImage imgPDF = renderer.renderImage(0);
			});
			*/
					
			System.out.println("Content " + pdf.getBytes().toString());
			
			PDFFile pdfFile = new PDFFile();
			pdfFile.setPagesNumber(docPDF.getNumberOfPages());
			pdfFile.setData(pdf.getBytes());
			pdfFile.setName(pdf.getName());
			pdfFile.setWordsNumber(0);
			pdfFile.setImagesNumber(0);
			
			return Optional.of(pdf.getBytes().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private int countWords(PDDocument docPDF) throws IOException {
		int wordsNumber = 0;
		PDFTextStripper stripper = new PDFTextStripper();
		
		StringTokenizer st = new StringTokenizer(stripper.getText(docPDF));
		wordsNumber = st.countTokens();
		
		return wordsNumber;
	}
	
	private List<String> getImages(){
		
		return null;
	}
	
}
