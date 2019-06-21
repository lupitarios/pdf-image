package com.example.pdfimagesservice.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.pdfimagesservice.PdfImagesServiceApplicationTests;

public class PDFImageControllerTest extends PdfImagesServiceApplicationTests {

	private final String FILE_NAME = "Reason–May 2019.pdf";
	private final String FILE_PATH = "/Users/LupitaRios/Documents/DOCS/";
	private File filePdf = new File(FILE_PATH + FILE_NAME );
	
	@Test
	public void shouldReturnContent() throws Exception{
				
		MockMultipartFile multipartFile = new MockMultipartFile("file", filePdf.getName(), MediaType.APPLICATION_PDF.toString() ,Files.readAllBytes(Paths.get(filePdf.getPath())) ); 

		System.out.println(multipartFile.getName());
		
		/*
		mockMvc.perform(get("/api-pdf")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.APPLICATION_PDF)
				.requestAttr("file", multipartFile)
				.param("file", FILE_NAME))
				.andExpect(status().isOk());
		*/
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.multipart("/api-pdf")
				.file(multipartFile))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		System.out.println(" shouldReturnContent RESPONSE MVC -> " + content);
	}
	
	@Test
	public void shouldReturnImages() throws Exception{
		
		MockMultipartFile multipartFile = new MockMultipartFile("file", filePdf.getName(), MediaType.APPLICATION_PDF.toString() ,Files.readAllBytes(Paths.get(filePdf.getPath())) ); 
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.multipart("/api-pdf/images")
				.file(multipartFile)
				.contentType(MediaType.APPLICATION_PDF))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println(" shouldReturnImages RESPONSE MVC -> " + content);
		System.out.println("getErrorMessage -> " +result.getResponse().getErrorMessage());
				
	}

	
}
