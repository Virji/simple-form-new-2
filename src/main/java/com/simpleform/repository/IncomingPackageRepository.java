package com.simpleform.repository;

import com.simpleform.entity.Employee;
import com.simpleform.entity.IncomingPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncomingPackageRepository extends JpaRepository<IncomingPackage,Integer> {

    @Override
    List<IncomingPackage> findAll();
    @Override
    Optional<IncomingPackage> findById(Integer id);

    List<IncomingPackage> findByRegisteredBy(Employee employee);

}