package no.linska.webapp.prebuilt;

import no.linska.webapp.entity.Seller;

public class SellerBuilder {

    public static Seller createSeller() {
        Seller seller = new Seller();
        seller.setId(1);
        return seller;
    }
}
