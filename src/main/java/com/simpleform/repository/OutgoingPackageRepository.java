package com.simpleform.repository;

import com.simpleform.entity.Client;
import com.simpleform.entity.OutgoingPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutgoingPackageRepository extends JpaRepository<OutgoingPackage, Integer> {
    List<OutgoingPackage> findBySender(Client sender);
}
