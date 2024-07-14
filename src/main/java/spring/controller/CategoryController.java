package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.repository.CategoryRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
}
