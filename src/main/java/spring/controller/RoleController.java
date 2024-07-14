package spring.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.dto.RoleDto;
import spring.model.Role;
import spring.repository.RoleRepository;

import java.util.List;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value="/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/add_role", method = RequestMethod.GET)
    public ModelAndView showRegister(Model model) {
        Role role = new Role();
        return new ModelAndView("role_add", "role", role);
    }

    @RequestMapping(value = "/do_role", method = RequestMethod.POST)
    public String showResult(@ModelAttribute("role") Role role, Model model) {

        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName(role.getRoleName());

        int i = roleRepository.insertRole(roleDto);
        if (i > 0) {
            return "redirect:/role/showroles";
        } else {
            model.addAttribute("role", new Role());
            return "add_role";
        }
    }

    @GetMapping(value = "/showroles")
    public String showAllRoles(Model model) {
        List<RoleDto> roleDtos = roleRepository.showAllRoles();
        model.addAttribute("roleDtoList", roleDtos);
        return "role_list";
    }

    @GetMapping(value = "/editrole/{id}")
    public String showRoleById(@PathVariable("id") Long id, Model model) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(id);

        RoleDto dto = roleRepository.showRole(roleDto);

        Role role = modelMapper.map(dto, Role.class);

        model.addAttribute("role", role);
        return "role_update";
    }


    @PostMapping(value = "/doupdate")
    public String updateRole(@ModelAttribute("role") Role role) {
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);

        int i = roleRepository.updateRole(roleDto);
        if (i > 0) {
            return "redirect:/role/showroles";
        } else {
            return "redirect:editrole/" + roleDto.getId();
        }
    }

    @RequestMapping(value = "/deleterole/{id}", method = RequestMethod.GET)
    public String deleteRole(@PathVariable("id") Long id, Model model) {
        return null;
    }
}
