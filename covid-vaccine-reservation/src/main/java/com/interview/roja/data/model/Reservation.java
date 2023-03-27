package com.interview.roja.data.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer id;

    @Column(name = "nric")
    private String nric;

    @Column(name = "customer_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vaccination_centre_id")
    private VaccinationCentre vaccinationCentre;

    @Column(name = "reservation_time")
    private Date reservationTime;

    @Column(name = "status")
    private String status;

    public Reservation() {
        super();
    }

    public Reservation(String nric, String name, VaccinationCentre vaccinationCentre,
                       Date reservationTime, String status) {
        super();
        this.vaccinationCentre = vaccinationCentre;
        this.nric = nric;
        this.name = name;
        this.reservationTime = reservationTime;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VaccinationCentre getVaccinationCentre() {
        return vaccinationCentre;
    }

    public void setVaccinationCentre(VaccinationCentre vaccinationCentre) {
        this.vaccinationCentre = vaccinationCentre;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
