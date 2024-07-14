package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.CategoryDto;
import spring.dto.ProductDto;
import spring.dto.RoleDto;
import spring.model.Category;
import spring.model.Product;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/add_product", method = RequestMethod.GET)
	public ModelAndView showRegister(Model model) {
		Product product = new Product();
		return new ModelAndView("product_add", "product", product);
	}

	@RequestMapping(value = "/do_product", method = RequestMethod.POST)
	public String showResult(@ModelAttribute("product") Product product, Model model) {

		ProductDto productDto = new ProductDto();
		productDto.setName(product.getName());
		productDto.setCategoryId(product.getCategoryId());

		int i = productRepository.insertProduct(productDto);
		if (i > 0) {
			return "redirect:/product/showproducts";
		} else {
			model.addAttribute("product", new Product());
			return "product_add";
		}
	}

	@GetMapping(value = "/showproducts")
	public String showAllProducts(Model model) {
		List<ProductDto> productDtos = productRepository.showAllProducts();
		model.addAttribute("productDtoList", productDtos);
		return "product_list";
	}

	@GetMapping(value = "/editproduct/{id}")
	public String showProductById(@PathVariable("id") Long id, Model model) {
		ProductDto productDto = new ProductDto();
		productDto.setId(id);

		ProductDto dto = productRepository.showProduct(productDto);

		Product product = modelMapper.map(dto, Product.class);

		model.addAttribute("product", product);
		return "product_update";
	}


	@PostMapping(value = "/doupdate")
	public String updateProduct(@ModelAttribute("product") Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);

		int i = productRepository.updateProduct(productDto);
		if (i > 0) {
			return "redirect:/product/showproducts";
		} else {
			return "redirect:editproduct/" + productDto.getId();
		}
	}

	@RequestMapping(value = "/deleteproduct/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") Long id, Model model) {
		return null;
	}

	@ModelAttribute("categories")
	public List<CategoryDto> getCategories() {
		List<CategoryDto> categoryDtos = categoryRepository.showAllCategories().stream()
				.map(category -> new CategoryDto(category.getId(), category.getName())).collect(Collectors.toList());
		return categoryDtos;
	}
}
