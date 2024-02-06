package com.simpleform.repository;

import com.simpleform.entity.Client;
import com.simpleform.entity.IncomingPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Override
    List<Client> findAll();

    // This will allow you to find an incoming package by its ID
    @Override
    Optional<Client> findById(Integer id);
}
