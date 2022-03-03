package example.greetings.Controller;

import com.mysql.cj.util.StringUtils;
import com.sun.istack.NotNull;
import example.greetings.Models.Message;
import example.greetings.Models.User;
import example.greetings.interfaces.MessageRepo;
import example.greetings.interfaces.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Controller
public class MainController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Value("${upload.path}")
    private String uploadpath;

    @Value("${delete.path}")
    private  String deletepath;






    private void saveFile(MultipartFile file, Message message) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()){

            File fileDir =new File(uploadpath);
            if (!fileDir.exists()){

                fileDir.mkdir();
            }

            String uuidFile= UUID.randomUUID().toString();
            String resultFilename =uuidFile+"."+ file.getOriginalFilename();

            file.transferTo(new File(uploadpath+"/"+resultFilename));
            message.setFilename(resultFilename);
        }
    }

    private void deleteFile(@NotNull String fileName) throws IOException {
        if (fileName != null){
            Files.deleteIfExists(Paths.get(deletepath+"\\"+fileName));
        }
    }

    //удаляю по message.id  сам file
    private void fileRemove(Long id) throws IOException {
        Message message = messageRepo.findById(id).get();
        String filename =message.getFilename();
        if (filename != null){
            Files.deleteIfExists(Paths.get(deletepath+"\\"+filename));
        }
    }









    @GetMapping("/")
    public String greeting(
             Model model) {

        return "greeting";

    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model){

        Iterable<Message> messages= messageRepo.findAll();

        if(filter != null && !filter.isEmpty()){
            messages =messageRepo.findByTag(filter);
        }else{
            messages =messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";

    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      Model model,
                      @RequestParam("file") MultipartFile file)
            throws IOException {

        Message message =new Message(tag,text,user);

        saveFile(file, message);


        messageRepo.save(message);
        Iterable<Message> messages= messageRepo.findAll();
        model.addAttribute("messages", messages);
        return "main";

    }



    @GetMapping("/chat")
    public String chat(@AuthenticationPrincipal User user,
                       Model model){

        model.addAttribute("userName", user.getUsername());
        return "chat";
    }


    @GetMapping("/userMessages/{user}")
    public String userMessages(@AuthenticationPrincipal User CurrentUser,
                                @PathVariable User user,
                                Model model,
                                @RequestParam(required = false) Message message,
                                @RequestParam(required = false, defaultValue = "") String filter){

        Set<Message> messages =user.getMessages();

        if(message==null) {
//            if(filter != null && !filter.isEmpty()){
//                message =messageRepo.findByTag(filter);
//            }else{
//                message =messageRepo.findAll();
//            }
//            model.addAttribute("messages", messages);
//            model.addAttribute("filter", filter);

            model.addAttribute("messages", messages);
        }else{
            model.addAttribute("message",message);

        }

        model.addAttribute("isCurrentUser",CurrentUser.equals(user));


       return "userMessages";
    }

    @PostMapping("/userMessages/{user}")
    public String updateMessage(@AuthenticationPrincipal User currentUser,
                                @PathVariable User user,
                                @RequestParam(value = "id",required = false) Message message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("file") MultipartFile file) throws IOException {
        if(message.getAuthor().equals(currentUser)){
            if(!StringUtils.isNullOrEmpty(text)){
                message.setText(text);
            }

            if(!StringUtils.isNullOrEmpty(tag)){
                message.setTag(tag);
            }
            if(message.getFilename()!=null){
                deleteFile(message.getFilename());
                saveFile(file, message);
            }else{
                saveFile(file, message);
            }
            messageRepo.save(message);
        }

        return "redirect:/userMessages/"+user.getId();

    }





    @GetMapping(value = "/userDelete/{user}")
    public String userDelete(@AuthenticationPrincipal User CurrentUser,
                             @PathVariable User user,
                             Model model,
                             @RequestParam(value = "message") Long id) throws IOException {

        if(CurrentUser.isAdmin()){
            fileRemove(id);
            messageRepo.deleteById(id);

        } else if(CurrentUser.equals(user)){
            fileRemove(id);
            messageRepo.deleteById(id);
        }

        return "redirect:/userMessages/"+user.getId();
    }




}