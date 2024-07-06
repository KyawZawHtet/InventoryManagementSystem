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
import spring.model.Role;
import spring.repository.RoleRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/add_user_role")
	public ModelAndView addUserRole() {
		Role role = new Role();
		return new ModelAndView("addrole", "role", role);
	}

	@PostMapping(value = "do_user_addition")
	public String doUserAddition(@Valid @ModelAttribute("role") Role role, BindingResult bindingResult,
								 Model model) {

		if (bindingResult.hasErrors()) {
			return "addrole";
		}
		if (role != null) {
			RoleDto roleDTO = modelMapper.map(role, RoleDto.class);
			int i = roleRepository.insert(roleDTO);
			if (i > 0) {
				return "redirect:/show_user_roles";
			} else {
				return "redirect:/add_user_role";
			}
		} else {
			model.addAttribute("role", role);
			return "redirect:/add_user_role";
		}
	}

	@GetMapping(value = "/show_user_roles")
	public String showUserRoles(Model model) {

		List<RoleDto> roles = roleRepository.showAllUserRole();
		model.addAttribute("roleList", roles);
		return "rolelist";
	}

	@GetMapping(value = "/user_role_update/{user_role_id}")
	public String showUserRoleUpdateForm(@PathVariable("user_role_id") Long id, Model model) {
		RoleDto roleDTO = roleRepository.showUserRoleById(id);
		Role role = modelMapper.map(roleDTO, Role.class);
		model.addAttribute("role", role);

		return "updaterole";
	}

	@PostMapping(value = "/do_user_role_update")
	public String doUserRoleUpdate(@Valid @ModelAttribute("userRole") Role role, BindingResult bindingResult,
								   Model model) {
		if (bindingResult.hasErrors()) {
			return "updaterole";
		}

		RoleDto roleDTO = modelMapper.map(role, RoleDto.class);
		int i = roleRepository.updateUserRoleById(roleDTO);
		if (i > 0) {
			return "redirect:/show_user_roles";
		} else {
			model.addAttribute("role", role);
			return "redirect:/user_update_role" + role.getId();
		}
	}
}
