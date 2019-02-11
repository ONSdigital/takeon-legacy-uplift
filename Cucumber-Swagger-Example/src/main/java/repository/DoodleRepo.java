package uk.gov.ons.collection.repository;
/*
    Name: DoodleRepo.java

    Description: DoodleRepo.java gives the methods that will be used by the controller

*/
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DoodleRepo extends CrudRepository<uk.gov.ons.collection.entity.DoodleEntity, String>, JpaSpecificationExecutor<uk.gov.ons.collection.entity.DoodleEntity> {

    //uk.gov.ons.collection.entity.DoodleEntity findById(String nameID);

    List<uk.gov.ons.collection.entity.DoodleEntity> findByFirstNameLike(String firstName);

    List<uk.gov.ons.collection.entity.DoodleEntity> findBySurNameLike(String surName);
}
