package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
}
