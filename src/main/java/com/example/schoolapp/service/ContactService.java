package com.example.schoolapp.service;

import com.example.schoolapp.constants.SchoolConstants;
import com.example.schoolapp.model.Contact;
import com.example.schoolapp.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;

        contact.setStatus(SchoolConstants.OPEN);
        contact.setCreatedBy(SchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());

        Contact savedContact = contactRepository.save(contact);
        if (savedContact != null && savedContact.getContactId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }


    public List<Contact> findMessagesWithOpenStatus() {
        return contactRepository.findByStatus(SchoolConstants.OPEN);
    }


    public boolean updateMsgStatus(int contactId, String updatedBy) {
        boolean isUpdated = false;

        Optional<Contact> contact = contactRepository.findById(contactId);
        if (contact.isPresent()) {
            contact.get().setStatus(SchoolConstants.CLOSE);
            contact.get().setUpdatedBy(updatedBy);
            contact.get().setUpdatedAt(LocalDateTime.now());

            Contact updatedContact = contactRepository.save(contact.get());
            if (updatedContact != null && updatedContact.getUpdatedBy() != null) {
                isUpdated = true;
            }
        }

        return isUpdated;
    }
}
