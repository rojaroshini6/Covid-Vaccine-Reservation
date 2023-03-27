package com.interview.roja;

import com.interview.roja.data.model.Employee;
import com.interview.roja.data.model.Reservation;
import com.interview.roja.data.model.VaccinationCentre;
import com.interview.roja.data.repository.EmployeeRepository;
import com.interview.roja.data.repository.ReservationRepository;
import com.interview.roja.util.GlobalConstants;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class CovidVaccineReservation implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CovidVaccineReservation.class, args);
    }

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        if (CollectionUtils.isEmpty(employees)) {
            VaccinationCentre vc1 = new VaccinationCentre("Punggol Community Centre");
            VaccinationCentre vc2 = new VaccinationCentre("Little India Community Centre");
            String dateStr1 = "2021-11-19";
            String dateStr2 = "2021-11-20";
            Date date1 = DateUtils.parseDateStrictly(dateStr1.concat(" 10:00"), new String(GlobalConstants.YYYY_MM_DD_HH_MM));
            Date date2 = DateUtils.parseDateStrictly(dateStr1.concat(" 16:00"), new String(GlobalConstants.YYYY_MM_DD_HH_MM));
            Date date3 = DateUtils.parseDateStrictly(dateStr2.concat(" 13:45"), new String(GlobalConstants.YYYY_MM_DD_HH_MM));
            Date date4 = DateUtils.parseDateStrictly(dateStr2.concat(" 11:45"), new String(GlobalConstants.YYYY_MM_DD_HH_MM));
            Date date5 = DateUtils.parseDateStrictly(dateStr1.concat(" 10:15"), new String(GlobalConstants.YYYY_MM_DD_HH_MM));
            System.out.println(date1);
            System.out.println(date2);
            System.out.println(date3);
            System.out.println(date4);
            System.out.println(date5);
            Reservation reservation1 = new Reservation("G320", "Roja0", vc1, date1, "A");
            Reservation reservation2 = new Reservation("G321", "Roja1", vc1, date2, "A");
            Reservation reservation3 = new Reservation("G322", "Roja2", vc1, date3, "A");
            Reservation reservation4 = new Reservation("G323", "Roja3", vc2, date4, "A");
            Reservation reservation5 = new Reservation("G324", "Roja4", vc1, date5, "I");
            reservationRepository.saveAll(Arrays.asList(
                    reservation1, reservation2, reservation3, reservation4, reservation5));

            Employee employee1 = new Employee("Roshini1", (short) 2, vc1, Time.valueOf("10:00:00"), Time.valueOf("14:00:00"));
            Employee employee2 = new Employee("Roshini2", (short) 2, vc1, Time.valueOf("14:00:00"), Time.valueOf("18:00:00"));
            Employee employee3 = new Employee("Roshini3", (short) 2, vc2, Time.valueOf("10:00:00"), Time.valueOf("14:00:00"));
            employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));
        }
    }
}
