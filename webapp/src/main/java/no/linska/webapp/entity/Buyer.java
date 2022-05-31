package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "buyer")
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyer_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String city;

    private Integer postBox;

    private String streetName;

    private String streetNumber;

}
