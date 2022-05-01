package no.linska.webapp.repository;


import no.linska.webapp.entity.PriceRequestOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRequestOrderRepository  extends CrudRepository<PriceRequestOrder, Long> {

    @Query(value = "select * from price_request_order\n" +
            "where seller_id = (select seller_id from seller\n" +
            "where user_id = ?)", nativeQuery = true)
    List<PriceRequestOrder> findByUserId(Long id);


    @Query(value = "select * from price_request_order\n" +
            "where price_request_id = ?"
            , nativeQuery = true)
    List<PriceRequestOrder> findPriceRequestId(Long id);

}
