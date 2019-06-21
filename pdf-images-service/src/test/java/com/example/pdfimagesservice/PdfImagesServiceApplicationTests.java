package com.example.pdfimagesservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.pdfimagesservice.service.PDFImageService;

@RunWith(SpringRunner.class)
@WebMvcTest
public abstract class PdfImagesServiceApplicationTests {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext wac;
	
	@MockBean
	protected PDFImageService pdfImageService;

	@Before
	public void setUp() {
		Mockito.reset(pdfImageService);
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
}
