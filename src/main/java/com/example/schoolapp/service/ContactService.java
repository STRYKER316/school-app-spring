package com.example.schoolapp.service;

import com.example.schoolapp.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    public void saveMessageDetails(Contact contact) {

        log.info(contact.toString());

    }
}
