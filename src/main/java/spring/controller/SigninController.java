package spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.dto.UserDto;
import spring.model.User;
import spring.repository.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
public class SigninController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showSignin() {
        return new ModelAndView("signin", "signin", new User());
    }

    @PostMapping(value = "/do_signin")
    public String signin(@ModelAttribute("signin") User user, Model model, HttpSession session,
                         RedirectAttributes redirectAttributes) {
        if (userRepository.checkEmail(user.getEmail())) {
            UserDto dto = new UserDto();
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            UserDto userDto = userRepository.signinUser(dto);
            if (userDto != null) {
                session.setAttribute("res", userDto);
                return "redirect:/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("loginError", "Incorrect Password!");
                return "redirect:/";
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Invalid User!");
            return "redirect:/";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
