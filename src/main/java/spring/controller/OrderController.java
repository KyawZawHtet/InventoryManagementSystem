package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.LocationDto;
import spring.dto.OrderDto;
import spring.dto.ProductDto;
import spring.model.Order;
import spring.repository.LocationRepository;
import spring.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private ModelMapper modelMapper;


}
