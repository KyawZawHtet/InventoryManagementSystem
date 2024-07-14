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
<<<<<<< HEAD
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

=======
>>>>>>> origin/ATD
import spring.dto.LocationDto;
import spring.model.Location;
import spring.repository.LocationRepository;

import java.util.List;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/location")
public class LocationController {

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private ModelMapper modelMapper;
<<<<<<< HEAD

	@RequestMapping(value = "/add_location", method = RequestMethod.GET)
	public ModelAndView showRegister(Model model) {
		Location location = new Location();
		return new ModelAndView("location_add", "location", location);
	}

	@RequestMapping(value = "/do_location", method = RequestMethod.POST)
	public String showResult(@ModelAttribute("location") Location location, Model model) {

		LocationDto locationDto = new LocationDto();
		locationDto.setName(location.getName());
		locationDto.setAddress(location.getAddress());

		int i = locationRepository.insertLocation(locationDto);
		if (i > 0) {
			return "redirect:/location/showlocations";
		} else {
			model.addAttribute("location", new Location());
			return "location_add";
		}
	}

	@GetMapping(value = "/showlocations")
	public String showAllLocations(Model model) {
		List<LocationDto> locationDtos = locationRepository.showAllLocations();
		model.addAttribute("locationDtoList", locationDtos);
		return "location_list";
	}

	@GetMapping(value = "/editlocation/{id}")
	public String showLocationById(@PathVariable("id") Long id, Model model) {
		LocationDto locationDto = new LocationDto();
		locationDto.setId(id);

		LocationDto dto = locationRepository.showLocation(locationDto);

		Location location = modelMapper.map(dto, Location.class);

		model.addAttribute("location", location);
		return "location_update";
	}


	@PostMapping(value = "/doupdate")
	public String updateLocation(@ModelAttribute("location") Location location) {
		LocationDto locationDto = modelMapper.map(location, LocationDto.class);

		int i = locationRepository.updateLocation(locationDto);
		if (i > 0) {
			return "redirect:/location/showlocations";
		} else {
			return "redirect:editlocation/" + locationDto.getId();
		}
	}

	@RequestMapping(value = "/deletelocation/{id}", method = RequestMethod.GET)
	public String deleteLocation(@PathVariable("id") Long id, Model model) {
		return null;
	}
=======
	
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
>>>>>>> origin/ATD
}
