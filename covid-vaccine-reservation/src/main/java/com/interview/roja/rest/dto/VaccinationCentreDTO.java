package com.interview.roja.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaccinationCentreDTO implements Serializable {

    private Integer id;

    private String centreName;

    private Set<String> slotDates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public Set<String> getSlotDates() {
        return slotDates;
    }

    public void setSlotDates(Set<String> slotDates) {
        this.slotDates = slotDates;
    }

    @Override
    public String toString() {
        return "VaccinationCentreDTO{" +
                "id=" + id +
                ", centreName='" + centreName + '\'' +
                ", slotDates=" + slotDates +
                '}';
    }
}
