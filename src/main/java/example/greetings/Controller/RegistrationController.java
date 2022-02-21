package example.greetings.Controller;


import example.greetings.Models.Role;
import example.greetings.Models.User;
import example.greetings.interfaces.UserRepo;
import example.greetings.service.UserService;
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
    private UserService userService;


    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String addUser(User user, Model model){

       if(!userService.addUser(user)){

       }

        if (userFromDB != null ){
            model.addAttribute("message", "User exists!");
            return "register";
        }



        return"redirect:/login";
    }

//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
}
