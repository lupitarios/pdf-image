package com.example.pdfimagesservice.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.pdfimagesservice.model.PDFFile;
import com.example.pdfimagesservice.service.PDFImageService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PDFImageControllerTest  {

	private final String FILE_NAME = "Agile-Manifesto.pdf";
	private final String FILE_PATH = "/Users/LupitaRios/Documents/DOCS/";
	private File filePdf = new File(FILE_PATH + FILE_NAME );
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext wac;

	@MockBean
	private PDFImageService pdfImageService;

	@Before
	public void setUp() {
		//Mockito.reset(pdfImageServiceMock);
		pdfImageService = new PDFImageService();
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
	}

	
	@Test
	public void shouldReturnImages() throws Exception{
		
		byte[] pArray = Files.readAllBytes(Paths.get(filePdf.getPath()));
		
		MockMultipartFile multipartFile = new MockMultipartFile("file", filePdf.getName(), MediaType.APPLICATION_PDF.toString() , pArray); 
		Optional optPDF = pdfImageService.getContentFromPDF(multipartFile);
		if(optPDF.isPresent()) {
			PDFFile pdfContent = (PDFFile) optPDF.get();
			System.out.println("PDF CONTENT  " + pdfContent);
		}
		
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
