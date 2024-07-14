package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.CategoryDto;
import spring.model.Category;
import spring.repository.CategoryRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/add_category", method = RequestMethod.GET)
	public ModelAndView showRegister(Model model) {
		Category category = new Category();
		return new ModelAndView("category_add", "category", category);
	}

	@RequestMapping(value = "/do_category", method = RequestMethod.POST)
	public String showResult(@ModelAttribute("category") Category category, Model model) {

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(category.getName());
		categoryDto.setDescription(category.getDescription());

		int i = categoryRepository.insertCategory(categoryDto);
		if (i > 0) {
			return "redirect:/category/showcategories";
		} else {
			model.addAttribute("category", new Category());
			return "add_category";
		}
	}

	@GetMapping(value = "/showcategories")
	public String showAllCategories(Model model) {
		List<CategoryDto> categoryDtos = categoryRepository.showAllCategories();
		model.addAttribute("categoryDtoList", categoryDtos);
		return "category_list";
	}

	@GetMapping(value = "/editcategory/{id}")
	public String showCategoryById(@PathVariable("id") Long id, Model model) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(id);

		CategoryDto dto = categoryRepository.showCategory(categoryDto);

		Category category = modelMapper.map(dto, Category.class);

		model.addAttribute("category", category);
		return "category_update";
	}


	@PostMapping(value = "/doupdate")
	public String updateCategory(@ModelAttribute("category") Category category) {
		CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

		int i = categoryRepository.updateCategory(categoryDto);
		if (i > 0) {
			return "redirect:/category/showcategories";
		} else {
			return "redirect:editcategory/" + categoryDto.getId();
		}
	}

	@RequestMapping(value = "/deletecategory/{id}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") Long id, Model model) {
		return null;
	}
}
