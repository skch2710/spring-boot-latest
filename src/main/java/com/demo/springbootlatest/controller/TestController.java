package com.demo.springbootlatest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

	@Autowired
	private CompletableFutureEx futureEx;

	@GetMapping("/async-test")
	public ResponseEntity<?> getHi() {

		Long intialTime = System.currentTimeMillis();
		
		Map<Long, String> testReturn = futureEx.testReturnExeguter("Test");
		
		Long finalTime = System.currentTimeMillis();
		System.out.println("Total Time : "+(finalTime - intialTime));

		return ResponseEntity.ok(testReturn);
	}

}
