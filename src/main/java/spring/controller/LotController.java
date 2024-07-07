package spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import spring.dto.LotDto;
import spring.dto.ProductDto;
import spring.repository.LotRepository;
import spring.repository.ProductRepository;

@Controller
@RequestMapping(value="/lot")
public class LotController {

	
	@Autowired
	private ModelMapper modelMapper;
	
	 @Autowired
	  private LotRepository lotRepository;

	    @Autowired
	  private ProductRepository productRepository;

	    
}
