package no.linska.webapp.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

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

    @Transient
    private MultipartFile fileConfiguration;

    @Column
    private Date deadline;

    @NotNull(message = "Dekk valg kan ikke være tom")
    @OneToOne
    @JoinColumn(name = "tire_option_id", nullable = false)
    private TireOption tireOption;
}
