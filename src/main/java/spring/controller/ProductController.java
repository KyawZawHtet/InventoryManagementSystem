package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.repository.ProductRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
}
