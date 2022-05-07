package com.sts.controller;


import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import com.google.zxing.WriterException;
import com.sts.service.QRCodeGenerator;

@org.springframework.stereotype.Controller
public class Controller {
	private Map<Integer, String> cache=new HashMap<Integer, String>();
	@Autowired
	QRCodeGenerator qrCodeGenerator;
	
	@GetMapping()
	public String home(){
		return "home";
	}
	
	@PostMapping("/shortUrl")
	public ResponseEntity<String> shortUrl(@RequestBody Map<String, String> request){
		int hash=Math.abs(request.get("url").hashCode())/10_000;
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		//System.out.println(baseUrl);
		if(cache.containsKey(hash)) {
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(baseUrl+"/shortend/"+hash);
		}
		cache.put(hash, request.get("url"));
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(baseUrl+"/shortend/"+hash);
	}
	
	@PostMapping("/QRUrl")
	public ResponseEntity<byte[]> QRUrl(@RequestBody Map<String, String> request) throws WriterException, IOException{
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(qrCodeGenerator.getQRImage(request.get("url"), 350, 350));
	}
	
	@GetMapping("/shortend/{hash}")
	public ResponseEntity<Void> visitShortend(@PathVariable int hash) {
		
		//RedirectView redirectView=new RedirectView(cache.get(hash));
		//System.out.println(cache.get(hash));
		//return redirectView;
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(cache.get(hash))).build();
	}
}
