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
import spring.model.Category;
import spring.repository.CategoryRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value="/categoryregister")
    public ModelAndView showRegisterForm() {
        return new ModelAndView("categoryRegister", "category", new Category());
    }

    @PostMapping(value="/doregister")
    public String registerCategory(@ModelAttribute("category") @Valid Category categoryDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "categoryRegister";
        }

        boolean exists = categoryRepository.checkCategoryname(categoryDTO.getName());
        if(exists) {
        	model.addAttribute("error", "Category name already exists. please choose a different name");
        	return "categoryRegister";
        }
        CategoryDto dto = modelMapper.map(categoryDTO, CategoryDto.class);
        if (dto.getName() == null || dto.getName().trim().isEmpty()
        	|| dto.getDescription()	==null || dto.getDescription().trim().isEmpty()) {
            model.addAttribute("error", "Category name and description is required.");
            return "categoryRegister";
        }
        
        int result = categoryRepository.insertCategory(dto);

        if (result > 0) {
            return "redirect:/category/showcategories";
        } else {
            model.addAttribute("error", "Failed to register category. Please try again.");
            return "categoryRegister";
        }
    }
    
    @GetMapping(value="/showcategories")
    public String showAllCategories(Model model) {
        List<CategoryDto> categoryList = categoryRepository.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "categoryList";
    }
    

    @PostMapping("/search")
    public String searchCategories(@RequestParam(name = "name", required = false) String categoryName,
                                   @RequestParam(name = "description", required = false) String categoryDescription,
                                   Model model) {
        List<CategoryDto> categoryList;
        
        if ((categoryName != null && !categoryName.isEmpty()) || (categoryDescription != null && !categoryDescription.isEmpty())) {
            categoryList = categoryRepository.searchCategoriesByNameAndDescription(categoryName, categoryDescription);
        } else {
            categoryList = categoryRepository.getAllCategories();
        }

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("searchTermName", categoryName); 
        model.addAttribute("searchTermDescription", categoryDescription);  
        return "categoryList"; 
    }


    @GetMapping(value="/editcategory/{id}")
    public String showCategoryById(@PathVariable("id") int id, Model model) {
    	CategoryDto dto = categoryRepository.getCategoryById(id);
        if (dto != null) {
            Category category = modelMapper.map(dto, Category.class);
            model.addAttribute("category", category);
            return "categoryEdit";
        } else {
            return "redirect:/category/showcategories";
        }
    }

    @PostMapping(value="/doupdate")
    public String updateCategory(@ModelAttribute("category") @Valid Category categoryDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "categoryEdit";
        }

        CategoryDto dto = modelMapper.map(categoryDTO, CategoryDto.class);
        int result = categoryRepository.updateCategory(dto);

        if (result > 0) {
            return "redirect:/category/showcategories";
        } else {
            model.addAttribute("error", "Failed to update category. Please try again.");
            return "categoryEdit";
        }
    }

    @GetMapping(value="/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        int result = categoryRepository.softDeleteCategory(id);
        return "redirect:/category/showcategories";
    }
}
