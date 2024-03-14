package com.project.pageflow.util;

import com.project.pageflow.models.Student;

public interface ServiceHelper<T> {
    void setStudent(Student student, T entity);
    void saveEntity(T entity);

    void createOrUpdateEntity(T entity, Student student);
}
