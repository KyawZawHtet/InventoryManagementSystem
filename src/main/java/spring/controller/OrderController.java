package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.repository.OrderRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
}
