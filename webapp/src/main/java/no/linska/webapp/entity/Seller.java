package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "seller")
@Data
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "retailer_id", nullable = false)
    private Retailer retailer;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
    private List<PriceRequestOrder> priceRequestOrders;
}
