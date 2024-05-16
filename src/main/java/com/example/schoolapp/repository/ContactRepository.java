package com.example.schoolapp.repository;

import com.example.schoolapp.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByStatus(String status);

    //@Query("SELECT c FROM Contact AS c WHERE c.status = :status")
    @Query(value = "SELECT * FROM contact_msg AS c WHERE c.status = :status", nativeQuery = true)
    Page<Contact> findByStatusWithQuery(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact AS c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);

}
