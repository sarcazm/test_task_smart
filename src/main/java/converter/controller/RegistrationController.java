package converter.controller;

import converter.model.User;
import converter.repository.UserRepo;
import converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addClient(User user, Model model){
        User result = userService.findByUsername(user.getUsername());
        if (result != null){
            model.addAttribute("message", "Такой клиент уж существует");
            return "registration";
        }
        user.setActive(true);
        userService.save(user);

        return "redirect:/login";
    }
}
