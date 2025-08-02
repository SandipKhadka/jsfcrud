package com.mycompany.jsf.crud.service;

import com.mycompany.jsf.crud.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class StudentService {

    @PersistenceContext(unitName = "StudentPU")
    private EntityManager em;

    public List<Student> getAll() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            em.persist(student);
            return student;
        } else {
            return em.merge(student);
        }
    }

    public void delete(Student student) {
        if (student != null && student.getId() != null) {
            Student managed = em.find(Student.class, student.getId());
            if (managed != null) {
                em.remove(managed);
            }
        }
    }
}
