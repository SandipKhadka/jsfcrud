package com.mycompany.jsf.crud.bean;

import com.mycompany.jsf.crud.entity.Student;
import com.mycompany.jsf.crud.service.StudentService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@ViewScoped
public class StudentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private List<Student> students;
    private List<String> classOptions;
    private List<String> subjectOptions;

    @Inject
    private StudentService studentService;

    @PostConstruct
    public void init() {
        student = new Student();
        classOptions = Arrays.asList("Class 1", "Class 2", "Class 3", "Class 4", "Class 5");
        subjectOptions = Arrays.asList("Math", "Science", "English", "History", "Art");
        loadStudents();
    }

    public void loadStudents() {
        students = studentService.getAll();
    }

    public void save() {
        if (student != null) {
            studentService.save(student);
            student = new Student(); // Reset form
            loadStudents();          // Refresh list
        }
    }

    public void edit(Student selected) {
        if (selected != null) {
            this.student = selected;
        }
    }

    public void delete(Student selected) {
        if (selected != null) {
            studentService.delete(selected);
            loadStudents();
        }
    }

    // Getters and Setters

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<String> getClassOptions() {
        return classOptions;
    }

    public List<String> getSubjectOptions() {
        return subjectOptions;
    }
}
