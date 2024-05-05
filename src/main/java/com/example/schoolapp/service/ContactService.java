package com.example.schoolapp.service;

import com.example.schoolapp.constants.SchoolConstants;
import com.example.schoolapp.model.Contact;
import com.example.schoolapp.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;

        contact.setStatus(SchoolConstants.OPEN);
        contact.setCreatedBy(SchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());

        int result = contactRepository.saveContactMsg(contact);
        if (result > 0) {
            isSaved = true;
        }

        return isSaved;
    }

    public List<Contact> findMessagesWithOpenStatus() {
        return contactRepository.findMsgsWithStatus(SchoolConstants.OPEN);
    }
}
