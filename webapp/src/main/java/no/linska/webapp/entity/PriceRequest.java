package no.linska.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
    @JsonIgnore
    private User user;

    @NotNull(message = "Må velge bilmerke")
    @OneToOne
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


    @NotNull
    private Date deadline;

    @NotNull(message = "Dekk valg kan ikke være tom")
    @OneToOne
    @JoinColumn(name = "tire_option_id", nullable = false)
    private TireOption tireOption;

    @Column(name = "num_retailers_sent_to")
    private Integer numRetailersSentTo;
    @Column(name = "num_retailers_answered")
    private Integer numRetailersAnswered;
}
