package com.interview.roja.service.impl;

import com.interview.roja.data.model.Employee;
import com.interview.roja.data.model.Reservation;
import com.interview.roja.data.model.VaccinationCentre;
import com.interview.roja.data.repository.EmployeeRepository;
import com.interview.roja.data.repository.ReservationRepository;
import com.interview.roja.data.repository.VaccinationCentreRepository;
import com.interview.roja.rest.dto.ReservationDTO;
import com.interview.roja.service.ReservationService;
import com.interview.roja.util.GlobalConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VaccinationCentreRepository vaccinationCentreRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReservationDTO getReservationByNric(String nric) {
        ReservationDTO reservationDTO = new ReservationDTO();
        if (StringUtils.isNotBlank(nric)) {
            Optional<Reservation> reservation = reservationRepository.findByActiveNric(nric);
            if (reservation.isPresent()) {
                reservationDTO.setId(reservation.get().getId());
                reservationDTO.setNric(reservation.get().getNric());
                reservationDTO.setName(reservation.get().getName());
                reservationDTO.setReservationTime(reservation.get().getReservationTime().toString());
                reservationDTO.setCentreName(reservation.get().getVaccinationCentre().getCentreName());
            }
        }
        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> getAllActiveReservation() {
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findByStatus(GlobalConstants.ACTIVE);
        if(!CollectionUtils.isEmpty(reservations)){
            for(Reservation reservation:reservations){
                ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setId(reservation.getId());
                reservationDTO.setNric(reservation.getNric());
                reservationDTO.setName(reservation.getName());
                reservationDTO.setReservationTime(reservation.getReservationTime().toString());
                reservationDTO.setCentreName(reservation.getVaccinationCentre().getCentreName());
                reservationDTOs.add(reservationDTO);
            }
        }
        return reservationDTOs;
    }

    @Override
    public boolean reservationExists(Integer id) {
        return reservationRepository.findByActiveId(id).isPresent();
    }

    @Override
    public boolean isNricExist(String nric) {
        return reservationRepository.findByActiveNric(nric).isPresent();
    }

    @Override
    @Transactional
    public String reserveVaccinationSlot(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(205);
        reservation.setName(reservationDTO.getName());
        reservation.setNric(reservationDTO.getNric());
        Date date = null;
        try {
            date = DateUtils.parseDate(reservationDTO.getReservationTime(),
                    new String(GlobalConstants.YYYY_MM_DD_HH_MM));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        reservation.setReservationTime(date);
        reservation.setStatus(GlobalConstants.ACTIVE);
        Optional<VaccinationCentre> vaccinationCentre =
                vaccinationCentreRepository.findByCentreName(reservationDTO.getCentreName());
        if (vaccinationCentre.isPresent()) {
            reservation.setVaccinationCentre(vaccinationCentre.get());
        }
        reservationRepository.save(reservation);
        return "Reservation Successful!!";
    }

    @Override
    @Transactional
    public String deleteReservation(Integer reservationId) {
        if (reservationId != null) {
            reservationRepository.inactivateReservation(reservationId);
        }
        return "Reservation cancelled successfully!!";
    }

    @Override
    @Transactional
    public String updateReservation(Integer reservationId, ReservationDTO reservationDTO) {
        reservationRepository.inactivateReservation(reservationId);
        reserveVaccinationSlot(reservationDTO);
        return "Reservation updated successfully!!";
    }

    @Override
    public boolean isSlotAvailable(ReservationDTO reservationDTO) {
        Optional<VaccinationCentre> vaccinationCentre =
                vaccinationCentreRepository.findByCentreName(reservationDTO.getCentreName());

        if (vaccinationCentre.isPresent()) {
            List<Employee> nurses =
                    employeeRepository.findAllNurseForCenterId(vaccinationCentre.get().getId());

            if (!CollectionUtils.isEmpty(nurses)) {
                // Find all available slot
                Map<String, Integer> timeSlotDateAndCountMap = new HashMap<>();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(GlobalConstants.YYYY_MM_DD_HH_MM);
                LocalDateTime givenDate = LocalDateTime.parse(
                        reservationDTO.getReservationTime(), dateTimeFormatter);

                for (Employee nurse : nurses) {
                    LocalTime from = nurse.getDutyScheduleTimeFrom().toLocalTime();
                    LocalTime to = nurse.getDutyScheduleTimeTo().toLocalTime();
                    while (from.isBefore(to.minusMinutes(GlobalConstants.MINUTES_INTERVAL_14))) {
                        String slotDate = givenDate.withHour(from.getHour())
                                .withMinute(from.getMinute()).format(dateTimeFormatter);
                        if (timeSlotDateAndCountMap.containsKey(slotDate)) {
                            timeSlotDateAndCountMap.put(slotDate, timeSlotDateAndCountMap.get(slotDate) + 1);
                        } else {
                            timeSlotDateAndCountMap.put(slotDate, 1);
                        }
                        from = from.plusMinutes(GlobalConstants.MINUTES_INTERVAL_15);
                    }
                }

                // Find All Reserved Slots
                Map<String, Integer> reservedSlotDateAndCountMap = new HashMap<>();
                try {
                    Date date = DateUtils.parseDate(reservationDTO.getReservationTime(),
                            new String(GlobalConstants.YYYY_MM_DD_HH_MM));
                    Date startDate = DateUtils.addMinutes(date, -1);
                    Date endDate = DateUtils.addMinutes(date, 1);
                    Optional<List<Reservation>> reservations =
                            reservationRepository.findAllActiveReservationByCentreAndDate(
                                    vaccinationCentre.get().getId(), startDate, endDate);
                    if (reservations.isPresent()) {
                        for (Reservation reservation : reservations.get()) {
                            String reservedDate = reservation.getReservationTime().toString().substring(0, 16);
                            if (reservedSlotDateAndCountMap.containsKey(reservedDate)) {
                                reservedSlotDateAndCountMap.put(
                                        reservedDate, reservedSlotDateAndCountMap.get(reservedDate) + 1);
                            } else {
                                reservedSlotDateAndCountMap.put(reservedDate, 1);
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Check vacant slots against reserved slots
                if (CollectionUtils.isEmpty(reservedSlotDateAndCountMap)) {
                    if (timeSlotDateAndCountMap.containsKey(reservationDTO.getReservationTime())) {
                        return true;
                    }
                } else {
                    Integer totalSlotCount = timeSlotDateAndCountMap.get(reservationDTO.getReservationTime());
                    Integer reservedSlotCount = reservedSlotDateAndCountMap.get(reservationDTO.getReservationTime());
                    if (totalSlotCount != reservedSlotCount) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
