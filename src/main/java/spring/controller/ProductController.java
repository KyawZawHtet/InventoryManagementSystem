package spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.dto.CategoryDto;
import spring.dto.ProductDto;
import spring.model.Product;
import spring.model.Product;
import spring.repository.CategoryRepository;
import spring.repository.ProductRepository;

/*
 *  product/lists		->		show products 
 *  product/add			->		create product
 *  product/update/id	->		update product
 * 
 * */



@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value="/lists")
	public String showProducts(ModelMap model)
	{
		List<ProductDto> dbRs = productRepository.getAll();
		model.addAttribute("products", dbRs);
		System.out.println(dbRs);
		return "product_list";
	}
	
	
	@GetMapping(value="/add")
	public String showAdd(ModelMap model)
	{
		Product bean = new Product();
		bean.setCategory(getCategories().get(0));
		bean.setUom(getUoms().get(0));
		model.addAttribute("product", bean);
		return "product_add";
	}
	
	@PostMapping(value="/doadd")
	public String doAdd(@ModelAttribute("product")@Valid Product product, BindingResult br, RedirectAttributes model)
	{
		if(br.hasErrors())
		{
			return "product_add";
		}
		ProductDto dto = modelMapper.map(product, ProductDto.class);
		List<ProductDto> dbRs = productRepository.getAll();
		for(ProductDto pd: dbRs)
		{
			if(pd.getCode().equalsIgnoreCase(dto.getCode()) || pd.getName().equalsIgnoreCase(dto.getName()))
			{
				model.addFlashAttribute("msg", "Product has already registered!Check and Try Again!");
				return "redirect:/product/add";
			}
		}
		productRepository.insertOne(dto);
		return "redirect:./lists";		
	}
	
	
	@GetMapping(value="/update/{id}")
	public String getProduct(@PathVariable("id")String id, ModelMap model)
	{
		ProductDto dbRs = productRepository.getOne(id);
		Product bean = modelMapper.map(dbRs, Product.class);
		model.addAttribute("product", bean);
		return "product_update";
	}
	
	@PostMapping(value="/update/doupdate")
	public String updateProduct(@ModelAttribute("product")@Valid Product product, BindingResult br, RedirectAttributes model)
	{
		if(br.hasErrors())
		{
			return "product_update";
		}
		ProductDto dto=modelMapper.map(product, ProductDto.class);
		List<ProductDto> dbRs = productRepository.getExceptId(dto.getId());
		for(ProductDto pd: dbRs)
		{
			if(pd.getCode().equalsIgnoreCase(dto.getCode()) || pd.getName().equalsIgnoreCase(dto.getName()))
			{
				model.addFlashAttribute("msg", "Data has already registered.Please use different one!");
				return "redirect:./"+dto.getId();
			}
		}
		productRepository.updateOne(dto);
		return "redirect:../lists";
	}
	
	
	
	
	
	
	
	
	
	
	@ModelAttribute("categories")
	public Map<String, String> getCategories()
	{
		List<CategoryDto> dbRs = categoryRepository.getAll();
		HashMap<String, String> categories=new HashMap<>();
		for(CategoryDto dto: dbRs)
		{
			categories.put(dto.getId(), dto.getName());
		}
		return categories;
	}
	
	@ModelAttribute("uom")
	public List<String> getUoms()
	{
		List<String> uoms = new ArrayList<>();
		uoms.add("KG");
		uoms.add("Bag");
		uoms.add("EA");
		uoms.add("Box");
		uoms.add("Bottle");
		return uoms;
	}
	
	
	@ModelAttribute("total")
	public String totalQty()
	{
		return "no";
	}
}
