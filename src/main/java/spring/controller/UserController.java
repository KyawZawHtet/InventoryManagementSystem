package spring.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.RoleDto;
import spring.dto.UserDto;
import spring.model.User;
import spring.repository.RoleRepository;
import spring.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/user_registration")
	public ModelAndView showRegister() {

		User user = new User();
		return new ModelAndView("adduser", "user", user);
	}

	@PostMapping(value = "/doregistration")
	public String doRegister(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("Fuck You Error! 1");
			return "adduser";
		}

		if (user != null) {
			UserDto userDto = modelMapper.map(user, UserDto.class);
			int i = userRepository.insert(userDto);
			if (i > 0) {
				System.out.println("Fuck You Error! 2");
				return "redirect:/showusers";
			} else {
				model.addAttribute("user", new User());
				System.out.println("Fuck You Error! 3");
				return "adduser";
			}
		} else {
			model.addAttribute("user", new User());
			System.out.println("Fuck You Error! 4");
			return "adduser";
		}

	}

	@GetMapping(value = "/user_update/{user_id}")
	public String showUserUpdateForm(@PathVariable("user_id") int id, Model model) {

		UserDto dto = userRepository.showUserById(id);
		User userBean = modelMapper.map(dto, User.class);
		model.addAttribute("userBean", userBean);
		return "updateuser";

	}

	@PostMapping(value = "/do_update")
	public String doUpdate(@Valid @ModelAttribute("userBean")User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "updateuser";
		}
		UserDto userDTO = modelMapper.map(user, UserDto.class);
		int i = userRepository.updateUserById(userDTO);
		System.out.println(i);
		if (i > 0) {
			return "redirect:/showusers";
		} else {
			return "redirect:/user_update/" + user.getId();
		}

	}

	@GetMapping(value = "/showusers")
	public String showAllUser(Model model) {
		List<UserDto> userDto = userRepository.showAllUser();
		model.addAttribute("dtoList", userDto);
		return "userlist";

	}

	@ModelAttribute("userRoles")
	public List<RoleDto> getUserRoles() {
		List<RoleDto> roles = roleRepository.showAllUserRole().stream()
				.map(role -> new RoleDto(role.getId(), role.getRole())).collect(Collectors.toList());
		return roles;
	}

}
