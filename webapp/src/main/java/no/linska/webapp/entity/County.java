package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "county")
@Data
public class County {

    @Id
    @Column(name = "county_id")
    private Integer id;

    @Column(name = "county_name")
    private String name;


}
