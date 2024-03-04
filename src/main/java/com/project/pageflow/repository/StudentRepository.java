package com.project.pageflow.repository;

import com.project.pageflow.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
