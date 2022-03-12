package no.linska.webapp.entity;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "price_request_order")
@Data
public class PriceRequestOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;


    @ManyToOne
    @JoinColumn(name = "price_request_id")
    private PriceRequest priceRequest;


    private Long totalPrice;

}
