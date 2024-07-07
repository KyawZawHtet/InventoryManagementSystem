package spring.controller;

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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.dto.LocationDto;
import spring.model.Location;
import spring.repository.LocationRepository;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/location")
public class LocationController {

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value="/locationregister")
    public ModelAndView showRegisterForm() {
        return new ModelAndView("locationRegister", "location", new Location());
    }

    @PostMapping(value="/doregister")
    public String registerLocation(@ModelAttribute("location") @Valid Location locationDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "locationRegister";
        }
        
        
        boolean exists = locationRepository.checkWarehousename(locationDTO.getName());
        if(exists) {
        	model.addAttribute("error", "Warehouse name already exists. please choose a different name");
        	return "locationRegister";
        }
        LocationDto dto = modelMapper.map(locationDTO, LocationDto.class);
        if (dto.getName() == null || dto.getName().trim().isEmpty()
            || dto.getAddress()	==null || dto.getAddress() .trim().isEmpty()) {
            model.addAttribute("error", "Location name and address is required.");
            return "locationRegister";
        }
        
        int result = locationRepository.insertLocation(dto);
    	if (result > 0) {
            return "redirect:/location/showlocations";
        } else {
            model.addAttribute("error", "Failed to register location. Please try again.");
            return "locationRegister";
        }	    
    }

    
    @GetMapping(value="/showlocations")
    public String showAllLocations(Model model) {
        List<LocationDto> locationList = locationRepository.getAllLocations();
        model.addAttribute("locationList", locationList);
        return "locationList";
    }

    @PostMapping("/search")
    public String searchLocations(@RequestParam(name = "name", required = false) String locationName,
                                  @RequestParam(name = "address", required = false) String locationAddress,
                                  Model model) {
        List<LocationDto> locationList;

        if ((locationName != null && !locationName.isEmpty()) || (locationAddress != null && !locationAddress.isEmpty())) {
            locationList = locationRepository.searchLocationsByNameAndAddress(locationName, locationAddress);
        } else {
            locationList = locationRepository.getAllLocations();
        }

        model.addAttribute("locationList", locationList);
        model.addAttribute("searchTermName", locationName);
        model.addAttribute("searchTermAddress", locationAddress);
        return "locationList"; 
    }
    
    @GetMapping(value="/editlocation/{id}")
    public String showLocationById(@PathVariable("id") int id, Model model) {
        LocationDto dto = locationRepository.getLocationById(id);
        if (dto != null) {
            Location location = modelMapper.map(dto, Location.class);
            model.addAttribute("location", location);
            return "locationEdit";
        } else {
            return "redirect:/location/showlocations";
        }
    }

    @PostMapping(value="/doupdate")
    public String updateLocation(@ModelAttribute("location") @Valid Location locationDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "locationEdit";
        }

        LocationDto dto = modelMapper.map(locationDTO, LocationDto.class);
        int result = locationRepository.updateLocation(dto);

        if (result > 0) {
            return "redirect:/location/showlocations";
        } else {
            model.addAttribute("error", "Failed to update location. Please try again.");
            return "locationEdit";
        }
    }

    
    @GetMapping(value="/deletelocation/{id}")
    public String deleteLocation(@PathVariable("id") int id) {
        int result = locationRepository.softDeleteLocation(id);
        return "redirect:/location/showlocations";
    }
}
