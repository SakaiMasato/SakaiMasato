package com.bxw.gradleDemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * helloworld
 * @author bxw
 *
 */
@Controller
public class HelloWorld {

	@RequestMapping("/hello")
	public String hello(){
		int i=1/0;
		return "/index";
	}
}
