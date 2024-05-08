package com.example.schoolapp.service;

import com.example.schoolapp.constants.SchoolConstants;
import com.example.schoolapp.model.Person;
import com.example.schoolapp.model.Roles;
import com.example.schoolapp.repository.PersonRepository;
import com.example.schoolapp.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, RolesRepository rolesRepository) {
        this.personRepository = personRepository;
        this.rolesRepository = rolesRepository;
    }

    public boolean createNewPerson(Person person){
        boolean isSaved = false;

        Roles role = rolesRepository.findByRoleName(SchoolConstants.STUDENT_ROLE);
        person.setRole(role);
        person = personRepository.save(person);

        if (person != null && person.getPersonId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }
}
