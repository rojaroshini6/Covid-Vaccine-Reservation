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
import java.sql.Time;

@SuppressWarnings("serial")
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "employee_role")
    private short role;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "vaccination_centre_id")
    private VaccinationCentre vaccinationCentre;

    @Column(name = "duty_schedule_time_from")
    private Time dutyScheduleTimeFrom;

    @Column(name = "duty_schedule_time_to")
    private Time dutyScheduleTimeTo;

    public Employee() {
        super();
    }

    public Employee(String name, short role, VaccinationCentre vaccinationCentre,
                    Time dutyScheduleTimeFrom, Time dutyScheduleTimeTo) {
        super();
        this.name = name;
        this.vaccinationCentre = vaccinationCentre;
        this.role = role;
        this.dutyScheduleTimeFrom = dutyScheduleTimeFrom;
        this.dutyScheduleTimeTo = dutyScheduleTimeTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public Time getDutyScheduleTimeFrom() {
        return dutyScheduleTimeFrom;
    }

    public void setDutyScheduleTimeFrom(Time dutyScheduleTimeFrom) {
        this.dutyScheduleTimeFrom = dutyScheduleTimeFrom;
    }

    public Time getDutyScheduleTimeTo() {
        return dutyScheduleTimeTo;
    }

    public void setDutyScheduleTimeTo(Time dutyScheduleTimeTo) {
        this.dutyScheduleTimeTo = dutyScheduleTimeTo;
    }
}
