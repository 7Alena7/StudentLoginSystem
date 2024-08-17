package com.example.StudentLogin.controller;

import com.example.StudentLogin.dto.StudentDTO;
import com.example.StudentLogin.model.Student;
import com.example.StudentLogin.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentAuthController {
    private StudentService studentService;

    @Autowired
    public StudentAuthController(StudentService studentService){
        this.studentService = studentService;
    }
    //handler method to handle the home(index.html is home) page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }
    //handler method handles the login request
    @GetMapping("login")
    public String login(){
        return "login";
    }
    //handler method to handle the student registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        //create a model object to store form data
        StudentDTO student = new StudentDTO();
        model.addAttribute("student", student);
        return "register";
    }
    //handler method to handle student registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("student") StudentDTO studentDTO, BindingResult result, Model model){
        Student existingStudent = studentService.findStudentByEmail(studentDTO.getEmail());
        if(existingStudent != null && existingStudent.getEmail() !=null && !existingStudent.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already an account registred with the same email");
        }
        if(result.hasErrors()){
            model.addAttribute("student", studentDTO);
            return "/register";
        }
        studentService.saveStudent((studentDTO));
        return "redirect:/register?success";
    }
    //handler method is used to handle a list of students
    @GetMapping("/students")
    public String students(Model model){
        List<StudentDTO>students=studentService.findAllStudents();
        model.addAttribute("students", students);
        return "students";
    }
}
