package com.example.pdfimagesservice.rest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.pdfimagesservice.model.PDFFile;
import com.example.pdfimagesservice.service.PDFImageService;

@Controller
@RestController
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class PDFImageController {

	@Autowired
	private final PDFImageService pdfimageService;

	public PDFImageController(PDFImageService pdfimageService) {
		this.pdfimageService = pdfimageService;
	}
	
	
	@PostMapping("/api-pdf/images")
	public ResponseEntity<PDFFile> getImagesPDF(@NotNull @RequestParam("file") MultipartFile multipartFile){
		
		System.out.println("getContentType->"+multipartFile.getContentType());
		System.out.println("getName->"+multipartFile.getName());
		System.out.println("getOriginalFilename->"+multipartFile.getOriginalFilename());
		System.out.println("getSize->"+multipartFile.getSize());
		
		try {
			System.out.println("getContentType Size ->"+multipartFile.getBytes().length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("getImagesPDF IN");
		
		Optional optPDF = pdfimageService.getContentFromPDF(multipartFile);
		
		if(optPDF.isPresent()) {
			PDFFile resultService = (PDFFile) optPDF.get();
		
			System.out.println("getImagesPDF MIDDLE");
			
			System.out.println("getImagesPDF END service content ->" + resultService);
			return ResponseEntity.ok(resultService);
		}
		
		System.out.println("getImagesPDF SERVICE List EMPTY");
		return ResponseEntity.notFound().build();
	}
	
	
	
}
