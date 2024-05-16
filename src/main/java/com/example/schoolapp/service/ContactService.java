package com.example.schoolapp.service;

import com.example.schoolapp.constants.SchoolConstants;
import com.example.schoolapp.model.Contact;
import com.example.schoolapp.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

        Contact savedContact = contactRepository.save(contact);
        if (savedContact != null && savedContact.getContactId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }


    public Page<Contact> findMessagesWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());

        Page<Contact> msgPage = contactRepository.findByStatusWithQuery(SchoolConstants.OPEN, pageable);

        return msgPage;
    }


    public boolean updateMsgStatus(int contactId) {
        boolean isUpdated = false;

//        // Taking the help of JPA Queries
//        Optional<Contact> contact = contactRepository.findById(contactId);
//        if (contact.isPresent()) {
//            contact.get().setStatus(SchoolConstants.CLOSE);
//
//            Contact updatedContact = contactRepository.save(contact.get());
//            if (updatedContact != null && updatedContact.getUpdatedBy() != null) {
//                isUpdated = true;
//            }
//        }

        // Taking the help of @Query annotation with derived JPA queries
        int updatedRows = contactRepository.updateStatusById(SchoolConstants.CLOSE, contactId);
        if (updatedRows > 0) {
            isUpdated = true;
        }

        return isUpdated;
    }
}
