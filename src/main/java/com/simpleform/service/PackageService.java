package com.simpleform.service;
import com.simpleform.entity.Client;
import com.simpleform.entity.Employee;
import com.simpleform.entity.IncomingPackage;
import com.simpleform.entity.OutgoingPackage;
import com.simpleform.repository.ClientRepository;
import com.simpleform.repository.EmployeeRepository;
import com.simpleform.repository.IncomingPackageRepository;
import com.simpleform.repository.OutgoingPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PackageService {
    @Autowired
    IncomingPackageRepository incomingPackageRepository;
    @Autowired
    OutgoingPackageRepository outgoingPackageRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public void addIncomingPackageAdmin(IncomingPackage incomingPackage_){
        incomingPackageRepository.save(incomingPackage_);
    }

    public void addIncomingPackageEmployee(IncomingPackage incomingPackage_){
        incomingPackageRepository.save(incomingPackage_);
    }

    public void addOutgoingPackageAdmin(OutgoingPackage outgoingPackage_){
        outgoingPackageRepository.save(outgoingPackage_);
    }

    public void addOutgoingPackageEmployee(OutgoingPackage outgoingPackage_){
        outgoingPackageRepository.save(outgoingPackage_);
    }





    public void updatePackage(IncomingPackage updatedIncomingPackage) {
        IncomingPackage existingIncomingPackage = incomingPackageRepository.findById(updatedIncomingPackage.getId()).orElse(null);
        if (existingIncomingPackage != null) {
            boolean shouldSave = false;

            if (updatedIncomingPackage.getSender() != null && !updatedIncomingPackage.getSender().isEmpty()) {
                existingIncomingPackage.setSender(updatedIncomingPackage.getSender());
                shouldSave = true;
            }
            if (updatedIncomingPackage.getRecipient() != null && !updatedIncomingPackage.getRecipient().isEmpty()) {
                existingIncomingPackage.setRecipient(updatedIncomingPackage.getRecipient());
                shouldSave = true;
            }
            if (updatedIncomingPackage.getDeliveryAddress() != null && !updatedIncomingPackage.getDeliveryAddress().isEmpty()) {
                existingIncomingPackage.setDeliveryAddress(updatedIncomingPackage.getDeliveryAddress());
                shouldSave = true;
            }

            if (updatedIncomingPackage.getWeight() != null) {
                existingIncomingPackage.setWeight(updatedIncomingPackage.getWeight());
                shouldSave = true;
            }

            if (shouldSave) {
                incomingPackageRepository.save(existingIncomingPackage);
            }
        }
    }


    public void deletePackage(IncomingPackage incomingPackage_){
        IncomingPackage existingIncomingPackage = incomingPackageRepository.findById(incomingPackage_.getId()).orElse(null);
        if(existingIncomingPackage != null){
            incomingPackageRepository.delete(existingIncomingPackage);
        }

    }

    public IncomingPackage getPackageById(Integer id) {
        Optional<IncomingPackage> optionalPackage = incomingPackageRepository.findById(id);
        return optionalPackage.orElse(null);
    }

    public List<IncomingPackage> findAllIncomingPackages() {
        return incomingPackageRepository.findAll();
    }

    public Optional<IncomingPackage> findIncomingPackageById(int id) {
        return incomingPackageRepository.findById(id);
    }

    public Optional<OutgoingPackage> findOutgoingPackageById(Integer packageId) {
        return  outgoingPackageRepository.findById(packageId);
    }
    public List<OutgoingPackage> findAllOutgoingPackages() {
        return outgoingPackageRepository.findAll();
    }
    public List<IncomingPackage> getAllPackages() {
        return incomingPackageRepository.findAll();
    }
    public List<OutgoingPackage> getAllOutgoingPackages() {
        return outgoingPackageRepository.findAll();
    }

    public List<IncomingPackage> findIncomingPackagesRegisteredByEmployee(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return incomingPackageRepository.findByRegisteredBy(employee.get());
        } else {
            return Collections.emptyList(); // Връщаме празен списък, ако служителят не съществува
        }
    }
}
