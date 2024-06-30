package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.repository.LotRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class LotController {

	@Autowired
	private LotRepository lotRepository;
	
	@Autowired
	private ModelMapper modelMapper;
}
