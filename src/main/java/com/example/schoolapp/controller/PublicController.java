package com.example.schoolapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("/public")
public class PublicController {

    @RequestMapping(value ="/register",method = { RequestMethod.GET})
    public String displayRegisterPage() {
//        model.addAttribute("person", new Person());
        return "register";
    }

}
