package no.linska.webapp.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "config_method")
@Data
public class ConfigMethod {

    @Id
    @Column(name = "config_method_id")
    private Integer id;

    @NotNull
    private String name;



}
