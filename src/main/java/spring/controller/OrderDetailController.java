package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.repository.OrderDetailRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailController {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ModelMapper modelMapper;
}
