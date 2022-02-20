package no.linska.webapp.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
