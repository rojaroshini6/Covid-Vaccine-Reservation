package com.interview.roja.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDTO implements Serializable {

    private Integer id;

    private String nric;

    private String name;

    private String centreName;

    private String reservationTime;

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

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", nric='" + nric + '\'' +
                ", name='" + name + '\'' +
                ", centreName='" + centreName + '\'' +
                ", reservationTime='" + reservationTime + '\'' +
                '}';
    }
}
