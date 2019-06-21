package com.example.pdfimagesservice.rest;

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
	
	
	@PostMapping("/api-pdf")
	public ResponseEntity<String> getContentPDF(@NotNull @RequestParam("file") MultipartFile multipartFile){
		
		Optional<String> resultService = pdfimageService.getContentPDF(multipartFile);
		
		if(!resultService.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(resultService.get());
	}
	
	
	@PostMapping("/api-pdf/images")
	public ResponseEntity<List<PDFFile>> getImagesPDF(@NotNull @RequestParam("file") MultipartFile multipartFile){
		
		System.out.println("getImagesPDF IN");
		List<PDFFile> resultService = pdfimageService.getImageFromPDF(multipartFile);
		
		System.out.println("getImagesPDF MIDDLE");
		
		if(resultService.isEmpty()) {
			System.out.println("getImagesPDF EMPTY");
			return ResponseEntity.notFound().build();
		}
		
		System.out.println("getImagesPDF END");
		return ResponseEntity.ok(resultService);
		
	}
	
	
	
}
