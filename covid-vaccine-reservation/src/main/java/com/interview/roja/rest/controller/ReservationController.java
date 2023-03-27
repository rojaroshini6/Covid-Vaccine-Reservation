package com.interview.roja.rest.controller;

import com.interview.roja.rest.dto.ReservationDTO;
import com.interview.roja.service.ReservationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3002")
@RestController
@RequestMapping(value = ("/reservation"))
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping({"/get-active-reservations"})
    public ResponseEntity<List<ReservationDTO>> getAllReservation() {
        return ResponseEntity.ok(reservationService.getAllActiveReservation());
    }

    @GetMapping({"/get-reservation-for-nric"})
    public ResponseEntity<ReservationDTO> getReservationByNric(
            @NotNull @NotBlank @RequestParam("nric") String nric) {
        return ResponseEntity.ok(reservationService.getReservationByNric(nric));
    }

    @PostMapping({"/book-vaccination-slot"})
    public ResponseEntity<String> bookVaccinationSlot(@RequestBody ReservationDTO reservationDTO) {
        if (isRequestValid(reservationDTO) && !reservationService.isNricExist(reservationDTO.getNric())) {
            if (reservationService.isSlotAvailable(reservationDTO)) {
                return ResponseEntity.ok(reservationService.reserveVaccinationSlot(reservationDTO));
            } else {
                return ResponseEntity.status(HttpStatus.IM_USED).body("Slot already booked. Please select another slot.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid request or reservation already exists!!");
        }
    }

    @DeleteMapping("/delete-reservation")
    public ResponseEntity<String> deleteReservation(
            @NotNull @NotBlank @RequestParam("reservationId") Integer reservationId) {
        if (reservationId != null && reservationService.reservationExists(reservationId)) {
            String response = reservationService.deleteReservation(reservationId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid request or reservation does not exists!!");
        }
    }

    @PutMapping("/update-reservation")
    public ResponseEntity<String> updateReservation(
            @NotNull @NotBlank @RequestParam("reservationId") Integer reservationId,
            @NotNull @RequestBody ReservationDTO reservationDTO) {
        if (reservationId != null && isRequestValid(reservationDTO)
                && reservationService.reservationExists(reservationId)) {
            if(reservationService.isSlotAvailable(reservationDTO)){
                String response = reservationService.updateReservation(reservationId, reservationDTO);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.IM_USED).body("Slot already booked. Please select another slot.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid request or reservation does not exists!!");
        }
    }

    private boolean isRequestValid(ReservationDTO reservationDTO) {
        if (reservationDTO != null
                && StringUtils.isNotBlank(reservationDTO.getNric())
                && StringUtils.isNotBlank(reservationDTO.getName())
                && StringUtils.isNotBlank(reservationDTO.getCentreName())
                && StringUtils.isNotBlank(reservationDTO.getReservationTime())) {
            return true;
        }
        return false;
    }
}
