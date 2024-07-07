package spring.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.dto.CategoryDto;
import spring.dto.ProductDto;
import spring.model.Product;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;

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
	
	@GetMapping(value="/register")
  public ModelAndView showRegisterForm() {
      ModelAndView mav = new ModelAndView("productRegister");
      mav.addObject("product", new Product());
      List<CategoryDto> categories = categoryRepository.getAllCategories();		
      mav.addObject("categories", categories);
      return mav;
  }

  @PostMapping(value="/doregister")
  public String registerProduct(@ModelAttribute("product") @Valid Product productDTO, BindingResult br, Model model) {
      if (br.hasErrors()) {
          model.addAttribute("categories", categoryRepository.getAllCategories());
          return "productRegister";
      }
      ProductDto dto = modelMapper.map(productDTO, ProductDto.class);
      if (dto.getName() == null || dto.getName() .trim().isEmpty()) {
          model.addAttribute("error", "Invalid!");
          return "productRegister";
      }

      int result = productRepository.insertProduct(dto);

      if (result > 0) {
          return "redirect:/product/showproducts";
      } else {
          model.addAttribute("error", "Failed to register warehouse. Please try again.");
          return "productRegister";
      }
  }

  @GetMapping(value="/showproducts")
  public String showAllProducts(Model model) {
      List<ProductDto> productList = productRepository.getAllProducts();
      model.addAttribute("productList", productList);
      return "productList";
  }

  @GetMapping(value="/editproduct/{id}")
  public String showProductById(@PathVariable("id") int id, Model model) {
      ProductDto dto = productRepository.getProductById(id);
      if (dto != null) {
          Product product = modelMapper.map(dto, Product.class);
          model.addAttribute("product", product);
          model.addAttribute("categories", categoryRepository.getAllCategories());
          return "productEdit";
      } else {
          return "redirect:/product/showproducts";
      }
  }

  @PostMapping(value="/doupdate")
  public String updateProduct(@ModelAttribute("product") @Valid Product productDTO, BindingResult br, Model model) {
      if (br.hasErrors()) {
          model.addAttribute("categories", categoryRepository.getAllCategories());
          return "productEdit";
      }

      ProductDto dto = modelMapper.map(productDTO, ProductDto.class);
      int result = productRepository.updateProduct(dto);

      if (result > 0) {
          return "redirect:/product/showproducts";
      } else {
          model.addAttribute("error", "Failed to update warehouse. Please try again.");
          return "productEdit";
      }
  }
  
  @PostMapping("/search")
  public String searchProducts(@RequestParam(name = "name", required = false) String productName, Model model) {
      List<ProductDto> productList;

      if ( productName != null && !productName.isEmpty()) {
          productList = productRepository.searchProductsName(productName);	  
      } else {
          productList = productRepository.getAllProducts();
      }

      model.addAttribute("productList", productList);
      model.addAttribute("searchTermName", productName);
      return "productList"; 
  }
  
  @GetMapping(value="/deleteproduct/{id}")
  public String deleteproduct(@PathVariable("id") int id) {
      int result = productRepository.softDeleteProduct(id);
      if(result>0) {
      	return "redirect:/product/showproducts";
      }else {
      	return "error";
      }
      
  }
}
