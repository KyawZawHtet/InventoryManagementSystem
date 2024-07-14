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
import spring.dto.LotDto;
import spring.model.Lot;
import spring.repository.LotRepository;
import spring.repository.ProductRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/lot")
public class LotController {

	@Autowired
	private LotRepository lotRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/register")
	public ModelAndView showRegisterForm() {
		ModelAndView mav = new ModelAndView("lot_add");
		mav.addObject("lot", new Lot());
		mav.addObject("products", productRepository.showAllProducts());

		return mav;
	}

	@PostMapping(value="/doregister")
	public String registerLot(@ModelAttribute("lot") @Valid LotDto lotDto, BindingResult br, Model model) {
		if (br.hasErrors()) {
			model.addAttribute("products", productRepository.showAllProducts());
			return "lot_add";
		}

		if (lotDto.getProductId() == null ||
				lotDto.getExpiredDate() == null ||
				lotDto.getPrice() <= 0.0 ||
				lotDto.getDate() == null ||
				lotDto.getUom() == null ||
				lotDto.getQuantity() <= 0) {
			model.addAttribute("error", "Invalid!");
			return "lot_add";
		}

		try {
			int result = lotRepository.insertLot(lotDto);
			if (result > 0) {
				return "redirect:/lot/showlots";
			} else {
				model.addAttribute("error", "Failed to register lot. Please try again.");
				return "lot_add";
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error: " + e.getMessage());
			return "lot_add";
		}
	}

	@GetMapping("/showlots")
	public String showAllLots(Model model) {
		List<LotDto> lotList = lotRepository.getAllLots();
		model.addAttribute("lotList", lotList);
		return "lot_list";
	}


	@GetMapping("/editlot/{id}")
	public String showLotById(@PathVariable("id") int id, Model model) {
		LotDto dto = lotRepository.getLotById(id);
		if (dto != null) {
			Lot lot = modelMapper.map(dto, Lot.class);
			model.addAttribute("lot", lot);
			model.addAttribute("products", productRepository.showAllProducts());

			return "lot_update";
		} else {
			return "redirect:/lot/showlots";
		}
	}

	@PostMapping("/doupdate")
	public String updateLot(@ModelAttribute("lot") @Valid Lot lot, BindingResult br, Model model) {
		if (br.hasErrors()) {
			model.addAttribute("products", productRepository.showAllProducts());

			return "lot_update";
		}

		LotDto dto = modelMapper.map(lot, LotDto.class);
		int result = lotRepository.updateLot(dto);

		if (result > 0) {
			return "redirect:/lot/showlots";
		} else {
			model.addAttribute("error", "Failed to update lot. Please try again.");
			return "lot_update";
		}
	}

	@GetMapping("/deletelot/{id}")
	public String deleteLot(@PathVariable("id") int id) {
		int result = lotRepository.softDeleteLot(id);
		if(result > 0) {
			return "redirect:/lot/showlots";
		} else {
			return "error";
		}
	}
}
