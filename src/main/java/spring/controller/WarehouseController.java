package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.repository.WarehouseRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseController {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
	private ModelMapper modelMapper;
}
