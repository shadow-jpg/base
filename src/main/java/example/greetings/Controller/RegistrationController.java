package example.greetings.Controller;


import example.greetings.Models.User;
import example.greetings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
           model.addAttribute("message", "User exists. Try over nickname!");
           return "register";
       }

       return"redirect:/login";
    }

    @GetMapping("/activation/{code}")
    public String activate(Model model, @PathVariable String code){

        boolean isActivated =userService.activateUser(code);

        if(isActivated){
            model.addAttribute("message","User mail activated!");
        } else{
            model.addAttribute("message","Email haven't passed the activation. No code,Sorry!");
        }

        return "login";
    }

}
