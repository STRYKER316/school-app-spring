package com.example.schoolapp.controller;

import com.example.schoolapp.model.Address;
import com.example.schoolapp.model.Person;
import com.example.schoolapp.model.Profile;
import com.example.schoolapp.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Controller
public class ProfileController {

    private PersonRepository personRepository;

    public ProfileController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @RequestMapping("/displayProfile")
    public ModelAndView displayProfile(Model model, HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInUser");

        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());
        profile.setMobileNumber(person.getMobileNumber());

        if (person.getAddress() != null && person.getAddress().getAddressId() > 0) {
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profile", profile);

        return modelAndView;
    }


    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, HttpSession session) {
        if(errors.hasErrors()){
            return "profile";
        }

        Person person = (Person) session.getAttribute("loggedInUser");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());

        if (person.getAddress() ==null || !(person.getAddress().getAddressId()>0)) {
            person.setAddress(new Address());
        }

        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());

        Person savedPerson = personRepository.save(person);
        session.setAttribute("loggedInUser", savedPerson);

        return "redirect:/displayProfile";
    }

}
