package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "car_brand")
@Data
@Table(name = "car_brand")
public class CarBrand implements Serializable {

    @Id
    @Column(name = "car_brand_id")
    private int id;

    @Column(name = "car_brand_name")
    private String name;

    @ManyToMany(mappedBy = "carBrands", fetch = FetchType.LAZY)
    private Set<Retailer> retailers = new HashSet<>();
}
