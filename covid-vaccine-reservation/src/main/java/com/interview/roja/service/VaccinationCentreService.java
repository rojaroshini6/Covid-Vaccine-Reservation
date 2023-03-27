package com.interview.roja.service;

import com.interview.roja.rest.dto.VaccinationCentreDTO;

import java.util.List;

public interface VaccinationCentreService {
    public List<VaccinationCentreDTO> listVaccinationCentre(Integer centreId, String centreName);

    public VaccinationCentreDTO availableCentreSlots(Integer centreId, String date);
}
