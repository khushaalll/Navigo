package com.example.travel.controller;

import com.example.travel.dto.UserDTO;
import com.example.travel.model.Itinerary;
import com.example.travel.model.User;
import com.example.travel.repository.ItineraryRepo;
import com.example.travel.repository.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ItineraryController {

    @Autowired
    ItineraryRepo itineraryRepo;
    @Autowired
    UserRepo userRepo;

    @RequestMapping("/create")
    public String createItinerary(){
        return "create";
    }
    @RequestMapping("/saveItinerary")
    public String saveItinerary(Itinerary itinerary,
                                @RequestParam String placesToVisit,
                                HttpSession session, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        String email = loggedInUser.getEmail();
        User user = userRepo.findByEmail(email);
        itinerary.setUser(user);
        // Convert placesToVisit string (JSON array) to List<String>
        List<String> placesList = new ObjectMapper().readValue(placesToVisit, new TypeReference<List<String>>() {});
        itinerary.setPlacesToVisit(placesList);
        // Save the itinerary (use your repository/service here)
        itineraryRepo.save(itinerary);
        redirectAttributes.addFlashAttribute("message", "Itinerary added successfully.");
        return "redirect:viewItineraries";
    }
    @GetMapping("/viewItineraries")
    public String viewAllItineraries(HttpSession session, Model model){
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            String email = loggedInUser.getEmail();
            List<Itinerary> itineraries = itineraryRepo.findByUserEmail(email);
            model.addAttribute("itineraries", itineraries);
            return "viewItinerary";
        }
        else return "redirect:login";

    }
    @PostMapping("/addPlace")
    public String addPlace(@RequestParam int itineraryId, @RequestParam String newPlace, RedirectAttributes redirectAttributes) {
        Itinerary itinerary = itineraryRepo.findById(itineraryId).orElse(null);

        if (itinerary != null) {
            List<String> placesToVisit = itinerary.getPlacesToVisit();
            if (placesToVisit == null) {
                placesToVisit = new ArrayList<>();
            }
            placesToVisit.add(newPlace);
            itinerary.setPlacesToVisit(placesToVisit);
            itineraryRepo.save(itinerary);
            redirectAttributes.addFlashAttribute("message", "Place added successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Itinerary not found.");
        }

        return "redirect:viewItineraries";
    }

    @PostMapping("/removePlace")
    public String removePlace(@RequestParam int itineraryId, @RequestParam String placeIndex, RedirectAttributes redirectAttributes) {
        Itinerary itinerary = itineraryRepo.findById(itineraryId).orElse(null);
        if (itinerary != null) {

            List<String> placesToVisit = itinerary.getPlacesToVisit();
            if (placesToVisit != null) {
                Pattern pattern = Pattern.compile("index\\s*=\\s*(\\d+)");
                Matcher matcher = pattern.matcher(placeIndex);
                if(matcher.find()){
                    int toRemove = Integer.parseInt(matcher.group(1));
                placesToVisit.remove(toRemove);
                itinerary.setPlacesToVisit(placesToVisit);
                itineraryRepo.save(itinerary);
                redirectAttributes.addFlashAttribute("message", "Place removed successfully.");}
            } else {
                redirectAttributes.addFlashAttribute("error", "Place not found.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Itinerary not found.");
        }

        return "redirect:viewItineraries";
    }

    @PostMapping("/deleteItinerary")
    public String deleteItinerary(@RequestParam int itineraryId, RedirectAttributes redirectAttributes) {
        Itinerary itinerary = itineraryRepo.findById(itineraryId).orElse(null);

        if (itinerary != null) {
            itineraryRepo.delete(itinerary);
            redirectAttributes.addFlashAttribute("message", "Itinerary deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Itinerary not found.");
        }

        return "redirect:viewItineraries";
    }

    @PostMapping("/updateNotes")
    public String updateNotes(@RequestParam int itineraryId, @RequestParam String notes) {
        // Fetch the itinerary by ID and update the notes
        Itinerary itinerary = itineraryRepo.findById(itineraryId).orElse(null);
        if (itinerary != null) {
            itinerary.setNotes(notes);
            itineraryRepo.save(itinerary); // Save the updated itinerary
        }
        return "redirect:/viewItineraries"; // Redirect back to the itineraries view
    }
}
