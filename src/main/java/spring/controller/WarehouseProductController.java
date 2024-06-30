package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.repository.WarehouseProductRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProductController {

	@Autowired
	private WarehouseProductRepository warehouseProductRepository;
	
	@Autowired
	private ModelMapper modelMapper;
}