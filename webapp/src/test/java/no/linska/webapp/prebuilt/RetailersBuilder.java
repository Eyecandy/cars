package no.linska.webapp.prebuilt;

import no.linska.webapp.entity.Retailer;


public class RetailersBuilder {


    public  static Retailer createRetailer() {
        Retailer retailer = new Retailer();
        retailer.setName("business1");
        retailer.setOrgNumber(12345L);

        return retailer;
    }

}
