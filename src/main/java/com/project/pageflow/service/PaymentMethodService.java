package com.project.pageflow.service;

import com.project.pageflow.models.PaymentMethod;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.PaymentMethodRepository;
import com.project.pageflow.util.ServiceHelper;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService implements ServiceHelper<PaymentMethod> {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public void setStudent(Student student, PaymentMethod paymentMethod) {
        paymentMethod.setStudent(student);
        saveEntity(paymentMethod);
    }
    @Override
    public void saveEntity(PaymentMethod paymentMethod){
        paymentMethodRepository.save(paymentMethod);
    }
}
