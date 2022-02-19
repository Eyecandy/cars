package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "car_price_request")
@Data
public class PriceRequest {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column
    private Integer carBrandId;

    @NotNull
    @Column
    private Integer countyId;

    @NotNull
    @Column
    private Integer configMethodId;

    @NotBlank
    @Column
    private String configuration;

    @Column
    private Date deadline;
}
