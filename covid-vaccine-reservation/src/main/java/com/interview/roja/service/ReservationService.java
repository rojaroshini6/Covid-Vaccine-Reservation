package com.interview.roja.service;

import com.interview.roja.rest.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {

    public List<ReservationDTO> getAllActiveReservation();

    public ReservationDTO getReservationByNric(String nric);

    public boolean reservationExists(Integer id);

    public boolean isNricExist(String nric);

    public boolean isSlotAvailable(ReservationDTO reservationDTO);

    public String reserveVaccinationSlot(ReservationDTO reservationDTO);

    public String deleteReservation(Integer reservationId);

    public String updateReservation(Integer reservationId, ReservationDTO reservationDTO);
}
