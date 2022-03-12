package no.linska.webapp.entity;


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
    @Column(name = "org_number")
    private Long orgNumber;


    private String name;



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "retailer_car_brands",
            joinColumns = {
                    @JoinColumn(name = "org_number", referencedColumnName = "org_number",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "car_brand_id", referencedColumnName = "car_brand_id",
                            nullable = false, updatable = false)})
    private Set<CarBrand> carBrands;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "retailer")
    private List<Seller> sellers;







}
