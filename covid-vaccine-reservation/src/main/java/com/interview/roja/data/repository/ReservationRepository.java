package com.interview.roja.data.repository;

import com.interview.roja.data.model.Reservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    List<Reservation> findByStatus(String status);

    @Query("from Reservation where reservation_id= :id and status = 'A'")
    Optional<Reservation> findByActiveId(@Param("id") Integer id);

    @Query("from Reservation where nric= :nric and status = 'A'")
    Optional<Reservation> findByActiveNric(@Param("nric") String nric);

    @Query("from Reservation where vaccination_centre_id = :vaccinationCentreId and reservationTime between :start and :end and status='A'")
    Optional<List<Reservation>> findAllActiveReservationByCentreAndDate(
            @Param(value = "vaccinationCentreId") Integer vaccinationCentreId,
            @Param(value = "start") Date start, @Param(value = "end") Date end);

    @Modifying
    @Query("update Reservation set status='I' where reservation_id = :id and status = 'A'")
    void inactivateReservation(@Param("id") Integer id);
}
