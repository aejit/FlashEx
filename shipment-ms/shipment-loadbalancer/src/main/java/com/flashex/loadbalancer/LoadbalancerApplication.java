package com.flashex.loadbalancer;

import com.flashex.loadbalancer.config.DemoApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.flashex.loadbalancer")
@RestController
@RibbonClient(name = "microservice-webservice", configuration = DemoApplicationConfiguration.class)
public class LoadbalancerApplication{

	@LoadBalanced
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

//    @RequestMapping("/hi")
//    public String hi(@RequestParam(value="name", defaultValue="Artaban") String name) {
//        String greeting = this.restTemplate.getForObject("http://localhost:8090/greeting", String.class);
//        return String.format("%s, %s!", greeting, name);
//    }


	@GetMapping("/hi")
	public String hi(){
		String greeting = this.restTemplate.getForObject("http://microservice-webservice/", String.class);
		System.out.println("greeting is " + greeting);
		return String.format("%s!", greeting);
	}

	@GetMapping("/hola")
	public String hola(){
		String greeting = this.restTemplate.getForObject("http://microservice-webservice/home", String.class);
		System.out.println("greeting is " + greeting);
		return String.format("%s!", greeting);
	}


    public static void main(String[] args) {
        SpringApplication.run(LoadbalancerApplication.class, args);
    }
}