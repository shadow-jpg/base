package example.greetings.Controller;


import example.greetings.Models.Role;
import example.greetings.Models.User;
import example.greetings.interfaces.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;


    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String addUser(User user, Model model){

        User userFromDB = userRepo.findByUsername(user.getUsername());

        if (userFromDB != null ){
            model.addAttribute("message", "User exists!");
            return "register";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return"redirect:/login";
    }

//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
}
