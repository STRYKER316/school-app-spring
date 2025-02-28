package com.example.schoolapp.repository;

import com.example.schoolapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "courses")
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByOrderByNameDesc();

    List<Course> findByOrderByName();

}
