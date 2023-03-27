package com.interview.roja.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Time;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO implements Serializable {

    private Integer id;

    private String name;

    private String role;

    private String centreName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    private Time dutyScheduleTimeFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    private Time dutyScheduleTimeTo;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
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

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", centreName='" + centreName + '\'' +
                ", dutyScheduleTimeFrom=" + dutyScheduleTimeFrom +
                ", dutyScheduleTimeTo=" + dutyScheduleTimeTo +
                '}';
    }
}
