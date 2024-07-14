package spring.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.CategoryDto;
import spring.dto.RoleDto;
import spring.dto.UserDto;
import spring.model.Category;
import spring.model.User;
import spring.repository.RoleRepository;
import spring.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/add_user", method = RequestMethod.GET)
	public ModelAndView showRegister(Model model) {
		User user = new User();
		return new ModelAndView("user_add", "user", user);
	}

	@RequestMapping(value = "/do_user", method = RequestMethod.POST)
	public String showResult(@ModelAttribute("user") User user, Model model) {

		UserDto userDto = new UserDto();
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setStatus(user.isStatus());
		userDto.setRoleId(user.getRoleId());

		int i = userRepository.insertUser(userDto);
		if (i > 0) {
			return "redirect:/user/showusers";
		} else {
			model.addAttribute("user", new User());
			return "add_user";
		}
	}

	@GetMapping(value = "/showusers")
	public String showAllUsers(Model model) {
		List<UserDto> userDtos = userRepository.showAllUsers();
		model.addAttribute("userDtoList", userDtos);
		return "user_list";
	}

	@GetMapping(value = "/edituser/{id}")
	public String showUserById(@PathVariable("id") Long id, Model model) {
		UserDto userDto = new UserDto();
		userDto.setId(id);

		UserDto dto = userRepository.showUser(userDto);

		User user = modelMapper.map(dto, User.class);

		model.addAttribute("user", user);
		return "user_update";
	}


	@PostMapping(value = "/doupdate")
	public String updateUser(@ModelAttribute("user") User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);

		int i = userRepository.updateUser(userDto);
		if (i > 0) {
			return "redirect:/user/showusers";
		} else {
			return "redirect:edituser/" + userDto.getId();
		}
	}

	@RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long id, Model model) {
		return null;
	}

	@ModelAttribute("roles")
	public List<RoleDto> getUserRoles() {
		List<RoleDto> roleDtos = roleRepository.showAllRoles().stream()
				.map(roles -> new RoleDto(roles.getId(), roles.getRoleName())).collect(Collectors.toList());
		return roleDtos;
	}
}
