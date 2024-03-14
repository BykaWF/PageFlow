package com.project.pageflow.repository;

import com.project.pageflow.models.ShippingAddress;
import com.project.pageflow.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long> {

    ShippingAddress findShippingAddressByStudentAndIsDefaultAddress(Student student, Boolean isDefault);
}
