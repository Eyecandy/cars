package no.linska.webapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Seller seller;


    @ManyToOne
    @JoinColumn(name = "price_request_id")
    private PriceRequest priceRequest;


    private Long totalPrice;

    private boolean sent = false;

    private boolean answered = false;

    private String offerFilePath;

    private boolean customerAcceptedThisOffer = false;

}
