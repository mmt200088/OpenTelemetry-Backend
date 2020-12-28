package com.tongji.javaEE;

import com.tongji.javaEE.Service.Metrics.ValueObserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Raven
 */
@SpringBootApplication
@RestController
public class JavaEeApplication {

	public JavaEeApplication() throws IOException {
	}

	public static void main(String[] args) {
		SpringApplication.run(JavaEeApplication.class, args);
	}

	/**
	 * AIOps
	 */
	ValueObserver valueObserver = new ValueObserver(19090);
	/**
	 * AIOps
	 */

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {

		/**
		 * AIOps
		 */
		long value = name.length();
		valueObserver.incomingMessageCount = value;
		/**
		 * AIOps
		 */

		return String.format("Hello %s!", name);
	}

	@GetMapping("/test")
	public String test(@RequestParam(value = "value") String val ){
		return val;
	}
}
