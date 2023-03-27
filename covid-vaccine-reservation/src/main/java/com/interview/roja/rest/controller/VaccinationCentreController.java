package com.interview.roja.rest.controller;

import com.interview.roja.rest.dto.VaccinationCentreDTO;
import com.interview.roja.service.VaccinationCentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = ("/vaccinationCentre"))
public class VaccinationCentreController {

    @Autowired
    private VaccinationCentreService vaccinationCentreService;

    @GetMapping({"/list-centre"})
    public ResponseEntity<List<VaccinationCentreDTO>> listCentre(
            @RequestParam(required = false) Integer centreId,
            @RequestParam(required = false) String centreName) {
        return ResponseEntity.ok(vaccinationCentreService.listVaccinationCentre(centreId, centreName));
    }

    @GetMapping(value = "/available-centre-slots/{centreId}/{date}")
    public ResponseEntity<VaccinationCentreDTO> availableCentreSlots(
            @PathVariable Integer centreId,
            @PathVariable String date) {
        return ResponseEntity.ok(vaccinationCentreService.availableCentreSlots(centreId, date));
    }
}
