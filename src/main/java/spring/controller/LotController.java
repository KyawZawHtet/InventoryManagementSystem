package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.repository.LotRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/lot")
public class LotController {

	@Autowired
	private LotRepository lotRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/create")
	public String registerLot()
	{
		return "lot_add";
	}
}
