package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "tire_option")
public class TireOption {

    @Id
    @Column(name = "tire_option_id")
    private Integer id;

    @NotNull
    private String name;
}
