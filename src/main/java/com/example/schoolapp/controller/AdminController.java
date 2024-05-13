package com.example.schoolapp.controller;

import com.example.schoolapp.model.Course;
import com.example.schoolapp.model.Person;
import com.example.schoolapp.model.WinterfellClass;
import com.example.schoolapp.repository.CourseRepository;
import com.example.schoolapp.repository.PersonRepository;
import com.example.schoolapp.repository.WinterfellClassRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final WinterfellClassRepository winterfellClassRepository;
    private final PersonRepository personRepository;
    private final CourseRepository courseRepository;

    public AdminController(WinterfellClassRepository winterfellClassRepository, PersonRepository personRepository, CourseRepository courseRepository) {
        this.winterfellClassRepository = winterfellClassRepository;
        this.personRepository = personRepository;
        this.courseRepository = courseRepository;
    }


    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<WinterfellClass> winterfellClasses = winterfellClassRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("classes");
        modelAndView.addObject("winterfellClass", new WinterfellClass());
        modelAndView.addObject("winterfellClasses", winterfellClasses);

        return modelAndView;
    }


    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("winterfellClass") WinterfellClass winterfellClass) {
        winterfellClassRepository.save(winterfellClass);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");

        return modelAndView;
    }


    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<WinterfellClass> winterfellClass = winterfellClassRepository.findById(id);

        for (Person person : winterfellClass.get().getPersons()) {
            person.setWinterfellClass(null);
            personRepository.save(person);
        }

        winterfellClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");

        return modelAndView;
    }


    @RequestMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students");

        Optional<WinterfellClass> winterfellClass = winterfellClassRepository.findById(classId);
        modelAndView.addObject("winterfellClass", winterfellClass.get());
        modelAndView.addObject("person", new Person());

        session.setAttribute("winterfellClass", winterfellClass.get());

        if (error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }


    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        WinterfellClass winterfellClass = (WinterfellClass) session.getAttribute("winterfellClass");
        Person personEntity = personRepository.findByEmail(person.getEmail());

        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + winterfellClass.getClassId() + "&error=true");
            return modelAndView;
        }

        personEntity.setWinterfellClass(winterfellClass);
        personRepository.save(personEntity);

        winterfellClass.getPersons().add(personEntity);
        winterfellClassRepository.save(winterfellClass);

        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + winterfellClass.getClassId());

        return modelAndView;
    }


    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        WinterfellClass winterfellClass = (WinterfellClass) session.getAttribute("winterfellClass");

        Optional<Person> person = personRepository.findById(personId);
        person.get().setWinterfellClass(null);

        winterfellClass.getPersons().remove(person.get());
        WinterfellClass winterfellClassSaved = winterfellClassRepository.save(winterfellClass);

        session.setAttribute("winterfellClass", winterfellClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+ winterfellClass.getClassId());

        return modelAndView;
    }


    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {
        List<Course> courses = courseRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("courses_secure");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Course());

        return modelAndView;
    }


    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Course course) {
        courseRepository.save(course);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/displayCourses");

        return modelAndView;
    }


    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int courseId, HttpSession session,
                                     @RequestParam(value = "error", required = false) String error) {
        Optional<Course> course = courseRepository.findById(courseId);

        ModelAndView modelAndView = new ModelAndView("course_students");
        modelAndView.addObject("course", course.get());
        modelAndView.addObject("person", new Person());

        session.setAttribute("course", course.get());
        if (error != null) {
            modelAndView.addObject("errorMessage", "Invalid Email entered!!");
        }

        return modelAndView;
    }


    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        Course course = (Course) session.getAttribute("course");
        Person personEntity = personRepository.findByEmail(person.getEmail());

        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?courseId=" + course.getCourseId() + "&error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(course);
        course.getPersons().add(personEntity);
        personRepository.save(personEntity);

        modelAndView.setViewName("redirect:/admin/viewStudents?courseId=" + course.getCourseId());

        return modelAndView;
    }


    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId, HttpSession session) {
        Course course = (Course) session.getAttribute("course");

        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(course);
        course.getPersons().remove(person.get());

        personRepository.save(person.get());
        courseRepository.save(course);

        session.setAttribute("course", course);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewStudents?courseId=" + course.getCourseId());

        return modelAndView;
    }

}
