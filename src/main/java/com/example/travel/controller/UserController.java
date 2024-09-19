package com.example.travel.controller;
import com.example.travel.dto.UserDTO;
import com.example.travel.model.User;
import com.example.travel.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    UserRepo repo;

    @RequestMapping ("/")
    public String indexPage()
    {
        return "index";
    }

    @RequestMapping("/save")
    public String nextPage(User user){
        repo.save(user);
        return "login";
    }
    @PostMapping("/success")
    public String validate(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes, HttpSession session){
        if(repo.findByEmailAndPassword(email,password).isPresent()) {
            UserDTO userDTO = new UserDTO(email);
            session.setAttribute("loggedInUser", userDTO);
            return "redirect:viewItineraries";
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Invalid email or password.");
            return "redirect:/login";
        }
    }

    @RequestMapping("/login")
    public String loginPage(){

        return "login";
    }

    @RequestMapping("/LoginSuccess")
    public String loginSuccess(){
        return "LoginSuccess";
    }

}
