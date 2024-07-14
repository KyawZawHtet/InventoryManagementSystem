package spring.controller;

import java.util.ArrayList;
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


import spring.dto.LotDto;
import spring.dto.ProductDto;
import spring.model.Lot;
import spring.repository.LotRepository;
import spring.repository.ProductRepository;

@Controller
@RequestMapping(value="/lot")
public class LotController {

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LotRepository lotRepository;

	@Autowired
	private ProductRepository productRepository;
	
	    @GetMapping("/register")
	    public ModelAndView showRegisterForm() {
	        ModelAndView mav = new ModelAndView("lotRegister");
	        mav.addObject("lot", new Lot());
	        mav.addObject("products", productRepository.getAllProducts());
	        
	        return mav;
	    }
	
	    @PostMapping(value="/doregister")
	    public String registerLot(@ModelAttribute("lot") @Valid LotDto lotDto, BindingResult br, Model model) {
	        if (br.hasErrors()) {
	            model.addAttribute("products", productRepository.getAllProducts());
	            return "lotRegister";
	        }

	        if (lotDto.getProductId() == null ||
	            lotDto.getExpiredDate() == null ||
	            lotDto.getPrice() <= 0.0 ||
	            lotDto.getDate() == null ||
	            lotDto.getUom() == null ||
	            lotDto.getQuantity() <= 0) {
	        	model.addAttribute("error", "Invalid!");
	        	return "lotRegister";     
	        	}
	        
	        try {
	            int result = lotRepository.insertLot(lotDto);
	            if (result > 0) {
	                return "redirect:/lot/showlots";
	            } else {
	                model.addAttribute("error", "Failed to register lot. Please try again.");
	                return "lotRegister";
	            }
	        } catch (Exception e) {
	            model.addAttribute("error", "Error: " + e.getMessage());
	            return "lotRegister";
	        }
	    }
	
	    @GetMapping("/showlots")
	    public String showAllLots(Model model) {
	        List<LotDto> lotList = lotRepository.getAllLots();
	        model.addAttribute("lotList", lotList);
	        return "lotList";
	    }

	
	    @GetMapping("/editlot/{id}")
	    public String showLotById(@PathVariable("id") int id, Model model) {
	        LotDto dto = lotRepository.getLotById(id);
	        if (dto != null) {
	            Lot lot = modelMapper.map(dto, Lot.class);
	            model.addAttribute("lot", lot);
	            model.addAttribute("products", productRepository.getAllProducts());
	            
	            return "lotEdit";
	        } else {
	            return "redirect:/lot/showlots";
	        }
	    }
	
	    @PostMapping("/doupdate")
	    public String updateLot(@ModelAttribute("lot") @Valid Lot lot, BindingResult br, Model model) {
	        if (br.hasErrors()) {
	            model.addAttribute("products", productRepository.getAllProducts());
	            
	            return "lotEdit";
	        }
	
	        LotDto dto = modelMapper.map(lot, LotDto.class);
	        int result = lotRepository.updateLot(dto);
	
	        if (result > 0) {
	            return "redirect:/lot/showlots";
	        } else {
	            model.addAttribute("error", "Failed to update lot. Please try again.");
	            return "lotEdit";
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
	    
	    


	    
	  @ModelAttribute("UOMList") 
		public List<String> getUOMList()
		{
			List<String> UOMList = new ArrayList<String>();
			UOMList.add("KG"); 
			UOMList.add("BAG");
			UOMList.add("BOTTLE"); 
			UOMList.add("EA");
			
			
			return UOMList;
		} 
}
