package com.interview.roja.service.impl;

import com.interview.roja.data.model.Employee;
import com.interview.roja.data.model.Reservation;
import com.interview.roja.data.model.VaccinationCentre;
import com.interview.roja.data.repository.EmployeeRepository;
import com.interview.roja.data.repository.ReservationRepository;
import com.interview.roja.data.repository.VaccinationCentreRepository;
import com.interview.roja.rest.dto.VaccinationCentreDTO;
import com.interview.roja.service.VaccinationCentreService;
import com.interview.roja.util.GlobalConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VaccinationCentreServiceImpl implements VaccinationCentreService {

    @Autowired
    private VaccinationCentreRepository vaccinationCentreRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<VaccinationCentreDTO> listVaccinationCentre(Integer centreId, String centreName) {
        List<VaccinationCentreDTO> vaccinationCentreDTOs = new ArrayList<>();
        if (centreId != null) {
            Optional<VaccinationCentre> vaccinationCentre = vaccinationCentreRepository.findById(centreId);
            if (vaccinationCentre.isPresent()) {
                VaccinationCentreDTO vaccinationCentreDTO = new VaccinationCentreDTO();
                vaccinationCentreDTO.setId(vaccinationCentre.get().getId());
                vaccinationCentreDTO.setCentreName(vaccinationCentre.get().getCentreName());
                vaccinationCentreDTOs.add(vaccinationCentreDTO);
            }
        } else if (StringUtils.isNotBlank(centreName)) {
            Optional<VaccinationCentre> vaccinationCentre = vaccinationCentreRepository.findByCentreName(centreName);
            if (vaccinationCentre.isPresent()) {
                VaccinationCentreDTO vaccinationCentreDTO = new VaccinationCentreDTO();
                vaccinationCentreDTO.setId(vaccinationCentre.get().getId());
                vaccinationCentreDTO.setCentreName(vaccinationCentre.get().getCentreName());
                vaccinationCentreDTOs.add(vaccinationCentreDTO);
            }
        } else {
            List<VaccinationCentre> vaccinationCentres = vaccinationCentreRepository.findAll();
            if (!CollectionUtils.isEmpty(vaccinationCentres)) {
                for (VaccinationCentre vaccinationCentre : vaccinationCentres) {
                    VaccinationCentreDTO vaccinationCentreDTO = new VaccinationCentreDTO();
                    vaccinationCentreDTO.setId(vaccinationCentre.getId());
                    vaccinationCentreDTO.setCentreName(vaccinationCentre.getCentreName());
                    vaccinationCentreDTOs.add(vaccinationCentreDTO);
                }
            }
        }
        return vaccinationCentreDTOs;
    }

    @Override
    public VaccinationCentreDTO availableCentreSlots(Integer centreId, String dateStr) {
        VaccinationCentreDTO vaccinationCentreDTO = new VaccinationCentreDTO();

        Optional<VaccinationCentre> vaccinationCentre = vaccinationCentreRepository.findById(centreId);

        if (vaccinationCentre.isPresent()) {
            vaccinationCentreDTO.setId(vaccinationCentre.get().getId());
            vaccinationCentreDTO.setCentreName(vaccinationCentre.get().getCentreName());
            List<Employee> nurses = employeeRepository.findAllNurseForCenterId(centreId);

            if (!CollectionUtils.isEmpty(nurses)) {

                // Find All Slots
                Map<String, Integer> timeSlotDateAndCountMap = new HashMap<>();
                String givenDateStr = dateStr.concat(" 00:00");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(GlobalConstants.YYYY_MM_DD_HH_MM);
                LocalDateTime givenDate = LocalDateTime.parse(givenDateStr, dateTimeFormatter);
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
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = DateUtils.parseDate(dateStr.concat(GlobalConstants.START_TIME),
                            new String(GlobalConstants.YYYY_MM_DD_HH_MM));
                    endDate = DateUtils.parseDate(dateStr.concat(GlobalConstants.END_TIME),
                            new String(GlobalConstants.YYYY_MM_DD_HH_MM));
                    Optional<List<Reservation>> reservations =
                            reservationRepository.findAllActiveReservationByCentreAndDate(
                                    centreId, startDate, endDate);
                    if (reservations.isPresent()) {
                        for (Reservation reservation : reservations.get()) {
                            String reservedDate = reservation.getReservationTime().toString().substring(0, 16);
                            if (reservedSlotDateAndCountMap.containsKey(reservedDate)) {
                                reservedSlotDateAndCountMap.put(reservedDate, reservedSlotDateAndCountMap.get(reservedDate) + 1);
                            } else {
                                reservedSlotDateAndCountMap.put(reservedDate, 1);
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Find All Vacant Slots
                if (CollectionUtils.isEmpty(reservedSlotDateAndCountMap)) {
                    vaccinationCentreDTO.setSlotDates(timeSlotDateAndCountMap.keySet());
                } else {
                    Map<String, Integer> vacantTimeSlotDateAndCountMap = new HashMap<>();
                    timeSlotDateAndCountMap.forEach((k, v) -> {
                        if (reservedSlotDateAndCountMap.containsKey(k)) {
                            if (reservedSlotDateAndCountMap.get(k) != v) {
                                vacantTimeSlotDateAndCountMap.put(k, (v - reservedSlotDateAndCountMap.get(k)));
                            }
                        } else {
                            vacantTimeSlotDateAndCountMap.put(k, v);
                        }
                    });
                    vaccinationCentreDTO.setSlotDates(vacantTimeSlotDateAndCountMap.keySet());
                }
            }
        }
        return vaccinationCentreDTO;
    }
}
