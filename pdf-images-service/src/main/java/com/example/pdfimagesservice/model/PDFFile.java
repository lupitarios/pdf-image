package com.example.pdfimagesservice.model;

import java.util.List;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFFile {

	private String name;
	private String contentType;
	private byte[] data;
	private int pagesNumber;
	private int imagesNumber;
	private int wordsNumber;
	private List<PDImageXObject> images;
	
	
	public PDFFile() {
		super();
	}

	public PDFFile(String name, String contentType, byte[] data, int pagesNumber, int imagesNumber, int wordsNumber,
			List<PDImageXObject> images) {
		super();
		this.name = name;
		this.contentType = contentType;
		this.data = data;
		this.pagesNumber = pagesNumber;
		this.imagesNumber = imagesNumber;
		this.wordsNumber = wordsNumber;
		this.images = images;
	}





	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

	public int getPagesNumber() {
		return pagesNumber;
	}

	public void setPagesNumber(int pagesNumber) {
		this.pagesNumber = pagesNumber;
	}

	public int getImagesNumber() {
		return imagesNumber;
	}

	public void setImagesNumber(int imagesNumber) {
		this.imagesNumber = imagesNumber;
	}

	public int getWordsNumber() {
		return wordsNumber;
	}

	public void setWordsNumber(int wordsNumber) {
		this.wordsNumber = wordsNumber;
	}

	public List<PDImageXObject> getImages() {
		return images;
	}

	public void setImages(List<PDImageXObject> images) {
		this.images = images;
	}
	
	
}
