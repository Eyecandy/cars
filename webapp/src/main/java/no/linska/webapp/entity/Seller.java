package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "seller")
@Data
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_number", nullable = false)
    private Retailer retailer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<PriceRequestOrder> priceRequestOrders;
}
