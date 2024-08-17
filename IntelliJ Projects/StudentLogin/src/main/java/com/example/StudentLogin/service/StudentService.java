package com.example.StudentLogin.service;

import com.example.StudentLogin.dto.StudentDTO;
import com.example.StudentLogin.model.Student;

import java.util.List;

public interface StudentService {
    void saveStudent(StudentDTO studentDto);
    Student findStudentByEmail(String email);
    List<StudentDTO> findAllStudents();
}
