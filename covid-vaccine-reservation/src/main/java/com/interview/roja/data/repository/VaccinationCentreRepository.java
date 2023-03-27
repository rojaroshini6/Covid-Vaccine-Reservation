package com.interview.roja.data.repository;

import com.interview.roja.data.model.VaccinationCentre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VaccinationCentreRepository extends CrudRepository<VaccinationCentre, Integer> {
    List<VaccinationCentre> findAll();

    Optional<VaccinationCentre> findByCentreName(String centreName);
}
