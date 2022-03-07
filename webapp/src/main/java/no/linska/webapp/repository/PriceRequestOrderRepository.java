package no.linska.webapp.repository;


import no.linska.webapp.entity.PriceRequestOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRequestOrderRepository  extends CrudRepository<PriceRequestOrder, Long> {

    @Query(value = "SELECT * FROM price_request_order WHERE user_id = ?", nativeQuery = true)
    List<PriceRequestOrder> findByUserId(Long id);

}
