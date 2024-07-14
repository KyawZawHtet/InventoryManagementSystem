package spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.dto.ProductDto;
import spring.repository.OrderRepository;
import spring.repository.ProductRepository;

@Controller
@RequestMapping(value="/order")
@AllArgsConstructor
@NoArgsConstructor
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/chooseProducts")
	public String showOrder(ModelMap model)
	{
		List<ProductDto> products = productRepository.getAll();
		model.addAttribute("products", products);
		return "order_to_choose";
	}
	
	@PostMapping("/choose_order")
	public String chooseOrder(HttpServletRequest req, ModelMap model)
	{
		String[] ids = req.getParameterValues("order_id");
		List<ProductDto> products=null;
		 if (ids != null && ids.length > 0) {
			 products= productRepository.getToOrder(ids);
	        }
		 model.addAttribute("products", products);
		return "order_add";
	}
}
