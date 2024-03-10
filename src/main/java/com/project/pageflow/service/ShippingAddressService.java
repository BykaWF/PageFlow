package com.project.pageflow.service;

import com.project.pageflow.models.ShippingAddress;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.ShippingAddressRepository;
import com.project.pageflow.util.ServiceHelper;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressService implements ServiceHelper<ShippingAddress> {
    private final ShippingAddressRepository shippingAddressRepository;

    public ShippingAddressService(ShippingAddressRepository shippingAddressRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
    }

    @Override
    public void setStudent(Student student, ShippingAddress shippingAddress) {
        shippingAddress.setStudent(student);
        shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public void saveEntity(ShippingAddress entity) {
        shippingAddressRepository.save(entity);
    }
}
