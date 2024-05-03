package com.example.schoolapp.controller;

import com.example.schoolapp.model.Contact;
import com.example.schoolapp.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class ContactController {

    private final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage() {
        return "contact";
    }


    @RequestMapping(value = "/saveMsg", method = POST)
    public ModelAndView saveMessage(Contact contact) {
        contactService.saveMessageDetails(contact);

        return new ModelAndView("redirect:/contact");
    }

}
