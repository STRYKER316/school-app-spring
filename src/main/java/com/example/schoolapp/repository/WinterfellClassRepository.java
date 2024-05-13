package com.example.schoolapp.repository;

import com.example.schoolapp.model.WinterfellClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinterfellClassRepository extends JpaRepository<WinterfellClass, Integer> {

}
