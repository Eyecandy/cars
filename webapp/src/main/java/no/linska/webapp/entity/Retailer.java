package no.linska.webapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity(name = "retailer")
@Data
@Table(name = "retailer")
public class Retailer implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "retailer_id")
    private Long id;


    @Column(name = "org_number")
    private Long orgNumber;


    private String name;



    @ManyToMany
    @JoinTable(name = "retailer_car_brands",
            joinColumns = {
                    @JoinColumn(name = "retailer_id", referencedColumnName = "retailer_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "car_brand_id", referencedColumnName = "car_brand_id",
                            nullable = false, updatable = false)})
    @JsonIgnore
    private Set<CarBrand> carBrands;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "retailer")
    private List<Seller> sellers;


}
