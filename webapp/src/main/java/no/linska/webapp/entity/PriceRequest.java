package no.linska.webapp.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "price_request")
@Data
public class PriceRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotNull(message = "Må velge bilmerke")
    @JoinColumn(name = "car_brand_id", nullable = false)
    private CarBrand carBrand;

    @NotNull(message = "Må velge fylke")
    @OneToOne
    @JoinColumn(name = "county_id", nullable = false)
    private County county;

    @NotNull(message = "Må velge konfigmetode")
    @OneToOne
    @JoinColumn(name = "config_method_id", nullable = false)
    private ConfigMethod configMethod;

    @NotNull(message = "Konfigurasjon kan ikke være tom")
    @Column
    private String configuration;

    @Column
    private Date deadline;

    @Column(name = "studded_tire")
    private Boolean studdedTire;
}
