package com.example.schoolapp.controller;

import com.example.schoolapp.model.Person;
import com.example.schoolapp.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class DashboardController {

    private final PersonRepository personRepository;

    public DashboardController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.findByEmail(authentication.getName());
        session.setAttribute("loggedInUser", person);

        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());

        if (null != person.getWinterfellClass() && null != person.getWinterfellClass().getName()) {
            model.addAttribute("enrolledClass", person.getWinterfellClass().getName());
        }

        return "dashboard";
    }
}
