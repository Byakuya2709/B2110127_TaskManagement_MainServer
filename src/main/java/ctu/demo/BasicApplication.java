package ctu.demo;

import ctu.demo.model.Product;
import ctu.demo.repository.ProductRepository;
import ctu.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasicApplication {
       
 
	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}
     
   
}
