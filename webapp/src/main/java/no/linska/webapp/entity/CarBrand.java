package no.linska.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Entity(name = "car_brand")
@Data
@Table(name = "car_brand")
public class CarBrand implements Serializable {

    @Id
    @Column(name = "car_brand_id")
    private Integer id;

    @Column(name = "car_brand_name")
    private String name;


    @ManyToMany(mappedBy = "carBrands")
    @JsonIgnore
    private Collection<Retailer> retailers = new HashSet<>();
}
