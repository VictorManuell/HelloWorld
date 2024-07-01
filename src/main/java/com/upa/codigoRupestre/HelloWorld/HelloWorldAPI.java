package com.upa.codigoRupestre.HelloWorld;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldAPI {
	
	@Autowired
	private HelloWorldRepository repository;
	
	@GetMapping("/hello/{name}/{age}")
	public String hello(@PathVariable String name, @PathVariable Integer age) {
		String welcomeMsg= "welcome " + name + " Age " + age + LocalDateTime.now();
		
		HelloEntity helloworld = new HelloEntity();
		helloworld.setMsg(welcomeMsg);
		repository.save(helloworld);
		
		return welcomeMsg;
		
	}

}
