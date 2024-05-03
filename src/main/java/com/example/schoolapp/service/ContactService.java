package com.example.schoolapp.service;

import com.example.schoolapp.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ContactService {

    public void saveMessageDetails(Contact contact) {
        log.info(contact.toString());
    }
}
