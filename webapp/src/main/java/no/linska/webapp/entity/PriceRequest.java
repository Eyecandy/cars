package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity(name = "car_price_request")
@Data
public class PriceRequest {

    @Id
    private Long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Column
    private Integer carBrandId;

    @NotBlank
    @Column
    private Integer countyId;

    @NotBlank
    @Column
    private Integer configMethodId;

    @NotBlank
    @Column
    private String configuration;

    @NotBlank
    @Column
    private Date deadline;
}
