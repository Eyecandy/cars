package no.linska.webapp.entity;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "user_role")
@Data
public class UserRole {

    @Id
    @Column(name="role_id")
    private Integer id;

    @Column(name="role_type")
    private String type;
}
