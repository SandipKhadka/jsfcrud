package com.mycompany.jsf.crud.bean;

import com.mycompany.jsf.crud.entity.Student;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@SessionScoped
public class StudentBean implements Serializable {
    private Student student = new Student();
    private List<Student> students;
    private List<String> classOptions;
    private List<String> subjectOptions;

    @PersistenceContext(unitName = "StudentPU")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    public StudentBean() {
        System.out.println("Initializing StudentBean");
        classOptions = Arrays.asList("Class 1", "Class 2", "Class 3", "Class 4", "Class 5");
        subjectOptions = Arrays.asList("Math", "Science", "English", "History", "Art");
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudents() {
        students = entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        System.out.println("Loading students");
        return students;
    }

    public List<String> getClassOptions() {
        return classOptions;
    }

    public List<String> getSubjectOptions() {
        return subjectOptions;
    }

    public void save() {
        System.out.println("Saving student: " + student.getName());
        try {
            userTransaction.begin();
            if (student.getId() == null) {
                entityManager.persist(student);
            } else {
                entityManager.merge(student);
            }
            userTransaction.commit();
            student = new Student();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                userTransaction.rollback();
            } catch (Exception re) {
                re.printStackTrace();
            }
        }
    }

    public void edit(Student student) {
        System.out.println("Editing student: " + student.getName());
        this.student = student;
    }

    public void delete(Student student) {
        System.out.println("Deleting student: " + student.getName());
        try {
            userTransaction.begin();
            Student managedStudent = entityManager.find(Student.class, student.getId());
            if (managedStudent != null) {
                entityManager.remove(managedStudent);
            }
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                userTransaction.rollback();
            } catch (Exception re) {
                re.printStackTrace();
            }
        }
    }
}
