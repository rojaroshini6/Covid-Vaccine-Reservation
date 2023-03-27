package com.interview.roja.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "vaccination_centre")
public class VaccinationCentre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccination_centre_id")
    private Integer id;

    @Column(name = "centre_name")
    private String centreName;

    public VaccinationCentre() {
        super();
    }

    public VaccinationCentre(String centreName) {
        super();
        this.centreName = centreName;
    }

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
}
