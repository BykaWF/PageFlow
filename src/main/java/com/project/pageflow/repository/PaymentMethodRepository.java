package com.project.pageflow.repository;

import com.project.pageflow.models.PaymentMethod;
import com.project.pageflow.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {

    PaymentMethod findPaymentMethodByStudentAndIsDefaultPaymentMethod(Student student,Boolean isDefault);
}
