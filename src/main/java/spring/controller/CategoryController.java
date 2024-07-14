package spring.controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.dto.CategoryDto;
import spring.model.Category;
import spring.repository.CategoryRepository;

/*
 *  category/lists		->		show categories  
 *  category/add		->		create category
 *  category/update/id		->		update category
 * 
 * */

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value="/lists")
	public String getCategories(ModelMap model)
	{
		List<CategoryDto> dbRs = categoryRepository.getAll();
		model.addAttribute("categories", dbRs);
		return "category_list";
	}
	
	@GetMapping(value="/add")
	public ModelAndView showAddForm()
	{
		return new ModelAndView("category_add", "category", new Category());
	}
	
	@PostMapping(value="/doadd")
	public String addCategory(@ModelAttribute("category") @Valid Category category, BindingResult br, RedirectAttributes model)
	{
		if(br.hasErrors())
		{
			return "category_add";
		}
		CategoryDto dto=modelMapper.map(category, CategoryDto.class);
		List<CategoryDto> dbRs = categoryRepository.getAll();
		for(CategoryDto cat: dbRs)
		{
			if(cat.getName().equalsIgnoreCase(dto.getName()))
			{
				model.addFlashAttribute("msg", "Name already registered.Use a different one!");
				return "redirect:/category/add";
			}
		}
		categoryRepository.insertOne(dto);
		return "redirect:/category/lists";
	}
	
	@GetMapping(value="/update/{id}")
	public String showCategory(@PathVariable("id")Integer id, ModelMap model)
	{
		CategoryDto dbRs = categoryRepository.getOne(id);
		Category bean = modelMapper.map(dbRs, Category.class);
		model.addAttribute("category", bean);
		return "category_update";
	}
	
	@PostMapping(value="/update/doupdate")
	public String updateCategory(@ModelAttribute("category")@Valid Category category, BindingResult br, RedirectAttributes model)
	{
		if(br.hasErrors())
		{
			return "category_update";
		}
		
		CategoryDto dto = modelMapper.map(category, CategoryDto.class);
		List<CategoryDto> dbRs = categoryRepository.getExceptId(dto.getId());
		for(CategoryDto cat: dbRs)
		{
			if(cat.getName().equalsIgnoreCase(dto.getName()))
			{
				model.addFlashAttribute("msg", "Name already registered.Use a different one!");
				return "redirect:./"+dto.getId();
			}
		}
		categoryRepository.updateOne(dto);
		return "redirect:/category/lists";
	}
}
