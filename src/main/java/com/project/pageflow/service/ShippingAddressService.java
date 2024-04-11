package com.project.pageflow.service;

import com.project.pageflow.models.ShippingAddress;
import com.project.pageflow.models.Student;
import com.project.pageflow.repository.ShippingAddressRepository;
import com.project.pageflow.util.ServiceHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShippingAddressService implements ServiceHelper<ShippingAddress> {

    private final ShippingAddressRepository shippingAddressRepository;

    @Override
    public void setStudent(Student student, ShippingAddress shippingAddress) {
        shippingAddress.setStudent(student);
        shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public void saveEntity(ShippingAddress entity) {
        shippingAddressRepository.save(entity);
    }

    @Override
    public void createOrUpdateEntity(ShippingAddress shippingAddress, Student currentStudent) {
        setStudent(currentStudent,shippingAddress);
        saveEntity(shippingAddress);
    }

    public ShippingAddress getShippingAddressOfCurrentStudent(Student currentStudent) {
        return shippingAddressRepository.findShippingAddressByStudentAndIsDefaultAddress(currentStudent,true);
    }

    public Optional<ShippingAddress> findById(Long addressId) {
        return shippingAddressRepository.findById(addressId);
    }

    public void updateShippingAddress(ShippingAddress updatedShippingAddress) {
        shippingAddressRepository.save(updatedShippingAddress);
    }
}
